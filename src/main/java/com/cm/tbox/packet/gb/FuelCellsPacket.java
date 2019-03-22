package com.cm.tbox.packet.gb;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * @author yunlu
 *
 */
public class FuelCellsPacket implements BasePacket,Serializable{
	/** 燃料电池电压 单元：V 范围：0至2000*/
	@Property("a")
	private Double voltage; 
	/** 燃料电池电流 单元：A 范围：0至2000*/
	@Property("b")
	private Double current; 
	/** 燃料消耗率 单元：kg/100km 范围：0至600*/
	@Property("c")
	private Double fuelUse; 
	/** 燃料电池温度探针总数 范围：0至65531*/
	@Property("d")
	private Integer tempProbeNum; 
	/** 探针温度值 单元：℃ 范围：-40至200*/
	@Property("e")
	private List<Integer> probeTempList; 
	/** 氢系统中最高温度 单元：℃ 范围：-40至200*/
	@Property("f")
	private Double hyHighestTemp; 
	/** 氢系统中最高温度探针代号 */
	@Property("g")
	private Integer hyHighestTempProbeNo; 
	/** 氢气最高浓度 单元：ppm 范围：0至60000*/
	@Property("h")
	private Integer hyHighestThickness; 
	/** 氢气最高浓度传感器代号 */
	@Property("i")
	private Integer hyHighestThicknessNo; 
	/** 氢气最高压力 单元：Mpa 范围：0至100*/
	@Property("j")
	private Double hyHighestPressure; 
	/** 氢气最高压力传感器代号 */
	@Property("k")
	private Integer hyHighestPressureNo; 
	/** 高压DC/DC状态 0x01：工作；0x02：断开 */
	@Property("l")
	private Integer dcStatus; 
	
	@NotSaved
	private int length;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		voltage = ByteUtils.toDouble(bytes[0], bytes[1],10, 0);
		current = ByteUtils.toDouble(bytes[2], bytes[3],10, 0);
		fuelUse = ByteUtils.toDouble(bytes[4], bytes[5],100, 0);
		tempProbeNum = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[6],bytes[7]}, 0);
		probeTempList=new ArrayList<Integer>();
		for(int i=0;i<tempProbeNum;i++){
			probeTempList.add((0xff & bytes[8+i])-40);
		}
		int position=8+tempProbeNum;
		hyHighestTemp = ByteUtils.toDouble(bytes[position], bytes[position+1],10, 40);
		hyHighestTempProbeNo = 0xff & bytes[position+2];
		hyHighestThickness = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[position+3],bytes[position+4]}, 0);
		hyHighestThicknessNo = 0xff & bytes[position+5];
		hyHighestPressure = ByteUtils.toDouble(bytes[position+6], bytes[position+7],10, 0);
		hyHighestPressureNo = 0xff & bytes[position+8];
		dcStatus = 0xff & bytes[position+9];
		length=position+10;
	}
	@Override
	public byte[] unbuild() throws Exception {
		byte[] voltageBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(voltage,10,0),2);
		byte[] currentBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(current,10,0),2);
		byte[] fuelUseBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(fuelUse,100,0),2);
		byte[] tempProbeNumBytes = ByteUtils.intToByteArray(tempProbeNum,2);
		byte[] probeTempListByte = new byte[tempProbeNum];
		for(int i=0;i<tempProbeNum;i++){
			probeTempListByte[i]=probeTempList.get(0).byteValue();
		}
		byte[] hyHighestTempBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(hyHighestTemp,10,40),2);
		byte hyHighestTempProbeNoByte = hyHighestTempProbeNo.byteValue();
		byte[] hyHighestThicknessBytes = ByteUtils.intToByteArray(hyHighestThickness,2);
		byte hyHighestThicknessNoByte = hyHighestThicknessNo.byteValue();
		byte[] hyHighestPressureBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(hyHighestPressure,10,0),2);
		byte hyHighestPressureNoByte = hyHighestPressureNo.byteValue();
		byte dcStatusByte = dcStatus.byteValue();
		byte[] result=ByteUtils.addAll(voltageBytes,currentBytes,fuelUseBytes,tempProbeNumBytes,probeTempListByte,hyHighestTempBytes,hyHighestTempProbeNoByte,hyHighestThicknessBytes,hyHighestThicknessNoByte,hyHighestPressureBytes,hyHighestPressureNoByte,dcStatusByte);
		length = result.length;
		return result;
	}
	@Override
	public Integer length() throws Exception {
		return length;
	}
	public Double getVoltage() {
        return this.voltage;
    }
    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }
	public Double getCurrent() {
        return this.current;
    }
    public void setCurrent(Double current) {
        this.current = current;
    }
	public Double getFuelUse() {
        return this.fuelUse;
    }
    public void setFuelUse(Double fuelUse) {
        this.fuelUse = fuelUse;
    }
	public Integer getTempProbeNum() {
        return this.tempProbeNum;
    }
    public void setTempProbeNum(Integer tempProbeNum) {
        this.tempProbeNum = tempProbeNum;
    }
    public List<Integer> getProbeTempList() {
		return probeTempList;
	}
	public void setProbeTempList(List<Integer> probeTempList) {
		this.probeTempList = probeTempList;
	}
	public Double getHyHighestTemp() {
        return this.hyHighestTemp;
    }
    public void setHyHighestTemp(Double hyHighestTemp) {
        this.hyHighestTemp = hyHighestTemp;
    }
	public Integer getHyHighestTempProbeNo() {
        return this.hyHighestTempProbeNo;
    }
    public void setHyHighestTempProbeNo(Integer hyHighestTempProbeNo) {
        this.hyHighestTempProbeNo = hyHighestTempProbeNo;
    }
	public Integer getHyHighestThickness() {
        return this.hyHighestThickness;
    }
    public void setHyHighestThickness(Integer hyHighestThickness) {
        this.hyHighestThickness = hyHighestThickness;
    }
	public Integer getHyHighestThicknessNo() {
        return this.hyHighestThicknessNo;
    }
    public void setHyHighestThicknessNo(Integer hyHighestThicknessNo) {
        this.hyHighestThicknessNo = hyHighestThicknessNo;
    }
	public Double getHyHighestPressure() {
        return this.hyHighestPressure;
    }
    public void setHyHighestPressure(Double hyHighestPressure) {
        this.hyHighestPressure = hyHighestPressure;
    }
	public Integer getHyHighestPressureNo() {
        return this.hyHighestPressureNo;
    }
    public void setHyHighestPressureNo(Integer hyHighestPressureNo) {
        this.hyHighestPressureNo = hyHighestPressureNo;
    }
	public Integer getDcStatus() {
        return this.dcStatus;
    }
    public void setDcStatus(Integer dcStatus) {
        this.dcStatus = dcStatus;
    }
}
