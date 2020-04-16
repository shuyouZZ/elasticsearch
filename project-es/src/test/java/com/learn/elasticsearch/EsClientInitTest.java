package com.learn.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Date 2019/8/28 11:11
 * @Created by dshuyou
 */
public class EsClientInitTest {

	private EsClientInit esClientInit;
	private RestHighLevelClient client;
	@Before
	public void init(){
		esClientInit = EsClientInit.getInstance();
	}

	@Test
	public void highLevelClient() {
		client = esClientInit.getClient();
	}


	@Test
	public void close() {
		client = esClientInit.getClient();
		boolean status = esClientInit.close(client);
		System.out.println(status);
	}
}