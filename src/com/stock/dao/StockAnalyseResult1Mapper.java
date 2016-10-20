package com.stock.dao;

import com.stock.model.StockAnalyseResult1;

public interface StockAnalyseResult1Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StockAnalyseResult1 record);

    int insertSelective(StockAnalyseResult1 record);

    StockAnalyseResult1 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StockAnalyseResult1 record);

    int updateByPrimaryKey(StockAnalyseResult1 record);
}