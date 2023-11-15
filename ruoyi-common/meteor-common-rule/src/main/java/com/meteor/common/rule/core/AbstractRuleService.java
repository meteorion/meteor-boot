package com.meteor.common.rule.core;

import com.meteor.common.rule.api.*;
import com.meteor.common.rule.exception.RuleException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@Slf4j
@Data
public abstract class AbstractRuleService<T extends BasicRule> implements RuleService {
    private static AtomicBoolean shouldRefreshConfig = new AtomicBoolean(true);
    protected final ConcurrentHashMap<String, RuleGroup<T>> ruleContainer = new ConcurrentHashMap<>();
    /**
     * 配置id
     */
    protected final Integer id;
    private String version;
    private RuleLoader<T> ruleLoader;

    public AbstractRuleService(Integer id, RuleLoader<T> ruleLoader) {
        this.id = id;
        this.ruleLoader = ruleLoader;
        this.addRuleGroups(this.ruleLoader.load());
        this.version = ruleLoader.getVersion();
    }

    protected final void init() {
        doInit();
        log.info("[init][配置初始化完成]");
    }

    protected final void refreshRule() {
        if (Double.valueOf(this.version).compareTo(Double.valueOf(this.ruleLoader.getVersion())) > 0) {
            log.error("新版本低于当前版本, 不进行加载: {} | {}", this.version, this.ruleLoader.getVersion());
            return;
        }
        this.clear();
        this.addRuleGroups(this.ruleLoader.load());
        this.version = this.ruleLoader.getVersion();
        shouldRefreshConfig.set(true);
        log.info("规则配配置刷新完成");
    }

    protected abstract void doInit();

    @Override
    public Integer getId() {
        return id;
    }

    public void addRuleGroup(RuleGroup<T> ruleGroup) {
        if (this.ruleContainer.containsKey(ruleGroup.getGroupName())) {
            throw new RuleException("规则组名已存在:" + ruleGroup.getGroupName());
        }
        this.ruleContainer.put(ruleGroup.getGroupName(), ruleGroup);
    }

    public void addRuleGroups(Set<RuleGroup<T>> ruleGroups) {
        for (RuleGroup<T> ruleGroup : ruleGroups) {
            this.addRuleGroup(ruleGroup);
        }
    }

    public void clear() {
        this.ruleContainer.clear();
    }

    @Override
    public Map<String, Object> run(RuleFact ruleFact) {
        // 检查规则版本是否变更
        if (versionIsChanged() && shouldRefreshConfig.getAndSet(false)) {
            log.info("规则版本已变更，重新加载配置");
            this.refreshRule();
        }
        log.info("开始执行规则：{} | {}", ruleFact.getGroupName(), this.version);
        RuleGroup<T> ruleGroup = this.ruleContainer.get(ruleFact.getGroupName());
        if (ruleGroup == null) {
            throw new RuleException("规则组未配置：" + ruleFact.getGroupName());
        }
        if (!ruleGroup.isEnabled()) {
            throw new RuleException("规则组已关闭");
        }

        List<T> triggerRules = ruleGroup.getTriggerRules(ruleFact.getFacts());

        // 执行规则
        return this.run(triggerRules, ruleFact.getFacts());
    }

    /**
     * 执行规则
     *
     * @param rules 规则组
     * @param facts 规则参数
     * @return /
     */
    protected abstract Map<String, Object> run(List<T> rules, HashMap<String, Object> facts);
    
    private boolean versionIsChanged() {
        return !this.version.equals(this.ruleLoader.getVersion());
    }
}
