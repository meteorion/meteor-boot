package pers.meteor.system.service.dept;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.meteor.common.pojo.Option;
import pers.meteor.system.model.dept.entity.Dept;
import pers.meteor.system.model.dept.form.DeptForm;
import pers.meteor.system.model.dept.query.DeptQuery;
import pers.meteor.system.model.dept.vo.DeptVO;

import java.util.List;

/**
 * 部门业务接口
 *
 * @author haoxr
 * @since 2021/8/22
 */
public interface DeptService extends IService<Dept> {
    /**
     * 部门列表
     *
     * @return 部门列表
     */
    List<DeptVO> getDeptList(DeptQuery queryParams);

    /**
     * 部门树形下拉选项
     *
     * @return 部门树形下拉选项
     */
    List<Option<Long>> listDeptOptions();

    /**
     * 新增部门
     *
     * @param formData 部门表单
     * @return 部门ID
     */
    Long saveDept(DeptForm formData);

    /**
     * 修改部门
     *
     * @param deptId  部门ID
     * @param formData 部门表单
     * @return 部门ID
     */
    Long updateDept(Long deptId, DeptForm formData);

    /**
     * 删除部门
     *
     * @param ids 部门ID，多个以英文逗号,拼接字符串
     * @return 是否成功
     */
    boolean deleteByIds(String ids);

    /**
     * 获取部门详情
     *
     * @param deptId 部门ID
     * @return 部门详情
     */
    DeptForm getDeptForm(Long deptId);
}
