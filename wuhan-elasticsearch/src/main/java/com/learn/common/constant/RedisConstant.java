package com.learn.common.constant;

/**
 * @author dshuyou
 * @date 2019/12/11 16:59
 */
public class RedisConstant {
    /**
     * Redis Key-Value expiration time
     */
    public final static long EXPIRE_TIME = 24 * 60 * 60L;
    /**
     * Serve as a cache for data services
     */
    public final static String DATA_SERVICE_CACHING = "DATA_SERVICE";
    /**
     * Save the database and data table hash mapping
     */
    public final static String DATABASE_TABLE = "DATABASE_TABLE";
    /**
     * Save the value of the specific id of the database table
     */
    public final static String SELECT_WITH_ID = "WITH_ID";
    /**
     * Save the full text search results to redis
     */
    public final static String FULLTEXT_QUERY = "FULLTEXT";
    /**
     * Save the terms search results to redis
     */
    public final static String TERMS_QUERY = "TERMS";
    /**
     * Save the geometry search results to redis
     */
    public final static String GEO_QUERY = "GEO";
    /**
     * Save the bool search results to redis
     */
    public final static String BOOL_QUERY = "BOOL";
}