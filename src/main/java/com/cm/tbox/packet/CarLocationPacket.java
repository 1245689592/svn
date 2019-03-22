package com.cm.tbox.packet;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Property;

import com.cm.common.utils.ByteUtils;
import com.cm.common.utils.LocationUtils;
import com.cm.tbox.packet.base.BasePacket;

public class CarLocationPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = -569952380430979636L;
	/**速度 0-220km/h*/
	private java.lang.Double speed;
	/**方向 0-359 正北为0 顺时针*/
	private java.lang.Integer direction;
	/**0 有效定位 1无效定位*/
	@Property("a")
	private java.lang.Integer status;
	/**0 东经 1西经*/
	@Property("b")
	private java.lang.Integer lonType;
	/**0 北纬 1南纬*/
	@Property("c")
	private java.lang.Integer latType;
	/**经度*/
	@Property("d")
	private java.lang.Double lon;
	/**纬度*/
	@Property("e")
	private java.lang.Double lat;
	
	public void build(byte[] bytes) throws Exception {
		byte[] statusBytes=ByteUtils.byte2BitArray(bytes[0]);
		status = 0xff & statusBytes[0];
		latType = 0xff & statusBytes[1];
		lonType = 0xff & statusBytes[2];
		lon = ByteUtils.toDouble(bytes[1],bytes[2],bytes[3],bytes[4], 1e6, 0);
		lat = ByteUtils.toDouble(bytes[5],bytes[6],bytes[7],bytes[8], 1e6, 0);
		//是否需要处理经纬度的转换？TODO
		double[] lonlat = LocationUtils.transformFromWGSToGCJ(lat, lon);
		lat = lonlat[0];
		lon = lonlat[1];
		speed = ByteUtils.toDouble(bytes[9],bytes[10], 10, 0);
		direction = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[11],bytes[12]}, 0);
	}
	
	public Integer length() throws Exception {
		return 17;
	}
	
	public byte[] unbuild() throws Exception {
		Integer statusInt = 0;
		statusInt+=status;
		statusInt+=latType*2;
		statusInt+=lonType*2*2;
		byte statusByte = statusInt.byteValue();
		double[] latlon = LocationUtils.transformFromGCJToWGS(lat, lon);
		byte[] lonBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(latlon[1],1000000,0), 4);
		byte[] latBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(latlon[0],1000000,0), 4);
		return ByteUtils.addAll(statusByte,lonBytes,latBytes);
	}
	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Double getLon() {
		return lon;
	}

	public void setLon(java.lang.Double lon) {
		this.lon = lon;
	}

	public java.lang.Double getLat() {
		return lat;
	}

	public void setLat(java.lang.Double lat) {
		this.lat = lat;
	}

	public java.lang.Double getSpeed() {
		return speed;
	}

	public void setSpeed(java.lang.Double speed) {
		this.speed = speed;
	}

	public java.lang.Integer getDirection() {
		return direction;
	}

	public void setDirection(java.lang.Integer direction) {
		this.direction = direction;
	}

	public java.lang.Integer getLonType() {
		return lonType;
	}

	public void setLonType(java.lang.Integer lonType) {
		this.lonType = lonType;
	}

	public java.lang.Integer getLatType() {
		return latType;
	}

	public void setLatType(java.lang.Integer latType) {
		this.latType = latType;
	}

}
