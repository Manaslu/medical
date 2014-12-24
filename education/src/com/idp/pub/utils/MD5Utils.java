package com.idp.pub.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
	public static String md5(String code) {
		return md5(code, 0);
	}

	private static String md5(final String code, int len) {
		String result = code;
		for (int i = 0; i < len + 1; i++) {
			result = DigestUtils.md5Hex(Base64.encodeBase64(result.getBytes()));
		}
		return result;
	}

	public static String base64e(String code) {
		if (!StringUtils.isEmpty(code)) {
			return new String(Base64.encodeBase64(code.getBytes()));
		}
		return null;
	}

	public static String base64d(String code) {
		if (!StringUtils.isEmpty(code)) {
			return new String(Base64.decodeBase64(code));
		}
		return null;
	}

	public static void main(String[] args) {
		// 9197ef0b1827a2c18af2cfbb87d9f774
		System.out.println(md5("123", 0));
	}
}
