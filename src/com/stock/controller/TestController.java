package com.stock.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.service.StockServiceI;
import com.stock.util.MapUtils;

@Controller
public class TestController {

	private StockServiceI detailSaveServiceI;

	@Autowired
	public void setDetailSaveServiceI(StockServiceI detailSaveServiceI) {
		this.detailSaveServiceI = detailSaveServiceI;
	}

	@RequestMapping("test.do")
	@ResponseBody
	public Map<String, Object> test()  {
		try {
			this.detailSaveServiceI.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MapUtils.createSuccessMap();
	}
	
	@RequestMapping("test1.do")
	@ResponseBody
	public Map<String, Object> test1() throws Exception {
		this.detailSaveServiceI.initStock();
//		this.detailSaveServiceI.volBigIncrease();
		return MapUtils.createSuccessMap();
	}
}
