package pers.meteor.system.api.token;

import pers.meteor.system.api.token.dto.form.AccessTokenCreateForm;
import pers.meteor.system.api.token.dto.vo.AccessTokenVO;

/**
 * @author meteor
 */
public interface AccessTokenManagerApi {

    /**
     * 保存token
     *
     * @param tokenCreateForm token信息
     * @return token
     */
    AccessTokenVO saveAccessToken(AccessTokenCreateForm tokenCreateForm);

    /**
     * 移除token
     *
     * @param token token
     * @return token
     */
    AccessTokenVO removeAccessToken(String token);

    /**
     * 获取token
     *
     * @param token token
     * @return token
     */
    AccessTokenVO getAccessToken(String token);
}
