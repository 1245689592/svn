package com.cm.tbox.packet;

import java.io.Serializable;

import com.cm.common.utils.ByteUtils;

/**
 * 车辆其他状态
 * @author yunlu
 *
 */
public class CarExpandOtherStatusPacket implements Serializable{
	private static final long serialVersionUID = -8984188437581865773L;
	/** 状态是否有效 1有效0无效*/
	private Integer otherStatus;
	/** 车辆类型 1燃油车 0电动车*/
	private Integer carType;
	/** 充电状态  1充电 0未充电*/
	private Integer chargeStatus;
	/**后备箱锁 1开锁 0关锁*/
	private java.lang.Integer trunkLockStatus;
	/**左后门锁 1开锁 0关锁*/
	private java.lang.Integer leftBackDoorLockStatus;
	/**右后门锁 1开锁 0关锁*/
	private java.lang.Integer rightBackDoorLockStatus;
	/**左前门锁 1开锁 0关锁*/
	private java.lang.Integer leftFrontDoorLockStatus;
	/**右前门 锁 1开锁 0关锁*/
	private java.lang.Integer rightFrontDoorLockStatus;
	
	public CarExpandOtherStatusPacket() {
		super();
	}

	public CarExpandOtherStatusPacket(Byte dataByte) {
		byte[] otherStatusBytes = ByteUtils.byte2BitArray(dataByte);
		otherStatus = 0xff & otherStatusBytes[7];
		carType = 0xff & otherStatusBytes[7];
		chargeStatus =0xff & otherStatusBytes[5];
		trunkLockStatus = 0xff & otherStatusBytes[4];
		leftBackDoorLockStatus = 0xff & otherStatusBytes[2];
		rightBackDoorLockStatus = 0xff & otherStatusBytes[3];
		leftFrontDoorLockStatus = 0xff & otherStatusBytes[0];
		rightFrontDoorLockStatus = 0xff & otherStatusBytes[1];
	}

	public Integer getOtherStatus() {
		return otherStatus;
	}

	public void setOtherStatus(Integer otherStatus) {
		this.otherStatus = otherStatus;
	}

	public Integer getCarType() {
		return carType;
	}

	public void setCarType(Integer carType) {
		this.carType = carType;
	}

	public Integer getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(Integer chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public java.lang.Integer getTrunkLockStatus() {
		return trunkLockStatus;
	}

	public void setTrunkLockStatus(java.lang.Integer trunkLockStatus) {
		this.trunkLockStatus = trunkLockStatus;
	}

	public java.lang.Integer getLeftBackDoorLockStatus() {
		return leftBackDoorLockStatus;
	}

	public void setLeftBackDoorLockStatus(java.lang.Integer leftBackDoorLockStatus) {
		this.leftBackDoorLockStatus = leftBackDoorLockStatus;
	}

	public java.lang.Integer getRightBackDoorLockStatus() {
		return rightBackDoorLockStatus;
	}

	public void setRightBackDoorLockStatus(java.lang.Integer rightBackDoorLockStatus) {
		this.rightBackDoorLockStatus = rightBackDoorLockStatus;
	}

	public java.lang.Integer getLeftFrontDoorLockStatus() {
		return leftFrontDoorLockStatus;
	}

	public void setLeftFrontDoorLockStatus(java.lang.Integer leftFrontDoorLockStatus) {
		this.leftFrontDoorLockStatus = leftFrontDoorLockStatus;
	}

	public java.lang.Integer getRightFrontDoorLockStatus() {
		return rightFrontDoorLockStatus;
	}

	public void setRightFrontDoorLockStatus(java.lang.Integer rightFrontDoorLockStatus) {
		this.rightFrontDoorLockStatus = rightFrontDoorLockStatus;
	}
	
	
}