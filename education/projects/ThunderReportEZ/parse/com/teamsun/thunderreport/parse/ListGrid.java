package com.teamsun.thunderreport.parse;

import java.util.List;

import org.dom4j.Document;


import com.teamsun.framework.ui.ListGridFactory;
import com.teamsun.thunderreport.core.RepresentContextService;

public class ListGrid implements RepresentContextService{
	public Document reportXml;

	String id;

	Sql Sql;

	String reportHeaderOutType;

	String reportHeaderOutValue;

	String reportBodyFunTitleType;

	String reportBodyFunTitleValue;

	Column[] columns;

	String reportFooterOutType;

	String reportFooterValue;
	
	public String templatefilename;

	public Column[] getColumns() {
		return columns;
	}

	public void setColumns(Column[] columns) {
		this.columns = columns;
	}




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReportBodyFunTitleType() {
		return reportBodyFunTitleType;
	}

	public void setReportBodyFunTitleType(String reportBodyFunTitleType) {
		this.reportBodyFunTitleType = reportBodyFunTitleType;
	}

	public String getReportBodyFunTitleValue() {
		return reportBodyFunTitleValue;
	}

	public void setReportBodyFunTitleValue(String reportBodyFunTitleValue) {
		this.reportBodyFunTitleValue = reportBodyFunTitleValue;
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

	public Sql getSql() {
		return Sql;
	}

	public void setSql(Sql sql) {
		Sql = sql;
	}

	public String formatContext(List data) {
		Column[] cols = this.getColumns();
		String gridId = this.getId();

		com.teamsun.framework.ui.ListGrid listGrid = ListGridFactory
				.getGrid(cols);

		listGrid.setCols(cols);
		//listGrid.setConditions(cons);
		//listGrid.setDataList(data);
		//listGrid.setParamValues(values);
		listGrid.setReportBodyFunTitleType(this.getReportBodyFunTitleType());
		listGrid.setReportBodyFunTitleValue(this.getReportBodyFunTitleValue());
		listGrid.setReportFooterOutType(this.getReportFooterOutType());
		listGrid.setReportFooterValue(this.getReportFooterValue());
		listGrid.setReportHeaderOutType(this.getReportHeaderOutType());
		listGrid.setReportHeaderOutValue(this.getReportHeaderOutValue());
		return null;
	}

	public String getTemplateVm() {
		// TODO Auto-generated method stub
		return templatefilename;
	}

	public String formatContext(List data, List allList, String[] paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTemplatefilename() {
		return templatefilename;
	}

	public void setTemplatefilename(String templatefilename) {
		this.templatefilename = templatefilename;
	}
}
