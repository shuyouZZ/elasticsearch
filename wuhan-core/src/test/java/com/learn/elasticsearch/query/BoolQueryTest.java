package com.learn.elasticsearch.query;

import com.learn.elasticsearch.EsClientInit;
import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.Filters;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.phrase.PhraseSuggestionBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * @Date 2019/8/28 11:10
 * @Created by dshuyou
 */
public class BoolQueryTest {

	private RestHighLevelClient client;
	private String index;
	@Before
	public void setUp(){
		client = EsClientInit.getInstance().getClient();
		index = "dshuyou1";
	}

	@Test
	public void executeBoolQuery() throws IOException {
		BoolQuery boolQuery = new BoolQuery(index,client);
		Map<String, BaseCondition> map = new HashMap<>();
		map.put("matchPhraseQuery",FullTextCondition.match(0,10,"content","wo"));
		TermsLevelCondition condition = TermsLevelCondition.fromTerm("username","as");
		map.put("termQuery",condition);
		long start = System.currentTimeMillis();
		/*List<String> list = boolQuery.executeQuery(map);
		long end = System.currentTimeMillis();
		System.out.println((end-start)/100 + "ms");
		for (String s :list){
			System.out.println(s);
		}
		System.out.println(list.size());*/
	}

	@Test
	public void suggestion(){
		PhraseSuggestionBuilder builder = SuggestBuilders.phraseSuggestion("content");

	}

	@Test
	public void aggregation() throws IOException {
		AggregationBuilder aggregation =
				AggregationBuilders
						.filters("agg",
								new FiltersAggregator.KeyedFilter("content", QueryBuilders.termQuery("content", "xw")),
								new FiltersAggregator.KeyedFilter("username", QueryBuilders.termQuery("username", "wo")));
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.aggregation(aggregation).from(0).size(100);
		SearchRequest request = new SearchRequest(index);
		request.source(builder);
		SearchResponse response = client.search(request,RequestOptions.DEFAULT);
		SearchHit[] hits = response.getHits().getHits();

		List<String> list = new ArrayList<>();
		for (SearchHit searchHit : hits) {
			list.add(searchHit.getSourceAsString());
			System.out.println(searchHit.getSourceAsString());
		}
		System.out.println(list.size());
		Filters agg = response.getAggregations().get("agg");
// For each entry
		for (Filters.Bucket entry : agg.getBuckets()) {
			String key = entry.getKeyAsString();            // bucket key
			long docCount = entry.getDocCount();            // Doc count
			System.out.println(key);
			System.out.println(docCount);
		}
	}
}