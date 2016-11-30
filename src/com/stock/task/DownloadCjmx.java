package com.stock.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.stock.dao.ExceptionLogMapper;
import com.stock.dao.HolidayMapper;
import com.stock.model.ExceptionLog;
import com.stock.service.InitStockServiceI;
import com.stock.util.CommonsUtil;

/**
 * 每天下载个股票成交明细记录（excel格式）
 * @author ll
 *
 */
public class DownloadCjmx {

	private InitStockServiceI initStockServiceI;
	private ExceptionLogMapper exceptionLogMapper;
	private HolidayMapper holidayMapper;
	private Logger log = Logger.getLogger(DownloadCjmx.class);

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

	/**
	 * 将excel格式的成交量导入到数据库中，每天执行一次
	 */
	public void execute(){
		try {
			while(CommonsUtil.checkTime(holidayMapper)){
				initStockServiceI.insertCJL();
			}
		} catch (Exception e) {
			log.info(CommonsUtil.join(e.getStackTrace(), ","));
			ExceptionLog record = new ExceptionLog(
					CommonsUtil.formatDateToString3(new Date()), this
							.getClass().getName(), "insertCJL", e.getMessage(),
					CommonsUtil.join(e.getStackTrace(), ","));
			this.exceptionLogMapper.insert(record);
		}
	}
	
	/**
	 * 下载成交量
	 */
	public void initCJL(){
		try {
			initStockServiceI.insertCJL();
		} catch (Exception e) {
			log.info(CommonsUtil.join(e.getStackTrace(), ","));
			ExceptionLog record = new ExceptionLog(
					CommonsUtil.formatDateToString3(new Date()), this
							.getClass().getName(), "initCJL", e.getMessage(),
					CommonsUtil.join(e.getStackTrace(), ","));
			this.exceptionLogMapper.insert(record);
		}
	}
	
}
