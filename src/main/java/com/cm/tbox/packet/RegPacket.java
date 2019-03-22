package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 注册报文解析类
 * @author yunlu
 *
 */
public class RegPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = 8165832229003537483L;
	/**注册时间*/
	private java.util.Date regTime;
	/**注册流水号*/
	private int regSN;
	/**终端编号*/
	private TerminalNoPacket terminalNo;
	/**车牌号*/
	private java.lang.String plateNumbers;
	/**电池组数量*/
	private int cellNum;
	/**电池组*/
	private List<CellPacket> cells;
	
	public void build(byte[] bytes) throws Exception {
//		regTime = ByteUtils.convertByteArrtDatetime(Arrays.copyOfRange(bytes, 0, 6));
		regTime = new Date();
		regSN = ByteUtils.byteArrayToInt(new byte[]{0x00, 0x00,bytes[6],bytes[7]},0);
		plateNumbers = new String(Arrays.copyOfRange(bytes, 8, 16));
		terminalNo = new TerminalNoPacket();
		terminalNo.build(Arrays.copyOfRange(bytes, 16, 28));
		int cellNum =ByteUtils.byteArrayToInt(new byte[]{0x00, 0x00,0x00,bytes[28]},0);
		cells=new ArrayList<CellPacket>();
		for(int i=0;i<cellNum;i++){
			CellPacket cellPacket = new CellPacket();
			cellPacket.build(Arrays.copyOfRange(bytes, 29+20*i, 29+20*(i+1)));
			cells.add(cellPacket);
		}
	}
	
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public java.util.Date getRegTime() {
		return regTime;
	}
	public void setRegTime(java.util.Date regTime) {
		this.regTime = regTime;
	}
	
	public int getRegSN() {
		return regSN;
	}

	public void setRegSN(int regSN) {
		this.regSN = regSN;
	}

	public TerminalNoPacket getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(TerminalNoPacket terminalNo) {
		this.terminalNo = terminalNo;
	}

	public java.lang.String getPlateNumbers() {
		return plateNumbers;
	}
	public void setPlateNumbers(java.lang.String plateNumbers) {
		this.plateNumbers = plateNumbers;
	}
	public int getCellNum() {
		return cellNum;
	}
	public void setCellNum(int cellNum) {
		this.cellNum = cellNum;
	}
	public List<CellPacket> getCells() {
		return cells;
	}
	public void setCells(List<CellPacket> cells) {
		this.cells = cells;
	}

	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
