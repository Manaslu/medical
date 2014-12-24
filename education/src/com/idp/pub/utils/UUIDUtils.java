package com.idp.pub.utils;

import java.util.UUID;

/**
 * 
 * @author panfei
 * 
 */
public abstract class UUIDUtils {

	public static final int PK_LEN = 32;

	public static String pk(String fg) {
		if (StringUtils.isEmpty(fg)) {
			fg = "k";
		}
		return pk(fg, PK_LEN);
	}

	public static String getDefPk() {
		return pk("", PK_LEN);
	}

	private static String pk(String fg, int number) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		uuid = fg.concat(uuid);
		return uuid.substring(0, number);
	}
}