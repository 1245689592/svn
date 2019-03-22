package com.cm.common.utils;
/**
 * 坐标转换工具
 * @author yunlu
 *
 */
public class CoordUtil {
	
	private static double x_pi = Math.PI * 3000.0 / 180.0;
	/**
	 * 火星转百度
	 * @param gg_lat
	 * @param gg_lon
	 * @param bd_lat
	 * @param bd_lon
	 */
	public static String bd_encrypt(double gg_lat, double gg_lon, double bd_lat, double bd_lon)
	{
	    double x = gg_lon, y = gg_lat;
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
	    bd_lon = z * Math.cos(theta) + 0.0065;
	    bd_lat = z * Math.sin(theta) + 0.006;
	    return bd_lon+","+bd_lat;
	}
	 /**
	  * 百度转火星
	  * @param bd_lat
	  * @param bd_lon
	  * @param gg_lat
	  * @param gg_lon
	  */
	public static String bd_decrypt(double bd_lat, double bd_lon, double gg_lat, double gg_lon)
	{
	    double x = bd_lon - 0.0065, y = bd_lat - 0.006;
	    double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
	    double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
	    gg_lon = z * Math.cos(theta);
	    gg_lat = z * Math.sin(theta);
	    return gg_lon+","+gg_lat;
	}
}
