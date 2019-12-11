package com.learn.elasticsearch.query.condition;

import org.elasticsearch.common.geo.GeoPoint;
import org.locationtech.jts.geom.Coordinate;

import java.io.Serializable;
import java.util.List;

/**
 * Geometry query condition
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

	public GeoCondition(int from, int size){
		super(from, size);
	}

	public void setBox(double leftLatitude, double leftLongitude, double rightLatitude, double rightLongitude){
		this.topLeft = new GeoPoint(leftLatitude,leftLongitude);
		this.bottomRight = new GeoPoint(rightLatitude,rightLongitude);
	}

	public void setPoint(double latitude,double longitude){
		this.point = new GeoPoint(latitude,longitude);
	}

	public void setCoordinate(double latitude,double longitude){
		this.coordinate = new Coordinate(longitude,latitude);
	}

	public void setBoxfromCoord(double leftLatitude,double leftLongitude,double rightLatitude,double rightLongitude){
		this.tlCoordinate = new Coordinate(leftLongitude,leftLatitude);
		this.brCoordinate = new Coordinate(rightLongitude,rightLatitude);
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

	public void setTopLeft(GeoPoint topLeft) {
		this.topLeft = topLeft;
	}

	public GeoPoint getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(GeoPoint bottomRight) {
		this.bottomRight = bottomRight;
	}

	public GeoPoint getPoint() {
		return point;
	}

	public void setPoint(GeoPoint point) {
		this.point = point;
	}

	public Coordinate getTlCoordinate() {
		return tlCoordinate;
	}

	public void setTlCoordinate(Coordinate tlCoordinate) {
		this.tlCoordinate = tlCoordinate;
	}

	public Coordinate getBrCoordinate() {
		return brCoordinate;
	}

	public void setBrCoordinate(Coordinate brCoordinate) {
		this.brCoordinate = brCoordinate;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
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

