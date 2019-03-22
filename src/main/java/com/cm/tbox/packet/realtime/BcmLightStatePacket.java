package com.cm.tbox.packet.realtime;

import com.cm.common.utils.ByteUtils;
import com.cm.tbox.packet.CanDataNewPacket;
import com.cm.tbox.packet.base.BasePacket;
import org.mongodb.morphia.annotations.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

public class BcmLightStatePacket implements BasePacket, Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BcmLightStatePacket.class);

    /**
     * canId 为 can_0346 时，携带的是车门数据
     */
    private static final String TIRE_CAN_ID = "can_0346";
    private static final int DATA_LENGTH = 8;

    /**
     * 后备箱门开关状态
     * 0：TrunkClosed; 1：TrunkAjar
     * 1.4 -> 1
     */
    @Property("b")
    private Integer bcmTrunckAjarStatus;

    /**
     * 左前门开关状态
     * 0：DoorClosed; 1：DoorAjar
     * 1.5 -> 1
     */
    @Property("c")
    private Integer bcmFrontLeftDoorAjarStatus;

    /**
     * 右前门开关状态
     * 0：DoorClosed; 1：DoorAjar
     * 1.6 -> 1
     */
    @Property("d")
    private Integer bcmFrontRightDoorAjarStatus;

    /**
     * 左后门开关状态
     * 0：DoorClosed; 1：DoorAjar
     * 1.7 -> 1
     */
    @Property("e")
    private Integer bcmRearLeftDoorAjarStatus;

    /**
     * 右后门开关状态
     * 0：inactive
     * 1：Active
     * 1.8 -> 1
     */
    @Property("f")
    private Integer bcmRearRightDoorAjarStatus;

    /**
     * 后背门预开启提醒
     * 2.2 -> 1
     */
    @Property("g")
    private Integer bcmRearDoorPreUnlock;

    /**
     * 左前门锁状态
     * 0：Unlcoked; 1：Locked; 2：Reserved; 3：Error
     * 3.7 -> 2
     */
    @Property("h")
    private Integer bcmDoorLockStatusDrv;



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
    public static BcmLightStatePacket valueOfCanDataNewPacket(CanDataNewPacket canDataNewPacket) {

        LOGGER.debug("canDataNewPacket == null :  {} ", canDataNewPacket == null);

        if (canDataNewPacket != null &&  canDataNewPacket.getData() != null) {

            List<String> dataList = canDataNewPacket.getData().get(TIRE_CAN_ID);

            LOGGER.debug("dataList is null : {}", dataList == null);

            if (dataList != null && !dataList.isEmpty()) {

                return valueOf(dataList.get(0));
            } else {
                LOGGER.debug("dataList is null : {}");
            }

        }

        return null;
    }



    public static BcmLightStatePacket valueOf(String hexStringValue) {

        byte[] bytes = ByteUtils.hexStringToBytes(hexStringValue);
        if (bytes != null ) {
            BcmLightStatePacket packet = new BcmLightStatePacket();
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
        bcmTrunckAjarStatus = 0xff & position0[3];
        bcmFrontLeftDoorAjarStatus = 0xff & position0[4];
        bcmFrontRightDoorAjarStatus = 0xff & position0[5];
        bcmRearLeftDoorAjarStatus = 0xff & position0[6];
        bcmRearRightDoorAjarStatus = 0xff & position0[7];

        byte[] position1 = byteToBitArray(bytes[1]);

        bcmRearDoorPreUnlock = 0xff & position1[1];

        byte[] position2 = byteToBitArray(bytes[2]);

        bcmDoorLockStatusDrv = position2[6] * 2 + position2[7];

    }

    @Override
    public byte[] unbuild() throws Exception {
        return new byte[0];
    }

    @Override
    public Integer length() throws Exception {
        return DATA_LENGTH;
    }



    @Override
    public String toString() {
        return "BcmLightStatePacket{" +
                "bcmTrunckAjarStatus=" + bcmTrunckAjarStatus +
                ", bcmFrontLeftDoorAjarStatus=" + bcmFrontLeftDoorAjarStatus +
                ", bcmFrontRightDoorAjarStatus=" + bcmFrontRightDoorAjarStatus +
                ", bcmRearLeftDoorAjarStatus=" + bcmRearLeftDoorAjarStatus +
                ", bcmRearRightDoorAjarStatus=" + bcmRearRightDoorAjarStatus +
                ", bcmRearDoorPreUnlock=" + bcmRearDoorPreUnlock +
                ", bcmDoorLockStatusDrv=" + bcmDoorLockStatusDrv +
                '}';
    }

    public Integer getBcmTrunckAjarStatus() {
        return bcmTrunckAjarStatus;
    }

    public void setBcmTrunckAjarStatus(Integer bcmTrunckAjarStatus) {
        this.bcmTrunckAjarStatus = bcmTrunckAjarStatus;
    }

    public Integer getBcmFrontLeftDoorAjarStatus() {
        return bcmFrontLeftDoorAjarStatus;
    }

    public void setBcmFrontLeftDoorAjarStatus(Integer bcmFrontLeftDoorAjarStatus) {
        this.bcmFrontLeftDoorAjarStatus = bcmFrontLeftDoorAjarStatus;
    }

    public Integer getBcmFrontRightDoorAjarStatus() {
        return bcmFrontRightDoorAjarStatus;
    }

    public void setBcmFrontRightDoorAjarStatus(Integer bcmFrontRightDoorAjarStatus) {
        this.bcmFrontRightDoorAjarStatus = bcmFrontRightDoorAjarStatus;
    }

    public Integer getBcmRearLeftDoorAjarStatus() {
        return bcmRearLeftDoorAjarStatus;
    }

    public void setBcmRearLeftDoorAjarStatus(Integer bcmRearLeftDoorAjarStatus) {
        this.bcmRearLeftDoorAjarStatus = bcmRearLeftDoorAjarStatus;
    }

    public Integer getBcmRearRightDoorAjarStatus() {
        return bcmRearRightDoorAjarStatus;
    }

    public void setBcmRearRightDoorAjarStatus(Integer bcmRearRightDoorAjarStatus) {
        this.bcmRearRightDoorAjarStatus = bcmRearRightDoorAjarStatus;
    }

    public Integer getBcmRearDoorPreUnlock() {
        return bcmRearDoorPreUnlock;
    }

    public void setBcmRearDoorPreUnlock(Integer bcmRearDoorPreUnlock) {
        this.bcmRearDoorPreUnlock = bcmRearDoorPreUnlock;
    }

    public Integer getBcmDoorLockStatusDrv() {
        return bcmDoorLockStatusDrv;
    }

    public void setBcmDoorLockStatusDrv(Integer bcmDoorLockStatusDrv) {
        this.bcmDoorLockStatusDrv = bcmDoorLockStatusDrv;
    }
}
