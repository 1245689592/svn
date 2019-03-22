 /**
 * 
 */
package com.cm.common.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.Map.Entry;


/**
 * @author ZhiJD
 *
 */
public class ConfigLoad {
	
//	/**
//	 * 从数据库加载配置
//	 */
//	public static void configLoadForDB(){
//		try{
//			Properties propertie=ConfigLoadForDatabase.configLoadForDB();
//			
//			properties2Map(propertie);
//		}catch(Exception e){
//			
//		}
//	}
	
	/**
	 * 从配置文件加载配置
	 */
	public static void configLoadForProPerties(String configFilePath){
		try{
			String path = "config";
			
			if (configFilePath!=null && !configFilePath.trim().isEmpty()){
				path = configFilePath;
			}
			if(path.trim().startsWith("/") ||
					java.util.regex.Pattern.compile("(^//.|^/|^[a-zA-Z])?:?/.+(/$)?").matcher(path).matches()
					//||  fileAbsolutePath.trim().matches("^[a-zA-Z]:")
					){
				path = path;
			}else{
				path = ConfigLoad.class.getResource("/").getPath()+path;
			}
			refreshFileList(path);
		}catch(Exception e){
			System.out.println("configLoadForProPerties Exception:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据目录加载目录下的所有的配置文件
	 * @param path
	 */
	private static void refreshFileList(String fileAbsolutePath) {

		try{
			File file = null;
			if(fileAbsolutePath.trim().startsWith("/") ||
					java.util.regex.Pattern.compile("(^//.|^/|^[a-zA-Z])?:?/.+(/$)?").matcher(fileAbsolutePath).matches()
					//||  fileAbsolutePath.trim().matches("^[a-zA-Z]:")
					){
				file =  new File(fileAbsolutePath);
			}else{
				file = new File(ConfigLoad.class.getResource("/").getPath()+fileAbsolutePath);
			}
			
			File[] subFile = file.listFiles();
			if(subFile!=null){
				for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
					// 判断是否为文件夹
					if (!subFile[iFileLength].isDirectory()) {
						String tempName = subFile[iFileLength].getName();
						// 判断是否为properties结尾
						if (tempName.trim().toLowerCase().endsWith("properties")) {
							//System.out.println("fileName:"+tempName);
							//readConfigFile(tempName);
							System.out.println("fileName:"+subFile[iFileLength].toString());
							properties2Map(readConfigFile(subFile[iFileLength].toString()));
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param confFilePathKey 配置文件路径Key
	 * @return
	 */
	public static java.util.Properties filePath2Properties(String confFilePathKey){
		
		String fileName = ConfigCommon.getProperties().get(confFilePathKey);
		String temp = null;
		if (fileName!=null && !fileName.trim().isEmpty()){
			if(fileName.trim().startsWith("/") ||
					java.util.regex.Pattern.compile("(^//.|^/|^[a-zA-Z])?:?/.+(/$)?").matcher(fileName).matches()
					//||  fileAbsolutePath.trim().matches("^[a-zA-Z]:")
					){
				temp = fileName;
			}else{
				temp = ConfigLoad.class.getResource("/").getPath()+fileName;
			}
			
			return readConfigFile(temp);
		}else{
			return null;
		}
	} 
	
	
	/**
	 * 从配置文件中读取配置参数
	 * @param fileName
	 */
	private static java.util.Properties readConfigFile(String fileName) {
		java.util.Properties prop = new java.util.Properties();
		InputStream is = null;
		try {
			is =  new BufferedInputStream (new FileInputStream(fileName));

			prop.load(is);
			
//			properties2Map(prop);
			
		} catch (Exception e) {
			System.out.println("读配置文件出错");
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}
	
	/**
	 * 将Properties配置参数 加载为MAP 键值对格式 
	 * @param prop
	 * @return
	 */
	private static void properties2Map(java.util.Properties prop) {

		try {
			Set<Entry<Object, Object>> entrySet = prop.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
//				if (!entry.getKey().toString().startsWith("#") && entry.getKey()!=null && entry.getValue()!=null ) {
//					ConfigCommon.properties.setProperty(
//							((String) entry.getKey()).trim(),
//							((String) entry.getValue()).trim());
//					
//					System.out.println("key:"+((String) entry.getKey()).trim()+" ,value:"+((String) entry.getValue()).trim());
//				}
				ConfigCommon.setProperties(
						String.valueOf(entry.getKey()),
						String.valueOf(entry.getValue()));
				
				System.out.println("key:"+((String) entry.getKey()).trim()+" ,value:"+((String) entry.getValue()).trim()+"    map value:"+ConfigCommon.getProperties(String.valueOf(entry.getKey()), ""));
				
			}
		} catch (Exception e) {
			System.out.println("properties2Map Exception:"+e.getMessage());
			e.printStackTrace();
		}

	}
	

	/**
	 * @param args
	 * 
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ConfigLoad cl = new ConfigLoad();
//		cl.refreshFileList("D:/WORK/workspace/story/new-story-server-config/src/config");
		
		System.out.println("1111===============================================");
		ConfigLoad.configLoadForProPerties("D:/WORK/workspace/story/new-story-server-config/src/config");
		System.out.println("2222===============================================");
		ConfigLoad.configLoadForProPerties("config");
		System.out.println("3333===============================================");
		ConfigLoad.configLoadForProPerties(null);
		
	}

}
