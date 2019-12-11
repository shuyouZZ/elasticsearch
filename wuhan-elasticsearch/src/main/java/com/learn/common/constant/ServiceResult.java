package com.learn.common.constant;

import com.learn.common.ResultCode;
import org.springframework.http.HttpStatus;

import java.util.Objects;

/**
 * @date 2019/8/19 9:50
 * @author dshuyou
 */
public class ServiceResult<T> implements ResultCode {
	private int code;
	private String message;
	private T result;

	public ServiceResult(){
	}

	public ServiceResult(int code) {
		this.code = code;
	}

	public ServiceResult(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ServiceResult(int code, String message, T result) {
		this.code = code;
		this.message = message;
		this.result = result;
	}

	public static ServiceResult success() {
		return new ServiceResult<>(HttpStatus.OK.value());
	}

	public static <T> ServiceResult success(T result) {
		return new ServiceResult(HttpStatus.OK.value(),HttpStatus.OK.getReasonPhrase(),result);
	}

	public static <T> ServiceResult successOf( String message, T result) {
		return new ServiceResult(HttpStatus.OK.value(),message,result);
	}

	public static ServiceResult notFound() {
		return new ServiceResult(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
	}

	public static ServiceResult notContent() {
		return new ServiceResult(HttpStatus.NO_CONTENT.value(),HttpStatus.NO_CONTENT.getReasonPhrase());
	}

	public static <T> ServiceResult paramsError(T params) {
		return new ServiceResult(HttpStatus.BAD_REQUEST.value(), "Parameter Error",params);
	}

	public static ServiceResult isExist() {
		return new ServiceResult(HttpStatus.CONFLICT.value(), "Index Is Existed");
	}

	public static ServiceResult internalServerError() {
		return new ServiceResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	}

	public static ServiceResult unAuthorized() {
		return new ServiceResult(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
	}

	public static ServiceResult requestTimeout() {
		return new ServiceResult(HttpStatus.REQUEST_TIMEOUT.value(), HttpStatus.REQUEST_TIMEOUT.getReasonPhrase());
	}

	public static ServiceResult exceptionFailed() {
		return new ServiceResult(HttpStatus.EXPECTATION_FAILED.value(), HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ServiceResult)) {
			return false;
		}
		ServiceResult<?> that = (ServiceResult<?>) o;
		return code == that.code &&
				Objects.equals(message, that.message) &&
				Objects.equals(result, that.result);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, message, result);
	}

	@Override
	public int code() {
		return code;
	}

	@Override
	public String message() {
		return message;
	}

	@Override
	public Object result() {
		return result;
	}
}
