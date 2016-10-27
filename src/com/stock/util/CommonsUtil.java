package com.stock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonsUtil {
	
	/** yyyy-MM-dd*/
	private static SimpleDateFormat dateFormat1 = new SimpleDateFormat(
			"yyyy-MM-dd");
	/** yyyy-MM-dd*/
	private static SimpleDateFormat dateFormat4 = new SimpleDateFormat(
			"yyyy-MM-dd");
	/** yyyy-MM-dd HH:mm:ss*/
	private static SimpleDateFormat dateFormat2 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	/** HH:mm:ss*/
	private static SimpleDateFormat dateFormat3 = new SimpleDateFormat(
			"HH:mm:ss");
	private static SimpleDateFormat dateFormat5 = new SimpleDateFormat(
			"yyyy");

	/**
	 * 以separate为分隔符，返回String类型，数组中的对象需要实现toString方法
	 * @param array 数组对象
	 * @param separate
	 * @return
	 */
	public static String join(Object[] array,String separate){
		if(array!=null&&array.length>0){
			if(separate==null){
				separate = ",";
			}
			if(array.length==1){
				return array[0].toString();
			}else{
				StringBuilder result = new StringBuilder();
				for(Object o : array){
					result.append(o.toString()+separate);
				}
				return result.substring(0, result.length()-1);
			}
		}
		return null;
	}
	
	/**
	 * 以yyyy-MM-dd格式格式化传入的时间
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToString1(Date date) {
		return dateFormat1.format(date);
	}

	/**
	 * 以MM-dd HH:mm格式格式化传入的时间
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToString2(Date date) {
		String result = dateFormat2.format(date);
		result = result.substring(5);
		result = result.substring(0, result.length()-3);
		return result;
	}
	
	/**
	 * 以yyyy-MM-dd HH:mm:ss格式格式化传入的时间
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToString3(Date date) {
		return dateFormat2.format(date);
	}
	
	/**
	 * 以HH:mm:ss格式格式化传入的时间
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateToString4(Date date) {
		return dateFormat3.format(date);
	}
	
	public static Date formatStringToDate1(String time) throws ParseException{
		return dateFormat4.parse(time);
	}

	public static String formatDateToString5(Date date) {
		// TODO Auto-generated method stub
		return dateFormat5.format(date);
	}
}
