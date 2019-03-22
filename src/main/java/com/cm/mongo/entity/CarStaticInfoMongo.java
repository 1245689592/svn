package com.cm.mongo.entity;

import java.io.Serializable;
import java.util.List;

public class CarStaticInfoMongo implements Serializable{
	private static final long serialVersionUID = -8914882515762471400L;
	/**如宝马*/
    private String vehicleBrand;
    /**如宝马X3*/
    private String vehicleSeries;
    /**车牌号*/
    private String plateNo;
    /**终端sn*/
    private String terminalSn;
    /**sim编号*/
    private String simCode;
    /**vin*/
    private String vin;
    /**角色ids*/
	private List<String> roleCode;
	
	public String getVehicleBrand() {
		return vehicleBrand;
	}
	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}
	public String getVehicleSeries() {
		return vehicleSeries;
	}
	public void setVehicleSeries(String vehicleSeries) {
		this.vehicleSeries = vehicleSeries;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public List<String> getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(List<String> roleCode) {
		this.roleCode = roleCode;
	}
	public String getSimCode() {
		return simCode;
	}
	public void setSimCode(String simCode) {
		this.simCode = simCode;
	}
	public String getTerminalSn() {
		return terminalSn;
	}
	public void setTerminalSn(String terminalSn) {
		this.terminalSn = terminalSn;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
}
