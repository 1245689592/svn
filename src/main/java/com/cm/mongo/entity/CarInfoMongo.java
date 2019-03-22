package com.cm.mongo.entity;

import java.io.Serializable;
import java.util.Date;

import com.cm.tbox.packet.realtime.BcmLightStatePacket;
import com.cm.tbox.packet.realtime.EnduranceMileagePacket;
import com.cm.tbox.packet.realtime.SurplusAvailableMileagePacket;
import com.cm.tbox.packet.realtime.TireDataPacket;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.utils.IndexType;

import com.cm.common.constant.CommonConstants;
import com.cm.common.utils.WarnUtil;
import com.cm.tbox.packet.CanDataNewPacket;
import com.cm.tbox.packet.CanDataPacket;
import com.cm.tbox.packet.CarCellTempPacket;
import com.cm.tbox.packet.CarCellVoltagePacket;
import com.cm.tbox.packet.CarExpandMorePacket;
import com.cm.tbox.packet.CarExpandPacket;
import com.cm.tbox.packet.CarExtremumPacket;
import com.cm.tbox.packet.CarHGExpandPacket;
import com.cm.tbox.packet.CarInfoPacket;
import com.cm.tbox.packet.CarLHXNExpandPacket;
import com.cm.tbox.packet.CarLocationPacket;
import com.cm.tbox.packet.CarWarnPacket;
import com.cm.tbox.packet.gb.ChargeDeviceTempPacket;
import com.cm.tbox.packet.gb.ChargeDeviceVoltagePacket;
import com.cm.tbox.packet.gb.DriveMotorPacket;
import com.cm.tbox.packet.gb.FuelCellsPacket;
import com.cm.tbox.packet.gb.MotorPacket;

/**
 * mongo维护的车辆在线数据
 * @author yunlu
 *
 */
@Entity("carStatus")
@Indexes({
	@Index(fields = @Field(value="updateTime", type = IndexType.ASC))
})
public class CarInfoMongo implements Serializable {
	private static final long serialVersionUID = -1637815014707107174L;
	/**车辆唯一识别码*/
	@Id
	private String carId;
	/**vin*/
	private String vin;
	/**终端code*/
	private String code;
	/**最后更新时间*/
	private Date updateTime;
	@Embedded("a")
	private CarInfoPacket carInfoRecord;
	/**驱动电机*/
	@Embedded("b")
	private DriveMotorPacket driveMotorRecord;
	/**燃料电池*/
	@Embedded("c")
	private FuelCellsPacket fuelCellsRecord;
	/**发动机数据*/
	@Embedded("d")
	private MotorPacket motorPacket;
	/**位置*/
	@Embedded("e")
	private CarLocationPacket carLocationRecord;
	/**极值信息*/
	@Embedded("f")
    private CarExtremumPacket carExtremum;
    /**报警信息*/
	@Embedded("g")
    private CarWarnPacket carWarn;
    /**可充电储能装置电压数据*/
	@Embedded("h")
    private ChargeDeviceVoltagePacket chargeDeviceVoltage;
    /**可充电储能装置温度数据*/
	@Embedded("i")
    private ChargeDeviceTempPacket chargeDeviceTemp;
    /**车辆状态扩展信息 */
	@Embedded("j")
    private CarExpandMorePacket carExpandMoreRecord;
    /**蓝海新能扩展信息*/
	@Embedded("k")
    private CarLHXNExpandPacket carLHXNExpandRecord;
	/**单体蓄电池温度数据*/
    private CarCellTempPacket carCellTemp;
    /**动力蓄电池包电压数据*/
    private CarCellVoltagePacket carCellVoltage;
    /**扩展信息*/
    private CarExpandPacket carExpandRecord;
    /**华冠扩展信息*/
    private CarHGExpandPacket carHGExpandRecord;
    /**can报文信息*/
    @Embedded("l")
    private CanDataPacket canDataRecord;
    /**can报文简报*/
	@Embedded("m")
    private CanDataNewPacket canDataNewRecord;

	/**
	 * 国金项目：轮胎数据信息包
	 */
	@Embedded("n")
	private TireDataPacket tireDataPacket;


    /**
     * 车门数据
     */
    @Embedded("o")
    private BcmLightStatePacket bcmLightStatePacket;
	/**
	 * 续航里程
	 */
	@Embedded("p")
	private EnduranceMileagePacket enduranceMileageRecord;
	/**
	 * 剩余可用里程
	 * 
	 */
	@Embedded("q")
	private SurplusAvailableMileagePacket surplusAvailableMileageRecord;
	public CarInfoMongo() {
		super();
	}
	
	public CarStatusInfoMongo toStatusInfo() throws Exception{
		CarStatusInfoMongo statusInfo = new CarStatusInfoMongo();
		statusInfo.setUpdateTime(this.updateTime);
		if(carInfoRecord!=null){
			statusInfo.setSpeed(carInfoRecord.getSpeed());
			statusInfo.setGears(carInfoRecord.getGears());
			statusInfo.setChargeStatus(carInfoRecord.getChargeStatus());
		}
		if(carLocationRecord!=null){
			if(carLocationRecord.getStatus()!=null&&carLocationRecord.getStatus()==0){
				statusInfo.setDirection(carLocationRecord.getDirection());
				statusInfo.setPoint(new DisPoint(carLocationRecord.getLonType()==1?-carLocationRecord.getLon():carLocationRecord.getLon(), 
						carLocationRecord.getLatType()==1?-carLocationRecord.getLat():carLocationRecord.getLat()));
			}
		}
		if(carExtremum!=null){
			statusInfo.setSoc(carExtremum.getSoc());
		}
		if(carExpandMoreRecord!=null){
			statusInfo.setDumpChargeTime(carExpandMoreRecord.getDumpTime());
		}
		statusInfo.setWarn(WarnUtil.checkWarn(carWarn));
		statusInfo.setOnline(CommonConstants.IS_ONLINE);
		return statusInfo;
	}
	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public CarCellTempPacket getCarCellTemp() {
		return carCellTemp;
	}

	public void setCarCellTemp(CarCellTempPacket carCellTemp) {
		this.carCellTemp = carCellTemp;
	}

	public CarCellVoltagePacket getCarCellVoltage() {
		return carCellVoltage;
	}

	public void setCarCellVoltage(CarCellVoltagePacket carCellVoltage) {
		this.carCellVoltage = carCellVoltage;
	}

	public CarInfoPacket getCarInfoRecord() {
		return carInfoRecord;
	}

	public void setCarInfoRecord(CarInfoPacket carInfoRecord) {
		this.carInfoRecord = carInfoRecord;
	}

	public CarLocationPacket getCarLocationRecord() {
		return carLocationRecord;
	}

	public void setCarLocationRecord(CarLocationPacket carLocationRecord) {
		this.carLocationRecord = carLocationRecord;
	}

	public CarExtremumPacket getCarExtremum() {
		return carExtremum;
	}

	public void setCarExtremum(CarExtremumPacket carExtremum) {
		this.carExtremum = carExtremum;
	}

	public CarWarnPacket getCarWarn() {
		return carWarn;
	}

	public void setCarWarn(CarWarnPacket carWarn) {
		this.carWarn = carWarn;
	}

	public CarExpandPacket getCarExpandRecord() {
		return carExpandRecord;
	}

	public void setCarExpandRecord(CarExpandPacket carExpandRecord) {
		this.carExpandRecord = carExpandRecord;
	}

	public CarHGExpandPacket getCarHGExpandRecord() {
		return carHGExpandRecord;
	}

	public void setCarHGExpandRecord(CarHGExpandPacket carHGExpandRecord) {
		this.carHGExpandRecord = carHGExpandRecord;
	}

	public CarExpandMorePacket getCarExpandMoreRecord() {
		return carExpandMoreRecord;
	}

	public void setCarExpandMoreRecord(CarExpandMorePacket carExpandMoreRecord) {
		this.carExpandMoreRecord = carExpandMoreRecord;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public CarLHXNExpandPacket getCarLHXNExpandRecord() {
		return carLHXNExpandRecord;
	}

	public void setCarLHXNExpandRecord(CarLHXNExpandPacket carLHXNExpandRecord) {
		this.carLHXNExpandRecord = carLHXNExpandRecord;
	}

	public CanDataPacket getCanDataRecord() {
		return canDataRecord;
	}

	public void setCanDataRecord(CanDataPacket canDataRecord) {
		this.canDataRecord = canDataRecord;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public FuelCellsPacket getFuelCellsRecord() {
		return fuelCellsRecord;
	}

	public void setFuelCellsRecord(FuelCellsPacket fuelCellsRecord) {
		this.fuelCellsRecord = fuelCellsRecord;
	}

	public MotorPacket getMotorPacket() {
		return motorPacket;
	}

	public void setMotorPacket(MotorPacket motorPacket) {
		this.motorPacket = motorPacket;
	}

	public ChargeDeviceVoltagePacket getChargeDeviceVoltage() {
		return chargeDeviceVoltage;
	}

	public void setChargeDeviceVoltage(ChargeDeviceVoltagePacket chargeDeviceVoltage) {
		this.chargeDeviceVoltage = chargeDeviceVoltage;
	}

	public ChargeDeviceTempPacket getChargeDeviceTemp() {
		return chargeDeviceTemp;
	}

	public void setChargeDeviceTemp(ChargeDeviceTempPacket chargeDeviceTemp) {
		this.chargeDeviceTemp = chargeDeviceTemp;
	}

	public DriveMotorPacket getDriveMotorRecord() {
		return driveMotorRecord;
	}

	public void setDriveMotorRecord(DriveMotorPacket driveMotorRecord) {
		this.driveMotorRecord = driveMotorRecord;
	}

	public CanDataNewPacket getCanDataNewRecord() {
		return canDataNewRecord;
	}

	public void setCanDataNewRecord(CanDataNewPacket canDataNewRecord) {
		this.canDataNewRecord = canDataNewRecord;
	}

	public TireDataPacket getTireDataPacket() {
		return tireDataPacket;
	}

	public void setTireDataPacket(TireDataPacket tireDataPacket) {
		this.tireDataPacket = tireDataPacket;
	}

	public BcmLightStatePacket getBcmLightStatePacket() {
		return bcmLightStatePacket;
	}

	public void setBcmLightStatePacket(BcmLightStatePacket bcmLightStatePacket) {
		this.bcmLightStatePacket = bcmLightStatePacket;
	}

	public EnduranceMileagePacket getEnduranceMileageRecord() {
		return enduranceMileageRecord;
	}

	public void setEnduranceMileageRecord(EnduranceMileagePacket enduranceMileageRecord) {
		this.enduranceMileageRecord = enduranceMileageRecord;
	}

	public SurplusAvailableMileagePacket getSurplusAvailableMileageRecord() {
		return surplusAvailableMileageRecord;
	}

	public void setSurplusAvailableMileageRecord(SurplusAvailableMileagePacket surplusAvailableMileageRecord) {
		this.surplusAvailableMileageRecord = surplusAvailableMileageRecord;
	}
	
}
