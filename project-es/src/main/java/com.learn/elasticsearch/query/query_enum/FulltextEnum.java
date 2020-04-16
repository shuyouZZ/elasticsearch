package com.learn.elasticsearch.query.query_enum;

/**
 * @date 2019/7/25 11:23
 * @author dshuyou
 */
public enum FulltextEnum {
	matchAllQuery(0,"matchAllQuery"),

	matchQuery(1,"matchQuery"),

	matchPhraseQuery(2,"matchPhraseQuery"),

	matchPhrasePrefixQuery(3,"matchPhrasePrefixQuery"),

	queryString(4,"queryString");

	public int index;
	public String name;

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	FulltextEnum(int index, String name) {
		this.index = index;
		this.name = name;
	}

	@Override
	public String toString() {
		return "index: " + this.index + ", name: " + this.name;
	}

}
