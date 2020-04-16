package com.learn.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ReturnMsgCallBack implements RabbitTemplate.ReturnCallback {

    private static final Logger logger = LoggerFactory.getLogger(ReturnMsgCallBack.class);

    @Override
    public void returnedMessage(Message message, int replyCode,
                                String replyText, String exchange, String routingKey) {
        logger.info("消息内容:{}",new String(message.getBody()));
        logger.info("回复文本:{},回复代码：{}",replyText,replyCode);
        logger.info("交换器名称:{},路由键：{}",exchange,routingKey);
    }
}