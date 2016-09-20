package com.stock.model;

public class StockShort {
	private Integer id;

	private Integer code;

	private String sname;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname == null ? null : sname.trim();
	}

	@Override
	public String toString() {
		return "StockShort [id=" + id + ", code=" + code + ", sname=" + sname
				+ ", type=" + type + "]";
	}

}