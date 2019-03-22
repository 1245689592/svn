package com.cm.common.utils;

import java.util.UUID;

public abstract class UUIDUtils {


	/**
	 * 生成新的UUID 32位串
	 * @return
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
