package com.learn.elasticsearch.aggregate;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @author dshuyou
 * @date 2019/9/25 11:44
 */
public class Aggregation {
	private RestHighLevelClient client;

	public Aggregation(RestHighLevelClient client){this.client = client;}

	public String aggregate(String[] field) throws IOException {
		AggregationBuilder aggregationBuilder = AggregationBuilders.max("max_XXX").field("")
				.subAggregation(AggregationBuilders.avg("avg_XXX").field(""))
				.subAggregation(AggregationBuilders.count("count_XXX").field(""));
		//根据用户需求在service中自己实现aggregation

		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		sourceBuilder.aggregation(aggregationBuilder);

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.source(sourceBuilder);
		SearchResponse aggregateResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		return aggregateResponse.getAggregations().get("max_XXX");
	}
}
