package com.cm.common.exception;

import java.io.Serializable;
/**
 * 多个异常测试
 * @author yuchen.xue
 * 2016年11月24日 下午4:02:34
 */
public class FMMultiException  extends RuntimeException implements Serializable {
	private static final long serialVersionUID = -7964200117311595577L;
	/**响应数据*/
	private String body;
	private String code;
	public FMMultiException(String code,String body){
		this.body=body;
		this.code=code;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public static void throwEx(String code,String body) throws Exception{
		throw new FMMultiException(code, body);
	}
}
