package com.cm.common.http;
/**tsp 响应code*/
public enum FMResponseCode {
	emailexist("106","Email is allready existed","邮箱已被注册"),
	phoneexist("107","Phone is allready existed","手机号已存在"),
	normal("200","success.",""),
	accountnotexist("101","user is not exist.","用户不存在"),
	accountremoved("102","user is stop.","用户已停用"),
	passworderror("103","password is error.","密码错误"),
	noroleforaccount("104","user has no role.","用户没有任何的角色"),
	sysbusy("500","system busy!","系统繁忙"),
	syserror("400","system busy!","系统繁忙"),
	paramserror("301","params error!","请求参数错误"),
	senderror("302","send fail!","发送失败"),
	notonline("319","this car is not online","当前车辆不在线"),
	cpsperror("303","cp/sp error!","cpsp异常"),
	tokenerror("304","token is null!","无token"),
	tokenfail("305","verify token fail!","token验证失败"),
	vinisnull("306", "vin is null!","vin为空"),
	vinexisted("307", "vin is allready existed!","vin已存在"),
	vinisnotexisted("308", "vin is not existed!","vin不存在"),
	companynull("309", "company is null!",""),
	accountnameexist("310", "account with this account name is already existed!","账号名已被使用"),
	userrolerestriction("311", "company user can not manage users!",""),
	arraywrongval("312", "provided array includes non-existed values!","数据中有无效的值"),
	idnotexist("313", "provided id is not existed!","id不存在"),
	vinnotfound("314", "there is no car for current user!","当前用户没有车辆"),
	notregistered("315", "account is not registered yet!","账号未注册"),
	alreadyregistered("316", "account is already registered!","账号已注册"),
	filetimeout("317", "the period of validity of this file is timeout!","文件已失效"),
	noData("318","find nothing!","查询不到任何数据"),
	noTerminalSn("319","this terminalSn is non-existent!","终端sn不存在!"),
	checkFail("320","check fail!","校验参数失败!"),
	usedTerminalSn("321","this terminalSn is used!","此终端sn已被其他车辆使用"),
	uploadFail("322","upload fail!","批量终端导入失败"),
	emptyPloygon("323","empty ploygon!","区域边界为空"),
	simcodeexisted("324","SIM code is allready existed","SIM号已存在!"),
	terminalnoexisted("325","TerminalNo code is allready existed","终端编号已存在!"),
	terminalsnexisted("326","TerminalSn is allready existed","终端序列号已存在!"),
	superadminexisted("327","SuperAdmin is allready existed","超级管理员已存在!"),
	multexception("328","","终端号、序列号或SIM号已存在"),
	vehicleisused("329","","车辆已被关联"),
	platnoisused("330","","车牌号已被使用"),
	autobeanfail("331","","动态添加bean失败"),
	batchtaskerror("332","","批量任务下发包校验失败"),
	versionexist("333","","版本号已存在"),
	rolenameexist("334","","角色名称已存在"),
	batchtaskexist("335","","批量任务已存在"),
	batchtaskcannotdo("336","","无权操作"),
	batchtasknotdoneisfull("336","","没有可用的升级端口,暂时不能创建任务,请稍候"),
	vehicleisonline("337","","当前车辆在线"),
	terminalIsUsed("338","","此终端已被其他车辆使用");
	
	
	private String code;
	private String  msg;
	private String cnMsg;
	
	private FMResponseCode (String code,String msg,String cnMsg){
		this.code=code;
		this.msg=msg;
		this.cnMsg=cnMsg;
	}
	
	public String getCode(){
		return this.code;
	}
	public String getMsg(){
		return this.msg;
	}
	public String getCnMsg(){
		return this.cnMsg;
	}
	public String toString(){
		return this.code;
	}
}
