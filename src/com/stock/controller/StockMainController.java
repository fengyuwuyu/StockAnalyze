package com.stock.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.model.StockQuery;
import com.stock.service.StockMainServiceI;

@RequestMapping("stockMain")
@Controller
public class StockMainController {
	
	private StockMainServiceI stockMainServiceI;

	@RequestMapping("dataList.do")
	@ResponseBody
	public Map<String,Object> dataList(StockQuery query){
		return stockMainServiceI.dataList(query);
	}
}
