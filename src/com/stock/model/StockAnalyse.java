package com.stock.model;

public class StockAnalyse {

	/** 起始位置 */
	private Integer begin;
	/** 结束位置 */
	private Integer end;
	/** 最大股价 */
	private StockMain maxStock;
	/** 最小股价 */
	private StockMain minStock;
	/** 股价最大所在的索引 */
	private Integer max;
	/** 股价最小所在的索引 */
	private Integer min;
	/** 一段时间内的走势类型 */
	private Integer zsType = 0;

	private StockMain beginStock;

	private StockMain endStock;

	public StockAnalyse() {
	}

	public StockAnalyse(Integer begin, Integer end, StockMain maxStock,
			StockMain minStock, Integer max, Integer min, Integer zsType,
			StockMain beginStock, StockMain endStock) {
		super();
		this.begin = begin;
		this.end = end;
		this.maxStock = maxStock;
		this.minStock = minStock;
		this.max = max;
		this.min = min;
		this.zsType = zsType;
		this.beginStock = beginStock;
		this.endStock = endStock;
	}

	public StockMain getBeginStock() {
		return beginStock;
	}

	public void setBeginStock(StockMain beginStock) {
		this.beginStock = beginStock;
	}

	public StockMain getEndStock() {
		return endStock;
	}

	public void setEndStock(StockMain endStock) {
		this.endStock = endStock;
	}

	public Integer getBegin() {
		return begin;
	}

	public void setBegin(Integer begin) {
		this.begin = begin;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public StockMain getMaxStock() {
		return maxStock;
	}

	public void setMaxStock(StockMain maxStock) {
		this.maxStock = maxStock;
	}

	public StockMain getMinStock() {
		return minStock;
	}

	public void setMinStock(StockMain minStock) {
		this.minStock = minStock;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getZsType() {
		return zsType;
	}

	public void setZsType(Integer zsType) {
		this.zsType = zsType;
	}

	@Override
	public String toString() {
		return "StockAnalyse [begin=" + begin + ", end=" + end + ", maxStock="
				+ maxStock + ", minStock=" + minStock + ", max=" + max
				+ ", min=" + min + ", zsType=" + zsType + ", beginStock="
				+ beginStock + ", endStock=" + endStock + "]";
	}

}
