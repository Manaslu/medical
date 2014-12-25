package com.teamsun.thunderreport.core;

import com.teamsun.thunderreport.parse.Sql;

/**
 * 
 * 
 */
public interface LayoutService {

	public String getId();
	
	public Sql getSql();
	
	public String getReportHeader();
	
	public String getReportBody();
	
	public String getReportFooter();
	
}
