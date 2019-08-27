package com.learn.common.elastic.query.builder.termlevel;

import com.learn.common.elastic.condition.TermLevelCondition;
import com.learn.common.elastic.query.builder.BaseSearchBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

/**
 * @author dshuyou
 * @create 2019/7/11
 *
 */
public class TermSearchBuilder extends BaseSearchBuilder {
	private String field;
	private String term;

	public TermSearchBuilder(TermLevelCondition condition) {
		super(condition);
		field = condition.getField();
		term = condition.getValue();

		if(field == null){
			throw new IllegalArgumentException("Illegal initial field: " + field);
		}
		if(term == null ){
			throw new IllegalArgumentException("Illegal initial term: " + term);
		}
	}

	@Override
	public SearchSourceBuilder builder() {
		TermQueryBuilder termQueryBuilder = new TermQueryBuilder(field,term);
		/*BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		boolBuilder.must(termQueryBuilder);*/

		sourceBuilder.query(termQueryBuilder);
		return sourceBuilder;
	}
}
