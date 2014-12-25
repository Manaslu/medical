package com.teamsun.framework.ui;
import java.util.List;

import com.teamsun.thunderreport.core.RepresentContextService;
import com.teamsun.thunderreport.parse.Column;
import com.teamsun.thunderreport.parse.Condition;
import com.teamsun.thunderreport.parse.Sql;

public abstract class ListGrid implements RepresentContextService
{
	protected Column[] cols;

//protected Column[] columns;//有group＝1的列的数组
	private Sql sql;
	

	protected String reportHeaderOutType;

	protected String reportHeaderOutValue;

	protected String reportBodyFunTitleType;

	protected String reportBodyFunTitleValue;

	protected String reportFooterOutType;

	protected String reportFooterValue;

	protected Condition[] conditions;
	
	protected String id;
	
	protected String templatefilename;

	//protected String[] paramValues;

	public Condition[] getConditions()
	{
		return conditions;
	}

	public void setConditions(Condition[] conditions)
	{
		this.conditions = new Condition[conditions.length];
		this.conditions=conditions;
	}

//	public String[] getParamValues();
//	{
//		return paramValues;
//	}
//
//	public void setParamValues(String[] paramValues)
//	{
//		this.paramValues = paramValues;
//	}

	public Column[] getCols()
	{
		return cols;
	}

	public void setCols(Column[] cols)
	{
		//int num = 0;
		this.cols = cols;
	//	List col_group = new ArrayList();
//		for (int i = 0; i < cols.length; i++)
//		{
//			if (cols[i].getGroup() != null && cols[i].getGroup().equals("1"))
//			{
//				col_group.add(num, cols[i]);
//				num++;
//			}
//		}
//		columns = new Column[col_group.size()];
//		for (int i = 0; i < col_group.size(); i++)
//		{
//			columns[i] = (Column) col_group.get(i);
//		}
	}



	public String getReportBodyFunTitleType()
	{
		return reportBodyFunTitleType;
	}

	public void setReportBodyFunTitleType(String reportBodyFunTitleType)
	{
		this.reportBodyFunTitleType = reportBodyFunTitleType;
	}

	public String getReportBodyFunTitleValue()
	{
		return reportBodyFunTitleValue;
	}

	public void setReportBodyFunTitleValue(String reportBodyFunTitleValue)
	{
		this.reportBodyFunTitleValue = reportBodyFunTitleValue;
	}

	public String getReportFooterOutType()
	{
		return reportFooterOutType;
	}

	public void setReportFooterOutType(String reportFooterOutType)
	{
		this.reportFooterOutType = reportFooterOutType;
	}

	public String getReportFooterValue()
	{
		return reportFooterValue;
	}

	public void setReportFooterValue(String reportFooterValue)
	{
		this.reportFooterValue = reportFooterValue;
	}

	public String getReportHeaderOutType()
	{
		return reportHeaderOutType;
	}

	public void setReportHeaderOutType(String reportHeaderOutType)
	{
		this.reportHeaderOutType = reportHeaderOutType;
	}

	public String getReportHeaderOutValue()
	{
		return reportHeaderOutValue;
	}

	public void setReportHeaderOutValue(String reportHeaderOutValue)
	{
		this.reportHeaderOutValue = reportHeaderOutValue;
	}

	public abstract String toHtml(List dataList,List allList,String[] paramValues);

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

	public String getTemplatefilename() {
		return templatefilename;
	}

	public void setTemplatefilename(String templatefilename) {
		this.templatefilename = templatefilename;
	}
	public String getTemplateVm() {
		// TODO Auto-generated method stub
		return templatefilename;
	}
	
    public String getColNames(){
    	if((this instanceof CrossGrid )||this instanceof CrossGridEx){
    		return "";
    	}
    	String str="";
    	for(int i=0;i<this.cols.length;i++){
    		if(i>0) str+=",";
    		str+=cols[i].getName();
    	}
    	return str;
    }


}
