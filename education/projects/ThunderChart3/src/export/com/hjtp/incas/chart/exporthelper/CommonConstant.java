package com.hjtp.incas.chart.exporthelper;

import java.util.ArrayList;
import java.util.Hashtable;

public class CommonConstant 
{
	public static Hashtable<String,String>hash_type=new Hashtable<String,String>();
	public static Hashtable<String,String>hash_fix=new Hashtable<String,String>();
	public static ArrayList<Object>arr=new ArrayList<Object>();
	static
	{
		hash_type.put("png", "png");
		hash_type.put("jpeg", "jpg");
		hash_type.put("gif", "gif");
		hash_type.put("bmp", "bmp");
		hash_type.put("doc", "application/ms-word");
		hash_type.put("csv", "csv");
		hash_type.put("xls", "xls");
		hash_type.put("pdf", "pdf");
		
		hash_fix.put("png", "png");
		hash_fix.put("jpeg", "jpg");
		hash_fix.put("gif", "gif");
		hash_fix.put("bmp", "bmp");
		hash_fix.put("doc", "doc");
		hash_fix.put("csv", "csv");
		hash_fix.put("xls", "xls");
		hash_fix.put("pdf", "pdf");
	}
}
