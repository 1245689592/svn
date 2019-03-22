/**
 * 
 */
package com.cm.common.config;
import java.util.HashMap;
import java.util.Map;
/**
 * @author ZhiJD
 *
 */
public class ConfigCommon {
	
	private String path;
	
	private static Map<String,String> properties =new HashMap<String,String>() ;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	static{
		if (properties == null || properties.size()==0){
			ConfigLoad.configLoadForProPerties("config/properties");
		}
	}
	
	public static void setProperties(Map<String,String> map){
		properties.putAll(map);
	}
	
	/**
	 * 返回整个完整的MAP对象
	 * @return
	 */
	public static Map<String,String> getProperties(){
		return properties;
	}
	
	/**
	 * 	设置的配置参数，使用Map存储
	 * @param K
	 * @param V
	 * @return 
	 */
	public static String setProperties(String K,String V){
		return properties.put(K, V);
	}
	
	/**
	 * 读取指定Key的参数，默认返回结果
	 * @param key
	 * @return
	 * 返回值默认为false
	 */
	public static Boolean getProperties(String K,Boolean defultV){
		
		Boolean V = false;
		if(defultV !=null)V =defultV; 
		
		if(properties.get(K) == null){
			//在Map中未查找到值域，则返回默认值
			return V;
		}else{
			
			return Boolean.valueOf(properties.get(K).trim());
		}
	}
	
	/**
	 * 读取指定Key的参数，默认返回结果
	 * @param key
	 * @return
	 * 返回值默认为false
	 */
	public static String getProperties(String K,String defultV){
		
		String V = "";
		if(defultV !=null)V =defultV; 
		
		if(properties.get(K) == null){
			//在Map中未查找到值域，则返回默认值
			return V;
		}else{
			return properties.get(K).trim();
		}
	}
	
	
	/**
	 * 读取指定Key的参数，默认返回结果
	 * @param key
	 * @return
	 * 返回值默认为false
	 */
	public static int getProperties(String K,int defultV){
		
		if(properties.get(K) == null){
			//在Map中未查找到值域，则返回默认值
			return defultV;
		}else{
			return Integer.valueOf(properties.get(K).trim());
		}
	}
	/**
	 * 读取指定Key的参数，默认返回结果
	 * @param key
	 * @return
	 * 返回值默认为false
	 */
	public static String getString(String K,String defultV){
		
		String V = "";
		if(defultV !=null)V =defultV; 
		
		if(properties.get(K) == null){
			//在Map中未查找到值域，则返回默认值
			return V;
		}else{
			return properties.get(K).trim();
		}
	}
	
	
	/**
	 * 读取指定Key的参数，默认返回结果
	 * @param key
	 * @return
	 * 返回值默认为false
	 */
	public static Boolean getBoolean(String K,Boolean defultV){
		Boolean V = false;
		if(defultV !=null)V =defultV; 
		
		if(properties.get(K) == null){
			//在Map中未查找到值域，则返回默认值
			return V;
		}else{
			
			return Boolean.valueOf(properties.get(K).trim());
		}
	}
	
	/**
	 * 读取指定Key的参数，默认返回结果
	 * @param key
	 * @return
	 * 返回值默认为false
	 */
	public static String get(String K){
		return properties.get(K);
	}
}
