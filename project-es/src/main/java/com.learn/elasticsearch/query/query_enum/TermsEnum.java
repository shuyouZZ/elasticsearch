package com.learn.elasticsearch.query.query_enum;

/**
 * @date 2019/8/30 11:39
 * @author dshuyou
 */
public enum  TermsEnum {
	fuzzyQuery(0,"fuzzyQuery"),

	idsQuery(1,"idsQuery"),

	rangeQuery(2,"rangeQuery"),

	prefixQuery(3,"prefixQuery"),

	termQuery(4,"termQuery"),

	termsQuery(5,"termsQuery");

	public int index;
	public String name;

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	TermsEnum(int index, String name) {
		this.index = index;
		this.name = name;
	}

	@Override
	public String toString() {
		return "index: " + this.index + ", name: " + this.name;
	}
}
