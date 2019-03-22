package com.cm.tbox.packet;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

/**
 *可充电储能装置电压数据
 * @author yunlu
 *
 */
public class ChargeDeviceVoltagePacket implements BasePacket,Serializable{
	/**子系统个数*/
	@Property("a")
	private Integer sysNum;
	/**可充电储能子系统电压信息列表*/
	@Embedded("b")
	private List<SingleChargeDeviceVoltagePacket> voltageInfos;
	
	@NotSaved
	private int length;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		sysNum = 0xff & bytes[0];
		int position=1;
		voltageInfos=new ArrayList<SingleChargeDeviceVoltagePacket>(sysNum);
		for(int i=0;i<sysNum;i++){
			SingleChargeDeviceVoltagePacket voltage = new SingleChargeDeviceVoltagePacket();
			voltage.build(Arrays.copyOfRange(bytes, position, bytes.length));
			position+=voltage.length();
			voltageInfos.add(voltage);
		}
		length=position;
	}

	@Override
	public byte[] unbuild() throws Exception {
		byte sysNumByte = sysNum.byteValue();
		length=1;
		byte[] result = new byte[]{sysNumByte};
		for(SingleChargeDeviceVoltagePacket voltageInfo:voltageInfos){
			result=ByteUtils.addAll(result,voltageInfo.unbuild());
			length+=voltageInfo.length();
		}
		return result;
	}

	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return length;
	}

	public Integer getSysNum() {
		return sysNum;
	}

	public void setSysNum(Integer sysNum) {
		this.sysNum = sysNum;
	}

	public List<SingleChargeDeviceVoltagePacket> getVoltageInfos() {
		return voltageInfos;
	}

	public void setVoltageInfos(List<SingleChargeDeviceVoltagePacket> voltageInfos) {
		this.voltageInfos = voltageInfos;
	}
	
}
