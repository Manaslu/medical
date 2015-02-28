package com.hjtp.incas.chart.exporthelper;


public class ExportFormat implements IExportFormat
{
	private String type;
	private String contentType;
	private String postfix;
	public ExportFormat(String type)
	{
		type=type.toLowerCase();
		type=type.trim();
		this.type=type;
		parseFormat();
	}
	public String getContentType() 
	{
		return contentType;
	}

	public String getPostfix() 
	{
		return postfix;
	}
	public void setContentType(String contentType) 
	{
		this.contentType=contentType;
	}

	public void setPostfix(String postfix) 
	{
		this.postfix=postfix;
	}
	private void parseFormat()
	{
		String types=CommonConstant.hash_type.get(type);
		String fix=CommonConstant.hash_fix.get(type);
		if(types==null||fix==null||fix.equals("")||types.equals(""))
		{
			types="jpg";
			fix="jpg";
		}
		this.setContentType(types);
		this.setPostfix(fix);
	}

}
