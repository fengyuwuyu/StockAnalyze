package com.stock.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.connection.HttpClientUtil;
import com.stock.dao.StockDetailMapper;
import com.stock.dao.StockMainMapper;
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
	private String timeBak = "";
	private Logger log = Logger.getLogger(InitStockServiceImpl.class);

	@Autowired
	public void setStockDetailMapper(StockDetailMapper stockDetailMapper) {
		this.stockDetailMapper = stockDetailMapper;
	}
	
	@Autowired
	public void setStockMainMapper(StockMainMapper stockMainMapper) {
		this.stockMainMapper = stockMainMapper;
	}



	/**
	 * 每30秒执行一次
	 * 用于获取股票的详细信息
	 */
	@Override
	public Map<String, Object> initStock() {
		log.info("下载股票数据，每分钟执行一次。。。"+(CommonsUtil.formatDateToString3(new Date())));
		String url = "http://quotes.money.163.com/hs/service/diyrank.php?"
				+ "host=http%3A%2F%2Fquotes.money.163.com%2Fhs%2Fservice%2Fdiyrank.php&"
				+ "page=0&query=STYPE%3AEQA&fields=NO%2CSYMBOL%2CNAME%2CPRICE%2CPERCENT%2CUPDOWN%2CFIVE_MINUTE%2COPEN%"
				+ "2CYESTCLOSE%2CHIGH%2CLOW%2CVOLUME%2CTURNOVER%2CHS%2CLB%2CWB%2CZF%2CPE%2CMCAP%2CTCAP%2CMFSUM%"
				+ "2CMFRATIO.MFRATIO2%2CMFRATIO.MFRATIO10%2CSNAME%2CCODE%2CANNOUNMT%2CUVSNEWS&s"
				+ "ort=PERCENT&order=desc&count=2953&type=query";
		try {
			HttpEntity entity = HttpClientUtil.get(url);
			while(entity==null&&HttpClientUtil.hasException){
				log.info("http请求出现异常，将再次尝试3次");
				int times = 0;
				while(times<3&&entity==null){
					times++;
					log.info("http请求出现异常，第 "+times+"次尝试。。。");
					entity = HttpClientUtil.get(url);
				}
			}
			if(entity!=null){
				download(entity);
				HttpClientUtil.hasException = false;
			}else{
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
	private void download(HttpEntity entity) throws Exception{
		LinkedHashMap<String, Object> detail = mapper.readValue(
				EntityUtils.toString(entity, "utf-8"), LinkedHashMap.class);
		log.info("下载的数据是： "+detail.get("count"));
		String time = (String) detail.get("time");
		log.info("本次下载的时间是："+time+"  上次下载的时间是： "+timeBak);
		if(!checkTime(time)){
			return ;
		}
		timeBak = time;
		final List<LinkedHashMap<String, Object>> list= (List<LinkedHashMap<String, Object>>) detail.get("list");
		 for (LinkedHashMap<String, Object> map : list) {
			 map.put("TIME", time);
		 }
		 Map<String,Object> map = MapUtils.createMap("list",list);
		 this.stockDetailMapper.insert(map);
		 //异步更新StockCache的prePrices对象
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
	 * @param time
	 * @return
	 */
	private boolean checkTime(String time) {
		if("".equals(timeBak)){
			int count = this.stockDetailMapper.selectCountByTime(time);
			return count==0?true:false;
		}
		return !timeBak.equals(time);
	}

	/**
	 * day, open, close, max, min, volume, increase
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> initStockEveryDay() throws Exception{
		List<String> codes = this.stockDetailMapper.selectAllCode();
		String year = CommonsUtil.formatDateToString5(new Date());
		for (String code : codes) {
				HttpEntity entity = HttpClientUtil
						.get("http://img1.money.126.net/data/hs/kline/day/history/"+year+"/"
								+ code + ".json");
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
							String lastDay = this.stockDetailMapper.selectLastDay(symbol);
							if(lastDay!=null){
								lastDay = lastDay.replaceAll("-", "");
								List<List<Object>> inserts = new ArrayList<List<Object>>();
								for(int i=list.size()-1;i>=0;i--){
									if(lastDay.equals(list.get(i).get(0))){
										break;
									}
									inserts.add(list.get(i));
								}
								if(inserts.size()>0){
									this.stockMainMapper.insert(MapUtils.createMap("list",inserts,"symbol",symbol));
									log.info("更新数据成功！插入的数据时 ： "+inserts);
								}
							}else{
								log.info("发现新的股票数据，开始插入，  "+list);
								this.stockMainMapper.insert(MapUtils.createMap("list",list,"symbol",symbol));
							}
						}
					}
				}
		}
		return MapUtils.createSuccessMap();
	}
	
	//
	public Map<String, Object> test() throws Exception{
		String url = "http://img1.money.126.net/data/hs/1000573.json";
		HttpEntity entity = HttpClientUtil.get(url);
		if(entity!=null){
			System.out.println(EntityUtils.toString(entity, "utf-8"));
//			LinkedHashMap<String, Object> detail = mapper.readValue(
//					EntityUtils.toString(entity, "utf-8"),
//					LinkedHashMap.class);
		}
		return null;
		
	}
	
}
