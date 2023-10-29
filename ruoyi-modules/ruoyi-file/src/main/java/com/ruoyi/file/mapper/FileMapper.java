package com.ruoyi.file.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.PageResult;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.file.controller.vo.file.FilePageReqVO;
import com.ruoyi.file.domain.File;

/**
 * @author meteor
 */
public interface FileMapper extends BaseMapper<File> {
    default PageResult<File> selectPage(FilePageReqVO reqVO) {
        LambdaQueryWrapper<File> queryWrapper = Wrappers.lambdaQuery(File.class)
                .like(StringUtils.hasText(reqVO.getPath()), File::getPath, reqVO.getPath())
                .like(StringUtils.hasText(reqVO.getType()), File::getType, reqVO.getType())
                .le(reqVO.getCreateTime() != null, File::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(File::getId);

        Page<File> filePage = selectPage(new Page<>(reqVO.getPageNo(), reqVO.getPageSize()), queryWrapper);

        return new PageResult<>(filePage.getRecords(), filePage.getTotal());
    }
}
