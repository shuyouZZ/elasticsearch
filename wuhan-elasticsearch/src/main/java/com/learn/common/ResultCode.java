package com.learn.common;

/**
 * @date 2019/12/4 9:50
 * @author dshuyou
 */
public interface ResultCode<T> {
    int code();

    String message();

    T result();
}