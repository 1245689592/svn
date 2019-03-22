package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

public class CanDataNewPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = -5771730124951249087L;
	/**数据包长度*/
	private int length;
	/**数据帧个数*/
	private Integer num;
	/**can数据*/
	private Map<String,List<String>> data;
	/**胎压数据*/
	
	@Override
	public void build(byte[] bytes) throws Exception {
		num = 0xff & bytes[0];
		int position = 1;
		data=new HashMap<String,List<String>>();
		for(int i=0;i<num;i++){
			String canId ="can_" + ByteUtils.asHex(new byte[]{bytes[position],bytes[position+1]}).toUpperCase();
			List<String> list = data.get(canId);
			if(list==null){
				list = new ArrayList<String>();
			}
			list.add(ByteUtils.asHex(Arrays.copyOfRange(bytes, position+2, position+2+8)).toUpperCase());
			data.put(canId, list);
			position+=2+8;
		}
		length=position;
	}
	@Override
	public byte[] unbuild() throws Exception {
		byte numByte = num.byteValue();
		byte[] canByteArray = new byte[] {numByte};
		for (String key:data.keySet()) {
			byte[] canId =ByteUtils.hexStringToBytes(key.substring(4, key.length()));
			for (String value:data.get(key)) {
				byte[] byte_data = ByteUtils.hexStringToBytes(value);
				canByteArray=ByteUtils.addAll(canByteArray,canId,byte_data);
			}
//			byte[] tempByteArray = new byte[13];
//			int lengthOfData = 0;
//			String val="";
//			for (String value:data.get(key)) {
//				byte[] byte_data = ByteUtils.hexStringToBytes(value);
//				lengthOfData+=byte_data.length;
//				//canByteArray=ByteUtils.addAll(canByteArray,byte_data);
//				val+=value;
//			}
//			//去掉key前面的can_
// 			String keyValue =key.substring(4, key.length());
// 			tempByteArray=ByteUtils.hexStringToBytes(keyValue+ByteUtils.asHex((Byte.valueOf(String.valueOf(lengthOfData))))+val);
// 			System.arraycopy(tempByteArray, 0, canByteArray, position, tempByteArray.length);
// 			position+=tempByteArray.length;
		}
//		byte[] re = ByteUtils.addAll(numByte,canByteArray);
		return  canByteArray ;
	}
	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return length;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Map<String, List<String>> getData() {
		return data;
	}
	public void setData(Map<String, List<String>> data) {
		this.data = data;
	}
}
