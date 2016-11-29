package com.stock.model;

public class CacheItem {

	private String symbol;
	/** 昨天收盘价格 */
	private float yestClose;
	/** 前一刻的价格 */
	private float prePrice;
	/** 当前价格 */
	private float nowPrice;
	/** 昨天成交量 */
	private long yestVol;
	/** 前一刻成交量 */
	private long preVol;
	/** 当前成交量 */
	private long nowVol;
	/** 当天成交量涨幅 */
//	private float currDayVol;
	/** 相比前一刻成交量涨幅 */
//	private float currVol;
	/** 当天股价涨幅 */
//	private float currDayIncrease;
	/** 相比前一次股价涨幅 */
//	private float currIncrease;
	/** 5档委买总量 */
	private int buyVol;
	/** 5档委卖总量 */
	private int sellVol;

	public CacheItem() {
	}

	public CacheItem(String symbol, float nowPrice, long nowVol) {
		this.symbol = symbol;
		this.nowPrice = nowPrice;
		this.nowVol = nowVol;
	}



	/** 昨天成交量 */
	public long getYestVol() {
		return yestVol;
	}

	public void setYestVol(long yestVol) {
		this.yestVol = yestVol;
	}

	/** 当天成交量涨幅 */
	public float getCurrDayVol(long vol) {
		setNowVol(vol);
		return (nowVol-yestVol)*100/yestVol;
	}

	/** 前一刻成交量 */
	public long getPreVol() {
		return preVol;
	}

	public void setPreVol(long preVol) {
		this.preVol = preVol;
	}

	/** 当前成交量涨幅(调用前需先调用getCurrDayVol方法) */
	public float getCurrVol(long vol) {
		return (nowVol-preVol)*100/preVol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/** 昨天收盘价格 */
	public float getYestClose() {
		return yestClose;
	}

	public void setYestClose(float yestClose) {
		this.yestClose = yestClose;
	}

	/** 当天涨幅 */
	public float getCurrDayIncrease(float setPrice) {
		setNowPrice(setPrice);
		return (nowPrice-yestClose)*100/yestClose;
	}

	/** 相比前一次涨幅 */
	public float getCurrIncrease(float setPrice) {
		return (nowPrice-prePrice)*100/prePrice;
	}

	/** 5档委买总量 */
	public int getBuyVol() {
		return buyVol;
	}

	public void setBuyVol(int buyVol) {
		this.buyVol = buyVol;
	}

	/** 5档委卖总量 */
	public int getSellVol() {
		return sellVol;
	}

	public void setSellVol(int sellVol) {
		this.sellVol = sellVol;
	}
	
	/** 前一刻的价格 */
	public float getPrePrice() {
		return prePrice;
	}

	public void setPrePrice(float prePrice) {
		this.prePrice = prePrice;
	}

	/** 当前价格*/
	public float getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(float nowPrice) {
		this.prePrice = this.nowPrice;
		this.nowPrice = nowPrice;
	}

	/** 当前成交量*/
	public long getNowVol() {
		return nowVol;
	}

	public void setNowVol(long nowVol) {
		this.preVol = this.nowVol;
		this.nowVol = nowVol;
	}

}
