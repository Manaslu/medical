package com.teamsun.thunderreport.parse;

import org.dom4j.Document;

public class BO {

	public Document reportXml;

	public String id;

	public String db;

	public String sqlText;
	
	public String indexText;


	public String getIndexText() {
		return indexText;
	}

	public void setIndexText(String indexText) {
		this.indexText = indexText;
	}

	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Document getReportXml() {
		return reportXml;
	}

	public void setReportXml(Document reportXml) {
		this.reportXml = reportXml;
	}

}
