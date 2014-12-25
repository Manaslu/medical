package com.teamsun.thunderreport.parse;

public class Layout {
	public String outputtype;

	public String value;

	public String getOutputtype() {
		return outputtype;
	}

	public void setOutputtype(String outputtype) {
		this.outputtype = outputtype;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String replace(String id, String replaceValue) {
		this.value = this.value.replaceAll(id, replaceValue);
		return this.value;
	}
}
