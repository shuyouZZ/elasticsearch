package com.learn.elasticsearch.model;

import java.io.Serializable;
import java.util.Map;

public class IndexEnity implements Serializable {

	private String indexName;
	private Object objectEnity;
	private int shards;
	private int replicas;
	private int reflushInterval;
	private Map<String, Object> mapping;
	private String settings;

	public IndexEnity(){}

	public IndexEnity(String indexName, Object objectEnity, int shards, int replicas, int reflushInterval) {
		this.indexName = indexName;
		this.objectEnity = objectEnity;
		this.shards = shards;
		this.replicas = replicas;
		this.reflushInterval = reflushInterval;
	}

	public IndexEnity(String indexName, String settings,Map<String, Object> mapping) {
		this.indexName = indexName;
		this.settings = settings;
		this.mapping = mapping;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public Object getObjectEnity() {
		return objectEnity;
	}

	public void setObjectEnity(Object objectEnity) {
		this.objectEnity = objectEnity;
	}

	public int getShards() {
		return shards;
	}

	public void setShards(int shards) {
		this.shards = shards;
	}

	public int getReplicas() {
		return replicas;
	}

	public void setReplicas(int replicas) {
		this.replicas = replicas;
	}

	public int getReflushInterval() {
		return reflushInterval;
	}

	public void setReflushInterval(int reflushInterval) {
		this.reflushInterval = reflushInterval;
	}

	public Map<String, Object> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String, Object> mapping) {
		this.mapping = mapping;
	}

	public String getSettings() {
		return settings;
	}

	public void setSettings(String settings) {
		this.settings = settings;
	}

	@Override
	public String toString() {
		return "IndexEnity{" +
				"indexName='" + indexName + '\'' +
				", mapping='" + mapping + '\'' +
				", settings='" + settings + '\'' +
				'}';
	}
}