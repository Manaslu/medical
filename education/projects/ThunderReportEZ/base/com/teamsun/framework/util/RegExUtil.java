package com.teamsun.framework.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.teamsun.thunderreport.parse.Condition;

public class RegExUtil {
	
	//public static void main(String[] args) {
		// String content = "select from b where b='#b#'[ and c=* ]";
		// String regex = "(\\[){1}(.)*(\\*){1}(.)*(\\]){1}";
		// String replacestr = " and c=7";
//		String content = "select from b where b='#b#'[ and c=* ]";
//		String regex = "[\\[*\\]]";
//		String replacestr = " and c=7";
//		System.out.println(replaceAllUtil(content, regex, replacestr));
//		content = "wanglei wanglei wanglei";
//		regex = "wanglei";
//		replacestr = "zhengchi";
//		String s="<table width=\"98%\"><tr><td>$grid1</td></tr></table>";
//		s=replaceLayout(s,"grid1","1111111111222222");
	//}

	public static String replaceAllUtil(String oristring, String regex,
			String replacestr) {
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(oristring);
		return m.replaceAll(replacestr);
	}
	
	public static boolean regEquals(String bString,String cString,String eString){

		 String regEx = "^"+bString+".*"+eString+"$";
			Pattern oPattern = Pattern.compile(regEx, Pattern.DOTALL
					+ Pattern.CASE_INSENSITIVE);
			Matcher oMatcher = oPattern.matcher(cString);
			boolean result = oMatcher.find();
			return result;

	}

	public static String replaceFirstUtil(String oristring, String regex,
			String replacestr) {
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(oristring);
		return m.group();
	}
	
	public static String getInBracketStr(String conTx){
		String regEx = "\\{(\\w*\\s*\\w*)\\}";
		Pattern oPattern = Pattern.compile(regEx, Pattern.DOTALL
				+ Pattern.CASE_INSENSITIVE);
		Matcher oMatcher = oPattern.matcher(conTx);
		boolean result = oMatcher.find();
		if (result) {
			return oMatcher.group(1);
		}else{
			return conTx;
		}
	}

	public static String replaceLayout(String oriString,String id,String replaceString)
	{
		return replaceAllUtil(oriString,"\\$"+id,replaceString);
	}
	
	/**
	 * 替换源字符串中的参数
	 * 
	 * @param oristring
	 * @param con
	 * @param value
	 * @return
	 */
	public static String replaceCon(String oristring, Condition[] con,
			String[] value) {

		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		boolean result = false;
		for (int i = 0; i < con.length; i++) {
			String regex = "#" + con[i].getParaname() + "#";
			p = Pattern.compile(regex);
			m = p.matcher(oristring);
			result = m.find();
			if (result) {
				oristring = RegExUtil
						.replaceAllUtil(oristring, regex, value[i]);
			}

		}

		return oristring;
	}

	public static String replaceParam(String oristring, Map valueMap) {

		Pattern p = null; // 正则表达式
		Matcher m = null; // 操作的字符串
		Set keys = valueMap.keySet();
		boolean result = false;
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			String regex = "\\{" + key + "\\}";
			p = Pattern.compile(regex);
			m = p.matcher(oristring);
			result = m.find();
			if (result) {
				oristring = RegExUtil.replaceAllUtil(oristring, regex, valueMap
						.get(key).toString().trim());
			}
		}
		return oristring;
	}
	
	public static void main(String[] args) {
		Map m = new HashMap();
		m.put("U3", "xxxx");
		System.out.println(RegExUtil.replaceParam("fnDrawColor({U3})", m));
		
	}
}
