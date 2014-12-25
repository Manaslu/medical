package com.teamsun.thunderreport.parse;

public class Dim
{
	/*
	 * <Dim name="市" sumname="小计" summary="1" sumby="dim1" sumpos="after">{city}</Dim>
	 */
	public String dimValue;

	public String name;

	public String sumname;

	public String summary;

	public String sumby;

	public String sumpos;

	// 2007-7-23 9:33 hj
	public String code;

	public String group;
	public String outputtype;
	public String keycode;
	

	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDimValue()
	{
		return dimValue;
	}

	public void setDimValue(String dimValue)
	{
		this.dimValue = dimValue;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSumby()
	{
		return sumby;
	}

	public void setSumby(String sumby)
	{
		this.sumby = sumby;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public String getSumname()
	{
		return sumname;
	}

	public void setSumname(String sumname)
	{
		this.sumname = sumname;
	}

	public String getSumpos()
	{
		return sumpos;
	}

	public void setSumpos(String sumpos)
	{
		this.sumpos = sumpos;
	}

	public String getOutputtype() {
		return outputtype;
	}

	public void setOutputtype(String outputtype) {
		this.outputtype = outputtype;
	}

	public String getKeycode() {
		return keycode;
	}

	public void setKeycode(String keycode) {
		this.keycode = keycode;
	}

	
}
