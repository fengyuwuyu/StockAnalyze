package com.stock.util;

import java.util.HashMap;
import java.util.Map;

import com.stock.dao.StockMainMapper;


public class StockCache {

	public static Map<String, Object> prePrices = new HashMap<String, Object>();
	
	public static void initPrePrices(StockMainMapper stockMainMapper){
		prePrices = stockMainMapper.initPrePrices();
	}
}
