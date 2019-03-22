package com.cm.tbox.packet;

import java.io.Serializable;
import java.util.List;

import org.mongodb.morphia.annotations.Property;

import com.cm.tbox.packet.base.BasePacket;
/**
 * 续航里程
 * @author w
 *
 */
public class EnduranceMileagePacket implements BasePacket,Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3579915668996576553L;
	/**
     * canId 为 can_0421 时，携带的是续航里程
     */
    private static final String Mileage_CAN_ID = "can_0421";
    @Property("a")
    /**续航里程*/
    private Double mileage;
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
	public static EnduranceMileagePacket valueOfCanDataNewPacket(CanDataNewPacket canDataNewPacket) {
		if (canDataNewPacket != null &&  canDataNewPacket.getData() != null) {
            List<String> dataList = canDataNewPacket.getData().get(Mileage_CAN_ID);
            if (dataList != null && !dataList.isEmpty()) {
                String data=dataList.get(0);
                System.out.println("data-------"+dataList.get(0));
                String mileageString=data.substring(6, 10);
                EnduranceMileagePacket enduranceMileage=new EnduranceMileagePacket();
                if("FFFF".equals(mileageString)){//无效数据
                    System.out.println("mileageString-------"+mileageString);
                    enduranceMileage.setMileage(-0.1);
                }else{
                	enduranceMileage.setMileage(Integer.parseInt(mileageString,16)/1e2);	
                }
                return enduranceMileage;
            }

        }
		return null;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	
}
