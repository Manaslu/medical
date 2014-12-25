package com.teamsun.framework.ui;

import java.util.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class SimpleFormat {
	public static String formatStr(Object value,String oPattern) {		
		try{
			double dbvalue;
			dbvalue=Double.parseDouble(value.toString());
			DecimalFormat df = new DecimalFormat(oPattern);
			return df.format(dbvalue).toString();
		}catch (NumberFormatException e){
			try{
			    Date datevalue=(Date)value;
			    SimpleDateFormat oSimpleDateFormat = new SimpleDateFormat(oPattern);
			    String szdateTime = oSimpleDateFormat.format(datevalue);
			    return szdateTime;
			}catch (Exception de){
			    return value.toString();
			}
		}
	}


	public static void main(String args[]) {
		System.out.println(formatStr("3212.212","#,###"));
		System.out.println(formatStr("3212.212","#,###.0"));
		System.out.println(formatStr("3212asd.212","#,###.0"));
		System.out.println(formatStr(new Date(),"yyyy年MM月dd日 时间：HHmmss"));
		System.out.println(formatStr(new Date(),"yyyyMMddHHmmss"));
		System.out.println(formatStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
		
	}

}
