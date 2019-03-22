package com.cm.tbox.packet.common;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

import java.io.Serializable;
import java.util.Date;
import java.util.Arrays;
/**
 * 
 * @author yunlu
 *
 */
public class CarLoginOutPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = 1232218880035695954L;
	/** 数据采集时间 0-已连接 1-未连接  */
	private Date uploadTime; 
	/** 登出流水号与当次登入流水号一致*/
	private Integer flowId; 
	
	@Override
	public void build(byte[] bytes) throws Exception {
		uploadTime = ByteUtils.convertByteArrtDatetime(Arrays.copyOfRange(bytes, 0,6));
		flowId = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[6],bytes[7]}, 0);
	}
	@Override
	public byte[] unbuild() throws Exception {
		byte[] uploadTimeBytes = ByteUtils.convertDatetime2ByteArr(uploadTime);
		byte[] flowIdBytes = ByteUtils.intToByteArray(flowId,2);
		return ByteUtils.addAll(uploadTimeBytes,flowIdBytes);
	}
	@Override
	public Integer length() throws Exception {
		return 45;
	}
	public Date getUploadTime() {
        return this.uploadTime;
    }
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
	public Integer getFlowId() {
        return this.flowId;
    }
    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }
}
