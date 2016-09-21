package com.stock.dao;

import java.util.List;
import java.util.Map;

import com.stock.model.StockMain;
import com.stock.model.StockQuery;

public interface StockMainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Map<String, Object> map);

    int insertSelective(StockMain record);

    StockMain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockMain record);

    int updateByPrimaryKey(StockMain record);

	List<StockMain> selectBy1(StockQuery query);

	List<StockMain> dataList(StockQuery query);

	int getTotal(StockQuery query);

	List<StockMain> showChart(StockQuery query);
}