package com.stock.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.model.StockQuery;
import com.stock.service.SearchMachineI;

@RequestMapping("searcher")
@Controller
public class SearchMachineController {

	private SearchMachineI searchMachineI;

	@Autowired
	public void setSearchMachineI(SearchMachineI searchMachineI) {
		this.searchMachineI = searchMachineI;
	}
	
	@RequestMapping("find.do")
	@ResponseBody
	public Map<String,Object> find(StockQuery query){
		return searchMachineI.find(query);
	}
	
	@RequestMapping("query.do")
	@ResponseBody
	public Map<String,Object> query(StockQuery query){
		return searchMachineI.query(query);
	}
}
