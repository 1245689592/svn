package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cm.tbox.packet.base.BasePacket;

/**
 * tbox毫秒级数据
 * @author yunlu
 *
 */
public class CarMSDataItemPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = 3132306353920569662L;

	/**时间周期*/
	private int timePeriod;
	
	private List data;
	
	public CarMSDataItemPacket() {
		super();
	}
	
	
	public CarMSDataItemPacket(int length,int timePeriod) {
		super();
		this.timePeriod=timePeriod*50;
		data=new ArrayList<>(length);
	}
	
	public void put(Object obj){
		data.add(obj);
	}
	
	@Override
	public void build(byte[] bytes) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(int timePeriod) {
		this.timePeriod = timePeriod;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
	
	
}
