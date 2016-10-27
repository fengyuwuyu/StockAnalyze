package com.stock.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.StockMainMapper;
import com.stock.model.StockMainAnalyse;
import com.stock.service.StockAnalyseJobI;

@Service
public class StockAnalyseJobImpl implements StockAnalyseJobI {
	
	private StockMainMapper stockMainMapper;
	
	private Logger log = Logger.getLogger(StockAnalyseJobImpl.class);
	
	@Autowired
	public void setStockMainMapper(StockMainMapper stockMainMapper) {
		this.stockMainMapper = stockMainMapper;
	}



	/**
	 * 每天五点后分析现有数据，找出已经跌到底部且将要上涨的股票，并插入到数据库中
	 */
	@Override
	public void findStock() {
		List<StockMainAnalyse> stocks = this.stockMainMapper.findStock();
		List<StockMainAnalyse> result = new ArrayList<StockMainAnalyse>();
		for (StockMainAnalyse stockMainAnalyse : stocks) {
			if(stockMainAnalyse.analyse1()){
				result.add(stockMainAnalyse);
			}
		}
		Collections.sort(result, new Comparator<StockMainAnalyse>() {

			@Override
			public int compare(StockMainAnalyse o1, StockMainAnalyse o2) {
				
				return 0;
			}
			
		});
	}

}
