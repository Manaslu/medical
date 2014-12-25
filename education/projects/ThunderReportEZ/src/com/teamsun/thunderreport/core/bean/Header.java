package com.teamsun.thunderreport.core.bean;

public class Header {

	private String outputtype;

	private String text;

	public Header() {
	}

	public Header(String outputtype, String text) {
		this.outputtype = outputtype;
		this.text = text;
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

}
