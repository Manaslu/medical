package com.teamsun.export.util;

import java.util.ArrayList;
import java.util.List;

public class ColumnInfoVO {
private String columnEName;
private String columnCName;
private String columnType;
private List lineData=new ArrayList();
public String getColumnCName() {
	return columnCName;
}
public void setColumnCName(String columnCName) {
	this.columnCName = columnCName;
}
public String getColumnEName() {
	return columnEName;
}
public void setColumnEName(String columnEName) {
	this.columnEName = columnEName;
}
public String getColumnType() {
	return columnType;
}
public void setColumnType(String columnType) {
	this.columnType = columnType;
}
public List getLineData() {
	return lineData;
}
public void setLineData(List lineData) {
	this.lineData = lineData;
}
}
