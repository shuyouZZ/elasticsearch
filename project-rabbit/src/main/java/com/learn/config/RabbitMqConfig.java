package com.learn.config;

import com.learn.dto.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dshuyou
 * @date 2019/11/1 9:44
 */
@Configuration
public class RabbitMqConfig {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqConfig.class);
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> logger.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause));
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> logger.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message));
        return rabbitTemplate;
    }

    /**
     * 消息实际消费队列所绑定的交换机
     */
    @Bean(name = "dataDirect")
    DirectExchange dataDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_DATA_TRANSPORT.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 延迟队列所绑定的交换机
     */
    @Bean(name = "dataTtlDirect")
    DirectExchange dataTtlDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.QUEUE_TTL_DATA_TRANSPORT.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 实际消费队列
     */
    @Bean(name = "dataQueue")
    public Queue dataQueue() {
        return new Queue(QueueEnum.QUEUE_DATA_TRANSPORT.getName());
    }

    /**
     * 延迟队列（死信队列）
     */
    @Bean(name = "dataTtlQueue")
    public Queue dataTtlQueue() {
        return QueueBuilder
                .durable(QueueEnum.QUEUE_TTL_DATA_TRANSPORT.getName())
                .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_DATA_TRANSPORT.getExchange())//到期后转发的交换机
                .withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_DATA_TRANSPORT.getRouteKey())//到期后转发的路由键
                .build();
    }

    /**
     * 将队列绑定到交换机
     */
    @Bean(name = "dataBinding")
    Binding dataBinding(@Qualifier("dataDirect") DirectExchange orderDirect, @Qualifier("dataQueue") Queue orderQueue){
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with(QueueEnum.QUEUE_DATA_TRANSPORT.getRouteKey());
    }

    /**
     * 将延迟队列绑定到交换机
     */
    @Bean(name = "dataTtlBinding")
    Binding dataTtlBinding(@Qualifier("dataTtlDirect") DirectExchange orderTtlDirect, @Qualifier("dataTtlQueue") Queue orderTtlQueue){
        return BindingBuilder
                .bind(orderTtlQueue)
                .to(orderTtlDirect)
                .with(QueueEnum.QUEUE_TTL_DATA_TRANSPORT.getRouteKey());
    }

}
