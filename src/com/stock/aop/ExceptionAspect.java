package com.stock.aop;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import com.stock.dao.ExceptionLogMapper;
import com.stock.model.ExceptionLog;

/**
 * 
 * AOP日志切面
 * 记录调用service方法的入参及出参等日志信息
 * @author lilei
 * 
 */
public class ExceptionAspect {
	private ExceptionLogMapper exceptionLogMapper;

	public void setExceptionLogMapper(ExceptionLogMapper exceptionLogMapper) {
		this.exceptionLogMapper = exceptionLogMapper;
	}

	/**
	 * 调用service方法出现后将异常信息存储到数据库中
	 * @param joinPoint
	 * @param e
	 */
	public void afterThrowing(JoinPoint joinPoint, Exception e){
		try { 
			Signature signature = joinPoint.getSignature();
			String className = signature.getDeclaringType().getName();
			String methodName = signature.getName();
			ExceptionLog exceptionLog = new ExceptionLog(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), className, methodName, e.toString(),getStackTrace(e));
			this.exceptionLogMapper.insert(exceptionLog);
		} catch (Exception e2) {
			ExceptionLog exceptionLog = new ExceptionLog(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), "com.cecep.aop.ExceptionAspect", "afterThrowing", "插入记录异常","字段长度不够啊");
			this.exceptionLogMapper.insert(exceptionLog);
		}
	}
	
	private String getStackTrace(Exception e){
		StringBuilder sb = new StringBuilder();
		StackTraceElement [] eles = e.getStackTrace();
		for(StackTraceElement ele : eles){
			sb.append(ele.toString()+"\n\r");
		}
		return sb.toString();
	}
	
}
