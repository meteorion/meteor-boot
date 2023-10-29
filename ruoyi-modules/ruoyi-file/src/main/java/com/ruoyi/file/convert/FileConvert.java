package com.ruoyi.file.convert;

import com.ruoyi.common.core.domain.PageResult;
import com.ruoyi.file.controller.vo.file.FileRespVO;
import com.ruoyi.file.domain.File;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(File bean);

    PageResult<FileRespVO> convertPage(PageResult<File> page);

}
