package com.stock.model;

import java.sql.Date;

public class StockDetail extends PageModel {
	private Integer id;

	private Float high;

	private Float low;

	private Long mcap;

	private Float open;

	private Double pe;

	private Double percent;

	private Float price;

	private String sname;

	private String symbol;

	private Date time;

	private Integer turnover;

	private Float updown;

	private Integer volume;

	private Float yestclose;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getHigh() {
		return high;
	}

	public void setHigh(Float high) {
		this.high = high;
	}

	public Float getLow() {
		return low;
	}

	public void setLow(Float low) {
		this.low = low;
	}

	public Long getMcap() {
		return mcap;
	}

	public void setMcap(Long mcap) {
		this.mcap = mcap;
	}

	public Float getOpen() {
		return open;
	}

	public void setOpen(Float open) {
		this.open = open;
	}

	public Double getPe() {
		return pe;
	}

	public void setPe(Double pe) {
		this.pe = pe;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname == null ? null : sname.trim();
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol == null ? null : symbol.trim();
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getTurnover() {
		return turnover;
	}

	public void setTurnover(Integer turnover) {
		this.turnover = turnover;
	}

	public Float getUpdown() {
		return updown;
	}

	public void setUpdown(Float updown) {
		this.updown = updown;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Float getYestclose() {
		return yestclose;
	}

	public void setYestclose(Float yestclose) {
		this.yestclose = yestclose;
	}

	@Override
	public String toString() {
		return "StockDetail [id=" + id + ", high=" + high + ", low=" + low
				+ ", mcap=" + mcap + ", open=" + open + ", pe=" + pe
				+ ", percent=" + percent + ", price=" + price + ", sname="
				+ sname + ", symbol=" + symbol + ", time=" + time
				+ ", turnover=" + turnover + ", updown=" + updown + ", volume="
				+ volume + ", yestclose=" + yestclose + "]";
	}

}