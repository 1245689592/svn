package com.cm.tbox.packet;

import java.io.Serializable;

import com.cm.tbox.packet.base.BasePacket;
/**
 * gps补充数据
 * @author yunlu
 *
 */
public class CarGPSExpandPacket implements BasePacket,Serializable {
	
	private static final long serialVersionUID = 4112713946895971945L;

	private Integer locationType;
	
	private String  satelliteNos;
	
	private Integer pdop;
	
	private Integer hdop;
	
	private Integer vdop;
	
	private Integer satelliteNum;
	
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
