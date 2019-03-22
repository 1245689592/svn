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
public class CarLoginPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = 5672386413733264200L;
	/** 数据采集时间 0-已连接 1-未连接  */
	private Date uploadTime; 
	/** 车载终端每登入一次，登入流水号自动加1，从1开始循环累加，最大值为65531，循环周期为天*/
	private Integer flowId; 
	/** iccid SIM卡ICCID号（ICCID应为终端从SIM卡获取的值，不应人为填写或修改） */
	private String iccid; 
	/** 可充电储能子系统数 可充电储能子系统数 范围：0至250*/
	private Integer subSysNum; 
	/** 可充电储能系统编码长度 可充电储能系统编码长度 范围：0至50*/
	private Integer subSysCodeLength; 
	/** 可充电储能系统编码 可充电储能系统编码 */
	private String subSysCode; 
	
	@Override
	public void build(byte[] bytes) throws Exception {
		uploadTime = ByteUtils.convertByteArrtDatetime(Arrays.copyOfRange(bytes, 0,6));
		flowId = ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[6],bytes[7]}, 0);
		iccid = new String(Arrays.copyOfRange(bytes, 8,28));
		subSysNum = 0xff & bytes[28];
		subSysCodeLength = 0xff & bytes[29];
		if(0!=subSysCodeLength){
			StringBuilder builder=new StringBuilder();
			for(int i=30;i<bytes.length;i=i+subSysCodeLength){
				builder.append(new String(Arrays.copyOfRange(bytes, i,i+subSysCodeLength))+",");
			}
			builder.deleteCharAt(builder.length()-1);
			subSysCode=builder.toString();
		}
	}
	@Override
	public byte[] unbuild() throws Exception {
		byte[] uploadTimeBytes = ByteUtils.convertDatetime2ByteArr(uploadTime);
		byte[] flowIdBytes = ByteUtils.intToByteArray(flowId,2);
		byte[] iccidBytes = iccid.getBytes();
		byte subSysNumByte = subSysNum.byteValue();
		byte subSysCodeLengthByte = subSysCodeLength.byteValue();
		byte[] subSysCodeBytes = subSysCode.getBytes();
		return ByteUtils.addAll(uploadTimeBytes,flowIdBytes,iccidBytes,subSysNumByte,subSysCodeLengthByte,subSysCodeBytes);
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
	public String getIccid() {
        return this.iccid;
    }
    public void setIccid(String iccid) {
        this.iccid = iccid;
    }
	public Integer getSubSysNum() {
        return this.subSysNum;
    }
    public void setSubSysNum(Integer subSysNum) {
        this.subSysNum = subSysNum;
    }
	public Integer getSubSysCodeLength() {
        return this.subSysCodeLength;
    }
    public void setSubSysCodeLength(Integer subSysCodeLength) {
        this.subSysCodeLength = subSysCodeLength;
    }
	public String getSubSysCode() {
        return this.subSysCode;
    }
    public void setSubSysCode(String subSysCode) {
        this.subSysCode = subSysCode;
    }
}
