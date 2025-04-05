package pers.meteor.system.api.log;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.meteor.common.constant.RpcConstants;
import pers.meteor.system.api.log.vo.SysLog;
import pers.meteor.common.constant.ServiceNameConstants;
import pers.meteor.common.entity.R;
import pres.meteor.feign.annotation.NoToken;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteLogService {
    String PREFIX = RpcConstants.RPC_API_PREFIX + "/system/log";

    /**
     * 保存日志 (异步多线程调用，无token)
     * @param sysLog 日志实体
     * @return succes、false
     */
    @PostMapping(PREFIX + "/save")
    R<Boolean> saveLog(@RequestBody SysLog sysLog);
}
