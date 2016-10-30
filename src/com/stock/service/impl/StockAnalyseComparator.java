package com.stock.service.impl;

import java.util.Comparator;

import com.stock.model.StockMainAnalyse;

public class StockAnalyseComparator implements Comparator<StockMainAnalyse> {

	@Override
	public int compare(StockMainAnalyse o1, StockMainAnalyse o2) {
		return o1.getMinNowIncrease()>=o2.getMinNowIncrease()?1:-1;
	}

}
