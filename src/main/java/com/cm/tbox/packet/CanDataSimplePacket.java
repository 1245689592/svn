package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

public class CanDataSimplePacket implements BasePacket,Serializable{
	/**数据包总长度*/
	private int length;
	/**数据包长度*/
	private int dataLength;
	/**can报文数据*/
	private CanDataNewPacket canData;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		dataLength = ByteUtils.byteArrayToInt(bytes[0],bytes[1]);
		canData=new CanDataNewPacket();
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
	public CanDataNewPacket getCanData() {
		return canData;
	}
	public void setCanData(CanDataNewPacket canData) {
		this.canData = canData;
	}
}
