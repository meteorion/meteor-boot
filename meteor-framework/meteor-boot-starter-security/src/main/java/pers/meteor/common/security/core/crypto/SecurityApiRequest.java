package pers.meteor.common.security.core.crypto;

import lombok.Data;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */

@Data
public class SecurityApiRequest {
    /**
     * 代理编号
     */
    private String agentId;
    /**
     * 请求时间戳
     */
    private long timestamp;
    /**
     * 版本号
     */
    private String version;
    /**
     * 加密数据
     */
    private String data;

}
