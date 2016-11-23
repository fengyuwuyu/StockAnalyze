package com.stock.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.connection.HttpClientUtil;
import com.stock.dao.HolidayMapper;
import com.stock.dao.StockDetailMapper;
import com.stock.dao.StockMainMapper;
import com.stock.model.FBVolume;
import com.stock.model.StockAnalyseBase;
import com.stock.model.StockBuySell;
import com.stock.model.StockConstant;
import com.stock.service.InitStockServiceI;
import com.stock.util.CommonsUtil;
import com.stock.util.MapUtils;
import com.stock.util.StockCache;
import com.stock.util.ThreadPool;

@Service
public class InitStockServiceImpl implements InitStockServiceI {
	private static ObjectMapper mapper = new ObjectMapper();
	private StockDetailMapper stockDetailMapper;
	private StockMainMapper stockMainMapper;
	private HolidayMapper holidayMapper;
	private String timeBak = "";
	private Logger log = Logger.getLogger(InitStockServiceImpl.class);
	private DataSourceTransactionManager transactionManager;
	
	@Autowired
	public void setTransactionManager(DataSourceTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	@Autowired
	public void setStockDetailMapper(StockDetailMapper stockDetailMapper) {
		this.stockDetailMapper = stockDetailMapper;
	}

	@Autowired
	public void setStockMainMapper(StockMainMapper stockMainMapper) {
		this.stockMainMapper = stockMainMapper;
	}
	
	@Autowired
	public void setHolidayMapper(HolidayMapper holidayMapper) {
		this.holidayMapper = holidayMapper;
	}

	/**
	 * 每30秒执行一次 用于获取股票的详细信息
	 * http://quotes.money.163.com/hs/service/marketradar_ajax
	 * .php?host=http%3A%2F
	 * %2Fquotes.money.163.com%2Fhs%2Fservice%2Fmarketradar_ajax
	 * .php&page=0&query=STYPE%3AEQA&types=&count=28&type=query&order=desc
	 */
	@Override
	public Map<String, Object> initStock() {
		log.info("下载股票数据，每分钟执行一次。。。"
				+ (CommonsUtil.formatDateToString3(new Date())));
		String url = "http://quotes.money.163.com/hs/service/diyrank.php?"
				+ "host=http%3A%2F%2Fquotes.money.163.com%2Fhs%2Fservice%2Fdiyrank.php&"
				+ "page=0&query=STYPE%3AEQA&fields=NO%2CSYMBOL%2CNAME%2CPRICE%2CPERCENT%2CUPDOWN%2CFIVE_MINUTE%2COPEN%"
				+ "2CYESTCLOSE%2CHIGH%2CLOW%2CVOLUME%2CTURNOVER%2CHS%2CLB%2CWB%2CZF%2CPE%2CMCAP%2CTCAP%2CMFSUM%"
				+ "2CMFRATIO.MFRATIO2%2CMFRATIO.MFRATIO10%2CSNAME%2CCODE%2CANNOUNMT%2CUVSNEWS&s"
				+ "ort=PERCENT&order=desc&count=10000&type=query";
		try {
			HttpEntity entity = HttpClientUtil.get(url);
			while (entity == null && HttpClientUtil.hasException) {
				log.info("http请求出现异常，将再次尝试3次");
				int times = 0;
				while (times < 3 && entity == null) {
					times++;
					log.info("http请求出现异常，第 " + times + "次尝试。。。");
					entity = HttpClientUtil.get(url);
				}
			}
			if (entity != null) {
				download(entity);
				HttpClientUtil.hasException = false;
			} else {
				log.info("连续4次http请求都失败了。。。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("json解析失败");
		}
		log.info("下载股票数据结束。。。");
		return MapUtils.createSuccessMap();
	}

	@SuppressWarnings({ "unchecked" })
	private void download(HttpEntity entity) throws Exception {
		LinkedHashMap<String, Object> detail = mapper.readValue(
				EntityUtils.toString(entity, "utf-8"), LinkedHashMap.class);
		String time = (String) detail.get("time");
		log.info("本次下载的时间是：" + time + "  上次下载的时间是： " + timeBak);
		if (!checkTime(time)) {
			return;
		}
		timeBak = time;
		final List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) detail
				.get("list");
		log.info("下载的数据是： " + list.size());
		for (LinkedHashMap<String, Object> map : list) {
			map.put("TIME", time);
		}
		Map<String, Object> map = MapUtils.createMap("list", list);
		this.stockDetailMapper.insert(map);
		// 异步更新StockCache的prePrices对象
		ThreadPool.execute(new Runnable() {

			@Override
			public void run() {
				StockCache.initByInternet(list);
			}
		});
		log.info("插入数据库成功！");
	}

	/**
	 * 判断股市此时是否是可交易时间
	 * 
	 * @param time
	 * @return
	 */
	private boolean checkTime(String time) {
		if ("".equals(timeBak)) {
			int count = this.stockDetailMapper.selectCountByTime(time);
			return count == 0 ? true : false;
		}
		return !timeBak.equals(time);
	}

	/**
	 * day, open, close, max, min, volume, increase
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> initStockEveryDay() throws Exception {
		List<String> codes = this.stockDetailMapper.selectAllCode();
		String year = CommonsUtil.formatDateToString5(new Date());
		for (String code : codes) {
			HttpEntity entity = HttpClientUtil
					.get("http://img1.money.126.net/data/hs/kline/day/history/"
							+ year + "/" + code + ".json");
			if (entity != null) {
				log.info("解析json数据。。。");
				LinkedHashMap<String, Object> detail = null;
				try {
					detail = mapper.readValue(
							EntityUtils.toString(entity, "utf-8"),
							LinkedHashMap.class);
				} catch (Exception e) {
					log.info("解析json数据失败");
					log.info(CommonsUtil.join(e.getStackTrace(), ",\r\n"));
				}
				if (detail != null) {
					log.info("开始判断数据正确性");
					List<List<Object>> list = (List<List<Object>>) detail
							.get("data");
					if (list != null && list.size() > 0) {
						String symbol = code.substring(1);
						String lastDay = this.stockDetailMapper
								.selectLastDay(symbol);
						if (lastDay != null) {
							lastDay = lastDay.replaceAll("-", "");
							List<List<Object>> inserts = new ArrayList<List<Object>>();
							for (int i = list.size() - 1; i >= 0; i--) {
								if (lastDay.equals(list.get(i).get(0))) {
									break;
								}
								inserts.add(list.get(i));
							}
							if (inserts.size() > 0) {
								this.stockMainMapper.insert(MapUtils.createMap(
										"list", inserts, "symbol", symbol));
								log.info("更新数据成功！插入的数据时 ： " + inserts);
							}
						} else {
							log.info("发现新的股票数据，开始插入，  " + list);
							this.stockMainMapper.insert(MapUtils.createMap(
									"list", list, "symbol", symbol));
						}
					}
				}
			}
		}
		return MapUtils.createSuccessMap();
	}

	public Map<String, Object> initBuyAndSell() {
		log.info("----------------------开始下载委买委卖数据----------------------");
		List<String> codes = this.stockMainMapper.selectAllCodes();
		int length = codes.size();
		int count = 1000;
		int begin = 0, end = (begin + count) <= length ? (begin + count)
				: length;
		List<String> subList = null;
		while (end <= length) {
			subList = codes.subList(begin, end);
			downloadBuyAndSell(subList);
			begin = end;
			end += count;
		}
		if (end > length && begin < length) {
			subList = codes.subList(begin, length);
			downloadBuyAndSell(subList);
		}
		log.info("----------------------下载委买委卖数据结束----------------------");
		return MapUtils.createSuccessMap();
	}

	// http://api.money.126.net/data/feed/0000001,0600137,1002485,1002291,1002763,1002486,money.api?callback=_ntes_quote_callback50000858
	// http://api.money.126.net/data/feed/0000001,1000573,0600275,1000553,0603777,0603313,0603816,0600321,0603006,0603887,0603016,1200413,1000755,1000002,0600589,0600137,1002485,1002291,1002763,1002486,money.api?callback=_ntes_quote_callback95430716
	@SuppressWarnings("unchecked")
	private void downloadBuyAndSell(List<String> subList) {
		HttpEntity entity;
		try {
			String url = "http://api.money.126.net/data/feed/"
					+ CommonsUtil.listToString(subList) + ",money.api";
			log.info(url);
			entity = HttpClientUtil.get(url);
			String temp = EntityUtils.toString(entity, "utf-8");
			String content = temp.substring(21, temp.length() - 2);
			if (entity != null) {
				LinkedHashMap<String, Object> detail = null;
				detail = mapper.readValue(content, LinkedHashMap.class);
				if (detail != null) {
					LinkedHashMap<String, Object> o = null;
					StockBuySell s = null;
					List<StockBuySell> list = new ArrayList<StockBuySell>();
					for (String code : subList) {
						o = (LinkedHashMap<String, Object>) detail.get(code);
						s = new StockBuySell(o);
						if (s.getSymbol() != null) {
							if (s.getAsk1() != 0 || s.getBid1() != 0) {
								list.add(s);
							}
						}
					}
					this.stockMainMapper.insertStockBuySell(MapUtils.createMap(
							"list", list));
					log.info("插入数据的数量是 ： " + list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(CommonsUtil.join(e.getStackTrace(), ","));
		}
	}

	//
	public Map<String, Object> test() throws Exception {
		String url = "http://img1.money.126.net/data/hs/1000573.json";
		HttpEntity entity = HttpClientUtil.get(url);
		if (entity != null) {
			System.out.println(EntityUtils.toString(entity, "utf-8"));
			// LinkedHashMap<String, Object> detail = mapper.readValue(
			// EntityUtils.toString(entity, "utf-8"),
			// LinkedHashMap.class);
		}
		return null;

	}

	// quotes.money.163.com/cjmx/2016/20161031/1002486.xls
	@Test
	public void downloadCJMX() throws Exception {
		String url = "http://quotes.money.163.com/cjmx/2016/20161031/1002486.xls";
		HttpEntity entity = HttpClientUtil.get(url);
		if (entity != null) {
			InputStream in = entity.getContent();
			OutputStream output = new FileOutputStream(
					"D:/stock_download/cjmx/1002486/1002486.xls");
			IOUtils.copy(in, output);
			// LinkedHashMap<String, Object> detail = mapper.readValue(
			// EntityUtils.toString(entity, "utf-8"),
			// LinkedHashMap.class);
		}

	}

	public Map<String, Object> initCjmx() throws Exception {
		List<String> codes = this.stockMainMapper.selectAllCodes();
		Date date = new Date();
		String year = CommonsUtil.formatYYYY(date);
		String day = CommonsUtil.formatYYYYMMDD(date);
		String direct = "D:/stock_download/cjmx/" + year + "/" + day;
		File directory = new File(direct);
		boolean exception = false;
		if (!directory.exists()) {
			exception = directory.mkdirs();
		}
		if (!exception) {
			return MapUtils.createFailedMap();
		}
		for (String code : codes) {
			try {
				File file = new File(direct + "/" + code + ".xls");
				String url = "http://quotes.money.163.com/cjmx/" + year + "/"
						+ day + "/" + code + ".xls";
				HttpEntity entity = HttpClientUtil.get(url);
				if (entity != null) {
					InputStream in = entity.getContent();
					OutputStream output = new FileOutputStream(file);
					IOUtils.copy(in, output);
					in.close();
					output.close();
				}
			} catch (Exception e) {
				log.info("code--" + code + " 下载失败！");
				log.info("------------------------------------------------------------------------------");
				log.info(CommonsUtil.join(e.getStackTrace()));
			}
		}
		return MapUtils.createSuccessMap();
	}
	
	/**
	 * 实时下载逐笔成交量
	 * url：http://quotes.money.163.com/service/zhubi_ajax.html?symbol=600868&end=09%3A52%3A00
	 * 
	 *  _id---{$id=582177fd0cf279f0882d18f7}
		TRADE_TYPE---1
		PRICE_PRE---9.14
		VOLUME_INC---1304200
		PRICE---9.15
		TURNOVER_INC---11933430
		DATE---{sec=1478588406, usec=0}
		PRICE_INC---0.0099999999999998
		DATE_STR---15:00:06
		TRADE_TYPE_STR---买盘
		
	 */

	@SuppressWarnings("unchecked")
	@Override
	public void insertCJL() {
		// TODO Auto-generated method stub
		List<String> symbols = this.stockMainMapper.selectAll();
		while(CommonsUtil.checkTime(this.holidayMapper)){
			long begin = System.currentTimeMillis();
			String time = CommonsUtil.formatDateToString4(new Date());
			String encodeTime = null;
			try {
				encodeTime = URLEncoder.encode(time,"utf-8");
			} catch (UnsupportedEncodingException e) {
				log.info("编码失败！");
			}
			String url = "";
			if(symbols!=null){
				List<FBVolume> fbVolumes = new ArrayList<FBVolume>();
				for (String symbol : symbols) {
					fbVolumes.clear();
					url = "http://quotes.money.163.com/service/zhubi_ajax.html?symbol="+symbol+"&end="+encodeTime;
					log.info(url);
					HttpEntity entity = HttpClientUtil.get(url);
					if(entity!=null){
						try {
							String content = EntityUtils.toString(entity, "utf-8");
							LinkedHashMap<String,Object> detail = mapper.readValue(content, LinkedHashMap.class);
							System.out.println(detail);
							ArrayList<Object> list = (ArrayList<Object>) detail.get("zhubi_list");
							if(list!=null&&list.size()>0){
								for (Object object : list) {
									LinkedHashMap<String,Object> o = (LinkedHashMap<String, Object>) object;
									FBVolume volume = new FBVolume(o);
									fbVolumes.add(volume);
								}
							}
							if(fbVolumes.size()>0){
								List<FBVolume> inserts = getInsertList("fbVolumes"+symbol, fbVolumes);
								this.stockMainMapper.insertFBVolume(MapUtils.createMap("list",inserts,"symbol",symbol));
								putFbVolumes("fbVolumes"+symbol, fbVolumes);
								log.info("插入数据库成功！---"+symbol);
							}else{
								log.info("该股票此时间段内无成交量---"+symbol);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			long remain = System.currentTimeMillis()-begin;
			long sleep = StockConstant.INIT_STOCK_SLEEP_TIME - remain;
			if(sleep>0){
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					log.info(CommonsUtil.join(e.getStackTrace()));
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void putFbVolumes(String key,List<FBVolume> fbVolumes){
		List<FBVolume> list = (List<FBVolume>) StockCache.getCache(key);
		if(list==null){
			StockCache.putCache(key, fbVolumes);
			List<List<FBVolume>> fbVolumes_list = new ArrayList<List<FBVolume>>();
			fbVolumes_list.add(fbVolumes);
		}else{
			List<List<FBVolume>> fbVolumes_list = (List<List<FBVolume>>) StockCache.getCache("fbVolumes_list");
			if(fbVolumes_list.size()==2){
				List<FBVolume> temp = fbVolumes_list.remove(0);
				List<FBVolume> list1 = (List<FBVolume>) StockCache.getCache(key);
				list1.removeAll(temp);
				list1.addAll(fbVolumes);
				fbVolumes_list.add(fbVolumes);
			}else{
				fbVolumes_list.add(fbVolumes);
				((List<FBVolume>) StockCache.getCache(key)).addAll(fbVolumes);
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private List<FBVolume> getInsertList(String key,List<FBVolume> fbVolumes){
		fbVolumes.removeAll((List<FBVolume>) StockCache.getCache(key));
		return fbVolumes;
	}
	
	public static void main(String[] args) {
		new InitStockServiceImpl().testInsertCJL();
	}
	
	@SuppressWarnings("unchecked")
	public void testInsertCJL() {
		String time = CommonsUtil.formatDateToString4(new Date());
		String encodeTime = null;
		try {
			encodeTime = URLEncoder.encode(time,"utf-8");
		} catch (UnsupportedEncodingException e) {
			log.info("编码失败！");
		}
		String url = "";
		String symbol = "000029";
			List<FBVolume> fbVolumes = new ArrayList<FBVolume>();
				fbVolumes.clear();
				url = "http://quotes.money.163.com/service/zhubi_ajax.html?symbol="+symbol+"&end="+encodeTime;
				log.info(url);
				HttpEntity entity = HttpClientUtil.get(url);
				if(entity!=null){
					try {
						String content = EntityUtils.toString(entity, "utf-8");
						System.out.println(content);
						LinkedHashMap<String,Object> detail = mapper.readValue(content, LinkedHashMap.class);
						ArrayList<Object> list = (ArrayList<Object>) detail.get("zhubi_list");
						if(list!=null&&list.size()>0){
							for (Object object : list) {
								LinkedHashMap<String,Object> o = (LinkedHashMap<String, Object>) object;
								FBVolume volume = new FBVolume(o);
								fbVolumes.add(volume);
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(fbVolumes.size()>0){
//					this.stockMainMapper.insertFBVolume(MapUtils.createMap("list",fbVolumes,"symbol",symbol));
					log.info("插入数据库成功！---"+symbol);
				}else{
					log.info("该股票此时间段内无成交量---"+symbol);
				}
	}   
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void initJunX(String symbol) throws Exception{
		StockAnalyseBase analyseBase = stockMainMapper.selectStockAnalyse(MapUtils.createMap("symbol",symbol));
		if(analyseBase!=null){
			analyseBase.initJunXian();
			if(analyseBase.junxians.size()>0){
				DefaultTransactionDefinition def = new DefaultTransactionDefinition();
				def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
				TransactionStatus status = transactionManager.getTransaction(def);
				stockMainMapper.insertJunXian(analyseBase);
				transactionManager.commit(status);
//				log.info("insert----------"+symbol+"---------------------size------"+analyseBase.junxians.size());
			}
			log.info("insert----------"+symbol+"---------------------size------"+analyseBase.junxians.size());
		}
	}
	
	public void initJunXEveryDay(){
		
	}
	
	
}
