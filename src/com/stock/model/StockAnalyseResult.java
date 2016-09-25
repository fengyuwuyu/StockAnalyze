package com.stock.model;

import java.sql.Date;

public class StockAnalyseResult {

	private String symbol;
	private Date begin;
	private Date end;
	/** 走势类型 */
	private Integer type;
	/** 涨跌幅度 */
	private float increase = 0f;
	private float minPrice;
	private float maxPrice;

	public StockAnalyseResult() {
	}

	public StockAnalyseResult(String symbol, Date begin, Date end,
			Integer type, float increase, float minPrice, float maxPrice) {
		super();
		this.symbol = symbol;
		this.begin = begin;
		this.end = end;
		this.type = type;
		this.increase = increase;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public float getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	public float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public float getIncrease() {
		return increase;
	}

	public void setIncrease(float increase) {
		this.increase = increase;
	}

	@Override
	public String toString() {
		return "StockAnalyseResult [symbol=" + symbol + ", begin=" + begin
				+ ", end=" + end + ", type=" + type + ", increase=" + increase
				+ ", minPrice=" + minPrice + ", maxPrice=" + maxPrice + "]";
	}

}
