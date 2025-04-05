package pers.meteor.system.api.log.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author lengleng
 * @since 2017-11-20
 */
@Data
@Schema(description = "日志")
public class SysLog implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 日志类型
	 */
	@NotBlank(message = "日志类型不能为空")
	@Schema(description = "日志类型")
	private String logType;

	/**
	 * 日志标题
	 */
	@NotBlank(message = "日志标题不能为空")
	@Schema(description = "日志标题")
	private String title;

	/**
	 * 创建者
	 */
	@Schema(description = "创建人")
	private String createBy;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 操作IP地址
	 */
	@Schema(description = "操作ip地址")
	private String remoteAddr;

	/**
	 * 用户代理
	 */
	@Schema(description = "用户代理")
	private String userAgent;

	/**
	 * 请求URI
	 */
	@Schema(description = "请求uri")
	private String requestUri;

	/**
	 * 操作方式
	 */
	@Schema(description = "操作方式")
	private String method;

	/**
	 * 操作提交的数据
	 */
	@Schema(description = "提交数据")
	private String params;

	/**
	 * 执行时间
	 */
	@Schema(description = "方法执行时间")
	private Long time;

	/**
	 * 异常信息
	 */
	@Schema(description = "异常信息")
	private String exception;

	/**
	 * 服务ID
	 */
	@Schema(description = "应用标识")
	private String serviceId;

}
