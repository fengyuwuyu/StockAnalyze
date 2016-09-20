package com.stock.controller;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.model.StockQuery;
import com.stock.service.StockServiceI;

@Controller
@RequestMapping("stock")
public class StockController {

	private StockServiceI detailSaveServiceI;

	@Autowired
	public void setDetailSaveServiceI(StockServiceI detailSaveServiceI) {
		this.detailSaveServiceI = detailSaveServiceI;
	}

	@RequestMapping("dataList.do")
	@ResponseBody
	public Map<String, Object> dataList(Date date) throws Exception {
		return this.detailSaveServiceI.volBigIncrease(date);
	}
	
	@RequestMapping("saveBigIncrease.do")
	@ResponseBody
	public Map<String, Object> saveBigIncrease(Date date) throws Exception {
		return this.detailSaveServiceI.saveBigIncrease();
	}
	
	@RequestMapping("priceDownVolUp.do")
	@ResponseBody
	public Map<String, Object> priceDownVolUp(Date date) throws Exception {
		return this.detailSaveServiceI.priceDownVolUp(date);
	}
	
	@RequestMapping("maxDown.do")
	@ResponseBody
	public Map<String, Object> maxDown(String maxDay,String minDay) throws Exception {
		return this.detailSaveServiceI.maxDown(maxDay,minDay);
	}
	
	@RequestMapping("queryPriceLargeChange.do")
	@ResponseBody
	public Map<String,Object> queryPriceLargeChange(StockQuery query) throws Exception{
		return this.detailSaveServiceI.queryPriceLargeChange(query);
	}
}
