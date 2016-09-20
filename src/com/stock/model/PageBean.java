package com.stock.model;

import java.util.List;

public class PageBean<T> {

	private int page;// ":0,"
	private int count;// ":24,"
	private String order;// ":"
	private int total;// ":1097,"
	private int pagecount;// ":46,"
	private String time;// ":"2016-06-24 08:34:50",
	private List<T> list;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPagecount() {
		return pagecount;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "PageBean [page=" + page + ", count=" + count + ", order="
				+ order + ", total=" + total + ", pagecount=" + pagecount
				+ ", time=" + time + ", list=" + list + "]";
	}

}
