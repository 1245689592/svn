package com.cm.tbox.packet.command;

import com.cm.tbox.packet.base.BasePacket;
import java.io.Serializable;

/**
 * 控制命令参数封装 工具类
 * @author zyl_home
 *
 */
public class CommandParamsPacket implements BasePacket,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**控制命令类型*/
	private Integer commandType;
	/**授权码是否有效 0无效 1有效*/
	private Integer hasWork;
	/**授权码*/
	private String authCode;
	/**证书*/
	private String cert;
	/**时长 毫秒*/
	private Integer duration;
	/**次数*/
	private Integer times;
	/**间隔 毫秒*/
	private Integer interval;
	/**0断开油电，1连接油电*/
	private Integer oliOutType;
	/**0关闭发动机 1启动发动机*/
	private Integer startUpType;
	/**多少秒内如果没有开门将重新上锁*/
	private Integer seconds;
	/**升级地址完整url*/
	private String updateUrl;
	/**拨号名称*/
	private String dialName;
	/**拨号账号*/
	private String dialUser;
	/**拨号密码*/
	private String dialPassword;
	/**升级服务器地址 host*/
	private String address;
	/**端口*/
	private Integer port;
	/**生产厂商*/
	private String mfrsNo;
	/**硬件版本*/
	private String hardwareVersion;
	/**固件版本*/
	private String firmwareVersion;
	/**连接到升级服务器时限*/
	private Integer timeout;
	/**告警等级1-5*/
	private Integer warnLevel;
	/**报警信息*/
	private String warnMsg;
	/**0开始充电，1停止充电*/
	private Integer chargeType;
	/**0为不预约，1-2880为预约时间 单位min*/
	private Integer orderCharge;
	/**0为不预约，1-2880为预约时间 单位min*/
	private Integer orderOutage;
	/**0关闭近光灯 1打开近光灯 默认0*/
	private Integer turn=0;
	/**自动关闭间隔，有效值范围0-3600 单位秒 */
	private Integer period;
	/**创驱下发指令**/
	private String content;
	/**0关闭空调，1打开空调*/
	private Integer airType;
	/**1正常运行，2.锁车降功率*/
	private Integer downPowerType;
	/**车门1.开锁，0.关锁*/
	private Integer carDoorType;
	/**车窗1.打开，0.关闭*/
	private Integer carWindowType;
	/**车辆控制1.启动，0关闭*/
	private Integer vehicleControlType;
	/**允许行使里程*/
	private Integer allowMileage;
	public Integer getCommandType() {
		return commandType;
	}

	public void setCommandType(Integer commandType) {
		this.commandType = commandType;
	}

	public Integer getHasWork() {
		return hasWork;
	}

	public void setHasWork(Integer hasWork) {
		this.hasWork = hasWork;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public Integer getOliOutType() {
		return oliOutType;
	}

	public void setOliOutType(Integer oliOutType) {
		this.oliOutType = oliOutType;
	}

	public Integer getStartUpType() {
		return startUpType;
	}

	public void setStartUpType(Integer startUpType) {
		this.startUpType = startUpType;
	}

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
	
	public String getUpdateUrl() {
		return updateUrl;
	}

	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}

	public String getDialName() {
		return dialName;
	}

	public void setDialName(String dialName) {
		this.dialName = dialName;
	}

	public String getDialUser() {
		return dialUser;
	}

	public void setDialUser(String dialUser) {
		this.dialUser = dialUser;
	}

	public String getDialPassword() {
		return dialPassword;
	}

	public void setDialPassword(String dialPassword) {
		this.dialPassword = dialPassword;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getMfrsNo() {
		return mfrsNo;
	}

	public void setMfrsNo(String mfrsNo) {
		this.mfrsNo = mfrsNo;
	}

	public String getHardwareVersion() {
		return hardwareVersion;
	}

	public void setHardwareVersion(String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	
	public Integer getWarnLevel() {
		return warnLevel;
	}

	public void setWarnLevel(Integer warnLevel) {
		this.warnLevel = warnLevel;
	}

	public String getWarnMsg() {
		return warnMsg;
	}

	public void setWarnMsg(String warnMsg) {
		this.warnMsg = warnMsg;
	}
	
	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}

	public Integer getOrderCharge() {
		return orderCharge;
	}

	public void setOrderCharge(Integer orderCharge) {
		this.orderCharge = orderCharge;
	}

	public Integer getOrderOutage() {
		return orderOutage;
	}

	public void setOrderOutage(Integer orderOutage) {
		this.orderOutage = orderOutage;
	}
	
	public Integer getTurn() {
		return turn;
	}

	public void setTurn(Integer turn) {
		this.turn = turn;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getAirType() {
		return airType;
	}

	public void setAirType(Integer airType) {
		this.airType = airType;
	}


	public Integer getDownPowerType() {
		return downPowerType;
	}

	public void setDownPowerType(Integer downPowerType) {
		this.downPowerType = downPowerType;
	}

	public Integer getCarDoorType() {
		return carDoorType;
	}

	public void setCarDoorType(Integer carDoorType) {
		this.carDoorType = carDoorType;
	}

	public Integer getCarWindowType() {
		return carWindowType;
	}

	public void setCarWindowType(Integer carWindowType) {
		this.carWindowType = carWindowType;
	}
	public Integer getVehicleControlType() {
		return vehicleControlType;
	}

	public void setVehicleControlType(Integer vehicleControlType) {
		this.vehicleControlType = vehicleControlType;
	}
	
	public Integer getAllowMileage() {
		return allowMileage;
	}

	public void setAllowMileage(Integer allowMileage) {
		this.allowMileage = allowMileage;
	}

	@Override
	public String toString() {
		return  (commandType==null?"":("commandType=" + commandType)) +
				(hasWork==null?"":(" hasWork="+ hasWork)) + 
				(authCode==null?"":(" authCode=" + authCode)) + 
				(cert==null?"":(" cert=" + cert))+ 
				(duration==null?"":(" duration=" + duration)) + 
				(times==null?"":(" times=" + times)) + 
				(interval==null?"":(" interval="+ interval)) + 
				(oliOutType==null?"":(" oliOutType=" + oliOutType)) + 
				(startUpType==null?"":(" startUpType="+ startUpType)) + 
				(updateUrl==null?"":(" updateUrl=" + updateUrl))+ 
				(dialName==null?"":(" dialName=" + dialName))+ 
				(dialUser==null?"":(" dialUser=" + dialUser))+ 
				(dialPassword==null?"":(" dialPassword=" + dialPassword))+ 
				(address==null?"":(" address=" + address))+ 
				(port==null?"":(" port=" + port))+ 
				(mfrsNo==null?"":(" mfrsNo=" + mfrsNo))+ 
				(hardwareVersion==null?"":(" hardwareVersion=" + hardwareVersion))+ 
				(firmwareVersion==null?"":(" firmwareVersion=" + firmwareVersion))+ 
				(timeout==null?"":(" timeout=" + timeout))+
				(warnLevel==null?"":(" warnLevel=" + warnLevel))+
				(warnMsg==null?"":(" warnMsg=" + warnMsg))+
				(chargeType==null?"":(" chargeType=" + chargeType))+
				(orderCharge==null?"":(" orderCharge=" + orderCharge))+
				(orderOutage==null?"":(" orderOutage=" + orderOutage))+
				(turn==null?"":(" turn=" + turn))+
				(period==null?"":(" period=" + period))+
				(content==null?"":(" content=" + content))+
				(airType==null?"":(" airType=" + airType))+
				(downPowerType==null?"":(" downPowerTpye=" + downPowerType))+
				(carDoorType==null?"":(" carDoorType=" + carDoorType))+
				(carWindowType==null?"":(" carWindowType=" + carWindowType))+
				(vehicleControlType==null?"":(" vehicleControlType=" + vehicleControlType))+
				(allowMileage==null?"":(" allowMileage=" + allowMileage));
		
	}

	@Override
	public void build(byte[] bytes) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
