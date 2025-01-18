package pers.meteor.system.convert.dept;

import org.mapstruct.Mapper;
import pers.meteor.system.model.dept.entity.Dept;
import pers.meteor.system.model.dept.form.DeptForm;
import pers.meteor.system.model.dept.vo.DeptVO;

/**
 * 部门对象转换器
 *
 * @author haoxr
 * @since 2022/7/29
 */
@Mapper(componentModel = "spring")
public interface DeptConverter {

    DeptForm toForm(Dept entity);

    DeptVO toVo(Dept entity);

    Dept toEntity(DeptForm deptForm);

}
