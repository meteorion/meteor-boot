package pers.meteor.event.core.entity;

/**
 * @author meteor
 */
public interface TenantDomainEvent extends DomainEvent {
    /**
     * 获取多租户id
     *
     * @return /
     */
    default String getTenantId() {
        return "";
    }
}
