package com.teamsun.thunderreport.parse;

import org.dom4j.Document;

public class CrossGrid {

	public Document reportXml;

	String id;

	Sql sql;

	String reportHeaderOutType;

	String reportHeaderOutValue;

	public Dim[] rowDim;

	public Dim[] ColDim;

	public Measure[] measures;
	
	public Title[] title;

	String reportFooterOutType;

	String reportFooterValue;
	public String templatefilename;

	public Dim[] getColDim() {
		return ColDim;
	}

	public void setColDim(Dim[] colDim) {
		ColDim = colDim;
	}

	public Sql getSql() {
		return sql;
	}

	public void setSql(Sql sql) {
		this.sql = sql;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Measure[] getMeasures() {
		return measures;
	}

	public void setMeasures(Measure[] measures) {
		this.measures = measures;
	}

	public String getReportFooterOutType() {
		return reportFooterOutType;
	}

	public void setReportFooterOutType(String reportFooterOutType) {
		this.reportFooterOutType = reportFooterOutType;
	}

	public String getReportFooterValue() {
		return reportFooterValue;
	}

	public void setReportFooterValue(String reportFooterValue) {
		this.reportFooterValue = reportFooterValue;
	}

	public String getReportHeaderOutType() {
		return reportHeaderOutType;
	}

	public void setReportHeaderOutType(String reportHeaderOutType) {
		this.reportHeaderOutType = reportHeaderOutType;
	}

	public String getReportHeaderOutValue() {
		return reportHeaderOutValue;
	}

	public void setReportHeaderOutValue(String reportHeaderOutValue) {
		this.reportHeaderOutValue = reportHeaderOutValue;
	}

	public Document getReportXml() {
		return reportXml;
	}

	public void setReportXml(Document reportXml) {
		this.reportXml = reportXml;
	}

	public Dim[] getRowDim() {
		return rowDim;
	}

	public void setRowDim(Dim[] rowDim) {
		this.rowDim = rowDim;
	}

	public Title[] getTitle() {
		return title;
	}

	public void setTitle(Title[] title) {
		this.title = title;
	}

	public String getTemplatefilename() {
		return templatefilename;
	}

	public void setTemplatefilename(String templatefilename) {
		this.templatefilename = templatefilename;
	}

}
