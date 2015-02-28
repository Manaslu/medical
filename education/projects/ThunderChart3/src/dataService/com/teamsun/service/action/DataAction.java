package com.teamsun.service.action;

public interface DataAction {
	
    SqlResult getSQL(String id);
	String toXML(String osql,String para,String pageNo);
	String toCSV(String osql,String para,String pageNo);
	String toJSON(String osql,String para,String pageNo);
	
}
