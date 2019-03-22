package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.Arrays;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 终端编号包
 * @author yunlu
 *
 */
public class TerminalNoPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = -8190114212308314992L;
	/**生产厂商编号*/
	private String mfrsNo;
	/**终端型号 批次号*/
	private java.lang.String modelNo;
	/**终端流水号*/
	private int terminalFlowId;
	
	public void build(byte[] bytes) throws Exception {
		mfrsNo = new String(Arrays.copyOfRange(bytes, 0, 4));
		modelNo =new String(Arrays.copyOfRange(bytes, 4, 10));
		terminalFlowId = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[10],bytes[11]}, 0);
	}

	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public java.lang.String getModelNo() {
		return modelNo;
	}

	public void setModelNo(java.lang.String modelNo) {
		this.modelNo = modelNo;
	}

	public String getMfrsNo() {
		return mfrsNo;
	}

	public void setMfrsNo(String mfrsNo) {
		this.mfrsNo = mfrsNo;
	}
	
	public int getTerminalFlowId() {
		return terminalFlowId;
	}

	public void setTerminalFlowId(int terminalFlowId) {
		this.terminalFlowId = terminalFlowId;
	}

	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
