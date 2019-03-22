package com.cm.tbox.packet.gb;

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
 * 可充电储能装置温度
 * @author yunlu
 *
 */
public class ChargeDeviceTempPacket implements BasePacket,Serializable{
	/**子系统个数*/
	@Property("a")
	private Integer sysNum;
	/**可充电储能子系统温度信息列表*/
	@Embedded("b")
	private List<SingleChargeDeviceTempPacket> tempInfos;
	
	@NotSaved
	private int length;

	@Override
	public void build(byte[] bytes) throws Exception {
		sysNum = 0xff & bytes[0];
		int position=1;
		tempInfos=new ArrayList<SingleChargeDeviceTempPacket>(sysNum);
		for(int i=0;i<sysNum;i++){
			SingleChargeDeviceTempPacket temp = new SingleChargeDeviceTempPacket();
			temp.build(Arrays.copyOfRange(bytes, position, bytes.length));
			position+=temp.length();
			tempInfos.add(temp);
		}
		length=position;
	}

	@Override
	public byte[] unbuild() throws Exception {
		byte sysNumByte = sysNum.byteValue();
		length=1;
		byte[] result = new byte[]{sysNumByte};
		for(SingleChargeDeviceTempPacket tempInfo:tempInfos){
			result=ByteUtils.addAll(result,tempInfo.unbuild());
			length+=tempInfo.length();
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

	public List<SingleChargeDeviceTempPacket> getTempInfos() {
		return tempInfos;
	}

	public void setTempInfos(List<SingleChargeDeviceTempPacket> tempInfos) {
		this.tempInfos = tempInfos;
	}
	
}
