package pers.meteor.system.api.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import pers.meteor.system.api.oauth2.dto.OauthClientDetailsDTO;
import pers.meteor.common.entity.R;

/**
 * @author lengleng
 * @date 2020/12/05
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class RemoteClientDetailsServiceImpl implements RemoteClientDetailsService {

	@Override
	public R<OauthClientDetailsDTO> getClientDetailsById(String clientId) {
		return R.ok(new OauthClientDetailsDTO());
	}
}
