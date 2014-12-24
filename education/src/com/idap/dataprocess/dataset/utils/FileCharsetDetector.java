package com.idap.dataprocess.dataset.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @###################################################
 * @创建日期：2014-4-4 14:58:30
 * @开发人员：李广彬
 * @功能描述：测试文件的字符编码
 * @修改日志：
 * @###################################################
 */
public class FileCharsetDetector {
	public static void main(String[] args) {
		File file = new File("d:\\Users\\libin\\Desktop\\testFile\\清洗数据.txt");
		System.out.println(detectFileCharset(file));
	}

	/**
	 * 测试文件的字符编码 
	 * 默认编码为GBK
	 * 现支持编码格式：GBK UTF-16LE UTF-16BE UTF-8
	 * @param sourceFile
	 * @return 文件的字符编码
	 */
	@SuppressWarnings("resource")
	public static String detectFileCharset(File sourceFile) {
		BufferedInputStream bis =null;
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			bis = new BufferedInputStream(new FileInputStream(sourceFile));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1) {
				return charset; // 文件编码为 ANSI
			} else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE"; // 文件编码为 Unicode
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE"; // 文件编码为 Unicode big endian
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8"; // 文件编码为 UTF-8
				checked = true;
			}
			bis.reset();
			if (!checked) {
				while ((read = bis.read()) != -1) {
					if (read >= 0xF0)
						break;
					if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
							// (0x80- 0xBF),也可能在GB编码内
							continue;
						else
							break;
					} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (bis != null) {
					bis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return charset;
	}
}