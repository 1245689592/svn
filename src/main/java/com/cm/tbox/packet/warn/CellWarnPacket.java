package com.cm.tbox.packet.warn;

import java.util.Arrays;

import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.annotations.Property;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 电池报警标志位
 * @author yunlu
 *
 */
public class CellWarnPacket extends BaseWarnPacket implements BasePacket{
	
	/** 故障标志位  0正常 1一级 2二级*/
	private java.lang.Integer warnFlag;
	/** 电池系统警报  0正常 1一级 2二级*/
	private java.lang.Integer warnCellSystem;
	/** 电池总电压过高 (1:异常；0：正常) */
	@Property("a")
	private Integer cellTotalVoltageHigher;
	/** 电池总电压过低 (1:异常；0：正常) */
	@Property("b")
	private Integer cellTotalVoltageLower;
	/** 绝缘状态低 (1:异常；0：正常) */
	@Property("c")
	private Integer insulationLow;
	/** 单体电压过低 (1:异常；0：正常) */
	@Property("d")
	private Integer singleVoltageLower;
	/** 单体电压过高 (1:异常；0：正常) */
	@Property("e")
	private Integer singleVoltageHigher;
	/** 温度过低 (1:异常；0：正常) */
	@Property("f")
	private Integer tempLower;
	/** 温度过高 (1:异常；0：正常) */
	@Property("g")
	private Integer tempHigher;
	/** 外接充电严重过流 (1:异常；0：正常) */
	@Property("h")
	private Integer plugInFlower;
	/** 放电电流严重过大 (1:异常；0：正常) */
	@Property("i")
	private Integer outCurrentHigher;
	/** 单体电池电压差过大 (1:异常；0：正常) */
	@Property("j")
	private Integer singleVoltageDiffHigher;
	/** 温度严重不均衡 (1:异常；0：正常) */
	@Property("k")
	private Integer tempDiffHigher;
	/** 电池总电压高 (1:异常；0：正常) */
	@Property("l")
	private Integer cellTotalVoltageHigh;
	/** 电池总电压低 (1:异常；0：正常) */
	@Property("m")
	private Integer cellTotalVoltageLow;
	/** 绝缘状态太低 (1:异常；0：正常) */
	@Property("n")
	private Integer insulationLower;
	/** 温度低 (1:异常；0：正常) */
	@Property("o")
	private Integer tempLow;
	/** 温度高 (1:异常；0：正常) */
	@Property("p")
	private Integer tempHigh;
	/** 外接充电过流 (1:异常；0：正常) */
	@Property("q")
	private Integer plugInFlow;
	/** 放电电流过大 (1:异常；0：正常) */
	@Property("r")
	private Integer outCurrentHigh;
	/** 单体电池电压差大 (1:异常；0：正常) */
	@Property("s")
	private Integer singleVoltageDiffHigh;
	/** 内网通讯故障(1:异常；0：正常) */
	@Property("t")
	private Integer networkInside;
	/** 电压采集故障(1:异常；0：正常) */
	@Property("u")
	private Integer voltageCollect;
	/** 电流采集故障(1:异常；0：正常) */
	@Property("v")
	private Integer currentCollect;
	/** 单体过充(1:异常；0：正常) */
	@Property("w")
	private Integer singleInOver;
	/** 单体过放(1:异常；0：正常) */
	@Property("x")
	private Integer singleOutOver;
	/** 温升过快(1:异常；0：正常) */
	@Property("y")
	private Integer tempUpFast;
	/** 短路保护(1:异常；0：正常) */
	@Property("z")
	private Integer shortOutProtect;
	/** 用来检验是的标识 */
	@NotSaved
	private byte[] flagBytes;
	@Override
	public void build(byte[] bytes) throws Exception {
		warnFlag=0xff & bytes[0];
		warnCellSystem=0xff & bytes[1];
		if(bytes.length>2){
			byte[] tempBytes =ByteUtils.byte2BitArray(bytes[2]);
			cellTotalVoltageHigher= 0xff & tempBytes[0];
			cellTotalVoltageLower= 0xff & tempBytes[1];
			insulationLow= 0xff & tempBytes[2];
			singleVoltageLower= 0xff & tempBytes[3];
			singleVoltageHigher= 0xff & tempBytes[4];
			tempLower= 0xff & tempBytes[5];
			tempHigher= 0xff & tempBytes[6];
			plugInFlower= 0xff & tempBytes[7];
			tempBytes =ByteUtils.byte2BitArray(bytes[3]);
			outCurrentHigher= 0xff & tempBytes[0];
			singleVoltageDiffHigher= 0xff & tempBytes[1];
			tempDiffHigher= 0xff & tempBytes[2];
			cellTotalVoltageHigh= 0xff & tempBytes[3];
			cellTotalVoltageLow= 0xff & tempBytes[4];
			insulationLower= 0xff & tempBytes[5];
			tempLow= 0xff & tempBytes[6];
			tempHigh= 0xff & tempBytes[7];
			tempBytes =ByteUtils.byte2BitArray(bytes[4]);
			plugInFlow= 0xff & tempBytes[0];
			outCurrentHigh= 0xff & tempBytes[1];
			singleVoltageDiffHigh= 0xff & tempBytes[2];
			networkInside= 0xff & tempBytes[3];
			voltageCollect= 0xff & tempBytes[4];
			currentCollect= 0xff & tempBytes[5];
			singleInOver= 0xff & tempBytes[6];
			singleOutOver= 0xff & tempBytes[7];
			tempBytes =ByteUtils.byte2BitArray(bytes[5]);
			tempUpFast= 0xff & tempBytes[0];
			shortOutProtect= 0xff & tempBytes[1];
		}
		flagBytes=bytes;
	}
	
	
	
	@Override
	public String checkWarn(byte[] bytes) {
		StringBuffer sb=new StringBuffer();
		Integer targetWarnFlag=0xff & bytes[0];
		Integer targetWarnCellSystem=0xff & bytes[1];
		if(targetWarnFlag!=warnFlag){
			sb.append(warnFlag==0?"-":"");
			sb.append(type());
			sb.append("x"+warnFlag+";");
		}
		if(targetWarnCellSystem!=warnCellSystem){
			sb.append(warnCellSystem==0?"-":"");
			sb.append(type());
			sb.append("y"+warnCellSystem+";");
		}
		if(bytes.length==2){
			return sb.toString();
		}else{
			return sb.toString()+checkWarn(Arrays.copyOfRange(bytes, 2, bytes.length),Arrays.copyOfRange(flagBytes, 2, flagBytes.length));
		}
		
	}



	@Override
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return 6;
	}
	
	public java.lang.Integer getWarnFlag() {
		return warnFlag;
	}
	public void setWarnFlag(java.lang.Integer warnFlag) {
		this.warnFlag = warnFlag;
	}
	public java.lang.Integer getWarnCellSystem() {
		return warnCellSystem;
	}
	public void setWarnCellSystem(java.lang.Integer warnCellSystem) {
		this.warnCellSystem = warnCellSystem;
	}
	public Integer getCellTotalVoltageHigher() {
		return cellTotalVoltageHigher;
	}
	public void setCellTotalVoltageHigher(Integer cellTotalVoltageHigher) {
		this.cellTotalVoltageHigher = cellTotalVoltageHigher;
	}
	public Integer getCellTotalVoltageLower() {
		return cellTotalVoltageLower;
	}
	public void setCellTotalVoltageLower(Integer cellTotalVoltageLower) {
		this.cellTotalVoltageLower = cellTotalVoltageLower;
	}
	public Integer getInsulationLow() {
		return insulationLow;
	}
	public void setInsulationLow(Integer insulationLow) {
		this.insulationLow = insulationLow;
	}
	public Integer getSingleVoltageLower() {
		return singleVoltageLower;
	}
	public void setSingleVoltageLower(Integer singleVoltageLower) {
		this.singleVoltageLower = singleVoltageLower;
	}
	public Integer getSingleVoltageHigher() {
		return singleVoltageHigher;
	}
	public void setSingleVoltageHigher(Integer singleVoltageHigher) {
		this.singleVoltageHigher = singleVoltageHigher;
	}
	public Integer getTempLower() {
		return tempLower;
	}
	public void setTempLower(Integer tempLower) {
		this.tempLower = tempLower;
	}
	public Integer getTempHigher() {
		return tempHigher;
	}
	public void setTempHigher(Integer tempHigher) {
		this.tempHigher = tempHigher;
	}
	public Integer getPlugInFlower() {
		return plugInFlower;
	}
	public void setPlugInFlower(Integer plugInFlower) {
		this.plugInFlower = plugInFlower;
	}
	public Integer getOutCurrentHigher() {
		return outCurrentHigher;
	}
	public void setOutCurrentHigher(Integer outCurrentHigher) {
		this.outCurrentHigher = outCurrentHigher;
	}
	public Integer getSingleVoltageDiffHigher() {
		return singleVoltageDiffHigher;
	}
	public void setSingleVoltageDiffHigher(Integer singleVoltageDiffHigher) {
		this.singleVoltageDiffHigher = singleVoltageDiffHigher;
	}
	public Integer getTempDiffHigher() {
		return tempDiffHigher;
	}
	public void setTempDiffHigher(Integer tempDiffHigher) {
		this.tempDiffHigher = tempDiffHigher;
	}
	public Integer getCellTotalVoltageHigh() {
		return cellTotalVoltageHigh;
	}
	public void setCellTotalVoltageHigh(Integer cellTotalVoltageHigh) {
		this.cellTotalVoltageHigh = cellTotalVoltageHigh;
	}
	public Integer getCellTotalVoltageLow() {
		return cellTotalVoltageLow;
	}
	public void setCellTotalVoltageLow(Integer cellTotalVoltageLow) {
		this.cellTotalVoltageLow = cellTotalVoltageLow;
	}
	public Integer getInsulationLower() {
		return insulationLower;
	}
	public void setInsulationLower(Integer insulationLower) {
		this.insulationLower = insulationLower;
	}
	public Integer getTempLow() {
		return tempLow;
	}
	public void setTempLow(Integer tempLow) {
		this.tempLow = tempLow;
	}
	public Integer getTempHigh() {
		return tempHigh;
	}
	public void setTempHigh(Integer tempHigh) {
		this.tempHigh = tempHigh;
	}
	public Integer getPlugInFlow() {
		return plugInFlow;
	}
	public void setPlugInFlow(Integer plugInFlow) {
		this.plugInFlow = plugInFlow;
	}
	public Integer getOutCurrentHigh() {
		return outCurrentHigh;
	}
	public void setOutCurrentHigh(Integer outCurrentHigh) {
		this.outCurrentHigh = outCurrentHigh;
	}
	public Integer getSingleVoltageDiffHigh() {
		return singleVoltageDiffHigh;
	}
	public void setSingleVoltageDiffHigh(Integer singleVoltageDiffHigh) {
		this.singleVoltageDiffHigh = singleVoltageDiffHigh;
	}
	public Integer getNetworkInside() {
		return networkInside;
	}
	public void setNetworkInside(Integer networkInside) {
		this.networkInside = networkInside;
	}
	public Integer getVoltageCollect() {
		return voltageCollect;
	}
	public void setVoltageCollect(Integer voltageCollect) {
		this.voltageCollect = voltageCollect;
	}
	public Integer getCurrentCollect() {
		return currentCollect;
	}
	public void setCurrentCollect(Integer currentCollect) {
		this.currentCollect = currentCollect;
	}
	public Integer getSingleInOver() {
		return singleInOver;
	}
	public void setSingleInOver(Integer singleInOver) {
		this.singleInOver = singleInOver;
	}
	public Integer getSingleOutOver() {
		return singleOutOver;
	}
	public void setSingleOutOver(Integer singleOutOver) {
		this.singleOutOver = singleOutOver;
	}
	public Integer getTempUpFast() {
		return tempUpFast;
	}
	public void setTempUpFast(Integer tempUpFast) {
		this.tempUpFast = tempUpFast;
	}
	public Integer getShortOutProtect() {
		return shortOutProtect;
	}
	public void setShortOutProtect(Integer shortOutProtect) {
		this.shortOutProtect = shortOutProtect;
	}
	@Override
	public byte[] bytes() {
		return flagBytes;
	}
	@Override
	public String type() {
		return "b";
	}
	
}
