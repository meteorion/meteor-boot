/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pers.meteor.common.entity;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import pers.meteor.common.exception.ErrorCode;
import pers.meteor.common.exception.GlobalErrorCode;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T>
 * @author lengleng
 */
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
public class R<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code;

	private String msg;

	private T data;

	public static <T> R<T> ok() {
		return restResult(null, GlobalErrorCode.SUCCESS.getCode(), null);
	}

	public static <T> R<T> ok(T data) {
		return restResult(data, GlobalErrorCode.SUCCESS.getCode(), null);
	}

	public static <T> R<T> ok(T data, String msg) {
		return restResult(data, GlobalErrorCode.SUCCESS.getCode(), msg);
	}

	public static <T> R<T> failed() {
		return restResult(null, GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode(), null);
	}

	public static <T> R<T> failed(String msg) {
		return restResult(null, GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode(), msg);
	}

	public static <T> R<T> failed(T data) {
		return restResult(data, GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode(), null);
	}

	public static <T> R<T> failed(T data, String msg) {
		return restResult(data, GlobalErrorCode.INTERNAL_SERVER_ERROR.getCode(), msg);
	}

	public static <T> R<T> restResult(T data, int code, String msg) {
		R<T> apiResult = new R<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}

	public static <T> R<T> restResult(T data, ErrorCode errorCode) {
		R<T> apiResult = new R<>();
		apiResult.setCode(errorCode.getCode());
		apiResult.setData(data);
		apiResult.setMsg(errorCode.getMsg());
		return apiResult;
	}

}
