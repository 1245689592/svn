package com.cm.common.utils;

import java.util.zip.ZipEntry;

public interface ZipHandler {
	/**
	 * 处理压缩包中的文件
	 * @param entry
	 * @throws Exception
	 */
	public void handleFile(ZipEntry entry)throws Exception;
}
