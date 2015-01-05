package com.idp.pub.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
 
/**
 * compareTo(String anotherString)按字典顺序比较两个字符串。 该比较基于字符串中各个字符的 Unicode 值。 将此
 * String 对象表示的字符序列与参数字符串所表示的字符序列进行比较。 如果按字典顺序此 String 对象在参数字符串之前，则比较结果为一个负整数。
 * 如果按字典顺序此 String 对象位于参数字符串之后，则比较结果为一个正整数。如果这两个字符串相等，则结果为 0；compareTo 只有在方法
 * equals(Object) 返回 true 时才返回 0。 这是字典排序的定义。
 * 如果这两个字符串不同，则要么它们在某个索引处具有不同的字符，该索引对二者均为有效索引，要么它们的长度不同，或者同时具备上述两种情况。
 * 如果它们在一个或多个索引位置上具有不同的字符，假设 k 是这类索引的最小值；则按照 < 运算符确定的那个字符串在位置 k
 * 上具有较小的值，其字典顺序在其他字符串之前。 这种情况下，compareTo 返回这两个字符串在位置 k 处的两个不同的 char 值，即值：
 * this.charAt(k)-anotherString.charAt(k)
 * 如果它们没有不同的索引位置，则较短字符串在字典顺序上位于较长字符串的前面。这种情况下，compareTo 返回这两个字符串长度的不同，即值：
 * this.length()-anotherString.length()
 * 
 * compareTo("你好","你好"); 结果：0 compareTo("你","你好"); 结果：－1 compareTo("你好","你");
 * 结果：1 以上，由于对应位置的值都相等，所有返回的是 长度差。
 * 
 * compareTo("你好1","你好2"); 结果：－1 compareTo("你好1","你好9"); 结果：－8 以上返回值是不同位置索引的
 * 差～，即：字符编码的差额
 */
public abstract class GetCh2Spell<T> {
	private String _FromEncode_ = "GBK";
	private String _ToEncode_ = "GBK";

	/*
	 * 功能：返回两个字符的字典顺序比较结果
	 */
	public int compare(String str1, String str2) {
		int result = 0;
		String m_s1 = null;
		String m_s2 = null;
		try {
			m_s1 = new String(str1.getBytes(_FromEncode_), _ToEncode_);
			m_s2 = new String(str2.getBytes(_FromEncode_), _ToEncode_);
		} catch (Exception e) {
			return str1.compareTo(str2);
		}
		result = chineseCompareTo(m_s1, m_s2);
		return result;
	}

	public static int chineseCompareTo(String s1, String s2) {
		int len1 = s1.length();
		int len2 = s2.length();
		int n = Math.min(len1, len2);
		for (int i = 0; i < n; i++) {
			int s1_code = getCharCode(s1.charAt(i) + "");
			int s2_code = getCharCode(s2.charAt(i) + "");
			if (s1_code * s2_code < 0)
				return Math.min(s1_code, s2_code);
			if (s1_code != s2_code)
				return s1_code - s2_code;
		}

		return len1 - len2;
	}

	public static int getCharCode(String s) {
		if (s == null && "".equals(s))
			return -1;
		byte b[] = s.getBytes();
		int value = 0;
		for (int i = 0; i < b.length && i <= 2; i++) {
			value = value * 100 + b[i];
		}

		return value;
	}

	/**//*
		 * 功能：返回字符串的 首字母
		 */
	public String getBeginCharacter(T res) {
		if (res == null)
			return "";
		String a = this.getKey(res);
		StringBuffer result = new StringBuffer("");
		for (int i = 0; i < a.length(); i++) {
			String current = a.substring(i, i + 1);
			if (compare(current, "啊") < 0)
				result = result.append(current);
			else if (compare(current, "啊") >= 0 && compare(current, "座") <= 0) {
				if (compare(current, "匝") >= 0)
					result = result.append("z");
				else if (compare(current, "压") >= 0)
					result = result.append("y");
				else if (compare(current, "昔") >= 0)
					result = result.append("x");
				else if (compare(current, "挖") >= 0)
					result = result.append("w");
				else if (compare(current, "塌") >= 0)
					result = result.append("t");
				else if (compare(current, "撒") >= 0)
					result = result.append("s");
				else if (compare(current, "然") >= 0)
					result = result.append("r");
				else if (compare(current, "期") >= 0)
					result = result.append("q");
				else if (compare(current, "啪") >= 0)
					result = result.append("p");
				else if (compare(current, "哦") >= 0)
					result = result.append("o");
				else if (compare(current, "拿") >= 0)
					result = result.append("n");
				else if (compare(current, "妈") >= 0)
					result = result.append("m");
				else if (compare(current, "垃") >= 0)
					result = result.append("l");
				else if (compare(current, "喀") >= 0)
					result = result.append("k");
				else if (compare(current, "击") >= 0)
					result = result.append("j");
				else if (compare(current, "哈") >= 0)
					result = result.append("h");
				else if (compare(current, "噶") >= 0)
					result = result.append("g");
				else if (compare(current, "发") >= 0)
					result = result.append("f");
				else if (compare(current, "蛾") >= 0)
					result = result.append("e");
				else if (compare(current, "搭") >= 0)
					result = result.append("d");
				else if (compare(current, "擦") >= 0)
					result = result.append("c");
				else if (compare(current, "芭") >= 0)
					result = result.append("b");
				else if (compare(current, "啊") >= 0)
					result = result.append("a");
			}
		}

		return result.toString();
	}

	/*
	 * 公能:得到一个汉字的首字符
	 */
	public String getFirstStr(T t) {
		String str = this.getKey(t);
		char a = str.charAt(0);
		char aa[] = { a };
		String sss = new String(aa);
		if (Character.isDigit(aa[0]))
			sss = a + "";
		else if (a >= 'a' && a <= 'z' || a >= 'A' && a <= 'Z')
			sss = a + "";
		else
			sss = getBeginCharacter(t);
		return sss;
	}

	public Collection<List<T>> groupBy(T[] arrays) {
		Map<Character, List<T>> maps = new LinkedHashMap<Character, List<T>>();

		this.sort(arrays);
		int len = arrays.length;
		int cnt = len / 2;
		if (len % 2 == 1) {
			cnt++;
		}
		for (int i = 0; i < cnt; i++) {
			this.put(maps, arrays, i);
			if (i < (len - i - 1)) {
				this.put(maps, arrays, len - i - 1);
			}
		}
		Collection<List<T>> _value = maps.values();

		return _value;

	}

	private void put(Map<Character, List<T>> maps, T[] arrays, int i) {
		List<T> values = null;
		Character key = new Character(getFirstStr(arrays[i]).charAt(0));
		if (maps.containsKey(key)) {
			values = maps.get(key);
			values.add(arrays[i]);
			maps.put(key, values);
		} else {
			values = new ArrayList<T>();
			values.add(arrays[i]);
			maps.put(key, values);
		}
	}

	/**
	 * 按什么字段取出首字母
	 * 
	 * @param t
	 * @return
	 */
	public abstract String getKey(T t);

	/**
	 * 对数据进行排序
	 * 
	 * @param arrays
	 */
	public abstract void sort(T[] arrays);
}
