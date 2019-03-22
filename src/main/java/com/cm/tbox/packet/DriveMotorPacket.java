package com.cm.tbox.packet;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

/**
 * 驱动电机数据包
 * @author yunlu
 *
 */
public class DriveMotorPacket implements BasePacket,Serializable{
	/**驱动电机个数*/
	@Property("a")
	private Integer num;
	/**驱动电机总成信息列表*/
	@Embedded("b")
	private List<DriveMotorInfoPacket> infos;
	/***/
	@NotSaved
	private int length;

	@Override
	public void build(byte[] bytes) throws Exception {
		num=0xff & bytes[0];
		infos=new ArrayList<DriveMotorInfoPacket>(num);
		int position=1;
		for(int i=0;i<num;i++){
			DriveMotorInfoPacket info = new DriveMotorInfoPacket();
			info.build(Arrays.copyOfRange(bytes, position, position+info.length()));
			position+=info.length();
			infos.add(info);
		}
		length=position;
	}

	@Override
	public byte[] unbuild() throws Exception {
		byte numByte = num.byteValue();
		byte[] result = new byte[]{numByte};
		for(int i=0;i<num;i++){
			result=ByteUtils.addAll(result,infos.get(i).unbuild());
		}
		length =result.length;
		return result;
	}
	
	@Override
	public Integer length() throws Exception {
		return length;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public List<DriveMotorInfoPacket> getInfos() {
		return infos;
	}

	public void setInfos(List<DriveMotorInfoPacket> infos) {
		this.infos = infos;
	}
	
}
