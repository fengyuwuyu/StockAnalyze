package com.stock.service;

import java.sql.Date;
import java.util.Map;

import com.stock.model.StockQuery;

public interface StockMainServiceI {

	Map<String,Object> dataList(StockQuery query);

	Map<String, Object> showChart(StockQuery query);
	
	void initStock();

	Map<String, Object> dataList1(StockQuery query);
	
	Map<String, Object> initIncreaseStock() ;

	Map<String, Object> analyseQuery(Date day);
	
	boolean analyse1(int count);
}
