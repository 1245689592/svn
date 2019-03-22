package com.cm.mongo.entity;

import java.util.Date;

import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.utils.IndexType;
/**
 * 车辆历史数据 原始报文版本
 * @author zyl
 *
 */
@Indexes({
	@Index(fields = {@Field(value="carId",type=IndexType.ASC),@Field(value="updateTime",type=IndexType.DESC)})
})
public class CarInfoHistoryHex {
	@Id
	private String id;
	/**是否为补发数据 0实时 1补发*/
	private int type;
	/**车辆唯一识别码*/
	private String carId;
	/**终端code*/
	private String code;
	/**最后更新时间*/
	private Date updateTime;
	/**服务器接受时间*/
	private Date receiveTime;
	/**报文数据*/
	private byte[] data;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
