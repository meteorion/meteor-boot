package pers.meteor.common.exception;

import lombok.NoArgsConstructor;

/**
 * @author lengleng
 * @date 2018年06月22日16:22:03 403 授权拒绝
 */
@NoArgsConstructor
public class MeteorDeniedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MeteorDeniedException(String message) {
		super(message);
	}

	public MeteorDeniedException(Throwable cause) {
		super(cause);
	}

	public MeteorDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public MeteorDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
