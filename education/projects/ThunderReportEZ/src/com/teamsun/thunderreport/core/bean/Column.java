package com.teamsun.thunderreport.core.bean;

public class Column {

	private String code;

	private String format;

	private int group = 0;

	private String align;

	private String outputtype;

	private String text;
	
	private Dim dim;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public String getOutputtype() {
		return outputtype;
	}

	public void setOutputtype(String outputtype) {
		this.outputtype = outputtype;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Dim getDim() {
		return dim;
	}

	public void setDim(Dim dim) {
		this.dim = dim;
	}

}
