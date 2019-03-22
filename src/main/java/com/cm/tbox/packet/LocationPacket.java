package com.cm.tbox.packet;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Property;

import com.cm.common.utils.ByteUtils;
import com.cm.common.utils.LocationUtils;
import com.cm.tbox.packet.base.BasePacket;

/**
 * 位置数据包
 * @author yunlu
 *
 */
public class LocationPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = -3594577639324328409L;
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
	
	@Override
	public void build(byte[] bytes) throws Exception {
		byte[] statusBytes=ByteUtils.byte2BitArray(bytes[0]);
//		status = 0xff & statusBytes[0];
		status = bytes[0]==0?0:1;
		latType = 0xff & statusBytes[1];
		lonType = 0xff & statusBytes[2];
		lon = ByteUtils.toDouble(bytes[1],bytes[2],bytes[3],bytes[4],1000000, 0);
		lat = ByteUtils.toDouble(bytes[5],bytes[6],bytes[7],bytes[8], 1000000, 0);
		//是否需要处理经纬度的转换？TODO
		double[] lonlat = LocationUtils.transformFromWGSToGCJ(lat, lon);
		lat = lonlat[0];
		lon = lonlat[1];
	}

	@Override
	public byte[] unbuild() throws Exception {
		Integer statusInt = 0;
		statusInt+=status;
		statusInt+=latType*2;
		statusInt+=lonType*2*2;
		byte statusByte = statusInt.byteValue();
		byte[] lonBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(lon,1000000,0), 4);
		byte[] latBytes = ByteUtils.intToByteArray(ByteUtils.DoubleToInt(lat,1000000,0), 4);
		return ByteUtils.addAll(statusByte,lonBytes,latBytes);
	}
	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return 9;
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

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
}
