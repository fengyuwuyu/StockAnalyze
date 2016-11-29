package com.stock.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import com.stock.dao.StockMainMapper;
import com.stock.model.CacheItem;
import com.stock.model.StockConstant;

/**
 * 功能：
 * 	1、根据委卖委买数据计算当天股价涨幅以及当前股价涨幅（间隔可自由控制）
 * 	2、计算当天成交量涨幅及当前成交量涨幅（间隔与当前股价间隔相同）
 * 	3、计算委卖委买笔数
 * @author ll
 *
 */
public class StockCache {

	public static Map<String, CacheItem> prePrices = new HashMap<String, CacheItem>();
	private static Logger log = Logger.getLogger(StockCache.class);
	private static List<CacheItem> maxIncreaseStocks = new ArrayList<CacheItem>(10);
	private static List<CacheItem> maxIncreaseThreeMinute = new ArrayList<CacheItem>(10);
	private static List<String> hasNotify = new ArrayList<String>();
	
	private static ConcurrentHashMap<Object,Object> cache = new ConcurrentHashMap<Object,Object>();
	private static SqlSessionFactory sqlSessionFactory = null;

	/**
	 * 系统第一次启动时调用，根据数据库中数据初始化prePrices对象
	 * 
	 * @param stockMainMapper
	 */
	public static void initPrePrices(StockMainMapper stockMainMapper) {
		List<CacheItem> list = stockMainMapper.initPrePrices();
		for(CacheItem item : list) {
			prePrices.put(item.getSymbol(), item);
		}
		log.info(prePrices);
	}

	public static void initByInternet(List<LinkedHashMap<String, Object>> list) {
		if (list != null && list.size() > 0) {
			maxIncreaseStocks.clear();
			maxIncreaseThreeMinute.clear();
			for (LinkedHashMap<String, Object> m : list) {
				updatePrce(m);
			}
			log.info("maxIncreaseStocks   "+maxIncreaseStocks);
			log.info("maxIncreaseThreeMinute   "+maxIncreaseThreeMinute);
			if (maxIncreaseStocks.size() > 0) {
				try {
					MailUtils.sendMail("当天涨幅", "当天涨幅 : "+maxIncreaseStocks.toString());
				} catch (Exception e) {
					log.error(CommonsUtil.join(e.getStackTrace(), ","));
					try {
						MailUtils.sendMail("系统异常", "3分钟涨幅数据发送失败::"+maxIncreaseStocks.toString());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			if (maxIncreaseThreeMinute.size() > 0) {
				try {
					MailUtils.sendMail("3分钟涨幅", "3分钟涨幅 : "+maxIncreaseThreeMinute.toString());
				} catch (Exception e) {
					log.error(CommonsUtil.join(e.getStackTrace(), ","));
					try {
						MailUtils.sendMail("系统异常", "3分钟涨幅数据发送失败::"+maxIncreaseThreeMinute.toString());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		findMaxIncrease();
	}

	public static void updatePrce(LinkedHashMap<String, Object> m) {
		String symbol = (String) m.get("SYMBOL");
		if( prePrices.get(symbol)==null){
			return ;
		}
		float price = Float.valueOf( m.get("PRICE")+"");
		float oldPrice = prePrices.get(symbol).getNowPrice();
		float oldClose = prePrices.get(symbol).getYestClose();
//		float open = Float.valueOf( m.get("OPEN")+"");
		long vol = (Integer) m.get("VOLUME");
//		float increase = Float.valueOf( m.get("PERCENT")+"");
		CacheItem main = new CacheItem(symbol, price, vol);
		if ((price - oldPrice) * 100 / oldPrice >= StockConstant.THREE_MINUTE_MAX_INCREASE) {
			
		}
		if ((price - oldClose) * 100 / oldClose >= StockConstant.MAX_INCREASE) {
			addMaxIncreaseStocks(main);
		}
		prePrices.get(symbol).setNowPrice(price);
	}

	private static void findMaxIncrease() {

	}

	private static void addMaxIncreaseStocks(CacheItem main) {
		if(hasNotify(main)){
			return ;
		}
		hasNotify.add(main.getSymbol());
		if (maxIncreaseStocks.size() < 20) {
			maxIncreaseStocks.add(main);
		} else {
//			Collections.sort(maxIncreaseStocks);
//			if (main.getIncrease() > maxIncreaseStocks.get(19).getIncrease()) {
//				maxIncreaseStocks.remove(19);
//				maxIncreaseStocks.add(main);
//			}
		}
	}
	
	private static void addMaxIncreaseThreeMinute(CacheItem main) {
		if (maxIncreaseThreeMinute.size() < 20) {
			maxIncreaseThreeMinute.add(main);
		} else {
//			Collections.sort(maxIncreaseThreeMinute);
//			if (main.getIncrease() > maxIncreaseThreeMinute.get(19).getIncrease()) {
//				maxIncreaseThreeMinute.remove(19);
//				maxIncreaseThreeMinute.add(main);
//			}
		}
	}
	
	private static boolean hasNotify(CacheItem main){
		if(hasNotify.indexOf(main.getSymbol())!=-1){
			return true;
		}
		return false;
	}
	
	public static boolean putCache(Object key,Object value){
		cache.put(key, value);
		return true;
	}
	
	public static Object getCache(Object key){
		return cache.get(key);
	}

	public static void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		StockCache.sqlSessionFactory = sqlSessionFactory;
	}
	
	/**
	 * 主要用于用户需要自己维护事务的SqlSession
	 * @param auto
	 * @return
	 */
	public static SqlSession openSession(boolean auto){
		if(sqlSessionFactory==null)
			return null;
		return sqlSessionFactory.openSession(ExecutorType.BATCH, auto);
	}
}
