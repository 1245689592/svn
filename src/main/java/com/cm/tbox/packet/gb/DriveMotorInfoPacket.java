package com.cm.tbox.packet.gb;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import java.io.Serializable;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 驱动电机信息数据包
 * @author yunlu
 *
 */
public class DriveMotorInfoPacket implements BasePacket,Serializable{
	/** 驱动电机顺序号，有效值范围1～253  */
	@Property("a")
	private Integer motorNo; 
	/** 驱动电机状态 0x01：耗电；0x02：发电；0x03：关闭状态；0x04：准备状态“0xFE”表示异常，“0xFF”表示无效。 范围：0至65531*/
	@Property("b")
	private Integer motorStatus; 
	/** 驱动电机控制器温度 “0xFE”表示异常，“0xFF”表示无效 单元：℃ 范围：-40至210*/
	@Property("c")
	private Integer motorControlTemp; 
	/** 驱动电机转速 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：r/min 范围：-20000至45531*/
	@Property("d")
	private Integer motorSpeed; 
	/** 驱动电机转矩 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：N*m 范围：-2000至4553.1*/
	@Property("e")
	private Double motorTorque; 
	/** 驱动电机温度 “0xFE”表示异常，“0xFF”表示无效 单元：℃ 范围：-40至210*/
	@Property("f")
	private Integer motorTemp; 
	/** 电机控制器输入电压 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：V 范围：0至6000*/
	@Property("g")
	private Double motorControlVoltage; 
	/** 电机控制器直流母线电流 “0xFF,0xFE”表示异常，“0xFF,0xFF”表示无效 单元：A 范围：-1000至1000*/
	@Property("h")
	private Double motorControlCurrent; 
	
	@Override
	public void build(byte[] bytes) throws Exception {
		motorNo = 0xff & bytes[0];
		motorStatus = 0xff & bytes[1];
		motorControlTemp = 0xff & bytes[2]-40;
		motorSpeed = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[3],bytes[4]}, 0)-20000;
		motorTorque = ByteUtils.toDouble(bytes[5], bytes[6],10, 2000);
		motorTemp = 0xff & bytes[7]-40;
		motorControlVoltage = ByteUtils.toDouble(bytes[8], bytes[9],10, 0);
		motorControlCurrent = ByteUtils.toDouble(bytes[10], bytes[11],10, 1000);
	}
	@Override
	public byte[] unbuild() throws Exception {
		byte motorNoByte = motorNo.byteValue();
		byte motorStatusByte = motorStatus.byteValue();
		byte motorControlTempByte = motorControlTemp.byteValue();
		byte[] motorSpeedBytes = ByteUtils.intToByteArray(motorSpeed,2);
		byte[] motorTorqueBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(motorTorque,10,2000),2);
		byte motorTempByte = motorTemp.byteValue();
		byte[] motorControlVoltageBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(motorControlVoltage,10,0),2);
		byte[] motorControlCurrentBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(motorControlCurrent,10,1000),2);
		return ByteUtils.addAll(motorNoByte,motorStatusByte,motorControlTempByte,motorSpeedBytes,motorTorqueBytes,motorTempByte,motorControlVoltageBytes,motorControlCurrentBytes);
	}
	@Override
	public Integer length() throws Exception {
		return 12;
	}
	public Integer getMotorNo() {
        return this.motorNo;
    }
    public void setMotorNo(Integer motorNo) {
        this.motorNo = motorNo;
    }
	public Integer getMotorStatus() {
        return this.motorStatus;
    }
    public void setMotorStatus(Integer motorStatus) {
        this.motorStatus = motorStatus;
    }
	public Integer getMotorControlTemp() {
        return this.motorControlTemp;
    }
    public void setMotorControlTemp(Integer motorControlTemp) {
        this.motorControlTemp = motorControlTemp;
    }
	public Integer getMotorSpeed() {
        return this.motorSpeed;
    }
    public void setMotorSpeed(Integer motorSpeed) {
        this.motorSpeed = motorSpeed;
    }
	public Double getMotorTorque() {
        return this.motorTorque;
    }
    public void setMotorTorque(Double motorTorque) {
        this.motorTorque = motorTorque;
    }
	public Integer getMotorTemp() {
        return this.motorTemp;
    }
    public void setMotorTemp(Integer motorTemp) {
        this.motorTemp = motorTemp;
    }
	public Double getMotorControlVoltage() {
        return this.motorControlVoltage;
    }
    public void setMotorControlVoltage(Double motorControlVoltage) {
        this.motorControlVoltage = motorControlVoltage;
    }
	public Double getMotorControlCurrent() {
        return this.motorControlCurrent;
    }
    public void setMotorControlCurrent(Double motorControlCurrent) {
        this.motorControlCurrent = motorControlCurrent;
    }
}
