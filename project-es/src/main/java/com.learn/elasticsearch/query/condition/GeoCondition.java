package com.learn.elasticsearch.query.condition;

import org.elasticsearch.common.geo.GeoPoint;
import org.locationtech.jts.geom.Coordinate;

import java.io.Serializable;
import java.util.List;

/**
 * Geometry query condition
 *
 * @date 2019/8/21 10:06
 * @author dshuyou
 */
public class GeoCondition extends BaseCondition implements Serializable {
	private String field;
	private String distance;
	private String shapeType;
	private int shapeNumber;

	private GeoPoint topLeft;
	private GeoPoint bottomRight;
	private GeoPoint point;
	private Coordinate tlCoordinate;
	private Coordinate brCoordinate;
	private Coordinate coordinate;
	private List<GeoPoint> points;
	private List<Coordinate> coordinates;

	public GeoCondition(){
		super();
	}

	public static GeoCondition setBox(double leftLatitude, double leftLongitude, double rightLatitude, double rightLongitude){
		return new GeoCondition(leftLatitude,leftLongitude,rightLatitude,rightLongitude);
	}

	public static GeoCondition setPoint(double latitude,double longitude){
		return new GeoCondition(latitude,longitude);
	}

	public static GeoCondition setCoordinate(double latitude,double longitude){
		return new GeoCondition(latitude,longitude);
	}

	public static GeoCondition setBoxfromCoord(double leftLatitude,double leftLongitude,double rightLatitude,double rightLongitude){
		return new GeoCondition(leftLatitude,leftLongitude,rightLatitude,rightLongitude);
	}

	private GeoCondition(double leftLatitude, double leftLongitude, double rightLatitude, double rightLongitude){
		super();
		this.topLeft = new GeoPoint(leftLatitude,leftLongitude);
		this.bottomRight = new GeoPoint(rightLatitude,rightLongitude);
		this.tlCoordinate = new Coordinate(leftLongitude,leftLatitude);
		this.brCoordinate = new Coordinate(rightLongitude,rightLatitude);
	}

	private GeoCondition(double latitude,double longitude){
		super();
		this.point = new GeoPoint(latitude,longitude);
		this.coordinate = new Coordinate(latitude,longitude);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getShapeType() {
		return shapeType;
	}

	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}

	public int getShapeNumber() {
		return shapeNumber;
	}

	public void setShapeNumber(int shapeNumber) {
		this.shapeNumber = shapeNumber;
	}

	public GeoPoint getTopLeft() {
		return topLeft;
	}

	public GeoPoint getBottomRight() {
		return bottomRight;
	}

	public GeoPoint getPoint() {
		return point;
	}

	public Coordinate getTlCoordinate() {
		return tlCoordinate;
	}

	public Coordinate getBrCoordinate() {
		return brCoordinate;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public List<GeoPoint> getPoints() {
		return points;
	}

	public void setPoints(List<GeoPoint> points) {
		this.points = points;
	}

	public List<Coordinate> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}
}

