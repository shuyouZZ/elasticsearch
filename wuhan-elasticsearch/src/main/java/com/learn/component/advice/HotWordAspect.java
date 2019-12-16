package com.learn.component.advice;

import com.learn.common.constant.RedisConstant;
import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.condition.BoolCondition;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;
import com.learn.service.RedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HotWordAspect {
	private static final Logger logger = LoggerFactory.getLogger(HotWordAspect.class);

	@Autowired
	@Qualifier("RedisServiceImpl")
	private RedisService hotWordService;

	@Around("@annotation(com.learn.component.HotWord)")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		Object object = joinPoint.proceed();
		Object o = joinPoint.getArgs()[1];
		action(o);
		return object;
	}

	public void action(Object object){
		if(object instanceof TermsLevelCondition){
			TermsLevelCondition condition = (TermsLevelCondition)object;
			String keyword = condition.getValue();
			if(condition.getValue() != null){
				logger.info("Start increment hot words score to redis: {key: hotword, value: {}}",keyword);
				hotWordService.increment("hotword", keyword, 1);
				hotWordService.expire(keyword, RedisConstant.EXPIRE_TIME);
				logger.info("completed increment");
			}
		}else if(object instanceof FullTextCondition){
			FullTextCondition condition = (FullTextCondition)object;
			String keyword = condition.getValue();
			if(condition.getValue() != null){
				logger.info("Start increment hot words score to redis: {key: hotword, value: {}}",keyword);
				hotWordService.increment("hotword", keyword, 1);
				hotWordService.expire(keyword, RedisConstant.EXPIRE_TIME);
				logger.info("completed increment");
			}
		}else if(object instanceof BoolCondition){
			BoolCondition condition = (BoolCondition)object;
			BaseCondition[] conditions  = condition.getConditions();
			for (BaseCondition c : conditions){
				action(c);
			}
		}else {
			String keyword = String.valueOf(object);
			logger.info("Start increment hot words score to redis: {key: hotword, value: {}}",keyword);
			hotWordService.increment("hotword", keyword, 1);
			hotWordService.expire(keyword, RedisConstant.EXPIRE_TIME);
			logger.info("completed increment");
		}
	}
}
