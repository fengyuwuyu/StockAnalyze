package com.stock.model;

import java.sql.Date;

public class StockVolume {

	private float price1;

	private Long volume;

	private Date time;

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public float getPrice1() {
		return price1;
	}

	public void setPrice1(float price1) {
		this.price1 = price1;
	}

	@Override
	public String toString() {
		return "StockVolume [price1=" + price1 + ", volume=" + volume
				+ ", time=" + time + "]";
	}

}
