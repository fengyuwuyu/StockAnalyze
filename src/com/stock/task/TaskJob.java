package com.stock.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.stock.dao.ExceptionLogMapper;
import com.stock.model.ExceptionLog;
import com.stock.model.StockConstant;
import com.stock.service.InitStockServiceI;
import com.stock.service.StockServiceI;
import com.stock.util.CommonsUtil;

public class TaskJob {

	private InitStockServiceI initStockServiceI;
	private ExceptionLogMapper exceptionLogMapper;
//	public static boolean download = true;

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
			while(true){
				//如果当前不在股市的交易时间
				while(!checkTime()){
					Thread.sleep(StockConstant.INIT_STOCK_SLEEP_TIME);
				}
				long begin = System.currentTimeMillis();
				initStockServiceI.initStock();
				long host = System.currentTimeMillis()-begin;
				long sleep = StockConstant.INIT_STOCK_SLEEP_TIME-host;
				if(sleep>0){
					Thread.sleep(StockConstant.INIT_STOCK_SLEEP_TIME);
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
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private boolean checkTime() {
		Date now = new Date();
//		if(isHoliday(now)){
//			return false;
//		}
		int hour = now.getHours();
		int minute = now.getMinutes();
		if(hour>=9 &&hour<12){
			if(hour==9){
				if(minute>=30){
					return true;
				}else{
					return false;
				}
			}else if(hour==11){
				if(minute<=30){
					return true;
				}else{
					return false;
				}
			}
			return true;
		}else if(hour>=13 &&hour<15){
			return true;
		}
		return false;
	}

	private boolean isHoliday(Date now) {
		
		return false;
	}
	
}
