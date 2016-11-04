package com.stock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.stock.dao.HolidayMapper;

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
	
	public static int getDayDiff(String date1,String date2){
		String strs1[] = date1.split("-");
		String strs2[] = date1.split("-");
		int year1 = Integer.valueOf(strs1[0]);
		int month1 = Integer.valueOf(strs1[1]);
		int day1 = Integer.valueOf(strs1[2]);
		int year2 = Integer.valueOf(strs2[0]);
		int month2 = Integer.valueOf(strs2[1]);
		int day2 = Integer.valueOf(strs2[2]);
		int days1 = getDays(year1,month1,day1);
		int days2 = getDays(year2,month2,day2);
		return days2-days1;
		
	}
	
	private static int getDays(int year, int month, int day) {
		int count1 = year*365;
		int count2 = getMonthCount(month);
		return count1+count2+day;
	}

	private static int getMonthCount(int month) {
		switch(month){
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31*month;
		case 2:
			return 28*month;
		default:
			return 30*month;
			
		}
	}

	public static int getDayDiff(Date date1,Date date2){
		return getDayDiff(dateFormat1.format(date1),dateFormat1.format(date2));
	}
	
	public static String listToString(List<String> list){
		String s = list.toString().replace(" ", "");
		return s.substring(1, s.length()-1);
	}
	
	/**
	 * 股票交易时间：每周一到周五上午时段9:30-11:30，下午时段13:00-15:00
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static boolean checkTime(HolidayMapper holidayMapper){
		Date now = new Date();
		// 如果是节假日，则直接返回false
		if (isHoliday(now,holidayMapper)) {
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
	
	private static boolean isHoliday(Date now,HolidayMapper holidayMapper) {
		String day = holidayMapper.queryByDay(new java.sql.Date(now
				.getTime()));
		if (day == null || "".equals(day)) {
			return false;
		}
		return true;
	}
}
