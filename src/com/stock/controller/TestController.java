package com.stock.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.service.InitStockServiceI;
import com.stock.service.StockAnalyseJobI;
import com.stock.service.StockMainServiceI;
import com.stock.service.StockServiceI;
import com.stock.util.MapUtils;

@Controller
public class TestController {

	private StockServiceI detailSaveServiceI;

	private StockMainServiceI stockMainServiceI;
	private InitStockServiceI initStockServiceI;
	private StockAnalyseJobI stockAnalyseJobI;
	
	@Autowired
	public void setStockAnalyseJobI(StockAnalyseJobI stockAnalyseJobI) {
		this.stockAnalyseJobI = stockAnalyseJobI;
	}

	@Autowired
	public void setInitStockServiceI(InitStockServiceI initStockServiceI) {
		this.initStockServiceI = initStockServiceI;
	}

	@Autowired
	public void setStockMainServiceI(StockMainServiceI stockMainServiceI) {
		this.stockMainServiceI = stockMainServiceI;
	}

	@Autowired
	public void setDetailSaveServiceI(StockServiceI detailSaveServiceI) {
		this.detailSaveServiceI = detailSaveServiceI;
	}

	@RequestMapping("test.do")
	@ResponseBody
	public Map<String, Object> test() {
		try {
			boolean run = true;
			for(int i = 20;i>0;i--){
				while(run){
					run = this.stockMainServiceI.analyse1(i);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MapUtils.createFailedMap();
	}

	@RequestMapping("test1.do")
	@ResponseBody
	public Map<String, Object> test1() throws Exception {
		this.stockAnalyseJobI.findStock();
		return MapUtils.createSuccessMap();
		// this.detailSaveServiceI.volBigIncrease();
	}
}
