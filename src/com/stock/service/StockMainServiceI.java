package com.stock.service;

import java.util.Map;

import com.stock.model.StockQuery;

public interface StockMainServiceI {

	Map<String,Object> dataList(StockQuery query);
}
