package com.learn.elasticsearch.query.condition;

import java.io.Serializable;

/**
 * Full text query condition
 *
 * @date 2019/8/21 10:05
 * @author dshuyou
 */
public class FullTextCondition extends BaseCondition implements Serializable {
	private String field;
	private String[] fields;
	private String value;

	public FullTextCondition(){
		super();
	}

	public static FullTextCondition match(String field, String value){
		return new FullTextCondition(field,value);
	}

	public static FullTextCondition queryString(String[] fields, String value){
		return new FullTextCondition(fields,value);
	}

	public static FullTextCondition match(int from, int size, String field, String value){
		return new FullTextCondition(from,size,field,value);
	}

	public static FullTextCondition queryString(int from, int size, String[] fields, String value){
		return new FullTextCondition(from,size,fields,value);
	}

	private FullTextCondition(String field, String value){
		super();
		this.field = field;
		this.value = value;
	}

	private FullTextCondition(String[] fields, String value){
		super();
		this.fields = fields;
		this.value = value;
	}

	private FullTextCondition(int from, int size, String field, String value){
		super(from, size);
		this.field = field;
		this.value = value;
	}

	private FullTextCondition(int from, int size, String[] fields, String value){
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
}
