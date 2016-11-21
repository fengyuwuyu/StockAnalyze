package com.stock.service;

import java.util.List;
import java.util.Map;

import com.stock.model.StockMain;
import com.stock.model.StockQuery;

public interface SearchMachineI {
	
	/**
	 * 搜索器，针对从该天气之后的5天涨幅超过10%股票进行统计分析
	 * @param query
	 * @return
	 */
	Map<String,Object> find(StockQuery query);
	
	/**
	 * 查找处于黄金交叉点的股票
	 * @param begin
	 * @return
	 */
	List<StockMain> findGlodStock(StockQuery query);
	
	Map<String,Object> searcher();
	
}
