package com.learn.elasticsearch;

import com.learn.elasticsearch.model.IndexEnity;
import com.learn.util.JSONUtil;
import com.vividsolutions.jts.geom.Geometry;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;


/**
 * @Date 2019/8/28 11:11
 * @Created by dshuyou
 */
public class IndiceTest {

	private Indice indice;
	private RestHighLevelClient client;
	private String index;
	@Before
	public void init(){
		client = EsClientInit.getInstance().getClient();
		indice = Indice.getInstance(client);
		index = "dshuyou2";
	}

	@Test
	public void create() {
		try {
			indice.create(index);
			System.out.println("create index:" + index +" success");
		} catch (IOException e) {
			System.out.println("create index:" + index +" failed");
		}
	}

	@Test
	public void create2() {
		String analysis = JSONUtil.readLocalTextFile("C:\\Users\\dong6\\Desktop\\elasticsearch\\elasticsearch-core\\src\\main\\resources\\setting\\settings1.json");
		try {
			indice.create(index,analysis);
			System.out.println("create index:" + index +" success");
		} catch (IOException e) {
			System.out.println("create index:" + index +" failed");
		}
	}

	@Test
	public void putMapping() {
		//Comment comment = new Comment();
		//XContentBuilder mapping = indice.createMapping(comment);
		String mapping = JSONUtil.readLocalTextFile("C:\\Users\\dong6\\Desktop\\elasticsearch\\elasticsearch-core\\src\\main\\resources\\setting\\mappings1.json");
		try {
			indice.putMapping(index,mapping);
			System.out.println("put mapping:" + index +" success");
		} catch (IOException e) {
			System.out.println("put mapping:" + index +" failed");
		}
	}

	@Test
	public void createMapping(){
		Earthquake earthquake = new Earthquake();
		System.out.println(indice.createMapping(earthquake));
	}

	@Test
	public void updateSetting() throws IOException {
		UpdateSettingsRequest request = new UpdateSettingsRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_replicas",0)
				.put("index.refresh_interval",-1)
		);
		client.indices().putSettings(request, RequestOptions.DEFAULT);
	}

	@Test
	public void getSetting() {
		try {
			String setting = indice.getSetting(index);
			System.out.println(setting);
		} catch (IOException e) {
			System.out.println("get setting failed");
		}
	}

	@Test
	public void getMapping() {
		try {
			System.out.println(indice.getMapping(index));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteIndex() {
		try {
			indice.deleteIndex(index);
			System.out.println("delete index success");
		} catch (IOException e) {
			System.out.println("delete index failed");
		}
	}

	@Test
	public void isExists() {
		boolean result = false;
		try {
			result = indice.isExists(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}

	@Test
	public void putIndexTemplate(){
		String source = JSONUtil.readLocalTextFile("C:\\Users\\dong6\\Desktop\\elasticsearch\\elasticsearch-core\\src\\main\\resources\\setting\\indexTemplate.json");
		try {
			if(indice.putIndexTemplate(index,source)){
				System.out.println("put indexTemplate success");
			}
		} catch (IOException e) {
			System.out.println("put indexTemplate failed");
		}
	}

	@Test
	public void get() {
		try {
			IndexEnity enity = indice.get(index);
			System.out.println(enity.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getAllIndices(){
		try {
			Set<String> set = indice.getAllIndices();
			String[] indices = set.toArray(new String[0]);
			System.out.println(Arrays.toString(indices));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void refresh() {
	}

	@Test
	public void clearCache() {
	}

	@Test
	public void flush() {
	}

	@Test
	public void addAlias() throws IOException {
		indice.addAlias(index,"dsy");
	}

	private static class Earthquake{
		private Long id;
		private String time;
		private double latitude;
		private double longitude;
		private double depth;
		private double mag;
		private Geometry point;

		public Earthquake(){
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		public double getDepth() {
			return depth;
		}

		public void setDepth(double depth) {
			this.depth = depth;
		}

		public double getMag() {
			return mag;
		}

		public void setMag(double mag) {
			this.mag = mag;
		}

		@Override
		public String toString() {
			return "Earthquake{" +
					"id=" + id +
					", time='" + time + '\'' +
					", latitude=" + latitude +
					", longitude=" + longitude +
					", depth=" + depth +
					", mag=" + mag +
					'}';
		}
	}
	private static class Comment{
		private int id;
		private int liked;
		private String username;
		private String address;
		private String date;
		private String content;

		public Comment(){
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getLiked() {
			return liked;
		}

		public void setLiked(int liked) {
			this.liked = liked;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		@Override
		public String toString() {
			return "Comment{" +
					"id=" + id +
					", liked=" + liked +
					", username='" + username + '\'' +
					", address='" + address + '\'' +
					", date='" + date + '\'' +
					", content='" + content + '\'' +
					'}';
		}
	}
}
