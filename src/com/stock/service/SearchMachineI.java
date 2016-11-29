package com.stock.service;

import java.util.List;
import java.util.Map;

import com.stock.model.StockAnalyseBase;
import com.stock.model.StockQuery;

public interface SearchMachineI {
	
	/**
	 * 搜索器，针对从该天气之后的5天涨幅超过10%股票进行统计分析
	 * @param query
	 * @return
	 */
	Map<String,Object> find(StockQuery query);
	
	/**
	 * 根据list查找处于黄金交叉点附近的股票
	 * @param query
	 * @param list
	 * @return
	 */
	List<StockAnalyseBase> findGlodStock(StockQuery query,List<StockAnalyseBase> list);
	
	/**
	 * 根据list查找处于低位的股票：包括震荡走势和下跌到最低点后还没正式攀升的股票
	 * @param query
	 * @param list
	 * @return
	 */
	List<StockAnalyseBase> findLowPoint(StockQuery query,List<StockAnalyseBase> list);
	
	Map<String,Object> searcher(StockQuery query);
	
	List<StockAnalyseBase> findHighVolume(StockQuery query,List<StockAnalyseBase> list);
	
	
	
}
