package com.stock.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.model.StockQuery;
import com.stock.service.StockMainServiceI;

@RequestMapping("stockMain")
@Controller
public class StockMainController {

	private StockMainServiceI stockMainServiceI;

	@Autowired
	public void setStockMainServiceI(StockMainServiceI stockMainServiceI) {
		this.stockMainServiceI = stockMainServiceI;
	}

	@RequestMapping("dataList.do")
	@ResponseBody
	public Map<String, Object> dataList(StockQuery query) {
		return stockMainServiceI.dataList(query);
	}
	
	@RequestMapping("dataList1.do")
	@ResponseBody
	public Map<String, Object> dataList1(StockQuery query) {
		return stockMainServiceI.dataList1(query);
	}
	
	@RequestMapping("showChart.do")
	@ResponseBody
	public Map<String,Object> showChart(StockQuery query){
		return this.stockMainServiceI.showChart(query);
	}
	
	@RequestMapping("updateIncreaseRate.do")
	@ResponseBody
	public Map<String,Object> updateIncreaseRate(){
//		return this.stockMainServiceI.updateIncreaseRate();
		return null;
	}
}
