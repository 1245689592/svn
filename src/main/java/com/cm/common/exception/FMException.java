package com.cm.common.exception;

import com.cm.common.http.FMResponseCode;

public class FMException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**流水id*/
	private String code;

	public FMException(String code,String message) {
		super(message);
		this.code=code;
	}
	
	public FMException(FMResponseCode code) {
		super(code.getCnMsg());
		this.code=code.toString();
	}
	
	public FMException(FMResponseCode code,String message) {
		super(message);
		this.code=code.toString();
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public static void throwEx(String code,String msg) throws Exception{
		throw new FMException(code, msg);
	}
	
	public static void throwEx(FMResponseCode code,String msg) throws Exception{
		throw new FMException(code, msg);
	}
	
	public static void throwEx(FMResponseCode code) throws Exception{
		throw new FMException(code, code.getCnMsg());
	}
}
