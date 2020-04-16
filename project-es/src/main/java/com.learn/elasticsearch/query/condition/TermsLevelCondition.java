package com.learn.elasticsearch.query.condition;

import java.io.Serializable;

/**
 * Terms level query condition
 *
 * @date 2019/8/21 10:06
 * @author dshuyou
 */
public class TermsLevelCondition extends BaseCondition implements Serializable {
	private String field;
	private String value;
	private String[] values;
	private String[] ids;
	private String gte;
	private String lte;

	public TermsLevelCondition(){
		super();
	}

	public TermsLevelCondition(int from, int size){
		super(from, size);
	}

	public static TermsLevelCondition fromRange(String field, String gte, String lte){
		return new TermsLevelCondition(field,gte,lte);
	}

	public static TermsLevelCondition fromRange(int from, int size, String field, String gte, String lte){
		return new TermsLevelCondition(from,size,field,gte,lte);
	}

	public static TermsLevelCondition fromTerm(String field, String value){
		return new TermsLevelCondition(field,value);
	}

	public static TermsLevelCondition fromTerm(int from, int size, String field, String value){
		return new TermsLevelCondition(from,size,field,value);
	}

	public static TermsLevelCondition fromTerms(String field, String[] values){
		return new TermsLevelCondition(field,values);
	}

	public static TermsLevelCondition fromTerms(int from, int size, String field, String[] values){
		return new TermsLevelCondition(from,size,field,values);
	}

	private TermsLevelCondition(String field, String gte, String lte){
		super();
		this.field = field;
		this.gte = gte;
		this.lte = lte;
	}

	private TermsLevelCondition(int from, int size, String field, String gte, String lte){
		super(from,size);
		this.field = field;
		this.gte = gte;
		this.lte = lte;
	}

	private TermsLevelCondition(String field, String value){
		super();
		this.field = field;
		this.value = value;
	}

	private TermsLevelCondition(int from, int size, String field, String value){
		super(from,size);
		this.field = field;
		this.value = value;
	}

	private TermsLevelCondition(String field, String[] values){
		super();
		this.field = field;
		this.values = values;
	}

	private TermsLevelCondition(int from, int size, String field, String[] values){
		super(from,size);
		this.field = field;
		this.values = values;
	}

	public String getField() {
		return field;
	}

	public String[] getIds() {
		return ids;
	}

	public String getGte() {
		return gte;
	}

	public String getLte() {
		return lte;
	}

	public String getValue() {
		return value;
	}

	public String[] getValues() {
		return values;
	}
}

