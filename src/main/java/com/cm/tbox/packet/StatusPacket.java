package com.cm.tbox.packet;
import java.io.Serializable;
import java.util.Date;
import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
public class StatusPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = -8720996637279079149L;
	/**数据采集时间*/
	private java.util.Date updateTime;
	/**是否通电 1通电 0断电*/
	private java.lang.Integer hasElectricity;
	/**电源 1正常 0异常*/
	private java.lang.Integer powerStatus;
	/**通信 1正常 0异常*/
	private java.lang.Integer communicationStatus;
	/**其他异常 1正常 0异常*/
	private java.lang.Integer otherStatus;
	
	public void build(byte[] bytes) throws Exception {
//		updateTime = ByteUtils.convertByteArrtDatetime(Arrays.copyOfRange(bytes, 0, 6));
		updateTime = new Date();
		byte[] statusBytes = ByteUtils.byte2BitArray(bytes[6]);
		hasElectricity = 0xff & statusBytes[0];
		powerStatus = 0xff & statusBytes[1];
		communicationStatus = 0xff & statusBytes[2];
		otherStatus = 0xff & statusBytes[3];
	}
	
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.Integer getHasElectricity() {
		return hasElectricity;
	}

	public void setHasElectricity(java.lang.Integer hasElectricity) {
		this.hasElectricity = hasElectricity;
	}

	public java.lang.Integer getPowerStatus() {
		return powerStatus;
	}

	public void setPowerStatus(java.lang.Integer powerStatus) {
		this.powerStatus = powerStatus;
	}

	public java.lang.Integer getCommunicationStatus() {
		return communicationStatus;
	}

	public void setCommunicationStatus(java.lang.Integer communicationStatus) {
		this.communicationStatus = communicationStatus;
	}

	public java.lang.Integer getOtherStatus() {
		return otherStatus;
	}

	public void setOtherStatus(java.lang.Integer otherStatus) {
		this.otherStatus = otherStatus;
	}

	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
