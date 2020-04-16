package com.learn.util;

import com.vividsolutions.jts.geom.Geometry;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

/**
 * @Date 2019/8/28 11:12
 * @author dshuyou
 */
public class GeoUtilTest {
	private String json;
	@Before
	public void init(){
		json = "{\n" +
				"                \"type\": \"LineString\",\n" +
				"                \"coordinates\": [\n" +
				"                    [\n" +
				"                        120.6584555,\n" +
				"                        30.45144\n" +
				"                    ],\n" +
				"                    [\n" +
				"                        120.1654515,\n" +
				"                        30.54848\n" +
				"                    ]\n" +
				"                ]\n" +
				"            }";
	}

	@Test
	public void geojson2Geometry() throws IOException {
		Geometry geometry = GeoUtil.geojson2Geometry(json);
		System.out.println(geometry);
	}

	@Test
	public void geometry2Geojson() throws IOException {
		Geometry geometry = GeoUtil.geojson2Geometry(json);
		String geojson = GeoUtil.geometry2Geojson(geometry);
		System.out.println(geojson);
	}

	@Test
	public void geometry2Wkt() throws IOException {
		Geometry geometry = GeoUtil.geojson2Geometry(json);
		String wkt = GeoUtil.geometry2Wkt(geometry);
		System.out.println(wkt);
	}

	@Test
	public void wkt2Geometry() throws ParseException, IOException {
		Geometry geometry = GeoUtil.geojson2Geometry(json);
		String wkt = GeoUtil.geometry2Wkt(geometry);
		System.out.println(GeoUtil.wkt2Geometry(wkt));
	}

	@Test
	public void wkt2Geojson() throws IOException, ParseException {
		Geometry geometry = GeoUtil.geojson2Geometry(json);
		String wkt = GeoUtil.geometry2Wkt(geometry);
		String geojson = GeoUtil.wkt2Geojson(wkt);
		System.out.println(geojson);
	}

	@Test
	public void geojson2Wkt() throws IOException {
		Geometry geometry = GeoUtil.geojson2Geometry(json);
		String geojson = GeoUtil.geometry2Geojson(geometry);
		System.out.println(GeoUtil.geojson2Wkt(geojson));
	}
}