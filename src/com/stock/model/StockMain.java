package com.stock.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class StockMain {
	private Integer id;

	private String symbol;

	@JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
	private Date day;

	private Float open;

	private Float close;

	private Float max;

	private Float min;

	private Long volume;

	private Float increase;

	public StockMain() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StockMain(String symbol, Date day, Float open, Float close,
			Float max, Float min, Long volume, Float increase) {
		super();
		this.symbol = symbol;
		this.day = day;
		this.open = open;
		this.close = close;
		this.max = max;
		this.min = min;
		this.volume = volume;
		this.increase = increase;
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

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Float getOpen() {
		return open;
	}

	public void setOpen(Float open) {
		this.open = open;
	}

	public Float getClose() {
		return close;
	}

	public void setClose(Float close) {
		this.close = close;
	}

	public Float getMax() {
		return max;
	}

	public void setMax(Float max) {
		this.max = max;
	}

	public Float getMin() {
		return min;
	}

	public void setMin(Float min) {
		this.min = min;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Float getIncrease() {
		return increase;
	}

	public void setIncrease(Float increase) {
		this.increase = increase;
	}

	@Override
	public String toString() {
		return "StockMain [id=" + id + ", symbol=" + symbol + ", day=" + day
				+ ", open=" + open + ", close=" + close + ", max=" + max
				+ ", min=" + min + ", volume=" + volume + ", increase="
				+ increase + "]";
	}

}