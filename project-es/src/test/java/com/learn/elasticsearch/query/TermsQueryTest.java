package com.learn.elasticsearch.query;

import com.learn.elasticsearch.EsClientInit;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;
import com.learn.elasticsearch.query.model.DataContent;
import com.learn.elasticsearch.query.query_enum.TermsEnum;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Date 2019/8/28 11:11
 * @Created by dshuyou
 */
public class TermsQueryTest {

	private RestHighLevelClient client;
	private TermsQuery termsQuery;
	@Before
	public void setUp(){
		client = EsClientInit.getInstance().getClient();
		String index = "dshuyou1";
		termsQuery = new TermsQuery(index,client, TermsEnum.rangeQuery);
	}

	@Test
	public void executeQuery() throws IOException {

		long start = System.currentTimeMillis();
		/*for (int i = 0;i<100;i++) {
			query();
		}*/
		query();
		long end = System.currentTimeMillis();
		//System.out.println((end-start)/100 + "ms");
		System.out.println((end-start) + "ms");
	}

	public void query() throws IOException {
		TermsLevelCondition condition = TermsLevelCondition.fromTerms(0,10,"ids",new String[]{"100","1000"});
		//condition.setValue("我");
		DataContent dataContent = termsQuery.executeQuery(condition);
		System.out.println(dataContent);
	}


}