package com.learn.elasticsearch.query.query_enum;

/**
 * @date 2019/8/30 11:41
 * @author dshuyou
 */
public enum  GeoEnum {
	boundingBoxQuery(0,"boundingBoxQuery"),

	disjointQuery(1,"disjointQuery"),

	distanceQuery(2,"distanceQuery"),

	geoShapeQuery(3,"geoShapeQuery"),

	intersectionQuery(4,"intersectionQuery"),

	polygonQuery(5,"polygonQuery"),

	withinQuery(6,"withinQuery");

	public int index;
	public String name;

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	GeoEnum(int index, String name) {
		this.index = index;
		this.name = name;
	}

	@Override
	public String toString() {
		return "index: " + this.index + ", name: " + this.name;
	}
}
