package pers.meteor.system.controller.admin.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.meteor.common.pojo.Option;
import pers.meteor.common.pojo.response.PageResponse;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.security.core.utils.SecurityUtils;
import pers.meteor.system.model.user.dto.UserExportDTO;
import pers.meteor.system.model.user.enums.ContactType;
import pers.meteor.system.model.user.form.*;
import pers.meteor.system.model.user.query.UserPageQuery;
import pers.meteor.system.model.user.vo.UserInfoVO;
import pers.meteor.system.model.user.vo.UserPageVO;
import pers.meteor.system.model.user.vo.UserProfileVO;
import pers.meteor.system.service.user.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 用户控制层
 *
 * @author Ray
 * @since 2022/10/16
 */
@Tag(name = "02.用户接口")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "用户分页列表")
    @GetMapping("/page")
    public PageResponse<UserPageVO> getUserPage(@Valid UserPageQuery queryParams) {
        IPage<UserPageVO> result = userService.getUserPage(queryParams);
        return PageResponse.success(result.getRecords(), result.getTotal(), result.getSize(), result.getCurrent());
    }

    @Operation(summary = "新增用户")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('sys:user:add')")
    public SingleResponse<Long> saveUser(@RequestBody @Valid UserForm userForm) {
        return SingleResponse.success(userService.saveUser(userForm));
    }

    @Operation(summary = "用户表单数据")
    @GetMapping("/{userId}/form")
    @Parameters({
            @Parameter(name = "userId", description = "用户ID")
    })
    public SingleResponse<UserForm> getUserForm(@PathVariable Long userId
    ) {UserForm formData = userService.getUserFormData(userId);
        return SingleResponse.success(formData);
    }

    @Operation(summary = "修改用户")
    @PutMapping(value = "/{userId}")
    @PreAuthorize("@ss.hasPermission('sys:user:edit')")
    @Parameters({
            @Parameter(name = "userId", description = "用户ID")
    })
    public SingleResponse<Void> updateUser(@PathVariable Long userId, @RequestBody @Valid UserForm userForm) {
        userService.updateUser(userId, userForm);
        return SingleResponse.success();
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPermission('sys:user:delete')")
    @Parameters({
            @Parameter(name = "ids", description = "用户ID，多个以英文逗号(,)分割")
    })
    public SingleResponse<Void> deleteUsers(@PathVariable String ids) {
        userService.deleteUsers(ids);
        return SingleResponse.success();
    }

    @Operation(summary = "修改用户状态")
    @PatchMapping(value = "/update-status")
    public SingleResponse<Void> updateUserStatus(@RequestBody UserUpdateStatusForm form) {
        userService.updateUserStatus(form.getId(), form.getStatus());
        return SingleResponse.success();
    }

    @Operation(summary = "获取当前登录用户信息")
    @GetMapping("/me")
    public SingleResponse<UserInfoVO> getCurrentUserInfo() {
        UserInfoVO userInfoVO = userService.getCurrentUserInfo();
        return SingleResponse.success(userInfoVO);
    }

    @Operation(summary = "用户导入模板下载")
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        String fileName = "用户导入模板.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));

        String fileClassPath = "templates" + File.separator + "excel" + File.separator + fileName;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileClassPath);

//        ServletOutputStream outputStream = response.getOutputStream();
//        ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();
//
//        excelWriter.finish();
    }

    @Operation(summary = "导入用户")
    @PostMapping("/import")
    public SingleResponse<String> importUsers(MultipartFile file) throws IOException {
//        UserImportListener listener = new UserImportListener();
//        String msg = ExcelUtils.importExcel(file.getInputStream(), UserImportDTO.class, listener);
        return SingleResponse.success("msg");
    }

    @Operation(summary = "导出用户")
    @GetMapping("/export")
    public void exportUsers(UserPageQuery queryParams, HttpServletResponse response) throws IOException {
        String fileName = "用户列表.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));

        List<UserExportDTO> exportUserList = userService.listExportUsers(queryParams);
//        EasyExcel.write(response.getOutputStream(), UserExportDTO.class).sheet("用户列表")
//                .doWrite(exportUserList);
    }

    @Operation(summary = "获取个人中心用户信息")
    @GetMapping("/profile")
    public SingleResponse<UserProfileVO> getUserProfile() {
        Long userId = SecurityUtils.getLoginUserId();
        UserProfileVO userProfile = userService.getUserProfile(userId);
        return SingleResponse.success(userProfile);
    }

    @Operation(summary = "个人中心修改用户信息")
    @PutMapping("/profile")
    public SingleResponse<Void> updateUserProfile(@RequestBody UserProfileForm formData) {
        userService.updateUserProfile(SecurityUtils.getLoginUserId(), formData);
        return SingleResponse.success();
    }

    @Operation(summary = "重置用户密码")
    @PutMapping(value = "/{userId}/password/reset")
    @PreAuthorize("@ss.hasPermission('sys:user:password:reset')")
    @Parameters({
            @Parameter(name = "userId", description = "用户ID"),
            @Parameter(name = "password", description = "密码")
    })
    public SingleResponse<?> resetPassword(@PathVariable Long userId, @RequestParam String password) {
        userService.resetPassword(userId, password);
        return SingleResponse.success();
    }

    @Operation(summary = "修改密码")
    @PutMapping(value = "/password")
    public SingleResponse<?> changePassword(@RequestBody PasswordChangeForm data) {
        Long currUserId = SecurityUtils.getLoginUserId();
        userService.changePassword(currUserId, data);
        return SingleResponse.success();
    }

    @Operation(summary = "发送短信/邮箱验证码")
    @PostMapping(value = "/send-verification-code")
    @Parameters({
            @Parameter(name = "contact", description = "联系方式（手机号码或邮箱地址"),
            @Parameter(name = "contactType", description = "联系方式类型（Mobile或Email）")
    })
    public SingleResponse<?> sendVerificationCode(@RequestParam String contact, @RequestParam ContactType contactType) {
        boolean result = userService.sendVerificationCode(contact, contactType);
        return SingleResponse.success(result);
    }

    @Operation(summary = "个人中心绑定用户手机号")
    @PutMapping(value = "/mobile")
    public SingleResponse<?> bindMobile(@RequestBody @Validated MobileBindingForm data) {
        userService.updateUserMobile(SecurityUtils.getLoginUserId(), data.getMobile());
        return SingleResponse.success();
    }

    @Operation(summary = "个人中心绑定用户邮箱")
    @PutMapping(value = "/email")
    public SingleResponse<?> bindEmail(@RequestBody @Validated EmailBindingForm data) {
        userService.updateUserEmail(SecurityUtils.getLoginUserId(), data.getEmail());
        return SingleResponse.success();
    }

    @Operation(summary = "用户下拉选项")
    @GetMapping("/options")
    public SingleResponse<List<Option<String>>> listUserOptions() {
        List<Option<String>> list = userService.listUserOptions();
        return SingleResponse.success(list);
    }
}
