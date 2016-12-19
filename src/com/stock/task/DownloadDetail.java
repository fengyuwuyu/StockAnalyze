package com.stock.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.stock.dao.ExceptionLogMapper;
import com.stock.dao.HolidayMapper;
import com.stock.model.ExceptionLog;
import com.stock.service.InitStockServiceI;
import com.stock.util.CommonsUtil;

public class DownloadDetail {

	private InitStockServiceI initStockServiceI;
	private ExceptionLogMapper exceptionLogMapper;
	private HolidayMapper holidayMapper;
	private Logger log = Logger.getLogger(DownloadDetail.class);

	@Autowired
	public void setHolidayMapper(HolidayMapper holidayMapper) {
		this.holidayMapper = holidayMapper;
	}

	@Autowired
	public void setInitStockServiceI(InitStockServiceI initStockServiceI) {
		this.initStockServiceI = initStockServiceI;
	}

	@Autowired
	public void setExceptionLogMapper(ExceptionLogMapper exceptionLogMapper) {
		this.exceptionLogMapper = exceptionLogMapper;
	}

	public void execute(){
		long time = 20000;
		log.info("开始下载委卖委买数据，每20s一次");
		String day = CommonsUtil.formatDateToString1(new Date());
		while(CommonsUtil.checkTime(holidayMapper)){
			long begin = System.currentTimeMillis();
			initStockServiceI.initBuyAndSell(day);
			long remain = System.currentTimeMillis() - begin;
			long sleep = time-remain;
			if(sleep>0){
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					log.info(CommonsUtil.join(e.getStackTrace(), ","));
					ExceptionLog record = new ExceptionLog(
							CommonsUtil.formatDateToString3(new Date()), this
									.getClass().getName(), "initBuyAndSell", e.getMessage(),
							CommonsUtil.join(e.getStackTrace(), ","));
					this.exceptionLogMapper.insert(record);
				}
			}
		}
		log.info("下载委卖委买数据结束。。。");
	}
	
}
