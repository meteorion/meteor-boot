package pers.meteor.file.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.meteor.common.core.domain.PageResult;
import pers.meteor.common.core.utils.StringUtils;
import pers.meteor.file.controller.vo.config.FileConfigPageReqVO;
import pers.meteor.file.domain.FileStrogeClient;

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
