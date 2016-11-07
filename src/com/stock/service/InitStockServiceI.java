package com.stock.service;

import java.util.Map;

public interface InitStockServiceI {
	
	/**下载成交明细*/
	Map<String,Object> initStock();

	/**下载当天股票数据*/
	Map<String, Object> initStockEveryDay() throws Exception;

	Map<String, Object> test() throws Exception;
	
	/**下载包含五档委买卖信息的数据*/
	Map<String,Object> initBuyAndSell();
	
	/**下载成交明细*/
	Map<String,Object> initCjmx() throws Exception;

	void insertCJL();
}
