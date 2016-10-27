package com.stock.model;

import java.util.List;

public class StockMainAnalyse {

	private String symbol;
	/** 当天日期 */
	private String now;
	/** 当天涨幅 */
	private float nowIncrease;
	private List<DayIncrease> dayIncreases;
	/** 从now开始的若干天的持续涨幅 */
	private float lastIncrease;
	private String type;
	private int count;
	private int index = -1;
	private int analyseType = 0;
	private StringBuilder builder = new StringBuilder();
	private String data;

	/**
	 * 判断从now开始的涨幅是否大于20，若大于20，则将其插入到数据库中
	 * 
	 * @param now
	 */
	public boolean analyse(String n, int c) {
		this.now = n;
		this.count = c;
		this.analyseType = c;
		if (dayIncreases != null && dayIncreases.size() > 0) {
			for (int i = 0; i < dayIncreases.size(); i++) {
				float temp = dayIncreases.get(i).getIncrease();
				if (now.equals(dayIncreases.get(i).getDay())) {
					index = i;
					nowIncrease = temp;
				}
				if (index != -1) {
					lastIncrease += temp;
					if(temp <= 0){
						chargeType();
						return true;
					}
				}
			}
		}
		return false;
	}

	private void chargeType() {
		int begin = index - count;
		if(begin<0){
			return;
		}
		getStockAnalyse(begin);
	}

	/**
	 * 找到list中股价最高的
	 * 
	 * @param list
	 * @return
	 */
	private void getStockAnalyse(int begin) {
		DayIncrease maxStock = null;
		DayIncrease minStock = null;
		Integer end = index - 1;
		Integer max = begin;
		Integer min = begin;
		maxStock = dayIncreases.get(begin);
		minStock = dayIncreases.get(begin);
		builder.append(dayIncreases.get(begin).getIncrease()+", ");
		for (int i = begin + 1; i <= end; i++) {
			builder.append(dayIncreases.get(i).getIncrease()+", ");
			if (dayIncreases.get(i).getClose() > maxStock.getClose()) {
				maxStock = dayIncreases.get(i);
				max = i;
			} else if (dayIncreases.get(i).getClose() < minStock.getClose()) {
				minStock = dayIncreases.get(i);
				min = i;
			}
		}
		this.data = builder.toString();
		DayIncrease beginStock = dayIncreases.get(begin);
		DayIncrease endStock = dayIncreases.get(end);
		float beginClose = beginStock.getClose();
		float endClose = endStock.getClose();
		float minClose = minStock.getClose();
		float maxClose = maxStock.getClose();
		int type1 = 0;
		int type2 = 0;
		int type3 = 0;
		if ((maxClose - minClose) < 10) {
			type1 = 3;
			type2 = 3;
			type3 = 3;
		} else {
			if (max > min) {
				type2 = 1;
				if ((beginClose - minClose) >= 10) {
					type1 = 2;
				} else {
					type1 = 3;
				}
				if ((maxClose - endClose) >= 10) {
					type3 = 2;
				} else {
					type3 = 3;
				}
			} else {
				type2 = 2;
				if ((maxClose - beginClose) > 10) {
					type1 = 1;
				} else {
					type1 = 3;
				}
				if ((endClose - minClose) >= 10) {
					type3 = 1;
				} else {
					type3 = 3;
				}
			}
		}
		this.type = "" + type1 + type2 + type3;
	}
	
	/**
	 * 判断连续的50天内波动情况
	 * @return
	 */
	public boolean analyse1(){
		if(dayIncreases==null||dayIncreases.size()<20){
			return false;
		}
		
		
		
		return true;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getNow() {
		return now;
	}

	public void setNow(String now) {
		this.now = now;
	}

	public float getNowIncrease() {
		return nowIncrease;
	}

	public void setNowIncrease(float nowIncrease) {
		this.nowIncrease = nowIncrease;
	}

	public List<DayIncrease> getDayIncreases() {
		return dayIncreases;
	}

	public void setDayIncreases(List<DayIncrease> dayIncreases) {
		this.dayIncreases = dayIncreases;
	}

	public float getLastIncrease() {
		return lastIncrease;
	}

	public void setLastIncrease(float lastIncrease) {
		this.lastIncrease = lastIncrease;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAnalyseType() {
		return analyseType;
	}

	public void setAnalyseType(int analyseType) {
		this.analyseType = analyseType;
	}
	
	

	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return "StockMainAnalyse [symbol=" + symbol + ", now=" + now
				+ ", nowIncrease=" + nowIncrease + ", dayIncreases="
				+ dayIncreases + ", lastIncrease=" + lastIncrease + ", type="
				+ type + ", count=" + count + ", index=" + index
				+ ", analyseType=" + analyseType + ", builder=" + builder
				+ ", data=" + data + "]";
	}

}
