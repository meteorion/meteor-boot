package com.ruoyi.file.mapper;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.meteor.common.file.core.client.db.DBFileContentFrameworkDAO;
import com.ruoyi.file.domain.FileContent;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Repository
public class FileContentDAOImpl implements DBFileContentFrameworkDAO {

    @Resource
    private FileContentMapper fileContentMapper;

    @Override
    public void insert(Long configId, String path, byte[] content) {
        FileContent entity = FileContent.builder().configId(configId)
                .path(path).content(content).build();
        fileContentMapper.insert(entity);
    }

    @Override
    public void delete(Long configId, String path) {
        fileContentMapper.delete(buildQuery(configId, path));
    }

    @Override
    public byte[] selectContent(Long configId, String path) {
        List<FileContent> list = fileContentMapper.selectList(buildQuery(configId, path)
                .select(FileContent::getContent).orderByDesc(FileContent::getId));
        return Optional.ofNullable(CollUtil.getFirst(list))
                .map(FileContent::getContent)
                .orElse(null);
    }

    private LambdaQueryWrapper<FileContent> buildQuery(Long configId, String path) {
        return new LambdaQueryWrapper<FileContent>()
                .eq(FileContent::getConfigId, configId)
                .eq(FileContent::getPath, path);
    }

}
