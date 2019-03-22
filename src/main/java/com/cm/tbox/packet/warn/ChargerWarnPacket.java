package com.cm.tbox.packet.warn;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 充电机故障报警标志位
 * @author yunlu
 *
 */
public class ChargerWarnPacket extends BaseWarnPacket implements BasePacket{
	/** 硬件故障(1:异常；0：正常)  */
	@Property("a")
	private Integer hardwareStatus;
	/** 通信超时(1:异常；0：正常) */
	@Property("b")
	private Integer communicationOvertime;
	/** 充电机温度过高警告(1:异常；0：正常)*/
	@Property("c")
	private Integer chargerTempHighWarn;
	/** 充电机温度过高保护(1:异常；0：正常)*/
	@Property("d")
	private Integer chargerTempHighProtect;
	/**  输入电压异常(1:异常；0：正常)*/
	@Property("e")
	private Integer inVoltageStatus;
	/** 过流(1:异常；0：正常) */
	@Property("f")
	private Integer overFlow;
	/** 漏电(1:异常；0：正常) */
	@Property("g")
	private Integer leakage;
	/** 电池电极反接(1:异常；0：正常)*/
	@Property("h")
	private Integer cellElectrodeReverse;
	/** 用来检验是的标识 */
	@NotSaved
	private byte[] flagBytes;
	@Override
	public void build(byte[] bytes) throws Exception {
		byte[] tempBytes =ByteUtils.byte2BitArray(bytes[0]);
		hardwareStatus= 0xff & tempBytes[0];
		communicationOvertime= 0xff & tempBytes[1];
		chargerTempHighWarn= 0xff & tempBytes[2];
		chargerTempHighProtect= 0xff & tempBytes[3];
		inVoltageStatus= 0xff & tempBytes[4];
		overFlow= 0xff & tempBytes[5];
		leakage= 0xff & tempBytes[6];
		cellElectrodeReverse= 0xff & tempBytes[7];
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
		return 1;
	}
	
	public Integer getHardwareStatus() {
		return hardwareStatus;
	}
	public void setHardwareStatus(Integer hardwareStatus) {
		this.hardwareStatus = hardwareStatus;
	}
	public Integer getCommunicationOvertime() {
		return communicationOvertime;
	}
	public void setCommunicationOvertime(Integer communicationOvertime) {
		this.communicationOvertime = communicationOvertime;
	}
	public Integer getChargerTempHighWarn() {
		return chargerTempHighWarn;
	}
	public void setChargerTempHighWarn(Integer chargerTempHighWarn) {
		this.chargerTempHighWarn = chargerTempHighWarn;
	}
	public Integer getChargerTempHighProtect() {
		return chargerTempHighProtect;
	}
	public void setChargerTempHighProtect(Integer chargerTempHighProtect) {
		this.chargerTempHighProtect = chargerTempHighProtect;
	}
	public Integer getInVoltageStatus() {
		return inVoltageStatus;
	}
	public void setInVoltageStatus(Integer inVoltageStatus) {
		this.inVoltageStatus = inVoltageStatus;
	}
	public Integer getOverFlow() {
		return overFlow;
	}
	public void setOverFlow(Integer overFlow) {
		this.overFlow = overFlow;
	}
	public Integer getLeakage() {
		return leakage;
	}
	public void setLeakage(Integer leakage) {
		this.leakage = leakage;
	}
	public Integer getCellElectrodeReverse() {
		return cellElectrodeReverse;
	}
	public void setCellElectrodeReverse(Integer cellElectrodeReverse) {
		this.cellElectrodeReverse = cellElectrodeReverse;
	}
	@Override
	public byte[] bytes() {
		return flagBytes;
	}
	@Override
	public String type() {
		return "e";
	}
}
