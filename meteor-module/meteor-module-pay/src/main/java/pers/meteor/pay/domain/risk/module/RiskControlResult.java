package pers.meteor.pay.domain.risk.module;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author meteor
 */
@Getter
@RequiredArgsConstructor
public class RiskControlResult {
    private final String ruleType;
    private final String value;
    private final String message;
    private final boolean pass;

    public static RiskControlResult pass(String ruleType) {
        return new RiskControlResult(ruleType, null, null, true);
    }

    public static RiskControlResult pass(String ruleType, String data) {
        return new RiskControlResult(ruleType, data, null, true);
    }

    public static RiskControlResult fail(String ruleType, String message) {
        return new RiskControlResult(ruleType, null, message, false);
    }
}
