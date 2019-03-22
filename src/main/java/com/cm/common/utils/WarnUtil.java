package com.cm.common.utils;

import com.cm.tbox.packet.CarWarnPacket;

public class WarnUtil {
	
	public static boolean checkWarn(CarWarnPacket carWarn){
		if(carWarn==null){
			return false;
		}
		for(byte b:carWarn.bytes()){
			if(b!=(byte)0){
				return true;
			}
		}
		return false;
	}
	
}
