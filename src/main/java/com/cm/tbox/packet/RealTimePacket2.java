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
public class RealTimePacket2 implements BasePacket,Serializable{
	private static final long serialVersionUID = 7156899721601736162L;
	/**上报数据时间*/
	@Property("a")
	private Date uploadTime;
	/**详情参考REALTIME_SUBINFO常量枚举类*/
	@Embedded("b")
	private Map<FMPackageConstant.REALTIME_SUBINFO,BasePacket> subInfoPackets;
	
	@NotSaved
	private int length;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		uploadTime = ByteUtils.convertByteArrtDatetime(Arrays.copyOfRange(bytes, 0, 6));
		byte[] oddBytes = Arrays.copyOfRange(bytes, 6, bytes.length);
		subInfoPackets = bulidSubPacket(oddBytes);
	}

	@Override
	public byte[] unbuild() throws Exception {
		byte[] uploadTimeBytes= ByteUtils.convertDatetime2ByteArr(uploadTime);
		byte[] result = uploadTimeBytes;
		for(Entry<FMPackageConstant.REALTIME_SUBINFO,BasePacket> entry:subInfoPackets.entrySet()){
			result=ByteUtils.addAll(result,entry.getKey().type(),entry.getValue().unbuild());
		}
		length=result.length;
		return result;
	}
	
	/**
	 * 迭代获取实时信息下的各种数据包
	 * @param bytes
	 * @return
	 * @throws Exception
	 */
	private static Map<FMPackageConstant.REALTIME_SUBINFO,BasePacket> bulidSubPacket(byte[] oddBytes) throws Exception{
		Map<FMPackageConstant.REALTIME_SUBINFO,BasePacket> subInfoPackets = 
				new HashMap<FMPackageConstant.REALTIME_SUBINFO,BasePacket>();
		while(oddBytes.length>0){
			int type = 0xff & oddBytes[0];
			FMPackageConstant.REALTIME_SUBINFO subInfoPacket=FMPackageConstant.getSubInfo(type);
			int length=0;
			if(subInfoPacket!=null){
				length = subInfoPacket.build(Arrays.copyOfRange(oddBytes, 1, oddBytes.length), subInfoPackets);
			}else{
				length = ByteUtils.byteArrayToInt(oddBytes[1], oddBytes[2]);
				length=2+length;
			}
			oddBytes = Arrays.copyOfRange(oddBytes, length+1,oddBytes.length);
		}
		return subInfoPackets;
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

	public Map<FMPackageConstant.REALTIME_SUBINFO, BasePacket> getSubInfoPackets() {
		return subInfoPackets;
	}

	public void setSubInfoPackets(Map<FMPackageConstant.REALTIME_SUBINFO, BasePacket> subInfoPackets) {
		this.subInfoPackets = subInfoPackets;
	}
	
}
