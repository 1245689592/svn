package com.cm.tbox.packet.base;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.common.FMPackageConstant;

/**
 * 国标数据包
 * @author yunlu
 *
 */
public class FMPacket {
	/**原始头信息*/
	private byte[] header;
	/**原始数据单元*/
	private byte[] data;
	private byte parity;
	/**解析后的协议头*/
	private FMHeader head;
	/**解析后的数据单元*/
	private BasePacket entity;
	
	public FMPacket(FMHeader header,BasePacket packet){
		super();
		this.head=header;
		this.entity=packet;
	}
	
	public FMPacket(byte[] header, byte[] data, byte parity) {
		super();
		this.header=header;
		this.data=data;
		this.parity=parity;
	}
	
	public boolean checkParity(){
		return parity == mathParity();
	}
	
	public byte mathParity(){
		byte result = header[2];
		for(int i= 3; i <= header.length-1;i++){
			result ^= header[i];
		}
		for(int i= 0; i <= data.length-1;i++){
			result ^= data[i];
		}
		return result;
	}
	
	public void unbuild()throws Exception{
		if(entity==null){
			data=new byte[0];
		}else{
			data = entity.unbuild();
			if(data==null){
				data=new byte[0];
			}
		}
		head.setDataLength(data.length);
		header = head.unBulid();
		parity = mathParity();
	}
	
	public void build()throws Exception{
		//解析头部
		head=FMHeader.bulid(header);
		//解析身体 理论上来说 对方返回的数据应该没有消息体 
//		entity = FMPackageConstant.getUpPacket(head.getCommand()).getInstance(data);
	}
	public byte[] convertBytes(){
		return ByteUtils.addAll(header, data,parity);
	}
	
	public byte getParity() {
		return parity;
	}

	public void setParity(byte parity) {
		this.parity = parity;
	}

	public FMHeader getHead() {
		return head;
	}

	public void setHead(FMHeader head) {
		this.head = head;
	}

	public BasePacket getEntity() {
		return entity;
	}

	public void setEntity(BasePacket entity) {
		this.entity = entity;
	}
}
