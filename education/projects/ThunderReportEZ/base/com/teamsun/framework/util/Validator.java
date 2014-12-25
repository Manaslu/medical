package com.teamsun.framework.util;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.lang.StringUtils;
public final class Validator
{
	public static boolean isShort(String str){
		return GenericValidator.isShort(str);
	}
	public static boolean isInt(String str){
		return GenericValidator.isInt(str);
	}
	public static boolean isLong(String str){
		return GenericValidator.isLong(str);
	}
	public static boolean isFloat(String str){
		return GenericValidator.isFloat(str);
	}
	public static boolean isDouble(String str){
		return GenericValidator.isDouble(str);
	}
	public static boolean isEmail(String str){
		return GenericValidator.isEmail(str);
	}
	public static boolean isInRange(byte arg0,byte arg1,byte arg2)
	{
		return GenericValidator.isInRange(arg0,arg1,arg2);
	}
	public static boolean isInRange(short arg0,short arg1,short arg2)
	{
		return GenericValidator.isInRange(arg0,arg1,arg2);
	}
	public static boolean isInRange(int arg0,int arg1,int arg2)
	{
		return GenericValidator.isInRange(arg0,arg1,arg2);
	}
	public static boolean isInRange(long arg0,long arg1,long arg2)
	{
		return GenericValidator.isInRange(arg0,arg1,arg2);
	}
	public static boolean isInRange(float arg0,float arg1,float arg2)
	{
		return GenericValidator.isInRange(arg0,arg1,arg2);
	}
	public static boolean isInRange(double arg0,double arg1,double arg2)
	{
		return GenericValidator.isInRange(arg0,arg1,arg2);
	}
	/**
	 * isPInt：判断是否是正整数
	 * @param str
	 * @return true---是正整数 false---否
	 */
	public static boolean isPInt(String str){
		return GenericValidator.matchRegexp(str, "^(0{1})$|^[^0\\D]+(\\d)*$");
	}
	/**
	 * isDecimal：检验参数<code>str</code>的小数位是否小于等于参数<code>digitCount</code>
	 * @param str
	 * @param digitCount
	 * @return 
	 * 
	 */
	public static boolean isDecimal(String str,int digitCount)
	{
		String reg="^(0{1})$|^-?[1-9]+\\d*(\\.\\d{1,"+digitCount+"})?$|^0{1}(\\.\\d{1,"+digitCount+"})?$|^-?[0]{1}(\\.\\d{1,"+digitCount+"})?$";
		return GenericValidator.matchRegexp(str,reg);
	}
	//检查小数的情况，包含数据长度和小数长度
	public static boolean isNumeric(String str,int length,int digit){
		//整数的情况
		if(str.indexOf(".")<0){
			return isInt(str,length);
		}
		//小数的情况
		else{
			String[] strarr = str.split("\\.");
			if(strarr.length!=2){
				return false;
			}
			else{
				String onlynumberstr = StringUtils.replace(str, "-", "");
				onlynumberstr = StringUtils.replace(onlynumberstr, ".", "");
				if(GenericValidator.matchRegexp(onlynumberstr,"^\\d{"+length+"}$")){
					return isDecimal(str,digit);
				}
				else{
					return false;
				}
			}
		}
	}
    //验证日期值:yyyymmdd
	public static boolean isDate(String dateString)
	{
		String reg="^(19|20)\\d\\d(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])$";
		return GenericValidator.matchRegexp(dateString,reg);
	}
	//验证时间值:
	public static boolean isTime(String dateString)
	{
		String reg = "";
		if(dateString.length()==1){
			reg="^([0-9])$";
		}
		else if(dateString.length()==2){
			reg="^([0-5][0-9])$";
		}
		else if(dateString.length()==3){
			reg="^([0-9])([0-5][0-9])$";
		}
		else if(dateString.length()==4){
			reg="^([0-5][0-9])([0-5][0-9])$";
		}
		else if(dateString.length()==5){
			reg="^([0-9])([0-5][0-9])([0-5][0-9])$";
		}
		else if(dateString.length()==6){
			reg="^(([0-1][0-9])|([2][0-3]))([0-5][0-9])([0-5][0-9])$";
		}
		return GenericValidator.matchRegexp(dateString,reg);
	}
	/**
	 * isInt:检验参数<code>str</code>的长度是否小于等于参数<code>count</code>
	 * @param str
	 * @param count--整形数的长度
	 * @return
	 */
	public static boolean isInt(String str, int count)
	{
		return GenericValidator.matchRegexp(str, "^(-)?(\\d){1,"+count+"}$");
	}
}
