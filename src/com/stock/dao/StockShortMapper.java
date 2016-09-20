package com.stock.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.stock.model.StockShort;

public interface StockShortMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockShort record);

    int insertSelective(StockShort record);

    StockShort selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockShort record);

    int updateByPrimaryKey(StockShort record);

	void insertSelectiveMap(LinkedHashMap<String, Object> map);

	List<String> selectAll();
}