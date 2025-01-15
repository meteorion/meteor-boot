package pers.meteor.auth.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pers.meteor.auth.api.dto.AccessToken;
import pers.meteor.auth.api.dto.AuthUserDetail;
import pers.meteor.auth.enums.ApiConstants;
import pers.meteor.common.pojo.response.SingleResponse;

import javax.validation.Valid;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@FeignClient(name = ApiConstants.NAME)
@Tag(name = "RPC 服务 - 令牌")
public interface AuthTokenApi {

    String PREFIX = ApiConstants.PREFIX + "/auth/token";

    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建访问令牌")
    SingleResponse<AccessToken> createAccessToken(@Valid @RequestBody AuthUserDetail userDetail);

    @PutMapping(PREFIX + "/refresh")
    @Operation(summary = "刷新访问令牌")
    @Parameters({
            @Parameter(name = "refreshToken", description = "刷新令牌", required = true, example = "haha"),
            @Parameter(name = "clientId", description = "客户端编号", required = true, example = "yudaoyuanma")
    })
    SingleResponse<AccessToken> refreshAccessToken(@RequestParam("refreshToken") String refreshToken,
                                                   @RequestParam("clientId") String clientId);

    @GetMapping(PREFIX + "/check")
    @Operation(summary = "校验访问令牌")
    @Parameter(name = "accessToken", description = "访问令牌", required = true, example = "tudou")
    SingleResponse<AccessToken> checkAccessToken(@RequestParam("accessToken") String accessToken);

    @DeleteMapping(PREFIX + "/remove")
    @Operation(summary = "移除访问令牌")
    @Parameter(name = "accessToken", description = "访问令牌", required = true, example = "tudou")
    SingleResponse<AccessToken> removeAccessToken(@RequestParam("accessToken") String accessToken);
}
