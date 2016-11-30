package com.stock.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.stock.dao.ExceptionLogMapper;
import com.stock.dao.HolidayMapper;
import com.stock.model.ExceptionLog;
import com.stock.model.StockConstant;
import com.stock.service.InitStockServiceI;
import com.stock.util.CommonsUtil;

/**
 * 没两分钟下载股票的基本数据（当前股价、成交量、昨收、今开等）
 * @author ll
 *
 */
public class DownloadBase {
	
	private InitStockServiceI initStockServiceI;
	private ExceptionLogMapper exceptionLogMapper;
	private HolidayMapper holidayMapper;
	private Logger log = Logger.getLogger(DownloadBase.class);

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
		try {
			// 如果当前不在股市的交易时间
			while (CommonsUtil.checkTime(this.holidayMapper)) {
				log.info("开始下载每天股票详情。。。");
				long begin = System.currentTimeMillis();
				initStockServiceI.initStock();
				long host = System.currentTimeMillis() - begin;
				long sleep = StockConstant.INIT_STOCK_SLEEP_TIME - host;
				if (sleep > 0) {
					log.info("休眠时间是："+sleep);
					Thread.sleep(sleep);
				}
			}
			log.info("下载每天股票详情任务结束");
		} catch (Exception e) {
			log.info(CommonsUtil.join(e.getStackTrace(), ","));
			ExceptionLog record = new ExceptionLog(
					CommonsUtil.formatDateToString3(new Date()), this
							.getClass().getName(), "downLoad1", e.getMessage(),
					CommonsUtil.join(e.getStackTrace(), ","));
			this.exceptionLogMapper.insert(record);
		}
	}
}
