package com.cm.export;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cm.common.utils.DateTimeUtils;
import com.cm.mongo.entity.CarInfoHistoryMongo;
import com.cm.tbox.packet.CanDataNewPacket;
import com.cm.tbox.packet.CanDataPacket;
import com.cm.tbox.packet.CarCellTempPacket;
import com.cm.tbox.packet.CarCellTempSinglePacket;
import com.cm.tbox.packet.CarCellVoltagePacket;
import com.cm.tbox.packet.CarCellVoltageSinglePacket;
import com.cm.tbox.packet.CarDoorStatusPacket;
import com.cm.tbox.packet.CarExpandMorePacket;
import com.cm.tbox.packet.CarExpandOtherStatusPacket;
import com.cm.tbox.packet.CarExpandPacket;
import com.cm.tbox.packet.CarExtremumPacket;
import com.cm.tbox.packet.CarHGExpandPacket;
import com.cm.tbox.packet.CarInfoPacket;
import com.cm.tbox.packet.CarLHXNExpandPacket;
import com.cm.tbox.packet.CarLocationPacket;
import com.cm.tbox.packet.CarOtherStatusPacket;
import com.cm.tbox.packet.CarWarnPacket;
import com.cm.tbox.packet.ChargeDeviceTempPacket;
import com.cm.tbox.packet.ChargeDeviceVoltagePacket;
import com.cm.tbox.packet.DriveMotorInfoPacket;
import com.cm.tbox.packet.DriveMotorPacket;
import com.cm.tbox.packet.ExtremumPacket;
import com.cm.tbox.packet.FuelCellsPacket;
import com.cm.tbox.packet.LocationPacket;
import com.cm.tbox.packet.MotorPacket;
import com.cm.tbox.packet.SingleChargeDeviceTempPacket;
import com.cm.tbox.packet.SingleChargeDeviceVoltagePacket;
import com.cm.tbox.packet.warn.CarInfoWarnPacket;
import com.cm.tbox.packet.warn.CarWarnCommonPacket;
import com.cm.tbox.packet.warn.CellWarnPacket;
import com.cm.tbox.packet.warn.ChargerWarnPacket;
import com.cm.tbox.packet.warn.DCWarnPacket;
import com.cm.tbox.packet.warn.LHXNWarnPacket;
import com.cm.tbox.packet.warn.MotorWarnPacket;
import com.cm.tbox.packet.warn.PowerCellWarnPacket;

/**
 * 历史数据导出为excel
 * @author yunlu
 *
 */
@SuppressWarnings("unchecked")
public class HistoryToExcel2 {
	
	private static final String NA="N/A";
	
	/**
	 * 包类型
	 * @author yunlu
	 *
	 */
	public static enum Type{
		location,
		cellTemp,
		cellVoltage,
		carInfo,
		extremum,
		expand,
		hgExpand,
		zxExpand,
		expand1,
		expand2,
		powerCellWarn,
		cellWarn,
		carInfoWarn,
		chargerWarn,
		motorWarn,
		dcWarn,
		lhxnWarn,
		lhxnExpand,
		
		//2016-12-20新增
		driveMotorRecord,
		fuelCellsRecord,
		motorPacket,
		carWarnCommon,
		chargeDeviceVoltage,
		chargeDeviceTemp,
		//2017-03-06新增,导出车辆和终端信息
		vehilceAndTerminal,
		//2018-06-27新增
		canData
	}
	/**
	 * 通用值有效范围判断
	 * @param value
	 * @param min
	 * @param max
	 */
	private static String checkValue(Integer value,Integer min,Integer max){
		if(value!=null){
			if(min<=value&&value<=max){
				return value.toString();
			}else{
				return NA;
			}
		}else{
			return "";
		}
	}
	/**
	 * 通用值有效范围判断
	 * @param value
	 * @param min
	 * @param max
	 */
	private static String checkValue(Double value,Double min,Double max){
		if(value!=null){
			if(min<=value&&value<=max){
				return value.toString();
			}else{
				return NA;
			}
		}else{
			return "";
		}
	}
	/**
	 * 增加空数据 
	 * @param list
	 * @param num
	 */
	private static void addEmpty(List list, int num) {
		for (int i = 0; i < num; i++) {
			list.add("");
		}
	}
	/**
	 * 通用值判断
	 * @param node
	 * @param oneValue 如果值为1的描述
	 * @param zeroValue 如果值为0的描述
	 * @return
	 */
	private static String checkValue(Integer value,String oneValue,String zeroValue){
		if(value==null){
			return "";
		}else{
			if(value==0){
				return zeroValue;
			}else if(value==1){
				return oneValue;
			}else{
				return NA;
			}
		}
	}
	/**
	 * 通用值判断
	 * @param node
	 * @param zeroValue 如果值为0的描述
	 * @param oneValue 如果值为1的描述
	 * @param twoValue 如果值为2的描述
	 * @param treeValue 如果值为3的描述
	 * @return
	 */
	private static String checkValue(Integer value,String zeroValue,String oneValue,String twoValue,String treeValue){
		if(value==null){
			return "";
		}else{
			if(value==0){
				return zeroValue;
			}else if(value==1){
				return oneValue;
			}else if(value==2){
				return twoValue;
			}else if(value==3){
				return treeValue;
			}else{
				return NA;
			}
		}
	}	
	/**
	 * 通用值判断
	 * @param node
	 * @return
	 */
	private static Object checkValue(Object node){
		if(node==null){
			return "";
		}else{
			return node;
		}
	}
	/**
	 * 生成临时下载文件 返回文件地址
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void exportReportHistoryData(File file,List<CarInfoHistoryMongo> result) throws Exception{
		//定义所有包的sheet对象

		ExcelSheet carInfoSheet = new ExcelSheet("基本",ExcelHeaderUtil2.getHeader(Type.carInfo),new ArrayList<List>());
		//2016-12-20新增
		ExcelSheet driveMotorRecordSheet = new ExcelSheet("驱动电机信息",ExcelHeaderUtil2.getHeader(Type.driveMotorRecord),new ArrayList<List>());
		ExcelSheet fuelCellsRecordSheet = new ExcelSheet("燃料电池信息",ExcelHeaderUtil2.getHeader(Type.fuelCellsRecord),new ArrayList<List>());
		ExcelSheet motorPacketSheet = new ExcelSheet("发动机数据",ExcelHeaderUtil2.getHeader(Type.motorPacket),new ArrayList<List>());
		ExcelSheet chargeDeviceVoltageSheet = new ExcelSheet("可充电储能装置电压数据",ExcelHeaderUtil2.getHeader(Type.chargeDeviceVoltage),new ArrayList<List>());
		ExcelSheet chargeDeviceTempSheet = new ExcelSheet("可充电储能装置温度数据",ExcelHeaderUtil2.getHeader(Type.chargeDeviceTemp),new ArrayList<List>());

		ExcelSheet extremumSheet = new ExcelSheet("极值",ExcelHeaderUtil2.getHeader(Type.extremum),new ArrayList<List>());
		ExcelSheet locationSheet = new ExcelSheet("位置",ExcelHeaderUtil2.getHeader(Type.location),new ArrayList<List>());
		ExcelSheet cellTempSheet = new ExcelSheet("电池温度",ExcelHeaderUtil2.getHeader(Type.cellTemp),new ArrayList<List>());
		ExcelSheet cellVoltageSheet = new ExcelSheet("电池电压",ExcelHeaderUtil2.getHeader(Type.cellVoltage),new ArrayList<List>());
		
//		ExcelSheet expandSheet = new ExcelSheet("扩展",ExcelHeaderUtil2.getHeader(Type.expand),new ArrayList<List>());
		ExcelSheet powerCellWarnSheet = new ExcelSheet("报警-动力蓄电池",ExcelHeaderUtil2.getHeader(Type.powerCellWarn),new ArrayList<List>());
		//2016-12-20新增
		ExcelSheet carWarnCommonSheet = new ExcelSheet("通用报警",ExcelHeaderUtil2.getHeader(Type.carWarnCommon),new ArrayList<List>());
		
		ExcelSheet cellWarnheet = new ExcelSheet("报警-电池",ExcelHeaderUtil2.getHeader(Type.cellWarn),new ArrayList<List>());
		ExcelSheet carInfoWarnSheet = new ExcelSheet("报警-整车",ExcelHeaderUtil2.getHeader(Type.carInfoWarn),new ArrayList<List>());
		ExcelSheet chargerWarnSheet = new ExcelSheet("报警-充电机",ExcelHeaderUtil2.getHeader(Type.chargerWarn),new ArrayList<List>());
		ExcelSheet motorWarnSheet = new ExcelSheet("报警-电机",ExcelHeaderUtil2.getHeader(Type.motorWarn),new ArrayList<List>());
		ExcelSheet dcWarnSheet = new ExcelSheet("报警-DC",ExcelHeaderUtil2.getHeader(Type.dcWarn),new ArrayList<List>());

		ExcelSheet canDataSheet = new ExcelSheet("can报文",ExcelHeaderUtil2.getHeader(Type.canData),new ArrayList<List>());
//		ExcelSheet hgExpandSheet = new ExcelSheet("HG扩展",ExcelHeaderUtil2.getHeader(Type.hgExpand),new ArrayList<List>());
//		ExcelSheet expand1Sheet = new ExcelSheet("扩展一",ExcelHeaderUtil2.getHeader(Type.expand1),new ArrayList<List>());
//		ExcelSheet expand2Sheet = new ExcelSheet("扩展二",ExcelHeaderUtil2.getHeader(Type.expand2),new ArrayList<List>());
		
//		ExcelSheet lhxnWarnSheet = new ExcelSheet("蓝海新能告警",ExcelHeaderUtil2.getHeader(Type.lhxnWarn),new ArrayList<List>());
//		ExcelSheet lhxnExpandSheet = new ExcelSheet("蓝海新能扩展",ExcelHeaderUtil2.getHeader(Type.lhxnExpand),new ArrayList<List>());
		List<ExcelSheet> sheetList = new ArrayList<ExcelSheet>();
		sheetList.add(carInfoSheet);
		//2016-12-20新增
		sheetList.add(driveMotorRecordSheet);
		sheetList.add(fuelCellsRecordSheet);
		sheetList.add(motorPacketSheet);
		sheetList.add(chargeDeviceVoltageSheet);
		sheetList.add(chargeDeviceTempSheet);
		
		sheetList.add(extremumSheet);
		sheetList.add(locationSheet);
		sheetList.add(cellTempSheet);
		sheetList.add(cellVoltageSheet);
	
//		sheetList.add(expandSheet);
		sheetList.add(powerCellWarnSheet);
		sheetList.add(carWarnCommonSheet);
		sheetList.add(cellWarnheet);
		sheetList.add(carInfoWarnSheet);
		sheetList.add(chargerWarnSheet);
		sheetList.add(motorWarnSheet);
		sheetList.add(dcWarnSheet);
		sheetList.add(canDataSheet);
//		sheetList.add(hgExpandSheet);
//		sheetList.add(expand1Sheet);
//		sheetList.add(expand2Sheet);
		
//		sheetList.add(cellWarnheet);
//		sheetList.add(carInfoWarnSheet);
//		sheetList.add(chargerWarnSheet);
		
		
//		sheetList.add(lhxnWarnSheet);
//		sheetList.add(lhxnExpandSheet);
		// 得到输出流
		for(int i=0;i<result.size();i++){
			CarInfoHistoryMongo carHistory = result.get(i);
			String dateTime = DateTimeUtils.formatDate(carHistory.getUpdateTime());
			String receiveTime = DateTimeUtils.formatDate(carHistory.getReceiveTime());
			String type  = carHistory.getType()==0?"实时数据":"补发数据";
			carInfo(carInfoSheet,carHistory,dateTime,receiveTime,type);
			//2016-12-20新增
			driveMotorRecord(driveMotorRecordSheet,carHistory,dateTime,receiveTime,type);
			fuelCellsRecord(fuelCellsRecordSheet,carHistory,dateTime,receiveTime,type);
			motorPacket(motorPacketSheet,carHistory,dateTime,receiveTime,type);
			chargeDeviceVoltage(chargeDeviceVoltageSheet,carHistory,dateTime,receiveTime,type);
			chargeDeviceTemp(chargeDeviceTempSheet,carHistory,dateTime,receiveTime,type);
			
			extreme(extremumSheet,carHistory,dateTime,receiveTime,type);
			location(locationSheet,carHistory,dateTime,receiveTime,type);
			cellTemp(cellTempSheet,carHistory,dateTime,receiveTime,type);
			cellVoltage(cellVoltageSheet,carHistory,dateTime,receiveTime,type);
//			carExpandMore(expandSheet,carHistory,dateTime);
//			
//			carHGExpand(hgExpandSheet,carHistory,dateTime);
//			carExpandMore1(expand1Sheet,carHistory,dateTime);
//			carExpandMore2(expand2Sheet,carHistory,dateTime);
			CarWarnPacket carWarn = carHistory.getCarWarn();
			if(carWarn!=null){
				//2016-12-20新增
				carWarnCommon(carWarnCommonSheet,carWarn,dateTime,receiveTime,type);
				cellWarn(cellWarnheet, carWarn, dateTime,receiveTime,type);
				carInfoWarn(carInfoWarnSheet, carWarn, dateTime,receiveTime,type);
				chargerWarn(chargerWarnSheet, carWarn, dateTime,receiveTime,type);
				powerCellWarn(powerCellWarnSheet, carWarn, dateTime,receiveTime,type);
				motorWarn(motorWarnSheet, carWarn, dateTime,receiveTime,type);
				dcWarn(dcWarnSheet, carWarn, dateTime,receiveTime,type);
//				lhxnWarn(lhxnWarnSheet, carWarn, dateTime);
			}
//			lhxnExpand(lhxnExpandSheet,carHistory,dateTime);
//			expand(expandSheet,carHistory,dateTime);
			canData(canDataSheet,carHistory,dateTime,receiveTime,type);
		}
		ListToExcel.exportExcelForSheet(sheetList, file);
	}
	private static void canData(ExcelSheet canDataSheet, CarInfoHistoryMongo carHistory, String dateTime,
			String receiveTime, String type) {
		CanDataNewPacket canDataNewRecord = carHistory.getCanDataNewRecord();
		CanDataPacket canDataRecord = carHistory.getCanDataRecord();
		Map<String, List<String>> data=null;
		if(canDataNewRecord!=null) {
			data= canDataNewRecord.getData();
		}else if(canDataRecord!=null) {
			data = canDataRecord.getData();
		}
		if(null==data) {
			return;
		}
		if(data.size()>canDataSheet.getHeaderList().size()-3) {
			for(String key:data.keySet()) {
				if(!canDataSheet.getHeaderList().contains(key)) {
					canDataSheet.getHeaderList().add(key);
				}
			}
		}
		List temp = new ArrayList();
		temp.add(dateTime);
		temp.add(receiveTime);
		temp.add(type);
		for(int i=3;i<canDataSheet.getHeaderList().size();i++) {
			temp.add(data.get(canDataSheet.getHeaderList().get(i)));
		}
		canDataSheet.addData(temp);
	}
	/**
	 * 定位信息
	 * @param temp
	 * @param carHistory
	 * @return
	 */
	private static void location(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type){
		LocationPacket carLocation =carHistory.getCarLocationRecord();
		List temp = new ArrayList();
		if(carLocation !=null){
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			temp.add(checkValue(carLocation.getStatus(), "无效", "有效"));
			temp.add(checkValue(carLocation.getLonType(), "西经", "东经")+":"+checkValue(carLocation.getLon()));
			temp.add(checkValue(carLocation.getLatType(), "南纬", "北纬")+":"+checkValue(carLocation.getLat()));
			sheet.addData(temp);
		}
	}
	
	/**
	 * 车辆扩展信息
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 */
	private static void expand(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type){
		CarExpandPacket expand =carHistory.getCarExpandRecord();
		CarExpandMorePacket expandMore = carHistory.getCarExpandMoreRecord();
		List temp = new ArrayList();
		if(expand !=null){
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			CarDoorStatusPacket carDoor=expand.getCarDoorStatus();
			CarExpandOtherStatusPacket carOtherStatus = expand.getCarOtherStatus();
			
			temp.add(checkValue(expand.getDumpEnergy()));
			temp.add(checkValue(expand.getRange(),0.0,6553.4));
			temp.add(checkValue(carDoor.getDoorStatus(), "有效","无效"));
			temp.add(checkValue(expand.getAccStatus(),"点火","熄火"));
			temp.add(checkValue(carDoor.getSkyLightStatus(),"开门" , "关门"));
			temp.add(checkValue(carDoor.getTrunkStatus()  ,"开门" , "关门"));
			temp.add(checkValue(carDoor.getLeftBackDoorStatus()  ,"开门" , "关门"));
			temp.add(checkValue(carDoor.getRightBackDoorStatus(),"开门" , "关门"));
			temp.add(checkValue(carDoor.getLeftFrontDoorStatus() ,"开门" , "关门"));
			temp.add(checkValue(carDoor.getRightFrontDoorStatus(),"开门" , "关门"));
//			temp.add(checkValue(expand.getAccStatus(),"点火","熄火"));
			temp.add(checkValue(expand.getStorageCellVoltage(),0.0,25.4));
			temp.add(checkValue(expand.getSpeed(),0.0,220.0));
			temp.add(checkValue(expand.getMileage(),0.0,999999.9));
			temp.add(checkValue(expand.getNetworkStrength(),0,31));
			temp.add(checkValue(carOtherStatus.getOtherStatus(),"有效","无效"));
			temp.add(checkValue(carOtherStatus.getCarType(),"燃油车","电动车"));
			temp.add(checkValue(carOtherStatus.getChargeStatus(),"充电","未充电"));
			temp.add(checkValue(carOtherStatus.getTrunkLockStatus(),"开锁","关锁"));
			temp.add(checkValue(carOtherStatus.getLeftBackDoorLockStatus(),"开锁","关锁"));
			temp.add(checkValue(carOtherStatus.getRightBackDoorLockStatus(),"开锁","关锁"));
			temp.add(checkValue(carOtherStatus.getLeftFrontDoorLockStatus(),"开锁","关锁"));
			temp.add(checkValue(carOtherStatus.getRightFrontDoorLockStatus(),"开锁","关锁"));
			sheet.addData(temp);
		}
	}
	/**
	 * 整车信息
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 */
	private static void carInfo(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type){
		CarInfoPacket carInfo =carHistory.getCarInfoRecord();
		List temp = new ArrayList();
		if(carInfo !=null){
			//时间
			temp.add(dateTime);
			//数据类型
			temp.add(receiveTime);
			//服务器接受时间
			temp.add(type);
			//速度
			temp.add(checkValue(carInfo.getSpeed(),0.0,220.0));
			//里程
			temp.add(checkValue(carInfo.getMileage(),0.0,999999.9));
			//档位
			if(carInfo.getGears() !=null){
				if(carInfo.getGears()==0){
					temp.add("空挡");
				}else if(carInfo.getGears()<7){
					temp.add(carInfo.getGears());
				}else if(carInfo.getGears()==14){
					temp.add("自动挡");
				}else if(carInfo.getGears()==13){
					temp.add("倒挡");
				}else if (carInfo.getGears()==15){
					temp.add("P挡");
				}else{
					temp.add("N/A");
				}
				
			}else{
				temp.add("");
			}
			//加速形成踏板
			temp.add(checkValue(carInfo.getAccelTripValue()));
			//刹车形成踏板
			temp.add(checkValue(carInfo.getBrakeTripValue()));
			//充电状态 (2016-12-20国标)
			//0x01：停车充电；0x02：行驶充电；0x03：未充电状态；0x04：充电完成；“0xFE”表示异常，“0xFF”表示无效。
			if(carInfo.getChargeStatus() !=null){
				if(carInfo.getChargeStatus()==1){
					temp.add("停车充电");
				}else if(carInfo.getChargeStatus()==2){
					temp.add("行驶充电");
				}else if(carInfo.getChargeStatus()==3){
					temp.add("未充电状态");
				}else if(carInfo.getChargeStatus()==4){
					temp.add("充电完成");
				}else if(carInfo.getChargeStatus()==0xFE){
					temp.add("异常");
				}else if(carInfo.getChargeStatus()==0xFF){
					temp.add("无效");
				}else{
					temp.add("");
				}
			}else{
				temp.add("");
			}
			temp.add(checkValue(carInfo.getDriveStatus(), "有效" ,"无效"));
			temp.add(checkValue(carInfo.getBrakeStatus(), "有效" ,"无效"));
			
			//国标新增 (2016-12-20国标)
			//carStatus 车辆状态 0x01：车辆启动状态；0x02：熄火；0x03：其他状态；“0xFE”表示异常，“0xFF”表示无效。 
			if(null != carInfo.getCarStatus()){
				if(carInfo.getCarStatus()==0x01){
					temp.add("启动");
				}else if(carInfo.getCarStatus()==0x02){
					temp.add("熄火");
				}else if(carInfo.getCarStatus()==0x03){
					temp.add("其他状态");
				}else if(carInfo.getCarStatus()==0xFE){
					temp.add("异常");
				}else if(carInfo.getCarStatus()==0xFF){
					temp.add("无效");
				}
			}else{
				temp.add("");
			}
			
			//runModel	int	 运行模式 0x01: 纯电；0x02：混动；0x03：燃油；0xFE表示异常；0xFF表示无效 
			if(null != carInfo.getRunModel()){
				if(carInfo.getRunModel()==0x01){
					temp.add("纯电");
				}else if(carInfo.getRunModel()==0x02){
					temp.add("混动");
				}else if(carInfo.getRunModel()==0x03){
					temp.add("燃油");
				}else if(carInfo.getRunModel()==0xFE){
					temp.add("异常");
				}else if(carInfo.getRunModel()==0xFF){
					temp.add("无效");
				}
			}else{
				temp.add("");
			}
			//totalVoltage	double	 总电压 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。 单元：V 范围：0至1000
			temp.add(checkValue(carInfo.getTotalVoltage(),0.0,1000.0));
			//totalCurrent	double	 总电流 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效。 单元：A 范围：-1000至1000
			temp.add(checkValue(carInfo.getTotalCurrent(),-1000.0,1000.0));
			//soc	int	 soc “0xFE”表示异常，“0xFF”表示无效 范围：0至100
			temp.add(checkValue(carInfo.getSoc(),0,100));
			//dcStatus	int	 dc状态 0x01：工作；0x02：断开，“0xFE”表示异常，“0xFF”表示无
			if(null != carInfo.getDcStatus()){
				if(carInfo.getDcStatus()==0x01){
					temp.add("工作");
				}else if(carInfo.getDcStatus()==0x02){
					temp.add("断开");
				}else if(carInfo.getDcStatus()==0xFE){
					temp.add("异常");
				}else if(carInfo.getDcStatus()==0xFF){
					temp.add("无效");
				}
			}else{
				temp.add("");
			}
			//resistance	int	 绝缘电阻 单元：KΩ 范围：0至60000
			temp.add(checkValue(carInfo.getResistance(),0,60000));
			sheet.addData(temp);
		}
	}
	/**
	 * 极值信息
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 */
	private static void extreme(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type){
		ExtremumPacket extreme =carHistory.getCarExtremum();
		List temp = new ArrayList();
		if (extreme != null) {
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			//最高电压
			temp.add(checkValue(extreme.getHighestVoltageCellNo(),1,254));
			temp.add(checkValue(extreme.getHighestVoltageSingle(),0.0,15.0));
			temp.add(checkValue(extreme.getHighestVoltageCellsNo(),1,254));
			//最低电压
			temp.add(checkValue(extreme.getLowestVoltageCellsNo(),1,254));
			temp.add(checkValue(extreme.getLowestVoltageSingle(),0.0,15.0 ));
			temp.add(checkValue(extreme.getLowestVoltageCellNo(),1,254));
			//最高温度
			temp.add(checkValue(extreme.getHighestTemp(),-40,125));
			temp.add(checkValue(extreme.getHighestTempCellsNo(),1,254));
			temp.add(checkValue(extreme.getHighestTempProbeNo(),1,254));
			//最低温度
			temp.add(checkValue(extreme.getLowestTempCellsNo(),1,254));
			temp.add(checkValue(extreme.getLowestTempProbeNo(),1,254));
			temp.add(checkValue(extreme.getLowestTemp(),-40,125));
			sheet.addData(temp);
		} 
	}	
	/**
	 * 中兴
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 */
	private static List carExpandMore(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type){
		CarExpandMorePacket carExpandMore = carHistory.getCarExpandMoreRecord();
		List temp = new ArrayList();
		if (carExpandMore != null) {
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			temp.add(checkValue(carExpandMore.getMotorTorque(),-2000.0,2000.0));
			temp.add(checkValue(carExpandMore.getChargeCurrent(),0.0,500.0));
			temp.add(checkValue(carExpandMore.getChargeVoltage(),0.0,600.0));
			temp.add(checkValue(carExpandMore.getMotorAcCurrent()));
			temp.add(checkValue(carExpandMore.getMotorAcVoltage()));
			temp.add(checkValue(carExpandMore.getDcVoltage()));
			temp.add(checkValue(carExpandMore.getDcStatus(),"工作" ,"停止"));
			temp.add(checkValue(carExpandMore.getDcOutCurrent()));
			temp.add(checkValue(carExpandMore.getDcOutVoltage()));
			temp.add(checkValue(carExpandMore.getDcTemp()));
			temp.add(checkValue(carExpandMore.getCellBoxTemp()));
			temp.add(checkValue(carExpandMore.getHighOutCurrentAllow()));
			temp.add(checkValue(carExpandMore.getHighInCurrentAllow()));
			temp.add(checkValue(carExpandMore.getHighChargeVoltageAllow(),0.0,6553.4));
			temp.add(checkValue(carExpandMore.getHighChargeCurrentAllow(),0.0,6553.4));
			temp.add(checkValue(carExpandMore.getChargerOutVoltage(),0.0,6553.4));
			temp.add(checkValue(carExpandMore.getChargerOutCurrent(),0.0,6553.4));
			temp.add(checkValue(carExpandMore.getPositiveInsulationResistance(),0.0,6553.4));
			temp.add(checkValue(carExpandMore.getCathodeInsulatingResistance(),0.0,6553.4));
			temp.add(checkValue(carExpandMore.getHighDriverTorque(),-32000,33534));
			temp.add(checkValue(carExpandMore.getHighDriverSpeed(),-32000,33534));
			temp.add(checkValue(carExpandMore.getMotorTargetOutTorque(),-32000,33534));
			temp.add(checkValue(carExpandMore.getMotorTargetOutSpeed(),-32000,33534));
			temp.add(checkValue(carExpandMore.getContactorFrontVoltage(),-32000,33534));
			temp.add(checkValue(carExpandMore.getContactorBackVoltage(),-32000,33534));
			temp.add(checkValue(carExpandMore.getSoh()));
			switch (carExpandMore.getSteeringSystemFaultCode()){
				case 0:
					temp.add("正常");
					break;
				case 1:
					temp.add("自检异常");
					break;
				case 17:
					temp.add("过压（输入端）");
					break;
				case 18:
					temp.add("欠压（输入端）");
					break;
				case 19:
					temp.add("过流/短路（输出端）");
					break;
				case 20:
					temp.add("转速过高");
					break;
				case 21:
					temp.add("开路检测异常");
					break;
				case 22:
					temp.add("控制器温度过高");
					break;
				default:
					temp.add(NA);
					break;
			}
			temp.add(checkValue(carExpandMore.getWarmUpControl(),"开启" , "关闭"));
			if (carExpandMore.getCellWorkStatus() != null) {
				if (carExpandMore.getCellWorkStatus() == 0) {
					temp.add("充电完成");
				} else if (carExpandMore.getCellWorkStatus() ==1) {
					temp.add("放电");
				} else if (carExpandMore.getCellWorkStatus() ==2) {
					temp.add("充电");
				} else if (carExpandMore.getCellWorkStatus() == 3) {
					temp.add("充电故障");
				} else {
					temp.add(NA);
				}
			} else {
				temp.add("");
			}
			temp.add(checkValue(carExpandMore.getDischargeRelayStatus(), "吸合", "断开"));
			temp.add(checkValue(carExpandMore.getChargeRelayStatus(),  "吸合", "断开"));
			temp.add(checkValue(carExpandMore.getIdleMachine(), "无扭矩输出", "无效"));
			if (carExpandMore.getCanAcc() != null) {
				if (carExpandMore.getCanAcc() == 1) {
					temp.add("can控制");
				} else if (carExpandMore.getCanAcc() == 2) {
					temp.add("油门控制");
				} else {
					temp.add(NA);
				}
			} else {
				temp.add("");
			}
			temp.add(checkValue(carExpandMore.getPreChargeContactor(), "吸合" , "断开"));
			temp.add(checkValue(carExpandMore.getPwmWave(), "开pwm波", "关pwm波"));
			temp.add(checkValue(carExpandMore.getMotorSchema(),"转速控制" , "转矩控制"));
			temp.add(checkValue(carExpandMore.getMainContactorStatus(),"吸合" ,"断开"));
			sheet.addData(temp);
		}
		return temp;
	}
	
	/**
	 * 中兴
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 */
	private static void carExpandMore1(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type){
		CarExpandMorePacket carExpandMore = carHistory.getCarExpandMoreRecord();
		List temp = new ArrayList();
		if (carExpandMore != null) {
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			temp.add(checkValue(carExpandMore.getMotorTorque(),-2000.0,2000.0));
			temp.add(checkValue(carExpandMore.getChargeCurrent(),0.0,500.0));
			temp.add(checkValue(carExpandMore.getChargeVoltage(),0.0,600.0));
			temp.add(checkValue(carExpandMore.getHighChargeVoltageAllow(),0.0,6553.4));
			temp.add(checkValue(carExpandMore.getHighChargeCurrentAllow(),0.0,6553.4));
			temp.add(checkValue(carExpandMore.getChargerOutVoltage(),0.0,6553.4));
			temp.add(checkValue(carExpandMore.getChargerOutCurrent(),0.0,6553.4));
//			temp.add(checkValue(carExpandMore.getPositiveInsulationResistance(),0.0,6553.4));
//			temp.add(checkValue(carExpandMore.getCathodeInsulatingResistance(),0.0,6553.4));
//			temp.add(checkValue(carExpandMore.getHighDriverTorque(),-32000,33534));
//			temp.add(checkValue(carExpandMore.getHighDriverSpeed(),-32000,33534));
//			temp.add(checkValue(carExpandMore.getMotorTargetOutTorque(),-32000,33534));
//			temp.add(checkValue(carExpandMore.getMotorTargetOutSpeed(),-32000,33534));
//			temp.add(checkValue(carExpandMore.getContactorFrontVoltage(),-32000,33534));
//			temp.add(checkValue(carExpandMore.getContactorBackVoltage(),-32000,33534));
//			temp.add(checkValue(carExpandMore.getWarmUpControl(),"开启" , "关闭"));
//			if (carExpandMore.getCellWorkStatus() != null) {
//				if (carExpandMore.getCellWorkStatus() == 0) {
//					temp.add("充电完成");
//				} else if (carExpandMore.getCellWorkStatus() ==1) {
//					temp.add("放电");
//				} else if (carExpandMore.getCellWorkStatus() ==2) {
//					temp.add("充电");
//				} else if (carExpandMore.getCellWorkStatus() == 3) {
//					temp.add("充电故障");
//				} else {
//					temp.add(NA);
//				}
//			} else {
//				temp.add("");
//			}
//			temp.add(checkValue(carExpandMore.getDischargeRelayStatus(), "关闭", "打开"));
//			temp.add(checkValue(carExpandMore.getChargeRelayStatus(), "关闭" , "打开"));
//			temp.add(checkValue(carExpandMore.getIdleMachine(), "无扭矩输出", "无效"));
//			if (carExpandMore.getCanAcc() != null) {
//				if (carExpandMore.getCanAcc() == 1) {
//					temp.add("can控制");
//				} else if (carExpandMore.getCanAcc() == 2) {
//					temp.add("油门控制");
//				} else {
//					temp.add(NA);
//				}
//			} else {
//				temp.add("");
//			}
//			temp.add(checkValue(carExpandMore.getPreChargeContactor(), "吸合" , "断开"));
//			temp.add(checkValue(carExpandMore.getPwmWave(), "开pwm波", "关pwm波"));
//			temp.add(checkValue(carExpandMore.getMotorSchema(),"转速控制" , "转矩控制"));
//			temp.add(checkValue(carExpandMore.getMainContactorStatus(),"吸合" ,"断开"));
			temp.add(checkValue(carExpandMore.getDumpTime(),0,65534));
			sheet.addData(temp);
		}
		
	}
	/**
	 * 中兴
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 */
	private static void carExpandMore2(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type){
		CarExpandMorePacket carExpandMore = carHistory.getCarExpandMoreRecord();
		List temp = new ArrayList();
		if (carExpandMore != null) {
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
//			temp.add(checkValue(carExpandMore.getMotorTorque(),-2000.0,2000.0));
//			temp.add(checkValue(carExpandMore.getChargeCurrent(),0.0,500.0));
//			temp.add(checkValue(carExpandMore.getChargeVoltage(),0.0,600.0));
//			temp.add(checkValue(carExpandMore.getHighChargeVoltageAllow(),0.0,6553.4));
//			temp.add(checkValue(carExpandMore.getHighChargeCurrentAllow(),0.0,6553.4));
//			temp.add(checkValue(carExpandMore.getChargerOutVoltage(),0.0,6553.4));
//			temp.add(checkValue(carExpandMore.getChargerOutCurrent(),0.0,6553.4));
			temp.add(checkValue(carExpandMore.getPositiveInsulationResistance(),0.0,6553.4));
			temp.add(checkValue(carExpandMore.getCathodeInsulatingResistance(),0.0,6553.4));
			temp.add(checkValue(carExpandMore.getHighDriverTorque(),-32000,33534));
			temp.add(checkValue(carExpandMore.getHighDriverSpeed(),-32000,33534));
			temp.add(checkValue(carExpandMore.getMotorTargetOutTorque(),-32000,33534));
			temp.add(checkValue(carExpandMore.getMotorTargetOutSpeed(),-32000,33534));
			temp.add(checkValue(carExpandMore.getContactorFrontVoltage(),-32000,33534));
			temp.add(checkValue(carExpandMore.getContactorBackVoltage(),-32000,33534));
			temp.add(checkValue(carExpandMore.getSoh()));
			temp.add(checkValue(carExpandMore.getWarmUpControl(),"开启" , "关闭"));
			
			if (carExpandMore.getCellWorkStatus() != null) {
				if (carExpandMore.getCellWorkStatus() == 0) {
					temp.add("充电完成");
				} else if (carExpandMore.getCellWorkStatus() ==1) {
					temp.add("放电");
				} else if (carExpandMore.getCellWorkStatus() ==2) {
					temp.add("充电");
				} else if (carExpandMore.getCellWorkStatus() == 3) {
					temp.add("充电故障");
				} else {
					temp.add(NA);
				}
			} else {
				temp.add("");
			}
			temp.add(checkValue(carExpandMore.getDischargeRelayStatus(), "关闭", "开启"));
			temp.add(checkValue(carExpandMore.getChargeRelayStatus(), "关闭" , "开启"));
			temp.add(checkValue(carExpandMore.getIdleMachine(), "无扭矩输出", "无效"));
			temp.add(checkValue(carExpandMore.getCanAcc(), "can控制", "油门控制"));
			temp.add(checkValue(carExpandMore.getPreChargeContactor(), "吸合" , "断开"));
			temp.add(checkValue(carExpandMore.getPwmWave(), "开pwm波", "关pwm波"));
			temp.add(checkValue(carExpandMore.getMotorSchema(),"转速控制" , "转矩控制"));
			temp.add(checkValue(carExpandMore.getMainContactorStatus(),"吸合" ,"断开"));
			sheet.addData(temp);
		}
		
	}
	/**
	 * 电池温度
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 */
	private static void cellTemp(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type){
		CarCellTempPacket cellTemp =carHistory.getCarCellTemp();
		if (cellTemp != null) {
			List<CarCellTempSinglePacket> children = cellTemp.getChildren();
			for(int i=0;i<children.size();i++){
				List temp = new ArrayList();
				if(i==0){
					temp.add(dateTime);
					temp.add(receiveTime);
					temp.add(type);
					temp.add(checkValue(cellTemp.getTempProbeNum()));
					temp.add(checkValue(cellTemp.getCellsNum()));
				}else{
					addEmpty(temp, 5);
				}
				temp.add(checkValue(children.get(i).getCellsNo()));
				temp.add(checkValue(children.get(i).getTempProbeNum()));
				temp.add(checkValue(children.get(i).getTempList()));
				sheet.addData(temp);
			}
		}
	}
	/**
	 * 电池电压
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 */
	private static void cellVoltage(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type){
		CarCellVoltagePacket cellVoltage = carHistory.getCarCellVoltage();
		if (cellVoltage != null) {
			List<CarCellVoltageSinglePacket> children = cellVoltage.getChildren();
			for(int i=0;i<children.size();i++){
				List temp = new ArrayList();
				if(i==0){
					temp.add(dateTime);
					temp.add(receiveTime);
					temp.add(type);
					temp.add(checkValue(cellVoltage.getSingleCellNum()));
					temp.add(checkValue(cellVoltage.getCellsNum()));
				}else{
					addEmpty(temp, 5);
				}
				temp.add(checkValue(children.get(i).getCellsNo()));
				temp.add(checkValue(children.get(i).getCellNum()));
				temp.add(checkValue(children.get(i).getCellVoltageList()));
				sheet.addData(temp);
			}
		} 
	}
	/**
	 * 华冠扩展
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 */
	private static void carHGExpand(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type){
		CarHGExpandPacket carHGExpand = carHistory.getCarHGExpandRecord();
		if (carHGExpand != null) {
			List temp = new ArrayList();
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			temp.add(checkValue(carHGExpand.getOrderChargeVoltage(),0.0,1200.0));
			temp.add(checkValue(carHGExpand.getDumpTime(),0,65535));
			temp.add(checkValue(carHGExpand.getRange(),0.0,600.0));
			temp.add(checkValue(carHGExpand.getCellWaterTemp(),-50,+200));
			switch (carHGExpand.getFrontMotorFaultLevel()){
			case 0:
				temp.add("无故障");
				break;
			case 1:
				temp.add("警告");
				break;
			case 2:
				temp.add("故障");
				break;
			case 3:
				temp.add("比较严重故障");
				break;
			case 4:
				temp.add("严重故障");
				break;
			default:
				temp.add(NA);
				break;
		}
			temp.add(checkValue(carHGExpand.getFrontMotorFaultCode(),0,65535));
			temp.add(checkValue(carHGExpand.getFrontMotorHFaultCodes()));
			switch (carHGExpand.getBackMotorFaultLevel()){
			case 0:
				temp.add("无故障");
				break;
			case 1:
				temp.add("警告");
				break;
			case 2:
				temp.add("故障");
				break;
			case 3:
				temp.add("比较严重故障");
				break;
			case 4:
				temp.add("严重故障");
				break;
			default:
				temp.add(NA);
				break;
		}
			temp.add(checkValue(carHGExpand.getBackMotorFaultCode(),0,65535));
			temp.add(checkValue(carHGExpand.getBackMotorHFaultCodes()));
			temp.add(checkValue(carHGExpand.getBMSChargeFaultLevel()));
			temp.add(checkValue(carHGExpand.getBMSChargeFaultCode(),0,65535));
			if(carHGExpand.getOtherStatus() !=null){
				CarOtherStatusPacket carOtherStatus=carHGExpand.getOtherStatus();
				temp.add(checkValue(carOtherStatus.getStatus(),"无效","有效"));
				temp.add(checkValue(carOtherStatus.getpGearsStatus(),"f否","是"));
				temp.add(checkValue(carOtherStatus.getChargeLicence(),"许可","不许可"));
				temp.add(checkValue(carOtherStatus.getThermalManageLicence(),"许可","不许可"));
				temp.add(checkValue(carOtherStatus.getChargeStatus(),"许可","不许可"));
				temp.add(checkValue(carOtherStatus.getChargeGunStatus(),"插入","未插入"));
				temp.add(checkValue(carOtherStatus.getRapidSwtichStatus(),"急停开始","急停关闭"));
				temp.add(checkValue(carOtherStatus.getWarnLightStatus(),"开启","关闭"));
				temp.add(checkValue(carOtherStatus.getLowBeamLightStatus(),"开启","关闭"));
			}else{
				addEmpty(temp, 9);
			}
			sheet.addData(temp);
		} 
	}
	/**
	 * 动力蓄电池故障
	 * @param sheet
	 * @param carWarn
	 * @param dateTime
	 */
	private static void powerCellWarn(ExcelSheet sheet,CarWarnPacket carWarn ,String dateTime,String receiveTime,String type){
		PowerCellWarnPacket powerCellWarn = carWarn.getPowerCellWarn();
		if(powerCellWarn!=null){
			List temp = new ArrayList();
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			temp.add(checkValue(powerCellWarn.getWarnTempDiff(),"报警" , "正常"));
			temp.add(checkValue(powerCellWarn.getWarnHighTemp(),"报警" , "正常"));
			temp.add(checkValue(powerCellWarn.getWarnCellsHighVoltage(),"报警" , "正常"));
			temp.add(checkValue(powerCellWarn.getWarnCellsLowVoltage(),"报警" , "正常"));
			temp.add(checkValue(powerCellWarn.getWarnSocLow(),"报警" , "正常"));
			temp.add(checkValue(powerCellWarn.getWarnCellHighVoltage() ,"报警" , "正常"));
			temp.add(checkValue(powerCellWarn.getWarnCellLowVoltage(),"报警" , "正常"));
			temp.add(checkValue(powerCellWarn.getWarnSocLower(),"报警" , "正常"));
			temp.add(checkValue(powerCellWarn.getWarnSocHigher(),"报警" , "正常"));
			temp.add(checkValue(powerCellWarn.getWarnCellsNoMatch(),"报警" , "正常"));
			temp.add(checkValue(powerCellWarn.getWarnCellsLowMatch(),"报警" , "正常"));
			temp.add(checkValue(powerCellWarn.getWarnInsulation(),"报警" , "正常"));
			sheet.addData(temp);
		}
	}
	/**
	 * 
	 * @param sheet
	 * @param carWarn
	 * @param dateTime
	 */
	private static void cellWarn(ExcelSheet sheet,CarWarnPacket carWarn ,String dateTime,String receiveTime,String type){
		CellWarnPacket cellWarn = carWarn.getCellWarn();
		if(cellWarn!=null){
			List temp = new ArrayList();
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			if (cellWarn.getWarnFlag() != null) {
				if (cellWarn.getWarnFlag()==0) {
					temp.add("正常");
				} else if (cellWarn.getWarnFlag()==1) {
					temp.add("一级");
				} else if (cellWarn.getWarnFlag()==2) {
					temp.add("二级");
				} else {
					temp.add(NA);
				}
			} else {
				temp.add("");
			}
			if (cellWarn.getWarnCellSystem()!= null) {
				if (cellWarn.getWarnCellSystem()==0) {
					temp.add("正常");
				} else if (cellWarn.getWarnCellSystem()==1) {
					temp.add("一级");
				} else if (cellWarn.getWarnCellSystem()==2) {
					temp.add("二级");
				} else {
					temp.add(NA);
				}
			} else {
				temp.add("");
			}
			temp.add(checkValue(cellWarn.getCellTotalVoltageHigher(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getCellTotalVoltageLower(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getInsulationLow(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getSingleVoltageLower(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getSingleVoltageHigher(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getTempLower(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getTempHigher() ,"异常" , "正常"));
			temp.add(checkValue(cellWarn.getPlugInFlower(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getOutCurrentHigher(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getSingleVoltageDiffHigher(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getTempDiffHigher(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getCellTotalVoltageHigh(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getCellTotalVoltageLow(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getInsulationLower() ,"异常" , "正常"));
			temp.add(checkValue(cellWarn.getTempLow() ,"异常" , "正常"));
			temp.add(checkValue(cellWarn.getTempHigh() ,"异常" , "正常"));
			temp.add(checkValue(cellWarn.getPlugInFlow(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getOutCurrentHigh(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getSingleVoltageDiffHigh(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getNetworkInside(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getVoltageCollect(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getCurrentCollect(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getSingleInOver(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getSingleOutOver(),"异常" , "正常"));
			temp.add(checkValue(cellWarn.getTempUpFast() ,"异常" , "正常"));
			temp.add(checkValue(cellWarn.getShortOutProtect(),"异常" , "正常"));
			sheet.addData(temp);
		}
		
	}
	/**
	 * 
	 * @param sheet
	 * @param carWarn
	 * @param dateTime
	 */
	private static void carInfoWarn(ExcelSheet sheet,CarWarnPacket carWarn ,String dateTime,String receiveTime,String type){
		CarInfoWarnPacket carInfoWarn = carWarn.getCarInfoWarn();
		if(carInfoWarn!=null){
			List temp = new ArrayList();
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			temp.add(checkValue(carInfoWarn.getPowerCellStatus(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getMotorHot(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getDcStatus(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getCarStatus(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getPowerCellCutLight(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getRunReadyStatus(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getGearsStatus(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getAcceleratorStatus(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getMotorHoter(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getPreChargeStatus(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getMainContactorStatus(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getIgbtStatus(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getMotorFlow(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getMotorVoltageHigher(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getMotorVoltageLower(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getMotorSpeedOver(),"异常" , "正常"));
			temp.add(checkValue(carInfoWarn.getMotorStatus(),"异常" , "正常"));
			sheet.addData(temp);
		}
	
	}

	/**
	 * 
	 * @param sheet
	 * @param carWarn
	 * @param dateTime
	 */
	private static void chargerWarn(ExcelSheet sheet,CarWarnPacket carWarn ,String dateTime,String receiveTime,String type){
		ChargerWarnPacket chargerWarn = carWarn.getChargerWarn();
		if(chargerWarn!=null){
			List temp = new ArrayList();
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			temp.add(checkValue(chargerWarn.getHardwareStatus(),"异常" , "正常"));
			temp.add(checkValue(chargerWarn.getCommunicationOvertime(),"异常" , "正常"));
			temp.add(checkValue(chargerWarn.getChargerTempHighWarn(),"异常" , "正常"));
			temp.add(checkValue(chargerWarn.getChargerTempHighProtect(),"异常" , "正常"));
			temp.add(checkValue(chargerWarn.getInVoltageStatus(),"异常" , "正常"));
			temp.add(checkValue(chargerWarn.getOverFlow(),"异常" , "正常"));
			temp.add(checkValue(chargerWarn.getLeakage(),"异常" , "正常"));
			temp.add(checkValue(chargerWarn.getCellElectrodeReverse(),"异常" , "正常"));
			sheet.addData(temp);
		}
	
	}
	
	
	/**
	 * 
	 * @param sheet
	 * @param carWarn
	 * @param dateTime
	 */
	private static void motorWarn(ExcelSheet sheet,CarWarnPacket carWarn ,String dateTime,String receiveTime,String type){
		MotorWarnPacket motorWarn = carWarn.getMotorWarn();
		if(motorWarn!=null){
			List temp = new ArrayList();
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			temp.add(checkValue(motorWarn.getOverCurrentSpeedUp(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getOverCurrentSpeedDown(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getOverCurrentSpeedConstant() ,"异常" , "正常"));
			temp.add(checkValue(motorWarn.getOverVoltageSpeedUp(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getOverVoltageSpeedDown(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getOverVoltageSpeedConstant() ,"异常" , "正常"));
			temp.add(checkValue(motorWarn.getLowVoltageStatus(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getDriverOverload(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getMotorOverload(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getOutputPhase(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getDriverHot(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getDriverHotPre(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getEepormRWStatus(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getMotorGroundShortOutStatus(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getFastLimitCurrent(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getMotorSpeedOver(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getSpeedDiffHigh(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getMotorHotPre(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getCanInitStatus(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getCanCommunicationStatus(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getEncoderStatus(),"异常" , "正常"));
			temp.add(checkValue(motorWarn.getCurrentCollect(),"异常" , "正常"));
			sheet.addData(temp);
		}
	
	}
	
	/**
	 * 
	 * @param sheet
	 * @param carWarn
	 * @param dateTime
	 */
	private static void dcWarn(ExcelSheet sheet,CarWarnPacket carWarn ,String dateTime,String receiveTime,String type){
		DCWarnPacket dcWarn = carWarn.getDcWarn();
		if(dcWarn!=null){
			List temp = new ArrayList();
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			temp.add(checkValue(dcWarn.getDcTempStatus(),"异常" , "正常"));
			temp.add(checkValue(dcWarn.getDcOutFlowHigh(),"异常" , "正常"));
			temp.add(checkValue(dcWarn.getDcOutVoltageHigh(),"异常" , "正常"));
			temp.add(checkValue(dcWarn.getDcOutVoltageLow(),"异常" , "正常"));
			temp.add(checkValue(dcWarn.getDcInVoltageHigh(),"异常" , "正常"));
			temp.add(checkValue(dcWarn.getDcInVoltageLow(),"异常" , "正常"));
			temp.add(checkValue(dcWarn.getDcOutShortOutStatus(),"异常" , "正常"));
			temp.add(checkValue(dcWarn.getDcHardwareStatus(),"异常" , "正常"));
			temp.add(checkValue(dcWarn.getCommunicationOvertime(),"异常" , "正常"));
			sheet.addData(temp);
		}
	
	}
	/**
	 * 蓝海新能告警
	 * @param sheet
	 * @param carWarn
	 * @param dateTime
	 */
	@SuppressWarnings("rawtypes")
	private static void lhxnWarn(ExcelSheet sheet,CarWarnPacket carWarn ,String dateTime,String receiveTime,String type){
		LHXNWarnPacket lhxnWarn = carWarn.getLhxnWarn();
		if(lhxnWarn!=null){
			List temp = new ArrayList();
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			temp.add(checkValue(lhxnWarn.getLowVoltagePowerLow(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getLowVoltagePowerHigh(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getMotorSysCheck(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getMotorRotary(),"过流" , "正常"));
			temp.add(checkValue(lhxnWarn.getMotorControlOverFlow(),"过压" , "正常"));
			temp.add(checkValue(lhxnWarn.getMotorControlOverVoltage(),"欠压" , "正常"));
			temp.add(checkValue(lhxnWarn.getMotorControlLowVoltage(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getVacuumWarn(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getSysStatus(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getEps(),"正常" , "一路失效","两路失效","两路不回位"));
			temp.add(checkValue(lhxnWarn.getAccelTrip(),"正常" , "丢帧","掉线","not used"));
			temp.add(checkValue(lhxnWarn.getMotorControl(),"正常" , "丢帧","掉线","not used"));
			temp.add(checkValue(lhxnWarn.getCellSys(),"正常" , "丢帧","掉线","not used"));
			temp.add(checkValue(lhxnWarn.getTcu(),"正常" , "丢帧","掉线","not used"));
			temp.add(checkValue(lhxnWarn.getFloatPlug(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getMotorEnableFeedback(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getMasterContactorFeedback(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getTorqueFeedback(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getAbs(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getBms(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getHighVoltageControl(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getVcu(),"异常" , "正常"));
			temp.add(checkValue(lhxnWarn.getRepair(),"异常" , "正常"));
			sheet.addData(temp);
		}
	
	}
	/**
	 * 蓝海新能扩展
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 * @throws Exception
	 */
	private static void lhxnExpand(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type) throws Exception{
		CarLHXNExpandPacket LHXNExpand = carHistory.getCarLHXNExpandRecord();
		if (LHXNExpand != null) {
			List temp = new ArrayList();
			temp.add(checkValue(LHXNExpand.getSimStatus(),"未连接" , "已连接"));
			temp.add(checkValue(LHXNExpand.getTfStatus(),"未连接" , "已连接"));
			temp.add(checkValue(LHXNExpand.getGsmStatus(),"未连接" , "已连接"));
			temp.add(checkValue(LHXNExpand.getGpsStatus(),"未连接" , "已连接"));
			temp.add(checkValue(LHXNExpand.getSimStatus(),"无效","正常工作模式","跛行模式","待机模式"));
			temp.add(checkValue(LHXNExpand.getTboxVersion()));
			temp.add(checkValue(LHXNExpand.getTorqueFeedback(),-300.0 , 300.0));
			temp.add(checkValue(LHXNExpand.getMotorControlVersion()));
			temp.add(checkValue(LHXNExpand.getTorqueCommand(),-300.0 , 300.0));
			temp.add(checkValue(LHXNExpand.getVcuVersion()));
			temp.add(checkValue(LHXNExpand.getVacuum(),-3000.0,3000.0));
			temp.add(checkValue(LHXNExpand.getPtcTemp(),-100,155));
			temp.add(checkValue(LHXNExpand.getSingleMileage(),0.0,6000.0));
			temp.add(checkValue(LHXNExpand.getChargeDumpTime(),0.0,25.5));
			temp.add(checkValue(LHXNExpand.getCellsMileage(),0.0,1677721.5));
			temp.add(checkValue(LHXNExpand.getCellsAvgTemp(),-100,155));
			temp.add(checkValue(LHXNExpand.getBmsVersion()));
			temp.add(checkValue(LHXNExpand.getCellsChargeTimes()));
			temp.add(checkValue(LHXNExpand.getSingleVoltageDiff(),0.0000,6.5535));
			temp.add(checkValue(LHXNExpand.getDumpMileage(),0,250));
			temp.add(checkValue(LHXNExpand.getCellMaxVolume()));
			temp.add(checkValue(LHXNExpand.getCellProducerNo()));
			temp.add(checkValue(LHXNExpand.getCellsNo()));
			temp.add(checkValue(LHXNExpand.getAllowOutVoltageHigh(),0.00,655.35));
			temp.add(checkValue(LHXNExpand.getAllowOutCurrentHigh(),0.00,655.35));
			temp.add(checkValue(LHXNExpand.getSlowChargeControlModel(),"默认值","电加热模式","充电模式",""));
			temp.add(checkValue(LHXNExpand.getSlowChargeControlStatus(),"启动","关闭输出"));
			temp.add(checkValue(LHXNExpand.getFastChargeControl(),"启动","关闭输出"));
			temp.add(checkValue(LHXNExpand.getAllowChargeVoltageHigh(),0.00,655.35));
			temp.add(checkValue(LHXNExpand.getAllowChargeCurrentHigh(),0.00,655.35));
			temp.add(checkValue(LHXNExpand.getCellsStandardAH()));
			temp.add(checkValue(LHXNExpand.getCellsRealAH()));
			temp.add(checkValue(LHXNExpand.getCellsNum()));
			temp.add(checkValue(LHXNExpand.getSingleProtectVoltageLow(),0.000,65.535));
			temp.add(checkValue(LHXNExpand.getFanStatus(),"开启","关闭"));
			temp.add(checkValue(LHXNExpand.getHeaterStatus(),"断开","闭合"));
			temp.add(checkValue(LHXNExpand.getRepairSwitch(),"未连接","连接"));
			temp.add(checkValue(LHXNExpand.getMinusAssistSignal(),"有效","无效"));
			temp.add(checkValue(LHXNExpand.getPlusAssistSignal(),"有效","无效"));
			temp.add(checkValue(LHXNExpand.getPlusChargeContactor(),"有效","无效"));
			temp.add(checkValue(LHXNExpand.getMinusContactor(),"有效","无效"));
			temp.add(checkValue(LHXNExpand.getPlusContactor(),"有效","无效"));
			temp.add(checkValue(LHXNExpand.getVoltageThermalAssistSignal(),"有效","无效"));
			temp.add(checkValue(LHXNExpand.getFastChargeContactor(),"闭合","断开"));
			temp.add(checkValue(LHXNExpand.getFastChargeAssistSignal(),"有效","无效"));
			temp.add(checkValue(LHXNExpand.getBmsProducer(),"预留","亿能"));
			sheet.addData(temp);
		} 		
	}
	/**
	 * 2016-12-20新增 驱动电机信息
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 */
	private static void driveMotorRecord(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type){
		DriveMotorPacket driveMotorPacket = carHistory.getDriveMotorRecord();
		if (driveMotorPacket != null) {
			List<DriveMotorInfoPacket> children = driveMotorPacket.getInfos();
			for(int i=0;i<children.size();i++){
				List temp = new ArrayList();
				if(i==0){
					temp.add(dateTime);
					temp.add(receiveTime);
					temp.add(type);
					temp.add(checkValue(driveMotorPacket.getNum())); 
				}else{
					addEmpty(temp, 4);
				}
				temp.add(checkValue(children.get(i).getMotorNo(),1,253));
				// 驱动电机状态 0x01：耗电；0x02：发电；0x03：关闭状态；0x04：准备状态“0xFE”表示异常，“0xFF”表示无效。 范围：0至65531
				if(null!=children.get(i).getMotorStatus()){
					if(children.get(i).getMotorStatus()==1){
						temp.add("耗电");
					}else if(children.get(i).getMotorStatus()==2){
						temp.add("发电");
					}else if(children.get(i).getMotorStatus()==3){
						temp.add("关闭");
					}else if(children.get(i).getMotorStatus()==4){
						temp.add("准备");
					}else if(children.get(i).getMotorStatus()==0xFE){
						temp.add("异常");
					}else if(children.get(i).getMotorStatus()==0xFF){
						temp.add("无效");
					}
				}else{
					temp.add("");
				}
				//驱动电机控制器温度 “0xFE”表示异常，“0xFF”表示无效 单元：℃ 范围：-40至210
				if(null!=children.get(i).getMotorControlTemp()){
					if(children.get(i).getMotorControlTemp()==0xFE){
						temp.add("异常");
					}else if(children.get(i).getMotorControlTemp()==0xFF){
						temp.add("无效");
					}else{
						temp.add(checkValue(children.get(i).getMotorControlTemp(),-40,210));
					}
				}else{
					temp.add("");
				}
				//驱动电机转速 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：rmin 范围：-20000至45531
				if(null!=children.get(i).getMotorSpeed()){
					if(children.get(i).getMotorSpeed()==0xFE){
						temp.add("异常");
					}else if(children.get(i).getMotorSpeed()==0xFF){
						temp.add("无效");
					}else{
						temp.add(checkValue(children.get(i).getMotorSpeed(),-20000,45531));
					}
				}else{
					temp.add("");
				}
				//驱动电机转矩 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：Nm 范围：-2000至4553.1
				if(null!=children.get(i).getMotorTorque()){
					if(children.get(i).getMotorTorque()==0xFE){
						temp.add("异常");
					}else if(children.get(i).getMotorTorque()==0xFF){
						temp.add("无效");
					}else{
						temp.add(checkValue(children.get(i).getMotorTorque(),-2000.0,4553.1));
					}
				}else{
					temp.add("");
				}
				//驱动电机温度 “0xFE”表示异常，“0xFF”表示无效 单元：℃ 范围：-40至210
				if(null!=children.get(i).getMotorTemp()){
					if(children.get(i).getMotorTemp()==0xFE){
						temp.add("异常");
					}else if(children.get(i).getMotorTemp()==0xFF){
						temp.add("无效");
					}else{
						temp.add(checkValue(children.get(i).getMotorTemp(),-40,210));
					}
				}else{
					temp.add("");
				}
				//电机控制器输入电压 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：V 范围：0至6000
				if(null!=children.get(i).getMotorControlVoltage()){
					if(children.get(i).getMotorControlVoltage()==0xFE){
						temp.add("异常");
					}else if(children.get(i).getMotorControlVoltage()==0xFF){
						temp.add("无效");
					}else{
						temp.add(checkValue(children.get(i).getMotorControlVoltage(),0.0,6000.0));
					}
				}else{
					temp.add("");
				}
				//电机控制器直流母线电流 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：A 范围：-1000至1000
				if(null!=children.get(i).getMotorControlCurrent()){
					if(children.get(i).getMotorControlCurrent()==0xFE){
						temp.add("异常");
					}else if(children.get(i).getMotorControlCurrent()==0xFF){
						temp.add("无效");
					}else{
						temp.add(checkValue(children.get(i).getMotorControlCurrent(),-1000.0,1000.0));
					}
				}else{
					temp.add("");
				}
				sheet.addData(temp);
			}
		} 
	}
	/**
	 * 2016-12-20 燃料电池信息
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 * @throws Exception
	 */
	private static void fuelCellsRecord(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type) throws Exception{
		FuelCellsPacket fuelCellsPacket = carHistory.getFuelCellsRecord();
		if (null != fuelCellsPacket) {
			List temp = new ArrayList();
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			temp.add(checkValue(fuelCellsPacket.getVoltage(),0.0,2000.0));
			temp.add(checkValue(fuelCellsPacket.getCurrent(),0.0,2000.0));
			temp.add(checkValue(fuelCellsPacket.getFuelUse(),0.0,600.0));
			temp.add(checkValue(fuelCellsPacket.getTempProbeNum(),0,65531));
			temp.add(checkValue(fuelCellsPacket.getProbeTempList()));
			temp.add(checkValue(fuelCellsPacket.getHyHighestTemp(),-40.0,200.0));
			temp.add(checkValue(fuelCellsPacket.getHyHighestTempProbeNo()));
			temp.add(checkValue(fuelCellsPacket.getHyHighestThickness(),0,60000));
			temp.add(checkValue(fuelCellsPacket.getHyHighestThicknessNo()));
			temp.add(checkValue(fuelCellsPacket.getHyHighestPressure(),0.0,100.0));
			temp.add(checkValue(fuelCellsPacket.getHyHighestPressureNo()));
			if(null!=fuelCellsPacket.getDcStatus()){
				if(fuelCellsPacket.getDcStatus()==0){
					temp.add("工作");
				}else if(fuelCellsPacket.getDcStatus()==1){
					temp.add("断开");
				}
			}else{
				temp.add("");
			}
			sheet.addData(temp);
		}
	}
	
	private static void motorPacket(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type) throws Exception{
		MotorPacket motorPacket = carHistory.getMotorPacket();
		if (null != motorPacket) {
			List temp = new ArrayList();
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			//motorStatus	int	 发动机状态 0x01：启动状态；0x02：关闭状态，“0xFE”表示异常，“0xFF”表示无效 
			if(null!=motorPacket.getMotorStatus()){
				if(motorPacket.getMotorStatus()==1){
					temp.add("启动");
				}else if(motorPacket.getMotorStatus()==2){
					temp.add("关闭");
				}else if(motorPacket.getMotorStatus()==0xFE){
					temp.add("异常");
				}else if(motorPacket.getMotorStatus()==0xFF){
					temp.add("无效");
				}
			}else{
				temp.add("");
			}
			//crankshaftSpeed	int	 曲轴转速 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：rpm 范围：0至60000
			if(null!=motorPacket.getCrankshaftSpeed()){
				 if(motorPacket.getCrankshaftSpeed()==0xFE){
					temp.add("异常");
				}else if(motorPacket.getCrankshaftSpeed()==0xFF){
					temp.add("无效");
				}else{
					temp.add(checkValue(motorPacket.getCrankshaftSpeed(),0,60000));
				}
			}else{
				temp.add("");
			}
			//fuelUse	double	 燃料消耗率 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：L100km 范围：0至600
			if(null!=motorPacket.getFuelUse()){
				 if(motorPacket.getFuelUse()==0xFE){
					temp.add("异常");
				}else if(motorPacket.getFuelUse()==0xFF){
					temp.add("无效");
				}else{
					temp.add(checkValue(motorPacket.getFuelUse(),0.0,600.0));
				}
			}else{
				temp.add("");
			}
			sheet.addData(temp);
		}
	}
	
	/**
	 * 2016-12-20新增
	 * @param sheet
	 * @param carWarn
	 * @param dateTime
	 */
	private static void carWarnCommon(ExcelSheet sheet,CarWarnPacket carWarn ,String dateTime,String receiveTime,String type){
		CarWarnCommonPacket carWarnCommon = carWarn.getCommonWarn();
		if(carWarnCommon!=null){
			List temp = new ArrayList();
			temp.add(dateTime);
			temp.add(receiveTime);
			temp.add(type);
			temp.add(carWarn.getWarnLevel());
			
			if (carWarnCommon.getTempDiff() != null) {
				if (carWarnCommon.getTempDiff()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getTempDiff()==1) {
					temp.add("温度差异报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getHighTemp() != null) {
				if (carWarnCommon.getHighTemp()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getHighTemp()==1) {
					temp.add("电池差异报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getEnergyDeviceOverVoltage() != null) {
				if (carWarnCommon.getEnergyDeviceOverVoltage()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getEnergyDeviceOverVoltage()==1) {
					temp.add("车载储能装置类型过压报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getEnergyDeviceUnderVoltage() != null) {
				if (carWarnCommon.getEnergyDeviceUnderVoltage()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getEnergyDeviceUnderVoltage()==1) {
					temp.add("车载储能装置类型欠压报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getSocLow() != null) {
				if (carWarnCommon.getSocLow()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getSocLow()==1) {
					temp.add("SOC低报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getSingleCellOverVoltage() != null) {
				if (carWarnCommon.getSingleCellOverVoltage()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getSingleCellOverVoltage()==1) {
					temp.add("单体电池过压报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getSingleCellUnderVoltage() != null) {
				if (carWarnCommon.getSingleCellUnderVoltage()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getSingleCellUnderVoltage()==1) {
					temp.add("单体电池欠压报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getSocHigh() != null) {
				if (carWarnCommon.getSocHigh()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getSocHigh()==1) {
					temp.add("SOC过高报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getSocHop() != null) {
				if (carWarnCommon.getSocHop()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getSocHop()==1) {
					temp.add("SOC跳变报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getEnergyDeviceNoMatch() != null) {
				if (carWarnCommon.getEnergyDeviceNoMatch()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getEnergyDeviceNoMatch()==1) {
					temp.add("可充电储能系统不匹配报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getSingleCellLowMatch() != null) {
				if (carWarnCommon.getSingleCellLowMatch()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getSingleCellLowMatch()==1) {
					temp.add("电池单体一致性差报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getInsulation() != null) {
				if (carWarnCommon.getInsulation()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getInsulation()==1) {
					temp.add("绝缘报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getDcTemp() != null) {
				if (carWarnCommon.getDcTemp()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getDcTemp()==1) {
					temp.add("DC-DC温度报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getBrakeSys() != null) {
				if (carWarnCommon.getBrakeSys()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getBrakeSys()==1) {
					temp.add("D制动系统报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getDcStatus() != null) {
				if (carWarnCommon.getDcStatus()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getDcStatus()==1) {
					temp.add("DC-DC状态报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getMotorControlTemp() != null) {
				if (carWarnCommon.getDcStatus()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getMotorControlTemp()==1) {
					temp.add("驱动电机控制器温度报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getHighVoltageEachLock() != null) {
				if (carWarnCommon.getHighVoltageEachLock()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getHighVoltageEachLock()==1) {
					temp.add("高压互锁状态报警");
				} 
			} else {
				temp.add("");
			}
			if (carWarnCommon.getMotorTemp() != null) {
				if (carWarnCommon.getMotorTemp()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getMotorTemp()==1) {
					temp.add("驱动电机温度报警");
				} 
			} else {
				temp.add("");
			}
			
			if (carWarnCommon.getEnergyDeviceOverCharge() != null) {
				if (carWarnCommon.getEnergyDeviceOverCharge()==0) {
					temp.add("正常");
				} else if (carWarnCommon.getEnergyDeviceOverCharge()==1) {
					temp.add("车载储能装置类型过充");
				} 
			} else {
				temp.add("");
			}
			sheet.addData(temp);
		}
	}
	/**
	 * 2016-12-21新增 可充电储能装置电压数据
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 * @throws Exception
	 */
	private static void chargeDeviceVoltage(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type) throws Exception{
		ChargeDeviceVoltagePacket charge=carHistory.getChargeDeviceVoltage();
		if (null != charge) {
			List<SingleChargeDeviceVoltagePacket> children = charge.getVoltageInfos();
			for(int i=0;i<children.size();i++){
				List temp = new ArrayList();
				if(i==0){
					temp.add(dateTime);
					temp.add(receiveTime);
					temp.add(type);
					temp.add(checkValue(charge.getSysNum()));
				}else{
					addEmpty(temp, 4);
				}
				temp.add(children.get(i).getSysNo());
				temp.add(children.get(i).getVoltage());
				temp.add(children.get(i).getCurrent());
				temp.add(children.get(i).getAllCellsNum());
				temp.add(children.get(i).getCellsNo());
				temp.add(children.get(i).getCellsNum());
				temp.add(children.get(i).getVoltages());
			sheet.addData(temp);
		}
	  }
	}
	/**
	 * 2016-12-21 新增 可充电储能装置温度数据
	 * @param sheet
	 * @param carHistory
	 * @param dateTime
	 * @throws Exception
	 */
	private static void chargeDeviceTemp(ExcelSheet sheet,CarInfoHistoryMongo carHistory,String dateTime,String receiveTime,String type) throws Exception{
		ChargeDeviceTempPacket tempPack=carHistory.getChargeDeviceTemp();
		if (null != tempPack) {
			List<SingleChargeDeviceTempPacket> children = tempPack.getTempInfos();
			for(int i=0;i<children.size();i++){
				List temp = new ArrayList();
				if(i==0){
					temp.add(dateTime);
					temp.add(receiveTime);
					temp.add(type);
					temp.add(checkValue(tempPack.getSysNum()));
				}else{
					addEmpty(temp, 4);
				}
				temp.add(children.get(i).getSysNo());
				temp.add(children.get(i).getProbeNum());
				temp.add(children.get(i).getTemps());
			sheet.addData(temp);
		}
	  }
	}
}
