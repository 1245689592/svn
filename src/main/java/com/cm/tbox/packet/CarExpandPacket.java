package com.cm.tbox.packet;

import java.io.Serializable;
import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

public class CarExpandPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = -5151690624920220336L;
	/**剩余电量 百分比*/
	private java.lang.Double dumpEnergy;
	/**续航里程 km 有效值0-6553.4*/
	private java.lang.Double range;
	/**车辆门状态*/
	private CarDoorStatusPacket carDoorStatus;
	/**1点火 0熄火*/
	private java.lang.Integer accStatus;
	/** 蓄电池电压 有效值0-25.4V*/
	private Double storageCellVoltage;
	/** 车速 有效值0-220km/h*/
	private Double speed;
	/** 里程 有效值0-999999.9km*/
	private Double mileage;
	/** 网络信号强度 有效值0-31 0位无信号 31信号最强*/
	private Integer networkStrength;
	/** 车辆其他状态*/
	private CarExpandOtherStatusPacket carOtherStatus;
	
	public void build(byte[] bytes) throws Exception {
		dumpEnergy = ByteUtils.toDouble(bytes[0], 2.5, 0);
		range = ByteUtils.toDouble(bytes[1],bytes[2], 10, 0);
		carDoorStatus=new CarDoorStatusPacket(bytes[3]);
		accStatus = 0xff & bytes[4];
		storageCellVoltage = ByteUtils.toDouble(bytes[5], 10, 0);
		speed=ByteUtils.toDouble(bytes[6],bytes[7], 10, 0);
		mileage=ByteUtils.toDouble(bytes[8],bytes[9],bytes[10],bytes[11],10, 0);
		carOtherStatus=new CarExpandOtherStatusPacket(bytes[12]);
		networkStrength= 0xff & bytes[13];
	}
	
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Integer length() throws Exception {
		return 16;
	}

	public java.lang.Double getDumpEnergy() {
		return dumpEnergy;
	}

	public void setDumpEnergy(java.lang.Double dumpEnergy) {
		this.dumpEnergy = dumpEnergy;
	}

	public java.lang.Double getRange() {
		return range;
	}

	public void setRange(java.lang.Double range) {
		this.range = range;
	}

	public java.lang.Integer getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(java.lang.Integer accStatus) {
		this.accStatus = accStatus;
	}

	public Double getStorageCellVoltage() {
		return storageCellVoltage;
	}

	public void setStorageCellVoltage(Double storageCellVoltage) {
		this.storageCellVoltage = storageCellVoltage;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public Integer getNetworkStrength() {
		return networkStrength;
	}

	public void setNetworkStrength(Integer networkStrength) {
		this.networkStrength = networkStrength;
	}

	public CarDoorStatusPacket getCarDoorStatus() {
		return carDoorStatus;
	}

	public void setCarDoorStatus(CarDoorStatusPacket carDoorStatus) {
		this.carDoorStatus = carDoorStatus;
	}

	public CarExpandOtherStatusPacket getCarOtherStatus() {
		return carOtherStatus;
	}

	public void setCarOtherStatus(CarExpandOtherStatusPacket carOtherStatus) {
		this.carOtherStatus = carOtherStatus;
	}

	
}

