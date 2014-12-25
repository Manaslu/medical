package com.teamsun.framework.ui;

public class FusionChartHtml {

	private static String html = "";

	private static final String UPDATE_DATA_JS = "function updateChart(domId,check_name,other_params,cond_name, cond_value){"
			// + "alert(other_params);"
			+ "try{var cp=\"\";\n"
			+ " var cv=\"\";\n"
			+ " if (check_name!=\"\"){\n"
			+ " var cks = document.getElementsByName(check_name);\n"
			+ " for(var i =0;i<cks.length; i++){\n"
			+ "   if (cks[i].checked){\n"
			+ "cp+=cks[i].value+\",\";\n"
			+ "cv+=cks[i].text+\",\";"
			+ "}}cp=cond_name+\"=\"+cp;cv=cond_value+\"=\"+cv;}\n"
			+ " other_params = encodeURI(other_params+\"&\"+cp+\"&\"+cv,true);\n"
			// + "alert(other_params);"
			+ "var chartObj = getChartFromId(domId);\n"
			+ "Ext.Ajax.defaultHeaders = {'Content-Type':'application/text; charset=utf-8'}; "
			+ "Ext.Ajax.request({ \n"
			+ "url : other_params ,"
			+ "params : null, "
			+ "method : \"POST\", \n"
			+ "success : function(result, request){ \n"
			+ "chartObj.setDataXML(result.responseText);},"
			+ "failure : function(result, request) {}});}catch(e){}}";

	private static String html_bottom = "</center></body></html>";

	static {
		// html = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
		html += "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n";
		// html += "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n";
		html += "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n";
		html += "<link type=\"text/css\" rel=\"stylesheet\" href=\"ext-all.css\" />\n";
		html += "<link type=\"text/css\" rel=\"stylesheet\" href=\"report.css\" />\n";

		html += "<SCRIPT LANGUAGE=\"Javascript\" SRC=\"fusionchart/FusionCharts.js\"></SCRIPT>";
		html += "<SCRIPT LANGUAGE=\"Javascript\" SRC=\"include/js/ext-base.js\"></SCRIPT>";
		html += "<SCRIPT LANGUAGE=\"Javascript\" SRC=\"include/js/ext-all.js\"></SCRIPT>";
		html += "<script language=\"javascript\">";
		html += UPDATE_DATA_JS;
		html += "</script>";
		html += "</head>";
		html += "<body><center>";
	}

	public static String formateLayout(String table) {
		return html + table + html_bottom;
	}

}
