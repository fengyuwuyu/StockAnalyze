package com.stock.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	private static float minIncrease = 0;
	private static float minIncreaseThreeMinute = 0;

	/**
	 * 系统第一次启动时调用，根据数据库中数据初始化prePrices对象
	 * 
	 * @param stockMainMapper
	 */
	public static void initPrePrices(StockMainMapper stockMainMapper) {
		Map<String, Object> map = stockMainMapper.initPrePrices();
		if (map != null && map.keySet().size() > 0) {
			for (String symbol : map.keySet()) {
				float close = (float) map.get(symbol);
				prePrices.put(symbol, new CacheItem(close, close));
			}
		}
		System.out.println(prePrices);
	}

	public static void initByInternet(List<LinkedHashMap<String, Object>> list) {
		if (list != null && list.size() > 0) {
			maxIncreaseStocks.clear();
			maxIncreaseThreeMinute.clear();
			for (LinkedHashMap<String, Object> m : list) {
				updatePrce(m);
			}
			if (maxIncreaseStocks.size() > 0) {
				try {
					MailUtils.sendMail("当天目前涨幅前二十", "");
				} catch (Exception e) {
					log.error(CommonsUtil.join(e.getStackTrace(), ","));
				}
			}
			if (maxIncreaseThreeMinute.size() > 0) {

			}
		}
		findMaxIncrease();
	}

	public static void updatePrce(LinkedHashMap<String, Object> m) {
		String symbol = (String) m.get("SYMBOL");
		float price = Float.valueOf((String) m.get("PRICE"));
		float oldPrice = prePrices.get(symbol).getPrice();
		float oldClose = prePrices.get(symbol).getClose();
		float open = Float.valueOf((String) m.get("OPEN"));
		long vol = Long.valueOf((String) m.get("VOLUME"));
		float increase = Float.valueOf((String) m.get("PERCENT"));
		StockMain main = new StockMain(symbol, open, price, vol, increase);
		if ((price - oldPrice) * 100 / oldPrice >= StockConstant.THREE_MINUTE_MAX_INCREASE) {
			maxIncreaseThreeMinute.add(main);
		}
		if ((price - oldClose) * 100 / oldClose >= StockConstant.MAX_INCREASE) {
			maxIncreaseStocks.add(main);
		}
		prePrices.get(symbol).setPrice(price);
	}

	private static void findMaxIncrease() {

	}

	private static void addMaxIncreaseStocks(StockMain main) {
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
}
