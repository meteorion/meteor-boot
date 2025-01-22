package pers.meteor.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.meteor.auth.model.captcha.form.CaptchaRequestForm;
import pers.meteor.auth.model.captcha.form.CaptchaSmsSendForm;
import pers.meteor.auth.model.captcha.form.CaptchaSmsValidateForm;
import pers.meteor.auth.model.captcha.form.CaptchaValidateForm;
import pers.meteor.auth.model.captcha.vo.CaptchaResponse;
import pers.meteor.auth.service.CaptchaService;
import pers.meteor.common.pojo.response.Response;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.security.core.utils.SecurityUtils;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author meteor
 */
@Tag(name = "验证码")
@RestController
@RequestMapping("/auth")
@Validated
@Slf4j
public class CaptchaController {
    @Resource
    private CaptchaService captchaService;

    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public SingleResponse<CaptchaResponse> getCaptcha(@Valid CaptchaRequestForm requestForm) {
        return SingleResponse.success(captchaService.getCaptcha(requestForm));
    }

    @Operation(summary = "校验验证码")
    @PostMapping("/check-captcha")
    public Response checkCaptcha(@RequestBody @Valid CaptchaValidateForm validateForm) {
        boolean validateCaptcha = captchaService.validateCaptcha(validateForm);
        return validateCaptcha ? Response.success() : Response.error("验证码错误");
    }

    @PostMapping("/send-sms-code")
    @Operation(summary = "发送手机验证码")
    public SingleResponse<Boolean> sendSmsCode(@RequestBody @Valid CaptchaSmsSendForm smsSendForm) {
        Long userId = SecurityUtils.getLoginUserId();
        captchaService.sendSmsCode(userId, smsSendForm);
        return SingleResponse.success(true);
    }

    @PostMapping("/validate-sms-code")
    @Operation(summary = "校验手机验证码")
    public SingleResponse<Boolean> validateSmsCode(@RequestBody @Valid CaptchaSmsValidateForm smsValidateForm) {
        Long userId =  SecurityUtils.getLoginUserId();
        captchaService.validateSmsCode(userId, smsValidateForm);
        return SingleResponse.success(true);
    }
}
