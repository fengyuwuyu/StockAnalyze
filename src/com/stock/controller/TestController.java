package com.stock.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.dao.StockMainMapper;
import com.stock.service.InitStockServiceI;
import com.stock.service.SearchMachineI;
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
	private StockMainMapper stockMainMapper;
	private SearchMachineI searchMachineI;
	private Logger log = Logger.getLogger(TestController.class);
	
	@Autowired
	public void setSearchMachineI(SearchMachineI searchMachineI) {
		this.searchMachineI = searchMachineI;
	}

	@Autowired
	public void setStockMainMapper(StockMainMapper stockMainMapper) {
		this.stockMainMapper = stockMainMapper;
	}

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
//					run = this.stockMainServiceI.analyse1(i);
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
		initStockServiceI.initStockEveryDay();
//		stockAnalyseJobI.initStockAnalyse();
//		List<String> symbols = stockMainMapper.selectAll();
//		int index = 0;
//		if(symbols!=null&&symbols.size()>0){
//			for (String symbol : symbols) {
//				initStockServiceI.initJunX(symbol);
//				log.info("--------------------------"+(++index));
//			}
//		}
		return MapUtils.createSuccessMap();
		// this.detailSaveServiceI.volBigIncrease();
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add("asf"+i);
		}
		System.out.println(list.toString());
	}
}
