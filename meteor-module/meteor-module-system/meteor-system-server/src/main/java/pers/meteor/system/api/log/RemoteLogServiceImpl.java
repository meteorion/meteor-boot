package pers.meteor.system.api.log;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import pers.meteor.system.api.log.vo.SysLog;
import pers.meteor.common.entity.R;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class RemoteLogServiceImpl implements RemoteLogService {

    @Override
    public R<Boolean> saveLog(SysLog sysLog) {
        return R.ok();
    }
}
