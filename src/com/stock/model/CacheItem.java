package com.stock.model;

public class CacheItem {

	/** 前一刻的价格 */
	private float price;
	/** 昨天收盘价格 */
	private float close;

	public CacheItem() {
	}

	public CacheItem(float price, float close) {
		this.price = price;
		this.close = close;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getClose() {
		return close;
	}

	public void setClose(float close) {
		this.close = close;
	}

	@Override
	public String toString() {
		return "CacheItem [price=" + price + ", close=" + close + "]";
	}

}
