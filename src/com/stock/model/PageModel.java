package com.stock.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 分页参数实体类
 * @date 2016-03-21
 * */
public class PageModel {
	
	@JsonIgnore  
	private Integer page;
	@JsonIgnore  
	private Integer rows;
	@JsonIgnore  
	private Integer start;
	
	
	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	public Integer getRows() {
		return rows;
	}
	
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
	public Integer getStart() {
		return (this.page - 1) * this.rows;
	}
	
	public void setStart(Integer start) {
		this.start = start;
	}

	
	
	

}
