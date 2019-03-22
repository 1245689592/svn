package com.cm.tbox.packet;

import java.io.Serializable;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

public class CarCellVoltageSinglePacket implements BasePacket,Serializable{
	private static final long serialVersionUID = -3476457614168138869L;
	private Integer length;
	/**动力蓄电池包序号*/
	private java.lang.Integer cellsNo;
	/**该包单体蓄电池总数*/
	private java.lang.Integer cellNum;
	/**单体蓄电池电压值列表 有效值0-15V */
	private java.lang.String cellVoltageList;
    
	@Override
	public void build(byte[] bytes) throws Exception {
		cellsNo = 0xff & bytes[0];
		cellNum = 0xff & bytes[1];
		cellVoltageList="";
		for(int i=0;i<cellNum;i++){
			cellVoltageList = cellVoltageList+ByteUtils.toDouble(bytes[2+2*i],bytes[2+2*i+1], 1000, 0)+",";
		}
		cellVoltageList= cellVoltageList.substring(0, cellVoltageList.length()-1);
		length=2+cellNum*2;	
	}

	@Override
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return length;
	}

	public java.lang.Integer getCellsNo() {
		return cellsNo;
	}

	public void setCellsNo(java.lang.Integer cellsNo) {
		this.cellsNo = cellsNo;
	}

	public java.lang.Integer getCellNum() {
		return cellNum;
	}

	public void setCellNum(java.lang.Integer cellNum) {
		this.cellNum = cellNum;
	}

	public java.lang.String getCellVoltageList() {
		return cellVoltageList;
	}

	public void setCellVoltageList(java.lang.String cellVoltageList) {
		this.cellVoltageList = cellVoltageList;
	}
	
}
