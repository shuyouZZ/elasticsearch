package com.learn.dto;

/**
 * @author dshuyou
 * @date 2019/11/1 9:46
 */
public enum QueueEnum {

    /**
     * 消息通知队列
     */
    QUEUE_DATA_TRANSPORT("rabbit.data.direct", "rabbit.data.transport", "rabbit.data.transport"),
    /**
     * 消息通知ttl队列
     */
    QUEUE_TTL_DATA_TRANSPORT("rabbit.data.direct.ttl", "rabbit.data.transport.ttl", "rabbit.data.transport.ttl");
    /**
     * 交换名称
     */
    private String exchange;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 路由键
     */
    private String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }

    public String getExchange() {
        return exchange;
    }

    public String getName() {
        return name;
    }

    public String getRouteKey() {
        return routeKey;
    }
}