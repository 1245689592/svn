package com.cm.mongo.entity;

import java.io.Serializable;
import java.util.Date;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.utils.IndexType;

import com.cm.tbox.packet.CanDataNewPacket;
import com.cm.tbox.packet.CanDataPacket;
import com.cm.tbox.packet.CarCellTempPacket;
import com.cm.tbox.packet.CarCellVoltagePacket;
import com.cm.tbox.packet.CarExpandMorePacket;
import com.cm.tbox.packet.CarExpandPacket;
import com.cm.tbox.packet.ExtremumPacket;
import com.cm.tbox.packet.CarHGExpandPacket;
import com.cm.tbox.packet.CarInfoPacket;
import com.cm.tbox.packet.CarLHXNExpandPacket;
import com.cm.tbox.packet.LocationPacket;
import com.cm.tbox.packet.CarWarnPacket;
import com.cm.tbox.packet.ChargeDeviceTempPacket;
import com.cm.tbox.packet.ChargeDeviceVoltagePacket;
import com.cm.tbox.packet.DriveMotorPacket;
import com.cm.tbox.packet.FuelCellsPacket;
import com.cm.tbox.packet.MotorPacket;
import com.cm.tbox.packet.realtime.BcmLightStatePacket;
import com.cm.tbox.packet.realtime.EnduranceMileagePacket;
import com.cm.tbox.packet.realtime.SurplusAvailableMileagePacket;
import com.cm.tbox.packet.realtime.TireDataPacket;

/**
 * 车辆历史数据查询
 * @author yunlu
 *
 */
@Indexes({
	@Index(fields = {@Field(value="carId",type=IndexType.ASC),@Field(value="updateTime",type=IndexType.DESC)})
})
public class CarInfoHistoryMongo implements Serializable{
	private static final long serialVersionUID = -6995489980062968179L;
	@Id
	private String id;
	/**是否为补发数据 0实时 1补发*/
	private int type;
	/**车辆唯一识别码*/
	private String carId;
	/**vin*/
	private String vin;
	/**终端code*/
	private String code;
	/**最后更新时间*/
	private Date updateTime;
	/**服务器接受时间*/
	private Date receiveTime;
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
	private LocationPacket carLocationRecord;
	/**极值信息*/
	@Embedded("f")
    private ExtremumPacket carExtremum;
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
	public LocationPacket getCarLocationRecord() {
		return carLocationRecord;
	}
	public void setCarLocationRecord(LocationPacket carLocationRecord) {
		this.carLocationRecord = carLocationRecord;
	}
	public ExtremumPacket getCarExtremum() {
		return carExtremum;
	}
	public void setCarExtremum(ExtremumPacket carExtremum) {
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
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public CarLHXNExpandPacket getCarLHXNExpandRecord() {
		return carLHXNExpandRecord;
	}
	public void setCarLHXNExpandRecord(CarLHXNExpandPacket carLHXNExpandRecord) {
		this.carLHXNExpandRecord = carLHXNExpandRecord;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public DriveMotorPacket getDriveMotorRecord() {
		return driveMotorRecord;
	}
	public void setDriveMotorRecord(DriveMotorPacket driveMotorRecord) {
		this.driveMotorRecord = driveMotorRecord;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
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
