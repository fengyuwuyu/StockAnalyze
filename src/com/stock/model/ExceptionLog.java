package com.stock.model;

/**
 * @author lilei
 * 
 */
/**
 * @author lilei
 * 
 */
public class ExceptionLog {
	private Long id;

	private String date;

	private String classname;

	private String methodname;

	private String exception;

	private String stackTrace;

	public ExceptionLog() {
	}

	public ExceptionLog(String date, String classname, String methodname,
			String exception, String stackTrace) {
		this.date = date;
		this.classname = classname;
		this.methodname = methodname;
		this.exception = exception;
		this.stackTrace = stackTrace;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date == null ? null : date.trim();
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname == null ? null : classname.trim();
	}

	public String getMethodname() {
		return methodname;
	}

	public void setMethodname(String methodname) {
		this.methodname = methodname == null ? null : methodname.trim();
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception == null ? null : exception.trim();
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	@Override
	public String toString() {
		return "ExceptionLog [id=" + id + ", date=" + date + ", classname="
				+ classname + ", methodname=" + methodname + ", exception="
				+ exception + ", stackTrace=" + stackTrace + "]";
	}

}