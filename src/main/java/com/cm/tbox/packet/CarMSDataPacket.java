package com.cm.tbox.packet;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

import java.io.Serializable;
import java.util.Date;
import java.util.Arrays;
/**
 * 短周期数据包
 * @author yunlu
 *
 */
public class CarMSDataPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = -464080377270368636L;

	/**数据采集时间*/
	private Date createTime;
	
	private Integer length;
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
	
	@Override
	public void build(byte[] bytes) throws Exception {
		createTime = ByteUtils.convertByteArrtDatetime(Arrays.copyOfRange(bytes, 0, 6));
		int position=6;
		while(position<bytes.length){
			position=convert(position,bytes);
		}
		length=position;
	}
	
	public int convert(int position,byte[] bytes)throws Exception{
		int dataId = bytes[position];
		int timePeriod = 0xff & bytes[position+1];
		int dataLength= 0xff & bytes[position+2];
		position+=3;
		switch (dataId){
			case 0x01:
				speed= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					speed.put(ByteUtils.toDouble(bytes[position+i*2], bytes[position+1+i*2],10, 0));
				}
				position+=2*dataLength;
				break;	
			case 0x02:
				torque= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					torque.put(ByteUtils.toDouble(bytes[position+i*2], bytes[position+1+i*2],100, 300));
				}
				position+=2*dataLength;
				break;	
			case 0x03:
				totalVoltage= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					totalVoltage.put(ByteUtils.toDouble(bytes[position+i*2], bytes[position+1+i*2],10, 0));
				}
				position+=2*dataLength;
				break;	
			case 0x04:
				singleVoltageHighest= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					singleVoltageHighest.put(ByteUtils.toDouble(bytes[position+i*2], bytes[position+1+i*2],1000, 0));
				}
				position+=2*dataLength;
				break;	
			case 0x05:
				singleVoltageLowest= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					singleVoltageLowest.put(ByteUtils.toDouble(bytes[position+i*2], bytes[position+1+i*2],1000, 0));
				}
				position+=2*dataLength;
				break;	
			case 0x06:
				singleVoltageDiff= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					singleVoltageDiff.put(ByteUtils.toDouble(bytes[position+i*2], bytes[position+1+i*2],10000, 0));
				}
				position+=2*dataLength;
				break;	
			case 0x07:
				brakeSignal= new CarMSDataItemPacket(dataLength,timePeriod);
				chargeMotorStatus= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					brakeSignal.put(0x01 & (bytes[position+i] >>> 0));
					chargeMotorStatus.put(0x01 & (bytes[position+i] >>> 1));
				}
				position+=dataLength;
				break;	
			case 0x08:
				wanrFlag= new CarMSDataItemPacket(dataLength,timePeriod);
				motorControlTemp= new CarMSDataItemPacket(dataLength,timePeriod);
				motorSpeedOver= new CarMSDataItemPacket(dataLength,timePeriod);
				igbtStatus= new CarMSDataItemPacket(dataLength,timePeriod);
				motorCheckSelfStatus= new CarMSDataItemPacket(dataLength,timePeriod);
				motorResolver= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					wanrFlag.put(0x03 & (bytes[position+i] >>> 0));
					motorControlTemp.put(0x03 & (bytes[position+i] >>> 2));
					motorSpeedOver.put(0x01 & (bytes[position+i] >>> 4));
					igbtStatus.put(0x01 & (bytes[position+i] >>> 5));
					motorCheckSelfStatus.put(0x01 & (bytes[position+i] >>> 6));
					motorResolver.put(0x01 & (bytes[position+i] >>> 7));
				}
				position+=dataLength;
				break;	
			case 0x09:
				accelTrip= new CarMSDataItemPacket(dataLength,timePeriod);
				motorControl= new CarMSDataItemPacket(dataLength,timePeriod);
				cellSys= new CarMSDataItemPacket(dataLength,timePeriod);
				tcu= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					accelTrip.put(0x03 & (bytes[position+i] >>> 0));
					motorControl.put(0x03 & (bytes[position+i] >>> 2));
					cellSys.put(0x03 & (bytes[position+i] >>> 4));
					tcu.put(0x03 & (bytes[position+i] >>> 6));
				}
				position+=dataLength;
				break;	
			case 0x0a:
				cellTempWarn= new CarMSDataItemPacket(dataLength,timePeriod);
				orderChargeWarnFlag= new CarMSDataItemPacket(dataLength,timePeriod);
				cellTempDiffHigher= new CarMSDataItemPacket(dataLength,timePeriod);
				cellVoltageDiffHigher= new CarMSDataItemPacket(dataLength,timePeriod);
				outCurrentHigher= new CarMSDataItemPacket(dataLength,timePeriod);
				cellTempLower= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					cellTempWarn.put(0x03 & (bytes[position+i] >>> 0));
					orderChargeWarnFlag.put(0x01 & (bytes[position+i] >>> 2));
					cellTempDiffHigher.put(0x01 & (bytes[position+i] >>> 3));
					cellVoltageDiffHigher.put(0x01 & (bytes[position+i] >>> 4));
					outCurrentHigher.put(0x01 & (bytes[position+i] >>> 5));
					cellTempLower.put(0x03 & (bytes[position+i] >>> 6));
				}
				position+=dataLength;
				break;	
			case 0x0b:
				singleVoltageLower= new CarMSDataItemPacket(dataLength,timePeriod);
				totalVoltageLower= new CarMSDataItemPacket(dataLength,timePeriod);
				lowVoltageSourceLower= new CarMSDataItemPacket(dataLength,timePeriod);
				chargeCurrentHigher= new CarMSDataItemPacket(dataLength,timePeriod);
				powerCellVoltageOver= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					singleVoltageLower.put(0x03 & (bytes[position+i] >>> 0));
					totalVoltageLower.put(0x03 & (bytes[position+i] >>> 2));
					lowVoltageSourceLower.put(0x03 & (bytes[position+i] >>> 4));
					chargeCurrentHigher.put(0x01 & (bytes[position+i] >>> 6));
					powerCellVoltageOver.put(0x01 & (bytes[position+i] >>> 7));
				}
				position+=dataLength;
				break;	
			case 0x0c:
				cellSysWarn= new CarMSDataItemPacket(dataLength,timePeriod);
				bmsCheck= new CarMSDataItemPacket(dataLength,timePeriod);
				cellsWarn= new CarMSDataItemPacket(dataLength,timePeriod);
				highVoltageControl= new CarMSDataItemPacket(dataLength,timePeriod);
				cellsIsolation= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					cellSysWarn.put(0x03 & (bytes[position+i] >>> 0));
					bmsCheck.put(0x01 & (bytes[position+i] >>> 2));
					cellsWarn.put(0x01 & (bytes[position+i] >>> 3));
					highVoltageControl.put(0x01 & (bytes[position+i] >>> 4));
					cellsIsolation.put(0x03 & (bytes[position+i] >>> 5));
				}
				position+=dataLength;
				break;	
			case 0x0d:
				floatPlug= new CarMSDataItemPacket(dataLength,timePeriod);
				gearWarn= new CarMSDataItemPacket(dataLength,timePeriod);
				motorEnableFeedback= new CarMSDataItemPacket(dataLength,timePeriod);
				masterContactorFeedback= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					floatPlug.put(0x03 & (bytes[position+i] >>> 0));
					gearWarn.put(0x01 & (bytes[position+i] >>> 2));
					motorEnableFeedback.put(0x01 & (bytes[position+i] >>> 3));
					masterContactorFeedback.put(0x01 & (bytes[position+i] >>> 4));
				}
				position+=dataLength;
				break;	
			case 0x0e:
				chargePulseCurrentHigher= new CarMSDataItemPacket(dataLength,timePeriod);
				singleVoltageHigher= new CarMSDataItemPacket(dataLength,timePeriod);
				vcuConn= new CarMSDataItemPacket(dataLength,timePeriod);
				chargeMotorConn= new CarMSDataItemPacket(dataLength,timePeriod);
				maintainSwitch= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					chargePulseCurrentHigher.put(0x01 & (bytes[position+i] >>> 0));
					singleVoltageHigher.put(0x01 & (bytes[position+i] >>> 1));
					vcuConn.put(0x01 & (bytes[position+i] >>> 2));
					chargeMotorConn.put(0x01 & (bytes[position+i] >>> 3));
					maintainSwitch.put(0x01 & (bytes[position+i] >>> 4));
				}
				position+=dataLength;
				break;	
			case 0x0f:
				motorControlOverFlow= new CarMSDataItemPacket(dataLength,timePeriod);
				motorControlVoltageOver= new CarMSDataItemPacket(dataLength,timePeriod);
				motorControlVoltageLower= new CarMSDataItemPacket(dataLength,timePeriod);
				for(int i=0;i<dataLength;i++){
					motorControlOverFlow.put(0x01 & (bytes[position+i] >>> 1));
					motorControlVoltageOver.put(0x01 & (bytes[position+i] >>> 2));
					motorControlVoltageLower.put(0x01 & (bytes[position+i] >>> 3));
				}
				position+=dataLength;
				break;	
			default:
				break;
		}
		return position;
	}
	
	@Override
	public byte[] unbuild() throws Exception {
		return null;
	}
	@Override
	public Integer length() throws Exception {
		return length;
	}
	public CarMSDataItemPacket getSpeed() {
        return this.speed;
    }
    public void setSpeed(CarMSDataItemPacket speed) {
        this.speed = speed;
    }
	public CarMSDataItemPacket getTorque() {
        return this.torque;
    }
    public void setTorque(CarMSDataItemPacket torque) {
        this.torque = torque;
    }
	public CarMSDataItemPacket getTotalVoltage() {
        return this.totalVoltage;
    }
    public void setTotalVoltage(CarMSDataItemPacket totalVoltage) {
        this.totalVoltage = totalVoltage;
    }
	public CarMSDataItemPacket getSingleVoltageHighest() {
        return this.singleVoltageHighest;
    }
    public void setSingleVoltageHighest(CarMSDataItemPacket singleVoltageHighest) {
        this.singleVoltageHighest = singleVoltageHighest;
    }
	public CarMSDataItemPacket getSingleVoltageLowest() {
        return this.singleVoltageLowest;
    }
    public void setSingleVoltageLowest(CarMSDataItemPacket singleVoltageLowest) {
        this.singleVoltageLowest = singleVoltageLowest;
    }
	public CarMSDataItemPacket getSingleVoltageDiff() {
        return this.singleVoltageDiff;
    }
    public void setSingleVoltageDiff(CarMSDataItemPacket singleVoltageDiff) {
        this.singleVoltageDiff = singleVoltageDiff;
    }
	public CarMSDataItemPacket getBrakeSignal() {
        return this.brakeSignal;
    }
    public void setBrakeSignal(CarMSDataItemPacket brakeSignal) {
        this.brakeSignal = brakeSignal;
    }
	public CarMSDataItemPacket getChargeMotorStatus() {
        return this.chargeMotorStatus;
    }
    public void setChargeMotorStatus(CarMSDataItemPacket chargeMotorStatus) {
        this.chargeMotorStatus = chargeMotorStatus;
    }
	public CarMSDataItemPacket getWanrFlag() {
        return this.wanrFlag;
    }
    public void setWanrFlag(CarMSDataItemPacket wanrFlag) {
        this.wanrFlag = wanrFlag;
    }
	public CarMSDataItemPacket getMotorControlTemp() {
        return this.motorControlTemp;
    }
    public void setMotorControlTemp(CarMSDataItemPacket motorControlTemp) {
        this.motorControlTemp = motorControlTemp;
    }
	public CarMSDataItemPacket getMotorSpeedOver() {
        return this.motorSpeedOver;
    }
    public void setMotorSpeedOver(CarMSDataItemPacket motorSpeedOver) {
        this.motorSpeedOver = motorSpeedOver;
    }
	public CarMSDataItemPacket getIgbtStatus() {
        return this.igbtStatus;
    }
    public void setIgbtStatus(CarMSDataItemPacket igbtStatus) {
        this.igbtStatus = igbtStatus;
    }
	public CarMSDataItemPacket getMotorCheckSelfStatus() {
        return this.motorCheckSelfStatus;
    }
    public void setMotorCheckSelfStatus(CarMSDataItemPacket motorCheckSelfStatus) {
        this.motorCheckSelfStatus = motorCheckSelfStatus;
    }
	public CarMSDataItemPacket getMotorResolver() {
        return this.motorResolver;
    }
    public void setMotorResolver(CarMSDataItemPacket motorResolver) {
        this.motorResolver = motorResolver;
    }
	public CarMSDataItemPacket getAccelTrip() {
        return this.accelTrip;
    }
    public void setAccelTrip(CarMSDataItemPacket accelTrip) {
        this.accelTrip = accelTrip;
    }
	public CarMSDataItemPacket getMotorControl() {
        return this.motorControl;
    }
    public void setMotorControl(CarMSDataItemPacket motorControl) {
        this.motorControl = motorControl;
    }
	public CarMSDataItemPacket getCellSys() {
        return this.cellSys;
    }
    public void setCellSys(CarMSDataItemPacket cellSys) {
        this.cellSys = cellSys;
    }
	public CarMSDataItemPacket getTcu() {
        return this.tcu;
    }
    public void setTcu(CarMSDataItemPacket tcu) {
        this.tcu = tcu;
    }
	public CarMSDataItemPacket getCellTempWarn() {
        return this.cellTempWarn;
    }
    public void setCellTempWarn(CarMSDataItemPacket cellTempWarn) {
        this.cellTempWarn = cellTempWarn;
    }
	public CarMSDataItemPacket getOrderChargeWarnFlag() {
        return this.orderChargeWarnFlag;
    }
    public void setOrderChargeWarnFlag(CarMSDataItemPacket orderChargeWarnFlag) {
        this.orderChargeWarnFlag = orderChargeWarnFlag;
    }
	public CarMSDataItemPacket getCellTempDiffHigher() {
        return this.cellTempDiffHigher;
    }
    public void setCellTempDiffHigher(CarMSDataItemPacket cellTempDiffHigher) {
        this.cellTempDiffHigher = cellTempDiffHigher;
    }
	public CarMSDataItemPacket getCellVoltageDiffHigher() {
        return this.cellVoltageDiffHigher;
    }
    public void setCellVoltageDiffHigher(CarMSDataItemPacket cellVoltageDiffHigher) {
        this.cellVoltageDiffHigher = cellVoltageDiffHigher;
    }
	public CarMSDataItemPacket getOutCurrentHigher() {
        return this.outCurrentHigher;
    }
    public void setOutCurrentHigher(CarMSDataItemPacket outCurrentHigher) {
        this.outCurrentHigher = outCurrentHigher;
    }
	public CarMSDataItemPacket getCellTempLower() {
        return this.cellTempLower;
    }
    public void setCellTempLower(CarMSDataItemPacket cellTempLower) {
        this.cellTempLower = cellTempLower;
    }
	public CarMSDataItemPacket getSingleVoltageLower() {
        return this.singleVoltageLower;
    }
    public void setSingleVoltageLower(CarMSDataItemPacket singleVoltageLower) {
        this.singleVoltageLower = singleVoltageLower;
    }
	public CarMSDataItemPacket getTotalVoltageLower() {
        return this.totalVoltageLower;
    }
    public void setTotalVoltageLower(CarMSDataItemPacket totalVoltageLower) {
        this.totalVoltageLower = totalVoltageLower;
    }
	public CarMSDataItemPacket getLowVoltageSourceLower() {
        return this.lowVoltageSourceLower;
    }
    public void setLowVoltageSourceLower(CarMSDataItemPacket lowVoltageSourceLower) {
        this.lowVoltageSourceLower = lowVoltageSourceLower;
    }
	public CarMSDataItemPacket getChargeCurrentHigher() {
        return this.chargeCurrentHigher;
    }
    public void setChargeCurrentHigher(CarMSDataItemPacket chargeCurrentHigher) {
        this.chargeCurrentHigher = chargeCurrentHigher;
    }
	public CarMSDataItemPacket getPowerCellVoltageOver() {
        return this.powerCellVoltageOver;
    }
    public void setPowerCellVoltageOver(CarMSDataItemPacket powerCellVoltageOver) {
        this.powerCellVoltageOver = powerCellVoltageOver;
    }
	public CarMSDataItemPacket getCellSysWarn() {
        return this.cellSysWarn;
    }
    public void setCellSysWarn(CarMSDataItemPacket cellSysWarn) {
        this.cellSysWarn = cellSysWarn;
    }
	public CarMSDataItemPacket getBmsCheck() {
        return this.bmsCheck;
    }
    public void setBmsCheck(CarMSDataItemPacket bmsCheck) {
        this.bmsCheck = bmsCheck;
    }
	public CarMSDataItemPacket getCellsWarn() {
        return this.cellsWarn;
    }
    public void setCellsWarn(CarMSDataItemPacket cellsWarn) {
        this.cellsWarn = cellsWarn;
    }
	public CarMSDataItemPacket getHighVoltageControl() {
        return this.highVoltageControl;
    }
    public void setHighVoltageControl(CarMSDataItemPacket highVoltageControl) {
        this.highVoltageControl = highVoltageControl;
    }
	public CarMSDataItemPacket getCellsIsolation() {
        return this.cellsIsolation;
    }
    public void setCellsIsolation(CarMSDataItemPacket cellsIsolation) {
        this.cellsIsolation = cellsIsolation;
    }
	public CarMSDataItemPacket getFloatPlug() {
        return this.floatPlug;
    }
    public void setFloatPlug(CarMSDataItemPacket floatPlug) {
        this.floatPlug = floatPlug;
    }
	public CarMSDataItemPacket getGearWarn() {
        return this.gearWarn;
    }
    public void setGearWarn(CarMSDataItemPacket gearWarn) {
        this.gearWarn = gearWarn;
    }
	public CarMSDataItemPacket getMotorEnableFeedback() {
        return this.motorEnableFeedback;
    }
    public void setMotorEnableFeedback(CarMSDataItemPacket motorEnableFeedback) {
        this.motorEnableFeedback = motorEnableFeedback;
    }
	public CarMSDataItemPacket getMasterContactorFeedback() {
        return this.masterContactorFeedback;
    }
    public void setMasterContactorFeedback(CarMSDataItemPacket masterContactorFeedback) {
        this.masterContactorFeedback = masterContactorFeedback;
    }
	public CarMSDataItemPacket getMotorControlOverFlow() {
        return this.motorControlOverFlow;
    }
    public void setMotorControlOverFlow(CarMSDataItemPacket motorControlOverFlow) {
        this.motorControlOverFlow = motorControlOverFlow;
    }
	public CarMSDataItemPacket getMotorControlVoltageOver() {
        return this.motorControlVoltageOver;
    }
    public void setMotorControlVoltageOver(CarMSDataItemPacket motorControlVoltageOver) {
        this.motorControlVoltageOver = motorControlVoltageOver;
    }
	public CarMSDataItemPacket getMotorControlVoltageLower() {
        return this.motorControlVoltageLower;
    }
    public void setMotorControlVoltageLower(CarMSDataItemPacket motorControlVoltageLower) {
        this.motorControlVoltageLower = motorControlVoltageLower;
    }
	public CarMSDataItemPacket getChargePulseCurrentHigher() {
        return this.chargePulseCurrentHigher;
    }
    public void setChargePulseCurrentHigher(CarMSDataItemPacket chargePulseCurrentHigher) {
        this.chargePulseCurrentHigher = chargePulseCurrentHigher;
    }
	public CarMSDataItemPacket getSingleVoltageHigher() {
        return this.singleVoltageHigher;
    }
    public void setSingleVoltageHigher(CarMSDataItemPacket singleVoltageHigher) {
        this.singleVoltageHigher = singleVoltageHigher;
    }
	public CarMSDataItemPacket getVcuConn() {
        return this.vcuConn;
    }
    public void setVcuConn(CarMSDataItemPacket vcuConn) {
        this.vcuConn = vcuConn;
    }
	public CarMSDataItemPacket getChargeMotorConn() {
        return this.chargeMotorConn;
    }
    public void setChargeMotorConn(CarMSDataItemPacket chargeMotorConn) {
        this.chargeMotorConn = chargeMotorConn;
    }
	public CarMSDataItemPacket getMaintainSwitch() {
        return this.maintainSwitch;
    }
    public void setMaintainSwitch(CarMSDataItemPacket maintainSwitch) {
        this.maintainSwitch = maintainSwitch;
    }
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
