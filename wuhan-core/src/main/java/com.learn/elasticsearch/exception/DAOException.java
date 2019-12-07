package com.learn.elasticsearch.exception;

/**
 * @date 2019/12/4 9:50
 * @author dshuyou
 */
public class DAOException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public DAOException(ExceptionEnum exceptionEnum){
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum(){
        return exceptionEnum;
    }
}