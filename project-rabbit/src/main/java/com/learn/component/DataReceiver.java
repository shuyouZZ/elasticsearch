package com.learn.component;

import com.alibaba.fastjson.JSONObject;
import com.learn.elasticsearch.model.SourceEntity;
import com.learn.service.ElasticsearchService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/11/1 10:52
 */
@Component
@RabbitListener(queues = "rabbit.data.transport")
public class DataReceiver<T extends Map<String, Object>> {
    private static final Logger logger = LoggerFactory.getLogger(DataReceiver.class);
    @Autowired
    private ElasticsearchService elasticsearchService;
    @Value("${elastic.index}")
    private String index;
    @Value("${elastic.idColumn}")
    private String idColumn;

    @RabbitHandler
    public void handle(List<T> object, Message message, Channel channel) throws IOException {
        logger.info("Receive delay message size is : {}" ,object.size());
        try {
            if(!object.isEmpty()){
                List<SourceEntity> bulk = new ArrayList<>();
                for (Map<String, Object> r : object){
                    SourceEntity sourceEntity = new SourceEntity();
                    sourceEntity.setSource(JSONObject.toJSONString(r));
                    sourceEntity.setId(String.valueOf(r.get(idColumn)));
                    bulk.add(sourceEntity);
                }
                elasticsearchService.bulkIndex(index,bulk);
            }
            logger.info("Consumption data success");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            logger.error(e.getMessage());//TODO 业务处理    记录未被消费的消息id，并返回队列继续发送
            channel.basicRecover(true);
        }
    }
}
