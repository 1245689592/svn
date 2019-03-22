package com.cm.tbox.packet.base;
/**
 * 协议解析基础封装接口
 * @author yunlu
 *
 */
public interface BasePacket {
	
	/**
	 * 解析协议
	 * @param bytes
	 * @throws Exception
	 */
	public void build(byte[] bytes)throws Exception;
	
	/**
	 * 封装协议
	 * @return
	 * @throws Exception
	 */
	public byte[] unbuild()throws Exception;
	
	/**
	 * 消耗的长度
	 * @return
	 * @throws Exception
	 */
	public Integer length()throws Exception;
}
