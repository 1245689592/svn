package com.cm.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.cm.common.format.MYDateFormat;
/**
 * 
 * @author yunlu
 *
 */
public class DateTimeUtils {
	
	public static final String DateFormat="yyyy-MM-dd";
	
	public static final String TimeFormat="HH:mm:ss";
	
	public static Date getCurrentTime(){
		return Calendar.getInstance().getTime();
	}

	public static String formatDate(Date date, String format) {
		return MYDateFormat.getInstance(format).format(date);
	}
	
	public static String formatDate(long time, String format) {
		return MYDateFormat.getInstance(format).format(time);
	}
	
	public static String formatDate(String format){
		return MYDateFormat.getInstance(format).format(getCurrentTime());
	}

	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDate(Date date) {
		return MYDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(date);
	}
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDate(long time) {
		return MYDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(time);
	}
	/**
	 * yyyy-MM-dd HH:mm:ss
	 * current time
	 * @return
	 */
	public static String formatDate() {
		return MYDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(getCurrentTime());
	}

	public static Date parseDate(String date, String format)
			throws ParseException {
		return MYDateFormat.getInstance(format).parse(date);
	}
	
	public static String parseDate(String date, String format,String newFormat)
			throws ParseException {
		return MYDateFormat.getInstance(newFormat).format(MYDateFormat.getInstance(format).parse(date));
	}
	
	/**
	 * 获取今天0点的时间字符
	 * @return
	 */
	public static String getEarlyDateStr() {
		return getEarlyDateStr(new Date());
	}
	
	/**
	 * 获取今天0点的时间类
	 * @return
	 * @throws ParseException 
	 */
	public static Date getEarlyDate() throws ParseException {
		return getEarlyDate(new Date());
	}
	
	/**
	 * 获取0点的时间字符
	 * @return
	 */
	public static String getEarlyDateStr(Date date) {
		return MYDateFormat.getInstance("yyyy-MM-dd").format(date);
	}
	
	/**
	 * 获取0点的时间类
	 * @return
	 * @throws ParseException 
	 */
	public static Date getEarlyDate(Date date) throws ParseException {
		return MYDateFormat.getInstance("yyyy-MM-dd").parse(MYDateFormat.getInstance("yyyy-MM-dd").format(date));
	}
	/** 
     *  
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度 
     *  
     * @param date 
     * @return 
     */  
    public static int getSeason(Date date) {  
  
        int season = 0;  
  
        Calendar c = Calendar.getInstance();  
        c.setTime(date);  
        int month = c.get(Calendar.MONTH);  
        switch (month) {  
        case Calendar.JANUARY:  
        case Calendar.FEBRUARY:  
        case Calendar.MARCH:  
            season = 1;  
            break;  
        case Calendar.APRIL:  
        case Calendar.MAY:  
        case Calendar.JUNE:  
            season = 2;  
            break;  
        case Calendar.JULY:  
        case Calendar.AUGUST:  
        case Calendar.SEPTEMBER:  
            season = 3;  
            break;  
        case Calendar.OCTOBER:  
        case Calendar.NOVEMBER:  
        case Calendar.DECEMBER:  
            season = 4;  
            break;  
        default:  
            break;  
        }  
        return season;  
    }  
    public static void main(String[] args) throws ParseException {
		System.out.println(DateTimeUtils.parseDate("2017-08-23", "yyyy-MM-dd").getTime());
	}
}
