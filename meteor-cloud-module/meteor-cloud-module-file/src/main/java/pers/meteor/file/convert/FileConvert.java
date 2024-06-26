package pers.meteor.file.convert;

import pers.meteor.common.core.domain.PageResult;
import pers.meteor.file.controller.vo.file.FileRespVO;
import pers.meteor.file.domain.File;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(File bean);

    PageResult<FileRespVO> convertPage(PageResult<File> page);

}
