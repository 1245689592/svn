package com.cm.mongo.entity;

import java.io.Serializable;
import java.util.Date;

import org.mongodb.morphia.annotations.NotSaved;

public class DisPoint implements Serializable{
	
	private static final long serialVersionUID = -1297850125686682677L;

	private double lon;
	
	private double lat;
	@NotSaved
	private Date updateTime;
	
	public DisPoint() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DisPoint(double lon, double lat) {
		super();
		this.lon = lon;
		this.lat = lat;
	}
	
	public DisPoint(double[] latlon){
		super();
		this.lon = latlon[1];
		this.lat = latlon[0];
	}
	public DisPoint(double lon, double lat,Date date) {
		super();
		this.lon = lon;
		this.lat = lat;
		this.updateTime=date;
	}
	
	public DisPoint(double[] latlon,Date date){
		super();
		this.lon = latlon[1];
		this.lat = latlon[0];
		this.updateTime=date;
	}
	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	

	
}
