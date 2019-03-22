package com.cm.tbox.packet.gb;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import java.io.Serializable;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 发动机数据包
 * @author yunlu
 *
 */
public class MotorPacket implements BasePacket,Serializable{
	/** 发动机状态 0x01：启动状态；0x02：关闭状态，“0xFE”表示异常，“0xFF”表示无效 */
	@Property("a")
	private Integer motorStatus; 
	/** 曲轴转速 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：rpm 范围：0至60000*/
	@Property("b")
	private Integer crankshaftSpeed; 
	/** 燃料消耗率 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：L/100km 范围：0至600*/
	@Property("c")
	private Double fuelUse; 
	
	@Override
	public void build(byte[] bytes) throws Exception {
		motorStatus = 0xff & bytes[0];
		crankshaftSpeed = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[1],bytes[2]}, 0);
		fuelUse = ByteUtils.toDouble(bytes[3], bytes[4],100, 0);
	}
	@Override
	public byte[] unbuild() throws Exception {
		byte motorStatusByte = motorStatus.byteValue();
		byte[] crankshaftSpeedBytes = ByteUtils.intToByteArray(crankshaftSpeed,2);
		byte[] fuelUseBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(fuelUse,100,0),2);
		return ByteUtils.addAll(motorStatusByte,crankshaftSpeedBytes,fuelUseBytes);
	}
	@Override
	public Integer length() throws Exception {
		return 5;
	}
	public Integer getMotorStatus() {
        return this.motorStatus;
    }
    public void setMotorStatus(Integer motorStatus) {
        this.motorStatus = motorStatus;
    }
	public Integer getCrankshaftSpeed() {
        return this.crankshaftSpeed;
    }
    public void setCrankshaftSpeed(Integer crankshaftSpeed) {
        this.crankshaftSpeed = crankshaftSpeed;
    }
	public Double getFuelUse() {
        return this.fuelUse;
    }
    public void setFuelUse(Double fuelUse) {
        this.fuelUse = fuelUse;
    }
}
