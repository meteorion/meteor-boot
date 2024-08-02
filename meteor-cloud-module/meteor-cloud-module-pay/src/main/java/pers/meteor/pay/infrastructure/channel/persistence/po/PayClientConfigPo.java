package pers.meteor.pay.infrastructure.channel.persistence.po;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

/**
 * @author meteor
 */
@TableName("pay_channel_client_config")
@Data
public class PayClientConfigPo {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private Long channelConfigId;
    /**
     * 支付通道id
     */
    private Long payChannelId;
    /**
     * 支付通道类型，{@link  pers.meteor.pay.domain.channel.module.enums.PayChannelEnum}
     */
    private String channelType;
    /**
     * 服务地址
     */
    private String serviceUrl;
    /**
     * 运用id
     */
    private String appId;

    /**
     * 扩展参数
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private JSONObject metadata;
}
