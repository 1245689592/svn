package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

/**
 * 参数设置包
 * @author zyl_home
 *
 */
public class SetupPacket implements BasePacket,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2598885753367175708L;

	/**查询时间和返回时间*/
	private Date queryTime;
	
	private int paramsNum=0;
	/**车载终端本地存储时间周期，有效值范围：0～60000（表示  0ms～60000ms）*/
	private Integer localStorageTimePeriod;
	/**正常时， 信息上报时间周期， 有效值范围： 1～600 （表示  1s～600s）*/
	private Integer updateTimePeriod;
	/**出现报警时，信息上报时间周期，有效值范围：0～60000（表示  0ms～60000ms）*/
	private Integer warnTimePeriod;
	/**平台ip*/
	private String ip;
	/**端口*/
	private Integer port;
	/**硬件版本*/
	private String hardwareVersion;
	/**固件版本*/
	private String firmwareVersion;
	/**心跳周期有效值范围：1～240（表示1s～240s）*/
	private Integer heartTimePeriod;
	/**应答超时时间有效值范围：1～600（表示  1s～600s） ，*/
	private Integer terminalAnswerOverTime;
	/**平台应答超时时间，有效值范围：1～600（表示  1s～600s）*/
	private Integer tspAnswerOverTime;
	/**连续三次登入失败后，到下一次登入的间隔时间。有效值范围：1～240 单位分钟*/
	private Integer loginPeriod;
	/**公共平台ip*/
	private String publicIp;
	/**公共平台端口*/
	private Integer publicPort;
	/**是否抽样 1是 2否 */
	private Integer sample;
	/**设置vin码**/
	private String changeVin;

	@Override
	public void build(byte[] bytes) throws Exception {
		
	}
	public static void main(String[] args) {
		String ss="232302fe435332303136313030313030313030303101015911051710331b010003010000ffffffff27fd3b10ff010f00000065020101ffffffffffffffffff3b09050006f1b9940260c21c0601ff00000100000001ffff01ffff07000007ffff0000000008010127fd3b100078000178000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000ffff000013880bb80bb80bb80bb803e8e418e4189c189c183a9803e803e803e8e418e418e418e418e418e418e418e418e418e4189c189c189c18e418090101000c000000000000000000000000";
		byte[] bytes = ByteUtils.hexStringToBytes(ss);
		byte b=bytes[0];
		for(int i=1;i<bytes.length;i++){
			b^=bytes[i];
		}
		System.out.println(b);
		byte[] aa={b};
		System.out.println(ByteUtils.asHex(aa));
	}
	@Override
	public byte[] unbuild() throws Exception {
		byte[] createTimeBytes = ByteUtils.convertDatetime2ByteArr(queryTime==null?new Date():queryTime);
		byte[] result = ArrayUtils.addAll(createTimeBytes, new byte[]{0x00});
		if(localStorageTimePeriod!=null){
			byte[] bytes = ArrayUtils.addAll(new byte[]{0x01}, ByteUtils.intToByteArray2(localStorageTimePeriod));
			result= ArrayUtils.addAll(result,bytes);
			paramsNum++;
		}
		if(updateTimePeriod!=null){
			byte[] bytes = ArrayUtils.addAll(new byte[]{0x02}, ByteUtils.intToByteArray2(updateTimePeriod));
			result= ArrayUtils.addAll(result,bytes);
			paramsNum++;
		}
		if(warnTimePeriod!=null){
			byte[] bytes = ArrayUtils.addAll(new byte[]{0x03}, ByteUtils.intToByteArray2(warnTimePeriod));
			result= ArrayUtils.addAll(result,bytes);
			paramsNum++;
		}
		if(ip!=null && !"".equals(ip.trim())){
			byte[] ipBytes = new byte[6];
			String[] ipStrs = ip.split("\\.");
			for(int i=0;i<ipBytes.length;i++){
				if(ipBytes.length-ipStrs.length>i){
					ipBytes[i]=0x00;
				}else{
					ipBytes[i] = ByteUtils.intToByteArray(Integer.parseInt(ipStrs[i-(ipBytes.length-ipStrs.length)]))[3];
				}
			}
			byte[] bytes = ArrayUtils.addAll(new byte[]{0x04}, ipBytes);
			result= ArrayUtils.addAll(result,bytes);
			paramsNum++;
		}
		if(port!=null){
			byte[] bytes = ArrayUtils.addAll(new byte[]{0x05}, ByteUtils.intToByteArray2(port));
			result= ArrayUtils.addAll(result,bytes);
			paramsNum++;
		}
		if(hardwareVersion!=null && !"".equals(hardwareVersion.trim())){
			byte[] bytes = ArrayUtils.addAll(new byte[]{0x06}, hardwareVersion.getBytes());
			result= ArrayUtils.addAll(result,bytes);
			paramsNum++;
		}
		if(firmwareVersion!=null && !"".equals(firmwareVersion.trim())){
			byte[] bytes = ArrayUtils.addAll(new byte[]{0x07}, firmwareVersion.getBytes());
			result= ArrayUtils.addAll(result,bytes);
			paramsNum++;
		}
		if(heartTimePeriod!=null){
			byte[] bytes = new byte[]{0x08,ByteUtils.intToByteArray(heartTimePeriod)[3]};
			result= ArrayUtils.addAll(result,bytes);
			paramsNum++;
		}
		if(terminalAnswerOverTime!=null){
			byte[] bytes = ArrayUtils.addAll(new byte[]{0x09}, ByteUtils.intToByteArray2(terminalAnswerOverTime));
			result= ArrayUtils.addAll(result,bytes);
			paramsNum++;
		}
		if(tspAnswerOverTime!=null){
			byte[] bytes = ArrayUtils.addAll(new byte[]{0x10}, ByteUtils.intToByteArray2(tspAnswerOverTime));
			result= ArrayUtils.addAll(result,bytes);
			paramsNum++;
		}
		if(changeVin!=null && !changeVin.equals("")){
			result=ByteUtils.addAll(result,0x30,changeVin.getBytes());
			paramsNum++;
		}
		result[6] = ByteUtils.intToByteArray(paramsNum)[3];
		return result;
	}
	public Integer getLocalStorageTimePeriod() {
		return localStorageTimePeriod;
	}

	public void setLocalStorageTimePeriod(Integer localStorageTimePeriod) {
		this.localStorageTimePeriod = localStorageTimePeriod;
	}

	public Integer getUpdateTimePeriod() {
		return updateTimePeriod;
	}

	public void setUpdateTimePeriod(Integer updateTimePeriod) {
		this.updateTimePeriod = updateTimePeriod;
	}

	public Integer getWarnTimePeriod() {
		return warnTimePeriod;
	}

	public void setWarnTimePeriod(Integer warnTimePeriod) {
		this.warnTimePeriod = warnTimePeriod;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
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

	public Integer getHeartTimePeriod() {
		return heartTimePeriod;
	}

	public void setHeartTimePeriod(Integer heartTimePeriod) {
		this.heartTimePeriod = heartTimePeriod;
	}

	public Integer getTerminalAnswerOverTime() {
		return terminalAnswerOverTime;
	}

	public void setTerminalAnswerOverTime(Integer terminalAnswerOverTime) {
		this.terminalAnswerOverTime = terminalAnswerOverTime;
	}

	public Integer getTspAnswerOverTime() {
		return tspAnswerOverTime;
	}

	public void setTspAnswerOverTime(Integer tspAnswerOverTime) {
		this.tspAnswerOverTime = tspAnswerOverTime;
	}

	public Date getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Date queryTime) {
		this.queryTime = queryTime;
	}
	public String getChangeVin() {
		return changeVin;
	}

	public void setChangeVin(String changeVin) {
		this.changeVin = changeVin;
	}
	public String getPublicIp() {
		return publicIp;
	}
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	public Integer getSample() {
		return sample;
	}
	public void setSample(Integer sample) {
		this.sample = sample;
	}
	public Integer getPublicPort() {
		return publicPort;
	}
	public void setPublicPort(Integer publicPort) {
		this.publicPort = publicPort;
	}
	public Integer getLoginPeriod() {
		return loginPeriod;
	}
	public void setLoginPeriod(Integer loginPeriod) {
		this.loginPeriod = loginPeriod;
	}
	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
