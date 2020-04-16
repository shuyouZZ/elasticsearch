package com.learn.component;

import com.learn.mapper.CommentsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @date 2019/8/21 13:06
 * @author dshuyou
 */
@Component
public class OrderTimeOutCancelTask {
	private static final Logger logger = LoggerFactory.getLogger(OrderTimeOutCancelTask.class);
	private static final long DELAY_TIME = 20 * 1000;
	@Autowired
	private DataSender dataSender;
	@Resource
	private CommentsMapper commentsMapper;
	@Value("${mysql.dshuyou.comments.updatetime}")
	private String updatetime;

	/**
	 * cron表达式：Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
	 * 每10分钟扫描一次
	 */
	//@Scheduled(cron = "0 0/1 * ? * ?")
	@Scheduled(cron = "0/10 * * * * ?")
	private void dataTransport() {
		//List<Map<String,Object>> list = commentsMapper.update1();
		List<Map<String,Object>> list = commentsMapper.updateByTime(updatetime);
		if(!list.isEmpty()){
			logger.info("Start send message to RabbitMQ");
			dataSender.sendMessage(DELAY_TIME,list);
		}else {logger.info("List is empty");
		};
	}
}
