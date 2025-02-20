package pers.meteor.system.api.oauth2;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.meteor.system.api.oauth2.dto.OauthClientDetailsDTO;
import pers.meteor.common.constant.ServiceNameConstants;
import pers.meteor.common.entity.R;

/**
 * @author lengleng
 * @date 2020/12/05
 */
@FeignClient(contextId = "remoteClientDetailsService", value = ServiceNameConstants.SYSTEM_SERVICE)
public interface RemoteClientDetailsService {

	/**
	 * 通过clientId 查询客户端信息 (未登录，需要无token 内部调用)
	 * @param clientId 用户名
	 * @return R
	 */
	@GetMapping("/client/getClientDetailsById/{clientId}")
	R<OauthClientDetailsDTO> getClientDetailsById(@PathVariable("clientId") String clientId);

}
