package pers.meteor.system.convert.user;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pers.meteor.system.model.user.bo.UserBO;
import pers.meteor.system.model.user.dto.UserImportDTO;
import pers.meteor.system.model.user.entity.User;
import pers.meteor.system.model.user.form.UserForm;
import pers.meteor.system.model.user.form.UserProfileForm;
import pers.meteor.system.model.user.vo.UserInfoVO;
import pers.meteor.system.model.user.vo.UserPageVO;
import pers.meteor.system.model.user.vo.UserProfileVO;

/**
 * 用户对象转换器
 *
 * @author haoxr
 * @since 2022/6/8
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    UserPageVO toPageVo(UserBO bo);

    Page<UserPageVO> toPageVo(Page<UserBO> bo);

    UserForm toForm(User entity);

    @InheritInverseConfiguration(name = "toForm")
    User toEntity(UserForm entity);

    @Mappings({
            @Mapping(target = "userId", source = "id")
    })
    UserInfoVO toUserInfoVo(User entity);

    User toEntity(UserImportDTO vo);


    UserProfileVO toProfileVO(UserBO bo);

    User toEntity(UserProfileForm formData);
}
