package com.cm.tbox.packet;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 可充电储能子系统温度信息
 * @author yunlu
 *
 */
public class SingleChargeDeviceTempPacket implements BasePacket,Serializable{
	/**系统编号*/
	@Property("a")
	private Integer sysNo;
	/**探针总数*/
	@Property("b")
	private Integer probeNum;
	/**温度值*/
	@Property("c")
	private List<Integer> temps;
	
	@NotSaved
	private int length;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		sysNo=0xff & bytes[0];
		probeNum=ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[1],bytes[2]}, 0);
		temps=new ArrayList<Integer>(probeNum);
		for(int i=0;i<probeNum;i++){
			temps.add((0xff & bytes[3+i])-40);
		}
		length=3+probeNum;
	}

	@Override
	public byte[] unbuild() throws Exception {
		byte sysNoByte = sysNo.byteValue();
		byte[] probeNumBytes = ByteUtils.intToByteArray(probeNum, 2);
		byte[] tempsBytes = new byte[probeNum];
		for(int i=0;i<probeNum;i++){
			tempsBytes[i]=(byte)(temps.get(i)+40);
		}
		length=1+2+probeNum;
		return ByteUtils.addAll(sysNoByte,probeNumBytes,tempsBytes);
	}

	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return length;
	}

	public Integer getSysNo() {
		return sysNo;
	}

	public void setSysNo(Integer sysNo) {
		this.sysNo = sysNo;
	}

	public Integer getProbeNum() {
		return probeNum;
	}

	public void setProbeNum(Integer probeNum) {
		this.probeNum = probeNum;
	}

	public List<Integer> getTemps() {
		return temps;
	}

	public void setTemps(List<Integer> temps) {
		this.temps = temps;
	}
	
}
