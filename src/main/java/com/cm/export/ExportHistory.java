package com.cm.export;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.MapperOptions;
import org.mongodb.morphia.query.Query;

import com.cm.common.config.ConfigCommon;
import com.cm.common.config.ConfigLoad;
import com.cm.common.format.MYDateFormat;
import com.cm.common.utils.DateTimeUtils;
import com.cm.mongo.entity.CarInfoHistoryMongo;
import com.cm.mongo.entity.CarSimpleInfoMongo;
import com.cm.export.HistoryToExcel2;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.MongoClientOptions.Builder;

public class ExportHistory {
	
	private static AdvancedDatastore datastore;
	
	private static AdvancedDatastore datastore2;
	
	private static String targetPath;
	
	private static MYDateFormat format = MYDateFormat.getInstance("yyww");
	
	private static String collectionName="";
	
	public static void main(String[] args) throws Exception {
		if(args.length>1){
			ConfigLoad.configLoadForProPerties(args[0]);
			//history_1844
			collectionName=args[1];
		}else{
			System.out.println("缺少配置文件目录参数");
			System.exit(0);
		}
		//初始化morphia
		initDatastore();
		initDatastore2();
		//获取vin列表
		String filepath = ConfigCommon.getString("vins.path", "");
		File file =new File(filepath);
		if(!file.exists()) {
			System.out.println("找不到vin文件");
			System.exit(0);
		}
		//获取车架号列表 vinlist
		List<String> vins=FileUtils.readLines(file);
//		vins.add(ConfigCommon.getString("car.vin", "LS4ADM3C8HA940125"));
		//获取历史数据导出时间列表
		//获取历史数据导出时间列表
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(format.parse(collectionName.substring(8)));
		final Date[] dates = new Date[8];
		for(int i=0;i<8;i++) {
			dates[i]=startTime.getTime();
			startTime.add(Calendar.DAY_OF_YEAR, 1);
		}
		//获取导出文件路径
		targetPath = ConfigCommon.getString("target.path", "/home/futuremove/history");
		//查询并导出数据
		for(String vin:vins) {
			String carId=getCarId(vin);
			if(null!=carId) {
				new File(targetPath+"/"+vin).mkdirs();
				for(int i=0;i<7;i++) {
					exportData(vin,carId,dates[i],dates[i+1]);
				}
				System.out.println(vin+" export finish");
			}else {
				System.out.println(vin+" no exists");
			}
		}
	}
	
	private static String getCarId(String vin) {
		Query<CarSimpleInfoMongo> query = datastore2.createQuery(CarSimpleInfoMongo.class);
		query.field("staticInfo.vin").equal(vin);
		query.retrievedFields(true, "staticInfo.vin","carId","_id");
		CarSimpleInfoMongo carInfo = query.get();
		if(carInfo!=null) {
			return carInfo.getCarId();
		}
		return null;
	}
	
	private static void exportData(String vin,String carId,Date startTime,Date endTime) throws Exception {
		String date =DateTimeUtils.formatDate( startTime.getTime(), "yyyy-MM-dd");
		//查询数据
		List<CarInfoHistoryMongo> datas = queryData(carId,startTime,endTime);
		System.out.println(vin+"-"+date+" query "+datas.size());
		//导出数据
		if(datas.size()>0) {
			HistoryToExcel2.exportReportHistoryData(getExcelFile(vin,date), datas);
			System.out.println(vin+"-"+date+" export over ");
		}
	}

	private static void exportReportHistoryData(File hexFile, List<CarInfoHistoryMongo> datas) throws Exception {
		List<String> lines = new ArrayList<String>();
		for(CarInfoHistoryMongo carInfo:datas) {
			lines.add(convertHex(carInfo));
		}
		FileUtils.writeLines(hexFile, lines);
	}

	private static String convertHex(CarInfoHistoryMongo carInfo) {
		return null;
	}

	private static File getExcelFile(String vin,String date) {
		return new File(targetPath+"/"+vin+"/history_"+date+".xls");
	}
	
	private static File getHexFile(String vin,String date) {
		return new File(targetPath+"/"+vin+"/history_"+date+".txt");
	}

	private static List<CarInfoHistoryMongo> queryData(String carId, Date startTime,Date endTime) throws Exception {
		Query<CarInfoHistoryMongo> query = datastore.createQuery(getCollectionName(startTime),CarInfoHistoryMongo.class);
		query.field("carId").equal(carId);
		query.field("updateTime").greaterThanOrEq(startTime);
		query.field("updateTime").lessThanOrEq(endTime);
		query.order("updateTime");
		return query.asList();
	}

	private static void initDatastore() {
		Morphia morphia = new Morphia();
		//morphia参数设置
		MapperOptions options = new MapperOptions();
		options.setStoreEmpties(ConfigCommon.getProperties("store.empties",false));
		options.setStoreNulls(ConfigCommon.getProperties("store.nulls",false));
		//morphia的相关配置文件
		morphia.getMapper().setOptions(options);
		morphia.mapPackage(ConfigCommon.getString("mapper.path", "com.cm.mongo.entity"), true);
		String key="mongo.history";
		String host = ConfigCommon.getString(key+".host", "182.92.132.44");
		int port = ConfigCommon.getProperties(key+".port", 27018);
		String dbname=ConfigCommon.getString(key+".db", "history");
		String username = ConfigCommon.getString(key+".username", "history");
		String password = ConfigCommon.getString(key+".password", "futuremove");
		ServerAddress oldAddr = new ServerAddress(host, port);
		ArrayList<MongoCredential> arrayList = new ArrayList<MongoCredential>();
		if(!"".equals(username)&&!"".equals(password)){
			MongoCredential credential = MongoCredential.createCredential(username, dbname, password.toCharArray());
			arrayList.add(credential);
		}
		Builder builder = new MongoClientOptions.Builder()
					.connectionsPerHost(ConfigCommon.getProperties(key+".pool.maxsize", 100))
					.minConnectionsPerHost(ConfigCommon.getProperties(key+".pool.minsize", 0))
					.maxWaitTime(ConfigCommon.getProperties(key+".pool.waittime", 120)*1000)
					.maxConnectionIdleTime(ConfigCommon.getProperties(key+".pool.idletime", 300)*1000)
					.maxConnectionLifeTime(ConfigCommon.getProperties(key+".pool.lifetime", 0)*1000);
		datastore=(AdvancedDatastore) morphia.createDatastore(new MongoClient(oldAddr,arrayList,builder.build()), dbname);
	}
	
	private static void initDatastore2() {
		Morphia morphia = new Morphia();
		//morphia参数设置
		MapperOptions options = new MapperOptions();
		options.setStoreEmpties(ConfigCommon.getProperties("store.empties",false));
		options.setStoreNulls(ConfigCommon.getProperties("store.nulls",false));
		//morphia的相关配置文件 TODO
		morphia.getMapper().setOptions(options);
		morphia.mapPackage(ConfigCommon.getString("mapper.path", "com.cm.mongo.entity"), true);
		String key="mongo.monitor";
		String host = ConfigCommon.getString(key+".host", "182.92.132.44");
		int port = ConfigCommon.getProperties(key+".port", 27018);
		String dbname=ConfigCommon.getString(key+".db", "monitor");
		String username = ConfigCommon.getString(key+".username", "monitor");
		String password = ConfigCommon.getString(key+".password", "futuremove");
		ServerAddress oldAddr = new ServerAddress(host, port);
		ArrayList<MongoCredential> arrayList = new ArrayList<MongoCredential>();
		if(!"".equals(username)&&!"".equals(password)){
			MongoCredential credential = MongoCredential.createCredential(username, dbname, password.toCharArray());
			arrayList.add(credential);
		}
		Builder builder = new MongoClientOptions.Builder()
					.connectionsPerHost(ConfigCommon.getProperties(key+".pool.maxsize", 100))
					.minConnectionsPerHost(ConfigCommon.getProperties(key+".pool.minsize", 0))
					.maxWaitTime(ConfigCommon.getProperties(key+".pool.waittime", 120)*1000)
					.maxConnectionIdleTime(ConfigCommon.getProperties(key+".pool.idletime", 300)*1000)
					.maxConnectionLifeTime(ConfigCommon.getProperties(key+".pool.lifetime", 0)*1000);
		datastore2=(AdvancedDatastore) morphia.createDatastore(new MongoClient(oldAddr,arrayList,builder.build()), dbname);
	}
	
	
	private static String getCollectionName(Date date) {
		return "history_"+MYDateFormat.getInstance("yyww").format(date);
	}
}
