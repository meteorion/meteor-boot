package pers.meteor.system.controller.admin.notice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.meteor.common.pojo.response.PageResponse;
import pers.meteor.common.pojo.response.SingleResponse;
import pers.meteor.system.model.notice.form.NoticeForm;
import pers.meteor.system.model.notice.query.NoticePageQuery;
import pers.meteor.system.model.notice.vo.NoticeDetailVO;
import pers.meteor.system.model.notice.vo.NoticePageVO;
import pers.meteor.system.model.notice.vo.UserNoticePageVO;
import pers.meteor.system.service.notice.NoticeService;
import pers.meteor.system.service.notice.UserNoticeService;

import javax.validation.Valid;


/**
 * 通知公告前端控制层
 *
 * @author youlaitech
 * @since 2024-08-27 10:31
 */
@Tag(name = "12.通知公告接口")
@RestController
@RequestMapping("/system/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    private final UserNoticeService userNoticeService;

    @Operation(summary = "通知公告分页列表")
    @GetMapping("/page")
    @PreAuthorize("@ss.hasPermission('sys:notice:query')")
    public PageResponse<NoticePageVO> getNoticePage(NoticePageQuery queryParams) {
        IPage<NoticePageVO> result = noticeService.getNoticePage(queryParams);
        return PageResponse.success(result.getRecords(), result.getTotal(), result.getSize(), result.getCurrent());
    }

    @Operation(summary = "新增通知公告")
    @PostMapping
    @PreAuthorize("@ss.hasPermission('sys:notice:add')")
    public SingleResponse<?> saveNotice(@RequestBody @Valid NoticeForm formData) {
        boolean result = noticeService.saveNotice(formData);
        return SingleResponse.success(result);
    }

    @Operation(summary = "获取通知公告表单数据")
    @GetMapping("/{id}/form")
    @PreAuthorize("@ss.hasPermission('sys:notice:edit')")
    @Parameters({
            @Parameter(name = "id", description = "通知公告ID")
    })
    public SingleResponse<NoticeForm> getNoticeForm(@PathVariable Long id) {
        NoticeForm formData = noticeService.getNoticeFormData(id);
        return SingleResponse.success(formData);
    }

    @Operation(summary = "阅读获取通知公告详情")
    @GetMapping("/{id}/detail")
    @Parameters({
            @Parameter(name = "id", description = "通知公告ID")
    })
    public SingleResponse<NoticeDetailVO> getNoticeDetail(@PathVariable Long id) {
        NoticeDetailVO detailVO = noticeService.getNoticeDetail(id);
        return SingleResponse.success(detailVO);
    }

    @Operation(summary = "修改通知公告")
    @PutMapping(value = "/{id}")
    @PreAuthorize("@ss.hasPermission('sys:notice:edit')")
    @Parameters({
            @Parameter(name = "id", description = "通知公告ID")
    })
    public SingleResponse<Boolean> updateNotice(@PathVariable Long id, @RequestBody @Validated NoticeForm formData) {
        boolean result = noticeService.updateNotice(id, formData);
        return SingleResponse.success(result);
    }

    @Operation(summary = "发布通知公告")
    @PutMapping("/{id}/publish")
    @PreAuthorize("@ss.hasPermission('sys:notice:publish')")
    public SingleResponse<Boolean> publishNotice(@Parameter(description = "通知公告ID") @PathVariable Long id) {
        boolean result = noticeService.publishNotice(id);
        return SingleResponse.success(result);
    }

    @Operation(summary = "撤回通知公告")
    @PutMapping("/{id}/revoke")
    @PreAuthorize("@ss.hasPermission('sys:notice:revoke')")
    public SingleResponse<Boolean> revokeNotice(@Parameter(description = "通知公告ID") @PathVariable Long id) {
        boolean result = noticeService.revokeNotice(id);
        return SingleResponse.success(result);
    }

    @Operation(summary = "删除通知公告")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPermission('sys:notice:delete')")
    public SingleResponse<Boolean> deleteNotices(@Parameter(description = "通知公告ID，多个以英文逗号(,)分割") @PathVariable String ids) {
        boolean result = noticeService.deleteNotices(ids);
        return SingleResponse.success(result);
    }

    @Operation(summary = "全部已读")
    @PutMapping("/read-all")
    public SingleResponse<Void> readAll() {
        userNoticeService.readAll();
        return SingleResponse.success();
    }

    @Operation(summary = "获取我的通知公告分页列表")
    @GetMapping("/my-page")
    public PageResponse<UserNoticePageVO> getMyNoticePage(NoticePageQuery queryParams) {
        IPage<UserNoticePageVO> result = noticeService.getMyNoticePage(queryParams);
        return PageResponse.success(result.getRecords(), result.getTotal(), result.getSize(), result.getCurrent());
    }
}
