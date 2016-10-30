package com.stock.model;

public class DayIncrease {

	private String day;
	private float increase;
	private float close;
	private float max;
	private float min;
	private long volume;
	/** 向上震幅 */
	private float maxIncrease;
	/** 向下震幅 */
	private float minIncrease;
	private float open;

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

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public long getVolume() {
		long result = 0;
		if(volume<10000){
			result = (volume/1000)*1000;
		}else {
			long sub = 10000;
			int i = (int) (volume/10000);
			while(i>10){
				i /= 10;
				sub*=10;
			}
			result = i*sub;
		}
		return result;
	}

	public void setVolume(long volume) {
		this.volume = volume;
	}

	public float getOpen() {
		return open;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public float getMaxIncrease() {
		float price = Math.max(open, close);
		return  (max - price)*100/price;
	}

	public float getMinIncrease() {
		float price = Math.min(open,close);
		return (price-minIncrease)*100/price;
	}

	@Override
	public String toString() {
		return "DayIncrease [day=" + day + ", increase=" + increase
				+ ", close=" + close + ", max=" + max + ", min=" + min
				+ ", volume=" + volume + ", maxIncrease=" + maxIncrease
				+ ", minIncrease=" + minIncrease + ", open=" + open + "]";
	}

}
