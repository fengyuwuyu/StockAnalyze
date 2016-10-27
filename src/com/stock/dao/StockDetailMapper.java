package com.stock.dao;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.stock.model.StockDetail;
import com.stock.model.StockVol;

public interface StockDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Map<String, Object> map);

    int insertSelective(LinkedHashMap<String, Object> record);

    StockDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockDetail record);

    int updateByPrimaryKey(StockDetail record);
    
    List<StockVol> dataList(Date date);
    
    List<StockVol> dataList1(Date date);

	void saveBigIncrease(StockVol vol);

	int selectCountByTime(String day);

	List<String> selectAllCode();

	String selectLastDay(String symbol);
}