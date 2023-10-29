package com.ruoyi.file.service.impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.DigestUtil;
import com.meteor.common.file.core.client.FileClient;
import com.ruoyi.common.core.domain.PageResult;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.file.FileTypeUtils;
import com.ruoyi.file.controller.vo.file.FilePageReqVO;
import com.ruoyi.file.domain.File;
import com.ruoyi.file.mapper.FileMapper;
import com.ruoyi.file.service.FileService;
import com.ruoyi.file.service.FileStrogeClientService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;

/**
 * @author meteor
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    @Resource
    private FileStrogeClientService fileStrogeClientService;

    @Resource
    private FileMapper fileMapper;

    @Override
    public PageResult<File> getFilePage(FilePageReqVO pageReqVO) {
        return fileMapper.selectPage(pageReqVO);
    }

    @Override
    @SneakyThrows
    public String createFile(String name, String path, byte[] content) {
        // 计算默认的 path 名
        String type = FileTypeUtils.getFileType(content);
        if (StringUtils.isEmpty(path)) {
            path = generatePath(content, name);
        }
        // 如果 name 为空，则使用 path 填充
        if (StringUtils.isEmpty(name)) {
            name = path;
        }

        // 上传到文件存储器
        FileClient client = fileStrogeClientService.getMasterFileClient();
        Assert.notNull(client, "客户端(master) 不能为空");
        String url = client.upload(content, path, type);

        // 保存到数据库
        File file = new File();
        file.setConfigId(client.getId());
        file.setName(name);
        file.setPath(path);
        file.setUrl(url);
        file.setType(type);
        file.setSize(content.length);
        fileMapper.insert(file);
        return url;
    }

    @Override
    public void deleteFile(Long id) throws Exception {
        // 校验存在
        File file = validateFileExists(id);

        // 从文件存储器中删除
        FileClient client = fileStrogeClientService.getFileClient(file.getConfigId());
        Assert.notNull(client, "客户端({}) 不能为空", file.getConfigId());
        client.delete(file.getPath());

        // 删除记录
        fileMapper.deleteById(id);
    }

    private File validateFileExists(Long id) {
        File file = fileMapper.selectById(id);
        if (file == null) {
            throw new ServiceException("FILE_NOT_EXISTS");
        }
        return file;
    }

    @Override
    public byte[] getFileContent(Long configId, String path) throws Exception {
        FileClient client = fileStrogeClientService.getFileClient(configId);
        Assert.notNull(client, "客户端({}) 不能为空", configId);
        return client.getContent(path);
    }

    /**
     * 生成文件路径
     *
     * @param content      文件内容
     * @param originalName 原始文件名
     * @return path，唯一不可重复
     */
    public static String generatePath(byte[] content, String originalName) {
        String sha256Hex = DigestUtil.sha256Hex(content);
        // 情况一：如果存在 name，则优先使用 name 的后缀
        if (StringUtils.hasText(originalName)) {
            String extName = FileNameUtil.extName(originalName);
            return StringUtils.isBlank(extName) ? sha256Hex : sha256Hex + "." + extName;
        }
        // 情况二：基于 content 计算
        return sha256Hex + '.' + FileTypeUtil.getType(new ByteArrayInputStream(content));
    }
}
