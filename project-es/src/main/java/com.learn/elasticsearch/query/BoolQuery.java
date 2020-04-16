package com.learn.elasticsearch.query;

import com.learn.elasticsearch.query.condition.*;
import com.learn.elasticsearch.query.model.DataContent;
import com.learn.elasticsearch.query.query_enum.FulltextEnum;
import com.learn.elasticsearch.query.query_enum.GeoEnum;
import com.learn.elasticsearch.query.query_enum.TermsEnum;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.io.IOException;
import java.util.*;

/**
 * @date 2019/8/22 16:50
 * @author dshuyou
 */
public class BoolQuery extends BaseQuery{

	public BoolQuery(String index,RestHighLevelClient client){
		super(index,client);
	}

	@Override
	public DataContent executeQuery(BaseCondition baseCondition) throws IOException {
		sourceBuilder.query(queryBuilder(baseCondition));
		if(sourceBuilder == null){
			return new DataContent(Collections.emptyList(),0);
		}
		return returnList(sourceBuilder,baseCondition);
	}

	@Override
	public QueryBuilder queryBuilder(BaseCondition baseCondition) throws IOException {
		if (!(baseCondition instanceof BoolCondition)) {
			throw new IllegalArgumentException("Bool query need bool query condition");
		}

		BoolCondition condition = (BoolCondition) baseCondition;
		Map<String, BaseCondition> conditions = condition.setConditions();
		QueryBuilder queryBuilder = null;
		List<QueryBuilder> queryBuilders = new ArrayList<>();

		for (Map.Entry<String, BaseCondition> entry : conditions.entrySet()) {
			String type = entry.getKey();
			if (entry.getValue() instanceof FullTextCondition) {
				queryBuilder = new FulltextQuery(index, client, FulltextEnum.valueOf(type))
						.queryBuilder(entry.getValue());
			} else if (entry.getValue() instanceof TermsLevelCondition) {
				queryBuilder = new TermsQuery(index, client, TermsEnum.valueOf(type))
						.queryBuilder(entry.getValue());
			} else if (entry.getValue() instanceof GeoCondition) {
				queryBuilder = new GeoQuery(index, client, GeoEnum.valueOf(type))
						.queryBuilder(entry.getValue());
			}
			if (queryBuilder != null) {
				queryBuilders.add(queryBuilder);
			}
		}

		BoolQueryBuilder builder = new BoolQueryBuilder();
		for (QueryBuilder q : queryBuilders) {
			builder.must(q);
		}
		return builder;
	}
}
