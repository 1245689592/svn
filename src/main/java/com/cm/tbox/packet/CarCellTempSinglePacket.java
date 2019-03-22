package com.cm.tbox.packet;

import java.io.Serializable;

import com.cm.tbox.packet.base.BasePacket;

public class CarCellTempSinglePacket implements BasePacket,Serializable{
	private static final long serialVersionUID = -3494337904953219388L;
	private Integer length;
	/**电池包序号*/
	private java.lang.Integer cellsNo;
	/**探针总数*/
	private java.lang.Integer tempProbeNum;
	/**温度列表，有效值-40-125 摄氏度*/
	private java.lang.String tempList;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		cellsNo = 0xff & bytes[0];
		tempProbeNum = 0xff & bytes[1];
		tempList="";
		for(int i=0;i<tempProbeNum;i++){
			tempList = tempList+((0xff & bytes[2+i])-40)+",";
		}
		tempList= tempList.substring(0, tempList.length()-1);
		length=2+tempProbeNum;
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

	public java.lang.Integer getCellsNo() {
		return cellsNo;
	}

	public void setCellsNo(java.lang.Integer cellsNo) {
		this.cellsNo = cellsNo;
	}

	public java.lang.Integer getTempProbeNum() {
		return tempProbeNum;
	}

	public void setTempProbeNum(java.lang.Integer tempProbeNum) {
		this.tempProbeNum = tempProbeNum;
	}

	public java.lang.String getTempList() {
		return tempList;
	}

	public void setTempList(java.lang.String tempList) {
		this.tempList = tempList;
	}
	
}
