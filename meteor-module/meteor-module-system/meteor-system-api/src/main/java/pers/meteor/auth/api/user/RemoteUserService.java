package pers.meteor.auth.api.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import pers.meteor.auth.api.user.vo.AdminUserVO;
import pers.meteor.common.constant.ServiceNameConstants;
import pers.meteor.common.entity.R;

/**
 * @author meteor
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteUserService {

    @PostMapping("/user/getUserByUsername")
    R<AdminUserVO> getUserByUsername(String username);
}
