package com.cm.export;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
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
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.MongoClientOptions.Builder;

import sun.misc.BASE64Encoder;

public class KafkaExportHistory {

	private static AdvancedDatastore datastore;
	
	private static AdvancedDatastore datastore2;
	
	private static Map<String,String> vinMap=new HashMap<String,String>();
	
	private static Producer<String, byte[]> producer;
	
	public static void main(String[] args) throws Exception {
		if(args.length>0){
			ConfigLoad.configLoadForProPerties(args[0]);
		}else{
			System.out.println("缺少配置文件目录参数");
			System.exit(0);
		}
		setProducer();
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
		List<String> vins=FileUtils.readLines(file);
		//获取历史数据导出时间列表
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(DateTimeUtils.parseDate(ConfigCommon.getString("time.start", "2018-07-22 13:11:25"), "yyyy-MM-dd HH:mm:ss"));
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(DateTimeUtils.parseDate(ConfigCommon.getString("time.end", "2018-08-07 09:57:00"), "yyyy-MM-dd HH:mm:ss"));
		//查询并导出数据
		for(String vin:vins) {
			String carId=getCarId(vin);
			Calendar initTime = Calendar.getInstance();
			initTime.setTime(startTime.getTime());
			if(null!=carId) {
				while(initTime.compareTo(endTime)<=0) {
					exportData(vin,carId,initTime,endTime);
				}
				System.out.println(vin+" export finish");
			}else {
				System.out.println(vin+" no exists");
			}
		}
	}
	
	private static void setProducer() {
		Map<String, Object> configs =new HashMap<String,Object>();
		configs.put("bootstrap.servers", ConfigCommon.getString("kafka.url", "10.21.200.206:9092"));
		configs.put("group.id","producer-2");
		configs.put("retries","1");
		configs.put("batch.size","16384");
		configs.put("linger.ms","1");
		configs.put("buffer.memory","33554432");
		configs.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        configs.put("value.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");
		producer=new KafkaProducer<String,byte[]>(configs);
				
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
	
	private static void exportData(String vin,String carId,Calendar startTime,Calendar endTime) throws Exception {
		String date =DateTimeUtils.formatDate( startTime.getTime(), "yyyy-MM-dd");
		//查询数据
		List<CarInfoHistoryMongo> datas = queryData(carId,startTime,endTime);
		System.out.println(vin+"-"+date+" query "+datas.size());
		//导出数据
		exportReportHistoryData(vin, datas);
		System.out.println(vin+"-"+date+" export over ");
	}

	private static void exportReportHistoryData(String vin, List<CarInfoHistoryMongo> datas) throws Exception {
		for(CarInfoHistoryMongo carInfo:datas) {
			byte[] data = convertHex(carInfo,vin);
			if(data!=null) {
				producer.send(new ProducerRecord<String, byte[]>(ConfigCommon.getString("kafka.topic", "hx-gb-topic"),vin, data));
			}else {
				System.out.println("vin:"+vin+"    数据异常");
				break;
			}
		}
	}

	private static byte[] convertHex(CarInfoHistoryMongo carInfo,String vin) throws Exception {
		if(!carInfo.getCode().equals(vin)) {
			return null;
		}
		FMHeader header = new FMHeader();
		header.setBegin("##");
		header.setAnswer(254);
		header.setEncryptType(1);
		header.setCode(vin);
		header.setCommand(3);
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
		return packet.convertBytes();
	}


	private static List<CarInfoHistoryMongo> queryData(String carId, Calendar startTime, Calendar endTime) throws Exception {
		Date start = startTime.getTime();
		startTime.add(Calendar.DAY_OF_YEAR, 1);
		Date end = startTime.getTime();
		if(startTime.compareTo(endTime)>0) {
			System.out.println("test time");
			end=endTime.getTime();
		}
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
