package com.stock.service;

import java.util.Map;

import com.stock.model.VacationParam;

public interface HolidayServiceI {

	Map<String, Object> dataList(VacationParam record);

	Map<String, Object> save(String vacations);

	 void delete(String holiday);

}
