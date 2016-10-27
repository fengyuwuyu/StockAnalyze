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
			HttpEntity entity = null;
			while(entity==null){
				entity = HttpClientUtil.get(url);
			}
			download(entity);
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
		List<LinkedHashMap<String, Object>> list= (List<LinkedHashMap<String, Object>>) detail.get("list");
		 for (LinkedHashMap<String, Object> map : list) {
			 map.put("TIME", time);
		 }
		 Map<String,Object> map = MapUtils.createMap("list",list);
		 this.stockDetailMapper.insert(map);
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

	
	@SuppressWarnings("unchecked")
	public Map<String,Object> initStockEveryDay() throws Exception{
		List<String> codes = this.stockDetailMapper.selectAllCode();
		String year = CommonsUtil.formatDateToString5(new Date());
		for (String code : codes) {
				HttpEntity entity = HttpClientUtil
						.get("http://img1.money.126.net/data/hs/kline/day/history/"+year+"/"
								+ code + ".json");
				if (entity != null) {
					LinkedHashMap<String, Object> detail = null;
					try {
						detail = mapper.readValue(
								EntityUtils.toString(entity, "utf-8"),
								LinkedHashMap.class);
					} catch (Exception e) {
						continue;
					}
					if (detail != null) {
						List<List<Object>> list = (List<List<Object>>) detail
								.get("data");
						if (list != null && list.size() > 0) {
							String symbol = code.substring(1);
							String lastDay = this.stockDetailMapper.selectLastDay(symbol);
							List<List<Object>> inserts = new ArrayList<List<Object>>();
							for(int i=list.size();i>=0;i--){
								
							}
							this.stockMainMapper.insert(MapUtils.createMap("list",list,"symbol",symbol));
							String now = CommonsUtil.formatDateToString1(new Date());
							for(List<Object> objects : list){
								log.info(objects);
							}
							this.stockDetailMapper.insertStockMain(MapUtils.createMap("list",list,"symbol",symbol));
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
