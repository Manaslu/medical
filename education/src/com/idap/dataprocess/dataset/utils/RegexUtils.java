package com.idap.dataprocess.dataset.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @###################################################
 * @创建日期：2014-4-4 14:58:30
 * @开发人员：李广彬
 * @功能描述：常用正则表达式
 * @修改日志：
 * @###################################################
 */
public class RegexUtils {
	// struts2 内校验框架使用 较严格
	private static final String email_ptn = "^['_a-z0-9-\\+]+(\\.['_a-z0-9-\\+]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2}|aero|arpa|asia|biz|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|nato|net|org|pro|tel|travel|xxx)$";
	// 只校验email的格式 // 内校验框架使用
	private static final String email_ptn1 = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
	private final static String mobile_ptn = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
	private final static String phone_ptn = "^(\\d{3,4}\\-)?\\d{7,8}$";// 验证电话号码-正确格式为："XXX-XXXXXXX"、"XXXX-XXXXXXXX"、"XXX-XXXXXXX"、"XXX-XXXXXXXX"、"XXXXXXX"和"XXXXXXXX"。
	private final static String pass_ptn = "^([\\!\\\\\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\.\\/\\:\\;\\<\\=\\>\\?\\@\\[\\]\\^\\_\\`\\{\\|\\}\\~\\|\\\\\\w]+$)";//去除一些特殊化字符 单词字符：[a-zA-Z_0-9] 
	private final static String pass_ptn1 = "([\\!\\\\\"\\#\\$\\%\\&\\'\\(\\)\\*\\+\\,\\-\\.\\/\\:\\;\\<\\=\\>\\?\\@\\[\\]\\^\\_\\`\\{\\|\\}\\~\\|\\\\\\w\\\\\\s])";//去除一些特殊化字符 单词字符：[a-zA-Z_0-9] 空白字符：[ \t\n\x0B\f\r]
	private final static String password_ptn = "^[a-zA-Z]\\w{5,17}$";// 以字母开头，长度在6~18之间，只能包含字符、数字和下划线。
	private final static String nickname_ptn = "^[\u4e00-\u9fa5\\w-]*$";
	private final static String length_ptn = ".";
	private final static String date_ptn = "^(\\d){2,4}-(\\d){1,2}-(\\d){1,2}$";// 支持类型 YYYY-MM-DD\YY-MM-DD\YY/MM/DD\ YYYY/MM/DD
	private final static String datetime_ptn = "^(\\d){2,4}-(\\d){1,2}-(\\d){1,2} (\\d){1,2}\\:(\\d){1,2}$";// 支持类型\ YYYY-MM-DD HH:MM\YYYY/MM/DD
	private final static String number_ptn = "^\\d+[\\.]{0,1}\\d*$";// 支持类型 XXX\XXX.XXX
	private final static String number_positive_ptn = "^\\+?[1-9][0-9]*$";// 只能输入非零的正整数
	private final static String number_negative_ptn = "^\\-?[1-9][0-9]*$";// 只能输入非零的负整数
	private final static String number_s_zero_ptn = "^(0|[1-9][0-9]*)$";// 只能输入零和非零开头的数字
	private final static String float_2_ptn = "^[0-9]+(.[0-9]{2})?$";// 只能输入有两位小数的正实数

	private final static String special_string_1_ptn = "[^%&',;=?$\\x22]+";// 验证是否含有^%&',;=?$\"等字符
	private final static String chinese_characters_jian_ptn = "^[\u4e00-\u9fa5]{0,}$";// 只能输入简体汉字
	private final static String chinese_characters_fan_ptn = "^[\u9fa5-\u9fff]{0,}$";// 只能输入繁体汉字
	private final static String chinese_characters_ptn = "[\u4e00-\u9fff]";// 只能输入汉字
	private final static String url_ptn = "^http://%28[/\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";// 验证InternetURL
	private final static String id_card_ptn = "^[1-9]([0-9]{13}|[0-9]{16}){1}[[0-9]|x|X]{1}$";// 验证身份证号（15位或18位数字）
	private final static String ip_address_ptn = " ((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";// IP地址
																																							// 正则表达式
	private final static String post_ptn = "^[0-9]\\d{5}$";// 邮政编码的正则表达式
	private final static String start_zero_ptn = "^0\\d+$";// 邮政编码的正则表达式

	// 字符串 数字 小数 邮箱 网址 百分比 手机号 电话 日期

	public static void main(String args[]) {
		System.out.println(RegexUtils.testIdCard("37243019451025163x"));
		Pattern pattern = Pattern.compile("^[1-9]");
		Matcher matcher = pattern.matcher("37243019451025163X");
		System.out.println(matcher.find());
		
		 pattern = Pattern.compile(pass_ptn1);
		 matcher = pattern.matcher("a");
		 System.out.println(matcher.find());
		 System.out.println(calcDBStringLen("sfdsldf~!@#$%^&*()_加盟"));
		 
	}

	public final static RegexUtils getInstance() {
		return new RegexUtils();
	}
	
	// 计算字符串的数据内长度【汉字为两个字符串的长度】
	public static final int calcDBStringLen(final String input) {
		int otherCharacterSize=0;
		Pattern pattern = Pattern.compile(pass_ptn1);
		Matcher matcher = pattern.matcher(input);
		   while (matcher.find()) {  
			   otherCharacterSize++;  
       }  
		return (input.length()-otherCharacterSize)*2+otherCharacterSize;
	}


	// 测试给定的正则
	public static final Boolean test(final String regex, final String input) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	// 邮箱 测试
	public static final Boolean testEmail(final String input) {
		Pattern pattern = Pattern.compile(email_ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	// 数字 测试
	public static final Boolean testNumber(final String input) {
		Pattern pattern = Pattern.compile(number_ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	// 日期 测试
	public static final Boolean testDate(final String input) {
		Pattern pattern = Pattern.compile(date_ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	// 日期 测试
	public static final Boolean testDate(final String input, final String separate) {
		String ptn = date_ptn.replaceAll("-", separate);
		Pattern pattern = Pattern.compile(ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	// 日期时间 测试
	public static final Boolean testDateTime(final String input) {
		Pattern pattern = Pattern.compile(datetime_ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	// 日期时间 测试
	public static final Boolean testDateTime(final String input, final String separate) {
		String ptn = datetime_ptn.replaceAll("-", separate);
		Pattern pattern = Pattern.compile(ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	// 手机号 测试
	public static final Boolean testMobile(final String input) {
		Pattern pattern = Pattern.compile(mobile_ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}
	
	// 身份证 测试
	public static final Boolean testIdCard(final String input) {
		Pattern pattern = Pattern.compile(id_card_ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}
	
	// 邮编 测试
	public static final Boolean testPost(final String input) {
		Pattern pattern = Pattern.compile(post_ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}
	
	// 邮编 测试
	public static final Boolean testPhone(final String input) {
		Pattern pattern = Pattern.compile(phone_ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}
	
	// 密码 测试[字母、数字]
	public static final Boolean testPassword(final String input) {
		Pattern pattern = Pattern.compile(pass_ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	// 密码长度 测试
	public static final Boolean testPasswordLen(final String input, final Integer min, final Integer max) {
		return testLengthArea(input, min, max) && testPassword(input);
	}

	// 昵称 测试
	public static final Boolean testNickname(final String input) {
		Pattern pattern = Pattern.compile(nickname_ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	// 昵称长度 测试
	public static final Boolean testNicknameLen(final String input, final Integer min, final Integer max) {
		return testLengthArea(input, min, max) && testNickname(input);
	}

	// 以0开头的字符 测试
	public static final Boolean testStartZero(final String input) {
		Pattern pattern = Pattern.compile(start_zero_ptn);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	// 长度 测试
	public static final Boolean testLengthArea(final String input, final Integer min, final Integer max) {
		Pattern pattern = Pattern.compile(length_ptn + "{" + min + "," + max + "}");
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

}