package com.cm.common.constant;
/**
 * 通用常量
 * @author yunlu
 *
 */
public class CommonConstants {
	/**
	 * 删除标识
	 */
	public static final int DELETED=1;
	public static final int NO_DELETED=0;
	
	/**
	 * 下发命令相关标识
	 */
	public static final int SEND_SUCCESS=0;
	public static final int SEND_NOONLINE=1;
	public static final int SEND_FAIL=2;
	/**
	 * token相关key
	 */
	public static final String TOKEN_ROLECODE_KEY="roleCode";
	
	public static final String TOKEN_VIN_KEY="vin";
	
	public static final String TOKEN_USER_KEY="userId";
	
	public static final String TOKEN_TYPE="type";
	
	public static final String TYPE_MANAGER="manager";
	
	public static final String TYPE_CAROWNER="carOwner";
	
	public static final String TOKEN_LOGINNAME_KEY="loginName";
	
	public static final String TOKEN_LOGINIP_KEY="loginIP";
	
	public static final String TOKEN_REPORT_EMAIL="reportEmail";
	/**车辆状态相关*/
	public static final int HAS_REG=0;
	public static final int NO_REG=1;
	
	public static final int IS_ONLINE=1;
	public static final int NO_ONLINE=0;
	/**redis相关缓存*/
	public static final String CACHE_LOCATION="location";
	
	public static final String CACHE_ACC="acc";
	
	/** 上次告警信息记录*/
	public static final String CACHE_KEY_WARN="warn";
	
	public static final String PRE_WARN_TYPE="warn-";
	
	/**
	 * 资源文件配置前缀读取
	 */
	public static final String PRE_REDIS_COMMON="COMMON";
	
	/**用来缓存vin和客户id*/
	public static final String CACHE_KEY_COMPANY="company";
	
	public static final String CACHE_KEY_ROLEID="role.";
	
	public static final String CACHE_KEY_STATIC="static";
	
	public static final String ADMIN_ID = "ADMIN_ID";
	public static final String ADMIN_ROLE_ID = "A";
	
	public static final String POLYGON = "polygon_";
	
	public static final String AREA_REPORT = "area_report";
	//mongo表前缀
	
	public static final String HISTORY="";
	
	public static final String MS_DATA="ms_";
	
	/**日志相关*/
	public static enum LOG_TYPE{
		ADD(1,"新增",1),
		UPDATE(2,"修改",1),
		DELETE(3,"删除",1),
		QUERY(4,"查询",0),
		EXPORT(5,"导出",1),
		SEND(6,"发送命令",1),
		LOGIN(7,"登陆",1),
		LOGINOUT(8,"登出",1),
		SIGNUP(9,"注册",1),
		DOWN(10,"下载",1);
		
		private int key;
		private String content;
		private int level; 
		
		private LOG_TYPE(int key,String content,int level){
			this.key=key;
			this.content=content;
			this.level=level;
		}
		
		public int key(){
			return key;
		}
		public String content(){
			return content;
		}
		public int level(){
			return level;
		}
	}
	
}
