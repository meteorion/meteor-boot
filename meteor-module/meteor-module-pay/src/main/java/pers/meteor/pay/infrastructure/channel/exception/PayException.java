package pers.meteor.pay.infrastructure.channel.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author meteor
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayException extends RuntimeException {

    public PayException(Throwable throwable) {
        super(throwable);
    }
}
