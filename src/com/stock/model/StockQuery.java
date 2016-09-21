package com.stock.model;

import java.sql.Date;

/**
 * @author lilei
 * 
 */
public class StockQuery extends PageModel {

	private Date begin;
	private Date end;
	private Boolean down = true;
	private String symbol;
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Boolean getDown() {
		return down;
	}

	public void setDown(Boolean down) {
		this.down = down;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return "StockQuery [begin=" + begin + ", end=" + end + ", down=" + down
				+ ", symbol=" + symbol + ", type=" + type + ", toString()="
				+ super.toString() + "]";
	}

}
