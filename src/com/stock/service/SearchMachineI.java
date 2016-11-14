package com.stock.service;

import java.util.Map;

import com.stock.model.StockQuery;

public interface SearchMachineI {

	Map<String,Object> find(StockQuery query);
	
}
