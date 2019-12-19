package com.learn.elasticsearch.query.condition;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Bool query condition
 *
 * @author dshuyou
 * @date 2019/9/17 16:24
 */
public class BoolCondition extends BaseCondition implements Serializable {
	private String[] queryType;
	private BaseCondition[] conditions;

	public BoolCondition setConditions(String[] queryType, BaseCondition[] conditions){
		return new BoolCondition(queryType,conditions);
	}

	public BoolCondition setConditions(int from, int size, String[] queryType, BaseCondition[] conditions){
		return new BoolCondition(from,size,queryType,conditions);
	}

	private BoolCondition(String[] queryType, BaseCondition[] conditions){
		super();
		this.queryType = queryType;
		this.conditions = conditions;
	}

	private BoolCondition(int from, int size, String[] queryType, BaseCondition[] conditions){
		super(from, size);
		this.queryType = queryType;
		this.conditions = conditions;
	}

	public Map<String,BaseCondition> setConditions(){
		Map<String, BaseCondition> map = new HashMap<>();
		for (int i = 0;i < queryType.length;i++){
			map.put(queryType[i],conditions[i]);
		}
		return map;
	}

	public String[] getQueryType() {
		return queryType;
	}

	public BaseCondition[] getConditions() {
		return conditions;
	}
}
