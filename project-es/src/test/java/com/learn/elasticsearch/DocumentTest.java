package com.learn.elasticsearch;

import com.learn.elasticsearch.model.SourceEntity;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date 2019/8/28 11:11
 * @Created by dshuyou
 */
public class DocumentTest {

	private Indice indice;
	private RestHighLevelClient client;
	private String index;
	@Before
	public void init(){
		client = EsClientInit.getInstance().getClient();
		indice = Indice.getInstance(client);
		index = "dshuyou0";
	}
	@Test
	public void index() throws IOException {
		Document document = Document.getInstance(client);
		Map<String, Object> map = new HashMap<>();
		map.put("point","POINT(-118.1850357 34.1645203)");
		Object o = document.index(index,map);
		System.out.println(o);
	}

	@Test
	public void index1() throws IOException {
		String json = "{\"mag\":3.69," +
				"\"depth\":6.0," +
				"\"latitude\":37.4333333," +
				"\"id\":2," +
				"\"time\":\"我是个中国人\"," +
				" \"point\":{\"type\": \"Point\", \"coordinates\": [-118.7435, 37.4333333]},"+
				"\"longitude\":-118.7435}";

		Document document = Document.getInstance(client);
		Object object = document.index(index,json);
		System.out.println(object);
	}

	@Test
	public void insertAsync() {
	}

	@Test
	public void delete() throws IOException {
		Document document = Document.getInstance(client);
		boolean result = document.delete(index,"s5zQ3GwBiAEaKMA44SCd");
		System.out.println(result);
	}

	@Test
	public void count() {
	}

	@Test
	public void isIdExists() {
	}

	@Test
	public void get() {
	}

	@Test
	public void getFieldsValues() {
	}

	@Test
	public void multiGet() {
	}

	@Test
	public void update() {
	}

	@Test
	public void generateRequests() {
	}

	@Test
	public void bulkIndex() {

	}

	@Test
	public void bulkIndex1() {
	}

	@Test
	public void bulkUpdate() {
	}

	@Test
	public void batchInsert() {
	}

	@Test
	public void batchAscendingId() {
	}


	@Test
	public void bulkProcessorIndex() throws IOException, InterruptedException {
		Document document = Document.getInstance(client);
		SourceEntity sourceEntity = new SourceEntity();
		sourceEntity.setId("0");
		Map<String, Object> map = new HashMap<>();
		map.put("title","123456465465");
		sourceEntity.setSource(map);
		List<SourceEntity> list = new ArrayList<>();
		list.add(sourceEntity);
		document.asycBulkIndex(index,list);
	}
}