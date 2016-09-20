package com.stock.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.config.Configuration;
import com.stock.connection.HttpClientUtil;
import com.stock.dao.StockDetailMapper;
import com.stock.dao.StockMainMapper;
import com.stock.dao.StockShortMapper;
import com.stock.model.StockMain;
import com.stock.model.StockQuery;
import com.stock.model.StockVol;
import com.stock.service.StockServiceI;
import com.stock.util.CommonsUtil;
import com.stock.util.MapUtils;

/**
 * 总股票数2682
 * 
 * @author lilei
 * 
 */
@Service
public class StockServiceimpl implements StockServiceI {

	private static ObjectMapper mapper = new ObjectMapper();
	private StockShortMapper shortMapper;
	private StockDetailMapper detailMapper;
	private StockMainMapper mainMapper;
	private Integer[] years = {/*1990,1991,1992,1993,1994,1995,1996,1997,1998,1999,*/2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016};
	private Logger log = Logger.getLogger(StockServiceimpl.class);

	@Autowired
	public void setMainMapper(StockMainMapper mainMapper) {
		this.mainMapper = mainMapper;
	}

	@Autowired
	public void setShortMapper(StockShortMapper shortMapper) {
		this.shortMapper = shortMapper;
	}

	@Autowired
	public void setDetailMapper(StockDetailMapper detailMapper) {
		this.detailMapper = detailMapper;
	}

	@SuppressWarnings("unchecked")
	public void insertStockShort() throws Exception {
		List<String> symbols = this.shortMapper.selectAll();
		for (String symbol : symbols) {
			String url = "http://quotes.money.163.com/hs/service/diyrank.php?host=http://quotes.money.163.com/hs/service/diyrank.php&count=24&"
					+ "field=SNAME,CODE,"
					+ "ANNOUNMT,UVSNEWS&order=desc&page=0&count=24"
					+ "&query=SYMBOL:"
					+ symbol
					+ ";STYPE:EQA&sort=PERCENT&type=query";
			HttpEntity entity = HttpClientUtil.get(url);
			if (entity != null) {
				LinkedHashMap<String, Object> detail = mapper.readValue(
						EntityUtils.toString(entity, "utf-8"),
						LinkedHashMap.class);
				List<LinkedHashMap<String, Object>> list = (List<LinkedHashMap<String, Object>>) detail
						.get("list");
				if(list!=null&&list.size()>0){
					this.detailMapper.insertSelective(list.get(0));
				}
			}
		}
	}

	public void insertDetail() throws Exception {
		java.util.Date day = new java.util.Date();
		int count = this.detailMapper.selectCountByTime(CommonsUtil
				.formatDateToString1(day));
		if (count != 0) {
			return;
		}
		List<LinkedHashMap<String, Object>> list1 = insert(Configuration.HUS_A,
				Configuration.HUS_A_TYPE);
		List<LinkedHashMap<String, Object>> list2 = insert(Configuration.HUS_B,
				Configuration.HUS_B_TYPE);
		List<LinkedHashMap<String, Object>> list3 = insert(Configuration.CYB,
				Configuration.CYB_TYPE);
		List<LinkedHashMap<String, Object>> list4 = insert(Configuration.ZXB,
				Configuration.ZXB_TYPE);
		Map<String, LinkedHashMap<String, Object>> mmp = new HashMap<>();
		for (LinkedHashMap<String, Object> l : list1) {
			if (check(l)) {
				mmp.put((String) l.get("SYMBOL"), l);
			}
		}
		for (LinkedHashMap<String, Object> l : list2) {
			if (check(l)) {
				mmp.put((String) l.get("SYMBOL"), l);
			}
		}
		for (LinkedHashMap<String, Object> l : list3) {
			if (check(l)) {
				mmp.put((String) l.get("SYMBOL"), l);
			}
		}
		for (LinkedHashMap<String, Object> l : list4) {
			if (check(l)) {
				mmp.put((String) l.get("SYMBOL"), l);
			}
		}
		for (LinkedHashMap<String, Object> map : mmp.values()) {
			this.detailMapper.insertSelective(map);
		}
	}

	private boolean check(LinkedHashMap<String, Object> l) {
		Integer vol = (Integer) l.get("VOLUME");
		if (vol == null || vol == 0) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private List<LinkedHashMap<String, Object>> insert(String url, String type)
			throws Exception {
		// url =
		// "http://quotes.money.163.com/old/#query=EQA&DataType=HS_RANK&sort=PERCENT&order=desc&count=2880&page=0";
		HttpEntity entity = HttpClientUtil.get(url);
		if (entity != null) {
			// System.out.println("---------------------------------------------------------------------");
			// System.out.println(EntityUtils.toString(entity, "utf-8"));
			// System.out.println("---------------------------------------------------------------------");
			LinkedHashMap<String, Object> detail = mapper.readValue(
					EntityUtils.toString(entity, "utf-8"), LinkedHashMap.class);
			return (List<LinkedHashMap<String, Object>>) detail.get("list");
			// for (LinkedHashMap<String, Object> map : list) {
			// map.put("type", type);
			// this.detailMapper.insertSelective(map);
			// System.out.println(map.get("HIGH"));
			// System.out.println(map.get("LOW"));
			// System.out.println(map.get("MCAP")); // 流通市值
			// System.out.println(map.get("OPEN"));
			// System.out.println(map.get("PE")); // 市盈率
			// System.out.println(map.get("PERCENT")); // 涨跌比
			// System.out.println(map.get("SNAME"));
			// System.out.println(map.get("VOLUME")); // 成交量
			// System.out.println(map.get("UPDOWN")); // 涨跌额
			// System.out.println(map.get("TURNOVER")); // 成交额
			// System.out.println(map.get("YESTCLOSE")); // 昨收
			// System.out.println(map.get("PRICE")); // 当前价格
			// System.out.println(map.get("SYMBOL")); // 代码
			// System.out.println(map.get("TIME"));
			// }
		}
		return null;
	}

	@Override
	public void getVolData() throws Exception {
		HttpClientUtil
				.get("http://quotes.money.163.com/service/chddata.html?"
						+ "code=1300448&start=2010-10-10&end=2016-06-26&fields=TCLOSE;HIGH;LOW;"
						+ "TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP");
	}

	/**
	 * 返回当天成交量增长情况
	 */
	public Map<String, Object> volBigIncrease(Date date) throws Exception {
		List<StockVol> list = this.detailMapper.dataList(date);
		if (list != null && list.size() > 0) {
			for (StockVol vol : list) {
				vol.execute();
			}
		}
		Collections.sort(list);
		return MapUtils.createSuccessMap("rows", list.subList(0, 100), "total",
				100);
	}

	/**
	 * 返回价跌量涨的股票
	 */
	public Map<String, Object> priceDownVolUp(Date date) throws Exception {
		List<StockVol> list = this.detailMapper.dataList1(date);
		List<StockVol> result = new ArrayList<>();
		if (list != null && list.size() > 0) {
			for (StockVol vol : list) {
				vol.priceDownVolUp();
				if (vol.isPriceDownVolUp()) {
					result.add(vol);
				}
			}
		}
		return MapUtils
				.createSuccessMap("rows", result, "total", result.size());
	}

	/**
	 * 返回当天成交量增长情况
	 */
	public Map<String, Object> saveBigIncrease() throws Exception {
		List<StockVol> list = this.detailMapper.dataList(null);
		if (list != null && list.size() > 0) {
			for (StockVol vol : list) {
				vol.execute();
				this.detailMapper.saveBigIncrease(vol);
			}
		}
		return MapUtils.createSuccessMap();
	}

	@Override
	public Map<String, Object> maxDown(String maxDay, String minDay) {

		return null;
	}

	/**
	 * 
	 * @return
	 */
	public Map<String, Object> queryPriceLargeChange(StockQuery query) {

		return MapUtils.createSuccessMap();
	}

	/**
	 * url : http://img1.money.126.net/data/hs/kline/day/times/1000755.json
	 * 参数：0：时间、0：开盘，1：收盘，2：最高，3：最低，4：成交量，5：涨幅
	 * 
	 * http://img1.money.126.net/data/hs/kline/day/history/2016/1000755.json
	 * 返回股票的收盘价
	 * 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> initStock() throws Exception {
			List<String> symbols = this.shortMapper.selectAll();
			for (String symbol : symbols) {
				for (Integer day : years) {
					HttpEntity entity = HttpClientUtil
							.get("http://img1.money.126.net/data/hs/kline/day/history/"+day+"/2"
									+ symbol + ".json");
					if (entity != null) {
//						System.out.println(EntityUtils.toString(entity, "utf-8"));
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
							int end = 0;
							if (list != null && list.size() > 0) {
								log.info("---------list.size()-------"+list.size());
								log.info("--------list--------"+list);
//								for (int i = 0; i < list.size(); i+=100) {
//									end = i+100;
//									end = end<list.size()?end:list.size();
//									List<List<Object>> sub = list.subList(i, end);
//									log.info("--------sub--------"+sub);
//									List<Object> s = list.get(i);
//									StockMain main = new StockMain(symbol,
//											CommonsUtil.formatStringToDate1(s
//													.get(0).toString()),
//											Float.valueOf(s.get(1).toString()),
//											Float.valueOf(s.get(2).toString()),
//											Float.valueOf(s.get(3).toString()),
//											Float.valueOf(s.get(4).toString()),
//											Long.valueOf(s.get(5).toString()),
//											Float.valueOf(s.get(6).toString()));
//									if(sub!=null&&sub.size()>0){
										this.mainMapper.insert(MapUtils.createMap("list",list,"symbol",symbol));
//									}
//								}
							}
						}
					}
				}
			}
		return MapUtils.createSuccessMap();
	}

	//http://img1.money.126.net/data/hs/kline/day/history/2016/0603016.json?callback=neb7ceb25d5da26
	public Map<String,Object> test(){
		HttpEntity entity;
		try {
			entity = HttpClientUtil.get("http://img1.money.126.net/data/hs/kline/day/history/2016/0603016.json");
			String content = EntityUtils.toString(entity, "utf-8");
			System.out.println(content);
			if (entity != null) {
				LinkedHashMap<String, Object> detail = null;
					detail = mapper.readValue(
							content,
							LinkedHashMap.class);
					System.out.println(detail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MapUtils.createSuccessMap();
	}
	
	public Map<String,Object> queryStockMain(StockQuery query){
		int type = query.getType();
		List<StockMain> list = null;
		switch (type) {
		case 1:
			list = this.mainMapper.selectBy1(query);
			break;
		case 2:
			
			break;
		default:
			break;
		}
		return MapUtils.createSuccessMap();
	}
}
