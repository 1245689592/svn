package com.cm.tbox.packet.warn;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * dc故障报警标志位
 * @author yunlu
 *
 */
public class DCWarnPacket extends BaseWarnPacket implements BasePacket{
	/** 温度故障(1:异常；0：正常) */
	@Property("a")
	private Integer dcTempStatus;
	/** 输出过流(1:异常；0：正常) */
	@Property("b")
	private Integer dcOutFlowHigh;
	/** 输出过压(1:异常；0：正常)*/
	@Property("c")
	private Integer dcOutVoltageHigh;
	/** 输出欠压(1:异常；0：正常)*/
	@Property("d")
	private Integer dcOutVoltageLow;
	/** 输入过压(1:异常；0：正常)*/
	@Property("e")
	private Integer dcInVoltageHigh;
	/** 输入欠压(1:异常；0：正常)*/
	@Property("f")
	private Integer dcInVoltageLow;
	/** 输出短路(1:异常；0：正常)*/
	@Property("g")
	private Integer dcOutShortOutStatus;
	/** 硬件故障(1:异常；0：正常)*/
	@Property("h")
	private Integer dcHardwareStatus;
	/** 通信超时(1:异常；0：正常)  */
	@Property("i")
	private Integer communicationOvertime;
	/** 用来检验是的标识 */
	@NotSaved
	private byte[] flagBytes;
	@Override
	public void build(byte[] bytes) throws Exception {
		byte[] tempBytes =ByteUtils.byte2BitArray(bytes[0]);
		dcTempStatus= 0xff & tempBytes[0];
		dcOutFlowHigh= 0xff & tempBytes[1];
		dcOutVoltageHigh= 0xff & tempBytes[2];
		dcOutVoltageLow= 0xff & tempBytes[3];
		dcInVoltageHigh= 0xff & tempBytes[4];
		dcInVoltageLow= 0xff & tempBytes[5];
		dcOutShortOutStatus= 0xff & tempBytes[6];
		dcHardwareStatus= 0xff & tempBytes[7];
		tempBytes =ByteUtils.byte2BitArray(bytes[1]);
		communicationOvertime= 0xff & tempBytes[0];
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
	public Integer getDcTempStatus() {
		return dcTempStatus;
	}
	public void setDcTempStatus(Integer dcTempStatus) {
		this.dcTempStatus = dcTempStatus;
	}
	public Integer getDcOutFlowHigh() {
		return dcOutFlowHigh;
	}
	public void setDcOutFlowHigh(Integer dcOutFlowHigh) {
		this.dcOutFlowHigh = dcOutFlowHigh;
	}
	public Integer getDcOutVoltageHigh() {
		return dcOutVoltageHigh;
	}
	public void setDcOutVoltageHigh(Integer dcOutVoltageHigh) {
		this.dcOutVoltageHigh = dcOutVoltageHigh;
	}
	public Integer getDcOutVoltageLow() {
		return dcOutVoltageLow;
	}
	public void setDcOutVoltageLow(Integer dcOutVoltageLow) {
		this.dcOutVoltageLow = dcOutVoltageLow;
	}
	public Integer getDcInVoltageHigh() {
		return dcInVoltageHigh;
	}
	public void setDcInVoltageHigh(Integer dcInVoltageHigh) {
		this.dcInVoltageHigh = dcInVoltageHigh;
	}
	public Integer getDcInVoltageLow() {
		return dcInVoltageLow;
	}
	public void setDcInVoltageLow(Integer dcInVoltageLow) {
		this.dcInVoltageLow = dcInVoltageLow;
	}
	public Integer getDcOutShortOutStatus() {
		return dcOutShortOutStatus;
	}
	public void setDcOutShortOutStatus(Integer dcOutShortOutStatus) {
		this.dcOutShortOutStatus = dcOutShortOutStatus;
	}
	public Integer getDcHardwareStatus() {
		return dcHardwareStatus;
	}
	public void setDcHardwareStatus(Integer dcHardwareStatus) {
		this.dcHardwareStatus = dcHardwareStatus;
	}
	public Integer getCommunicationOvertime() {
		return communicationOvertime;
	}
	public void setCommunicationOvertime(Integer communicationOvertime) {
		this.communicationOvertime = communicationOvertime;
	}
	@Override
	public byte[] bytes() {
		return flagBytes;
	}
	@Override
	public String type() {
		return "f";
	}
}
