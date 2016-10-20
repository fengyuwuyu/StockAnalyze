package com.stock.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.model.VacationParam;
import com.stock.service.HolidayServiceI;

@Controller
@RequestMapping(value = "holidayController")
public class HolidayController {
	
	private HolidayServiceI holidayService;

	@Autowired
	public void setholidayService(HolidayServiceI holidayService) {
		this.holidayService = holidayService;
	}
	
	/**
	 * 根据条件分页查询部门
	 * @return Map
	 * @author BYP
	 * @date 2016-01-20
	 * */
	@RequestMapping(value = "dataList.do")
	@ResponseBody
	public Map<String, Object> dataList(VacationParam record,HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> map = this.holidayService.dataList(record);
		return map;
	}
	
	/**
	 * 
	 * @param record
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "save.do")
	@ResponseBody
	public Map<String, Object> save(String vacations,HttpServletRequest request,HttpSession session,HttpServletResponse response) {
		Map<String, Object> map = this.holidayService.save(vacations);
		return map;
	}
	
}
