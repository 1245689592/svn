package com.cm.tbox.packet.warn;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

/**
 * 蓝海新能扩展包
 * @author yunlu
 *
 */
public class LHXNWarnPacket extends BaseWarnPacket implements BasePacket {
	/** 低压电源低(1:异常；0：正常)*/
	@Property("a")
	private Integer lowVoltagePowerLow;
	/** 低压电源高(1:异常；0：正常)*/
	@Property("b")
	private Integer lowVoltagePowerHigh;
	/** 电机系统自检故障(1:异常；0：正常)*/
	@Property("c")
	private Integer motorSysCheck;
	/** 电机旋变故障(1:异常；0：正常)*/
	@Property("d")
	private Integer motorRotary;
	/** 电机控制器过流状态(1:过流；0：正常)*/
	@Property("e")
	private Integer motorControlOverFlow;
	/** 电机控制器过压(1:过压；0：正常)*/
	@Property("f")
	private Integer motorControlOverVoltage;
	/** 电机控制器欠压(1:欠压；0：正常)*/
	@Property("g")
	private Integer motorControlLowVoltage;
	/** 真空度报警状态(1:异常；0：正常)*/
	@Property("h")
	private Integer vacuumWarn;
	/** 系统故障状态(1:异常；0：正常)*/
	@Property("i")
	private Integer sysStatus;
	/** EPS故障状态(1:异常；0：正常)*/
	@Property("j")
	private Integer eps;
	/** 加速踏板故障状态(0：正常；1:一路失效；2：两路失效；3:两路不回位)*/
	@Property("k")
	private Integer accelTrip;
	/** 电机控制器通信故障(0：正常；1:丢帧；2：掉线；3:not used)*/
	@Property("l")
	private Integer motorControl;
	/** 电池系统通信故障(0：正常；1:丢帧；2：掉线；3:not used)*/
	@Property("m")
	private Integer cellSys;
	/** TCU通信故障(0：正常；1:丢帧；2：掉线；3:not used)*/
	@Property("n")
	private Integer tcu;
	/** 浮动插头状态(0：正常；1:一级故障；2：二级故障；3:not used)*/
	@Property("o")
	private Integer floatPlug;
	/** 电机使能反馈不一致故障(1:异常；0：正常)*/
	@Property("p")
	private Integer motorEnableFeedback;
	/** 主接触器反馈不一致故障(1:异常；0：正常)*/
	@Property("q")
	private Integer masterContactorFeedback;
	/** 扭矩反馈不一致(1:异常；0：正常)*/
	@Property("r")
	private Integer torqueFeedback;
	/** ABS故障状态(1:异常；0：正常)*/
	@Property("s")
	private Integer abs;
	/** BMS自检(1:异常；0：正常)*/
	@Property("t")
	private Integer bms;
	/** 高压控制盒故障(1:异常；0：正常)*/
	@Property("u")
	private Integer highVoltageControl;
	/** VCU通信故障(1:异常；0：正常)*/
	@Property("v")
	private Integer vcu;
	/** 维修开关故障(1:异常；0：正常)*/
	@Property("w")
	private Integer repair;
	/** 用来检验是的标识 */
	@NotSaved
	private byte[] flagBytes;
	@Override
	public void build(byte[] bytes) throws Exception {
		byte[] tempBytes =ByteUtils.byte2BitArray(bytes[0]);
		lowVoltagePowerLow=0xff & tempBytes[0];
		lowVoltagePowerHigh=0xff & tempBytes[1];
		motorSysCheck = 0xff & tempBytes[2];
		motorRotary = 0xff & tempBytes[3];
		motorControlOverFlow =0xff & tempBytes[4];
		motorControlOverVoltage = 0xff & tempBytes[5];
		motorControlLowVoltage = 0xff & tempBytes[6];
		vacuumWarn = 0xff & tempBytes[7];
		tempBytes =ByteUtils.byte2BitArray(bytes[1]);
		sysStatus = 0xff & tempBytes[0];
		eps = 0xff & tempBytes[1];
		accelTrip = tempBytes[3]*2+tempBytes[2];
		motorControl = tempBytes[5]*2+tempBytes[4];
		cellSys =  tempBytes[7]*2+tempBytes[6];
		tempBytes =ByteUtils.byte2BitArray(bytes[2]);
		tcu = tempBytes[1]*2+tempBytes[0];
		floatPlug = tempBytes[3]*2+tempBytes[2];
		motorEnableFeedback = 0xff & tempBytes[4];
		masterContactorFeedback = 0xff & tempBytes[5];
		torqueFeedback = 0xff & tempBytes[6];
		abs = 0xff & tempBytes[7];
		tempBytes =ByteUtils.byte2BitArray(bytes[3]);
		bms = 0xff & tempBytes[0];
		highVoltageControl = 0xff & tempBytes[1];
		vcu = 0xff & tempBytes[2];
		repair = 0xff & tempBytes[3];
		flagBytes=bytes;
	}

	@Override
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer length() throws Exception {
		return 4;
	}

	@Override
	public byte[] bytes() {
		// TODO Auto-generated method stub
		return flagBytes;
	}

	@Override
	public String type() {
		return "g";
	}
	@Override
	public String checkWarn(byte[] bytes){
		StringBuffer sb =new StringBuffer();
		byte[] srcBytes = bytes();
		//处理单位数据
		x:for(int i=0;i<srcBytes.length;i++){
			if(srcBytes[i]!=bytes[i]){
				byte temp = (byte)(srcBytes[i]^bytes[i]);
				byte[] oldbytes = ByteUtils.byte2BitArray(bytes[i]);
				if(temp!=0){
					byte[] tempBytes = ByteUtils.byte2BitArray(temp);
					byte[] srcTempBytes = ByteUtils.byte2BitArray(srcBytes[i]);
					y:for(int j=0;j<8;j++){
						if((i==1&&j>1)||(i==2&&j<4)){
							if(j%2==0){
								int oldWarn= oldbytes[j+1]*2+oldbytes[j];
								int newWarn = srcTempBytes[j+1]*2+srcTempBytes[j];
								if(newWarn!=oldWarn){
									sb.append(newWarn!=0?"":"-");
									sb.append(type());
									sb.append(i);
									sb.append("-");
									sb.append(j/2);
									sb.append("-");
									sb.append(newWarn==0?oldWarn:newWarn);
									sb.append(";");
								}
							}
							continue y;
						}
						if(tempBytes[j]==1){
							sb.append(srcTempBytes[j]!=(byte)0?"":"-");
							sb.append(type());
							sb.append(i*8+j);
							sb.append(";");
						}
					}
				}
			}
		}
		//处理双位数据
		
		return sb.toString();
	}

	public Integer getLowVoltagePowerLow() {
		return lowVoltagePowerLow;
	}

	public void setLowVoltagePowerLow(Integer lowVoltagePowerLow) {
		this.lowVoltagePowerLow = lowVoltagePowerLow;
	}

	public Integer getLowVoltagePowerHigh() {
		return lowVoltagePowerHigh;
	}

	public void setLowVoltagePowerHigh(Integer lowVoltagePowerHigh) {
		this.lowVoltagePowerHigh = lowVoltagePowerHigh;
	}

	public Integer getMotorSysCheck() {
		return motorSysCheck;
	}

	public void setMotorSysCheck(Integer motorSysCheck) {
		this.motorSysCheck = motorSysCheck;
	}

	public Integer getMotorRotary() {
		return motorRotary;
	}

	public void setMotorRotary(Integer motorRotary) {
		this.motorRotary = motorRotary;
	}

	public Integer getMotorControlOverFlow() {
		return motorControlOverFlow;
	}

	public void setMotorControlOverFlow(Integer motorControlOverFlow) {
		this.motorControlOverFlow = motorControlOverFlow;
	}

	public Integer getMotorControlOverVoltage() {
		return motorControlOverVoltage;
	}

	public void setMotorControlOverVoltage(Integer motorControlOverVoltage) {
		this.motorControlOverVoltage = motorControlOverVoltage;
	}

	public Integer getMotorControlLowVoltage() {
		return motorControlLowVoltage;
	}

	public void setMotorControlLowVoltage(Integer motorControlLowVoltage) {
		this.motorControlLowVoltage = motorControlLowVoltage;
	}

	public Integer getVacuumWarn() {
		return vacuumWarn;
	}

	public void setVacuumWarn(Integer vacuumWarn) {
		this.vacuumWarn = vacuumWarn;
	}

	public Integer getSysStatus() {
		return sysStatus;
	}

	public void setSysStatus(Integer sysStatus) {
		this.sysStatus = sysStatus;
	}

	public Integer getEps() {
		return eps;
	}

	public void setEps(Integer eps) {
		this.eps = eps;
	}

	public Integer getAccelTrip() {
		return accelTrip;
	}

	public void setAccelTrip(Integer accelTrip) {
		this.accelTrip = accelTrip;
	}

	public Integer getMotorControl() {
		return motorControl;
	}

	public void setMotorControl(Integer motorControl) {
		this.motorControl = motorControl;
	}

	public Integer getCellSys() {
		return cellSys;
	}

	public void setCellSys(Integer cellSys) {
		this.cellSys = cellSys;
	}

	public Integer getTcu() {
		return tcu;
	}

	public void setTcu(Integer tcu) {
		this.tcu = tcu;
	}

	public Integer getFloatPlug() {
		return floatPlug;
	}

	public void setFloatPlug(Integer floatPlug) {
		this.floatPlug = floatPlug;
	}

	public Integer getMotorEnableFeedback() {
		return motorEnableFeedback;
	}

	public void setMotorEnableFeedback(Integer motorEnableFeedback) {
		this.motorEnableFeedback = motorEnableFeedback;
	}

	public Integer getMasterContactorFeedback() {
		return masterContactorFeedback;
	}

	public void setMasterContactorFeedback(Integer masterContactorFeedback) {
		this.masterContactorFeedback = masterContactorFeedback;
	}

	public Integer getTorqueFeedback() {
		return torqueFeedback;
	}

	public void setTorqueFeedback(Integer torqueFeedback) {
		this.torqueFeedback = torqueFeedback;
	}

	public Integer getAbs() {
		return abs;
	}

	public void setAbs(Integer abs) {
		this.abs = abs;
	}

	public Integer getBms() {
		return bms;
	}

	public void setBms(Integer bms) {
		this.bms = bms;
	}

	public Integer getHighVoltageControl() {
		return highVoltageControl;
	}

	public void setHighVoltageControl(Integer highVoltageControl) {
		this.highVoltageControl = highVoltageControl;
	}

	public Integer getVcu() {
		return vcu;
	}

	public void setVcu(Integer vcu) {
		this.vcu = vcu;
	}

	public Integer getRepair() {
		return repair;
	}

	public void setRepair(Integer repair) {
		this.repair = repair;
	}
}
