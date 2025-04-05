package pers.meteor.log.event;

import org.springframework.context.ApplicationEvent;
import pers.meteor.system.api.log.vo.SysLog;

/**
 * @author lengleng 系统日志事件
 */
public class SysLogEvent extends ApplicationEvent {

	public SysLogEvent(SysLog source) {
		super(source);
	}

}
