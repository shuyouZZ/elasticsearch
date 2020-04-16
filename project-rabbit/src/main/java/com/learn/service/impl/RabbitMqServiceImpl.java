package com.learn.service.impl;

import com.learn.component.DataSender;
import com.learn.model.Student;
import com.learn.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author dshuyou
 * @date 2019/11/1 9:52
 */
public class RabbitMqServiceImpl implements RabbitMqService {
    private final long delayTime = 5 * 60;
    @Autowired
    private DataSender dataSender;

    @Override
    public void dataTransport(List object) {
        dataSender.sendMessage(delayTime,object);
    }
}
