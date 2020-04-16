package com.learn.component;

import com.learn.dto.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * @author dshuyou
 * @date 2019/11/1 10:45
 */
@Component
public class DataSender<T> {
    private static final Logger logger = LoggerFactory.getLogger(DataSender.class);
    private RabbitTemplate rabbitTemplate;
    @Autowired
    public DataSender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        //rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMessage(final long delayTime, List<T> object){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(QueueEnum.QUEUE_TTL_DATA_TRANSPORT.getExchange(),
                QueueEnum.QUEUE_TTL_DATA_TRANSPORT.getRouteKey(),object,message ->{
                            message.getMessageProperties().setExpiration(String.valueOf(delayTime));
                            return message; },correlationData);
        logger.info("Send delay message size is : {}" ,object.size());
    }

    /*@Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info(" Callback id: {}", correlationData.getId());
        if (ack) {
            logger.info("Message sent successfully");
        } else {
            logger.info("Message sent failed: {}" , cause);
        }
    }*/
}
