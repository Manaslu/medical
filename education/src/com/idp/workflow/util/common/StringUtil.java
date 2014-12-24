package com.idp.workflow.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idp.workflow.vo.service.task.ConfigParamsVO;

/**
 * 字符串工具类
 * 
 * @author panfei
 * 
 */
public class StringUtil {

	/**
	 * 判断字符串是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		if (value == null) {
			return true;
		}
		if ("".equals(value.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否结尾，忽视大小写
	 * 
	 * @param value
	 * @param checkstr
	 * @return
	 */
	public static boolean isEndWith(String value, String checkstr) {
		if (!isEmpty(value) && !isEmpty(checkstr)) {
			if (value.length() <= checkstr.length()) {
				int begindex = checkstr.length() - value.length();
				String comparevaule = checkstr.substring(begindex);
				if (value.equalsIgnoreCase(comparevaule)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 把特定格式的字符串<br>
	 * 必须key和value对应形式，每组用^分隔，key和value用｜分隔，多个value用，分隔，全部为英文字母符号<br>
	 * 例如： isSign|Y^rule|101,102,301^inputtext|dasdaada
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @return
	 */

	@Deprecated
	public static Map<String, String[]> convertMap(String str) {
		Map<String, String[]> params = new HashMap<String, String[]>();
		if (!isEmpty(str)) {
			String[] splitpart = str.split("\\^");
			if (splitpart != null) {
				for (String part : splitpart) {
					if (!isEmpty(part)) {
						String[] keyvalues = part.split("\\|");
						if (keyvalues.length > 1) {
							String[] values = keyvalues[1].split(",");
							params.put(keyvalues[0], values);
						} else {
							params.put(keyvalues[0], null);
						}
					}
				}
			}
		}
		return params;
	}

	/**
	 * 把特定格式的字符串<br>
	 * 必须key和value对应形式，每组用^分隔，key和value用｜分隔，多个value用，分隔，全部为英文字母符号<br>
	 * 例如： isSign|Y^rule|101,102,301^inputtext|dasdaada
	 * 
	 * @param str
	 *            需要转换的字符串
	 * @return
	 */
	public static List<ConfigParamsVO> convertConfigParamsVO(String str) {
		List<ConfigParamsVO> params = new ArrayList<ConfigParamsVO>();
		if (!isEmpty(str)) {
			String[] splitpart = str.split("\\^");
			if (splitpart != null) {
				for (String part : splitpart) {
					if (!isEmpty(part)) {
						String[] keyvalues = part.split("\\|");
						if (keyvalues.length > 1) {
							// split key
							String codeTabName = keyvalues[0];
							String[] codenames = codeTabName.split("\\t");
							// split value
							String[] valuenames = keyvalues[1].split("\\t");
							String[] values = null;
							String[] valnames = null;
							if (valuenames.length > 1) {
								valnames = valuenames[1].split(",");
							}
							values = valuenames[0].split(",");
							ConfigParamsVO param = null;
							if (codenames.length > 1) {
								param = new ConfigParamsVO(codenames[0],
										codenames[1], values, valnames);
							} else {
								param = new ConfigParamsVO(codenames[0], null,
										values, valnames);
							}
							params.add(param);
						} else {
							String codeTabName = keyvalues[0];
							String[] codenames = codeTabName.split("\\t");
							ConfigParamsVO param = null;
							if (codenames.length > 1) {
								param = new ConfigParamsVO(codenames[0],
										codenames[1], null);
							} else {
								param = new ConfigParamsVO(codenames[0], null);
							}
							params.add(param);
						}
					}
				}
			}
		}
		return params;
	}

	/**
	 * 字符串转换
	 * 
	 * @param obj
	 *            需要的对象
	 * @return
	 */
	public static String convertString(Object obj) {
		if (obj == null) {
			return null;
		}
		return String.valueOf(obj);
	}
}
