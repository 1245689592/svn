package com.cm.tbox.packet.warn;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import org.apache.commons.lang.ArrayUtils;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 动力蓄电池报警标志位
 * @author yunlu
 *
 */
public class PowerCellWarnPacket extends BaseWarnPacket  implements BasePacket{
	/**温度差异报警  1报警 0正常*/
	@Property("a")
	private java.lang.Integer warnTempDiff;
	/**电池极柱高温报警  1报警 0正常*/
	@Property("b")
	private java.lang.Integer warnHighTemp;
	/**动力蓄电池包过压报警  1报警 0正常*/
	@Property("c")
	private java.lang.Integer warnCellsHighVoltage;
	/**动力蓄电池包欠压报警  1报警 0正常*/
	@Property("d")
	private java.lang.Integer warnCellsLowVoltage;
	/**soc低报警  1报警 0正常*/
	@Property("e")
	private java.lang.Integer warnSocLow;
	/**单体蓄电池过压报警  1报警 0正常*/
	@Property("f")
	private java.lang.Integer warnCellHighVoltage;
	/**单体蓄电池欠压报警  1报警 0正常*/
	@Property("g")
	private java.lang.Integer warnCellLowVoltage;
	/**soc过低报警  1报警 0正常*/
	@Property("h")
	private java.lang.Integer warnSocLower;
	/**soc太高报警  1报警 0正常*/
	@Property("i")
	private java.lang.Integer warnSocHigher;
	/**蓄电池不匹配报警  1报警 0正常*/
	@Property("j")
	private java.lang.Integer warnCellsNoMatch;
	/**蓄电池一致性差报警  1报警 0正常*/
	@Property("k")
	private java.lang.Integer warnCellsLowMatch;
	/**绝缘警告 1报警 0正常*/
	@Property("l")
	private java.lang.Integer warnInsulation;
	/** 用来检验是的标识 */
	@NotSaved
	private byte[] flagBytes;
	@Override
	public void build(byte[] bytes) throws Exception {
		byte[] tempBytes =ArrayUtils.addAll(ByteUtils.byte2BitArray(bytes[1]), ByteUtils.byte2BitArray(bytes[0])); 
		warnTempDiff = 0xff & tempBytes[0];
		warnHighTemp = 0xff & tempBytes[1];
		warnCellsHighVoltage = 0xff & tempBytes[2];
		warnCellsLowVoltage = 0xff & tempBytes[3];
		warnSocLow = 0xff & tempBytes[4];
		warnCellHighVoltage = 0xff & tempBytes[5];
		warnCellLowVoltage = 0xff & tempBytes[6];
		warnSocLower = 0xff & tempBytes[7];
		warnSocHigher = 0xff & tempBytes[8];
		warnCellsNoMatch = 0xff & tempBytes[9];
		warnCellsLowMatch = 0xff & tempBytes[10];
		warnInsulation = 0xff & tempBytes[11];
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
		return 2;
	}
	
	public java.lang.Integer getWarnTempDiff() {
		return warnTempDiff;
	}
	public void setWarnTempDiff(java.lang.Integer warnTempDiff) {
		this.warnTempDiff = warnTempDiff;
	}
	public java.lang.Integer getWarnHighTemp() {
		return warnHighTemp;
	}
	public void setWarnHighTemp(java.lang.Integer warnHighTemp) {
		this.warnHighTemp = warnHighTemp;
	}
	public java.lang.Integer getWarnCellsHighVoltage() {
		return warnCellsHighVoltage;
	}
	public void setWarnCellsHighVoltage(java.lang.Integer warnCellsHighVoltage) {
		this.warnCellsHighVoltage = warnCellsHighVoltage;
	}
	public java.lang.Integer getWarnCellsLowVoltage() {
		return warnCellsLowVoltage;
	}
	public void setWarnCellsLowVoltage(java.lang.Integer warnCellsLowVoltage) {
		this.warnCellsLowVoltage = warnCellsLowVoltage;
	}
	public java.lang.Integer getWarnSocLow() {
		return warnSocLow;
	}
	public void setWarnSocLow(java.lang.Integer warnSocLow) {
		this.warnSocLow = warnSocLow;
	}
	public java.lang.Integer getWarnCellHighVoltage() {
		return warnCellHighVoltage;
	}
	public void setWarnCellHighVoltage(java.lang.Integer warnCellHighVoltage) {
		this.warnCellHighVoltage = warnCellHighVoltage;
	}
	public java.lang.Integer getWarnCellLowVoltage() {
		return warnCellLowVoltage;
	}
	public void setWarnCellLowVoltage(java.lang.Integer warnCellLowVoltage) {
		this.warnCellLowVoltage = warnCellLowVoltage;
	}
	public java.lang.Integer getWarnSocLower() {
		return warnSocLower;
	}
	public void setWarnSocLower(java.lang.Integer warnSocLower) {
		this.warnSocLower = warnSocLower;
	}
	public java.lang.Integer getWarnSocHigher() {
		return warnSocHigher;
	}
	public void setWarnSocHigher(java.lang.Integer warnSocHigher) {
		this.warnSocHigher = warnSocHigher;
	}
	public java.lang.Integer getWarnCellsNoMatch() {
		return warnCellsNoMatch;
	}
	public void setWarnCellsNoMatch(java.lang.Integer warnCellsNoMatch) {
		this.warnCellsNoMatch = warnCellsNoMatch;
	}
	public java.lang.Integer getWarnCellsLowMatch() {
		return warnCellsLowMatch;
	}
	public void setWarnCellsLowMatch(java.lang.Integer warnCellsLowMatch) {
		this.warnCellsLowMatch = warnCellsLowMatch;
	}
	public java.lang.Integer getWarnInsulation() {
		return warnInsulation;
	}
	public void setWarnInsulation(java.lang.Integer warnInsulation) {
		this.warnInsulation = warnInsulation;
	}
	@Override
	public byte[] bytes() {
		return flagBytes;
	}
	@Override
	public String type() {
		return "a";
	}
	
}
