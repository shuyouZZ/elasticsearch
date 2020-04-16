package com.learn.elasticsearch.model;

import java.io.Serializable;

/**
 * @author dshuyou
 * @date 2019/8/30 16:05
 */
public class SourceEntity<T> implements Serializable {
	private String id;
	private Long version;
	private T source;

	public SourceEntity(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Object getSource() {
		return source;
	}

	public void setSource(T source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "SourceEntity{" +
				"id='" + id + '\'' +
				", version=" + version +
				", source=" + source +
				'}';
	}
}
