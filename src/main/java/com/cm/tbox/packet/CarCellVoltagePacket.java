package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

public class CarCellVoltagePacket implements BasePacket,Serializable{
	private static final long serialVersionUID = 4210491617965748086L;
	private Integer length;
	/**单体蓄电池总数*/
	private java.lang.Integer singleCellNum;
	/**动力蓄电池包总数*/
	private java.lang.Integer cellsNum;
	/**包列表*/
	private List<CarCellVoltageSinglePacket> children;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		singleCellNum = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[0],bytes[1]}, 0);
		cellsNum = 0xff & bytes[2];
		length=3;
		children = new ArrayList<CarCellVoltageSinglePacket>();
		byte[] oddBytes = Arrays.copyOfRange(bytes, 3, bytes.length);
		for(int i =0;i<cellsNum;i++){
			CarCellVoltageSinglePacket single = new CarCellVoltageSinglePacket();
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
	public java.lang.Integer getSingleCellNum() {
		return singleCellNum;
	}
	public void setSingleCellNum(java.lang.Integer singleCellNum) {
		this.singleCellNum = singleCellNum;
	}
	public java.lang.Integer getCellsNum() {
		return cellsNum;
	}
	public void setCellsNum(java.lang.Integer cellsNum) {
		this.cellsNum = cellsNum;
	}
	public List<CarCellVoltageSinglePacket> getChildren() {
		return children;
	}
	public void setChildren(List<CarCellVoltageSinglePacket> children) {
		this.children = children;
	}
	
}
