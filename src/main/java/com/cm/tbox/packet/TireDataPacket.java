package com.cm.tbox.packet;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.base.BasePacket;
import org.mongodb.morphia.annotations.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;


/**
 * 国金项目：轮胎数据
 * chengchao
 */
public class TireDataPacket implements BasePacket, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TireDataPacket.class);

    /**
     * canId 为 can_0340 时，携带的是轮胎数据
     */
    private static final String TIRE_CAN_ID = "can_0340";
    private static final int DATA_LENGTH = 8;

    private static final double tirePressureScale = 1.373D;

    /**
     * 轮胎位置
     * 0：RearRightTire; 1：RearLeftTire; 2：FrontLeftTire; 3：FrontRightTire
     * 1.1 x 4
     */
    @Property("b")
    private Integer tirePosition;

    /**
     * 高压报警
     * 0：AlarmNotActive; 1：AlarmActive
     * 1.5 x 1
     */
    @Property("c")
    private Integer highPresAlarmStu;

    /**
     * 低压报警
     * 0：AlarmNotActive; 1：AlarmActive
     * 1.6 x 1
     */
    @Property("d")
    private Integer lowPresAlarmStu;

    /**
     * 高温报警
     * 0：AlarmNotActive; 1：AlarmActive
     * 1.7 x 1
     */
    @Property("e")
    private Integer highTeamperAlarmStu;

    /**
     * 传感器电压低报警
     * 0：AlarmNotActive; 1：AlarmActive
     * 1.8 x 1
     */
    @Property("f")
    private Integer bVLowAlarmStu;

    /**
     * 当前轮胎气压 (Kpa)
     * 2.1 x 8
     * x 1.373 Kpa/bit
     */
    @Property("g")
    private Double tirePressure;

    /**
     * 当前轮胎温度 (℃)
     * 3.1 x 8
     */
    @Property("h")
    private Integer tireTemper;

    /**
     * 传感器信号丢失报警
     * 0：AlarmNotActive; 1：AlarmActive
     * 4.1 * 1
     */
    @Property("i")
    private Integer lostTransAlarmStu;

    /**
     * 轮胎漏气报警
     * 0：AlarmNotActive; 1：AlarmActive
     * 4.2 x 1
     */
    @Property("j")
    private Integer tireLeakAlarmStu;





    private static byte[] byteToBitArray(byte b) {

        byte[] array = new byte[8];

        for (int i = 7; i >=0; i--) {
            array[i] = (byte)(b & 1);
            b = (byte) (b >> 1);
        }

        return array;
    }

    private static byte bitArrayToByte(byte[] array) {

        return (byte)
                ((array[7] & 1) |
                (array[6] & 1) << 1 |
                (array[5] & 1) << 2 |
                (array[4] & 1) << 3 |
                (array[3] & 1) << 4 |
                (array[2] & 1) << 5 |
                (array[1] & 1) << 6 |
                (array[0] & 1) << 7);
    }





    /**
     * 使用 CanDataNewPacket 中的数据解析出轮胎数据。
     * 如果 CanDataNewPacket 中没有 TIRE_CAN_ID 的相关 数据， 返回空。
     * 如果解析出现任何异常，返回空。
     * @param canDataNewPacket CAN 总线报文简版携带的数据
     * @return TireDataPacket 或 null
     */
    public static TireDataPacket valueOfCanDataNewPacket(CanDataNewPacket canDataNewPacket) {

        LOGGER.debug("canDataNewPacket == null :  {} ", canDataNewPacket == null);

        if (canDataNewPacket != null &&  canDataNewPacket.getData() != null) {

            List<String> dataList = canDataNewPacket.getData().get(TIRE_CAN_ID);

            LOGGER.debug("dataList is null : {}", dataList == null);

            if (dataList != null && !dataList.isEmpty()) {

                return valueOf(dataList.get(0));
            }

        }

        return null;
    }



    public static TireDataPacket valueOf(String hexStringValue) {

        byte[] bytes = ByteUtils.hexStringToBytes(hexStringValue);
        if (bytes != null ) {
            TireDataPacket packet = new TireDataPacket();
            try {
                packet.build(bytes);
                return packet;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }




    @Override
    public void build(byte[] bytes) throws Exception {

        if (bytes == null || bytes.length != DATA_LENGTH) {
            return;
        }

        byte[] position0 = byteToBitArray(bytes[0]);

        this.tirePosition = position0[0] * 8 + position0[1] * 4 + position0[2] * 2 + position0[3];

        this.highPresAlarmStu = 0xff & position0[4];
        this.lowPresAlarmStu = 0xff & position0[5];
        this.highTeamperAlarmStu = 0xff & position0[6];
        this.bVLowAlarmStu = 0xff & position0[7];

        this.tirePressure = (0xff & bytes[1]) * tirePressureScale;
        this.tireTemper = 0xff & bytes[2];

        byte[] position3 = byteToBitArray(bytes[3]);

        this.lostTransAlarmStu = 0xff & position3[0];
        this.tireLeakAlarmStu =  0xff & position3[1];

    }

    @Override
    public byte[] unbuild() throws Exception {



        byte[] bytes = new byte[DATA_LENGTH + 2];

        bytes[0] = 0x03;
        bytes[1] = 0x40;

        byte[] pos02array = byteToBitArray((byte)0);

        // tirePosition
        if (this.tirePosition != null) {

            byte[] tireArray = byteToBitArray((byte) tirePosition.intValue());
            pos02array[0] = tireArray[4];
            pos02array[1] = tireArray[5];
            pos02array[2] = tireArray[6];
            pos02array[3] = tireArray[7];
        }

        pos02array[4] = highPresAlarmStu != null ? highPresAlarmStu.byteValue() : 0;
        pos02array[5] = lowPresAlarmStu != null ? lowPresAlarmStu.byteValue() : 0;
        pos02array[6] = highTeamperAlarmStu != null ? highTeamperAlarmStu.byteValue() : 0;
        pos02array[7] = bVLowAlarmStu != null ? bVLowAlarmStu.byteValue() : 0;


        bytes[2] = bitArrayToByte(pos02array);


        bytes[3] = tirePressure != null ? Double.valueOf(tirePressure / tirePressureScale ).byteValue() : 0;
        bytes[4] = tireTemper != null ? tireTemper.byteValue() : 0;


        byte[] pos05array = byteToBitArray((byte)0);
        pos05array[0] = lostTransAlarmStu != null ? lostTransAlarmStu.byteValue() : 0;
        pos05array[1] = tireLeakAlarmStu != null ? tireLeakAlarmStu.byteValue() : 0;

        bytes[5] = bitArrayToByte(pos05array);

        bytes[6] = (byte) 0xff;
        bytes[7] = (byte) 0xff;
        bytes[8] = (byte) 0xff;
        bytes[9] = (byte) 0xff;

        return bytes;
    }

    @Override
    public Integer length() throws Exception {
        return DATA_LENGTH;
    }

    @Override
    public String toString() {
        return "TireDataPacket{" +
                "tirePosition=" + tirePosition +
                ", highPresAlarmStu=" + highPresAlarmStu +
                ", lowPresAlarmStu=" + lowPresAlarmStu +
                ", highTeamperAlarmStu=" + highTeamperAlarmStu +
                ", bVLowAlarmStu=" + bVLowAlarmStu +
                ", tirePressure=" + tirePressure +
                ", tireTemper=" + tireTemper +
                ", lostTransAlarmStu=" + lostTransAlarmStu +
                ", tireLeakAlarmStu=" + tireLeakAlarmStu +
                '}';
    }

    public Integer getTirePosition() {
        return tirePosition;
    }

    public void setTirePosition(Integer tirePosition) {
        this.tirePosition = tirePosition;
    }

    public Integer getHighPresAlarmStu() {
        return highPresAlarmStu;
    }

    public void setHighPresAlarmStu(Integer highPresAlarmStu) {
        this.highPresAlarmStu = highPresAlarmStu;
    }

    public Integer getLowPresAlarmStu() {
        return lowPresAlarmStu;
    }

    public void setLowPresAlarmStu(Integer lowPresAlarmStu) {
        this.lowPresAlarmStu = lowPresAlarmStu;
    }

    public Integer getHighTeamperAlarmStu() {
        return highTeamperAlarmStu;
    }

    public void setHighTeamperAlarmStu(Integer highTeamperAlarmStu) {
        this.highTeamperAlarmStu = highTeamperAlarmStu;
    }

    public Integer getbVLowAlarmStu() {
        return bVLowAlarmStu;
    }

    public void setbVLowAlarmStu(Integer bVLowAlarmStu) {
        this.bVLowAlarmStu = bVLowAlarmStu;
    }

    public Double getTirePressure() {
        return tirePressure;
    }

    public void setTirePressure(Double tirePressure) {
        this.tirePressure = tirePressure;
    }

    public Integer getTireTemper() {
        return tireTemper;
    }

    public void setTireTemper(Integer tireTemper) {
        this.tireTemper = tireTemper;
    }

    public Integer getLostTransAlarmStu() {
        return lostTransAlarmStu;
    }

    public void setLostTransAlarmStu(Integer lostTransAlarmStu) {
        this.lostTransAlarmStu = lostTransAlarmStu;
    }

    public Integer getTireLeakAlarmStu() {
        return tireLeakAlarmStu;
    }

    public void setTireLeakAlarmStu(Integer tireLeakAlarmStu) {
        this.tireLeakAlarmStu = tireLeakAlarmStu;
    }
}
