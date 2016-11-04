package com.stock.connection;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import com.stock.util.CommonsUtil;

public class HttpClientUtil {
	
	private static Logger log = Logger.getLogger(HttpClientUtil.class);
	public static boolean hasException = false;
	

	public static HttpEntity get(String url) {
		hasException = false;
		HttpEntity entity = null;
		try {
			HttpClient client = HttpClients.createDefault();
			HttpGet get = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();//设置请求和传输超时时间
			get.setConfig(requestConfig);
			HttpResponse response = client.execute(get);
			entity = response.getEntity();
		} catch (Exception e) {
			hasException = true;
			log.info("http请求失败");
			log.info(CommonsUtil.join(e.getStackTrace(), ",\r\n"));
			return null;
		}
		return entity;
	}
	
	public static void get1(String url) throws Exception {
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		System.out.println(entity.getContentEncoding());
		System.out.println(entity.getContentType());
		if (entity != null) {
			InputStream in = entity.getContent();
			IOUtils.copy(in, System.out);
		}
	}

	public static void main(String[] args) {
		try {
			get("http://quotes.money.163.com/hs/service/diyrank.php?host=http://quotes.money.163.com/hs/service/diyrank.php&count=24&"
					+ "field=SNAME,CODE,"
					+ "ANNOUNMT,UVSNEWS&order=desc&page=0&count=2880"
					+ "&query=STYPE:EQA;EXCHANGE:CNSESH&sort=PERCENT&type=query");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
