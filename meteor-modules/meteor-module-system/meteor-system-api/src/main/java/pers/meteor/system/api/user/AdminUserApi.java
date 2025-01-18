package pers.meteor.system.api.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.meteor.common.constant.ApiConstants;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.system.api.user.vo.AdminUserVO;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 参数配置")
public interface AdminUserApi {

    String PREFIX = ApiConstants.PREFIX + "/user";

    @GetMapping(PREFIX + "/get")
    @Operation(summary = "通过用户手机号查询用户")
    @Parameter(name = "mobile", description = "手机号", example = "1", required = true)
    SingleResponse<AdminUserVO> getUserByMobile(@RequestParam("mobile") String mobile);

    @PostMapping(PREFIX + "/isPasswordMatch")
    @Operation(summary = "判断密码是否正确")
    @Parameter(name = "mobile", description = "手机号", example = "1", required = true)
    @Parameter(name = "password", description = "密码", example = "123344", required = true)
    SingleResponse<Boolean> isPasswordMatch(@RequestParam("mobile") String mobile, @RequestParam("password") String password);
}
