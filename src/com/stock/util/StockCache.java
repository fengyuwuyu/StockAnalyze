package com.stock.util;

import java.util.ArrayList;
import java.util.Collections;
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
import com.stock.model.StockMain;

public class StockCache {

	public static Map<String, CacheItem> prePrices = new HashMap<String, CacheItem>();
	private static Logger log = Logger.getLogger(StockCache.class);
	private static List<StockMain> maxIncreaseStocks = new ArrayList<StockMain>(
			20);
	private static List<StockMain> maxIncreaseThreeMinute = new ArrayList<StockMain>();
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
		float oldPrice = prePrices.get(symbol).getPrice();
		float oldClose = prePrices.get(symbol).getClose();
		float open = Float.valueOf( m.get("OPEN")+"");
		long vol = (Integer) m.get("VOLUME");
		float increase = Float.valueOf( m.get("PERCENT")+"");
		StockMain main = new StockMain(symbol, open, price, vol, increase);
		if ((price - oldPrice) * 100 / oldPrice >= StockConstant.THREE_MINUTE_MAX_INCREASE) {
			main.setIncrease((price - oldPrice) * 100 / oldPrice);
			addMaxIncreaseThreeMinute( main);
		}
		if ((price - oldClose) * 100 / oldClose >= StockConstant.MAX_INCREASE) {
			addMaxIncreaseStocks(main);
		}
		prePrices.get(symbol).setPrice(price);
	}

	private static void findMaxIncrease() {

	}

	private static void addMaxIncreaseStocks(StockMain main) {
		if(hasNotify(main)){
			return ;
		}
		hasNotify.add(main.getSymbol());
		if (maxIncreaseStocks.size() < 20) {
			maxIncreaseStocks.add(main);
		} else {
			Collections.sort(maxIncreaseStocks);
			if (main.getIncrease() > maxIncreaseStocks.get(19).getIncrease()) {
				maxIncreaseStocks.remove(19);
				maxIncreaseStocks.add(main);
			}
		}
	}
	
	private static void addMaxIncreaseThreeMinute(StockMain main) {
		if (maxIncreaseThreeMinute.size() < 20) {
			maxIncreaseThreeMinute.add(main);
		} else {
			Collections.sort(maxIncreaseThreeMinute);
			if (main.getIncrease() > maxIncreaseThreeMinute.get(19).getIncrease()) {
				maxIncreaseThreeMinute.remove(19);
				maxIncreaseThreeMinute.add(main);
			}
		}
	}
	
	private static boolean hasNotify(StockMain main){
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
