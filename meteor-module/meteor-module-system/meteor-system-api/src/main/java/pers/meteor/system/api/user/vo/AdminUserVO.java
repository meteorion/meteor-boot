package pers.meteor.system.api.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author meteor
 */
@Data
@Schema(description = "用户信息")
public class AdminUserVO {
    @Schema(description = "主键id")
    private Long userId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 锁定标记
     */
    @Schema(description = "锁定标记")
    private String lockFlag;

    /**
     * 手机号
     */
    @Schema(description = "手机号")
    private String phone;

    /**
     * 头像
     */
    @Schema(description = "头像地址")
    private String avatar;

    /**
     * 部门ID
     */
    @Schema(description = "用户所属部门id")
    private Long deptId;

    /**
     * 权限标识集合
     */
    @Schema(description = "权限标识集合")
    private String[] permissions;

    /**
     * 角色集合
     */
    @Schema(description = "角色标识集合")
    private Long[] roles;
}
