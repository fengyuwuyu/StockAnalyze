package com.stock.model;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.stock.dao.StockMainMapper;
import com.stock.util.CommonsUtil;

/**
 * 找出连续5天涨幅大于10%的股票
 * @author lilei
 *
 */
public class StockMainAnalyse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7325927076713573583L;

	private Logger log = Logger.getLogger(StockMainAnalyse.class);

	private String symbol;
	/** 当天日期 */
	private String now;
	/** 当天涨幅 */
	private float nowIncrease;
	/** 最高价与最低价之差 */
	private float maxMinIncrease;
	/** */
	private int maxMinIncreaseType;
	/** 最低价与当天价格之差 */
	private float minNowIncrease;
	/** */
	private int minNowIncreaseType;
	/** 最高价与最低价相隔天数 */
	private int maxMinDays;
	/** */
	private int maxMinDaysType;
	/** 最低价与当天相隔天数 */
	private int minNowDays;
	/** 最低价与当天相隔天数的类型 */
	private int minNowDaysType;
	/** 连续若干天的股票信息 */
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
					if (temp <= 0) {
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
		if (begin < 0) {
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
		builder.append(dayIncreases.get(begin).getIncrease() + ", ");
		for (int i = begin + 1; i <= end; i++) {
			builder.append(dayIncreases.get(i).getIncrease() + ", ");
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
	 * 
	 * @return
	 */
	public boolean analyse1() {
		if (dayIncreases == null || dayIncreases.size() < 30) {
			return false;
		}
		this.now = dayIncreases.get(dayIncreases.size() - 1).getDay();
		this.nowIncrease = dayIncreases.get(dayIncreases.size() - 1)
				.getIncrease();
		int[] indexs = getMaxAndMin(dayIncreases, 0, dayIncreases.size() - 1);
		return charge(indexs);

	}

	private boolean charge(int[] indexs) {
		int max = indexs[0];
		int min = indexs[1];
		log.info("最高点是：   " + dayIncreases.get(max).getDay() + ", "
				+ dayIncreases.get(max).getClose() + "   最低点是：  "
				+ dayIncreases.get(min).getDay() + " , "
				+ dayIncreases.get(min).getClose());
		float maxClose = dayIncreases.get(max).getClose();
		float minClose = dayIncreases.get(min).getClose();
		float nowClose = dayIncreases.get(dayIncreases.size() - 1).getClose();
		String maxDay = dayIncreases.get(max).getDay();
		String minDay = dayIncreases.get(min).getDay();
		this.maxMinDays = CommonsUtil.getDayDiff(maxDay, minDay);
		this.minNowDays = CommonsUtil.getDayDiff(minDay, now);
		this.maxMinIncrease = (maxClose - minClose) * 100 / maxClose;
		this.minNowIncrease = (nowClose - minClose) * 100 / minClose;
		this.maxMinDaysType = this.maxMinDays / 3 + 1;
		this.minNowDaysType = this.minNowDays / 3 + 1;
		this.maxMinIncreaseType = (int) (this.maxMinIncrease / 5 + 1);
		this.minNowIncreaseType = (int) (this.minNowIncrease / 5 + 1);
		if (this.maxMinIncrease < 40 || this.minNowIncrease > 15
				|| this.nowIncrease < 1) {
			return false;
		}
		log.info(this.toString());
		return true;
	}

	private int[] getMaxAndMin(List<DayIncrease> dayIncreases, int begin,
			int end) {
		DayIncrease maxStock = null;
		DayIncrease minStock = null;
		Integer max = begin;
		Integer min = begin;
		maxStock = dayIncreases.get(begin);
		minStock = dayIncreases.get(begin);
		for (int i = begin; i < end; i++) {
			if (dayIncreases.get(i).getClose() > maxStock.getClose()) {
				maxStock = dayIncreases.get(i);
				max = i;
			}
		}
		for (int i = max; i < end; i++) {
			if (dayIncreases.get(i).getClose() < minStock.getClose()) {
				minStock = dayIncreases.get(i);
				min = i;
			}
		}
		return new int[] { max, min };
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

	public float getMaxMinIncrease() {
		return maxMinIncrease;
	}

	public void setMaxMinIncrease(float maxMinIncrease) {
		this.maxMinIncrease = maxMinIncrease;
	}

	public float getMinNowIncrease() {
		return minNowIncrease;
	}

	public void setMinNowIncrease(float minNowIncrease) {
		this.minNowIncrease = minNowIncrease;
	}

	public int getMaxMinDays() {
		return maxMinDays;
	}

	public void setMaxMinDays(int maxMinDays) {
		this.maxMinDays = maxMinDays;
	}

	public int getMinNowDays() {
		return minNowDays;
	}

	public void setMinNowDays(int minNowDays) {
		this.minNowDays = minNowDays;
	}

	public StringBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(StringBuilder builder) {
		this.builder = builder;
	}

	public int getMaxMinIncreaseType() {
		return maxMinIncreaseType;
	}

	public void setMaxMinIncreaseType(int maxMinIncreaseType) {
		this.maxMinIncreaseType = maxMinIncreaseType;
	}

	public int getMinNowIncreaseType() {
		return minNowIncreaseType;
	}

	public void setMinNowIncreaseType(int minNowIncreaseType) {
		this.minNowIncreaseType = minNowIncreaseType;
	}

	public int getMaxMinDaysType() {
		return maxMinDaysType;
	}

	public void setMaxMinDaysType(int maxMinDaysType) {
		this.maxMinDaysType = maxMinDaysType;
	}

	public int getMinNowDaysType() {
		return minNowDaysType;
	}

	public void setMinNowDaysType(int minNowDaysType) {
		this.minNowDaysType = minNowDaysType;
	}

	@Override
	public String toString() {
		return "StockMainAnalyse [symbol=" + symbol + ", now=" + now
				+ ", nowIncrease=" + nowIncrease + ", maxMinIncrease="
				+ maxMinIncrease + ", maxMinIncreaseType=" + maxMinIncreaseType
				+ ", minNowIncrease=" + minNowIncrease
				+ ", minNowIncreaseType=" + minNowIncreaseType
				+ ", maxMinDays=" + maxMinDays + ", maxMinDaysType="
				+ maxMinDaysType + ", minNowDays=" + minNowDays
				+ ", minNowDaysType=" + minNowDaysType + "]";
	}

	public void initAnalyse(StockMainMapper stockMainMapper) {
		this.index = 50;
		while(index<dayIncreases.size()-5){
			
		}
	}

}
