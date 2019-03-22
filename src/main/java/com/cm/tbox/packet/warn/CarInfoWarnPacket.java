package com.cm.tbox.packet.warn;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 整车故障报警标志位
 * @author yunlu
 *
 */
public class CarInfoWarnPacket extends BaseWarnPacket implements BasePacket{
	/** 动力电池故障(1:异常；0：正常)*/
	@Property("a")
	private Integer powerCellStatus;
	/** 电机过热报警(1:异常；0：正常)*/
	@Property("b")
	private Integer motorHot;
	/**  DC故障(1:异常；0：正常) */
	@Property("c")
	private Integer dcStatus;
	/** 整车故障(1:异常；0：正常) */
	@Property("d")
	private Integer carStatus;
	/** 动力电池切断灯(1:异常；0：正常) */
	@Property("e")
	private Integer powerCellCutLight;
	/** 运行准备就绪故障  (1:异常；0：正常)*/
	@Property("f")
	private Integer runReadyStatus;
	/** 档位故障  (1:异常；0：正常)  */
	@Property("g")
	private Integer gearsStatus;
	/**  油门故障  (1:异常；0：正常)*/
	@Property("h")
	private Integer acceleratorStatus;
	/** 电机严重过热报警(1:异常；0：正常) */
	@Property("i")
	private Integer motorHoter;
	/** 预充故障(1:异常；0：正常)*/
	@Property("j")
	private Integer preChargeStatus;
	/** 主接触器故障(1:异常；0：正常) */
	@Property("k")
	private Integer mainContactorStatus;
	/** IGBT故障(1:异常；0：正常)*/
	@Property("l")
	private Integer igbtStatus;
	/** 电机过流(1:异常；0：正常)*/
	@Property("m")
	private Integer motorFlow;
	/** 电机严重过压(1:异常；0：正常)*/
	@Property("n")
	private Integer motorVoltageHigher;
	/** 电机严重欠压(1:异常；0：正常)*/
	@Property("o")
	private Integer motorVoltageLower;
	/** 电机超速(1:异常；0：正常)*/
	@Property("p")
	private Integer motorSpeedOver;
	/** 电机故障(1:异常；0：正常)*/
	@Property("q")
	private Integer motorStatus;
	/** 用来检验是的标识 */
	@NotSaved
	private byte[] flagBytes;
	@Override
	public void build(byte[] bytes) throws Exception {
		byte[] tempBytes =ByteUtils.byte2BitArray(bytes[0]);
		powerCellStatus= 0xff & tempBytes[0];
		motorHot= 0xff & tempBytes[1];
		dcStatus= 0xff & tempBytes[2];
		carStatus= 0xff & tempBytes[3];
		powerCellCutLight= 0xff & tempBytes[4];
		runReadyStatus= 0xff & tempBytes[5];
		gearsStatus= 0xff & tempBytes[6];
		acceleratorStatus= 0xff & tempBytes[7];
		tempBytes =ByteUtils.byte2BitArray(bytes[1]);
		motorHoter= 0xff & tempBytes[0];
		preChargeStatus= 0xff & tempBytes[1];
		mainContactorStatus= 0xff & tempBytes[2];
		igbtStatus= 0xff & tempBytes[3];
		motorFlow= 0xff & tempBytes[4];
		motorVoltageHigher= 0xff & tempBytes[5];
		motorVoltageLower= 0xff & tempBytes[6];
		motorSpeedOver= 0xff & tempBytes[7];
		tempBytes =ByteUtils.byte2BitArray(bytes[2]);
		motorStatus= 0xff & tempBytes[0];
		flagBytes=bytes;
	}
	@Override
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return 3;
	}
	
	public Integer getPowerCellStatus() {
		return powerCellStatus;
	}
	public void setPowerCellStatus(Integer powerCellStatus) {
		this.powerCellStatus = powerCellStatus;
	}
	public Integer getMotorHot() {
		return motorHot;
	}
	public void setMotorHot(Integer motorHot) {
		this.motorHot = motorHot;
	}
	public Integer getDcStatus() {
		return dcStatus;
	}
	public void setDcStatus(Integer dcStatus) {
		this.dcStatus = dcStatus;
	}
	public Integer getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}
	public Integer getPowerCellCutLight() {
		return powerCellCutLight;
	}
	public void setPowerCellCutLight(Integer powerCellCutLight) {
		this.powerCellCutLight = powerCellCutLight;
	}
	public Integer getRunReadyStatus() {
		return runReadyStatus;
	}
	public void setRunReadyStatus(Integer runReadyStatus) {
		this.runReadyStatus = runReadyStatus;
	}
	public Integer getGearsStatus() {
		return gearsStatus;
	}
	public void setGearsStatus(Integer gearsStatus) {
		this.gearsStatus = gearsStatus;
	}
	public Integer getAcceleratorStatus() {
		return acceleratorStatus;
	}
	public void setAcceleratorStatus(Integer acceleratorStatus) {
		this.acceleratorStatus = acceleratorStatus;
	}
	public Integer getMotorHoter() {
		return motorHoter;
	}
	public void setMotorHoter(Integer motorHoter) {
		this.motorHoter = motorHoter;
	}
	public Integer getPreChargeStatus() {
		return preChargeStatus;
	}
	public void setPreChargeStatus(Integer preChargeStatus) {
		this.preChargeStatus = preChargeStatus;
	}
	public Integer getMainContactorStatus() {
		return mainContactorStatus;
	}
	public void setMainContactorStatus(Integer mainContactorStatus) {
		this.mainContactorStatus = mainContactorStatus;
	}
	public Integer getIgbtStatus() {
		return igbtStatus;
	}
	public void setIgbtStatus(Integer igbtStatus) {
		this.igbtStatus = igbtStatus;
	}
	public Integer getMotorFlow() {
		return motorFlow;
	}
	public void setMotorFlow(Integer motorFlow) {
		this.motorFlow = motorFlow;
	}
	public Integer getMotorVoltageHigher() {
		return motorVoltageHigher;
	}
	public void setMotorVoltageHigher(Integer motorVoltageHigher) {
		this.motorVoltageHigher = motorVoltageHigher;
	}
	public Integer getMotorVoltageLower() {
		return motorVoltageLower;
	}
	public void setMotorVoltageLower(Integer motorVoltageLower) {
		this.motorVoltageLower = motorVoltageLower;
	}
	public Integer getMotorSpeedOver() {
		return motorSpeedOver;
	}
	public void setMotorSpeedOver(Integer motorSpeedOver) {
		this.motorSpeedOver = motorSpeedOver;
	}
	public Integer getMotorStatus() {
		return motorStatus;
	}
	public void setMotorStatus(Integer motorStatus) {
		this.motorStatus = motorStatus;
	}
	@Override
	public byte[] bytes() {
		return flagBytes;
	}
	@Override
	public String type() {
		return "c";
	}
	
	
}
