package pers.meteor.log.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.meteor.system.api.log.vo.SysLog;

/**
 * spring event log
 *
 * @author lengleng
 * @date 2023/8/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysLogEventSource extends SysLog {

	/**
	 * 参数重写成object
	 */
	private Object body;

}
