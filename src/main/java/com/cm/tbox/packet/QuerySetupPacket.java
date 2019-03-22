package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 参数查询包和响应包
 * @author zyl_home
 *
 */
public class QuerySetupPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = 5020383637240038379L;

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
	/**车辆其他状态查询*/
	private CarOtherStatusPacket otherStatusPacket;
	/**连续三次登入失败后，到下一次登入的间隔时间。有效值范围：1～240 单位分钟*/
	private Integer loginPeriod;
	/**公共平台ip*/
	private String publicIp;
	/**公共平台端口*/
	private Integer publicPort;
	/**是否抽样 1是 2否 */
	private Integer sample;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		queryTime = ByteUtils.convertByteArrtDatetime(Arrays.copyOfRange(bytes, 0, 6));
		paramsNum = 0xff & bytes[6];
		int publicIpLength=0;
		byte[] oddBytes = Arrays.copyOfRange(bytes, 7, bytes.length);
		w: while(oddBytes.length>0){
			s: switch (0xff & oddBytes[0]) {
				case 0xff & 0x01:
					localStorageTimePeriod = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,oddBytes[1],oddBytes[2]}, 0);
					oddBytes = Arrays.copyOfRange(oddBytes, 3, oddBytes.length);
					break s;
				case 0xff & 0x02:
					updateTimePeriod = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,oddBytes[1],oddBytes[2]}, 0);
					oddBytes = Arrays.copyOfRange(oddBytes, 3, oddBytes.length);
					break s;
				case 0xff & 0x03:
					warnTimePeriod = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,oddBytes[1],oddBytes[2]}, 0);
					oddBytes = Arrays.copyOfRange(oddBytes, 3, oddBytes.length);
					break s;
				case 0xff & 0x04:
					byte[] ipBytes = Arrays.copyOfRange(oddBytes, 1, 7);
					ip="";
					if(ipBytes[0] == 0x00 && ipBytes[1] == 0x00){
						ipBytes = Arrays.copyOfRange(ipBytes, 2, ipBytes.length);
					}
					for(byte ipByte:ipBytes){
						ip=ip+(0xff & ipByte)+".";
					}
					ip=ip.substring(0, ip.length()-1);
					oddBytes = Arrays.copyOfRange(oddBytes, 7, oddBytes.length);
					break s;
				case 0xff & 0x05:
					port = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,oddBytes[1],oddBytes[2]}, 0);
					oddBytes = Arrays.copyOfRange(oddBytes, 3, oddBytes.length);
					break s;
				case 0xff & 0x06:
					hardwareVersion = new String(Arrays.copyOfRange(oddBytes, 1, 6));
					oddBytes = Arrays.copyOfRange(oddBytes, 6, oddBytes.length);
					break s;
				case 0xff & 0x07:
					firmwareVersion = new String(Arrays.copyOfRange(oddBytes, 1, 6));
					oddBytes = Arrays.copyOfRange(oddBytes, 6, oddBytes.length);
					break s;
				case 0xff & 0x08:
					heartTimePeriod = 0xff & oddBytes[1];
					oddBytes = Arrays.copyOfRange(oddBytes, 2, oddBytes.length);
					break s;
				case 0xff & 0x09:
					terminalAnswerOverTime = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,oddBytes[1],oddBytes[2]}, 0);
					oddBytes = Arrays.copyOfRange(oddBytes, 3, oddBytes.length);
					break s;
				case 0xff & 0x0a:
					tspAnswerOverTime = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,oddBytes[1],oddBytes[2]}, 0);
					oddBytes = Arrays.copyOfRange(oddBytes, 3, oddBytes.length);
					break s;
				case 0xff & 0x80:
					otherStatusPacket = new CarOtherStatusPacket();
					otherStatusPacket.build(new byte[]{oddBytes[1],oddBytes[2]});
					oddBytes = Arrays.copyOfRange(oddBytes, 3, oddBytes.length);
					break s;
				case 0xff & 0x0d:
					publicIpLength = 0xff & oddBytes[1];
					oddBytes = Arrays.copyOfRange(oddBytes, 2, oddBytes.length);
					break s;
				case 0xff & 0x0e:
					if(0!=publicIpLength){
						publicIp = new String(Arrays.copyOfRange(oddBytes, 1, 1+publicIpLength));
					}
					oddBytes = Arrays.copyOfRange(oddBytes, 1+publicIpLength, oddBytes.length);
					break s;
				case 0xff & 0x0f:
					publicPort = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,oddBytes[1],oddBytes[2]}, 0);
					oddBytes = Arrays.copyOfRange(oddBytes, 3, oddBytes.length);
					break s;
				case 0xff & 0x10:
					sample = 0xff & oddBytes[1];
					oddBytes = Arrays.copyOfRange(oddBytes, 2, oddBytes.length);
					break s;
				default:
					break w;
			}
		}
	}

	@Override
	public byte[] unbuild() throws Exception {
		byte[] createTimeBytes = ByteUtils.convertDatetime2ByteArr(queryTime==null?new Date():queryTime);
		byte[] result = ArrayUtils.addAll(createTimeBytes, new byte[]{0x0a,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a});
//		byte[] result = ArrayUtils.addAll(createTimeBytes, new byte[]{0x0b,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,(byte)0x80});
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

	public Integer getLoginPeriod() {
		return loginPeriod;
	}

	public void setLoginPeriod(Integer loginPeriod) {
		this.loginPeriod = loginPeriod;
	}

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	public Integer getPublicPort() {
		return publicPort;
	}

	public void setPublicPort(Integer publicPort) {
		this.publicPort = publicPort;
	}

	public Integer getSample() {
		return sample;
	}

	public void setSample(Integer sample) {
		this.sample = sample;
	}

	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
