package com.cm.export;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.mongodb.morphia.AdvancedDatastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.MapperOptions;
import org.mongodb.morphia.query.Query;

import com.cm.common.config.ConfigCommon;
import com.cm.common.config.ConfigLoad;
import com.cm.common.format.MYDateFormat;
import com.cm.common.utils.ByteUtils;
import com.cm.common.utils.DateTimeUtils;
import com.cm.common.utils.LogicUtil;
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

import ch.qos.logback.core.db.dialect.MySQLDialect;
import sun.misc.BASE64Encoder;
import sun.misc.Compare;

import com.mongodb.MongoClientOptions.Builder;
/**
 * 创区数据导出 单线程
 * @author zyl
 *
 */
public class ExportHistory8 {
	
	private static AdvancedDatastore datastore;
	
	private static AdvancedDatastore datastore2;
	
	private static String targetPath;
	
	private static final BASE64Encoder encoder = new BASE64Encoder();
	
	private static Map<String,String> vinMap=new HashMap<String,String>();
	
	private static String collectionName="";
	
	private static MYDateFormat format = MYDateFormat.getInstance("yyww");
	
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
		startTime.setTime(format.parse(collectionName.substring(8)));
		final Date[] dates = new Date[8];
		for(int i=0;i<8;i++) {
			dates[i]=startTime.getTime();
			startTime.add(Calendar.DAY_OF_YEAR, 1);
		}
		//获取导出文件路径
		targetPath = ConfigCommon.getString("target.path", "/root/history");
		final List<Object[]> list =  new ArrayList<Object[]>();
		//查询并导出数据
		for(final String vin:vins) {
			final String carId=getCarId(vin);
			if(null!=carId) {
				for(int i=0;i<7;i++) {
//					exportData(vin, carId, dates[i], dates[i+1]);
					int count = (int) count(carId,dates[i],dates[i+1]);
					if(count>0) {
						list.add(new Object[] {count,vin,carId,dates[i],dates[i+1]});
					}
				}
			}else {
				System.out.println(vin+" no exists");
			}
		}
		System.out.println("all:"+list.size());
		Collections.sort(list, new Comparator<Object[]>() {
			public int compare(Object[] o1, Object[] o2) {
				return (int)o1[0]-(int)o2[0];
			}
		});
		for(int i=0;i<list.size();i++) {
			exportData((String)list.get(i)[1], (String)list.get(i)[2], (Date)list.get(i)[3], (Date)list.get(i)[4]);
			if(i%10==0) {
				System.out.println("all:"+list.size()+"\t finish:"+i);
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
	
	private static void exportData(String vin,String carId,Date startTime,Date endTime) throws Exception {
		String date =DateTimeUtils.formatDate(startTime, "yyyy-MM-dd");
		long start =System.currentTimeMillis();
		//查询数据
		List<CarInfoHistoryMongo> datas = queryData(carId,startTime,endTime);
		//导出数据
		exportReportHistoryData(vin,getHexFile(vin,date), datas);
		System.out.println(vin+"-"+date+" export "+datas.size()+" "+(System.currentTimeMillis()-start));
	}

	private static void exportReportHistoryData(String vin,File hexFile, List<CarInfoHistoryMongo> datas) throws Exception {
		List<String> lines = new ArrayList<String>();
		for(CarInfoHistoryMongo carInfo:datas) {
			String hex=convertHex(carInfo,vin);
			if(LogicUtil.isNotNullAndEmpty(hex)) {
				lines.add(hex);
			}
		}
		if(lines.size()>0) {
			FileWriter w=null;
			try {
				w = new FileWriter(hexFile, true);
				IOUtils.writeLines(lines, null, w);
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				if(null!=w) {
					w.close();
				}
			}
		}
	}

	private static String convertHex(CarInfoHistoryMongo carInfo,String vin) throws Exception {
		FMHeader header = new FMHeader();
		header.setBegin("##");
		header.setAnswer(254);
		header.setEncryptType(1);
		header.setCode(vin);
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
		try {
			packet.unbuild();
			return DateTimeUtils.formatDate(carInfo.getReceiveTime(), "yyyyMMddHHmmss")+","+
					encoder.encode(packet.convertBytes()).replace("\n", "").replaceAll("\r", "");
		}catch(Exception e) {
			System.out.println("convert error "+carInfo.getId());
			return null;
		}
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

	private static List<CarInfoHistoryMongo> queryData(String carId, Date startTime,Date endTime) throws Exception {
		Query<CarInfoHistoryMongo> query = datastore.createQuery(getCollectionName(startTime),CarInfoHistoryMongo.class);
		query.field("carId").equal(carId);
		query.field("updateTime").greaterThanOrEq(startTime);
		query.field("updateTime").lessThanOrEq(endTime);
		query.order("updateTime");
		return query.asList();
	}

	private static long count(String carId, Date startTime,Date endTime) throws Exception {
		Query<CarInfoHistoryMongo> query = datastore.createQuery(getCollectionName(startTime),CarInfoHistoryMongo.class);
		query.field("carId").equal(carId);
		query.field("updateTime").greaterThanOrEq(startTime);
		query.field("updateTime").lessThanOrEq(endTime);
		return query.countAll();
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
