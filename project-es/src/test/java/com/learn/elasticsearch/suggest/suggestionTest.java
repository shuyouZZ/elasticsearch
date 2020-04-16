package com.learn.elasticsearch.suggest;

import com.learn.elasticsearch.EsClientInit;
import com.learn.elasticsearch.model.SuggestEnity;
import com.learn.elasticsearch.query.FulltextQuery;
import com.learn.elasticsearch.query.TermsQuery;
import com.learn.elasticsearch.query.condition.FullTextCondition;
import com.learn.elasticsearch.query.condition.TermsLevelCondition;
import com.learn.elasticsearch.query.query_enum.TermsEnum;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author dshuyou
 * @Date 2019/9/24 17:55
 */
public class suggestionTest {
	private Suggestion suggestion;
	private String index;
	private RestHighLevelClient client;
	@Before
	public void init(){
		client = EsClientInit.getInstance().getClient();
		suggestion = new Suggestion(client);
		index = "dshuyou2";
	}

	@Test
	public void suggest() {
		String[] field = new String[]{"content.content_fullpinyin", "content.content_prefixpinyin", "content.content_text"};
		String keyword = "像我";
		int size = 10;
		List<String> list = null;
		try {
			list = suggestion.suggest(keyword, size, field, index);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
		for (String s : list) {
			System.out.println(s);
		}

	}
}