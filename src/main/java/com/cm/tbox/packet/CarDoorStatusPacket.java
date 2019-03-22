package com.cm.tbox.packet;

import com.cm.common.utils.ByteUtils;

/**
 * 车辆门状态
 * @author yunlu
 *
 */
public class CarDoorStatusPacket{
	/**门状态是否有效 1有效 0无效*/
	private java.lang.Integer doorStatus;
	/**天窗 1开门 0关门*/
	private java.lang.Integer skyLightStatus;
	/**后备箱 1开门 0关门*/
	private java.lang.Integer trunkStatus;
	/**左后门 1开门 0关门*/
	private java.lang.Integer leftBackDoorStatus;
	/**右后门 1开门 0关门*/
	private java.lang.Integer rightBackDoorStatus;
	/**左前门 1开门 0关门*/
	private java.lang.Integer leftFrontDoorStatus;
	/**右前门 1开门 0关门*/
	private java.lang.Integer rightFrontDoorStatus;
	
	public CarDoorStatusPacket() {
		super();
	}
	public CarDoorStatusPacket(Byte dataByte) {
		byte[] doorStatusBytes = ByteUtils.byte2BitArray(dataByte);
		doorStatus = 0xff & doorStatusBytes[7];
		skyLightStatus =0xff & doorStatusBytes[5];
		trunkStatus = 0xff & doorStatusBytes[4];
		leftBackDoorStatus = 0xff & doorStatusBytes[2];
		rightBackDoorStatus = 0xff & doorStatusBytes[3];
		leftFrontDoorStatus = 0xff & doorStatusBytes[0];
		rightFrontDoorStatus = 0xff & doorStatusBytes[1];
	}

	public java.lang.Integer getDoorStatus() {
		return doorStatus;
	}

	public void setDoorStatus(java.lang.Integer doorStatus) {
		this.doorStatus = doorStatus;
	}
	
	public java.lang.Integer getSkyLightStatus() {
		return skyLightStatus;
	}

	public void setSkyLightStatus(java.lang.Integer skyLightStatus) {
		this.skyLightStatus = skyLightStatus;
	}

	public java.lang.Integer getTrunkStatus() {
		return trunkStatus;
	}

	public void setTrunkStatus(java.lang.Integer trunkStatus) {
		this.trunkStatus = trunkStatus;
	}

	public java.lang.Integer getLeftBackDoorStatus() {
		return leftBackDoorStatus;
	}

	public void setLeftBackDoorStatus(java.lang.Integer leftBackDoorStatus) {
		this.leftBackDoorStatus = leftBackDoorStatus;
	}

	public java.lang.Integer getRightBackDoorStatus() {
		return rightBackDoorStatus;
	}

	public void setRightBackDoorStatus(java.lang.Integer rightBackDoorStatus) {
		this.rightBackDoorStatus = rightBackDoorStatus;
	}

	public java.lang.Integer getLeftFrontDoorStatus() {
		return leftFrontDoorStatus;
	}

	public void setLeftFrontDoorStatus(java.lang.Integer leftFrontDoorStatus) {
		this.leftFrontDoorStatus = leftFrontDoorStatus;
	}

	public java.lang.Integer getRightFrontDoorStatus() {
		return rightFrontDoorStatus;
	}

	public void setRightFrontDoorStatus(java.lang.Integer rightFrontDoorStatus) {
		this.rightFrontDoorStatus = rightFrontDoorStatus;
	}
}

