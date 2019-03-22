package com.cm.common.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class MACUtil {
	/**   
     * 生成签名数据 
     *    
     * @param data 待加密的数据   
     * @param key  加密使用的key   
     * @param code HmacMD5 HmacSHA1 HmacSHA256 HmacSHA516 HmacSHA384
     * @throws InvalidKeyException   
     * @throws NoSuchAlgorithmException   
     */    
    public static byte[] encode(String data,String key,String code) throws Exception{  
    	byte[] keyBytes=key.getBytes();  
        SecretKeySpec signingKey = new SecretKeySpec(keyBytes, code);     
        Mac mac = Mac.getInstance(code);     
        mac.init(signingKey);   
        return mac.doFinal(data.getBytes());
    }
}
