package com.stock.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.stock.dao.ExceptionLogMapper;
import com.stock.model.ExceptionLog;
import com.stock.service.StockServiceI;
import com.stock.util.CommonsUtil;

public class TaskJob {

	private StockServiceI detailSaveServiceI;
	private ExceptionLogMapper exceptionLogMapper;

	public void setExceptionLogMapper(ExceptionLogMapper exceptionLogMapper) {
		this.exceptionLogMapper = exceptionLogMapper;
	}

	@Autowired
	public void setDetailSaveServiceI(StockServiceI detailSaveServiceI) {
		this.detailSaveServiceI = detailSaveServiceI;
	}

	public void execute() {
		try {
			this.detailSaveServiceI.insertStockShort();
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionLog record = new ExceptionLog(CommonsUtil.formatDateToString3(new Date()), this.getClass().getName(), "", e.getMessage(), CommonsUtil.join(e.getStackTrace(), ",\r\n"));
			this.exceptionLogMapper.insert(record);
		}
	}

}
