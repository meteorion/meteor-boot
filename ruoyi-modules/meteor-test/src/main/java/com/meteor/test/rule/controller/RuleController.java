package com.meteor.test.rule.controller;

import com.meteor.test.rule.service.MeteorRuleService;
import com.ruoyi.common.core.domain.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
@RestController
@RequiredArgsConstructor
public class RuleController {

    private final MeteorRuleService ruleService;

    @PostMapping("fire")
    public R<Object> fire(@RequestBody Map<String, Object> params) {
        Object result = ruleService.fire(params);
        return R.ok(result);
    }
}
