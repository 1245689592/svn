package com.cm.tbox.packet;

import java.io.Serializable;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 华冠扩展数据
 * @author yunlu
 *
 */
public class CarHGExpandPacket implements BasePacket ,Serializable{
	private static final long serialVersionUID = 2038919178254086118L;
	/**预充电电压 有效值范围：0V～1200V，最小计量单元：0.1V*/
	private Double orderChargeVoltage;
	/**剩余充电时间 有效值范围： 0min～65535min，最小计量单元：1min*/
	private Integer dumpTime;
	/**车辆续驶里程 有效值范围： 0km～600km，最小计量单 元：0.1km*/
	private Double range;
	/**电池水温 有效值范围：-50℃～+200℃， 最小计量单元：1℃*/
	private Integer cellWaterTemp;
	/**车辆其他状态信息*/
	private CarOtherStatusPacket otherStatus;
	/**前电机故障等级 0无故障 1警告 2故障 3比较严重故障 4严重故障*/
	private Integer frontMotorFaultLevel;
	/**前电机故障代码  0-65535*/
	private Integer frontMotorFaultCode;
	/**前电机硬件故障代码列表 */
	private String frontMotorHFaultCodes;
	/**后电机故障等级 0无故障 1警告 2故障 3比较严重故障 4严重故障*/
	private Integer backMotorFaultLevel;
	/**后电机故障代码  0-65535*/
	private Integer backMotorFaultCode;
	/**后电机硬件故障代码列表*/
	private String backMotorHFaultCodes;
	/**BMS充电故障等级 1-7*/
	private Integer BMSChargeFaultLevel;
	/**BMS充电故障代码 0-65535*/
	private Integer BMSChargeFaultCode;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		orderChargeVoltage=ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[0],bytes[1]}, 0)*1.0/10;
		dumpTime = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[2],bytes[3]}, 0);
		range = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[4],bytes[5]}, 0)*1.0/10;
		cellWaterTemp = (0xff & bytes[6])-50;
		otherStatus = new CarOtherStatusPacket();
		otherStatus.build(new byte[]{bytes[7],bytes[8]});
		frontMotorFaultLevel = 0xff & bytes[9];
		frontMotorFaultCode = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[10],bytes[11]}, 0);
		frontMotorHFaultCodes = (0xff & bytes[12])+","+
				(0xff & bytes[13])+","+
				(0xff & bytes[14])+","+
				(0xff & bytes[15])+","+
				(0xff & bytes[16])+","+
				(0xff & bytes[17]);
		backMotorFaultLevel = 0xff & bytes[18];
		backMotorFaultCode = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[19],bytes[20]}, 0);
		backMotorHFaultCodes = (0xff & bytes[21])+","+
				(0xff & bytes[22])+","+
				(0xff & bytes[23])+","+
				(0xff & bytes[24])+","+
				(0xff & bytes[25])+","+
				(0xff & bytes[26]);
		BMSChargeFaultLevel = 0xff & bytes[27];
		BMSChargeFaultCode = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[28],bytes[29]}, 0);
	}
	
	@Override
	public byte[] unbuild() throws Exception {
		
		return null;
	}

	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return 40;
	}
	
	public Double getOrderChargeVoltage() {
		return orderChargeVoltage;
	}
	public void setOrderChargeVoltage(Double orderChargeVoltage) {
		this.orderChargeVoltage = orderChargeVoltage;
	}
	public Integer getDumpTime() {
		return dumpTime;
	}
	public void setDumpTime(Integer dumpTime) {
		this.dumpTime = dumpTime;
	}
	public Double getRange() {
		return range;
	}
	public void setRange(Double range) {
		this.range = range;
	}
	public Integer getCellWaterTemp() {
		return cellWaterTemp;
	}
	public void setCellWaterTemp(Integer cellWaterTemp) {
		this.cellWaterTemp = cellWaterTemp;
	}
	public CarOtherStatusPacket getOtherStatus() {
		return otherStatus;
	}
	public void setOtherStatus(CarOtherStatusPacket otherStatus) {
		this.otherStatus = otherStatus;
	}
	public Integer getFrontMotorFaultLevel() {
		return frontMotorFaultLevel;
	}
	public void setFrontMotorFaultLevel(Integer frontMotorFaultLevel) {
		this.frontMotorFaultLevel = frontMotorFaultLevel;
	}
	public Integer getFrontMotorFaultCode() {
		return frontMotorFaultCode;
	}
	public void setFrontMotorFaultCode(Integer frontMotorFaultCode) {
		this.frontMotorFaultCode = frontMotorFaultCode;
	}
	public Integer getBackMotorFaultLevel() {
		return backMotorFaultLevel;
	}
	public void setBackMotorFaultLevel(Integer backMotorFaultLevel) {
		this.backMotorFaultLevel = backMotorFaultLevel;
	}
	public Integer getBackMotorFaultCode() {
		return backMotorFaultCode;
	}
	public void setBackMotorFaultCode(Integer backMotorFaultCode) {
		this.backMotorFaultCode = backMotorFaultCode;
	}
	public Integer getBMSChargeFaultLevel() {
		return BMSChargeFaultLevel;
	}
	public void setBMSChargeFaultLevel(Integer bMSChargeFaultLevel) {
		BMSChargeFaultLevel = bMSChargeFaultLevel;
	}
	public Integer getBMSChargeFaultCode() {
		return BMSChargeFaultCode;
	}
	public void setBMSChargeFaultCode(Integer bMSChargeFaultCode) {
		BMSChargeFaultCode = bMSChargeFaultCode;
	}
	public String getFrontMotorHFaultCodes() {
		return frontMotorHFaultCodes;
	}
	public void setFrontMotorHFaultCodes(String frontMotorHFaultCodes) {
		this.frontMotorHFaultCodes = frontMotorHFaultCodes;
	}
	public String getBackMotorHFaultCodes() {
		return backMotorHFaultCodes;
	}
	public void setBackMotorHFaultCodes(String backMotorHFaultCodes) {
		this.backMotorHFaultCodes = backMotorHFaultCodes;
	}
	
}
