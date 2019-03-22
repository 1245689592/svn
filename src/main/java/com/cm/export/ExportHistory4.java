package com.cm.export;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.MapperOptions;
import org.mongodb.morphia.query.Query;

import com.cm.common.config.ConfigCommon;
import com.cm.common.config.ConfigLoad;
import com.cm.common.format.MYDateFormat;
import com.cm.common.utils.ByteUtils;
import com.cm.common.utils.DateTimeUtils;
import com.cm.mongo.entity.CarInfoHistoryMongo;
import com.cm.mongo.entity.CarSimpleInfoMongo;
import com.cm.tbox.packet.CanDataPacket2;
import com.cm.tbox.packet.CanDataSimplePacket;
import com.cm.tbox.packet.RealTimePacket;
import com.cm.tbox.packet.base.BasePacket;
import com.cm.tbox.packet.base.FMHeader;
import com.cm.tbox.packet.base.FMPacket;
import com.cm.export.HistoryToExcel2;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import sun.misc.BASE64Encoder;

import com.mongodb.MongoClientOptions.Builder;

public class ExportHistory4 {
	
	private static AdvancedDatastore datastore;
	
	private static AdvancedDatastore datastore2;
	
	private static String targetPath;
	
	private static final BASE64Encoder encoder = new BASE64Encoder();
	
	private static Map<String,String> vinMap=new HashMap<String,String>();
	
	public static void main(String[] args) throws Exception {
		if(args.length>0){
			ConfigLoad.configLoadForProPerties(args[0]);
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
		List<String> vins=new ArrayList<String>();
		Map<String,String> map = new HashMap<String,String>();
		for(String line:(List<String>)FileUtils.readLines(file)) {
			String[] strs=line.split(",");
			vins.add(strs[0]);
			map.put(strs[0], strs[1]);
		}
		//获取历史数据导出时间列表
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(DateTimeUtils.parseDate(ConfigCommon.getString("time.start", "2018-03-03"), "yyyy-MM-dd"));
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(DateTimeUtils.parseDate(ConfigCommon.getString("time.end", "2018-03-03"), "yyyy-MM-dd"));
		//获取导出文件路径
		targetPath = ConfigCommon.getString("target.path", "/root/history");
//		exportType = ConfigCommon.getString("export.type", "excel");
//		exportType = ConfigCommon.getString("export.type", "hex");
		//查询并导出数据
		for(String vin:vins) {
			String carId=getCarId(vin);
			Calendar initTime = Calendar.getInstance();
			initTime.setTime(startTime.getTime());
			if(null!=carId) {
				while(initTime.compareTo(endTime)<=0) {
					exportData(vin,carId,initTime,map.get(vin));
				}
				System.out.println(vin+" export finish");
			}else {
				System.out.println(vin+" no exists");
			}
		}
	}
	
	private static String getCarId(String vin) {
		String carId= vinMap.get(vin);
		if(null==carId) {
			Query<CarSimpleInfoMongo> query = datastore2.createQuery(CarSimpleInfoMongo.class);
			query.field("staticInfo.vin").equal(vin);
			query.retrievedFields(true, "staticInfo.vin");
			CarSimpleInfoMongo carInfo = query.get();
			if(carInfo!=null) {
				vinMap.put(vin,carInfo.getCarId());
				return carInfo.getCarId();
			}
		}
		return carId;
	}
	
	private static void exportData(String vin,String carId,Calendar startTime,String carVin) throws Exception {
		String date =DateTimeUtils.formatDate( startTime.getTime(), "yyyy-MM-dd");
		//查询数据
		List<CarInfoHistoryMongo> datas = queryData(carId,startTime);
		System.out.println(vin+"-"+date+" query "+datas.size());
		//导出数据
		exportReportHistoryData(vin,getHexFile(vin,date), datas,carVin);
		System.out.println(vin+"-"+date+" export over ");
	}

	private static void exportReportHistoryData(String vin,File hexFile, List<CarInfoHistoryMongo> datas,String carVin) throws Exception {
		List<String> lines = new ArrayList<String>();
		for(CarInfoHistoryMongo carInfo:datas) {
			lines.add(convertHex(carInfo,vin,carVin));
		}
		FileUtils.writeLines(hexFile, lines);
	}

	private static String convertHex(CarInfoHistoryMongo carInfo,String vin,String  carVin) throws Exception {
		FMHeader header = new FMHeader();
		header.setBegin("##");
		header.setAnswer(254);
		header.setEncryptType(1);
		header.setCode(carVin);
		header.setCommand(carInfo.getType()+2);
		RealTimePacket realtime = new RealTimePacket();
		realtime.setUploadTime(carInfo.getUpdateTime());
		Map<Byte,BasePacket> map = new HashMap<Byte,BasePacket>();
		realtime.setSubInfoPackets(map);
		if(null!=carInfo.getCarInfoRecord()) {
			map.put((byte)(0xff & 0x01), carInfo.getCarInfoRecord());
		}
		if(null!=carInfo.getDriveMotorRecord()) {
			map.put((byte)(0xff & 0x02), carInfo.getDriveMotorRecord());
		}
		if(null!=carInfo.getFuelCellsRecord()) {
			map.put((byte)(0xff & 0x03), carInfo.getFuelCellsRecord());
		}
		if(null!=carInfo.getMotorPacket()) {
			map.put((byte)(0xff & 0x04), carInfo.getMotorPacket());
		}
		if(null!=carInfo.getCarLocationRecord()) {
			map.put((byte)(0xff & 0x05), carInfo.getCarLocationRecord());
		}
		if(null!=carInfo.getCarExtremum()) {
			map.put((byte)(0xff & 0x06), carInfo.getCarExtremum());
		}
		if(null!=carInfo.getCarWarn()) {
			map.put((byte)(0xff & 0x07), carInfo.getCarWarn());
		}
		if(null!=carInfo.getChargeDeviceVoltage()) {
			map.put((byte)(0xff & 0x08), carInfo.getChargeDeviceVoltage());
		}
		if(null!=carInfo.getChargeDeviceTemp()) {
			map.put((byte)(0xff & 0x09), carInfo.getChargeDeviceTemp());
		}
		if(null!=carInfo.getCanDataRecord()) {
			CanDataPacket2 cdpacket = new CanDataPacket2();
			cdpacket.setCanData(carInfo.getCanDataRecord());
			map.put((byte)(0xff & 0x88), cdpacket);
		}
		if(null!=carInfo.getCanDataNewRecord()) {
			CanDataSimplePacket cdsPacket=new CanDataSimplePacket();
			cdsPacket.setCanData(carInfo.getCanDataNewRecord());
			map.put((byte)(0xff & 0x89), cdsPacket);
		}
		FMPacket packet= new FMPacket(header, realtime);
		packet.unbuild();
		return DateTimeUtils.formatDate(carInfo.getReceiveTime(), "yyyyMMddHHmmss")+","+
				encoder.encode(packet.convertBytes());
//				ByteUtils.asHex(packet.convertBytes());
	}

	private static File getExcelFile(String vin,String date) {
		return new File(targetPath+"/"+vin+"/history_"+date+".xls");
	}
	
	private static File getHexFile(String vin,String date) {
		String[] dates=date.split("-");
		String dirPath=targetPath+"/"+dates[0]+"/"+dates[1]+"/"+dates[2];
		new File(dirPath).mkdirs();
		return new File(dirPath+"/"+vin);
	}

	private static List<CarInfoHistoryMongo> queryData(String carId, Calendar startTime) throws Exception {
		Date start = startTime.getTime();
		startTime.add(Calendar.DAY_OF_YEAR, 1);
		Date end = startTime.getTime();
		Query<CarInfoHistoryMongo> query = datastore.createQuery(getCollectionName(start),CarInfoHistoryMongo.class);
		query.field("carId").equal(carId);
		query.field("updateTime").greaterThanOrEq(start);
		query.field("updateTime").lessThanOrEq(end);
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
		String key="history";
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
		String key="monitor";
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
