package pers.meteor.common.rule.api;

/**
 * 规则执行模式
 *
 * @author 钟宗兵
 * @since 1.0.0
 */
public enum TriggerMode {
    /**
     * 单个执行，根据执行顺序，成功触发执行一个规则后执行结束
     */
    SINGLE,
    /**
     * 全部执行，执行全部规则
     */
    ALL,
    /**
     * 链式执行，根据执行顺序，遇到不满足条件后的规则停止执行
     */
    CHAIN
}
