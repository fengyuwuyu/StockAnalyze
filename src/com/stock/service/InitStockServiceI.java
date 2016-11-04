package com.stock.service;

import java.util.Map;

public interface InitStockServiceI {
	
	Map<String,Object> initStock();

	Map<String, Object> initStockEveryDay() throws Exception;

	Map<String, Object> test() throws Exception;
	
	Map<String,Object> initBuyAndSell();
}
