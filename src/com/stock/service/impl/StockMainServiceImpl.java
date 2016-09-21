package com.stock.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.StockMainMapper;
import com.stock.model.StockMain;
import com.stock.model.StockQuery;
import com.stock.service.StockMainServiceI;
import com.stock.util.MapUtils;

@Service
public class StockMainServiceImpl implements StockMainServiceI {

	private StockMainMapper stockMainMapper;

	@Autowired
	public void setStockMainMapper(StockMainMapper stockMainMapper) {
		this.stockMainMapper = stockMainMapper;
	}

	public Map<String, Object> dataList(StockQuery query) {
		if(query.getBegin()==null||query.getEnd()==null){
			return MapUtils.createSuccessMap("rows",new ArrayList<StockMain>(),"total",0);
		}
		List<StockMain> list = this.stockMainMapper.dataList(query);
		int total = this.stockMainMapper.getTotal(query);
		return MapUtils.createSuccessMap("rows",list,"total",total);
	}

	public Map<String, Object> showChart(StockQuery query) {
		List<StockMain> list = this.stockMainMapper.showChart(query);
		return MapUtils.createSuccessMap("data",list);
	}

}
