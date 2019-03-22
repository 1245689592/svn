package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.Arrays;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 电池包
 * @author yunlu
 *
 */
public class CellPacket implements BasePacket ,Serializable{
	private static final long serialVersionUID = -3739055825927002869L;
	/**电池序号*/
	private java.lang.Integer cellIndex;
	/**生产厂商*/
	private java.lang.String mfrsNo;
	/**电池类型*/
	private java.lang.Integer cellType;
	/**额定能量 有效值 0-999.9 单位 kw/h*/
	private java.lang.Double ratedEnergy;
	/**额定电压 有效值 0-999.9 单位 v*/
	private java.lang.Double ratedVoltage;
	/**生产日期*/
	private java.util.Date createTime;
	/**流水号 0-9999*/
	private java.lang.Integer cellSn;
	
	public void build(byte[] bytes) throws Exception {
		cellIndex = 0xff & bytes[0];
		mfrsNo = new String(Arrays.copyOfRange(bytes, 1, 5));
		cellType= 0xff & bytes[5];
		ratedEnergy =ByteUtils.toDouble(bytes[6],bytes[7], 10, 0);
		ratedVoltage = ByteUtils.toDouble(bytes[8],bytes[9], 10, 0);
		createTime = ByteUtils.convertByteArrtDatetime(new byte[]{bytes[10],bytes[11],bytes[12],0x00,0x00,0x00});
		cellSn = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[13],bytes[14]}, 0);
	}
	
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public java.lang.Integer getCellIndex() {
		return cellIndex;
	}

	public void setCellIndex(java.lang.Integer cellIndex) {
		this.cellIndex = cellIndex;
	}

	public java.lang.String getMfrsNo() {
		return mfrsNo;
	}

	public void setMfrsNo(java.lang.String mfrsNo) {
		this.mfrsNo = mfrsNo;
	}

	public java.lang.Integer getCellType() {
		return cellType;
	}

	public void setCellType(java.lang.Integer cellType) {
		this.cellType = cellType;
	}

	public java.lang.Double getRatedEnergy() {
		return ratedEnergy;
	}

	public void setRatedEnergy(java.lang.Double ratedEnergy) {
		this.ratedEnergy = ratedEnergy;
	}

	public java.lang.Double getRatedVoltage() {
		return ratedVoltage;
	}

	public void setRatedVoltage(java.lang.Double ratedVoltage) {
		this.ratedVoltage = ratedVoltage;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.Integer getCellSn() {
		return cellSn;
	}

	public void setCellSn(java.lang.Integer cellSn) {
		this.cellSn = cellSn;
	}

	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
