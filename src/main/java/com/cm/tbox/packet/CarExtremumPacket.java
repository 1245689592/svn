package com.cm.tbox.packet;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Property;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

public class CarExtremumPacket implements BasePacket,Serializable{
	
	private static final long serialVersionUID = 3334036852892399240L;
	/**总电压  0-1000V*/
	private java.lang.Double totalVoltage;
	/**总电流  -1000-1000A*/
	private java.lang.Double totalCurrent;
	/**剩余电量 百分比*/
	private java.lang.Double soc;
	/**剩余能量  0-999.9kw h*/
	private java.lang.Double dumpEnergy;
	/**绝缘电子 0-9999kΩ*/
	private java.lang.Integer resistance;
	/** 最高电压电池子系统号 “0xFE”表示异常，“0xFF”表示无效 范围：1至250*/
	@Property("a")
	private Integer highestVoltageCellsNo; 
	/** 最高电压电池单体代号 “0xFE”表示异常，“0xFF”表示无效 范围：1至250*/
	@Property("b")
	private Integer highestVoltageCellNo; 
	/** 电池单体电压最高值 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：V 范围：1至15*/
	@Property("c")
	private Double highestVoltageSingle; 
	/** 最低电压电池子系统号 “0xFE”表示异常，“0xFF”表示无效 范围：1至250*/
	@Property("d")
	private Integer lowestVoltageCellsNo; 
	/** 最低电压电池单体代号 “0xFE”表示异常，“0xFF”表示无效 范围：1至250*/
	@Property("e")
	private Integer lowestVoltageCellNo; 
	/** 电池单体电压最低值 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：V 范围：1至15*/
	@Property("f")
	private Double lowestVoltageSingle; 
	/** 最高温度子系统号 “0xFE”表示异常，“0xFF”表示无效 范围：1至250*/
	@Property("g")
	private Integer highestTempCellsNo; 
	/** 最高温度探针单体代号 “0xFE”表示异常，“0xFF”表示无效 范围：1至250*/
	@Property("h")
	private Integer highestTempProbeNo; 
	/** 最高温度值 “0xFE”表示异常，“0xFF”表示无效 单元：℃ 范围：-40至210*/
	@Property("i")
	private Integer highestTemp; 
	/** 最低温度子系统号 “0xFE”表示异常，“0xFF”表示无效 范围：1至250*/
	@Property("j")
	private Integer lowestTempCellsNo; 
	/** 最低温度探针子系统代号 “0xFE”表示异常，“0xFF”表示无效 范围：1至250*/
	@Property("k")
	private Integer lowestTempProbeNo; 
	/** 最低温度值 “0xFE”表示异常，“0xFF”表示无效 单元：℃ 范围：-40至210*/
	@Property("l")
	private Integer lowestTemp; 
	
	@Override
	public void build(byte[] bytes) throws Exception {
		highestVoltageCellsNo = 0xff & bytes[0];
		highestVoltageCellNo = 0xff & bytes[1];
		highestVoltageSingle = ByteUtils.toDouble(bytes[2],bytes[3], 1000, 0);
		lowestVoltageCellsNo = 0xff & bytes[4];
		lowestVoltageCellNo = 0xff & bytes[5];
		lowestVoltageSingle = ByteUtils.toDouble(bytes[6],bytes[7], 1000, 0);
		highestTempCellsNo = 0xff & bytes[8];
		highestTempProbeNo= 0xff & bytes[9];
		highestTemp = (0xff & bytes[10]) -40;
		lowestTempCellsNo= 0xff & bytes[11];
		lowestTempProbeNo= 0xff & bytes[12];
		lowestTemp =(0xff & bytes[13]) -40;
		totalVoltage = ByteUtils.toDouble(bytes[14],bytes[15], 10, 0);
		totalCurrent = ByteUtils.toDouble(bytes[16],bytes[17], 10, 1000);
		soc = ByteUtils.toDouble(bytes[18],2.5, 0);
		dumpEnergy = ByteUtils.toDouble(bytes[19],bytes[20], 10, 0);
		resistance =  ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[21],bytes[22]}, 0);
	}

	@Override
	public byte[] unbuild() throws Exception {
		byte highestVoltageCellsNoByte = highestVoltageCellsNo.byteValue();
		byte highestVoltageCellNoByte = highestVoltageCellNo.byteValue();
		byte[] highestVoltageSingleBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(highestVoltageSingle,1000,0),2);
		byte lowestVoltageCellsNoByte = lowestVoltageCellsNo.byteValue();
		byte lowestVoltageCellNoByte = lowestVoltageCellNo.byteValue();
		byte[] lowestVoltageSingleBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(lowestVoltageSingle,1000,0),2);
		byte highestTempCellsNoByte = highestTempCellsNo.byteValue();
		byte highestTempProbeNoByte = highestTempProbeNo.byteValue();
		byte highestTempByte = Integer.valueOf(40+highestTemp).byteValue();
		byte lowestTempCellsNoByte = lowestTempCellsNo.byteValue();
		byte lowestTempProbeNoByte = lowestTempProbeNo.byteValue();
		byte lowestTempByte = Integer.valueOf(40+lowestTemp).byteValue();
		return ByteUtils.addAll(highestVoltageCellsNoByte,highestVoltageCellNoByte,highestVoltageSingleBytes,lowestVoltageCellsNoByte,lowestVoltageCellNoByte,lowestVoltageSingleBytes,highestTempCellsNoByte,highestTempProbeNoByte,highestTempByte,lowestTempCellsNoByte,lowestTempProbeNoByte,lowestTempByte);
	}

	@Override
	public Integer length() throws Exception {
		return 28;
	}

	public java.lang.Integer getHighestVoltageCellsNo() {
		return highestVoltageCellsNo;
	}

	public void setHighestVoltageCellsNo(java.lang.Integer highestVoltageCellsNo) {
		this.highestVoltageCellsNo = highestVoltageCellsNo;
	}

	public java.lang.Integer getHighestVoltageCellNo() {
		return highestVoltageCellNo;
	}

	public void setHighestVoltageCellNo(java.lang.Integer highestVoltageCellNo) {
		this.highestVoltageCellNo = highestVoltageCellNo;
	}

	public java.lang.Double getHighestVoltageSingle() {
		return highestVoltageSingle;
	}

	public void setHighestVoltageSingle(java.lang.Double highestVoltageSingle) {
		this.highestVoltageSingle = highestVoltageSingle;
	}

	public java.lang.Integer getLowestVoltageCellsNo() {
		return lowestVoltageCellsNo;
	}

	public void setLowestVoltageCellsNo(java.lang.Integer lowestVoltageCellsNo) {
		this.lowestVoltageCellsNo = lowestVoltageCellsNo;
	}

	public java.lang.Integer getLowestVoltageCellNo() {
		return lowestVoltageCellNo;
	}

	public void setLowestVoltageCellNo(java.lang.Integer lowestVoltageCellNo) {
		this.lowestVoltageCellNo = lowestVoltageCellNo;
	}

	public java.lang.Double getLowestVoltageSingle() {
		return lowestVoltageSingle;
	}

	public void setLowestVoltageSingle(java.lang.Double lowestVoltageSingle) {
		this.lowestVoltageSingle = lowestVoltageSingle;
	}

	public java.lang.Integer getHighestTempCellsNo() {
		return highestTempCellsNo;
	}

	public void setHighestTempCellsNo(java.lang.Integer highestTempCellsNo) {
		this.highestTempCellsNo = highestTempCellsNo;
	}

	public java.lang.Integer getHighestTempProbeNo() {
		return highestTempProbeNo;
	}

	public void setHighestTempProbeNo(java.lang.Integer highestTempProbeNo) {
		this.highestTempProbeNo = highestTempProbeNo;
	}

	public java.lang.Integer getHighestTemp() {
		return highestTemp;
	}

	public void setHighestTemp(java.lang.Integer highestTemp) {
		this.highestTemp = highestTemp;
	}

	public java.lang.Integer getLowestTempCellsNo() {
		return lowestTempCellsNo;
	}

	public void setLowestTempCellsNo(java.lang.Integer lowestTempCellsNo) {
		this.lowestTempCellsNo = lowestTempCellsNo;
	}

	public java.lang.Integer getLowestTempProbeNo() {
		return lowestTempProbeNo;
	}

	public void setLowestTempProbeNo(java.lang.Integer lowestTempProbeNo) {
		this.lowestTempProbeNo = lowestTempProbeNo;
	}

	public java.lang.Integer getLowestTemp() {
		return lowestTemp;
	}

	public void setLowestTemp(java.lang.Integer lowestTemp) {
		this.lowestTemp = lowestTemp;
	}

	public java.lang.Double getTotalVoltage() {
		return totalVoltage;
	}

	public void setTotalVoltage(java.lang.Double totalVoltage) {
		this.totalVoltage = totalVoltage;
	}

	public java.lang.Double getTotalCurrent() {
		return totalCurrent;
	}

	public void setTotalCurrent(java.lang.Double totalCurrent) {
		this.totalCurrent = totalCurrent;
	}

	public java.lang.Double getSoc() {
		return soc;
	}

	public void setSoc(java.lang.Double soc) {
		this.soc = soc;
	}

	public java.lang.Double getDumpEnergy() {
		return dumpEnergy;
	}

	public void setDumpEnergy(java.lang.Double dumpEnergy) {
		this.dumpEnergy = dumpEnergy;
	}

	public java.lang.Integer getResistance() {
		return resistance;
	}

	public void setResistance(java.lang.Integer resistance) {
		this.resistance = resistance;
	}
}
