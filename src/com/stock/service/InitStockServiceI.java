package com.stock.service;

import java.util.Map;

public interface InitStockServiceI {
	
	/**下载成交明细*/
	Map<String,Object> initStock();

	/**下载当天股票数据*/
	Map<String, Object> initStockEveryDay() throws Exception;

	Map<String, Object> test() throws Exception;
	
	/**下载包含五档委买卖信息的数据
	 * @param day */
	Map<String,Object> initBuyAndSell(String day);
	
	/**下载成交明细*/
	Map<String,Object> initCjmx() throws Exception;

	void insertCJL();
	
	/**
	 * 初始化均线
	 * 只调用一次
	 */
	public void initJunX(String symbol)  throws Exception;
	
	/**
	 * 每日计算新增均线
	 */
	public void initJunXEveryDay();
	
	public Map<String, Object> initCjmxPerWeek() throws Exception;
}
