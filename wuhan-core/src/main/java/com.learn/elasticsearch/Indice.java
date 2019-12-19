package com.learn.elasticsearch;

import com.learn.elasticsearch.model.IndexEnity;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import static org.elasticsearch.client.Requests.getRequest;
import static org.elasticsearch.client.Requests.refreshRequest;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @date 2019/8/21 10:02
 * @author dshuyou
 */
public class Indice {
	private static final int SHARDS = 2;
	public static final int INIT_REPLICAS = 0;
	public static final int REPLICAS = 1;
	private static final int TIMEOUT = 2;
	private static final int MASTER_TIMEOUT = 1;
	public static final int INIT_REFLUSH_INTERVAL = -1;
	public static final int REFRESH_INTERVAL = 30;

	private RestHighLevelClient client;

	private Indice(RestHighLevelClient client){
		this.client = client;
	}

	public static Indice getInstance(RestHighLevelClient client){
		return new Indice(client);
	}

	/**
	 * @param index 	索引
	 * @return  创建索引结果
	 * @throws IOException io异常
	 */
	public boolean create(String index) throws IOException {
		Objects.requireNonNull(index, "index");
		CreateIndexRequest request = new CreateIndexRequest(index);

		request.settings(Settings.builder()
				.put("index.number_of_shards", SHARDS)
				.put("index.number_of_replicas", INIT_REPLICAS)
				.put("index.refresh_interval", TimeValue.timeValueSeconds(INIT_REFLUSH_INTERVAL)));
		request.setTimeout(TimeValue.timeValueMinutes(TIMEOUT));
		request.setMasterTimeout(TimeValue.timeValueMinutes(MASTER_TIMEOUT));
		request.waitForActiveShards(ActiveShardCount.DEFAULT);
		//request.source(createAnalysis());
		return client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	/***
	 * @param index 	索引
	 * @param settings	 设置
	 * @return 	创建索引结果
	 * @throws IOException	io异常
	 */
	@SuppressWarnings("unchecked")
	public<T> boolean create(String index, T settings) throws IOException {
		CreateIndexRequest request = new CreateIndexRequest(index);
		if (settings instanceof String) {
			request.source(String.valueOf(settings), XContentType.JSON);
		} else if (settings instanceof Map) {
			request.source((Map) settings);
		} else if (settings instanceof XContentBuilder) {
			request.source((XContentBuilder) settings);
		}

		return client.indices().create(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	/**
	 * @param index	索引
	 * @param mapping	映射
	 * @return	更新索引映射结果
	 * @throws IOException io异常
	 */
	@SuppressWarnings("unchecked")
	public<T> boolean putMapping(String index, T mapping) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(mapping, "mapping");
		PutMappingRequest request = new PutMappingRequest(index);

		if (mapping instanceof String) {
			request.source(String.valueOf(mapping), XContentType.JSON);
		} else if (mapping instanceof Map) {
			request.source((Map) mapping);
		} else if (mapping instanceof XContentBuilder) {
			request.source((XContentBuilder) mapping);
		}
		return client.indices().putMapping(request,RequestOptions.DEFAULT).isAcknowledged();
	}

	/**
	 * @param index	索引
	 * @return 	更新索引设置结果
	 * @throws IOException  io异常
	 */
	public boolean updateSetting(String index) throws IOException {
		UpdateSettingsRequest request = new UpdateSettingsRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_replicas",REPLICAS)
				.put("index.refresh_interval",REFRESH_INTERVAL)
		);
		return client.indices().putSettings(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	public boolean updateSetting(String index,int replicas,String refresh) throws IOException {
		UpdateSettingsRequest request = new UpdateSettingsRequest(index);
		request.settings(Settings.builder()
				.put("index.number_of_replicas",replicas)
				.put("index.refresh_interval",refresh)
		);
		return client.indices().putSettings(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	public boolean updateSetting(String index, String setting) throws IOException {
		UpdateSettingsRequest request = new UpdateSettingsRequest(index);
		request.settings(setting,XContentType.JSON);
		return client.indices().putSettings(request, RequestOptions.DEFAULT).isAcknowledged();
	}

	/**
	 * @param index 	索引
	 * @return 	索引中的设置
	 * @throws IOException	io异常
	 */
	public String getSetting(String index) throws IOException {
		Objects.requireNonNull(index, "index");
		GetSettingsRequest request = new GetSettingsRequest().indices(index);
		return client.indices().getSettings(request,RequestOptions.DEFAULT).toString();
	}

	/**
	 * @param index	索引
	 * @return	索引中的映射
	 * @throws IOException	io异常
	 */
	public Map<String, Object> getMapping(String index) throws IOException {
		Objects.requireNonNull(index, "index");
		GetMappingsRequest request = new GetMappingsRequest().indices(index);

		Map<String,MappingMetaData> map =  client.indices().getMapping(request, RequestOptions.DEFAULT).mappings();
		if(map != null){
			return map.get(index).sourceAsMap();
		}
		return Collections.emptyMap();
	}

	/**
	 * @param index	索引
	 * @return	删除索引结果
	 * @throws IOException	io异常
	 */
	public boolean deleteIndex(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		if (isExists(index)) {
			return client.indices().delete(new DeleteIndexRequest(index),RequestOptions.DEFAULT).isAcknowledged();
		}
		return false;
	}

	/**
	 * @param index	索引
	 * @return	索引是否存在
	 * @throws IOException io异常
	 */
	public boolean isExists(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		GetIndexRequest request = new GetIndexRequest(index);
		return client.indices().exists(request, RequestOptions.DEFAULT);
	}

	public IndexEnity get(String index) throws IOException {
		Objects.requireNonNull(index, "index");
		GetIndexRequest request = new GetIndexRequest(index);

		GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
		Map<String,Settings> setting = response.getSettings();
		Map<String, Object> mapping = response.getMappings().get(index).getSourceAsMap();

		return new IndexEnity(index,setting.toString(),mapping);
	}

	public Set<String> getAllIndices() throws IOException {
		GetMappingsRequest request = new GetMappingsRequest();

		Map<String,MappingMetaData> map =  client.indices().getMapping(request, RequestOptions.DEFAULT).mappings();
		return map.keySet();
	}

	public void refresh(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		client.indices().refresh(refreshRequest(index),RequestOptions.DEFAULT);
	}

	public void clearCache(String field,String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(field, "field");
		ClearIndicesCacheRequest request = new ClearIndicesCacheRequest(index);
		request.indicesOptions(IndicesOptions.lenientExpandOpen());
		request.queryCache(true);
		request.fieldDataCache(true);
		request.requestCache(true);
		request.fields(field);

		client.indices().clearCache(request, RequestOptions.DEFAULT);

	}

	public void flush(String... index) throws IOException {
		Objects.requireNonNull(index, "index");
		FlushRequest request = new FlushRequest(index);
		request.indicesOptions(IndicesOptions.lenientExpandOpen());

		client.indices().flush(request, RequestOptions.DEFAULT);
	}

	/**
	 * @param index	索引
	 * @param aliasname	别名
	 * @return 	添加别名结果
	 * @throws IOException	io异常
	 */
	public Boolean addAlias(String index,String aliasname) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(aliasname, "aliasname");
		IndicesAliasesRequest.AliasActions aliasActions = IndicesAliasesRequest.AliasActions.add()
				.alias(aliasname)
				.index(index);
		return client.indices().updateAliases(new IndicesAliasesRequest().addAliasAction(aliasActions),
				RequestOptions.DEFAULT).isAcknowledged();

	}

	public XContentBuilder createAnalysis() throws IOException {
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject().startObject("settings")
				.startObject("analysis")
				.startObject("analyzer")
				.startObject("pinyin_analyzer").field("tokenizer","ik_smart")
				.field("filter","my_pinyin")
				.endObject()
				.startObject("onlyOne_analyzer").field( "tokenizer","onlyOne_pinyin")
				.endObject().endObject().startObject("tokenizer").startObject("onlyOne_pinyin")
				.field( "type","pinyin")
				.field("keep_separate_first_letter","true")
				.field("keep_full_pinyin","false")
				.endObject().endObject()
				.startObject("filter")
				.startObject("my_pinyin")
				.field( "type","pinyin")
				.field( "keep_first_letter",true)
				.field( "keep_separate_first_letter" ,true)
				.field( "keep_full_pinyin",true)
				.field("keep_original" ,false)
				.field("limit_first_letter_length" ,16)
				.field( "lowercase",true)
				.endObject().endObject().endObject().endObject().endObject();
		return builder;
	}

	public XContentBuilder createMapping(Object object) {
		Objects.requireNonNull(object, "object");
		List<Field> fieldList = getFields(object);
		XContentBuilder mapping = null;
		try {
			mapping = jsonBuilder().startObject().startObject("properties");
			for (Field field : fieldList) {
				if (Modifier.isStatic(field.getModifiers())){
					continue;
				}
				String name = field.getName();
				if ("string".equals(field.getType().getSimpleName().toLowerCase())) {
					mapping.startObject(name)
							.field("type", getElasticSearchMappingType(field.getType().getSimpleName().toLowerCase()))
							.field("analyzer", "pinyin_analyzer")
							.endObject();
				} else {
					mapping.startObject(name)
							.field("type", getElasticSearchMappingType(field.getType().getSimpleName().toLowerCase()))
							.endObject();
				}
			}
			mapping.endObject().endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapping;
	}

	/**
	 * 获取对象所有自定义的属性
	 * @param object -具体的对象
	 */
	private List<Field> getFields(Object object) {
		List<Field> fieldList = new ArrayList<>();
		Class objClass = object.getClass();
		while (null != objClass) {
			fieldList.addAll(Arrays.asList(objClass.getDeclaredFields()));
			objClass = objClass.getSuperclass();
		}
		return fieldList;
	}

	/**
	 * java类型与ElasticSearch类型映射
	 * @param varType -数据类型
	 */
	private String getElasticSearchMappingType(String varType) {
		String es;
		switch (varType) {
			case "date":
				es = "date";
				break;
			case "double":
				es = "double";
				break;
			case "long":
				es = "long";
				break;
			case "int":
				es = "integer";
				break;
			case "geometry"	:
				es = "geo_shape";
				break;
			default:
				es = "text";
				break;
		}
		return es;
	}


	/**
	 * @param templateName 索引模板
	 * @param source	模板资源
	 * @return	boolean
	 * @throws IOException io异常
	 */
	public boolean putIndexTemplate(String templateName, String source) throws IOException {
		PutIndexTemplateRequest request = new PutIndexTemplateRequest(templateName);
		request.source(source,XContentType.JSON);
		return client.indices().putTemplate(request,RequestOptions.DEFAULT).isAcknowledged();
	}
}

