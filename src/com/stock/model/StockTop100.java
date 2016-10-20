package com.stock.model;

import java.util.List;

public class StockTop100 implements Comparable<StockTop100> {

	private String symbol;
	private List<Float> increases;
	private float incre;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public List<Float> getIncreases() {
		return increases;
	}

	public void setIncreases(List<Float> increases) {
		this.increases = increases;
	}

	public float getIncre() {
		return incre;
	}

	public void setIncre(float incre) {
		this.incre = incre;
	}

	@Override
	public String toString() {
		return "StockTop100 [symbol=" + symbol + ", increases=" + increases
				+ ", incre=" + incre + "]";
	}

	@Override
	public int compareTo(StockTop100 o) {
		return this.incre<=o.getIncre()?1:-1;
	}

}
