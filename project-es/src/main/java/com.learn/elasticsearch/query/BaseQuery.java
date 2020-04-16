package com.learn.elasticsearch.query;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.learn.elasticsearch.query.condition.*;
import com.learn.elasticsearch.query.model.DataContent;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This class is the abstract class for query action.
 *
 * @date 2019/8/21 10:43
 * @author dshuyou
 * @see BoolQuery
 * @see FulltextQuery
 * @see GeoQuery
 * @see TermsQuery
 */
public abstract class BaseQuery {
	protected String index;
	protected RestHighLevelClient client;
	protected SearchSourceBuilder sourceBuilder;
	protected final int FROM = 0;
	protected final int SIZE = 100;


	protected final int threadNum;
	protected final ThreadPoolExecutor queryWorkersPool;
	protected final LinkedTransferQueue<String> resultQueue;


	public BaseQuery(){
		threadNum = 0;
		queryWorkersPool = null;
		resultQueue = null;
	}

	public BaseQuery(String index, RestHighLevelClient client) {
		this.index = index;
		this.client = client;
		this.sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.from(FROM).size(SIZE);


		threadNum = Runtime.getRuntime().availableProcessors() + 1;
		ThreadFactory threadFactory = new ThreadFactoryBuilder()
				.setDaemon(true).setNameFormat("internal-pol-%d").build();
		queryWorkersPool =
				(ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum, threadFactory);
		queryWorkersPool.prestartAllCoreThreads();
		resultQueue = new LinkedTransferQueue<>();
	}

	public abstract DataContent executeQuery(BaseCondition baseCondition) throws IOException;

	public abstract QueryBuilder queryBuilder(BaseCondition baseCondition) throws IOException;

	DataContent returnList(SearchSourceBuilder sourceBuilder, BaseCondition baseCondition) throws IOException {
		if(baseCondition.getFrom() != FROM){
			sourceBuilder.from(baseCondition.getFrom());
		}
		if(baseCondition.getSize() != SIZE){
			sourceBuilder.size(baseCondition.getSize());
		}

		//if(hasSort){sourceBuilder.sort("_score", SortOrder.DESC);}
		String sortField = baseCondition.getSortField();
		if(sortField == null||sortField.isEmpty()){
			sourceBuilder.sort(SortBuilders.scoreSort());
		}else {
			sourceBuilder.sort(sortField, SortOrder.DESC);
		}

		//选择是否高亮
		boolean hasHighlight = baseCondition.isHasHighlight();
		HighlightBuilder highlightBuilder = null;
		if(hasHighlight){
			if(baseCondition instanceof FullTextCondition){
				FullTextCondition condition = (FullTextCondition) baseCondition;
				String field = condition.getField();
				if(!field.isEmpty()){
					highlightBuilder = new HighlightBuilder().field(field);
				}else {
					String[] fields = condition.getFields();
					highlightBuilder = new HighlightBuilder();
					for (String s : fields){
						highlightBuilder.field(s);
					}
				}
			}else if(baseCondition instanceof GeoCondition){
				GeoCondition condition = (GeoCondition) baseCondition;
				highlightBuilder = new HighlightBuilder().field(condition.getField());
			}else if(baseCondition instanceof TermsLevelCondition){
				TermsLevelCondition condition = (TermsLevelCondition) baseCondition;
				highlightBuilder = new HighlightBuilder().field(condition.getField());
			}
		}
		if(highlightBuilder != null){
			sourceBuilder.highlighter(highlightBuilder);
		}
		//index : String.split("",),前端传参形式（a,b,c,d）
		SearchRequest searchRequest = new SearchRequest(index.split(","));
		searchRequest.source(sourceBuilder);

		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		long count = response.getHits().getTotalHits().value;
		SearchHit[] hits = response.getHits().getHits();

		List<String> list = new ArrayList<>();
		for (SearchHit searchHit : hits) {
			list.add(searchHit.getSourceAsString());
		}
		return new DataContent(list,count);
	}

}

