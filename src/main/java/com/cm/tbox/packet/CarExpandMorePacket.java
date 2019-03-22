package com.cm.tbox.packet;

import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.NotSaved;
import java.io.Serializable;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
/**
 * 扩展信息2
 * @author yunlu
 *
 */
public class CarExpandMorePacket  implements BasePacket,Serializable {
	
	@NotSaved
	private Integer length =0 ;
	/**电机转矩 有效值范围：0～40000（数值偏移量 2000Nm表示-2000Nm～2000Nm），最小计量单元：0.1Nm*/
	@Property("b")
	private java.lang.Double motorTorque;
	/**充电电流 有效值范围：0～5000（表示 0A～500A），最小计量单元：0.1A*/
	@Property("c")
	private java.lang.Double chargeCurrent;
	/**充电电压 有效值范围：0～6000（表示 0V～600V，最小计量单元：0.1V*/
	@Property("d")
	private java.lang.Double chargeVoltage;
	/**电机交流侧电流 有效值范围：0～20000（表示 0A～2000A），最小计量单元：0.1A*/
	@Property("e")
	private java.lang.Double motorAcCurrent;
	/**电机交流侧电压 有效值范围：0～10000（表示 0V～2000V，最小计量单元：0.1V*/
	@Property("f")
	private java.lang.Double motorAcVoltage;
	/**直流侧电压 有效值范围：0～6000（表示 0V～600V，最小计量单元：0.1V*/
	@Property("g")
	private java.lang.Double dcVoltage;
	/**DC工作状态 0-停止；1-工作*/
	@Property("h")
	private java.lang.Integer dcStatus;
	/**DC输出电流 有效值范围：0～5000（表示 0A～500A），最小计量单元：0.1A*/
	@Property("i")
	private java.lang.Double dcOutCurrent;
	/**DC输出电压 有效值范围：0～6000（表示 0V～600V，最小计量单元：0.1V*/
	@Property("j")
	private java.lang.Double dcOutVoltage;
	/**DC工作温度 有效值范围：0～250   （数值偏移量 50℃，表示-50℃～+200℃）， 最小计量单元：1℃*/
	@Property("k")
	private java.lang.Integer dcTemp;
	/**电池箱平均温度 有效值范围：0～250  （数值偏移量 50℃，表示-50℃～+200℃）， 最小计量单元：1℃*/
	@Property("l")
	private java.lang.Integer cellBoxTemp;
	/**剩余充电时间 有效值范围：0～65534（表示 0min～65534min），最小计量单元：1min*/
	@Property("m")
	private java.lang.Integer dumpTime;
	/**允许最大放电电流 有效值范围：0～10000（数值偏移量 500A，表示-500A～500A），最小计量单元：0.1A*/
	@Property("n")
	private java.lang.Double highOutCurrentAllow;
	/**允许最大回充电流 有效值范围：0～10000（数值偏移量 500A，表示-500A～500A），最小计量单元：0.1A*/
	@Property("o")
	private java.lang.Double highInCurrentAllow;
	/**最高允许充电端电压 有效值范围：0～65534（表示0～6553.4V），最小计量单元：0.1V*/
	@Property("p")
	private java.lang.Double highChargeVoltageAllow;
	/**最高允许充电电流 有效值范围：0～65534（表示0～6553.4A），最小计量单元：0.1A*/
	@Property("q")
	private java.lang.Double highChargeCurrentAllow;
	/**充电机输出电压 有效值范围：0～65534（表示0～6553.4V），最小计量单元：0.1V*/
	@Property("r")
	private java.lang.Double chargerOutVoltage;
	/**充电机输出电流 有效值范围：0～65534（表示0～6553.4A），最小计量单元：0.1A*/
	@Property("s")
	private java.lang.Double chargerOutCurrent;
	/**正极绝缘阻值  有效值范围：0～65534（表示0kΩ～6553.4kΩ)，最小计量单元：0.1 kΩ*/
	@Property("t")
	private java.lang.Double positiveInsulationResistance;
	/**负极绝缘阻值 有效值范围：0～65534（表示0kΩ～6553.4kΩ)，最小计量单元：0.1 kΩ*/
	@Property("u")
	private java.lang.Double cathodeInsulatingResistance;
	/**最大驱动转矩 有效值范围：0～65534（数值偏移量32000，表示-32000NM～33534NM），最小计量单元：1NM*/
	@Property("v")
	private java.lang.Integer highDriverTorque;
	/**最大驱动转速 有效值范围：0～65534（数值偏移量32000，表示-32000rpm～33534rpm），最小计量单元：1rpm*/
	@Property("w")
	private java.lang.Integer highDriverSpeed;
	/**电机目标输出转矩 有效值范围：0～65534（数值偏移量32000，表示-32000NM～33534NM），最小计量单元：1NM*/
	@Property("x")
	private java.lang.Integer motorTargetOutTorque;
	/**电机目标输出转速 有效值范围：0～65534（数值偏移量32000，表示-32000rpm～33534rpm），最小计量单元：1rpm*/
	@Property("y")
	private java.lang.Integer motorTargetOutSpeed;
	/**接触器前端电压 有效值范围：0～65534（数值偏移量32000，表示-32000V～33534V，最小计量单元：1V*/
	@Property("z")
	private java.lang.Integer contactorFrontVoltage;
	/**接触器后端电压 有效值范围：0～65534（数值偏移量32000，表示-32000V～33534V），最小计量单元：1V*/
	@Property("A")
	private java.lang.Integer contactorBackVoltage;
	/**SOH 有效值范围：0-1000（表示0～100.0%)，最小计量单元：0.1%*/
	@Property("B")
	private java.lang.Double soh;
	/**转向系统故障码 0-正常 1-自检异常 17-过压（输入端） 18-欠压（输入端） 19-过流/短路（输出端） 20-转速过高 21-开路检测异常 22-控制器温度过高*/
	@Property("C")
	private java.lang.Integer steeringSystemFaultCode;
	/**加热控制 1开启0关闭*/
	@Property("D")
	private java.lang.Integer warmUpControl;
	/**电池工作状态 0充电完成 1放电 2充电 3充电故障*/
	@Property("E")
	private java.lang.Integer cellWorkStatus;
	/**放电继电器状态 1吸合0断开*/
	@Property("F")
	private java.lang.Integer dischargeRelayStatus;
	/**充电继电器状态 1吸合0打开*/
	@Property("G")
	private java.lang.Integer chargeRelayStatus;
	/**怠机 1无扭矩输出 0无效*/
	@Property("H")
	private java.lang.Integer idleMachine;
	/**can/acc 1 can控制 0 油门控制*/
	@Property("I")
	private java.lang.Integer canAcc;
	/**预充接触器 1吸合0断开*/
	@Property("J")
	private java.lang.Integer preChargeContactor;
	/**开波 1开pwm波 0关pwm波*/
	@Property("K")
	private java.lang.Integer pwmWave;
	/**电机模式 1转速控制0转矩控制*/
	@Property("L")
	private java.lang.Integer motorSchema;
	/**主接触器状态 1吸合0断开*/
	@Property("M")
	private java.lang.Integer mainContactorStatus;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		motorTorque=ByteUtils.toDouble(bytes[0],bytes[1], 10, 0);
		motorTorque-=2000.0;
		chargeCurrent=ByteUtils.toDouble(bytes[2],bytes[3], 10, 0);
		chargeVoltage=ByteUtils.toDouble(bytes[4],bytes[5], 10, 0);
		motorAcCurrent=ByteUtils.toDouble(bytes[6],bytes[7], 10, 0);
		motorAcVoltage=ByteUtils.toDouble(bytes[8],bytes[9], 10, 0);
		dcVoltage=ByteUtils.toDouble(bytes[10],bytes[11], 10, 0);
		dcStatus=0xff & bytes[12];
		dcOutCurrent=ByteUtils.toDouble(bytes[13],bytes[14], 10, 0);
		dcOutVoltage=ByteUtils.toDouble(bytes[15],bytes[16], 10, 0);
		dcTemp=0xff & bytes[17]-50;
		cellBoxTemp=0xff & bytes[18]-50;
		dumpTime=ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[19],bytes[20]}, 0);
		highOutCurrentAllow=ByteUtils.toDouble(bytes[21],bytes[22], 10, 500);
		highInCurrentAllow=ByteUtils.toDouble(bytes[23],bytes[24], 10, 500);
		highChargeVoltageAllow=ByteUtils.toDouble(bytes[25],bytes[26], 10, 0);
		highChargeCurrentAllow=ByteUtils.toDouble(bytes[27],bytes[28], 10, 0);
		chargerOutVoltage=ByteUtils.toDouble(bytes[29],bytes[30], 10, 0);
		chargerOutCurrent=ByteUtils.toDouble(bytes[31],bytes[32], 10, 0);
		positiveInsulationResistance=ByteUtils.toDouble(bytes[33],bytes[34], 10, 0);
		cathodeInsulatingResistance=ByteUtils.toDouble(bytes[35],bytes[36], 10, 0);
		highDriverTorque=ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[37],bytes[38]}, 0)-32000;
		highDriverSpeed=ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[39],bytes[40]}, 0)-32000;
		motorTargetOutTorque=ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[41],bytes[42]}, 0)-32000;
		motorTargetOutSpeed=ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[43],bytes[44]}, 0)-32000;
		contactorFrontVoltage=ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[45],bytes[46]}, 0)-32000;
		contactorBackVoltage=ByteUtils.byteArrayToInt(new byte[]{0x00,0x00,bytes[47],bytes[48]}, 0)-32000;
		soh=ByteUtils.toDouble(bytes[49],bytes[50], 10, 0);
		steeringSystemFaultCode=0xff & bytes[52];
		byte[] tempBytes = ByteUtils.byte2BitArray(bytes[51]);
		warmUpControl=0xff & tempBytes[5];
		cellWorkStatus=0;
		cellWorkStatus+=tempBytes[4]*2*2;
		cellWorkStatus+=tempBytes[3]*2;
		cellWorkStatus+=tempBytes[2];
		dischargeRelayStatus=0xff & tempBytes[1];
		chargeRelayStatus=0xff & tempBytes[0];
		tempBytes = ByteUtils.byte2BitArray(bytes[53]);
		idleMachine=0xff & tempBytes[5];
		canAcc=0xff & tempBytes[4];
		preChargeContactor=0xff & tempBytes[3];
		pwmWave=0xff & tempBytes[2];
		motorSchema=0xff & tempBytes[1];
		mainContactorStatus=0xff & tempBytes[0];
	}

	@Override
	public byte[] unbuild() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer length() throws Exception {
		
		return 54;
	}

	public java.lang.Double getMotorTorque() {
		return motorTorque;
	}

	public void setMotorTorque(java.lang.Double motorTorque) {
		this.motorTorque = motorTorque;
	}

	public java.lang.Double getChargeCurrent() {
		return chargeCurrent;
	}

	public void setChargeCurrent(java.lang.Double chargeCurrent) {
		this.chargeCurrent = chargeCurrent;
	}

	public java.lang.Double getChargeVoltage() {
		return chargeVoltage;
	}

	public void setChargeVoltage(java.lang.Double chargeVoltage) {
		this.chargeVoltage = chargeVoltage;
	}

	public java.lang.Double getMotorAcCurrent() {
		return motorAcCurrent;
	}

	public void setMotorAcCurrent(java.lang.Double motorAcCurrent) {
		this.motorAcCurrent = motorAcCurrent;
	}

	public java.lang.Double getMotorAcVoltage() {
		return motorAcVoltage;
	}

	public void setMotorAcVoltage(java.lang.Double motorAcVoltage) {
		this.motorAcVoltage = motorAcVoltage;
	}

	public java.lang.Double getDcVoltage() {
		return dcVoltage;
	}

	public void setDcVoltage(java.lang.Double dcVoltage) {
		this.dcVoltage = dcVoltage;
	}

	public java.lang.Integer getDcStatus() {
		return dcStatus;
	}

	public void setDcStatus(java.lang.Integer dcStatus) {
		this.dcStatus = dcStatus;
	}

	public java.lang.Double getDcOutCurrent() {
		return dcOutCurrent;
	}

	public void setDcOutCurrent(java.lang.Double dcOutCurrent) {
		this.dcOutCurrent = dcOutCurrent;
	}

	public java.lang.Double getDcOutVoltage() {
		return dcOutVoltage;
	}

	public void setDcOutVoltage(java.lang.Double dcOutVoltage) {
		this.dcOutVoltage = dcOutVoltage;
	}

	public java.lang.Integer getDcTemp() {
		return dcTemp;
	}

	public void setDcTemp(java.lang.Integer dcTemp) {
		this.dcTemp = dcTemp;
	}

	public java.lang.Integer getCellBoxTemp() {
		return cellBoxTemp;
	}

	public void setCellBoxTemp(java.lang.Integer cellBoxTemp) {
		this.cellBoxTemp = cellBoxTemp;
	}

	public java.lang.Integer getDumpTime() {
		return dumpTime;
	}

	public void setDumpTime(java.lang.Integer dumpTime) {
		this.dumpTime = dumpTime;
	}

	public java.lang.Double getHighOutCurrentAllow() {
		return highOutCurrentAllow;
	}

	public void setHighOutCurrentAllow(java.lang.Double highOutCurrentAllow) {
		this.highOutCurrentAllow = highOutCurrentAllow;
	}

	public java.lang.Double getHighInCurrentAllow() {
		return highInCurrentAllow;
	}

	public void setHighInCurrentAllow(java.lang.Double highInCurrentAllow) {
		this.highInCurrentAllow = highInCurrentAllow;
	}

	public java.lang.Double getHighChargeVoltageAllow() {
		return highChargeVoltageAllow;
	}

	public void setHighChargeVoltageAllow(java.lang.Double highChargeVoltageAllow) {
		this.highChargeVoltageAllow = highChargeVoltageAllow;
	}

	public java.lang.Double getHighChargeCurrentAllow() {
		return highChargeCurrentAllow;
	}

	public void setHighChargeCurrentAllow(java.lang.Double highChargeCurrentAllow) {
		this.highChargeCurrentAllow = highChargeCurrentAllow;
	}

	public java.lang.Double getChargerOutVoltage() {
		return chargerOutVoltage;
	}

	public void setChargerOutVoltage(java.lang.Double chargerOutVoltage) {
		this.chargerOutVoltage = chargerOutVoltage;
	}

	public java.lang.Double getChargerOutCurrent() {
		return chargerOutCurrent;
	}

	public void setChargerOutCurrent(java.lang.Double chargerOutCurrent) {
		this.chargerOutCurrent = chargerOutCurrent;
	}

	public java.lang.Double getPositiveInsulationResistance() {
		return positiveInsulationResistance;
	}

	public void setPositiveInsulationResistance(java.lang.Double positiveInsulationResistance) {
		this.positiveInsulationResistance = positiveInsulationResistance;
	}

	public java.lang.Double getCathodeInsulatingResistance() {
		return cathodeInsulatingResistance;
	}

	public void setCathodeInsulatingResistance(java.lang.Double cathodeInsulatingResistance) {
		this.cathodeInsulatingResistance = cathodeInsulatingResistance;
	}

	public java.lang.Integer getHighDriverTorque() {
		return highDriverTorque;
	}

	public void setHighDriverTorque(java.lang.Integer highDriverTorque) {
		this.highDriverTorque = highDriverTorque;
	}

	public java.lang.Integer getHighDriverSpeed() {
		return highDriverSpeed;
	}

	public void setHighDriverSpeed(java.lang.Integer highDriverSpeed) {
		this.highDriverSpeed = highDriverSpeed;
	}

	public java.lang.Integer getMotorTargetOutTorque() {
		return motorTargetOutTorque;
	}

	public void setMotorTargetOutTorque(java.lang.Integer motorTargetOutTorque) {
		this.motorTargetOutTorque = motorTargetOutTorque;
	}

	public java.lang.Integer getMotorTargetOutSpeed() {
		return motorTargetOutSpeed;
	}

	public void setMotorTargetOutSpeed(java.lang.Integer motorTargetOutSpeed) {
		this.motorTargetOutSpeed = motorTargetOutSpeed;
	}

	public java.lang.Integer getContactorFrontVoltage() {
		return contactorFrontVoltage;
	}

	public void setContactorFrontVoltage(java.lang.Integer contactorFrontVoltage) {
		this.contactorFrontVoltage = contactorFrontVoltage;
	}

	public java.lang.Integer getContactorBackVoltage() {
		return contactorBackVoltage;
	}

	public void setContactorBackVoltage(java.lang.Integer contactorBackVoltage) {
		this.contactorBackVoltage = contactorBackVoltage;
	}

	public java.lang.Double getSoh() {
		return soh;
	}

	public void setSoh(java.lang.Double soh) {
		this.soh = soh;
	}

	public java.lang.Integer getSteeringSystemFaultCode() {
		return steeringSystemFaultCode;
	}

	public void setSteeringSystemFaultCode(java.lang.Integer steeringSystemFaultCode) {
		this.steeringSystemFaultCode = steeringSystemFaultCode;
	}

	public java.lang.Integer getWarmUpControl() {
		return warmUpControl;
	}

	public void setWarmUpControl(java.lang.Integer warmUpControl) {
		this.warmUpControl = warmUpControl;
	}

	public java.lang.Integer getCellWorkStatus() {
		return cellWorkStatus;
	}

	public void setCellWorkStatus(java.lang.Integer cellWorkStatus) {
		this.cellWorkStatus = cellWorkStatus;
	}

	public java.lang.Integer getDischargeRelayStatus() {
		return dischargeRelayStatus;
	}

	public void setDischargeRelayStatus(java.lang.Integer dischargeRelayStatus) {
		this.dischargeRelayStatus = dischargeRelayStatus;
	}

	public java.lang.Integer getChargeRelayStatus() {
		return chargeRelayStatus;
	}

	public void setChargeRelayStatus(java.lang.Integer chargeRelayStatus) {
		this.chargeRelayStatus = chargeRelayStatus;
	}

	public java.lang.Integer getIdleMachine() {
		return idleMachine;
	}

	public void setIdleMachine(java.lang.Integer idleMachine) {
		this.idleMachine = idleMachine;
	}

	public java.lang.Integer getCanAcc() {
		return canAcc;
	}

	public void setCanAcc(java.lang.Integer canAcc) {
		this.canAcc = canAcc;
	}

	public java.lang.Integer getPreChargeContactor() {
		return preChargeContactor;
	}

	public void setPreChargeContactor(java.lang.Integer preChargeContactor) {
		this.preChargeContactor = preChargeContactor;
	}

	public java.lang.Integer getPwmWave() {
		return pwmWave;
	}

	public void setPwmWave(java.lang.Integer pwmWave) {
		this.pwmWave = pwmWave;
	}

	public java.lang.Integer getMotorSchema() {
		return motorSchema;
	}

	public void setMotorSchema(java.lang.Integer motorSchema) {
		this.motorSchema = motorSchema;
	}

	public java.lang.Integer getMainContactorStatus() {
		return mainContactorStatus;
	}

	public void setMainContactorStatus(java.lang.Integer mainContactorStatus) {
		this.mainContactorStatus = mainContactorStatus;
	}

//	public java.lang.Integer getRechargeCycles() {
//		return rechargeCycles;
//	}
//
//	public void setRechargeCycles(java.lang.Integer rechargeCycles) {
//		this.rechargeCycles = rechargeCycles;
//	}
	
}
