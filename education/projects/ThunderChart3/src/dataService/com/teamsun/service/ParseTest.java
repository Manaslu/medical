package com.teamsun.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseTest {

	public Map parseParam(String sql)
	{
		Map paras = new HashMap(); 
		Pattern p = null,p1=null; 
		Matcher m = null,m1=null; 
		
		//处理false部分
		p = Pattern.compile("\\[[^\\]]*\\]");
		m = p.matcher(sql);
		String temp = "",varible=""; 
		 
		while (m.find())
		{
			temp = m.group(0); 
			p1 = Pattern.compile("#(\\w)+#"); 
			m1 = p1.matcher(temp); 
			while (m1.find())
			{
				varible =  m1.group(0); 
				varible = varible.substring(1, varible.length()-1); 
				paras.put( varible, "false"); 
			}
		
			sql = sql.replaceAll(temp.substring(1, temp.length()-1), " ");  
		}
		 

		//处理true的情况
		p1 = Pattern.compile("#(\\w)+#"); 
		m1 = p1.matcher(sql); 
		while (m1.find())
		{
			varible =  m1.group(0); 
			varible = varible.substring(1, varible.length()-1); 
			paras.put(varible, "true"); 
		}
		return paras; 
	}
	
	public static void main(String[] args) {

		String sql="select t.log_user,t.log_enity,t.log_instance,t.log_time  from se_log t where log_date between '#begindate#'   and '#enddate#' [ and t.log_enity='#entityid#' ]";
		sql="Select dim1,dim2,dim3,dim4,sum(m1),sum(m2) from t1,t2 where t1.id=t2.id [and t1.id=#cond1#] [and t2.date=#cond2#]";
		sql="select t.log_user,t.log_enity,t.log_instance,t.log_time  from se_log t where log_date between '#begindate#'   and '#enddate#' [ and t.log_enity='#entityid#' ] [ and t.log_time between '#begintime#' and '#endtime#']";
		sql="{ call proc(?,'#userid#','#orgid#'[,'#entityid#'][,'#logid#'])}";
		Map m = new ParseTest().parseParam(sql); 
		
		System.out.println(m); 
		
//		System.out.println("#(\\w)+#");
		
//		System.out.println("\\[[^\\]]*\\]");
	
	}

}
