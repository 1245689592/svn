package com.cm.tbox.common;

import java.util.HashMap;
import java.util.Map;

import com.cm.common.exception.FMException;
import com.cm.common.utils.LogicUtil;
import com.cm.tbox.packet.base.BasePacket;

/**
 * 国标数据包常量
 * @author yunlu
 *
 */
public class FMPackageConstant {
	/**会话状态缓存key*/
	public static final String CACHE_KEY="dispatcher";
	/**用来获取sessionid的key*/
	public static final String CACHE_KEY_SESSION="session";
	/**用来获取最后更新时间的key*/
	public static final String CACHE_KEY_SERVER="server";
	
	public static final String SESSION_CARID="carId";

	public static final String SESSION_CODE="code";
	/***/
	public static final Map<Integer,UP_COMMAND> commandMap = new HashMap<Integer,UP_COMMAND>();
	/***/
	public static final Map<Integer,REALTIME_SUBINFO> realtimeMap = new HashMap<Integer,REALTIME_SUBINFO>();
	/**注册*/
	public static final int COMMAND_REG=1;
	
	public static final int COMMAND_REAL_TIME=2;
	
	/**下行  128-255*/
	public static final int CONTROLLER_COMMAND=0xff & 0x82;
	/**命令类型*/
	/**远程升级*/
	
	/**命令类型*/
	public static enum CONTROLLER_TYPE{
		/**更新*/
		update(1),
		/**关闭*/
		shutdown(2),
		/**复位*/
		reset(3),
		/**恢复出厂设置*/
		factory_reset(4),
		/**断开连接*/
		discon(5),
		/**告警*/
		warn(6),
		/**抽样*/
		sample(7);
		
		private int type;
		
		private CONTROLLER_TYPE(int type){
			this.type=type;
		}
		public int value(){
			return this.type;
		}
	}
	
	/**响应类型类型*/
	public static final int ANSWER_SUCCESS=1;
	
	public static final int ANSWER_ERROR=2;
	
	public static final int ANSWER_REQUIRED =254;
	
	/**下行 128-255*/
	public static enum DOWN_COMMAND{
		/**控制命令*/
		controller(0xff & 0x82),
		/**设置命令*/
		setup(0xff & 0x81),
		/**查询命令*/
		query(0xff & 0x80);
		
		private int type;
		private DOWN_COMMAND(int type){
			this.type=type;
		}
		public int value(){
			return this.type;
		}
	}
	
	
	/**上行  1-127*/
	public static enum UP_COMMAND{
		/**车辆登录*/
		login(0xff & 0x01,"common.CarLoginPacket"),
		/**实时信息上报*/
		realtime(0xff & 0x02,"realtime.RealTimePacket"),
		/**补发信息上报*/
		again(0xff & 0x03,"realtime.RealTimePacket"),
		/**车辆登出*/
		loginout(0xff & 0x04,"common.CarLoginOutPacket"),
		/**心跳*/
		heart(0xff & 0x07,""),
		/**终端校时*/
		time(0xff & 0x08,""),
		/**控制命令-应答*/
		controller_answer(0xff & 0x82,""),
		/**设置命令—应答*/
		setup_answer(0xff & 0x81,""),
		/**查询命令-应答*/
		query_answer(0xff & 0x80,"QuerySetupPacket");
		
		
		private int type;
		private String beanName;
		
		private UP_COMMAND(int type,String beanName){
			this.type=type;
			this.beanName=beanName;
		}
		
		public int type(){
			return this.type;
		}
		
		public BasePacket getInstance(byte[] bytes)throws Exception{
			if(LogicUtil.isNullOrEmpty(this.beanName)){
				return null;
			}
			Class<?> clazz = Class.forName("com.cm.tbox.packet."+this.beanName);
			BasePacket packet = (BasePacket)clazz.newInstance();
			packet.build(bytes);
			return packet;
		}
	}
	
	/**实时上报数据子包类型*/
	public static enum REALTIME_SUBINFO{
		
		/**整车数据*/
		car_info(0xff & 0x01,"CarInfoPacket"),
		/**驱动电机数据*/
		drive_motor(0xff & 0x02,"DriveMotorPacket"),
		/**燃料电池数据*/
		fuel(0xff & 0x03,"FuelCellsPacket"),
		/**发动机数据*/
		motor(0xff & 0x04,"MotorPacket"),
		/**车辆位置*/
		location(0xff & 0x05,"LocationPacket"),
		/**极值数据*/
		extreme(0xff & 0x06,"ExtremumPacket"),
		/**报警数据*/
		warn(0xff & 0x07,"CarWarnPacket"),
		/**可充电储能装置电压数据*/
		voltage(0xff & 0x08,"ChargeDeviceVoltagePacket"),
		/**可充电储能装置温度数据*/
		temp(0xff & 0x09,"ChargeDeviceTempPacket"),
//		/**江铃补充数据 */
//		car_expand_more(0xff & 0x80,"CarExpandMorePacket"),
//		/**蓝海新能补充数据*/
//		car_lhxn_expand(0xff & 0x81,"CarLHXNExpandPacket"),
		/**can报文**/
		can_data(0xff & 0x87,"CanDataPacket"),
		/**can报文2**/
		can_data2(0xff & 0x88,"CanDataPacket2"),
		/**can报文简版*/
		can_data_simple(0xff & 0x89,"CanDataSimplePacket"),
		/**其他数据0x0b*/
		other_b(0xff & 0x0b,"OtherBPacket");
		
		private int type;
		private String beanName;
		
		private REALTIME_SUBINFO(int type,String beanName){
			this.type=type;
			this.beanName =beanName;
		}
		
		public int type(){
			return this.type;
		}
		
		/**反射调用静态方法获取数据实体,并放入map中*/
		public Integer build(byte[] bytes,Map<REALTIME_SUBINFO,BasePacket> map) throws Exception{
			if(LogicUtil.isNullOrEmpty(this.beanName)){
//				throw new CMException("501", "this type has no completion!");
				System.out.println("this type:"+this.type+" has no completion!");
				return 0;
			}
			Class<?> clazz = Class.forName("com.cm.tbox.packet."+beanName);
			BasePacket packet = (BasePacket)clazz.newInstance();
			packet.build(bytes);
			map.put(this, packet);
			return packet.length();
		}
	}
	
	/**
	 * 根据类型获取子包
	 * @param type
	 * @return
	 */
	public static REALTIME_SUBINFO getSubInfo(int type)throws Exception{
		if(null!=realtimeMap.get(type)){
			return realtimeMap.get(type);
		}
		for(REALTIME_SUBINFO subInfo:REALTIME_SUBINFO.values()){
			if(subInfo.type==type){
				realtimeMap.put(type, subInfo);
				return subInfo;
			}
		}
//		throw new FMException("502", "no this type:"+type+",please check this packet!");
		return  null;
	}
	
	/**
	 * 获取上行的数据包解析类
	 * @param type
	 * @return
	 */
	public static UP_COMMAND getUpPacket(int type)throws Exception{
		if(null!=commandMap.get(type)){
			return commandMap.get(type);
		}
		for(UP_COMMAND upPacket:UP_COMMAND.values()){
			if(upPacket.type==type){
				commandMap.put(type, upPacket);
				return upPacket;
			}
		}
		throw new FMException("502", "no this type:"+type+",please check this packet!");
	}
}
