package com.teamsun.thunderreport.parse;

import org.dom4j.Document;

public class Chart {
	public Document reportXml;

	public Measure measures;

	public Dim rowDim;

	public Dim ColDim;

	public String fileName;

	public String id;
	
	public Sql Sql;

	public String type;

	public String width;

	public String height;

	public String caption;

	public String legend;

	public String is3d;

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Dim getColDim() {
		return ColDim;
	}

	public void setColDim(Dim colDim) {
		ColDim = colDim;
	}

	public String getIs3d() {
		return is3d;
	}

	public void setIs3d(String is3d) {
		this.is3d = is3d;
	}

	public void setRowDim(Dim rowDim) {
		this.rowDim = rowDim;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLegend() {
		return legend;
	}

	public void setLegend(String legend) {
		this.legend = legend;
	}

	public Measure getMeasures() {
		return measures;
	}

	public void setMeasures(Measure measures) {
		this.measures = measures;
	}

	public Document getReportXml() {
		return reportXml;
	}

	public void setReportXml(Document reportXml) {
		this.reportXml = reportXml;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public Dim getRowDim() {
		return rowDim;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Sql getSql() {
		return Sql;
	}

	public void setSql(Sql sql) {
		Sql = sql;
	}


}
