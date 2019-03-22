package com.cm.common.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipUtil {
	/**
	 * 解压文件并解析
	 * @param filePath
	 * @param handler
	 * @throws Exception
	 */
	public static void unZip(String filePath,ZipHandler handler)throws Exception{
		ZipInputStream zin=null;
		try{
			List result = new ArrayList();
			ZipFile zipFile = new ZipFile(filePath);
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));  
			zin = new ZipInputStream(in);  
	        ZipEntry ze;
	        while((ze=zin.getNextEntry())!=null){
	        	handler.handleFile(ze);
	        	zin.closeEntry();
	        }
		}catch(Exception e){
			throw e;
		}finally{
			if(zin!=null){
				zin.close();
			}
		}
	}
	public static void main(String[] args) throws Exception{
		ZipUtil.unZip("C:\\Users\\yunlu\\Desktop\\1234.zip",null);
	}
}
