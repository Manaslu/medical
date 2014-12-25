package com.teamsun.thunderreport.parse;

public class Column {
   /*
    * <Column name="省" sumname="总计" format="#,###" group="1" summary="1" sumby="" outputtype="function" sumpos="before">
    */
	String name;

	String group;
	
	String sumname;
	
	String summary;
	
	String sumby;
	
	String sumpos;

	String outputtype;
	
	String format;
	
	String type;
	
	String code;

	String columValue;
	
	String aggrfunc;

	String textAlign;	
	
	String hidden;//是否隐藏标志
	
	public String getHidden() {
		return hidden;
	}

	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	public String getColumValue() {
		return columValue;
	}

	public void setColumValue(String columValue) {
		this.columValue = columValue;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOutputtype() {
		return outputtype;
	}

	public void setOutputtype(String outputtype) {
		this.outputtype = outputtype;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSumby() {
		return sumby;
	}

	public void setSumby(String sumby) {
		this.sumby = sumby;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getSumname() {
		return sumname;
	}

	public void setSumname(String sumname) {
		this.sumname = sumname;
	}

	public String getSumpos() {
		return sumpos;
	}

	public void setSumpos(String sumpos) {
		this.sumpos = sumpos;
	}

	public String getAggrfunc() {
		return aggrfunc;
	}

	public void setAggrfunc(String aggrfunc) {
		this.aggrfunc = aggrfunc;
	}

	public String getTextAlign() {
		return textAlign;
	}

	public void setTextAlign(String textAlign) {
		this.textAlign = textAlign;
	}

}
