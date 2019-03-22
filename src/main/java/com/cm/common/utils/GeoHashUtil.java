package com.cm.common.utils;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

public class GeoHashUtil {
    /** 
     * 1 2500km;2 630km;3 78km;4 30km 
     * 5 2.4km; 6 610m; 7 76m; 8 19m 
     */  
    private int hashLength = 8; //经纬度转化为geohash长度  
    private int latLength = 20; //纬度转化为二进制长度  
    private int lngLength = 20; //经度转化为二进制长度  
    
    public static final double MINLAT = -90;  
    public static final double MAXLAT = 90;  
    public static final double MINLNG = -180;  
    public static final double MAXLNG = 180;  
      
    private double minLat;//每格纬度的单位大小  
    private double minLng;//每个经度的倒下  
    private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7',   
                '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n',   
                'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};  
      
    public int gethashLength() {  
        return hashLength;  
    }  
      
    /** 
     * @Author:lulei   
     * @Description: 设置经纬度的最小单位 
     */  
    private void setMinLatLng() {  
        minLat = MAXLAT - MINLAT;  
        for (int i = 0; i < latLength; i++) {  
            minLat /= 2.0;  
        }  
        minLng = MAXLNG - MINLNG;  
        for (int i = 0; i < lngLength; i++) {  
            minLng /= 2.0;  
        }  
    }  
      
//    /** 
//     * @return 
//     * @Author:lulei   
//     * @Description: 求所在坐标点及周围点组成的九个 
//     */  
//    public List<String> getGeoHashBase32For9() {  
//        double leftLat = location.getLat() - minLat;  
//        double rightLat = location.getLat() + minLat;  
//        double upLng = location.getLng() - minLng;  
//        double downLng = location.getLng() + minLng;  
//        List<String> base32For9 = new ArrayList<String>();  
//        //左侧从上到下 3个  
//        String leftUp = getGeoHashBase32(leftLat, upLng);  
//        if (!(leftUp == null || "".equals(leftUp))) {  
//            base32For9.add(leftUp);  
//        }  
//        String leftMid = getGeoHashBase32(leftLat, location.getLng());  
//        if (!(leftMid == null || "".equals(leftMid))) {  
//            base32For9.add(leftMid);  
//        }  
//        String leftDown = getGeoHashBase32(leftLat, downLng);  
//        if (!(leftDown == null || "".equals(leftDown))) {  
//            base32For9.add(leftDown);  
//        }  
//        //中间从上到下 3个  
//        String midUp = getGeoHashBase32(location.getLat(), upLng);  
//        if (!(midUp == null || "".equals(midUp))) {  
//            base32For9.add(midUp);  
//        }  
//        String midMid = getGeoHashBase32(location.getLat(), location.getLng());  
//        if (!(midMid == null || "".equals(midMid))) {  
//            base32For9.add(midMid);  
//        }  
//        String midDown = getGeoHashBase32(location.getLat(), downLng);  
//        if (!(midDown == null || "".equals(midDown))) {  
//            base32For9.add(midDown);  
//        }  
//        //右侧从上到下 3个  
//        String rightUp = getGeoHashBase32(rightLat, upLng);  
//        if (!(rightUp == null || "".equals(rightUp))) {  
//            base32For9.add(rightUp);  
//        }  
//        String rightMid = getGeoHashBase32(rightLat, location.getLng());  
//        if (!(rightMid == null || "".equals(rightMid))) {  
//            base32For9.add(rightMid);  
//        }  
//        String rightDown = getGeoHashBase32(rightLat, downLng);  
//        if (!(rightDown == null || "".equals(rightDown))) {  
//            base32For9.add(rightDown);  
//        }  
//        return base32For9;  
//    }  
  
    /** 
     * @param length 
     * @return 
     * @Author:lulei   
     * @Description: 设置经纬度转化为geohash长度 
     */  
    public boolean sethashLength(int length) {  
        if (length < 1) {  
            return false;  
        }  
        hashLength = length;  
        latLength = (length * 5) / 2;  
        if (length % 2 == 0) {  
            lngLength = latLength;  
        } else {  
            lngLength = latLength + 1;  
        }  
        setMinLatLng();  
        return true;  
    }  
      
      
    /** 
     * @param lat 
     * @param lng 
     * @return 
     * @Author:lulei   
     * @Description: 获取经纬度的base32字符串 
     */  
    private String getGeoHashBase32(double lat, double lng) {  
        boolean[] bools = getGeoBinary(lat, lng);  
        if (bools == null) {  
            return null;  
        }  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < bools.length; i = i + 5) {  
            boolean[] base32 = new boolean[5];  
            for (int j = 0; j < 5; j++) {  
                base32[j] = bools[i + j];  
            }  
            char cha = getBase32Char(base32);  
            if (' ' == cha) {  
                return null;  
            }  
            sb.append(cha);  
        }  
        return sb.toString();  
    }  
      
    /** 
     * @param base32 
     * @return 
     * @Author:lulei   
     * @Description: 将五位二进制转化为base32 
     */  
    private char getBase32Char(boolean[] base32) {  
        if (base32 == null || base32.length != 5) {  
            return ' ';  
        }  
        int num = 0;  
        for (boolean bool : base32) {  
            num <<= 1;  
            if (bool) {  
                num += 1;  
            }  
        }  
        return CHARS[num % CHARS.length];  
    }  
      
    /** 
     * @param lat 
     * @param lng 
     * @return 
     * @Author:lulei   
     * @Description: 获取坐标的geo二进制字符串 
     */  
    private boolean[] getGeoBinary(double lat, double lng) {  
        boolean[] latArray = getHashArray(lat, MINLAT, MAXLAT, latLength);  
        boolean[] lngArray = getHashArray(lng, MINLNG, MAXLNG, lngLength);  
        return merge(latArray, lngArray);  
    }  
      
    /** 
     * @param latArray 
     * @param lngArray 
     * @return 
     * @Author:lulei   
     * @Description: 合并经纬度二进制 
     */  
    private boolean[] merge(boolean[] latArray, boolean[] lngArray) {  
        if (latArray == null || lngArray == null) {  
            return null;  
        }  
        boolean[] result = new boolean[lngArray.length + latArray.length];  
        Arrays.fill(result, false);  
        for (int i = 0; i < lngArray.length; i++) {  
            result[2 * i] = lngArray[i];  
        }  
        for (int i = 0; i < latArray.length; i++) {  
            result[2 * i + 1] = latArray[i];  
        }  
        return result;  
    }  
      
    /** 
     * @param value 
     * @param min 
     * @param max 
     * @return 
     * @Author:lulei   
     * @Description: 将数字转化为geohash二进制字符串 
     */  
    private boolean[] getHashArray(double value, double min, double max, int length) {  
        if (value < min || value > max) {  
            return null;  
        }  
        if (length < 1) {  
            return null;  
        }  
        boolean[] result = new boolean[length];  
        for (int i = 0; i < length; i++) {  
            double mid = (min + max) / 2.0;  
            if (value > mid) {  
                result[i] = true;  
                min = mid;  
            } else {  
                result[i] = false;  
                max = mid;  
            }  
        }  
        return result;  
    }  
      
  
    public static void main(String[] args) throws Exception {  
        GeoHashUtil g = new GeoHashUtil();  
        g .setMinLatLng();
        g.sethashLength(6);
        Set<String> set = new TreeSet<String>();
        File file =new File("d://polyline//110000.txt");
        List<double[]> readPolygons = readPolygons(file).get(0);
        for(double[] lonlat:readPolygons){
        	String hash = g.getGeoHashBase32(lonlat[1],lonlat[0]);
        	System.out.println(hash);  
        	set.add(hash);
        }
        System.out.println(readPolygons.size());
        System.out.println(set.size());
        System.out.println(set);
    }  
    
    
    private static List<List<double[]>> readPolygons(File file) throws Exception {
		String locations=FileUtils.readFileToString(file);
		List<List<double[]>> polygons = new LinkedList<>();
		String[] areas = locations.split("\\|");
		int limit = 100;
        for(String area:areas){
        	List<double[]> polygon = new LinkedList<>();
        	String[] split = area.split(";");
        	if(areas.length>10&&split.length<50){
    			continue;
    		}
        	int x=1;
    		if(limit!=0){
    			x=split.length/limit+1;
    		}
	        for(int i=0;i<split.length;i++){
//	        	if(i==0||i==split.length-1||i%x==0){
	        		String[] lonlatStr = split[i].split(",");
		        	polygon.add(new double[]{Double.parseDouble(lonlatStr[0]),Double.parseDouble(lonlatStr[1])});
//	        	}
	        }
	        polygons.add(polygon);
        }
        if(areas.length!=polygons.size()){
        	System.out.println(areas.length+"----"+polygons.size());
        }
		return polygons;
	}
}
