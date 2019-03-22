package com.cm.tbox.packet;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import java.io.Serializable;
import java.util.Arrays;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
import com.cm.tbox.packet.warn.CarInfoWarnPacket;
import com.cm.tbox.packet.warn.CarWarnCommonPacket;
import com.cm.tbox.packet.warn.CellWarnPacket;
import com.cm.tbox.packet.warn.ChargerWarnPacket;
import com.cm.tbox.packet.warn.DCWarnPacket;
import com.cm.tbox.packet.warn.LHXNWarnPacket;
import com.cm.tbox.packet.warn.MotorWarnPacket;
import com.cm.tbox.packet.warn.PowerCellWarnPacket;

/**
 * 告警数据包
 * @author yunlu
 *
 */
public class CarWarnPacket implements BasePacket,Serializable{
	@NotSaved
	private Integer length=0;
	/** 最高报警等级 */
	@Property("b")
	private Integer warnLevel;
	/** 通用报警*/
	@Embedded("c")
	private CarWarnCommonPacket commonWarn;
	/** 电池告警*/
	@Embedded("d")
	private CellWarnPacket cellWarn;
	/** 整车信息告警*/
	@Embedded("e")
	private CarInfoWarnPacket carInfoWarn;
	/** 充电机告警*/
	@Embedded("f")
	private ChargerWarnPacket chargerWarn;
	/** 电机告警*/
	@Embedded("g")
	private MotorWarnPacket motorWarn;
	/** dc告警*/
	@Embedded("h")
	private DCWarnPacket dcWarn;
	/** 蓝海新能告警*/
	@Embedded("i")
	private LHXNWarnPacket lhxnWarn;
	/** 动力蓄电池告警*/
	@Embedded("a")
	private PowerCellWarnPacket powerCellWarn;
	/** 用来检验是的标识 */
	@NotSaved
	private byte[] flagBytes;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		warnLevel=0xff & bytes[0];
		commonWarn=new CarWarnCommonPacket();
		byte[] commonWarnBytes = Arrays.copyOfRange(bytes, 1, 5);
		commonWarn.build(commonWarnBytes);
		flagBytes=commonWarnBytes;
		int position=5;
		int chargeWarnLength = 1+bytes[position]*4;
		position+=chargeWarnLength;
		int driveMotorWarnLength = 1+bytes[position]*4;
		position+=driveMotorWarnLength;
		int motorWarnLength = 1+bytes[position]*4;
		position+=motorWarnLength;
		int otherWarnLength=1+bytes[position]*4;
		position+=otherWarnLength;
		length=position;
//		int otherWarnLength=0xff & bytes[position++];
//		length=position;
//		if(otherWarnLength>=14){
//			cellWarn = new CellWarnPacket();
//			cellWarn.build(Arrays.copyOfRange(bytes, position,position+cellWarn.length()));
//			position+=cellWarn.length();
//			
//			carInfoWarn = new CarInfoWarnPacket();
//			carInfoWarn.build(Arrays.copyOfRange(bytes, position,position+carInfoWarn.length()));
//			position+=carInfoWarn.length();
//			
//			motorWarn = new MotorWarnPacket(); 
//			motorWarn.build(Arrays.copyOfRange(bytes, position,position+motorWarn.length()));
//			position+=motorWarn.length();
//			
//			chargerWarn = new ChargerWarnPacket(); 
//			chargerWarn.build(Arrays.copyOfRange(bytes, position,position+chargerWarn.length()));
//			position+=chargerWarn.length();
//			
//			dcWarn = new DCWarnPacket(); 
//			dcWarn.build(Arrays.copyOfRange(bytes, position,position+dcWarn.length()));
//			position+=dcWarn.length();
//			if(otherWarnLength==17){
//				lhxnWarn = new LHXNWarnPacket();
//				lhxnWarn.build(Arrays.copyOfRange(bytes, position,position+lhxnWarn.length()));
//				position+=lhxnWarn.length();
//			}
//			flagBytes=ByteUtils.addAll(flagBytes,Arrays.copyOfRange(bytes, length,position));
//			length=position;
//		}else{
//			length=position+otherWarnLength;
//		}
		
	}
	
	@Override
	public byte[] unbuild() throws Exception {
		byte levelByte = warnLevel.byteValue();
		return ByteUtils.addAll(levelByte,commonWarn.unbuild(),new byte[]{0,0,0,0});
	}

	@Override
	public Integer length() throws Exception {
		// TODO Auto-generated method stub
		return length;
	}
	
	public byte[] bytes(){
		return flagBytes;
	}
	
	public Integer getWarnLevel() {
		return warnLevel;
	}

	public void setWarnLevel(Integer warnLevel) {
		this.warnLevel = warnLevel;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public CarWarnCommonPacket getCommonWarn() {
		return commonWarn;
	}

	public void setCommonWarn(CarWarnCommonPacket commonWarn) {
		this.commonWarn = commonWarn;
	}

	public CellWarnPacket getCellWarn() {
		return cellWarn;
	}

	public void setCellWarn(CellWarnPacket cellWarn) {
		this.cellWarn = cellWarn;
	}

	public CarInfoWarnPacket getCarInfoWarn() {
		return carInfoWarn;
	}

	public void setCarInfoWarn(CarInfoWarnPacket carInfoWarn) {
		this.carInfoWarn = carInfoWarn;
	}

	public ChargerWarnPacket getChargerWarn() {
		return chargerWarn;
	}

	public void setChargerWarn(ChargerWarnPacket chargerWarn) {
		this.chargerWarn = chargerWarn;
	}

	public MotorWarnPacket getMotorWarn() {
		return motorWarn;
	}

	public void setMotorWarn(MotorWarnPacket motorWarn) {
		this.motorWarn = motorWarn;
	}

	public DCWarnPacket getDcWarn() {
		return dcWarn;
	}

	public void setDcWarn(DCWarnPacket dcWarn) {
		this.dcWarn = dcWarn;
	}

	public LHXNWarnPacket getLhxnWarn() {
		return lhxnWarn;
	}

	public void setLhxnWarn(LHXNWarnPacket lhxnWarn) {
		this.lhxnWarn = lhxnWarn;
	}

	public PowerCellWarnPacket getPowerCellWarn() {
		return powerCellWarn;
	}

	public void setPowerCellWarn(PowerCellWarnPacket powerCellWarn) {
		this.powerCellWarn = powerCellWarn;
	}
}
