package com.cm.mongo.entity;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("carInfo")
public class CarSimpleInfoMongo implements Serializable{
	private static final long serialVersionUID = 5197529322330061669L;
	/**vin*/
	@Id
	private String carId;
	/**车辆静态相关数据*/
	private CarStaticInfoMongo staticInfo;
	/**车辆动态相关数据*/
	private CarStatusInfoMongo statusInfo;
	
	public CarStaticInfoMongo getStaticInfo() {
		return staticInfo;
	}
	public void setStaticInfo(CarStaticInfoMongo staticInfo) {
		this.staticInfo = staticInfo;
	}
	public CarStatusInfoMongo getStatusInfo() {
		return statusInfo;
	}
	public void setStatusInfo(CarStatusInfoMongo statusInfo) {
		this.statusInfo = statusInfo;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
}
