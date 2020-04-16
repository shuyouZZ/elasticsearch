package com.learn.elasticsearch;

import com.learn.elasticsearch.model.SourceEntity;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @date 2019/8/21 10:03
 * @author dshuyou
 */
public class Document {
	private static final Logger logger = LoggerFactory.getLogger(Document.class);
	private static final int COUNT = 10000;
	private static final int BULK_SIZE = 20;
	private static final int FLUSH_INTERVAL = 5;
	private RestHighLevelClient client;

	private Document(RestHighLevelClient client){
		this.client = client;
	}

	public static Document getInstance(RestHighLevelClient client){
		return new Document(client);
	}

	/**
	 * @param index	索引名称
	 * @param id	索引id
	 * @param source	索引内容
	 * @return	索引结果
	 * @throws IOException	io异常
	 */
	public<T> boolean index(String index, String id, T source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");
		if(id == null){
			return index(index, source);
		}
		IndexRequest request = new IndexRequest(index).id(id);
		setIndexRequest(request,source);

		IndexResponse response = client.index(request,RequestOptions.DEFAULT);
		return response.status() == RestStatus.CREATED;
	}

	/**
	 * @param index	索引名称
	 * @param source	索引内容
	 * @return	索引结果
	 * @throws IOException	io异常
	 */
	public<T> boolean index(String index,T source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(source, "source");
		IndexRequest request = new IndexRequest(index);
		setIndexRequest(request,source);

		IndexResponse response = client.index(request,RequestOptions.DEFAULT);
		return response.status() == RestStatus.CREATED ;
	}

	/**
	 * @param index	索引名称
	 * @param id	索引id
	 * @param source	索引内容
	 */
	public void insertAsync(String index, String id, String source) {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(source, "source");
		IndexRequest request = new IndexRequest(index).id(id);
		request.source(source, XContentType.JSON);
		ActionListener listener = new ActionListener<IndexResponse>() {
			@Override
			public void onResponse(IndexResponse indexResponse) {
				//System.out.println(indexResponse.status().getStatus() == RestStatus.OK.getStatus());
				logger.info("Async index status: " + indexResponse.status());
			}

			@Override
			public void onFailure(Exception e) {
				logger.info("Async index failure");
			}
		};
		client.indexAsync(request, RequestOptions.DEFAULT, listener);
	}

	/**
	 * @param index	索引名称
	 * @param id	索引id
	 * @return	删除指定索引结果
	 * @throws IOException	io异常
	 */
	public boolean delete(String index, String id) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		DeleteRequest request = new DeleteRequest(index,id);

		DeleteResponse response = client.delete(request,RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK ;
	}

	/**
	 * @param index	索引名称
	 * @return	指定索引的数量
	 * @throws IOException	io异常
	 */
	public long count(String index) throws IOException {
		Objects.requireNonNull(index, "index");
		CountRequest request = new CountRequest(index);

		CountResponse response = client.count(request,RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK ? response.getCount() :0;
	}

	/**
	 * @param index	索引名称
	 * @param id	索引id
	 * @return	判定给定id的索引是否存在
	 */
	public boolean isIdExists(String index,String id) {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		try {
			GetRequest getRequest = new GetRequest(index,id);
			getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
			return client.exists(getRequest, RequestOptions.DEFAULT);
		}catch (Exception e){
			return false;
		}
	}

	/**
	 * @param index 	索引名称
	 * @param id 	索引id
	 * @return 	指定id的索引内容
	 * @throws IOException	io异常
	 */
	public String get(String index, String id) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		GetRequest request = new GetRequest(index,id);
		GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
		if (!getResponse.isExists() || getResponse.isSourceEmpty()) {
			return null;
		}
		return getResponse.getSourceAsString();
	}

	/**
	 * @param index	索引名称
	 * @param fields 	索引字段集合
	 * @return	索引集合
	 * @throws IOException	io异常
	 */
	public List<Object> getFieldsValues(String index, String[] fields) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(fields, "fields");
		GetRequest request = new GetRequest(index);
		request.storedFields(fields);
		List<Object> list = new ArrayList<>();

		GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);
		if (Objects.requireNonNull(getResponse).isExists() && !getResponse.isSourceEmpty()) {
			for (String field : fields) {
				list.add(getResponse.getField(field).getValues());
			}
		}
		return list;
	}

	/**
	 * @param index	索引名称
	 * @param ids	索引id集合
	 * @return	索引列表
	 * @throws IOException	io异常
	 */
	public List<Map<String,Object>> multiGet(String index, String[] ids) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(ids, "ids");
		MultiGetRequest request = new MultiGetRequest();
		for (String id:ids){
			request.add(new MultiGetRequest.Item(index,id));
		}
		MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
		List<Map<String, Object>> list = new LinkedList<>();
		for (int i = 0;i<response.getResponses().length;i++){
			MultiGetItemResponse firstItem = response.getResponses()[i];
			GetResponse getResponse = firstItem.getResponse();
			if (getResponse.isExists()) {
				Map<String,Object> sourceAsMap = getResponse.getSourceAsMap();
				list.add(sourceAsMap);
			}
		}
		return list;
	}

	/**
	 * @param index	索引名称
	 * @param id 	索引id
	 * @param source	索引内容
	 * @return	boolean
	 * @throws IOException 	io异常
	 */
	public<T> boolean update(String index, String id, T source) throws IOException {
		Objects.requireNonNull(index, "index");
		Objects.requireNonNull(id, "id");
		Objects.requireNonNull(source, "source");
		UpdateRequest request = new UpdateRequest(index, id).doc(source);
		setUpdateRequest(request,source);

		UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
		return response.status() == RestStatus.OK;
	}

	/**
	 * @param index	索引名称
	 * @param sources	索引内容
	 * @return	索引数量
	 * @throws IOException	io异常
	 */
	public long bulkIndex(String index, List<SourceEntity> sources) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<IndexRequest> requests = new ArrayList<>();
		int count = 0;
		for (SourceEntity source : sources) {
			IndexRequest indexRequest = new IndexRequest(index).id(source.getId());

			setIndexRequest(indexRequest, source.getSource());
			requests.add(indexRequest);
			if (requests.size() % COUNT == 0 ) {
				for (IndexRequest request : requests) {
					bulkRequest.add(request);
				}
				BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
				if (responses.status() == RestStatus.OK) {
					count = count + COUNT;
				}
				bulkRequest.requests().clear();
				requests.clear();
			}
		}
		for (IndexRequest request : requests) {
			bulkRequest.add(request);
		}
		BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		if(responses.status() == RestStatus.OK){
			count = count + requests.size();
		}
		return count;
	}

	/**
	 * @param index 	索引名称
	 * @param sources	索引更新的内容
	 * @return	更新数量
	 * @throws IOException	io异常
	 */
	public long bulkUpdate(String index, List<SourceEntity> sources) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<UpdateRequest> requests = new ArrayList<>();
		int count = 0;
		for (SourceEntity update : sources) {
			UpdateRequest updateRequest = new UpdateRequest(index, update.getId());

			setUpdateRequest(updateRequest, update.getSource());
			requests.add(updateRequest);

			if (requests.size() % COUNT == 0) {
				for (UpdateRequest request : requests) {
					bulkRequest.add(request);
				}
				BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
				if (responses.status() == RestStatus.OK) {
					count = count + COUNT;
				}
				bulkRequest.requests().clear();
				requests.clear();
			}
		}
		for (UpdateRequest request : requests) {
			bulkRequest.add(request);
		}
		BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		if(responses.status() == RestStatus.OK){
			count = count + requests.size();
		}
		return count;
	}

	/**
	 * @param index	索引名称
	 * @param sources	索引删除的内容
	 * @return	删除数量
	 * @throws IOException	io异常
	 */
	public long bulkDelete(String index, List<SourceEntity> sources) throws IOException {
		BulkRequest bulkRequest = new BulkRequest();
		List<DeleteRequest> requests = new ArrayList<>();
		int count = 0;
		for (SourceEntity delete : sources) {
			DeleteRequest deleteRequest = new DeleteRequest(index).id(delete.getId());
			requests.add(deleteRequest);
			if (requests.size() % COUNT == 0) {
				for (DeleteRequest request : requests) {
					bulkRequest.add(request);
				}
				BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
				if (responses.status() == RestStatus.OK) {
					count = count + COUNT;
				}
				bulkRequest.requests().clear();
				requests.clear();
			}
		}
		for (DeleteRequest request : requests) {
			bulkRequest.add(request);
		}
		BulkResponse responses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		if(responses.status() == RestStatus.OK){
			count = count + requests.size();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	private<T> void setIndexRequest(IndexRequest indexRequest,T source){
		if(source instanceof String){
			indexRequest.source(String.valueOf(source), XContentType.JSON);
		}else if(source instanceof Map) {
			indexRequest.source((Map) source, XContentType.JSON);
		}else if(source instanceof XContentBuilder){
			indexRequest.source((XContentBuilder)source);
		}
	}

	@SuppressWarnings("unchecked")
	private<T> void setUpdateRequest(UpdateRequest updateRequest,T source){
		if (source instanceof String) {
			updateRequest.doc(String.valueOf(source),XContentType.JSON);
		} else if (source instanceof Map) {
			updateRequest.doc((Map) source,XContentType.JSON);
		} else if (source instanceof XContentBuilder) {
			updateRequest.doc(XContentType.JSON,source);
		}
	}

	/**
	 * use bulk processor to Asynchronous batch indexing
	 */
	public void asycBulkIndex(String index, List<SourceEntity> queries) throws InterruptedException {
		BulkProcessor bulkProcessor = buldBulkProcessor();
		for (SourceEntity source : queries) {
			IndexRequest indexRequest = new IndexRequest(index).id(source.getId());
			setIndexRequest(indexRequest,source.getSource());
			bulkProcessor.add(indexRequest);
		}
		bulkProcessor.flush();
		bulkProcessor.awaitClose(10, TimeUnit.SECONDS);
	}

	/**
	 * use bulk processor to Asynchronous batch update
	 */
	public void asycBulkUpdate(String index, List<SourceEntity> queries) throws InterruptedException {
		BulkProcessor bulkProcessor = buldBulkProcessor();
		for (SourceEntity source : queries) {
			UpdateRequest request = new UpdateRequest(index,source.getId());
			setUpdateRequest(request,source.getSource());
			bulkProcessor.add(request);
		}
		bulkProcessor.flush();
		bulkProcessor.awaitClose(10, TimeUnit.SECONDS);
	}

	/**
	 * use bulk processor to Asynchronous batch delete
	 */
	public void asycBulkDelete(String index, List<SourceEntity> queries) throws InterruptedException {
		BulkProcessor bulkProcessor = buldBulkProcessor();
		for (SourceEntity source : queries) {
			IndexRequest indexRequest = new IndexRequest(index).id(source.getId());
			setIndexRequest(indexRequest,source.getSource());
			bulkProcessor.add(indexRequest);
		}
		bulkProcessor.flush();
		bulkProcessor.awaitClose(10, TimeUnit.SECONDS);
	}

	/**
	 * buld a custom asyc bulk processor
	 */
	private BulkProcessor buldBulkProcessor(){
		BulkProcessor.Listener listener = new BulkProcessor.Listener() {
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
				int numberOfActions = request.numberOfActions();
				logger.debug("Executing bulk:"+ executionId + " with "+ numberOfActions + " requests");
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request,
								  BulkResponse response) {
				if (response.hasFailures()) {
					logger.error("Executing bulk:"+ executionId + " failure");
				} else {
					logger.debug("Executing bulk:"+ executionId +
							"	completed in " + response.getTook().getMillis()+
							" milliseconds");
				}
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request,
								  Throwable failure) {
				logger.error("Failed to execute bulk executionId :{} , bulkRequest failure Num: {}, failureInfo: {}",
						executionId, request.numberOfActions(),failure.getCause());
			}
		};
		return BulkProcessor.builder(
				(request, bulkListener) ->
						client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), listener)
				.setBulkActions(COUNT)
				.setBulkSize(new ByteSizeValue(BULK_SIZE, ByteSizeUnit.MB))
				.setFlushInterval(TimeValue.timeValueSeconds(FLUSH_INTERVAL))
				.setConcurrentRequests(12)// threadpool.index.size: 12 (max = (core cpu * 3) /2 + 1)
				.setBackoffPolicy(
						BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
				.build();
	}

	/**
	 * Deletes all data in the index
	 */
	public void deleteData(String index){
		DeleteByQueryRequest request = new DeleteByQueryRequest(index).setQuery(new MatchAllQueryBuilder());
		client.deleteByQueryAsync(request, RequestOptions.DEFAULT, new ActionListener<BulkByScrollResponse>() {
			@Override
			public void onResponse(BulkByScrollResponse bulkByScrollResponse) {
				long total = bulkByScrollResponse.getTotal();
				List<BulkItemResponse.Failure> bulkFailures = bulkByScrollResponse.getBulkFailures();
				long searchRetries = bulkByScrollResponse.getSearchRetries();
				logger.debug("delete total: {},bulkFailures: {},searchRetries: {}",total,bulkFailures,searchRetries);
			}
			@Override
			public void onFailure(Exception e) {
				logger.error("Failed to delete data :" + e.getMessage());
			}
		});
	}

}


