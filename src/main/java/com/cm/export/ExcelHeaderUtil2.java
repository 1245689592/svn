package com.cm.export;

import java.util.ArrayList;
import java.util.List;
import com.cm.export.HistoryToExcel2.Type;
public class ExcelHeaderUtil2 {
	
	public static List<String> getHeader(Type type) {
		List<String> tableHead = new ArrayList<String>();
//		tableHead.add("vin");
		tableHead.add("时间");
		tableHead.add("服务器接收时间");
		tableHead.add("数据类型");
		switch (type){
			case location:
				tableHead.add("定位状态");
				tableHead.add("经度");
				tableHead.add("纬度");
				/*tableHead.add("经");
				tableHead.add("纬");*/
				break;
			case cellTemp:
				tableHead.add("动力蓄电池包温度探针总数");
				tableHead.add("动力蓄电池包总数");
				tableHead.add("电池包序号");
				tableHead.add("探针总数");
				tableHead.add("温度列表(有效值-40-125 摄氏度)");
				break;
			case cellVoltage:
				tableHead.add("单体蓄电池总数");
				tableHead.add("动力蓄电池包总数");
				tableHead.add("动力蓄电池包序号");
				tableHead.add("该包单体蓄电池总数");
				tableHead.add("单体蓄电池电压值列表(有效值0-15V)");
				break;
			case carInfo:
				tableHead.add("车速");
//				tableHead.add("驱动状态");
//				tableHead.add("里程(0-999999.9km)");
//				tableHead.add("车速(km/h)");
				tableHead.add("里程");
				tableHead.add("档位");
				tableHead.add("加速踏板行程值(%)");
				tableHead.add("刹车踏板行程(%)");
//				tableHead.add("制动状态");
				tableHead.add("充电状态");
				tableHead.add("驱动状态");
				tableHead.add("制动状态");
				//新增
				tableHead.add("车辆状态");
				tableHead.add("运行模式");
				tableHead.add("总电压");
				tableHead.add("总电流");
				tableHead.add("soc");
				tableHead.add("dc状态");
				tableHead.add("绝缘电阻");
				
				break;
			case extremum:
				tableHead.add("最高电压-蓄电池序号");
				tableHead.add("最高电压-电压值");
				tableHead.add("最高电压-电池包序号");
				tableHead.add("最低电压-电池包序号");
				tableHead.add("最低电压-电压值");
				tableHead.add("最低电压-蓄电池序号");
				tableHead.add("最高温度");
				tableHead.add("最高温度-电池包序号");
				tableHead.add("最高温度-蓄电池序号");
				tableHead.add("最低温度-电池包序号");
				tableHead.add("最低温度-蓄电池序号");
				tableHead.add("最低温度");
			/*	tableHead.add("电池单体电压最高值(0-15V)");
				tableHead.add("最高温度动力蓄电池单体所在电池包序号");
				tableHead.add("最高温度(-40-125摄氏度)");
				tableHead.add("最低电压动力蓄电池单体所在电池包序号");
				tableHead.add("最低电压动力蓄电池单体序号");
				tableHead.add("电池单体电压最低值(0-15 V)");
				tableHead.add("最高温度动力蓄电池单体所在电池包序号");
				tableHead.add("最高温度动力蓄电池单体序号");
				tableHead.add("最低温度动力蓄电池单体所在电池包序号");
				tableHead.add("最低温度动力蓄电池单体序号");
				tableHead.add("绝缘电子");*/
				break;
			case expand:
				tableHead.add("剩余电量(%)");
				tableHead.add("续航里程(km 有效值0-6553.4)");
				tableHead.add("门状态");
				tableHead.add("ACC状态");
				tableHead.add("天窗");
				tableHead.add("后备箱");
				tableHead.add("左后门");
				tableHead.add("右后门");
				tableHead.add("左前门");
				tableHead.add("右前门");
//				tableHead.add("点火/熄火");
				tableHead.add("蓄电池电压(有效值0-25.4V)");
				tableHead.add("车速(有效值0-220km/h)");
				tableHead.add("里程数(有效值0-999999.9km)");
				tableHead.add("网络信号强度(有效值0-31 0位无信号 31信号最强)");
				tableHead.add("其他状态是否有效");
				tableHead.add("车辆类型");
				tableHead.add("充电状态");
				tableHead.add("后备箱锁");
				tableHead.add("左后门锁");
				tableHead.add("右后门锁");
				tableHead.add("左前门锁");
				tableHead.add("右前门 锁");
				break;
			case hgExpand:
				tableHead.add("预充电电压(有效值范围：0V～1200V)");
				tableHead.add("剩余充电时间(有效值范围： 0min～65535min)");
				tableHead.add("车辆续驶里程(有效值范围： 0km～600km)");
				tableHead.add("电池水温(有效值范围：-50℃～+200℃)");
				tableHead.add("前电机故障等级");
				tableHead.add("前电机故障代码");
				tableHead.add("前电机硬件故障代码列表");
				tableHead.add("后电机故障等级");
				tableHead.add("后电机故障代码");
				tableHead.add("后电机硬件故障代码列表");
				tableHead.add("*BMS充电故障等级");
				tableHead.add("BMS充电故障代码");
				tableHead.add("其他状态是否有效");
				tableHead.add("是否P档");
				tableHead.add("充电许可");
				tableHead.add("热管理许可");
				tableHead.add("BMS充电状态");
				tableHead.add("充电枪");
				tableHead.add("急停开关");
				tableHead.add("危险告警灯");
				tableHead.add("近光灯");
				break;
//			case zxExpand:
//				tableHead.add("电机转矩");
//				tableHead.add("充电电流");
//				tableHead.add("充电电压");
////				tableHead.add("电机交流侧电流");
////				tableHead.add("电机交流侧电压");
////				tableHead.add("直流侧电压");
////				tableHead.add("DC工作状态");
////				tableHead.add("DC输出电流");
////				tableHead.add("DC输出电压");
////				tableHead.add("DC工作温度");
////				tableHead.add("电池箱平均温度");
////				tableHead.add("剩余充电时间");
////				tableHead.add("允许最大放电电流");
////				tableHead.add("允许最大回充电流");
//				tableHead.add("最高允许充电端电压");
//				tableHead.add("最高允许充电电流");
//				tableHead.add("充电机输出电压");
//				tableHead.add("充电机输出电流");
//				tableHead.add("正极绝缘阻值 ");
//				tableHead.add("负极绝缘阻值");
//				tableHead.add("最大驱动转矩");
//				tableHead.add("最大驱动转速");
//				tableHead.add("电机目标输出转矩");
//				tableHead.add("电机目标输出转速");
//				tableHead.add("接触器前端电压");
//				tableHead.add("接触器后端电压");
////				tableHead.add("SOH");
////				tableHead.add("转向系统故障码");
//				tableHead.add("加热控制");
//				tableHead.add("电池工作状态");
//				tableHead.add("放电继电器状态");
//				tableHead.add("充电继电器状态");
//				tableHead.add("怠机");
//				tableHead.add("can/acc");
//				tableHead.add("预充接触器");
//				tableHead.add("开波");
//				tableHead.add("电机模式");
//				tableHead.add("主接触器状态");
////				tableHead.add("充电循环次数");
//				break;
			case expand1:
				tableHead.add("电机转矩(-2000Nm～2000Nm)");
				tableHead.add("充电电流(0A～500A)");
				tableHead.add("充电电压( 0V～600V)");
				tableHead.add("最高允许充电端电压(0～6553.4V)");
				tableHead.add("最高允许充电电流(0～6553.4A)");
				tableHead.add("充电机输出电压(0～6553.4V)");
				tableHead.add("充电机输出电流(0～6553.4A)");
				tableHead.add("充电循环次数");
				break;	
			case expand2:
				tableHead.add("正极绝缘阻值(0kΩ～6553.4kΩ)");
				tableHead.add("负极绝缘阻值(0kΩ～6553.4kΩ)");
				tableHead.add("最大驱动转矩(-32000NM～33534NM)");
				tableHead.add("最大驱动转速(-32000rpm～33534rpm)");
				tableHead.add("电机目标输出转矩(-32000NM～33534NM)");
				tableHead.add("电机目标输出转速(-32000rpm～33534rpm)");
				tableHead.add("接触器前端电压(-32000V～33534V)");
				tableHead.add("接触器后端电压(-32000V～33534V)");
				tableHead.add("SOH(有效值范围：0-1000（表示0～100.0%)，最小计量单元：0.1%)");
				tableHead.add("加热控制");
				tableHead.add("电池工作状态");
				tableHead.add("放电继电器状态");
				tableHead.add("充电继电器状态");
				tableHead.add("怠机");
				tableHead.add("canacc");
				tableHead.add("预充接触器");
				tableHead.add("开波");
				tableHead.add("电机模式");
				tableHead.add("主接触器状态");
				break;	
			case powerCellWarn:
				tableHead.add("温度差异报警");
				tableHead.add("电池极柱高温报警");
				tableHead.add("动力蓄电池包过压报警 ");
				tableHead.add("动力蓄电池包欠压报警");
				tableHead.add("soc低报警");
				tableHead.add("单体蓄电池过压报警");
				tableHead.add("单体蓄电池欠压报警");
				tableHead.add("soc过低报警");
				tableHead.add("soc太高报警");
				tableHead.add("动力蓄电池包不匹配报警 ");
				tableHead.add("蓄电池一致性差报警");
				tableHead.add("绝缘警告");
				break;
			case cellWarn:
				tableHead.add("故障标志位");
				tableHead.add("电池系统警报");
				tableHead.add("电池总电压过高");
				tableHead.add("电池总电压过低");
				tableHead.add("绝缘状态低");
				tableHead.add("单体电压过低");
				tableHead.add("单体电压过高");
				tableHead.add("温度过低");
				tableHead.add("温度过高");
				tableHead.add("外接充电严重过流");
				tableHead.add("放电电流严重过大");
				tableHead.add("单体电池电压差过大");
				tableHead.add("温度严重不均衡");
				tableHead.add("电池总电压高");
				tableHead.add("电池总电压低");
				tableHead.add("绝缘状态太低");
				tableHead.add("温度低");
				tableHead.add("温度高");
				tableHead.add("外接充电过流");
				tableHead.add("放电电流过大");
				tableHead.add("单体电池电压差大");
				tableHead.add("内网通讯故障");
				tableHead.add("电压采集故障");
				tableHead.add("电流采集故障");
				tableHead.add("单体过充");
				tableHead.add("单体过放");
				tableHead.add("温升过快");
				tableHead.add("短路保护");
				break;
			case carInfoWarn:
				tableHead.add("动力电池故障");
				tableHead.add("电机过热报警");
				tableHead.add("DC故障");
				tableHead.add("整车故障");
				tableHead.add("动力电池切断灯");
				tableHead.add("运行准备就绪故障");
				tableHead.add("档位故障");
				tableHead.add("油门故障");
				tableHead.add("电机严重过热报警");
				tableHead.add("预充故障");
				tableHead.add("主接触器故障");
				tableHead.add("IGBT故障");
				tableHead.add("电机过流");
				tableHead.add("电机严重过压");
				tableHead.add("电机严重欠压");
				tableHead.add("电机超速");
				tableHead.add("电机故障");
				break;
			case chargerWarn:
				tableHead.add("硬件故障");
				tableHead.add("通信超时");
				tableHead.add("充电机温度过高警告");
				tableHead.add("充电机温度过高保护");
				tableHead.add("输入电压异常");
				tableHead.add("过流");
				tableHead.add("漏电");
				tableHead.add("电池电极反接");
				break;
			case motorWarn:
				tableHead.add("加速过电流");
				tableHead.add("减速过电流");
				tableHead.add("恒速过电流");
				tableHead.add("加速过电压");
				tableHead.add("减速过电压");
				tableHead.add("恒速过电压");
				tableHead.add("欠压故障");
				tableHead.add("驱动器过载");
				tableHead.add("电机过载");
				tableHead.add("输出缺相");
				tableHead.add("驱动器过热");
				tableHead.add("驱动器过热预报警");
				tableHead.add("EEPORM读写故障");
				tableHead.add("电机对地短路故障");
				tableHead.add("快速限流故障");
				tableHead.add("电机过速度");
				tableHead.add("速度偏差过大");
				tableHead.add("电机过热预报警");
				tableHead.add("CAN模块初始化故障");
				tableHead.add("CAN通讯故障");
				tableHead.add("编码器故障");
				tableHead.add("电流采样异常");
				break;
			case dcWarn:
				tableHead.add("温度故障");
				tableHead.add("输出过流");
				tableHead.add("输出过压");
				tableHead.add("输出欠压");
				tableHead.add("输入过压");
				tableHead.add("输入欠压");
				tableHead.add("输出短路");
				tableHead.add("硬件故障");
				tableHead.add("通信超时");
				break;
			case lhxnWarn:
				tableHead.add("低压电源低");
				tableHead.add("低压电源高");
				tableHead.add("电机系统自检故障");
				tableHead.add("电机旋变故障");
				tableHead.add("电机控制器过流状态");
				tableHead.add("电机控制器过压");
				tableHead.add("电机控制器欠压");
				tableHead.add("真空度报警状态");
				tableHead.add("系统故障状态");
				tableHead.add("EPS故障状态");
				tableHead.add("加速踏板故障状态");
				tableHead.add("电机控制器通信故障");
				tableHead.add("电池系统通信故障");
				tableHead.add("TCU通信故障");
				tableHead.add("浮动插头状态");
				tableHead.add("电机使能反馈不一致故障");
				tableHead.add("主接触器反馈不一致故障");
				tableHead.add("扭矩反馈不一致");
				tableHead.add("ABS故障状态");
				tableHead.add("BMS自检");
				tableHead.add("高压控制盒故障");
				tableHead.add("VCU通信故障");
				tableHead.add("维修开关故障");
				break;	
			case lhxnExpand:
				tableHead.add("sim卡连接状态");
				tableHead.add("TF卡连接状态");
				tableHead.add("GSM天线连接状态");
				tableHead.add("GPS天线连接状态");
				tableHead.add("整车工作模式");
				tableHead.add("tbox软件版本");
				tableHead.add("实际转矩反馈");
				tableHead.add("电机控制器版本");
				tableHead.add("转矩需求命令");
				tableHead.add("vcu软件版本");
				tableHead.add("真空值(单元：kPa 范围：-3000至3000)");
				tableHead.add("ptc温度(单元：deg 范围：-100至155)");
				tableHead.add("单次里程数(单元：km 范围：0至6000)");
				tableHead.add("充电剩余时间(单元：h 范围：0至25.5)");
				tableHead.add("电池包总里程(单元：km 范围：0至1677721.5)");
				tableHead.add("电池组平均温度(单元：deg 范围：-100至155)");
				tableHead.add("bms软件版本");
				tableHead.add("电池包充电次数");
				tableHead.add("单体电池压差值(单元：V 范围：0至6.5535)");
				tableHead.add("电池剩余里程(单元：km 范围：0至250)");
				tableHead.add("电池可用最大容量(单元：AH)");
				tableHead.add("电池生产厂代号");
				tableHead.add("电池箱序号");
				tableHead.add("最高允许输出端电压(单元：V 范围：0至655.35)");
				tableHead.add("最高允许输出端电流(单元：A 范围：0至655.35)");
				tableHead.add("慢充充电机工作模式");
				tableHead.add("慢充充电机控制字");
				tableHead.add("快充充电机控制");
				tableHead.add("最高允许充电端电压(单元：V 范围：0至655.35)");
				tableHead.add("最高允许充电端电流(单元：A 范围：0至655.35)");
				tableHead.add("电池组标称AH(单元：AH)");
				tableHead.add("电池组实际AH(单元：AH)");
				tableHead.add("电池节数");
				tableHead.add("单节电池最低保护电压(单元：V 范围：0至65.535)");
				tableHead.add("风机状态");
				tableHead.add("电热接触器状态");
				tableHead.add("维修开关连接状态");
				tableHead.add("总负辅助触点信号");
				tableHead.add("总正辅助触点信号");
				tableHead.add("充电正接触器状态");
				tableHead.add("总负接触器状态");
				tableHead.add("总正接触器状态");
				tableHead.add("电压热辅助触点信号");
				tableHead.add("快充接触器状态");
				tableHead.add("快充辅助触点信号");
				tableHead.add("bms厂商");
				break;
				//2016-12-20 新增
			case driveMotorRecord:
				tableHead.add("驱动电机数量");
				tableHead.add("驱动电机顺序号");
				tableHead.add("驱动电机状态");
				tableHead.add("驱动电机控制器温度(单位：℃ 范围：-40至210)");
				tableHead.add("驱动电机转速 (单位：r/min 范围：-20000至45531)");
				tableHead.add("驱动电机转矩 (单位：N*m 范围：-2000至4553.1)");
				tableHead.add("驱动电机温度(单位：℃ 范围：-40至210)");
				tableHead.add("电机控制器输入电压(单位：V 范围：0至6000)");
				tableHead.add("电机控制器直流母线电流(单元：A 范围：-1000至1000)");
				break;
			case fuelCellsRecord:
				tableHead.add("燃料电池电压 (单位:V范围:0至2000)");
				tableHead.add("燃料电池电流 (单位:A 范围:0至2000)");
				tableHead.add("燃料消耗率 (单位:kg/100km 范围:0至600)");
				tableHead.add("燃料电池温度探针总数 范围：0至65531");
				tableHead.add("探针温度值 (单位:℃ 范围:-40至200)");
				tableHead.add("氢系统中最高温度(单位:℃ 范围:-40至200)");
				tableHead.add("氢系统中最高温度探针代号");
				tableHead.add("氢气最高浓度 (单位:ppm 范围：0至60000)");
				tableHead.add("氢气最高浓度传感器代号");
				tableHead.add("氢气最高压力 (单位:Mpa 范围：0至100)");
				tableHead.add("氢气最高压力传感器代号");
				tableHead.add("高压DC/DC状态 0x01：工作；0x02：断开 ");
				break;
			case motorPacket:
				tableHead.add("发动机状态");
				tableHead.add("曲轴转速");
				tableHead.add("燃料消耗率");
				break;
			case chargeDeviceVoltage:
				tableHead.add("子系统个数");
				tableHead.add("可充电储能子系统号");
				tableHead.add("可充电储能装置电压");
				tableHead.add("可充电储能装置电流");
				tableHead.add("单体电池总数");
				tableHead.add("本帧起始电池序号");
				tableHead.add("本帧单体电池总数");
				tableHead.add("单体电池电压");
				break;
			case chargeDeviceTemp:
				tableHead.add("子系统个数");
				tableHead.add("系统编号");
				tableHead.add("探针总数");
				tableHead.add("温度值");
				break;
			case carWarnCommon:
				tableHead.add("最高报警等级");
				tableHead.add("温度差异报警");
				tableHead.add("电池高温报警");
				tableHead.add("车载储能装置类型过压报警");
				tableHead.add("车载储能装置类型欠压报警");
				tableHead.add("SOC低报警");
				tableHead.add("单体电池过压报警");
				tableHead.add("单体电池欠压报警");
				tableHead.add("SOC过高报警");
				tableHead.add("SOC跳变报警");
				tableHead.add("可充电储能系统不匹配报警");
				tableHead.add("电池单体一致性差报警");
				tableHead.add("绝缘报警");
				tableHead.add("DC-DC温度报警");
				tableHead.add("制动系统报警");
				tableHead.add("DC-DC状态报警");
				tableHead.add("驱动电机控制器温度报警");
				tableHead.add("高压互锁状态报警");
				tableHead.add("驱动电机温度报警");
				tableHead.add("车载储能装置类型过充");
				break;
			case vehilceAndTerminal:
				tableHead.add("终端号");
				tableHead.add("SIM卡号");
				tableHead.add("SN号");
				tableHead.add("VIN");
				tableHead.add("品牌");
				tableHead.add("车型");
				tableHead.add("车牌");
				tableHead.add("发动机编号");
				break;
			default:
				break;
		}
		return tableHead;
	}
}
