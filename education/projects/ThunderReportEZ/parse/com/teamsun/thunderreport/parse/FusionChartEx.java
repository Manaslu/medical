package com.teamsun.thunderreport.parse;

import java.util.Map;

import org.dom4j.Element;

public abstract class FusionChartEx {
	protected String filename;
	protected Element fusionchart;
	/**
	 * 页面传递的参数和值


	 * 
	 * 
	 * 
	 */
	protected Map condsValues;
	/**
	 * 得到复选条件隐含值的参数名


	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	public String selectConditionValueName() {
		Element chart = this.fusionchart.element("chart");
		Element datasets = chart.element("datasets");
		String cond_value = datasets.attributeValue("cond_value");
		return cond_value;
	}
	
	public abstract String formateFusionChartXml(DataService dataService);
	

	public String selectConditionDisplayName() {
		Element chart = this.fusionchart.element("chart");
		Element datasets = chart.element("datasets");
		String cond_disp = datasets.attributeValue("cond_disp");
		return cond_disp;
	}
	public String getFilename() {
		return filename;
	}
	public Map getCondsValues() {
		return condsValues;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * 可否复选


	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	public boolean selected() {
		Element chart = this.fusionchart.element("chart");
		Element datasets = chart.element("datasets");
		String s = datasets.attributeValue("select");
		return Boolean.valueOf(s).booleanValue();
	}
}
