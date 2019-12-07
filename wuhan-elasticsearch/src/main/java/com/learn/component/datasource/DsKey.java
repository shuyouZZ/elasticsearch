package com.learn.component.datasource;

/**
 * @author dshuyou
 * @date 2019/12/4 16:55
 */
public enum DsKey {

    DATASOURCE("datasource"),

    DB1("db1"),

    DB2("db2");

    private final String code;

    DsKey(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}