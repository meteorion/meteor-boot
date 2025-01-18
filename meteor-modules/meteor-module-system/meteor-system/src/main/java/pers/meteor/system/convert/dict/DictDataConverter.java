package pers.meteor.system.convert.dict;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jdk.nashorn.internal.runtime.options.Option;
import org.mapstruct.Mapper;
import pers.meteor.system.mode.dict.entity.DictData;
import pers.meteor.system.mode.dict.form.DictDataForm;
import pers.meteor.system.mode.dict.vo.DictPageVO;

import java.util.List;

/**
 * 字典项 对象转换器
 *
 * @author Ray
 * @since 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface DictDataConverter {

    Page<DictPageVO> toPageVo(Page<DictData> page);

    DictDataForm toForm(DictData entity);

    DictData toEntity(DictDataForm formFata);

    Option<Long> toOption(DictData dictData);
    List<Option<Long>> toOption(List<DictData> dictData);
}
