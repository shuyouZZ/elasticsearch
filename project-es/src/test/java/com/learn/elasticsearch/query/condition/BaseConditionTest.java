package com.learn.elasticsearch.query.condition;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author dshuyou
 * @Date 2019/9/3 21:42
 */
public class BaseConditionTest {

	@Test
	public void getFrom() {
	}

	@Test
	public void setFrom() {
	}

	@Test
	public void getSize() {
	}

	@Test
	public void setSize() {
	}

	@Test
	public void textEquals(){
		FullTextCondition fullTextCondition = FullTextCondition.match("","");
		GeoCondition geoCondition = new GeoCondition();
		TermsLevelCondition termsLevelCondition = new TermsLevelCondition();
		System.out.println(fullTextCondition instanceof BaseCondition);

		System.out.println(BaseCondition.class.getClass().equals(fullTextCondition.getClass()));
		System.out.println(fullTextCondition.getClass().equals(fullTextCondition.getClass()));
	}

	@Test
	public void testGetClass1(){
		Map<String, BaseCondition> map = new HashMap<>();
		map.put("match",FullTextCondition.match("",""));
		for (Map.Entry<String, BaseCondition> entry : map.entrySet()) {
			String type = entry.getKey();
			if (entry.getValue() instanceof FullTextCondition) {
				System.out.println(type);
			}else {
				System.out.println("false");
			}
		}
	}

	@Test
	public void testGetClass(){
		Map<String, BaseCondition> map = new HashMap<>();
		map.put("match",FullTextCondition.match("",""));
		for (Map.Entry<String, BaseCondition> entry : map.entrySet()) {
			String type = entry.getKey();
			if (entry.getValue().getClass() ==  FullTextCondition.class) {
				System.out.println(type);
			}else {
				System.out.println("false");
			}
		}
	}
}