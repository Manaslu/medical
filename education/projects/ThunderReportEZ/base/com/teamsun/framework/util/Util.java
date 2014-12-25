/*
 * @(#)Util.java        1.00 2005/03/16
 * @author  Denny
 * Copyright (c) 1994-2005 Teansun, Inc.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Teamsun, Inc.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Teamsun.
 *
 */
package com.teamsun.framework.util;

import java.text.*;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.security.*;

/**
 *  <p>Common Utility.</p>
 *
 */

public class Util
{
    private static Random randGen = null;
    private static char[] numbersAndLetters = null;
    private static Object initLock = new Object();
    private static MessageDigest digest = null;


	/**
   	 * <p>Utility function to convert byte[] to Hex String.</p>
   	 *
   	 * @param pBa
         *
         */
	public static String ByteAarryToHexString(byte[] pBa)
    	{
    		StringBuffer sb = new StringBuffer();
    		String temp = "";

    		for(int i = 0;i<pBa.length;i++)
		{
        		//temp = Integer.toHexString(new Byte(pBa[i]).intValue());
			temp = Integer.toHexString(pBa[i] & 0XFF);
			if(temp.length()==1)
			{
        			sb.append("0");
			}
			/*
			if(temp.length()==8)
			{
        			temp = temp.substring(6);
			}
			*/
        		sb.append(temp);
        	}
        	return sb.toString().toUpperCase();
	}

	/**
   	 * <p>Another Utility function to convert byte[] to Hex String.</p>
   	 *
   	 * @param b
         *
         */
	public static String byte2hex(byte[] b) //二行制转字符串
		{
		String hs="";
		String stmp="";
		for (int n=0;n<b.length;n++)
		{
		stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
		if (stmp.length()==1) hs=hs+"0"+stmp;
		else hs=hs+stmp;
		if (n<b.length-1) hs=hs+":";
		}
		return hs.toUpperCase();
	}

   /**
     * 使用指定的字符分割字符串
     * @param str 有待分割的字符串
     * @param split 分隔标识
     * @return 返回分割后的字符串数组
     */
    public static String[] splitString(String str, String split) {
        StringTokenizer st = new StringTokenizer(str, split);
        String[] arr = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            arr[i++] = (String) st.nextToken();
        }
        return arr;
    }

    /**
     * 根据格式编码
     * @param str
     * @return
     */
    public static String getEncodingString(String str) {
        if (str == null) {
            return "";
        }
        try {
            return new String(str.getBytes("ISO8859-1"), "GBK");
        }
        catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    /**
     * 根据格式编码
     * @param str
     * @return
     */
    public static String getEncodingString(String str, String encode) {
        try {
            return new String(str.getBytes(encode), "GBK");
        }
        catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    /**
     * 根据格式编码
     * @param str
     * @return
     */
    public static String getEncodingString(String str, String rencd,
                                           String dencd) {
        try {
            return new String(str.getBytes(rencd), dencd);
        }
        catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    /**
     * 将STR由ISO编码到GBK,如果STR是NULL，返回""
     * @param str
     * @return
     */
    public static String encodingToGBK(String str) {
        if (isNotEmpty(str)) {
            str = getEncodingString(str);
        } else {
            str = "";
        }
        return str;
    }

    /**
     * 将STR由ISO编码到GBK,如果STR是NULL，返回""
     * @param str
     * @return
     */
    public static String encodingToISO(String str) {
        if (isNotEmpty(str)) {
            try {
                return new String(str.getBytes("GBK"), "ISO8859-1");
            }
            catch (UnsupportedEncodingException ex) {
                return null;
            }
        } else {
            str = "";
        }
        return str;
    }

    /**
     * 获得当前的时间，格式为：yyyyMMddHHmmss
     * @return
     */
    public static String getCurrDatetime() {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String strToday = bartDateFormat.format(date); //得到服务器当天的日期
        return strToday;
    }

    /**
     * 获得当前的时间，格式为：1996.July.10 AD 12:08 PM
     * @return
     */
    public static String getSystemDatetime() {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(
            "yyyy.MMMMM.dd GGG hh:mm aaa");
        Date date = new Date();
        String strToday = bartDateFormat.format(date); //得到服务器当天的日期
        return strToday;
    }

    /**
     * 获得当前的时间，格式为：HH:mm:ss
     * @return
     */
    public static String getCurrTime() {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String strToday = bartDateFormat.format(date); //得到服务器当天的日期
        return strToday;
    }

    /**
     * 获得当前的时间，格式为：HH:mm:ss
     * @return
     */
    public static String getCurrTime1() {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        String strToday = bartDateFormat.format(date); //得到服务器当天的日期
        return strToday;
    }

    /**
     * 获得当前的日期，格式为：yy-MM-dd
     * @return
     */
    public static String getCurrDate() {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String strToday = bartDateFormat.format(date); //得到服务器当天的日期
        return strToday;
    }

    /**
     * 获得当前的日期，格式为：yyMMdd
     * @return
     */
    public static String getCurrDate1() {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String strToday = bartDateFormat.format(date); //得到服务器当天的日期
        return strToday;
    }

    /**
     * <p>String null check.</p>
     *
     * @param str
     */
    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    /**
     * <p>String null check.</p>
     *
     * @param str
     */
    public static boolean isEmpty(String str) {
        return! (isNotEmpty(str));
    }

    /**
     * <p>Print contents of collection.</p>
     *
     * @param col
     */
    public static void printCollection(Collection col) {
        if (col != null) {
            Iterator itr = col.iterator();

            while (itr.hasNext()) {
                System.out.println(itr.next().toString());
            }
        }
    }

   	/*
	@导出文件名中文解决方法
	@文件名转换为UTF8编码。
	@:author sunfg
	*/
 public static String toUtf8String(String s) {
     StringBuffer sb = new StringBuffer();
     for (int i=0;i<s.length();i++) {
         char c = s.charAt(i);
         if (c >= 0 && c <= 255) {sb.append(c);}
         else { byte[] b;
             try {
                 b = Character.toString(c).getBytes("utf-8");
             }
             catch (Exception ex) {
                 System.out.println(ex);
                 b = new byte[0];
             }
             for (int j = 0; j < b.length; j++) {
                 int k = b[j];
                 if (k < 0) k += 256;
                 sb.append("%" + Integer.toHexString(k).toUpperCase());
             }
         }
     }
     return sb.toString();
 }
 	/*
	@将数字转换为汉字 如：1（一）
	@范围 ：1－99
	@:sunfg
	*/

	public static String toNumberString (int i )
	{
		String s="",s1="",s2="",str="";
		int i1=0,i2=0;
		s= String.valueOf(i);
		if(s.length() == 1)str = tonumstr(i);
		else if(s.length() == 2)
		{
			s1=s.substring(0,1);
			s2=s.substring(1);
			try{
			i1=Integer.parseInt(s1);
			i2=Integer.parseInt(s2);
			}catch(Exception TE){System.out.println(TE);}
			if(s2.equals("0")){
				if (s1.equals("1"))str ="十";
				else{
					str =tonumstr(i1) +"十";
				}
			}
			else{
				if (s1.equals("1"))str ="十"+tonumstr(i2);
				else{
					str =tonumstr(i1)+"十"+tonumstr(i2);
				}
			}
		}
		else
		{
			str="";
		}
		return str;
	}
        /**
         * 将数字转为大写
         * @param s int
         * @return String
         */
        public static String tonumstr( int s){
		String str="";
		if(s<10)
		{
			switch (s)
			{
				case 0 :str="零";break;
				case 1 :str="一";break;
				case 2 :str="二";break;
				case 3 :str="三";break;
				case 4 :str="四";break;
				case 5 :str="五";break;
				case 6 :str="六";break;
				case 7 :str="七";break;
				case 8 :str="八";break;
				case 9 :str="九";break;
				default :str="";
			}
		}
		return str;
	}


	
	
	public static String toGBK(String s) {
		try {
			return new String(s.getBytes("ISO8859_1"), "GBK");
		} catch (Exception e) {
			return "";
		}
	}

	public static String toHex(byte hash[]) {
		StringBuffer buf = new StringBuffer(hash.length * 2);
		int i;

		for (i = 0; i < hash.length; i++) {
			if (((int) hash[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString((int) hash[i] & 0xff, 16));
		}
		return buf.toString();
	}

	public static String[] toLowerCaseWordArray(String text) {
		if (text == null || text.length() == 0) {
			return new String[0];
		}
		StringTokenizer tokens = new StringTokenizer(text, " ,\r\n.:/\\+");
		String[] words = new String[tokens.countTokens()];
		for (int i = 0; i < words.length; i++) {
			words[i] = tokens.nextToken().toLowerCase();
		}
		return words;
	}

        public static String[] toUpperCaseWordArray(String text) {
                if (text == null || text.length() == 0) {
                        return new String[0];
                }
                StringTokenizer tokens = new StringTokenizer(text, " ,\r\n.:/\\+");
                String[] words = new String[tokens.countTokens()];
                for (int i = 0; i < words.length; i++) {
                        words[i] = tokens.nextToken().toUpperCase();
                }
                return words;
	}


	public synchronized static String hash(String data) {
		if (digest == null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nsae) {
				System.err.println("Failed to load the MD5 MessageDigest.");
				nsae.printStackTrace();
			}
		}
		digest.update(data.getBytes());
		return toHex(digest.digest());
	}

	public static String randomString(int length) {
		if (length < 1) {
			return null;
		}

		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					numbersAndLetters =
						("0123456789abcdefghijklmnopqrstuvwxyz"
							+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
							.toCharArray();
				}
			}
		}

		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static String replace(
		String line,
		String oldString,
		String newString) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

	public static String replace(
		String line,
		String oldString,
		String newString,
		int[] count) {
		if (line == null) {
			return null;
		}
		int i = 0;
		if ((i = line.indexOf(oldString, i)) >= 0) {
			int counter = 0;
			counter++;
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = line.indexOf(oldString, i)) > 0) {
				counter++;
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			count[0] = counter;
			return buf.toString();
		}
		return line;
	}

	public static String replaceIgnoreCase(
		String line,
		String oldString,
		String newString) {
		if (line == null) {
			return null;
		}
		String lcLine = line.toLowerCase();
		String lcOldString = oldString.toLowerCase();
		int i = 0;
		if ((i = lcLine.indexOf(lcOldString, i)) >= 0) {
			char[] line2 = line.toCharArray();
			char[] newString2 = newString.toCharArray();
			int oLength = oldString.length();
			StringBuffer buf = new StringBuffer(line2.length);
			buf.append(line2, 0, i).append(newString2);
			i += oLength;
			int j = i;
			while ((i = lcLine.indexOf(lcOldString, i)) > 0) {
				buf.append(line2, j, i - j).append(newString2);
				i += oLength;
				j = i;
			}
			buf.append(line2, j, line2.length - j);
			return buf.toString();
		}
		return line;
	}

        public static String getShowString(String str){
            if(str==null||str.equals("")){
                return "";
            }
            else{
                String []sign = {"\n","  "};
                String []resi ={"<br>","&nbsp;"};
                for(int i=0;i<sign.length;i++){
                    str = replace(str, sign[i], resi[i]);
                }
                return str;
            }
        }

        public static String getSampleString(String str){

            if(str==null||str.equals("")){
                return "";
            }
            else{
                String sign = "'#%!;?";
                String reps ="‘＃％！；？";

                char [] c = sign.toCharArray();
                char [] s = reps.toCharArray();

                for (int i = 0; i < c.length; i++) {
                    String code = Character.toString(c[i]);
                    String ncod = Character.toString(s[i]);
                    str = replace(str, code, ncod);
                }
                return str;
            }
        }
        /**
         * 取得（start－end）Option元素
         * @param start int
         * @param end int
         * @return String
         */
        public static String getNumber(int start, int end) {
                String option = "<option value=\"\">选择</option>";
                for (int i = start; i <= end; i++) {
                        option = option + "\n";
                        option = option + "<option value=\"" + i + "\">" + i + "</option>";
                }
                return option;
	}
        public static String getNumberWithDefault(int start, int end, String value) {
                String option = "<option value=\"\">选择</option>";
                for (int i = start; i <= end; i++) {
                        option = option + "\n";
                        if (String.valueOf(i).equals(value)) {
                                option =
                                        option
                                                + "<option value=\""
                                                + i
                                                + "\" selected>"
                                                + i
                                                + "</option>";
                        } else {
                                option =
                                        option + "<option value=\"" + i + "\">" + i + "</option>";
                        }
                }
                return option;
	}
	public static String getYear(int start, int end) {
		String option = "<option value=\"\">选择</option>";
		for (int i = start; i <= end; i++) {
			option = option + "\n";
			option = option + "<option value=\"" + i + "\">" + i + "</option>";
		}
		return option;
	}

	public static String getYearWithDefault(int start, int end, String value) {
		String option = "<option value=\"\">选择</option>";
		for (int i = start; i <= end; i++) {
			option = option + "\n";
			if (String.valueOf(i).equals(value)) {
				option =
					option
						+ "<option value=\""
						+ i
						+ "\" selected>"
						+ i
						+ "</option>";
			} else {
				option =
					option + "<option value=\"" + i + "\">" + i + "</option>";
			}
		}
		return option;
	}

	public static String getMonth() {
		String temp = "";
		String option = "<option value=\"\">选择</option>";
		for (int i = 1; i <= 12; i++) {
			option = option + "\n";
			temp = String.valueOf(i);
			if (i < 10)
				temp = "0" + i;
			option =
				option + "<option value=\"" + temp + "\">" + temp + "</option>";
		}
		return option;
	}

	public static String getMonthWithDefault(String value) {
		String temp = "";
		String option = "<option value=\"\">选择</option>";
		for (int i = 1; i <= 12; i++) {
			option = option + "\n";
			temp = String.valueOf(i);
			if (i < 10)
				temp = "0" + i;
			if (temp.equals(value)) {
				option =
					option
						+ "<option value=\""
						+ temp
						+ "\" selected>"
						+ temp
						+ "</option>";
			} else {
				option =
					option
						+ "<option value=\""
						+ temp
						+ "\">"
						+ temp
						+ "</option>";
			}
		}
		return option;
	}

	public static String getDay() {
		String temp = "";
		String option = "<option value=\"\">选择</option>";
		for (int i = 1; i <= 31; i++) {
			option = option + "\n";
			temp = String.valueOf(i);
			if (i < 10)
				temp = "0" + i;
			option =
				option + "<option value=\"" + temp + "\">" + temp + "</option>";
		}
		return option;
	}

	public static String getDayWithDefault(String value) {
		String temp = "";
		String option = "<option value=\"\">选择</option>";
		for (int i = 1; i <= 31; i++) {
			option = option + "\n";
			temp = String.valueOf(i);
			if (i < 10)
				temp = "0" + i;
			if (temp.equals(value)) {
				option =
					option
						+ "<option value=\""
						+ temp
						+ "\" selected>"
						+ temp
						+ "</option>";
			} else {
				option =
					option
						+ "<option value=\""
						+ temp
						+ "\">"
						+ temp
						+ "</option>";
			}
		}
		return option;
	}

	public static String getHour() {
		String temp = "";
		String option = "<option value=\"\">选择</option>";
		for (int i = 0; i <= 23; i++) {
			option = option + "\n";
			temp = String.valueOf(i);
			if (i < 10)
				temp = "0" + i;
			option =
				option + "<option value=\"" + temp + "\">" + temp + "</option>";
		}
		return option;
	}

	public static String getHourWithDefault(String value) {
		String temp = "";
		String option = "<option value=\"\">选择</option>";
		for (int i = 0; i <= 23; i++) {
			option = option + "\n";
			temp = String.valueOf(i);
			if (i < 10)
				temp = "0" + i;
			if (temp.equals(value)) {
				option =
					option
						+ "<option value=\""
						+ temp
						+ "\" selected>"
						+ temp
						+ "</option>";
			} else {
				option =
					option
						+ "<option value=\""
						+ temp
						+ "\">"
						+ temp
						+ "</option>";
			}
		}
		return option;
	}

	public static String getMinute() {
		String temp = "";
		String option = "<option value=\"\">选择</option>";
		for (int i = 0; i <= 59; i++) {
			option = option + "\n";
			temp = String.valueOf(i);
			if (i < 10)
				temp = "0" + i;
			option =
				option + "<option value=\"" + temp + "\">" + temp + "</option>";
		}
		return option;
	}

	public static String getMinuteWithDefault(String value) {
		String temp = "";
		String option = "<option value=\"\">选择</option>";
		for (int i = 0; i <= 59; i++) {
			option = option + "\n";
			temp = String.valueOf(i);
			if (i < 10)
				temp = "0" + i;
			if (temp.equals(value)) {
				option =
					option
						+ "<option value=\""
						+ temp
						+ "\" selected>"
						+ temp
						+ "</option>";
			} else {
				option =
					option
						+ "<option value=\""
						+ temp
						+ "\">"
						+ temp
						+ "</option>";
			}
		}
		return option;
	}

        public static void main(String [] args){
            String st = getSampleString("哈哈哈,'\"!#游戏@!%^&*()_+?,:','\"!#@!%^&*方法()_+?,:','\"!#@!%^&*()_+?,:'");
            String stOut = getShowString(st);
            System.out.println(st);
            System.out.println(stOut);
        }

}
