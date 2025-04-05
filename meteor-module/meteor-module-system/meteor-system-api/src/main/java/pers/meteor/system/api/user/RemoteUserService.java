package pers.meteor.system.api.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import pers.meteor.common.constant.RpcConstants;
import pers.meteor.system.api.user.vo.AdminUserVO;
import pers.meteor.common.constant.ServiceNameConstants;
import pers.meteor.common.entity.R;

/**
 * @author meteor
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteUserService {
    String PREFIX = RpcConstants.RPC_API_PREFIX + "/system/user";

    @PostMapping(PREFIX + "/getUserByUsername")
    R<AdminUserVO> getUserByUsername(String username);
}
