package com.stock.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.stock.model.StockAnalyseResult;
import com.stock.model.StockMain;
import com.stock.model.StockQuery;

public interface StockMainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Map<String, Object> map);

    int insertSelective(StockMain record);

    StockMain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockMain record);

    int updateByPrimaryKey(StockMain record);

	List<StockMain> dataList(StockQuery query);

	int getTotal(StockQuery query);
	
	List<StockMain> dataList1(StockQuery query);

	int getTotal1(StockQuery query);

	List<StockMain> showChart(StockQuery query);

	List<String> selectSymbols();

	void insertStockAyalyseResult(StockAnalyseResult stockAnalyseResult);
	
	void updateStatus(Map<String,Object> map);

	List<String> selectAll();

	List<StockAnalyseResult> select1(String symbol);
}