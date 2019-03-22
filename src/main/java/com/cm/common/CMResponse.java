package com.cm.common;

import com.cm.common.constant.CommonConstants;
import com.cm.common.http.FMResponseCode;

public class CMResponse<T> {
	private String code;
	private String msg;
	private T body;
	
	public CMResponse(){
		super();
	}
	
	public CMResponse(String code, String msg){
		super();
		this.code = code;
		this.msg = msg;
		this.body=null;
	}
	
	public CMResponse(String code, String msg, T body) {
		super();
		this.code = code;
		this.msg = msg;
		this.body = body;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static CMResponse success(Object body){
		return new CMResponse(FMResponseCode.normal.toString(),"",body);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static CMResponse error(Object body){
		return new CMResponse(FMResponseCode.senderror.toString(),"",body);
	}	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}
	
}
