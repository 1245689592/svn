package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.List;

import org.mongodb.morphia.annotations.Property;

import com.cm.tbox.packet.base.BasePacket;

public class SurplusAvailableMileagePacket implements BasePacket,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1890579087541888441L;
	/**
     * canId 为 can_0470 时，携带的是剩余可用里程
     */
    private static final String Mileage_CAN_ID = "can_0470";
    @Property("a")
    /**剩余可用里程*/
    private int mileage;
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
	public static SurplusAvailableMileagePacket valueOfCanDataNewPacket(CanDataNewPacket canDataNewPacket) {
		if (canDataNewPacket != null &&  canDataNewPacket.getData() != null) {
            List<String> dataList = canDataNewPacket.getData().get(Mileage_CAN_ID);
            if (dataList != null && !dataList.isEmpty()) {
                String data=dataList.get(0);
                String mileageString=data.substring(0, 8);
                SurplusAvailableMileagePacket surplusAvailableMileage=new SurplusAvailableMileagePacket();
                if("FFFFFFFF".equals(mileageString)){//无效数据
                    surplusAvailableMileage.setMileage(-1);
                }else{
                	surplusAvailableMileage.setMileage(Integer.parseInt(mileageString,16)*2);	
                }
                return surplusAvailableMileage;
            }

        }
		return null;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

}
