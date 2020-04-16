package com.learn.elasticsearch.exception;

/**
 * @date 2019/12/4 9:50
 * @author dshuyou
 */
public enum ExceptionEnum {

    OK(200, "OK"),
    NO_CONTENT(204, "No Content"),
    MONITOR_EXCEPTION(400,"Invalid Parameters"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    REQUEST_TIMEOUT(408, "Request Timeout"),
    CONFLICT(409, "Conflict"),
    EXPECTATION_FAILED(417, "Expectation Failed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    public int code;
    public String message;

    ExceptionEnum(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }

}