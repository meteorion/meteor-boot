package com.ruoyi.file.service.impl;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.meteor.common.file.core.client.FileClient;
import com.meteor.common.file.core.client.FileClientConfig;
import com.meteor.common.file.core.client.FileClientFactory;
import com.meteor.common.file.core.enums.FileStorageEnum;
import com.ruoyi.common.core.domain.PageResult;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.bean.BeanValidators;
import com.ruoyi.file.controller.vo.config.FileConfigCreateReqVO;
import com.ruoyi.file.controller.vo.config.FileConfigPageReqVO;
import com.ruoyi.file.controller.vo.config.FileConfigUpdateReqVO;
import com.ruoyi.file.convert.FileConfigConvert;
import com.ruoyi.file.domain.FileStrogeClient;
import com.ruoyi.file.mapper.FileStrogeClientMapper;
import com.ruoyi.file.service.FileStrogeClientService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;

/**
 * @author meteor
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FileStrogeClientServiceImpl implements FileStrogeClientService {
    private final FileStrogeClientMapper fileStrogeClientMapper;
    private final FileClientFactory fileClientFactory;
    private final Validator validator;

    @Getter
    private FileClient masterFileClient;

    @PostConstruct
    @Override
    public void initFileClient() {
        List<FileStrogeClient> fileConfigs = fileStrogeClientMapper.selectList(Wrappers.emptyWrapper());
        log.info("[initFileClient][初始化文件客户端，数量为:{}]", fileConfigs.size());

        for (FileStrogeClient fileConfig : fileConfigs) {
            fileClientFactory.createOrUpdateFileClient(fileConfig.getId(), fileConfig.getStorage(), fileConfig.getConfig());
            if (Boolean.TRUE.equals(fileConfig.getMaster())) {
                masterFileClient = fileClientFactory.getFileClient(fileConfig.getId());
            }
        }
    }

    @Override
    public Long createFileConfig(FileConfigCreateReqVO createReqVo) {
        FileStrogeClient fileConfig = FileConfigConvert.INSTANCE.convert(createReqVo);
        fileConfig.setConfig(parseClientConfig(createReqVo.getStorage(), createReqVo.getConfig()));
        fileConfig.setMaster(false);

        fileStrogeClientMapper.insert(fileConfig);

        return fileConfig.getId();
    }

    @Override
    public void updateFileConfig(FileConfigUpdateReqVO updateReqVO) {
        // 校验存在
        FileStrogeClient config = validateFileConfig(updateReqVO.getId());
        // 更新
        FileStrogeClient updateObj = FileConfigConvert.INSTANCE.convert(updateReqVO);
        updateObj.setConfig(parseClientConfig(config.getStorage(), updateReqVO.getConfig()));
        fileStrogeClientMapper.updateById(updateObj);
    }

    @Override
    public void updateFileConfigMaster(Long id) {
        // 校验存在
        validateFileConfig(id);
        // 更新其它为非 master
        fileStrogeClientMapper.update(FileStrogeClient.builder().master(false).build(), Wrappers.emptyWrapper());
        // 更新
        fileStrogeClientMapper.updateById(FileStrogeClient.builder().id(id).master(true).build());

        masterFileClient = fileClientFactory.getFileClient(id);
    }

    @Override
    public void deleteFileConfig(Long id) {
        validateFileConfig(id);
        fileStrogeClientMapper.deleteById(id);
        initFileClient();
    }

    @Override
    public FileClient getFileClient(Long id) {
        return fileClientFactory.getFileClient(id);
    }

    @Override
    public String testFileConfig(Long id) throws Exception {
        // 校验存在
        validateFileConfig(id);
        // 上传文件
        byte[] content = ResourceUtil.readBytes("file/erweima.jpg");
        return fileClientFactory.getFileClient(id).upload(content, IdUtil.fastSimpleUUID() + ".jpg", "image/jpeg");
    }

    @Override
    public FileStrogeClient getFileConfig(Long id) {
        return fileStrogeClientMapper.selectById(id);
    }

    @Override
    public PageResult<FileStrogeClient> getFileConfigPage(FileConfigPageReqVO pageReqVo) {
        return fileStrogeClientMapper.selectPage(pageReqVo);
    }

    private FileStrogeClient validateFileConfig(Long id) {
        FileStrogeClient config = fileStrogeClientMapper.selectById(id);
        if (config == null) {
            throw new ServiceException("配置不存在");
        }
        return config;
    }

    /**
     * 解析配置
     *
     * @param storage /
     * @param config /
     * @return /
     */
    private FileClientConfig parseClientConfig(Integer storage, Map<String, Object> config) {
        // 获取配置类
        Class<? extends FileClientConfig> configClass = FileStorageEnum.getByStorage(storage)
                .getConfigClass();
        FileClientConfig clientConfig = JSON.parseObject(JSON.toJSONString(config), configClass);
        // 参数校验
        BeanValidators.validateWithException(validator, clientConfig);
        // 设置参数
        return clientConfig;
    }
}
