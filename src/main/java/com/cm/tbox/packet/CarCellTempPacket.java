package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

public class CarCellTempPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = -5483637716884686197L;
	private Integer length;
	/**动力蓄电池包温度探针总数*/
	private java.lang.Integer tempProbeNum;
	/**动力蓄电池包总数*/
	private java.lang.Integer cellsNum;
	
	private List<CarCellTempSinglePacket> children;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		tempProbeNum = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[0],bytes[1]}, 0);
		cellsNum = 0xff & bytes[2];
		length=3;
		children = new ArrayList<>();
		byte[] oddBytes = Arrays.copyOfRange(bytes, 3, bytes.length);
		for(int i =0;i<cellsNum;i++){
			CarCellTempSinglePacket single = new CarCellTempSinglePacket();
			single.build(oddBytes);
			children.add(single);
			length += single.length();
			oddBytes = Arrays.copyOfRange(oddBytes, single.length(), oddBytes.length);
		}
	}
	@Override
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer length() throws Exception {
		return length;
	}
	public java.lang.Integer getTempProbeNum() {
		return tempProbeNum;
	}
	public void setTempProbeNum(java.lang.Integer tempProbeNum) {
		this.tempProbeNum = tempProbeNum;
	}
	public java.lang.Integer getCellsNum() {
		return cellsNum;
	}
	public void setCellsNum(java.lang.Integer cellsNum) {
		this.cellsNum = cellsNum;
	}
	public List<CarCellTempSinglePacket> getChildren() {
		return children;
	}
	public void setChildren(List<CarCellTempSinglePacket> children) {
		this.children = children;
	}
	
	
}
