package com.cm.reimport;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.MapperOptions;
import org.mongodb.morphia.query.Query;

import com.cm.common.config.ConfigCommon;
import com.cm.common.config.ConfigLoad;
import com.cm.common.constant.CommonConstants;
import com.cm.common.format.MYDateFormat;
import com.cm.common.redis.RedisUtil;
import com.cm.common.utils.BeantUtil;
import com.cm.common.utils.ByteUtils;
import com.cm.common.utils.DateTimeUtils;
import com.cm.common.utils.JacksonUtil;
import com.cm.common.utils.LocationUtils;
import com.cm.mongo.entity.CarInfoHistoryMongo;
import com.cm.mongo.entity.CarInfoMongo;
import com.cm.mongo.entity.CarSimpleInfoMongo;
import com.cm.tbox.common.FMPackageConstant.REALTIME_SUBINFO;
import com.cm.tbox.packet.CanDataPacket2;
import com.cm.tbox.packet.CanDataSimplePacket;
import com.cm.tbox.packet.CarExtremumPacket;
import com.cm.tbox.packet.RealTimePacket;
import com.cm.tbox.packet.RealTimePacket2;
import com.cm.tbox.packet.base.BasePacket;
import com.cm.tbox.packet.base.FMHeader;
import com.cm.tbox.packet.base.FMPacket;
import com.cm.tbox.packet.realtime.BcmLightStatePacket;
import com.cm.tbox.packet.CanDataPacket;
import com.cm.tbox.packet.CarInfoPacket;
import com.cm.tbox.packet.CarLocationPacket;
import com.cm.tbox.packet.CarWarnPacket;
import com.cm.tbox.packet.ChargeDeviceTempPacket;
import com.cm.tbox.packet.ChargeDeviceVoltagePacket;
import com.cm.tbox.packet.DriveMotorPacket;
import com.cm.tbox.packet.realtime.EnduranceMileagePacket;
import com.cm.tbox.packet.ExtremumPacket;
import com.cm.tbox.packet.FuelCellsPacket;
import com.cm.tbox.packet.LocationPacket;
import com.cm.tbox.packet.MotorPacket;
import com.cm.tbox.packet.realtime.SurplusAvailableMileagePacket;
import com.cm.tbox.packet.realtime.TireDataPacket;
import com.cm.export.HistoryToExcel2;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import sun.misc.BASE64Encoder;

import com.mongodb.MongoClientOptions.Builder;

public class ImportHistory {
	
	private static AdvancedDatastore datastore;
	
	private static AdvancedDatastore datastore2;
	
	private static String targetPath;
	
	private static Map<String,String> vinMap=new HashMap<String,String>();
	
	private static final Set<String> finishFiles=new HashSet<String>();
	
	public static void main(String[] args) throws Exception {
		if(args.length>0){
			ConfigLoad.configLoadForProPerties(args[0]);
		}else{
			System.out.println("缺少配置文件目录参数");
			System.exit(0);
		}
		//初始化morphia
		readFinish(new File(ConfigCommon.getString("log.path", "import.log")));
		initDatastore();
		initDatastore2();
		String start=ConfigCommon.getString("time.start", "2017-10-22");
		String end=ConfigCommon.getString("time.end", "2017-11-11");
		//日志文件路径
		for(File file:getFileList(ConfigCommon.getString("vinlogs.path", "/home/futuremove/vinlogs"),start,end)) {
			//读取文件并导入数据
			System.out.println("start--"+file.getName());
			readAndInsert(file,start,end);
			System.out.println("end--"+file.getName());
		}
	}
	
	private static void readFinish(File file) throws Exception {
		if(!file.exists()) {
			return;
		}
		for(String line:(List<String>)FileUtils.readLines(file)) {
			if(line.startsWith("end--")) {
				finishFiles.add(line.substring(5));
			}
		}
		System.out.println(finishFiles);
	}

	private static void readAndInsert(File file,String start,String end) throws Exception {
		List<String> lines = FileUtils.readLines(file);
		String dateStr="";
		String name = file.getName();
		if(!name.contains("2017-")) {
			dateStr = lines.get(0).substring(0, 10);
			if(dateStr.compareTo(start)<0 || dateStr.compareTo(end)>0) {
				return;
			}
		}else {
			dateStr=name.substring(name.indexOf(".")-10,name.indexOf("."));
		}
		Date fileDate=DateTimeUtils.parseDate(dateStr, "yyyy-MM-dd");
		List<CarInfoHistoryMongo> list =new ArrayList<CarInfoHistoryMongo>();
		String code=name.substring(0,17);
		String carId=getCarId(code);
		for(String line:lines) {
			if(line.contains("receive:232303")||line.contains("receive:232302")) {
				Date date = DateTimeUtils.parseDate(line.substring(0,23), "yyyy-MM-dd HH:mm:ss,SSS");
				CarInfoHistoryMongo history = convert(carId,code,date,line.substring(32));
				if(null!=history) {
					list.add(history);
				}
			}
			if(list.size()==30) {
				insertBatch(getCollectionName(fileDate), list);
				list=new ArrayList<CarInfoHistoryMongo>();
			}
		}
		if(list.size()>0) {
			insertBatch(getCollectionName(fileDate), list);
		}
	}

	private static CarInfoHistoryMongo convert(String carId,String code,Date date, String hexStr) throws Exception {
		byte[] bytes = ByteUtils.hexStringToBytes(hexStr.substring(48,hexStr.length()-2));
		RealTimePacket2 realTimePacket = new RealTimePacket2();
		realTimePacket.build(bytes);
		boolean isAgain=(hexStr.charAt(5)=='3');
		//实时包重设时间校验不准的数据
		if((!isAgain) && Math.abs(date.getTime()-realTimePacket.getUploadTime().getTime())>60*60*1000l){
			realTimePacket.setUploadTime(date);
		}
		//mongo缓存对象
		CarInfoHistoryMongo history = new CarInfoHistoryMongo();
		history.setCarId(carId);
		history.setCode(code);
		history.setUpdateTime(realTimePacket.getUploadTime());
		//可充电储能装置电压数据
		ChargeDeviceVoltagePacket carCellVoltagePacket = (ChargeDeviceVoltagePacket)realTimePacket.getSubInfoPackets().
				get(REALTIME_SUBINFO.voltage);
		history.setChargeDeviceVoltage(carCellVoltagePacket);
		//动力蓄电池包温度数据
		ChargeDeviceTempPacket carCellTempPacket = (ChargeDeviceTempPacket)realTimePacket.getSubInfoPackets().
				get(REALTIME_SUBINFO.temp);
		history.setChargeDeviceTemp(carCellTempPacket);
		//基本信息
		CarInfoPacket carInfoPacket = (CarInfoPacket)realTimePacket.getSubInfoPackets().
													get(REALTIME_SUBINFO.car_info);
		history.setCarInfoRecord(carInfoPacket);
		//定位信息
		LocationPacket carLocationPacket = (LocationPacket)realTimePacket.getSubInfoPackets().
													get(REALTIME_SUBINFO.location);
		
		history.setCarLocationRecord(carLocationPacket);
		history.setDriveMotorRecord((DriveMotorPacket)realTimePacket.getSubInfoPackets().get(REALTIME_SUBINFO.drive_motor));
		history.setFuelCellsRecord((FuelCellsPacket)realTimePacket.getSubInfoPackets().get(REALTIME_SUBINFO.fuel));
		history.setMotorPacket((MotorPacket)realTimePacket.getSubInfoPackets().get(REALTIME_SUBINFO.motor));
		//极值信息
		ExtremumPacket extremunPacket =(ExtremumPacket)realTimePacket.getSubInfoPackets().get(REALTIME_SUBINFO.extreme);
		history.setCarExtremum(extremunPacket);
		//告警信息
		CarWarnPacket warnPacket =(CarWarnPacket)realTimePacket.getSubInfoPackets().
				get(REALTIME_SUBINFO.warn);
		history.setCarWarn(warnPacket);
		//can报文
		history.setCanDataRecord((CanDataPacket)realTimePacket.getSubInfoPackets().get(REALTIME_SUBINFO.can_data));
		//can报文
		CanDataPacket2 candata2=(CanDataPacket2)realTimePacket.getSubInfoPackets().get(REALTIME_SUBINFO.can_data2);
		if(candata2!=null){
			history.setCanDataRecord(candata2.getCanData());
		}
		//can报文简版
		CanDataSimplePacket candataSimple=(CanDataSimplePacket)realTimePacket.getSubInfoPackets().get(REALTIME_SUBINFO.can_data_simple);
		SurplusAvailableMileagePacket surplusAvailableMileagePacket=null;
		if(candataSimple !=null){
			history.setCanDataNewRecord(candataSimple.getCanData());
		    TireDataPacket tireDataPacket =  TireDataPacket.valueOfCanDataNewPacket(candataSimple.getCanData());
            if (tireDataPacket != null) {
                System.out.println(tireDataPacket);
                history.setTireDataPacket(tireDataPacket);
            }
            BcmLightStatePacket bcmLightStatePacket = BcmLightStatePacket.valueOfCanDataNewPacket(candataSimple.getCanData());
            if (bcmLightStatePacket != null) {
                System.out.println(bcmLightStatePacket);
                history.setBcmLightStatePacket(bcmLightStatePacket);
            }
            //续航里程
            EnduranceMileagePacket enduranceMileagePacket=EnduranceMileagePacket.valueOfCanDataNewPacket(candataSimple.getCanData());
            if(enduranceMileagePacket !=null){
            	history.setEnduranceMileageRecord(enduranceMileagePacket);
            }
            //剩余可用里程
            surplusAvailableMileagePacket=SurplusAvailableMileagePacket.valueOfCanDataNewPacket(candataSimple.getCanData());
            if(surplusAvailableMileagePacket !=null){
            	history.setSurplusAvailableMileageRecord(surplusAvailableMileagePacket);
            }
		}
		history.setReceiveTime(date);
		//实时数据 处理mongo缓存数据
		if(isAgain){
			history.setType(1);
			//直接插入
			insert(history);
			return null;
		}
		return history;
	}

	private static void insert(CarInfoHistoryMongo history) {
		datastore.insert(getCollectionName(history.getUpdateTime()), history);
	}
	
	private static void insertBatch(String name,List<CarInfoHistoryMongo> historys) {
		datastore.insert(name, historys);
	}

	private static List<File> getFileList(String dirPath,final String start,final String end){
		Collection listFiles = FileUtils.listFiles(new File(dirPath), new IOFileFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return false;
			}
			@Override
			public boolean accept(File file) {
				String name =file.getName();
				if(finishFiles.contains(name)) {
					return false;
				}
				if(name.contains("2017-")) {
					String date = name.substring(name.indexOf(".")-10,name.indexOf("."));
					if(date.compareTo(start)>=0 && date.compareTo(end)<=0) {
						return true;
					}else {
						return false;
					}
				}else {
					return true;
				}
			}
		}, null);
		return (List<File>)listFiles;
	}
	
	private static String getCarId(String code) {
		String carId= vinMap.get(code);
		if(null==carId) {
			Query<CarSimpleInfoMongo> query = datastore2.createQuery(CarSimpleInfoMongo.class);
			query.or(query.criteria("staticInfo.vin").equal(code),
					query.criteria("staticInfo.terminalSn").equal(code));
			query.retrievedFields(true, "staticInfo.vin");
			CarSimpleInfoMongo carInfo = query.get();
			if(carInfo!=null) {
				vinMap.put(code,carInfo.getCarId());
				return carInfo.getCarId();
			}
		}
		return carId;
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
