package com.stock.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.HolidayMapper;
import com.stock.model.Holiday;
import com.stock.model.VacationParam;
import com.stock.service.HolidayServiceI;

@Service("holidayService")
public class HolidayServiceImpl implements HolidayServiceI {
	
	private HolidayMapper holidayMapper;
	
	@Autowired
	public void setholidayMapper(HolidayMapper holidayMapper) {
		this.holidayMapper = holidayMapper;
	}

	public Map<String, Object> dataList(VacationParam record) {
		Map<String, Object> map = new HashMap<String, Object>();
		String param = record.getYear()+"-"+String.format("%02d",record.getMonth());
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("param", param);
		//查询节假日
		List<Holiday> list = this.holidayMapper.getVacations(paramMap);
		StringBuilder builder = new StringBuilder();
		for(int i = 0;i<list.size();i++){
			builder.append(list.get(i).getHolidayTime());
			if(i<list.size()-1){
				builder.append(",");
			}
		}
		String result = builder.toString();
		map.put("success", true);
		map.put("vacations",result);
		return map;
	}

	public Map<String, Object> save(String vacations) {
		String param = vacations.split(",")[0].substring(0, 7);
		// 若之前已经设置了节假日，则删除
		this.delete(param);
		Map<String, Object> map = new HashMap<String, Object>();
		String [] holidays = vacations.split(",");
		this.holidayMapper.save(holidays);
		map.put("success", true);
		map.put("message", "节假日添加成功！");
		return map;
	}

	public void delete(String param) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("param", param);
		this.holidayMapper.delete(paramMap);
	}

}
