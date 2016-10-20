package com.stock.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.stock.model.Holiday;

public interface HolidayMapper {

	/** 删除对应月份的节假日*/
	int delete(Map<String, String> paramMap);
	/** 根据月份查询节假日*/
	List<Holiday> getVacations(Map<String,String> param);
	/** 保存对应月份的节假日*/
	int save(String[] holidays);
	/** 根据起止时间查询节假日*/
	List<String> getVacationsByCondition(Map<String,Object> param);
	String queryByDay(Date kssj);
}