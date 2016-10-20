package com.stock.model;

import java.util.Date;

public class StockAnalyseResult1 {
	private Integer id;

	private String symbol;

	private Date begin;

	private Date end;

	private String type;

	private Float increase;

	private Float incease1;

	private Float incease2;

	private Float incease3;

	public StockAnalyseResult1() {
	}

	public StockAnalyseResult1(String symbol, Date begin, Date end,
			String type, Float increase, Float incease1, Float incease2,
			Float incease3) {
		this.symbol = symbol;
		this.begin = begin;
		this.type = type;
		this.increase = increase;
		this.incease1 = incease1;
		this.incease2 = incease2;
		this.incease3 = incease3;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol == null ? null : symbol.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public Float getIncrease() {
		return increase;
	}

	public void setIncrease(Float increase) {
		this.increase = increase;
	}

	public Float getIncease1() {
		return incease1;
	}

	public void setIncease1(Float incease1) {
		this.incease1 = incease1;
	}

	public Float getIncease2() {
		return incease2;
	}

	public void setIncease2(Float incease2) {
		this.incease2 = incease2;
	}

	public Float getIncease3() {
		return incease3;
	}

	public void setIncease3(Float incease3) {
		this.incease3 = incease3;
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

}