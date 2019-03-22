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
import com.cm.common.utils.DateTimeUtils;
import com.cm.mongo.entity.CarInfoHistoryHex;
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
import com.mongodb.MongoClientOptions.Builder;

public class ExportHistory3 {
	
	private static AdvancedDatastore datastore;
	
	private static AdvancedDatastore datastore2;
	
	private static String targetPath;
	
	private static String collectionName="";
	
	public static void main(String[] args) throws Exception {
		if(args.length>1){
			ConfigLoad.configLoadForProPerties(args[0]);
			collectionName=args[1];
		}else{
			System.out.println("缺少配置文件目录参数");
			System.exit(0);
		}
		//初始化morphia
		initDatastore();
		//获取导出文件路径
		//查询并导出数据
		for(String carId:queryCarIds()) {
			exportData(carId);
		}
	}
	
	private static List<String> queryCarIds() {
		List<String> carIds = datastore.getDB().getCollection(collectionName).distinct("carId");
		return carIds;
	}
	
	private static void exportData(String carId) throws Exception {
		System.out.println("start:"+carId);
		String collectionName2 = "t-"+collectionName;
		//查询数据
		List<CarInfoHistoryMongo> datas = queryData(carId);
		List<CarInfoHistoryHex> templist=new ArrayList<CarInfoHistoryHex>();
		//导出数据
		for(CarInfoHistoryMongo carInfo:datas) {
			templist.add(convertdata(carInfo));
			if(templist.size()==100) {
				datastore.insert(collectionName2,templist);
				templist=new ArrayList<CarInfoHistoryHex>();
			}
		}
		datastore.insert(collectionName2,templist);
		System.out.println(carId+"- export over "+datas.size());
	}
	
	private static CarInfoHistoryHex convertdata(CarInfoHistoryMongo carInfo) throws Exception{
		CarInfoHistoryHex carInfoHex=new CarInfoHistoryHex();
		carInfoHex.setCarId(carInfo.getCarId());
		carInfoHex.setCode(carInfo.getCode());
		carInfoHex.setUpdateTime(carInfo.getUpdateTime());
		carInfoHex.setReceiveTime(carInfo.getReceiveTime());
		carInfoHex.setType(carInfo.getType());
		carInfoHex.setData(convertHex(carInfo));
		return carInfoHex;
	}
	
	private static byte[] convertHex(CarInfoHistoryMongo carInfo) throws Exception {
		FMHeader header = new FMHeader();
		header.setBegin("##");
		header.setAnswer(254);
		header.setEncryptType(1);
		header.setCode(carInfo.getCode());
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
		return packet.convertBytes();
	}
	
	private static File getExcelFile(String vin,String date) {
		return new File(targetPath+"/"+vin+"/history_"+date+".xls");
	}
	
	private static File getHexFile(String vin,String date) {
		return new File(targetPath+"/"+vin+"/history_"+date+".txt");
	}

	private static List<CarInfoHistoryMongo> queryData(String carId) throws Exception {
		Query<CarInfoHistoryMongo> query = datastore.createQuery(collectionName,CarInfoHistoryMongo.class);
		query.field("carId").equal(carId);
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
		datastore.ensureIndexes("t-"+collectionName, CarInfoHistoryHex.class);
	}
	
	
	
	private static String getCollectionName(Date date) {
		return "history_"+MYDateFormat.getInstance("yyww").format(date);
	}
}
