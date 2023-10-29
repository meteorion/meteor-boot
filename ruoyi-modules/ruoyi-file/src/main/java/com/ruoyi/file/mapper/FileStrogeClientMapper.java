package com.ruoyi.file.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageResult;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.file.controller.vo.config.FileConfigPageReqVO;
import com.ruoyi.file.domain.FileStrogeClient;

/**
 * @author meteor
 */
public interface FileStrogeClientMapper extends BaseMapper<FileStrogeClient> {
    default PageResult<FileStrogeClient> selectPage(FileConfigPageReqVO reqVO) {
        LambdaQueryWrapper<FileStrogeClient> queryWrapper = Wrappers.lambdaQuery(FileStrogeClient.class)
                .like(StringUtils.hasText(reqVO.getName()), FileStrogeClient::getName, reqVO.getName())
                .eq(reqVO.getStorage() != null, FileStrogeClient::getStorage, reqVO.getStorage())
                .le(reqVO.getCreateTime() != null, FileStrogeClient::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FileStrogeClient::getId);

        Page<FileStrogeClient> fileStrogeClientPage = selectPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), queryWrapper);

        return new PageResult<>(fileStrogeClientPage.getRecords(), fileStrogeClientPage.getTotal());
    }
}
