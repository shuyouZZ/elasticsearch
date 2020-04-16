package com.learn.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @date 2019/8/26 11:22
 * @author dshuyou
 */
public class EsClientInit {

	private static final String[] IP_ADDRESS = new String[]{
			""
	};

	private static final int ADDRESS_LENGTH = 2;
	private static final String HTTP_SCHEME = "http";
	private boolean closeStatus = true;
	private RestHighLevelClient client;

	private EsClientInit(){
		client = highLevelClient();
	}

	public static EsClientInit getInstance(){
		return SingletonHolder.INSTANCE;
	}

	private static class SingletonHolder{
		private static final EsClientInit INSTANCE = new EsClientInit();
	}

	public RestHighLevelClient getClient() {
		return client;
	}

	public boolean close(RestHighLevelClient client){
		if(client != null){
			try {
				client.close();
			} catch (IOException e) {
				closeStatus = false;
			}
		}else {
			closeStatus = false;
		}
		return closeStatus;
	}

	private RestClientBuilder restClientBuilder() {
		HttpHost[] hosts = Arrays.stream(IP_ADDRESS)
				.map(this::makeHttpHost)
				.filter(Objects::nonNull)
				.toArray(HttpHost[]::new);
		return RestClient.builder(hosts);
	}

	private RestHighLevelClient highLevelClient() {
		return new RestHighLevelClient(restClientBuilder());
	}

	private HttpHost makeHttpHost(String s) {
		Objects.requireNonNull(s);
		String[] address = s.split(":");
		if (address.length == ADDRESS_LENGTH) {
			String ip = address[0];
			int port = Integer.parseInt(address[1]);
			return new HttpHost(ip, port, HTTP_SCHEME);
		} else {
			return null;
		}
	}
}
