package pers.meteor.system.convert.dict;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import pers.meteor.system.model.dict.entity.Dict;
import pers.meteor.system.model.dict.form.DictForm;
import pers.meteor.system.model.dict.vo.DictPageVO;

/**
 * 字典 对象转换器
 *
 * @author Ray Hao
 * @since 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface DictConverter {

    Page<DictPageVO> toPageVo(Page<Dict> page);

    DictForm toForm(Dict entity);

    Dict toEntity(DictForm entity);
}
