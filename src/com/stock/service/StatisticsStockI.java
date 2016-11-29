package com.stock.service;

import java.util.Map;

import com.stock.model.StockQuery;

public interface StatisticsStockI {
	/**
	 * 统计各个因素对股票涨幅的影响
	 * 	1、当前相对股价
	 * 	2、当前成交量
	 * 	3、当前股价与均线关系
	 * @param query
	 * @return
	 */
	Map<String,Object> statistics(StockQuery query);
}
