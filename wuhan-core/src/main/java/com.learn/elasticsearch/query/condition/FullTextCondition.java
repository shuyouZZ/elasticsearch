package com.learn.elasticsearch.query.condition;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Full text query condition
 * @date 2019/8/21 10:05
 * @author dshuyou
 */
public class FullTextCondition extends BaseCondition implements Serializable {
	private String field;
	private String[] fields;
	private String value;

	public FullTextCondition(){
	}

	public FullTextCondition(String field, String value){
		super();
		this.field = field;
		this.value = value;
	}

	public FullTextCondition(String[] fields, String value){
		super();
		this.fields = fields;
		this.value = value;
	}

	public FullTextCondition(int from, int size, String field, String value){
		super(from, size);
		this.field = field;
		this.value = value;
	}

	public FullTextCondition(int from, int size, String[] fields, String value){
		super(from, size);
		this.fields = fields;
		this.value = value;
	}

	public String getField() {
		return field;
	}

	public String getValue() {
		return value;
	}

	public String[] getFields() {
		return fields;
	}

	@Override
	public String toString() {
		if(this.field == null || this.field.isEmpty()){
			return "fields:" + Arrays.toString(fields) + ",value:" + value + ",from:" + from + ",size:" + size;
		}else {
			return "field:" + field + ",value:" + value + ",from:" + from + ",size:" + size;
		}
	}
}
