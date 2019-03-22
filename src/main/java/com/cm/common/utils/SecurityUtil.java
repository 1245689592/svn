package com.cm.common.utils;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

public class SecurityUtil {
	private static final String HMAC_SHA1 = "HmacSHA1";

	public static String weatherEncode(String data, String key) throws Exception {
		byte[] bytes = MACUtil.encode(data, key, HMAC_SHA1);
		return URLEncoder.encode(new BASE64Encoder().encode(bytes), "UTF-8");
	}

	@Deprecated
	private static String appendEqualSign(String s) {
		int len = s.length();
		int appendNum = 8 - (int) (len / 8);
		for (int n = 0; n < appendNum; n++) {
			s += "%3D";
		}
		return s;
	}

	/**
	 * md5摘要 注意：返回的字符串为小写
	 * 
	 * @param strSrc
	 * @return
	 * @throws Exception
	 */
	public static String md5(String strSrc) throws Exception {
		byte[] data = strSrc.getBytes(Charset.forName("utf-8"));
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		byte b[] = md5.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	/**
	 * sha-1摘要 注意：返回的字符串为小写
	 * 
	 * @param strSrc
	 * @return
	 * @throws Exception
	 */
	public static String sha1(String strSrc) throws Exception {
		byte[] data = strSrc.getBytes(Charset.forName("utf-8"));
		MessageDigest md5 = MessageDigest.getInstance("SHA-1");
		md5.update(data);
		byte b[] = md5.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	public static String decry_RC4(byte[] data, String key) {
		if (data == null || key == null) {
			return null;
		}
		return new String(RC4Base(data, key));
	}

	public static String decry_RC4(String data, String key) {
		if (data == null || key == null) {
			return null;
		}
		return new String(RC4Base(HexString2Bytes(data), key));
	}

	public static byte[] encry_RC4_byte(String data, String key) {
		if (data == null || key == null) {
			return null;
		}
		byte b_data[] = data.getBytes();
		return RC4Base(b_data, key);
	}

	public static String encry_RC4_string(String data, String key) {
		if (data == null || key == null) {
			return null;
		}
		return toHexString(new String(encry_RC4_byte(data, key)));
	}

	private static byte[] initKey(String aKey) {
		byte[] b_key = aKey.getBytes();
		byte state[] = new byte[256];

		for (int i = 0; i < 256; i++) {
			state[i] = (byte) i;
		}
		int index1 = 0;
		int index2 = 0;
		if (b_key == null || b_key.length == 0) {
			return null;
		}
		for (int i = 0; i < 256; i++) {
			index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
			byte tmp = state[i];
			state[i] = state[index2];
			state[index2] = tmp;
			index1 = (index1 + 1) % b_key.length;
		}
		return state;
	}

	private static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch & 0xFF);
			if (s4.length() == 1) {
				s4 = '0' + s4;
			}
			str = str + s4;
		}
		return str;// 0x表示十六进制
	}

	private static byte[] HexString2Bytes(String src) {
		int size = src.length();
		byte[] ret = new byte[size / 2];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < size / 2; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	private static byte uniteBytes(byte src0, byte src1) {
		char _b0 = (char) Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (char) (_b0 << 4);
		char _b1 = (char) Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	private static byte[] RC4Base(byte[] input, String mKkey) {
		int x = 0;
		int y = 0;
		byte key[] = initKey(mKkey);
		int xorIndex;
		byte[] result = new byte[input.length];

		for (int i = 0; i < input.length; i++) {
			x = (x + 1) & 0xff;
			y = ((key[x] & 0xff) + y) & 0xff;
			byte tmp = key[x];
			key[x] = key[y];
			key[y] = tmp;
			xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
			result[i] = (byte) (input[i] ^ key[xorIndex]);
		}
		return result;
	}
	
	public static void main(String[] args) {
		String data=SecurityUtil.decry_RC4("test", "futuremove");
		System.out.println(data);
		System.out.println(SecurityUtil.encry_RC4_string(data, "futuremove"));
	}
}
