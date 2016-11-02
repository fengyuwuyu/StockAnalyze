package com.stock.model;

public class StockConstant {
	
	public static final int THREE_MINUTE_MAX_INCREASE = 2;
	public static final int MAX_INCREASE = 3;
	
	public static final int COUNT = 7;
	
	/** 股票一段时间的趋势*/
	/** 股价上涨*/
	public static final int UP = 1;
	/** 股价下跌*/
	public static final int DOWN = 2;
	/** 股价震荡*/
	public static final int LINE = 3;
	

	/** 两种趋势结合体*/
	/** 震荡上涨*/
	public static final int LINE_UP = 1;
	/** 震荡下跌*/
	public static final int LINE_DOWN = 2;
	/** 先跌后涨*/
	public static final int DOWN_UP = 3;
	/** 先跌后震荡*/
	public static final int DOWN_LINE = 4;
	/** 先涨后跌*/
	public static final int UP_DOWN = 5;
	/** 先涨后震荡*/
	public static final int UP_LINE = 6;
	
	
	/** 一段时间内的走势类型*/
	/** 起始最小，末尾最大*/
	public static final int MAX_MIN = 1;
	/** 起始最大，末尾最小*/
	public static final int MIN_MAX = 2;
	/** 有最大无最小*/
	public static final int BEGIN_MAX_END = 3;
	/** 有最小无最大*/
	public static final int BEGIN_MIN_END = 4;
	/** 有最大有最小，最大在左*/
	public static final int BEGIN_MAX_MIN_END = 5;
	/** 有最大有最小，最小在左*/
	public static final int BEGIN_MIN_MAX_END = 6;
	/** 震荡走势*/
	public static final int BEGIN_LINE_END = 7;

	public static final long INIT_STOCK_SLEEP_TIME = 60000;
	

}
