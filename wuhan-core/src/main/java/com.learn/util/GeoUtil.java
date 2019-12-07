package com.learn.util;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.io.WKTWriter;
import org.geotools.geojson.geom.GeometryJSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @date 2019/8/24 16:48
 * @author dshuyou
 */
public class GeoUtil {
	private static final Logger logger = LoggerFactory.getLogger(GeoUtil.class);

	public static Geometry geojson2Geometry(String geoJson) {
		GeometryJSON json = new GeometryJSON();
		StringReader bf = new StringReader(geoJson);
		try {
			return json.read(bf);
		} catch (IOException e) {
			logger.error("Geojson to Geometry error");
			return null;
		}
	}

	public static String geometry2Geojson(Geometry geometry) {
		GeometryJSON json = new GeometryJSON();
		StringWriter writer = new StringWriter();
		try {
			json.write(geometry,writer);
		} catch (IOException e) {
			logger.error("Geometry to Geojson error");
			return null;
		}
		return writer.toString();
	}

	public static String wkt2Geojson(String wkt) {
		WKTReader wktReader = new WKTReader();
		Geometry geometry;

		try {
			geometry = wktReader.read(wkt);
		} catch (ParseException e) {
			logger.error("Wkt to Geojson error");
			return null;
		}
		return geometry2Geojson(geometry);
	}

	public static String geojson2Wkt(String geojson){
		Geometry geometry = geojson2Geometry(geojson);
		WKTWriter wktWriter = new WKTWriter();
		return wktWriter.write(geometry);
	}

	public static String geometry2Wkt(Geometry geometry){
		WKTWriter wktWriter = new WKTWriter();
		return wktWriter.write(geometry);
	}

	public static Geometry wkt2Geometry(String wkt) {
		WKTReader wktReader = new WKTReader();
		try {
			return wktReader.read(wkt);
		} catch (ParseException e) {
			logger.error("Wkt to Geometry error");
			return null;
		}
	}
}
