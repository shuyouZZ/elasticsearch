package com.learn.elasticsearch.query;

import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.model.DataContent;
import com.learn.elasticsearch.query.query_enum.FulltextEnum;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2019/8/21 10:04
 * @author dshuyou
 */
public class FulltextQuery extends BaseQuery{
	private FulltextEnum queryType = FulltextEnum.matchAllQuery;

	public FulltextQuery(String index, RestHighLevelClient client){
		super(index,client);
	}

	public FulltextQuery(String index, RestHighLevelClient client, FulltextEnum type){
		super(index, client);
		if(type != null){
			queryType = type;
		}
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
	public QueryBuilder queryBuilder(BaseCondition baseCondition){
		if (!(baseCondition instanceof FullTextCondition)) {
			throw new IllegalArgumentException("fulltext query need fulltext query condition");
		}

		FullTextCondition condition = (FullTextCondition) baseCondition;
		QueryBuilder queryBuilder;
		if(this.queryType == null){
			return matchAllBuilder();
		}
		switch (this.queryType){
			case matchAllQuery :
				queryBuilder = matchAllBuilder();
				break;
			case matchQuery:
				queryBuilder = matchBuilder(condition);
				break;
			case matchPhraseQuery:
				queryBuilder = matchPhraseBuilder(condition);
				break;
			case matchPhrasePrefixQuery:
				queryBuilder = matchPhrasePrefixBuilder(condition);
				break;
			case queryString:
				queryBuilder = queryStringBuilder(condition);
				break;
			default:
				throw new IllegalArgumentException("not supported FullText search type");
		}
		return queryBuilder;
	}

	private QueryBuilder matchAllBuilder(){
		return new MatchAllQueryBuilder();
	}

	private QueryBuilder matchBuilder(FullTextCondition condition){
		String field = condition.getField();
		String value = condition.getValue();

		return new MatchQueryBuilder(field,value);
	}

	private QueryBuilder matchPhraseBuilder(FullTextCondition condition){
		String field = condition.getField();
		String value = condition.getValue();

		return new MatchPhraseQueryBuilder(field,value);
	}

	private QueryBuilder matchPhrasePrefixBuilder(FullTextCondition condition){
		String field = condition.getField();
		String value = condition.getValue();
		MatchPhrasePrefixQueryBuilder builder = new MatchPhrasePrefixQueryBuilder(field,value);
		builder.maxExpansions(10);

		return builder;
	}

	private QueryBuilder queryStringBuilder(FullTextCondition condition){
		String value = condition.getValue();
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(value);
		String field = condition.getField();
		if(field != null){
		    return builder.field(field);
        }
        String[] fileds = condition.getFields();
		Map<String, Float> map = new HashMap<>();
		for (String s : fileds){
			map.put(s,1.0f);
		}
		return builder.fields(map);
	}
}
