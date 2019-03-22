package com.cm.tbox.packet.warn;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 电机报警标志位
 * @author yunlu
 *
 */
public class MotorWarnPacket extends BaseWarnPacket implements BasePacket{
	
	/** 加速过电流(1:异常；0：正常) */
	@Property("a")
	private Integer overCurrentSpeedUp;
	/** 减速过电流(1:异常；0：正常)*/
	@Property("b")
	private Integer overCurrentSpeedDown;
	/** 恒速过电流(1:异常；0：正常)  */
	@Property("c")
	private Integer overCurrentSpeedConstant;
	/** 加速过电压(1:异常；0：正常)*/
	@Property("d")
	private Integer overVoltageSpeedUp;
	/** 减速过电压(1:异常；0：正常)*/
	@Property("e")
	private Integer overVoltageSpeedDown;
	/** 恒速过电压(1:异常；0：正常)*/
	@Property("f")
	private Integer overVoltageSpeedConstant;
	/** 欠压故障(1:异常；0：正常)*/
	@Property("g")
	private Integer lowVoltageStatus;
	/** 驱动器过载(1:异常；0：正常)*/
	@Property("h")
	private Integer driverOverload;
	/** 电机过载(1:异常；0：正常) */
	@Property("i")
	private Integer motorOverload;
	/** 输出缺相(1:异常；0：正常)*/
	@Property("j")
	private Integer outputPhase;
	/** 驱动器过热(1:异常；0：正常)*/
	@Property("k")
	private Integer driverHot;
	/** 驱动器过热预报警(1:异常；0：正常)*/
	@Property("l")
	private Integer driverHotPre;
	/** EEPORM读写故障(1:异常；0：正常)*/
	@Property("m")
	private Integer eepormRWStatus;
	/** 电机对地短路故障(1:异常；0：正常) */
	@Property("n")
	private Integer motorGroundShortOutStatus;
	/** 快速限流故障(1:异常；0：正常)*/
	@Property("o")
	private Integer fastLimitCurrent;
	/** 电机过速度(1:异常；0：正常) */
	@Property("p")
	private Integer motorSpeedOver;
	/** 速度偏差过大(1:异常；0：正常) */
	@Property("q")
	private Integer speedDiffHigh;
	/** 电机过热预报警(1:异常；0：正常)*/
	@Property("r")
	private Integer motorHotPre;
	/**  CAN模块初始化故障(1:异常；0：正常) */
	@Property("s")
	private Integer canInitStatus;
	/** CAN通讯故障(1:异常；0：正常) */
	@Property("t")
	private Integer canCommunicationStatus;
	/** 编码器故障(1:异常；0：正常) */
	@Property("u")
	private Integer encoderStatus;
	/** 电流采样异常(1:异常；0：正常)*/
	@Property("v")
	private Integer currentCollect;
	/** 用来检验是的标识 */
	@NotSaved
	private byte[] flagBytes;
	@Override
	public void build(byte[] bytes) throws Exception {
		byte[] tempBytes =ByteUtils.byte2BitArray(bytes[0]);
		overCurrentSpeedUp= 0xff & tempBytes[0];
		overCurrentSpeedDown= 0xff & tempBytes[1];
		overCurrentSpeedConstant= 0xff & tempBytes[2];
		overVoltageSpeedUp= 0xff & tempBytes[3];
		overVoltageSpeedDown= 0xff & tempBytes[4];
		overVoltageSpeedConstant= 0xff & tempBytes[5];
		lowVoltageStatus= 0xff & tempBytes[6];
		driverOverload= 0xff & tempBytes[7];
		tempBytes =ByteUtils.byte2BitArray(bytes[1]);
		motorOverload= 0xff & tempBytes[0];
		outputPhase= 0xff & tempBytes[1];
		driverHot= 0xff & tempBytes[2];
		driverHotPre= 0xff & tempBytes[3];
		eepormRWStatus= 0xff & tempBytes[4];
		motorGroundShortOutStatus= 0xff & tempBytes[5];
		fastLimitCurrent= 0xff & tempBytes[6];
		motorSpeedOver= 0xff & tempBytes[7];
		tempBytes =ByteUtils.byte2BitArray(bytes[2]);
		speedDiffHigh= 0xff & tempBytes[0];
		motorHotPre= 0xff & tempBytes[1];
		canInitStatus= 0xff & tempBytes[2];
		canCommunicationStatus= 0xff & tempBytes[3];
		encoderStatus= 0xff & tempBytes[4];
		currentCollect= 0xff & tempBytes[5];
		flagBytes=bytes;
	}
	@Override
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer length() throws Exception {
		return 3;
	}
	public Integer getOverCurrentSpeedUp() {
		return overCurrentSpeedUp;
	}
	public void setOverCurrentSpeedUp(Integer overCurrentSpeedUp) {
		this.overCurrentSpeedUp = overCurrentSpeedUp;
	}
	public Integer getOverCurrentSpeedDown() {
		return overCurrentSpeedDown;
	}
	public void setOverCurrentSpeedDown(Integer overCurrentSpeedDown) {
		this.overCurrentSpeedDown = overCurrentSpeedDown;
	}
	public Integer getOverCurrentSpeedConstant() {
		return overCurrentSpeedConstant;
	}
	public void setOverCurrentSpeedConstant(Integer overCurrentSpeedConstant) {
		this.overCurrentSpeedConstant = overCurrentSpeedConstant;
	}
	public Integer getOverVoltageSpeedUp() {
		return overVoltageSpeedUp;
	}
	public void setOverVoltageSpeedUp(Integer overVoltageSpeedUp) {
		this.overVoltageSpeedUp = overVoltageSpeedUp;
	}
	public Integer getOverVoltageSpeedDown() {
		return overVoltageSpeedDown;
	}
	public void setOverVoltageSpeedDown(Integer overVoltageSpeedDown) {
		this.overVoltageSpeedDown = overVoltageSpeedDown;
	}
	public Integer getOverVoltageSpeedConstant() {
		return overVoltageSpeedConstant;
	}
	public void setOverVoltageSpeedConstant(Integer overVoltageSpeedConstant) {
		this.overVoltageSpeedConstant = overVoltageSpeedConstant;
	}
	public Integer getLowVoltageStatus() {
		return lowVoltageStatus;
	}
	public void setLowVoltageStatus(Integer lowVoltageStatus) {
		this.lowVoltageStatus = lowVoltageStatus;
	}
	public Integer getDriverOverload() {
		return driverOverload;
	}
	public void setDriverOverload(Integer driverOverload) {
		this.driverOverload = driverOverload;
	}
	public Integer getMotorOverload() {
		return motorOverload;
	}
	public void setMotorOverload(Integer motorOverload) {
		this.motorOverload = motorOverload;
	}
	public Integer getOutputPhase() {
		return outputPhase;
	}
	public void setOutputPhase(Integer outputPhase) {
		this.outputPhase = outputPhase;
	}
	public Integer getDriverHot() {
		return driverHot;
	}
	public void setDriverHot(Integer driverHot) {
		this.driverHot = driverHot;
	}
	public Integer getDriverHotPre() {
		return driverHotPre;
	}
	public void setDriverHotPre(Integer driverHotPre) {
		this.driverHotPre = driverHotPre;
	}
	public Integer getEepormRWStatus() {
		return eepormRWStatus;
	}
	public void setEepormRWStatus(Integer eepormRWStatus) {
		this.eepormRWStatus = eepormRWStatus;
	}
	public Integer getMotorGroundShortOutStatus() {
		return motorGroundShortOutStatus;
	}
	public void setMotorGroundShortOutStatus(Integer motorGroundShortOutStatus) {
		this.motorGroundShortOutStatus = motorGroundShortOutStatus;
	}
	public Integer getFastLimitCurrent() {
		return fastLimitCurrent;
	}
	public void setFastLimitCurrent(Integer fastLimitCurrent) {
		this.fastLimitCurrent = fastLimitCurrent;
	}
	public Integer getMotorSpeedOver() {
		return motorSpeedOver;
	}
	public void setMotorSpeedOver(Integer motorSpeedOver) {
		this.motorSpeedOver = motorSpeedOver;
	}
	public Integer getSpeedDiffHigh() {
		return speedDiffHigh;
	}
	public void setSpeedDiffHigh(Integer speedDiffHigh) {
		this.speedDiffHigh = speedDiffHigh;
	}
	public Integer getMotorHotPre() {
		return motorHotPre;
	}
	public void setMotorHotPre(Integer motorHotPre) {
		this.motorHotPre = motorHotPre;
	}
	public Integer getCanInitStatus() {
		return canInitStatus;
	}
	public void setCanInitStatus(Integer canInitStatus) {
		this.canInitStatus = canInitStatus;
	}
	public Integer getCanCommunicationStatus() {
		return canCommunicationStatus;
	}
	public void setCanCommunicationStatus(Integer canCommunicationStatus) {
		this.canCommunicationStatus = canCommunicationStatus;
	}
	public Integer getEncoderStatus() {
		return encoderStatus;
	}
	public void setEncoderStatus(Integer encoderStatus) {
		this.encoderStatus = encoderStatus;
	}
	public Integer getCurrentCollect() {
		return currentCollect;
	}
	public void setCurrentCollect(Integer currentCollect) {
		this.currentCollect = currentCollect;
	}
	@Override
	public byte[] bytes() {
		return flagBytes;
	}
	@Override
	public String type() {
		return "d";
	}
}
