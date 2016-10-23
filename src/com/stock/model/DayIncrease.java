package com.stock.model;

public class DayIncrease {

	private String day;
	private float increase;
	private float close;

	public float getClose() {
		return close;
	}

	public void setClose(float close) {
		this.close = close;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public float getIncrease() {
		return increase;
	}

	public void setIncrease(float increase) {
		this.increase = increase;
	}

	@Override
	public String toString() {
		return "DayIncrease [day=" + day + ", increase=" + increase
				+ ", close=" + close + "]";
	}

}
