package com.cm.mongo.entity;

import java.io.Serializable;
import java.util.Date;

import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.utils.IndexType;

@Indexes({
	@Index(fields = @Field(value="point", type = IndexType.GEO2DSPHERE))
})
public class CarStatusInfoMongo implements Serializable{
	private static final long serialVersionUID = 6628436884865676207L;
	/**1在线 0不在线*/
	private Integer online=1;
	/**最后更新时间*/
	private Date updateTime;
	/**最后定位时间*/
	private Date locateTime;
	/**方向 0-359 正北为0 顺时针*/
	private java.lang.Integer direction;
	/**是否有告警*/
	private Boolean warn;
	/**告警级别 0-3 0无告警*/
	private Integer warnLevel;
	/**剩余电量 百分比*/
	private java.lang.Double soc;
	/**速度 km/h*/
	private java.lang.Double speed;
	/**档位 0-6对应0-6档 14为倒档 15为自动挡*/
	private java.lang.Integer gears;
	/**充电状态 1充电 2放电*/
	private Integer chargeStatus;
	/**剩余充电时间*/
	private Integer dumpChargeTime;
	/**火星坐标*/
	private DisPoint point;
	/**连接来源key*/
	private String server;
	/**剩余可用里程*/
	private Integer surplusAvailableMileage;
	
	public Integer getOnline() {
		return online;
	}
	public void setOnline(Integer online) {
		this.online = online;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public java.lang.Integer getDirection() {
		return direction;
	}
	public void setDirection(java.lang.Integer direction) {
		this.direction = direction;
	}
	public Boolean getWarn() {
		return warn;
	}
	public void setWarn(Boolean warn) {
		this.warn = warn;
	}
	public java.lang.Double getSoc() {
		return soc;
	}
	public void setSoc(java.lang.Double soc) {
		this.soc = soc;
	}
	public java.lang.Double getSpeed() {
		return speed;
	}
	public void setSpeed(java.lang.Double speed) {
		this.speed = speed;
	}
	public java.lang.Integer getGears() {
		return gears;
	}
	public void setGears(java.lang.Integer gears) {
		this.gears = gears;
	}
	public Integer getChargeStatus() {
		return chargeStatus;
	}
	public void setChargeStatus(Integer chargeStatus) {
		this.chargeStatus = chargeStatus;
	}
	public Integer getDumpChargeTime() {
		return dumpChargeTime;
	}
	public void setDumpChargeTime(Integer dumpChargeTime) {
		this.dumpChargeTime = dumpChargeTime;
	}
	public DisPoint getPoint() {
		return point;
	}
	public void setPoint(DisPoint point) {
		this.point = point;
	}
	public Integer getWarnLevel() {
		return warnLevel;
	}
	public void setWarnLevel(Integer warnLevel) {
		this.warnLevel = warnLevel;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public Date getLocateTime() {
		return locateTime;
	}
	public void setLocateTime(Date locateTime) {
		this.locateTime = locateTime;
	}
	public Integer getSurplusAvailableMileage() {
		return surplusAvailableMileage;
	}
	public void setSurplusAvailableMileage(Integer surplusAvailableMileage) {
		this.surplusAvailableMileage = surplusAvailableMileage;
	}
	
}
