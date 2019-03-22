package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.Arrays;

import com.cm.tbox.packet.base.BasePacket;
/**
 * 0x0b数据
 * @author zyl
 *
 */
public class OtherBPacket implements BasePacket,Serializable{
	
	private byte[] data;

	@Override
	public void build(byte[] bytes) throws Exception {
		data=Arrays.copyOfRange(bytes, 0, 6);
	}

	@Override
	public byte[] unbuild() throws Exception {
		return data;
	}

	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return 6;
	}

}
