package com.cm.mongo.entity;

import java.io.Serializable;
import java.util.Date;

import org.mongodb.morphia.annotations.Id;

import com.cm.tbox.packet.CarMSDataItemPacket;

public class CarMSDataMongo implements Serializable{
	private static final long serialVersionUID = -4173457972374581970L;
	/**vin*/
	@Id
	private long timestamp;
	/**车辆唯一识别码*/
	private String carId;
	/**vin*/
	private String vin;
	/**终端code*/
	private String code;
	/**最后更新时间*/
	private Date updateTime;
	/** 车速 单元：km/h 范围：0至220*/
	private CarMSDataItemPacket speed; 
	/** 转矩 单元：Nm 范围：-300至300*/
	private CarMSDataItemPacket torque; 
	/** 电池总电压 单元：V 范围：0至1000*/
	private CarMSDataItemPacket totalVoltage; 
	/** 单节最高电压 单元：V 范围：0至15*/
	private CarMSDataItemPacket singleVoltageHighest; 
	/** 单节最低电压 单元：V 范围：0至15*/
	private CarMSDataItemPacket singleVoltageLowest; 
	/** 单节电压差值 单元：V 范围：0至6.5535*/
	private CarMSDataItemPacket singleVoltageDiff; 
	/** 制动信号状态 0-无；1-有 */
	private CarMSDataItemPacket brakeSignal; 
	/** 充电机控制字 0-充电机启动 1-充电机关闭输出。 */
	private CarMSDataItemPacket chargeMotorStatus; 
	/** 一级故障标志位 0-正常；1-一级故障2-二级故障；3-预留 */
	private CarMSDataItemPacket wanrFlag; 
	/** 电机或电机控制器温度状态 0-正常；1-偏高；2-过高；3-预留 */
	private CarMSDataItemPacket motorControlTemp; 
	/** 电机超速 0-正常；1-故障 */
	private CarMSDataItemPacket motorSpeedOver; 
	/** IGBT 状态 0-正常；1-故障 */
	private CarMSDataItemPacket igbtStatus; 
	/** 电机系统自检故障状态 0-正常；1-故障 */
	private CarMSDataItemPacket motorCheckSelfStatus; 
	/** 电机旋变故障 0-正常；1-故障 */
	private CarMSDataItemPacket motorResolver; 
	/** 加速踏板故障状态 0-正常；1-一路失效；2-两路失效；3-两路不回应 */
	private CarMSDataItemPacket accelTrip; 
	/** 电机控制器通信故障 0-正常；1-丢帧；2-掉线；3-预留； */
	private CarMSDataItemPacket motorControl; 
	/** 电池系统通信故障 0-正常；1-丢帧；2-掉线；3-预留； */
	private CarMSDataItemPacket cellSys; 
	/** TCU 通信故障 0-正常；1-丢帧；2-掉线；3-预留； */
	private CarMSDataItemPacket tcu; 
	/** 电池温度故障 0-正常；1-偏高；2-严重过高；3-预留 */
	private CarMSDataItemPacket cellTempWarn; 
	/** 预充电故障标志位 0-正常；1-故障 */
	private CarMSDataItemPacket orderChargeWarnFlag; 
	/** 电池温差过大 0-正常；1-故障 */
	private CarMSDataItemPacket cellTempDiffHigher; 
	/** 电池压差过大 0-正常；1-故障 */
	private CarMSDataItemPacket cellVoltageDiffHigher; 
	/** 放电电流过大 0-正常；1-故障 */
	private CarMSDataItemPacket outCurrentHigher; 
	/** 电池温度过低 0-正常；1-一级故障；2-二级故障；3-预留 */
	private CarMSDataItemPacket cellTempLower; 
	/** 单体电压过低 0-正常；1-一级故障；2-二级故障；3-预留 */
	private CarMSDataItemPacket singleVoltageLower; 
	/** 总压过低 0-正常；1-低压电源低；2-低压电源高；3-预留 */
	private CarMSDataItemPacket totalVoltageLower; 
	/** 低压电源低 0-正常；1-低压电源低；2-低压电源高；3-预留 */
	private CarMSDataItemPacket lowVoltageSourceLower; 
	/** 充电电流过大 0-正常；1-故障 */
	private CarMSDataItemPacket chargeCurrentHigher; 
	/** 动力电池过压 0-正常；1-故障 */
	private CarMSDataItemPacket powerCellVoltageOver; 
	/** 电池系统报警状态 0-正常；1-一级故障；2-二级故障；3-预留 */
	private CarMSDataItemPacket cellSysWarn; 
	/** BMS 自检 0-正常；1-故障 */
	private CarMSDataItemPacket bmsCheck; 
	/** 电池包故障 0-正常；1-故障 */
	private CarMSDataItemPacket cellsWarn; 
	/** 高压控制盒故障 0-正常；1-故障 */
	private CarMSDataItemPacket highVoltageControl; 
	/** 电池组绝缘报警 0-正常；1-一级故障；2-二级故障；3-预留 */
	private CarMSDataItemPacket cellsIsolation; 
	/** 浮动插头状态 0-正常；1-一级故障；2-二级故障；3-预留 */
	private CarMSDataItemPacket floatPlug; 
	/** 档位故障 0-正常；1-故障 */
	private CarMSDataItemPacket gearWarn; 
	/** 电机使能反馈不一致故障 0-正常；1-故障 */
	private CarMSDataItemPacket motorEnableFeedback; 
	/** 主接触器反馈不一致故障 0-正常；1-故障 */
	private CarMSDataItemPacket masterContactorFeedback; 
	/** 电机控制器过流 0-正常；1-故障 */
	private CarMSDataItemPacket motorControlOverFlow; 
	/** 电机控制器过压 0-正常；1-故障 */
	private CarMSDataItemPacket motorControlVoltageOver; 
	/** 电机控制器欠压 0-正常；1-故障 */
	private CarMSDataItemPacket motorControlVoltageLower; 
	/** 充电脉冲电流过大 0-正常；1-故障 */
	private CarMSDataItemPacket chargePulseCurrentHigher; 
	/** 单体电压过高 0-正常；1-故障 */
	private CarMSDataItemPacket singleVoltageHigher; 
	/** VCU 通信故障 0-正常；1-故障 */
	private CarMSDataItemPacket vcuConn; 
	/** 充电机通信故障 0-正常；1-故障 */
	private CarMSDataItemPacket chargeMotorConn; 
	/** 维修开关故障 0-正常；1-故障 */
	private CarMSDataItemPacket maintainSwitch;
	
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public CarMSDataItemPacket getSpeed() {
		return speed;
	}
	public void setSpeed(CarMSDataItemPacket speed) {
		this.speed = speed;
	}
	public CarMSDataItemPacket getTorque() {
		return torque;
	}
	public void setTorque(CarMSDataItemPacket torque) {
		this.torque = torque;
	}
	public CarMSDataItemPacket getTotalVoltage() {
		return totalVoltage;
	}
	public void setTotalVoltage(CarMSDataItemPacket totalVoltage) {
		this.totalVoltage = totalVoltage;
	}
	public CarMSDataItemPacket getSingleVoltageHighest() {
		return singleVoltageHighest;
	}
	public void setSingleVoltageHighest(CarMSDataItemPacket singleVoltageHighest) {
		this.singleVoltageHighest = singleVoltageHighest;
	}
	public CarMSDataItemPacket getSingleVoltageLowest() {
		return singleVoltageLowest;
	}
	public void setSingleVoltageLowest(CarMSDataItemPacket singleVoltageLowest) {
		this.singleVoltageLowest = singleVoltageLowest;
	}
	public CarMSDataItemPacket getSingleVoltageDiff() {
		return singleVoltageDiff;
	}
	public void setSingleVoltageDiff(CarMSDataItemPacket singleVoltageDiff) {
		this.singleVoltageDiff = singleVoltageDiff;
	}
	public CarMSDataItemPacket getBrakeSignal() {
		return brakeSignal;
	}
	public void setBrakeSignal(CarMSDataItemPacket brakeSignal) {
		this.brakeSignal = brakeSignal;
	}
	public CarMSDataItemPacket getChargeMotorStatus() {
		return chargeMotorStatus;
	}
	public void setChargeMotorStatus(CarMSDataItemPacket chargeMotorStatus) {
		this.chargeMotorStatus = chargeMotorStatus;
	}
	public CarMSDataItemPacket getWanrFlag() {
		return wanrFlag;
	}
	public void setWanrFlag(CarMSDataItemPacket wanrFlag) {
		this.wanrFlag = wanrFlag;
	}
	public CarMSDataItemPacket getMotorControlTemp() {
		return motorControlTemp;
	}
	public void setMotorControlTemp(CarMSDataItemPacket motorControlTemp) {
		this.motorControlTemp = motorControlTemp;
	}
	public CarMSDataItemPacket getMotorSpeedOver() {
		return motorSpeedOver;
	}
	public void setMotorSpeedOver(CarMSDataItemPacket motorSpeedOver) {
		this.motorSpeedOver = motorSpeedOver;
	}
	public CarMSDataItemPacket getIgbtStatus() {
		return igbtStatus;
	}
	public void setIgbtStatus(CarMSDataItemPacket igbtStatus) {
		this.igbtStatus = igbtStatus;
	}
	public CarMSDataItemPacket getMotorCheckSelfStatus() {
		return motorCheckSelfStatus;
	}
	public void setMotorCheckSelfStatus(CarMSDataItemPacket motorCheckSelfStatus) {
		this.motorCheckSelfStatus = motorCheckSelfStatus;
	}
	public CarMSDataItemPacket getMotorResolver() {
		return motorResolver;
	}
	public void setMotorResolver(CarMSDataItemPacket motorResolver) {
		this.motorResolver = motorResolver;
	}
	public CarMSDataItemPacket getAccelTrip() {
		return accelTrip;
	}
	public void setAccelTrip(CarMSDataItemPacket accelTrip) {
		this.accelTrip = accelTrip;
	}
	public CarMSDataItemPacket getMotorControl() {
		return motorControl;
	}
	public void setMotorControl(CarMSDataItemPacket motorControl) {
		this.motorControl = motorControl;
	}
	public CarMSDataItemPacket getCellSys() {
		return cellSys;
	}
	public void setCellSys(CarMSDataItemPacket cellSys) {
		this.cellSys = cellSys;
	}
	public CarMSDataItemPacket getTcu() {
		return tcu;
	}
	public void setTcu(CarMSDataItemPacket tcu) {
		this.tcu = tcu;
	}
	public CarMSDataItemPacket getCellTempWarn() {
		return cellTempWarn;
	}
	public void setCellTempWarn(CarMSDataItemPacket cellTempWarn) {
		this.cellTempWarn = cellTempWarn;
	}
	public CarMSDataItemPacket getOrderChargeWarnFlag() {
		return orderChargeWarnFlag;
	}
	public void setOrderChargeWarnFlag(CarMSDataItemPacket orderChargeWarnFlag) {
		this.orderChargeWarnFlag = orderChargeWarnFlag;
	}
	public CarMSDataItemPacket getCellTempDiffHigher() {
		return cellTempDiffHigher;
	}
	public void setCellTempDiffHigher(CarMSDataItemPacket cellTempDiffHigher) {
		this.cellTempDiffHigher = cellTempDiffHigher;
	}
	public CarMSDataItemPacket getCellVoltageDiffHigher() {
		return cellVoltageDiffHigher;
	}
	public void setCellVoltageDiffHigher(CarMSDataItemPacket cellVoltageDiffHigher) {
		this.cellVoltageDiffHigher = cellVoltageDiffHigher;
	}
	public CarMSDataItemPacket getOutCurrentHigher() {
		return outCurrentHigher;
	}
	public void setOutCurrentHigher(CarMSDataItemPacket outCurrentHigher) {
		this.outCurrentHigher = outCurrentHigher;
	}
	public CarMSDataItemPacket getCellTempLower() {
		return cellTempLower;
	}
	public void setCellTempLower(CarMSDataItemPacket cellTempLower) {
		this.cellTempLower = cellTempLower;
	}
	public CarMSDataItemPacket getSingleVoltageLower() {
		return singleVoltageLower;
	}
	public void setSingleVoltageLower(CarMSDataItemPacket singleVoltageLower) {
		this.singleVoltageLower = singleVoltageLower;
	}
	public CarMSDataItemPacket getTotalVoltageLower() {
		return totalVoltageLower;
	}
	public void setTotalVoltageLower(CarMSDataItemPacket totalVoltageLower) {
		this.totalVoltageLower = totalVoltageLower;
	}
	public CarMSDataItemPacket getLowVoltageSourceLower() {
		return lowVoltageSourceLower;
	}
	public void setLowVoltageSourceLower(CarMSDataItemPacket lowVoltageSourceLower) {
		this.lowVoltageSourceLower = lowVoltageSourceLower;
	}
	public CarMSDataItemPacket getChargeCurrentHigher() {
		return chargeCurrentHigher;
	}
	public void setChargeCurrentHigher(CarMSDataItemPacket chargeCurrentHigher) {
		this.chargeCurrentHigher = chargeCurrentHigher;
	}
	public CarMSDataItemPacket getPowerCellVoltageOver() {
		return powerCellVoltageOver;
	}
	public void setPowerCellVoltageOver(CarMSDataItemPacket powerCellVoltageOver) {
		this.powerCellVoltageOver = powerCellVoltageOver;
	}
	public CarMSDataItemPacket getCellSysWarn() {
		return cellSysWarn;
	}
	public void setCellSysWarn(CarMSDataItemPacket cellSysWarn) {
		this.cellSysWarn = cellSysWarn;
	}
	public CarMSDataItemPacket getBmsCheck() {
		return bmsCheck;
	}
	public void setBmsCheck(CarMSDataItemPacket bmsCheck) {
		this.bmsCheck = bmsCheck;
	}
	public CarMSDataItemPacket getCellsWarn() {
		return cellsWarn;
	}
	public void setCellsWarn(CarMSDataItemPacket cellsWarn) {
		this.cellsWarn = cellsWarn;
	}
	public CarMSDataItemPacket getHighVoltageControl() {
		return highVoltageControl;
	}
	public void setHighVoltageControl(CarMSDataItemPacket highVoltageControl) {
		this.highVoltageControl = highVoltageControl;
	}
	public CarMSDataItemPacket getCellsIsolation() {
		return cellsIsolation;
	}
	public void setCellsIsolation(CarMSDataItemPacket cellsIsolation) {
		this.cellsIsolation = cellsIsolation;
	}
	public CarMSDataItemPacket getFloatPlug() {
		return floatPlug;
	}
	public void setFloatPlug(CarMSDataItemPacket floatPlug) {
		this.floatPlug = floatPlug;
	}
	public CarMSDataItemPacket getGearWarn() {
		return gearWarn;
	}
	public void setGearWarn(CarMSDataItemPacket gearWarn) {
		this.gearWarn = gearWarn;
	}
	public CarMSDataItemPacket getMotorEnableFeedback() {
		return motorEnableFeedback;
	}
	public void setMotorEnableFeedback(CarMSDataItemPacket motorEnableFeedback) {
		this.motorEnableFeedback = motorEnableFeedback;
	}
	public CarMSDataItemPacket getMasterContactorFeedback() {
		return masterContactorFeedback;
	}
	public void setMasterContactorFeedback(CarMSDataItemPacket masterContactorFeedback) {
		this.masterContactorFeedback = masterContactorFeedback;
	}
	public CarMSDataItemPacket getMotorControlOverFlow() {
		return motorControlOverFlow;
	}
	public void setMotorControlOverFlow(CarMSDataItemPacket motorControlOverFlow) {
		this.motorControlOverFlow = motorControlOverFlow;
	}
	public CarMSDataItemPacket getMotorControlVoltageOver() {
		return motorControlVoltageOver;
	}
	public void setMotorControlVoltageOver(CarMSDataItemPacket motorControlVoltageOver) {
		this.motorControlVoltageOver = motorControlVoltageOver;
	}
	public CarMSDataItemPacket getMotorControlVoltageLower() {
		return motorControlVoltageLower;
	}
	public void setMotorControlVoltageLower(CarMSDataItemPacket motorControlVoltageLower) {
		this.motorControlVoltageLower = motorControlVoltageLower;
	}
	public CarMSDataItemPacket getChargePulseCurrentHigher() {
		return chargePulseCurrentHigher;
	}
	public void setChargePulseCurrentHigher(CarMSDataItemPacket chargePulseCurrentHigher) {
		this.chargePulseCurrentHigher = chargePulseCurrentHigher;
	}
	public CarMSDataItemPacket getSingleVoltageHigher() {
		return singleVoltageHigher;
	}
	public void setSingleVoltageHigher(CarMSDataItemPacket singleVoltageHigher) {
		this.singleVoltageHigher = singleVoltageHigher;
	}
	public CarMSDataItemPacket getVcuConn() {
		return vcuConn;
	}
	public void setVcuConn(CarMSDataItemPacket vcuConn) {
		this.vcuConn = vcuConn;
	}
	public CarMSDataItemPacket getChargeMotorConn() {
		return chargeMotorConn;
	}
	public void setChargeMotorConn(CarMSDataItemPacket chargeMotorConn) {
		this.chargeMotorConn = chargeMotorConn;
	}
	public CarMSDataItemPacket getMaintainSwitch() {
		return maintainSwitch;
	}
	public void setMaintainSwitch(CarMSDataItemPacket maintainSwitch) {
		this.maintainSwitch = maintainSwitch;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	} 
	
}
