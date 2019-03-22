package com.cm.tbox.packet;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import java.io.Serializable;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 蓝海新能源协议扩展包
 * @author yunlu
 *
 */
public class CarLHXNExpandPacket implements BasePacket,Serializable{
	/** sim卡连接状态 0-已连接 1-未连接  */
	@Property("a")
	private Integer simStatus; 
	/** TF卡连接状态 0-已连接 1-未连接 */
	@Property("b")
	private Integer tfStatus; 
	/** GSM天线连接状态 0-已连接 1-未连接 */
	@Property("c")
	private Integer gsmStatus; 
	/** GPS天线连接状态 0-已连接 1-未连接 */
	@Property("d")
	private Integer gpsStatus; 
	/** 整车工作模式 0-无效 1-正常工作模式 2-跛行模式 3-待机模式 */
	@Property("e")
	private Integer carWorkModel; 
	/** tbox软件版本 */
	@Property("f")
	private Integer tboxVersion; 
	/** 实际转矩反馈 单元：Nm 范围：-300至300*/
	@Property("g")
	private Double torqueFeedback; 
	/** 电机控制器版本 */
	@Property("h")
	private Integer motorControlVersion; 
	/** 转矩需求命令 单元：Nm 范围：-300至300*/
	@Property("i")
	private Double torqueCommand; 
	/** vcu软件版本 */
	@Property("j")
	private Integer vcuVersion; 
	/** 真空值 单元：kPa 范围：-3000至3000*/
	@Property("k")
	private Double vacuum; 
	/** ptc温度 单元：deg 范围：-100至155*/
	@Property("l")
	private Integer ptcTemp; 
	/** 单次里程数 单元：km 范围：0至6000*/
	@Property("m")
	private Double singleMileage; 
	/** 充电剩余时间 单元：h 范围：0至25.5*/
	@Property("n")
	private Double chargeDumpTime; 
	/** 电池包总里程 单元：km 范围：0至1677721.5*/
	@Property("o")
	private Double cellsMileage; 
	/** 电池组平均温度 单元：deg 范围：-100至155*/
	@Property("p")
	private Integer cellsAvgTemp; 
	/** bms软件版本 */
	@Property("q")
	private Integer bmsVersion; 
	/** 电池包充电次数 */
	@Property("r")
	private Integer cellsChargeTimes; 
	/** 单体电池压差值 单元：V 范围：0至6.5535*/
	@Property("s")
	private Double singleVoltageDiff; 
	/** 电池剩余里程 单元：km 范围：0至250*/
	@Property("t")
	private Integer dumpMileage; 
	/** 电池可用最大容量 单元：AH */
	@Property("u")
	private Integer cellMaxVolume; 
	/** 电池生产厂代号 */
	@Property("v")
	private Integer cellProducerNo; 
	/** 电池箱序号 */
	@Property("w")
	private Integer cellsNo; 
	/** 最高允许输出端电压 单元：V 范围：0至655.35*/
	@Property("x")
	private Double allowOutVoltageHigh; 
	/** 最高允许输出端电流 单元：A 范围：0至655.35*/
	@Property("y")
	private Double allowOutCurrentHigh; 
	/** 慢充充电机工作模式 0-默认值 1-电加热模式 2-充电模式 */
	@Property("z")
	private Integer slowChargeControlModel; 
	/** 慢充充电机控制字 0-关闭输出 1-启动 */
	@Property("A")
	private Integer slowChargeControlStatus; 
	/** 快充充电机控制 1-关闭输出 1-启动 */
	@Property("B")
	private Integer fastChargeControl; 
	/** 最高允许充电端电压 单元：V 范围：0至655.35*/
	@Property("C")
	private Double allowChargeVoltageHigh; 
	/** 最高允许充电端电流 单元：A 范围：0至655.35*/
	@Property("D")
	private Double allowChargeCurrentHigh; 
	/** 电池组标称AH 单元：AH */
	@Property("E")
	private Integer cellsStandardAH; 
	/** 电池组实际AH 单元：AH */
	@Property("F")
	private Integer cellsRealAH; 
	/** 电池节数 */
	@Property("G")
	private Integer cellsNum; 
	/** 单节电池最低保护电压 单元：V 范围：0至65.535*/
	@Property("H")
	private Double singleProtectVoltageLow; 
	/** 风机状态 0-关闭 1-开启 */
	@Property("I")
	private Integer fanStatus; 
	/** 电热接触器状态 0-断开 1-闭合 */
	@Property("J")
	private Integer heaterStatus; 
	/** 维修开关连接状态 0-未连接 1-连接 */
	@Property("K")
	private Integer repairSwitch; 
	/** 总负辅助触点信号 0-无效 1-有效 */
	@Property("L")
	private Integer minusAssistSignal; 
	/** 总正辅助触点信号 0-无效 1-有效 */
	@Property("M")
	private Integer plusAssistSignal; 
	/** 充电正接触器状态 0-无效 1-有效 */
	@Property("N")
	private Integer plusChargeContactor; 
	/** 总负接触器状态 0-无效 1-有效 */
	@Property("O")
	private Integer minusContactor; 
	/** 总正接触器状态 0-无效 1-有效 */
	@Property("P")
	private Integer plusContactor; 
	/** 电压热辅助触点信号 0-无效 1-有效 */
	@Property("Q")
	private Integer voltageThermalAssistSignal; 
	/** 快充接触器状态 0-断开 1-闭合 */
	@Property("R")
	private Integer fastChargeContactor; 
	/** 快充辅助触点信号 0-无效 1-有效 */
	@Property("S")
	private Integer fastChargeAssistSignal; 
	/** bms厂商 0-亿能 1-预留 */
	@Property("T")
	private Integer bmsProducer;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		simStatus = 0x01 & (bytes[0] >>> 7);
		tfStatus = 0x01 & (bytes[0] >>> 6);
		gsmStatus = 0x01 & (bytes[0] >>> 5);
		gpsStatus = 0x01 & (bytes[0] >>> 4);
		carWorkModel = 0x03 & bytes[0] ;
		tboxVersion = 0xff & bytes[1];
		torqueFeedback = ByteUtils.toDouble(bytes[2], bytes[3],100, 300);
		motorControlVersion = 0xff & bytes[4];
		torqueCommand = ByteUtils.toDouble(bytes[5], bytes[6],100, 300);
		vcuVersion = 0xff & bytes[7];
		vacuum = ByteUtils.toDouble(bytes[8], bytes[9],10, 3000);
		ptcTemp = 0xff & bytes[10]-100;
		singleMileage = ByteUtils.toDouble(bytes[11], bytes[12],10, 0);
		chargeDumpTime = ByteUtils.toDouble(bytes[13], 10, 0);
		cellsMileage = ByteUtils.toDouble((byte)0x00,bytes[14], bytes[15],bytes[16],10, 0);
		cellsAvgTemp = 0xff & bytes[17]-100;
		bmsVersion = 0xff & bytes[18];
		cellsChargeTimes = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[19],bytes[20]}, 0);
		singleVoltageDiff = ByteUtils.toDouble(bytes[21], bytes[22],10000, 0);
		dumpMileage = 0xff & bytes[23];
		cellMaxVolume = 0xff & bytes[24];
		cellProducerNo = 0xff & bytes[25];
		cellsNo = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[26],bytes[27]}, 0);
		allowOutVoltageHigh = ByteUtils.toDouble(bytes[28], bytes[29],100, 0);
		allowOutCurrentHigh = ByteUtils.toDouble(bytes[30], bytes[31],100, 0);
		slowChargeControlModel = 0x03 & (bytes[32] >>> 1);
		slowChargeControlStatus = 0x01 & bytes[32] ;
		fastChargeControl = 0xff & bytes[33];
		allowChargeVoltageHigh = ByteUtils.toDouble(bytes[34], bytes[35],100, 0);
		allowChargeCurrentHigh = ByteUtils.toDouble(bytes[36], bytes[37],100, 0);
		cellsStandardAH = 0xff & bytes[38];
		cellsRealAH = 0xff & bytes[39];
		cellsNum = 0xff & bytes[40];
		singleProtectVoltageLow = ByteUtils.toDouble(bytes[41], bytes[42],1000, 0);
		fanStatus = 0x01 & (bytes[43] >>> 7);
		heaterStatus = 0x01 & (bytes[43] >>> 6);
		repairSwitch = 0x01 & (bytes[43] >>> 5);
		minusAssistSignal = 0x01 & (bytes[43] >>> 4);
		plusAssistSignal = 0x01 & (bytes[43] >>> 3);
		plusChargeContactor = 0x01 & (bytes[43] >>> 2);
		minusContactor = 0x01 & (bytes[43] >>> 1);
		plusContactor = 0x01 & bytes[43] ;
		voltageThermalAssistSignal = 0x01 & (bytes[44] >>> 4);
		fastChargeContactor = 0x01 & (bytes[44] >>> 3);
		fastChargeAssistSignal = 0x01 & (bytes[44] >>> 2);
		bmsProducer = 0x03 & bytes[44] ;
	}
	@Override
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer length() throws Exception {
		return 45;
	}
	public Integer getSimStatus() {
        return this.simStatus;
    }
    public void setSimStatus(Integer simStatus) {
        this.simStatus = simStatus;
    }
	public Integer getTfStatus() {
        return this.tfStatus;
    }
    public void setTfStatus(Integer tfStatus) {
        this.tfStatus = tfStatus;
    }
	public Integer getGsmStatus() {
        return this.gsmStatus;
    }
    public void setGsmStatus(Integer gsmStatus) {
        this.gsmStatus = gsmStatus;
    }
	public Integer getGpsStatus() {
        return this.gpsStatus;
    }
    public void setGpsStatus(Integer gpsStatus) {
        this.gpsStatus = gpsStatus;
    }
	public Integer getCarWorkModel() {
        return this.carWorkModel;
    }
    public void setCarWorkModel(Integer carWorkModel) {
        this.carWorkModel = carWorkModel;
    }
	public Integer getTboxVersion() {
        return this.tboxVersion;
    }
    public void setTboxVersion(Integer tboxVersion) {
        this.tboxVersion = tboxVersion;
    }
	public Double getTorqueFeedback() {
        return this.torqueFeedback;
    }
    public void setTorqueFeedback(Double torqueFeedback) {
        this.torqueFeedback = torqueFeedback;
    }
	public Integer getMotorControlVersion() {
        return this.motorControlVersion;
    }
    public void setMotorControlVersion(Integer motorControlVersion) {
        this.motorControlVersion = motorControlVersion;
    }
	public Double getTorqueCommand() {
        return this.torqueCommand;
    }
    public void setTorqueCommand(Double torqueCommand) {
        this.torqueCommand = torqueCommand;
    }
	public Integer getVcuVersion() {
        return this.vcuVersion;
    }
    public void setVcuVersion(Integer vcuVersion) {
        this.vcuVersion = vcuVersion;
    }
	public Double getVacuum() {
        return this.vacuum;
    }
    public void setVacuum(Double vacuum) {
        this.vacuum = vacuum;
    }
	public Integer getPtcTemp() {
        return this.ptcTemp;
    }
    public void setPtcTemp(Integer ptcTemp) {
        this.ptcTemp = ptcTemp;
    }
	public Double getSingleMileage() {
        return this.singleMileage;
    }
    public void setSingleMileage(Double singleMileage) {
        this.singleMileage = singleMileage;
    }
	public Double getChargeDumpTime() {
        return this.chargeDumpTime;
    }
    public void setChargeDumpTime(Double chargeDumpTime) {
        this.chargeDumpTime = chargeDumpTime;
    }
	public Double getCellsMileage() {
        return this.cellsMileage;
    }
    public void setCellsMileage(Double cellsMileage) {
        this.cellsMileage = cellsMileage;
    }
	public Integer getCellsAvgTemp() {
        return this.cellsAvgTemp;
    }
    public void setCellsAvgTemp(Integer cellsAvgTemp) {
        this.cellsAvgTemp = cellsAvgTemp;
    }
	public Integer getBmsVersion() {
        return this.bmsVersion;
    }
    public void setBmsVersion(Integer bmsVersion) {
        this.bmsVersion = bmsVersion;
    }
	public Integer getCellsChargeTimes() {
        return this.cellsChargeTimes;
    }
    public void setCellsChargeTimes(Integer cellsChargeTimes) {
        this.cellsChargeTimes = cellsChargeTimes;
    }
	public Double getSingleVoltageDiff() {
        return this.singleVoltageDiff;
    }
    public void setSingleVoltageDiff(Double singleVoltageDiff) {
        this.singleVoltageDiff = singleVoltageDiff;
    }
	public Integer getDumpMileage() {
        return this.dumpMileage;
    }
    public void setDumpMileage(Integer dumpMileage) {
        this.dumpMileage = dumpMileage;
    }
	public Integer getCellMaxVolume() {
        return this.cellMaxVolume;
    }
    public void setCellMaxVolume(Integer cellMaxVolume) {
        this.cellMaxVolume = cellMaxVolume;
    }
	public Integer getCellProducerNo() {
        return this.cellProducerNo;
    }
    public void setCellProducerNo(Integer cellProducerNo) {
        this.cellProducerNo = cellProducerNo;
    }
	public Integer getCellsNo() {
        return this.cellsNo;
    }
    public void setCellsNo(Integer cellsNo) {
        this.cellsNo = cellsNo;
    }
	public Double getAllowOutVoltageHigh() {
        return this.allowOutVoltageHigh;
    }
    public void setAllowOutVoltageHigh(Double allowOutVoltageHigh) {
        this.allowOutVoltageHigh = allowOutVoltageHigh;
    }
	public Double getAllowOutCurrentHigh() {
        return this.allowOutCurrentHigh;
    }
    public void setAllowOutCurrentHigh(Double allowOutCurrentHigh) {
        this.allowOutCurrentHigh = allowOutCurrentHigh;
    }
	public Integer getSlowChargeControlModel() {
        return this.slowChargeControlModel;
    }
    public void setSlowChargeControlModel(Integer slowChargeControlModel) {
        this.slowChargeControlModel = slowChargeControlModel;
    }
	public Integer getSlowChargeControlStatus() {
        return this.slowChargeControlStatus;
    }
    public void setSlowChargeControlStatus(Integer slowChargeControlStatus) {
        this.slowChargeControlStatus = slowChargeControlStatus;
    }
	public Integer getFastChargeControl() {
        return this.fastChargeControl;
    }
    public void setFastChargeControl(Integer fastChargeControl) {
        this.fastChargeControl = fastChargeControl;
    }
	public Double getAllowChargeVoltageHigh() {
        return this.allowChargeVoltageHigh;
    }
    public void setAllowChargeVoltageHigh(Double allowChargeVoltageHigh) {
        this.allowChargeVoltageHigh = allowChargeVoltageHigh;
    }
	public Double getAllowChargeCurrentHigh() {
        return this.allowChargeCurrentHigh;
    }
    public void setAllowChargeCurrentHigh(Double allowChargeCurrentHigh) {
        this.allowChargeCurrentHigh = allowChargeCurrentHigh;
    }
	public Integer getCellsStandardAH() {
        return this.cellsStandardAH;
    }
    public void setCellsStandardAH(Integer cellsStandardAH) {
        this.cellsStandardAH = cellsStandardAH;
    }
	public Integer getCellsRealAH() {
        return this.cellsRealAH;
    }
    public void setCellsRealAH(Integer cellsRealAH) {
        this.cellsRealAH = cellsRealAH;
    }
	public Integer getCellsNum() {
        return this.cellsNum;
    }
    public void setCellsNum(Integer cellsNum) {
        this.cellsNum = cellsNum;
    }
	public Double getSingleProtectVoltageLow() {
        return this.singleProtectVoltageLow;
    }
    public void setSingleProtectVoltageLow(Double singleProtectVoltageLow) {
        this.singleProtectVoltageLow = singleProtectVoltageLow;
    }
	public Integer getFanStatus() {
        return this.fanStatus;
    }
    public void setFanStatus(Integer fanStatus) {
        this.fanStatus = fanStatus;
    }
	public Integer getHeaterStatus() {
        return this.heaterStatus;
    }
    public void setHeaterStatus(Integer heaterStatus) {
        this.heaterStatus = heaterStatus;
    }
	public Integer getRepairSwitch() {
        return this.repairSwitch;
    }
    public void setRepairSwitch(Integer repairSwitch) {
        this.repairSwitch = repairSwitch;
    }
	public Integer getMinusAssistSignal() {
        return this.minusAssistSignal;
    }
    public void setMinusAssistSignal(Integer minusAssistSignal) {
        this.minusAssistSignal = minusAssistSignal;
    }
	public Integer getPlusAssistSignal() {
        return this.plusAssistSignal;
    }
    public void setPlusAssistSignal(Integer plusAssistSignal) {
        this.plusAssistSignal = plusAssistSignal;
    }
	public Integer getPlusChargeContactor() {
        return this.plusChargeContactor;
    }
    public void setPlusChargeContactor(Integer plusChargeContactor) {
        this.plusChargeContactor = plusChargeContactor;
    }
	public Integer getMinusContactor() {
        return this.minusContactor;
    }
    public void setMinusContactor(Integer minusContactor) {
        this.minusContactor = minusContactor;
    }
	public Integer getPlusContactor() {
        return this.plusContactor;
    }
    public void setPlusContactor(Integer plusContactor) {
        this.plusContactor = plusContactor;
    }
	public Integer getVoltageThermalAssistSignal() {
		return voltageThermalAssistSignal;
	}
	public void setVoltageThermalAssistSignal(Integer voltageThermalAssistSignal) {
		this.voltageThermalAssistSignal = voltageThermalAssistSignal;
	}
	public Integer getFastChargeContactor() {
        return this.fastChargeContactor;
    }
    public void setFastChargeContactor(Integer fastChargeContactor) {
        this.fastChargeContactor = fastChargeContactor;
    }
	public Integer getFastChargeAssistSignal() {
        return this.fastChargeAssistSignal;
    }
    public void setFastChargeAssistSignal(Integer fastChargeAssistSignal) {
        this.fastChargeAssistSignal = fastChargeAssistSignal;
    }
	public Integer getBmsProducer() {
        return this.bmsProducer;
    }
    public void setBmsProducer(Integer bmsProducer) {
        this.bmsProducer = bmsProducer;
    }
}
