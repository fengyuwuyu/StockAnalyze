package com.stock.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.stock.util.CommonsUtil;

public class StockAnalyseBase {

	private String symbol;
	private List<DayIncrease> list;
	/** 统计的日期 */
	private String nowDay;
	/** 改天的涨幅 */
	private float nowIncrease;
	/** 当天的成交量 */
	private long nowVol;

	/** 持续5天的股价涨幅 */
	private float lastIncrease;

	/** 股价走势 */
	private String priceType;
	/** 第一阶段股价涨幅 */
	private float priceIncrease1;
	/** 第二阶段股价涨幅 */
	private float priceIncrease2;
	/** 第一阶段时间间隔 */
	private int priceDay1;
	/** 第二阶段时间间隔 */
	private int priceDay2;

	/** 10天内成交量走势 */
	private String vols;

	/** 连续5天最大震幅走势 */
	private String maxIncreases;
	/** 连续5天最小震幅走势 */
	private String minIncreases;
	private float maxMinIncrease;
	private float minMaxIncrease;
	private float maxNowIncrease;
	private float minNowIncrease;
	private int index;
	private Logger log = Logger.getLogger(StockAnalyseBase.class);
	public List<JunXian> junxians = new ArrayList<JunXian>();

	public StockAnalyseBase() {
	}

	public StockAnalyseBase(String symbol, String nowDay, float nowIncrease,
			long nowVol, float lastIncrease, String priceType,
			float priceIncrease1, float priceIncrease2, int priceDay1,
			int priceDay2, String vols, String maxIncreases, String minIncreases) {
		super();
		this.symbol = symbol;
		this.nowDay = nowDay;
		this.nowIncrease = nowIncrease;
		this.nowVol = nowVol;
		this.lastIncrease = lastIncrease;
		this.priceType = priceType;
		this.priceIncrease1 = priceIncrease1;
		this.priceIncrease2 = priceIncrease2;
		this.priceDay1 = priceDay1;
		this.priceDay2 = priceDay2;
		this.vols = vols;
		this.maxIncreases = maxIncreases;
		this.minIncreases = minIncreases;
	}

	public void initAnalyse(List<StockAnalyseBase> analyseBases) {
		index = 50;
		while (index < list.size() - 50) {
			// if (computeLastIncrease()) {
			if (true) {
				computPrice();
				computVolume();
				computZf();
				analyseBases.add(new StockAnalyseBase(symbol, nowDay,
						nowIncrease, nowVol, lastIncrease, priceType,
						priceIncrease1, priceIncrease2, priceDay1, priceDay2,
						vols, maxIncreases, minIncreases));
				initField();
			}
		}
	}

	// private void insert(StockMainMapper stockMainMapper) {
	// stockMainMapper.insertStockAnaylseBase(this);
	// }

	private void initField() {
		this.nowIncrease = list.get(index).getIncrease();
		this.nowVol = list.get(index).getVolume();
		this.lastIncrease = 0;
		this.priceType = "";
		this.priceIncrease1 = 0;
		this.priceIncrease2 = 0;
		this.priceDay1 = 0;
		this.priceDay2 = 0;
		this.vols = "";
		this.maxIncreases = "";
		this.minIncreases = "";
	}

	private boolean computeLastIncrease() {
		int begin = this.index + 1, end = this.index + 5;
		int max = getMaxMin(begin, end)[0];
		this.lastIncrease = (list.get(max).getClose() - list.get(begin)
				.getClose()) * 100 / list.get(begin).getClose();
		this.index += 3;
		if (this.lastIncrease >= 10) {
			return true;
		}
		return false;
	}

	/**
	 * 计算价格走势
	 */
	private void computPrice() {
		int max = getMaxMin(index - 50, index)[0];
		int min = getMaxMin(index - 50, index)[1];
		DayIncrease maxDay = list.get(max);
		DayIncrease minDay = list.get(min);
		DayIncrease nowDay = list.get(index);
		if (max > min) {
			minMaxIncrease = (maxDay.getClose()-minDay.getClose())*100/minDay.getClose();
			maxNowIncrease = (maxDay.getClose()-nowDay.getClose())*100/maxDay.getClose();
		} else {
			maxMinIncrease = (maxDay.getClose()-minDay.getClose())*100/maxDay.getClose();
			minNowIncrease = (nowDay.getClose()-minDay.getClose())*100/minDay.getClose();
		}

		if (max > min) {
			min = getMaxMin(max, index)[1];
			minDay = list.get(min);
		}
		this.priceIncrease1 = (maxDay.getClose() - minDay.getClose()) * 100
				/ maxDay.getClose();
		if (this.priceIncrease1 <= 0) {
			log.info("max--" + max + "maxDay.getClose()--" + maxDay.getClose()
					+ "--min--" + max + "--minDay.getClose()--"
					+ minDay.getClose());
		}
		this.priceDay1 = CommonsUtil.getDayDiff(maxDay.getDay(),
				minDay.getDay());
		if (minDay.getClose() < list.get(index).getClose()) {
			this.priceType = "21";
			this.priceIncrease2 = (list.get(index).getClose() - minDay
					.getClose()) * 100 / minDay.getClose();
			this.priceDay2 = CommonsUtil.getDayDiff(minDay.getDay(),
					list.get(index).getDay());
		} else {
			this.priceType = "20";
		}
		this.priceDay1 = CommonsUtil.getDayDiff(list.get(max).getDay(), list
				.get(min).getDay());
		this.priceDay2 = CommonsUtil.getDayDiff(list.get(min).getDay(), list
				.get(index).getDay());
	}

	private void computVolume() {
		for (int i = index - 10; i <= index; i++) {
			this.vols += list.get(i).getVolume() + ", ";
		}
		this.vols.substring(0, this.vols.length() - 1);
	}

	private void computZf() {
		for (int i = index - 5; i <= index; i++) {
			float maxPrice = Math.max(list.get(i).getClose(), list.get(i)
					.getOpen());
			float minPrice = Math.min(list.get(i).getClose(), list.get(i)
					.getOpen());
			this.maxIncreases += (list.get(i).getMax() - maxPrice) * 100
					/ maxPrice + ", ";
			this.minIncreases += (minPrice - list.get(i).getMin()) * 100
					/ minPrice + ", ";
		}
		this.maxIncreases.substring(0, this.maxIncreases.length() - 1);
		this.minIncreases.substring(0, this.minIncreases.length() - 1);
	}

	private int[] getMaxMin(int begin, int end) {
		int max = begin, min = begin;
		float maxClose = list.get(begin).getClose();
		float minClose = list.get(begin).getClose();
		for (int i = begin; i < end; i++) {
			if (maxClose < list.get(i).getClose()) {
				max = i;
			}
			if (list.get(i).getClose() < minClose) {
				min = i;
			}
		}
		return new int[] { max, min };
	}

	public void initJunXian() {
		if (list != null && list.size() > 0) {
			for (int i = 64; i < list.size(); i++) {
				String time = list.get(i).getDay();
				float day = list.get(i).getClose();
				float five = computeJunXian(i, 5);
				float nine = computeJunXian(i, 9);
				float thirteen = computeJunXian(i, 13);
				float nineteen = computeJunXian(i, 19);
				float twentySeven = computeJunXian(i, 27);
				float thirtyNine = computeJunXian(i, 39);
				float fourtyNine = computeJunXian(i, 49);
				float sixtyFive = computeJunXian(i, 65);
				JunXian domain = new JunXian(symbol, time, day, five, nine,
						thirteen, nineteen, twentySeven, thirtyNine,
						fourtyNine, sixtyFive);
				junxians.add(domain);
			}
		}
	}

	private float computeJunXian(int i, int count) {
		float sum = 0;
		for (int j = i - count + 1; j <= i; j++) {
			sum += list.get(j).getClose();
		}
		return sum / count;
	}

	public float getMaxMinIncrease() {
		return maxMinIncrease;
	}

	public void setMaxMinIncrease(float maxMinIncrease) {
		this.maxMinIncrease = maxMinIncrease;
	}

	public float getMinMaxIncrease() {
		return minMaxIncrease;
	}

	public void setMinMaxIncrease(float minMaxIncrease) {
		this.minMaxIncrease = minMaxIncrease;
	}

	public float getMaxNowIncrease() {
		return maxNowIncrease;
	}

	public void setMaxNowIncrease(float maxNowIncrease) {
		this.maxNowIncrease = maxNowIncrease;
	}

	public float getMinNowIncrease() {
		return minNowIncrease;
	}

	public void setMinNowIncrease(float minNowIncrease) {
		this.minNowIncrease = minNowIncrease;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public List<DayIncrease> getList() {
		return list;
	}

	public void setList(List<DayIncrease> list) {
		this.list = list;
	}

	public String getNowDay() {
		return nowDay;
	}

	public void setNowDay(String nowDay) {
		this.nowDay = nowDay;
	}

	public float getNowIncrease() {
		return nowIncrease;
	}

	public void setNowIncrease(float nowIncrease) {
		this.nowIncrease = nowIncrease;
	}

	public long getNowVol() {
		return nowVol;
	}

	public void setNowVol(int nowVol) {
		this.nowVol = nowVol;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public float getPriceIncrease1() {
		return priceIncrease1;
	}

	public void setPriceIncrease1(float priceIncrease1) {
		this.priceIncrease1 = priceIncrease1;
	}

	public float getPriceIncrease2() {
		return priceIncrease2;
	}

	public void setPriceIncrease2(float priceIncrease2) {
		this.priceIncrease2 = priceIncrease2;
	}

	public int getPriceDay1() {
		return priceDay1;
	}

	public void setPriceDay1(int priceDay1) {
		this.priceDay1 = priceDay1;
	}

	public int getPriceDay2() {
		return priceDay2;
	}

	public void setPriceDay2(int priceDay2) {
		this.priceDay2 = priceDay2;
	}

	public String getVols() {
		return vols;
	}

	public void setVols(String vols) {
		this.vols = vols;
	}

	public String getMaxIncreases() {
		return maxIncreases;
	}

	public void setMaxIncreases(String maxIncreases) {
		this.maxIncreases = maxIncreases;
	}

	public String getMinIncreases() {
		return minIncreases;
	}

	public void setMinIncreases(String minIncreases) {
		this.minIncreases = minIncreases;
	}

	public float getLastIncrease() {
		return lastIncrease;
	}

	public void setLastIncrease(float lastIncrease) {
		this.lastIncrease = lastIncrease;
	}

	@Override
	public String toString() {
		return "StockAnalyseBase [symbol=" + symbol + ", nowDay=" + nowDay
				+ ", nowIncrease=" + nowIncrease + ", nowVol=" + nowVol
				+ ", lastIncrease=" + lastIncrease + ", priceType=" + priceType
				+ ", priceIncrease1=" + priceIncrease1 + ", priceIncrease2="
				+ priceIncrease2 + ", priceDay1=" + priceDay1 + ", priceDay2="
				+ priceDay2 + ", vols=" + vols + ", maxIncreases="
				+ maxIncreases + ", minIncreases=" + minIncreases + ", index="
				+ index + "]";
	}
}
