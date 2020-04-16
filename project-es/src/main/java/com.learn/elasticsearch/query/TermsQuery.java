package com.learn.elasticsearch.query;

import com.learn.elasticsearch.query.condition.BaseCondition;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;
import com.learn.elasticsearch.query.model.DataContent;
import com.learn.elasticsearch.query.query_enum.TermsEnum;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;

import java.io.IOException;
import java.util.Collections;

/**
 * @date 2019/8/21 10:05
 * @author dshuyou
 */
public class TermsQuery extends BaseQuery{
	private TermsEnum queryType;

	public TermsQuery(String index, RestHighLevelClient client, TermsEnum type){
		super(index, client);
		this.queryType = type;
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
		if (!(baseCondition instanceof TermsLevelCondition)) {
			throw new IllegalArgumentException("term level query need term level query condition");
		}

		TermsLevelCondition condition = (TermsLevelCondition) baseCondition;
		QueryBuilder queryBuilder;
		switch (this.queryType){
			case prefixQuery:
				queryBuilder = prefixBuilder(condition);
				break;
			case rangeQuery:
				queryBuilder = rangeBuilder(condition);
				break;
			case fuzzyQuery:
				queryBuilder = fuzzyBuilder(condition);
				break;
			case termQuery:
				queryBuilder = termBuilder(condition);
				break;
			case termsQuery:
				queryBuilder = termsBuilder(condition);
				break;
			case idsQuery:
				queryBuilder = idsBuilder(condition);
				break;
			default:
				throw new IllegalArgumentException("not supported term level search type");
		}
		return queryBuilder;
	}

	private QueryBuilder fuzzyBuilder(TermsLevelCondition condition){
		String field = condition.getField();
		String value = condition.getValue();

		return new FuzzyQueryBuilder(field, value).maxExpansions(10);
	}

	private QueryBuilder prefixBuilder(TermsLevelCondition condition){
		String field = condition.getField();
		String value = condition.getValue();

		return new PrefixQueryBuilder(field, value);
	}

	private QueryBuilder rangeBuilder(TermsLevelCondition condition){
		String field = condition.getField();
		String gte = condition.getGte();
		String lte = condition.getLte();
		RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(field);
		rangeQueryBuilder.gte(gte);
		rangeQueryBuilder.lte(lte);

		return rangeQueryBuilder;
	}

	private QueryBuilder termBuilder(TermsLevelCondition condition){
		String field = condition.getField();
		String value = condition.getValue();

		return new TermQueryBuilder(field,value);
	}

	private QueryBuilder termsBuilder(TermsLevelCondition condition){
		String field = condition.getField();
		String[] values = condition.getValues();

		return new TermsQueryBuilder(field,values);
	}

	private QueryBuilder idsBuilder(TermsLevelCondition condition){
		String[] ids = condition.getIds();

		return new IdsQueryBuilder().addIds(ids);
	}
}
