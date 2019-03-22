package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * can总线数据包
 * @author yunlu
 *
 */
public class CanDataPacket2 implements BasePacket,Serializable{
	private static final long serialVersionUID = -3598265121578694161L;
	/**数据包总长度*/
	private int length;
	/**数据包长度*/
	private int dataLength;
	/**can报文数据*/
	private CanDataPacket canData;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		dataLength = ByteUtils.byteArrayToInt(bytes[0],bytes[1]);
		canData=new CanDataPacket();
		canData.build(Arrays.copyOfRange(bytes, 2, 2+dataLength));
		length=2+dataLength;
	}
	@Override
	public byte[] unbuild() throws Exception {
		byte[] canDataBytes = canData.unbuild();
		return ByteUtils.addAll(ByteUtils.intToByteArray2(canDataBytes.length),canDataBytes);
	}
	
	@Override
	public Integer length() throws Exception {
		return length;
	}
	public int getDataLength() {
		return dataLength;
	}
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	public CanDataPacket getCanData() {
		return canData;
	}
	public void setCanData(CanDataPacket canData) {
		this.canData = canData;
	}
}
