package com.learn.elasticsearch.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author dshuyou
 * @date 2019/9/24 15:19
 */
public class SuggestEnity implements Serializable {
	private String[] field;
	private String keyword;
	private int size;

	public SuggestEnity(){}

	public SuggestEnity(String[] field, String keyword, int size) {
		this.field = field;
		this.keyword = keyword;
		this.size = size;
	}

	public String[] getField() {
		return field;
	}

	public void setField(String[] field) {
		this.field = field;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "SuggestEnity{" +
				", field=" + Arrays.toString(field) +
				", keyword='" + keyword + '\'' +
				", size=" + size +
				'}';
	}
}
