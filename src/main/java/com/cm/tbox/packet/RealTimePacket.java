package com.cm.tbox.packet;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.common.FMPackageConstant;
import com.cm.tbox.packet.base.BasePacket;

/**
 * 车辆实时数据包
 * @author yunlu
 *
 */
public class RealTimePacket implements BasePacket,Serializable{
	private static final long serialVersionUID = 7156899721601736162L;
	/**上报数据时间*/
	@Property("a")
	private Date uploadTime;
	/**详情参考REALTIME_SUBINFO常量枚举类*/
	@Embedded("b")
	private Map<Byte,BasePacket> subInfoPackets;
	
	@NotSaved
	private int length;
	
	@Override
	public void build(byte[] bytes) throws Exception {
	}

	@Override
	public byte[] unbuild() throws Exception {
		byte[] uploadTimeBytes= ByteUtils.convertDatetime2ByteArr(uploadTime);
		byte[] result = uploadTimeBytes;
		for(Entry<Byte,BasePacket> entry:subInfoPackets.entrySet()){
			result=ByteUtils.addAll(result,entry.getKey(),entry.getValue().unbuild());
		}
		length=result.length;
		return result;
	}
	
	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return length;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Map<Byte, BasePacket> getSubInfoPackets() {
		return subInfoPackets;
	}

	public void setSubInfoPackets(Map<Byte, BasePacket> subInfoPackets) {
		this.subInfoPackets = subInfoPackets;
	}
	
}
