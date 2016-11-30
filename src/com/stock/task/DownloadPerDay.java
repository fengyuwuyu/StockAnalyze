package com.stock.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.stock.dao.ExceptionLogMapper;
import com.stock.model.ExceptionLog;
import com.stock.service.InitStockServiceI;
import com.stock.util.CommonsUtil;

/**
 * 每天下载股票的基本数据（当前股价、成交量、昨收、今开等）
 * @author ll
 *
 */
public class DownloadPerDay {
	
	private InitStockServiceI initStockServiceI;
	private ExceptionLogMapper exceptionLogMapper;
	private Logger log = Logger.getLogger(DownloadPerDay.class);

	@Autowired
	public void setInitStockServiceI(InitStockServiceI initStockServiceI) {
		this.initStockServiceI = initStockServiceI;
	}

	@Autowired
	public void setExceptionLogMapper(ExceptionLogMapper exceptionLogMapper) {
		this.exceptionLogMapper = exceptionLogMapper;
	}
	
	public void execute(){
		try {
			initStockServiceI.initStockEveryDay();
		} catch (Exception e) {
			log.info(CommonsUtil.join(e.getStackTrace(), ","));
			ExceptionLog record = new ExceptionLog(
					CommonsUtil.formatDateToString3(new Date()), this
							.getClass().getName(), "initStockEveryDay", e.getMessage(),
					CommonsUtil.join(e.getStackTrace(), ","));
			this.exceptionLogMapper.insert(record);
		}
	}
}
