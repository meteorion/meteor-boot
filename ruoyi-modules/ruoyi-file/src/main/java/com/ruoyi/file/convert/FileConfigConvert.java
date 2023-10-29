package com.ruoyi.file.convert;

import com.ruoyi.common.core.domain.PageResult;
import com.ruoyi.file.controller.vo.config.FileConfigCreateReqVO;
import com.ruoyi.file.controller.vo.config.FileConfigRespVO;
import com.ruoyi.file.controller.vo.config.FileConfigUpdateReqVO;
import com.ruoyi.file.domain.FileStrogeClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文件配置 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface FileConfigConvert {

    FileConfigConvert INSTANCE = Mappers.getMapper(FileConfigConvert.class);

    @Mapping(target = "config", ignore = true)
    FileStrogeClient convert(FileConfigCreateReqVO bean);

    @Mapping(target = "config", ignore = true)
    FileStrogeClient convert(FileConfigUpdateReqVO bean);

    FileConfigRespVO convert(FileStrogeClient bean);

    List<FileConfigRespVO> convertList(List<FileStrogeClient> list);

    PageResult<FileConfigRespVO> convertPage(PageResult<FileStrogeClient> page);

}
