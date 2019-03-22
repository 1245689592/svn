package com.cm.tbox.packet;

import java.io.Serializable;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;

/**
 * 查询参数中
 * @author yunlu
 *
 */
public class CarOtherStatusPacket implements BasePacket,Serializable{
	private static final long serialVersionUID = 8049965103920011926L;
	/**状态是否有效 0有效 1无效*/
	private Integer status;
	/**是否P档 0是 1否*/
	private Integer pGearsStatus;
	/**充电许可 1许可 0不许可*/
	private Integer chargeLicence;
	/**热管理许可 1许可 0不许可*/
	private Integer thermalManageLicence;
	/**BMS充电状态 0未充电 1充电中 2 BMS充电故障 3充电完成*/
	private Integer chargeStatus;
	/**充电枪 1插入 0未插入*/
	private Integer chargeGunStatus;
	/**急停开关 1 急停开始 0 急停关闭*/
	private Integer rapidSwtichStatus;
	/**危险告警灯 1开启 0关闭*/
	private Integer warnLightStatus;
	/**近光灯 1开启 0关闭*/
	private Integer lowBeamLightStatus;
	
	@Override
	public void build(byte[] bytes) throws Exception {
		byte[] statusBytes = ByteUtils.byte2BitArray(bytes[0]);
		status = 0xff & statusBytes[7];
		pGearsStatus = 0xff & statusBytes[0];
		byte[] statusBytes2 = ByteUtils.byte2BitArray(bytes[1]);
		chargeLicence = 0xff & statusBytes2[7];
		thermalManageLicence = 0xff & statusBytes2[6];
		chargeStatus = 0;
		if(statusBytes2[5]==1){
			chargeStatus += (int)Math.pow(2, 1);
		}
		if(statusBytes2[4]==1){
			chargeStatus += (int)Math.pow(2, 0);
		}
		chargeGunStatus = 0xff & statusBytes2[3];
		rapidSwtichStatus = 0xff & statusBytes2[2];
		warnLightStatus = 0xff & statusBytes2[1];
		lowBeamLightStatus = 0xff & statusBytes2[0];
	}
	@Override
	public byte[] unbuild() throws Exception {
		
		return null;
	}

	@Override
	public Integer length() throws Exception {
		
		return 2;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getpGearsStatus() {
		return pGearsStatus;
	}

	public void setpGearsStatus(Integer pGearsStatus) {
		this.pGearsStatus = pGearsStatus;
	}

	public Integer getChargeLicence() {
		return chargeLicence;
	}

	public void setChargeLicence(Integer chargeLicence) {
		this.chargeLicence = chargeLicence;
	}

	public Integer getThermalManageLicence() {
		return thermalManageLicence;
	}

	public void setThermalManageLicence(Integer thermalManageLicence) {
		this.thermalManageLicence = thermalManageLicence;
	}

	public Integer getChargeStatus() {
		return chargeStatus;
	}

	public void setChargeStatus(Integer chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public Integer getChargeGunStatus() {
		return chargeGunStatus;
	}

	public void setChargeGunStatus(Integer chargeGunStatus) {
		this.chargeGunStatus = chargeGunStatus;
	}

	public Integer getRapidSwtichStatus() {
		return rapidSwtichStatus;
	}

	public void setRapidSwtichStatus(Integer rapidSwtichStatus) {
		this.rapidSwtichStatus = rapidSwtichStatus;
	}

	public Integer getWarnLightStatus() {
		return warnLightStatus;
	}

	public void setWarnLightStatus(Integer warnLightStatus) {
		this.warnLightStatus = warnLightStatus;
	}

	public Integer getLowBeamLightStatus() {
		return lowBeamLightStatus;
	}

	public void setLowBeamLightStatus(Integer lowBeamLightStatus) {
		this.lowBeamLightStatus = lowBeamLightStatus;
	}
	
}
