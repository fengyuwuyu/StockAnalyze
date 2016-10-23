package com.stock.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.stock.dao.ExceptionLogMapper;
import com.stock.dao.HolidayMapper;
import com.stock.model.ExceptionLog;
import com.stock.model.StockConstant;
import com.stock.service.InitStockServiceI;
import com.stock.service.StockMainServiceI;
import com.stock.util.CommonsUtil;

public class TaskJob {

	private InitStockServiceI initStockServiceI;
	private ExceptionLogMapper exceptionLogMapper;
	private HolidayMapper holidayMapper;
	private StockMainServiceI stockMainServiceI;

	@Autowired
	public void setStockMainServiceI(StockMainServiceI stockMainServiceI) {
		this.stockMainServiceI = stockMainServiceI;
	}

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

	public void execute() {
		try {
			boolean run = true;
			int i = 20;
			while (run) {
				run = this.stockMainServiceI.analyse1(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载股票详情，所有A股每天每隔两分钟刷新一次的数据
	 */
	public void downLoad1() {
		try {
			// 如果当前不在股市的交易时间
			while (checkTime()) {
				long begin = System.currentTimeMillis();
				initStockServiceI.initStock();
				long host = System.currentTimeMillis() - begin;
				long sleep = StockConstant.INIT_STOCK_SLEEP_TIME - host;
				if (sleep > 0) {
					Thread.sleep(sleep);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionLog record = new ExceptionLog(
					CommonsUtil.formatDateToString3(new Date()), this
							.getClass().getName(), "", e.getMessage(),
					CommonsUtil.join(e.getStackTrace(), ",\r\n"));
			this.exceptionLogMapper.insert(record);
		}
	}

	/**
	 * 股票交易时间：每周一到周五上午时段9:30-11:30，下午时段13:00-15:00
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private boolean checkTime() {
		Date now = new Date();
		// 如果是节假日，则直接返回false
		if (isHoliday(now)) {
			return false;
		}
		int hour = now.getHours();
		int minute = now.getMinutes();
		if (hour >= 9 && hour < 12) {
			if (hour == 9) {
				if (minute >= 30) {
					return true;
				} else {
					return false;
				}
			} else if (hour == 11) {
				if (minute <= 30) {
					return true;
				} else {
					return false;
				}
			}
			return true;
		} else if (hour >= 13 && hour < 15) {
			return true;
		}
		return false;
	}

	private boolean isHoliday(Date now) {
		String day = this.holidayMapper.queryByDay(new java.sql.Date(now
				.getTime()));
		if (day == null || "".equals(day)) {
			return false;
		}
		return true;
	}
	
	

}
