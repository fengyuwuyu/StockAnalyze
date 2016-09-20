package com.stock.service;

import java.sql.Date;
import java.util.Map;

import com.stock.model.StockQuery;

public interface StockServiceI {

	void insertStockShort() throws Exception;

	void getVolData() throws Exception;

	public void insertDetail() throws Exception;

	Map<String, Object> volBigIncrease(Date date) throws Exception;
	
	public Map<String, Object> saveBigIncrease() throws Exception;

	public Map<String, Object> priceDownVolUp(Date date) throws Exception;

	Map<String, Object> maxDown(String maxDay, String minDay) throws Exception;
	
	Map<String,Object> queryPriceLargeChange(StockQuery query) throws Exception;
	
	Map<String,Object> initStock() throws Exception;
	
	public Map<String,Object> test() throws Exception;
}