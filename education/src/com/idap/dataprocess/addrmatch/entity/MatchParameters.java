package com.idap.dataprocess.addrmatch.entity;

public class MatchParameters {
	private String inputFile = "";// 源文件地址

	private String outputFile = "";// 目标文件地址

	private String addrColumn = "";// 地址所在列

	private String postCodeColumn = "";// 邮编所在列

	private String divisionColumn = "";// 行政区划所在列

	private String separator = "";// 分隔符

	private String matchType = "";// 匹配类型

	private String outputLevel = "";// 输出级别

	private String priority = "";// 匹配优先级

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public String getAddrColumn() {
		return addrColumn;
	}

	public void setAddrColumn(String addrColumn) {
		this.addrColumn = addrColumn;
	}

	public String getPostCodeColumn() {
		return postCodeColumn;
	}

	public void setPostCodeColumn(String postCodeColumn) {
		this.postCodeColumn = postCodeColumn;
	}

	public String getDivisionColumn() {
		return divisionColumn;
	}

	public void setDivisionColumn(String divisionColumn) {
		this.divisionColumn = divisionColumn;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	public String getOutputLevel() {
		return outputLevel;
	}

	public void setOutputLevel(String outputLevel) {
		this.outputLevel = outputLevel;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
}
