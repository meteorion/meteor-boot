package pers.meteor.pay.domain.channel.module.valueobject;

import lombok.Data;
import pers.meteor.common.core.exception.ServiceException;
import pers.meteor.pay.domain.channel.module.enums.PayChannelEnum;

import java.math.BigDecimal;

/**
 * 通道费率
 *
 * @author meteor
 */
@Data
public class ChannelRate {
    /**
     * 费率类型
     */
    private PayChannelEnum payChannel;
    /**
     * 成本费率（我方）
     */
    private BigDecimal costRate;
    /**
     * 成本手续费（我方）
     */
    private int costFee;
    /**
     * 最大费率(通道)
     */
    private BigDecimal maxRate;
    /**
     * 最低费率(通道)
     */
    private BigDecimal minRate;
    /**
     * 最大手续费(通道)
     */
    private int maxFee;
    /**
     * 最低手续费(通道)
     */
    private int minFee;

    /**
     * 是否有效费率
     *
     * @param rate /
     * @return /
     */
    public boolean isEffectiveRate(BigDecimal rate) {
        return (minRate != null && minRate.compareTo(rate) <= 0) && (maxRate == null || maxRate.compareTo(rate) >= 0);
    }

    /**
     * 是否有效费率范围
     *
     * @param minRate 最小费率
     * @param maxRate 最大费率
     * @return /
     */
    public boolean isEffectiveRateRange(BigDecimal minRate, BigDecimal maxRate) {
        if (minRate == null || maxRate == null) {
            return false;
        }
        return isEffectiveRate(minRate) && isEffectiveRate(maxRate);
    }

    /**
     * 是否有效手续费
     *
     * @param fee /
     * @return /
     */
    public boolean isEffectiveFee(int fee) {
        return minFee <= fee && maxFee >= fee;
    }

    /**
     * 是否有效手续费范围
     *
     * @param minFee 最小手续费
     * @param maxFee 最低手续费
     * @return /
     */
    public boolean isEffectiveFeeRange(int minFee, int maxFee) {
        return isEffectiveFee(minFee) && isEffectiveFee(maxFee);
    }

    /**
     * 检查费率配置是否有效
     *
     * @param channelRate 费率值
     */
    public void checkRateConfig(ChannelRate channelRate) {
        // 检查成本费率
        boolean isEffectiveRate = this.isEffectiveRate(channelRate.getCostRate());
        if (!isEffectiveRate) {
            throw new ServiceException("{0}费率不在有效范围内：{1}-{2}", payChannel.name(), this.getMinRate(), this.getMaxRate());
        }
        // 检查成本费率范围
        boolean isEffectiveRateRange = this.isEffectiveRateRange(channelRate.getMinRate(), channelRate.getMaxRate());
        if (!isEffectiveRateRange) {
            throw new ServiceException("{0}费率范围不在有效范围内：{1}-{2}", payChannel.name(), this.getMinRate(), this.getMaxRate());
        }
        // 检查成本手续费
        boolean isEffectiveFee = this.isEffectiveFee(channelRate.getCostFee());
        if (!isEffectiveFee) {
            throw new ServiceException("{0}手续费不在有效范围内：{1}-{2}", payChannel.name(), this.getMinFee(), this.getMaxFee());
        }
        // 检查成本手续费范围
        boolean isEffectiveFeeRange = this.isEffectiveFeeRange(channelRate.getMinFee(), channelRate.getMaxFee());
        if (!isEffectiveFeeRange) {
            throw new ServiceException("{0}手续费范围不在有效范围内：{1}-{2}", payChannel.name(), this.getMinFee(), this.getMaxFee());
        }
    }
}
