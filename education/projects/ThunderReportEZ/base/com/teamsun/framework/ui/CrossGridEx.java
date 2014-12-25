package com.teamsun.framework.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.teamsun.framework.util.RegExUtil;
import com.teamsun.thunderreport.javascript.JavaScriptParser;
import com.teamsun.thunderreport.parse.Column;
import com.teamsun.thunderreport.parse.Condition;
import com.teamsun.thunderreport.parse.Dim;
import com.teamsun.thunderreport.parse.Measure;

public class CrossGridEx extends ListGrid{

	private Dim[] rowDims;

	private Dim[] colDims;

	private Measure[] measures;

	private Column[] columns;

	private String measureCrossWith;

	private boolean isMeasureDisplay;

	public Dim[] getRowDims() {
		return rowDims;
	}

	public void setRowDims(Dim[] rowDims) {
		this.rowDims = rowDims;
	}

	public Dim[] getColDims() {
		return colDims;
	}

	public void setColDims(Dim[] colDims) {
		this.colDims = colDims;
	}

	public Measure[] getMeasures() {
		return measures;
	}

	public void setMeasures(Measure[] measures) {
		this.measures = measures;
	}

	public Column[] getColumns() {
		return columns;
	}

	public void setColumns(Column[] columns) {
		this.columns = columns;
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

	public Condition[] getConditions() {
		return conditions;
	}

	public void setConditions(Condition[] conditions) {
		this.conditions = conditions;
	}


	public String getMeasureCrossWith() {
		return measureCrossWith;
	}

	public void setMeasureCrossWith(String measureCrossWith) {
		this.measureCrossWith = measureCrossWith;
	}

	public boolean isMeasureDisplay() {
		return isMeasureDisplay;
	}

	public void setMeasureDisplay(boolean isMeasureDisplay) {
		this.isMeasureDisplay = isMeasureDisplay;
	}

	private String getReportHeader(List allList, String[] paramValues) {
		String reportHeader = "";
		if (this.reportHeaderOutType != null) {
			if (this.reportHeaderOutType.equals("function")) {
				reportHeader = RegExUtil.replaceCon(this.reportHeaderOutValue,
						this.conditions, paramValues);
				reportHeader = "<script>" + reportHeader + "</script>";
			} else {
				reportHeader = this.reportHeaderOutValue;
			}
		}
		return reportHeader;
	}

	private String getReportFooter(List allList, String[] paramValues) {
		String reportFooter = "";
		if (this.reportFooterOutType != null) {
			if (this.reportFooterOutType.equals("function")) {
				reportFooter = RegExUtil.replaceCon(this.reportFooterValue,
						this.conditions, paramValues);
				reportFooter = "<script>" + reportFooter + "</script>";
			} else {
				reportFooter = this.reportFooterValue;
			}
		}
		return reportFooter;
	}



	public String getReportBody(List dataList, String[] paramValues) {

		Dim rowDim = rowDims[0];
		Dim colDim = this.colDims[0];
		Measure measure = this.measures[0];

		Collection rows = new ArrayList();
		Set cols = new TreeSet(new Comparator() {

			public int compare(Object arg0, Object arg1) {
				return arg0.toString().compareTo(arg1.toString()) * (-1);
			}

		});

		// 追加列数据

		for (int i = 0; i < dataList.size(); i++) {
			Map map = (Map) dataList.get(i);
			if (!rows.contains(map.get(rowDim.getCode())))
				rows.add(map.get(rowDim.getCode()));

			cols.add(map.get(colDim.getCode()));
		}

		Map add_columns_values = new HashMap();
		for (java.util.Iterator it = rows.iterator(); it.hasNext();) {

			String row_caption = it.next().toString();
			Map columns_map = new HashMap();
			// m.put(row_caption, new BigDecimal(0));
			for (int i = 0; i < dataList.size(); i++) {
				Map map = (Map) dataList.get(i);
                
				for (int j = 0; j < this.columns.length; j++) {
					if (map.get(rowDim.getCode()).equals(row_caption))
						if (columns_map.containsKey(this.columns[j].getCode())) {
							BigDecimal bd = (BigDecimal) columns_map
									.get(this.columns[j].getCode());

							bd.add((BigDecimal) map.get(this.columns[j]
									.getCode()));
							columns_map.put(this.columns[j].getCode(), bd);
						} else {
							columns_map.put(this.columns[j].getCode(), map
									.get(this.columns[j].getCode()));
						}
				}
			}

			add_columns_values.put(row_caption, columns_map);
		}

		StringBuffer sb = new StringBuffer();
		sb
				.append("<table border=\"1px\" cellspacing=\"0\" class=\"thunderreport\" bordercolorlight=\"#333333\"  bordercolordark=\"#efefef\">");
		sb.append("<thead>");
		sb.append("<tr class=\"tr_head\">");
		sb.append("<td>"
				+ JavaScriptParser.getInstance().evalFunction("getName()")
				+ "</td>");
		for (java.util.Iterator cos_ = cols.iterator(); cos_.hasNext();) {
			sb.append("<td align=\"center\" noWrap=\"true\">").append(
					cos_.next().toString()).append("</td>");
		}
		for (int i = 0; i < this.columns.length; i++) {
			sb.append("<td align=\"center\" noWrap=\"true\">").append(
					this.columns[i].getName()).append("</td>");
		}
		sb.append("</tr>");
		sb.append("</thead>");
		int index = 0;
		// 对行进行循环
		sb.append("<tbody>");
		for (java.util.Iterator rows_ = rows.iterator(); rows_.hasNext();) {

			String rows_caption = rows_.next().toString();
			if (index % 2 == 0)
				sb
						.append(
								"<tr class=\"tr_odd\"><td align=\"left\" noWrap=\"true\">")
						.append(rows_caption).append("</td>");
			else
				sb
						.append(
								"<tr class=\"tr_even\"><td align=\"left\" noWrap=\"true\">")
						.append(rows_caption).append("</td>");
			// 对列进行循环
			for (java.util.Iterator cos_ = cols.iterator(); cos_.hasNext();) {

				sb.append("<td align=\"right\" noWrap=\"true\">&nbsp;");
				String col_caption = cos_.next().toString();
				// 数据循环
				for (int i = 0; i < dataList.size(); i++) {
					Map map = (Map) dataList.get(i);
					if (map.get(rowDim.getCode()).toString().equals(
							rows_caption)
							&& map.get(colDim.getCode()).toString().equals(
									col_caption)) {
						sb.append(map.get(measure.getCode()));
					}
				}
				sb.append("</td>");

			}

			// 追加列的循环
			for (int i = 0; i < this.columns.length; i++) {
				sb.append("<td align=\"right\" noWrap=\"true\">");
				if (columns[i].getOutputtype() == null
						|| "".equals(columns[i].getOutputtype()))
					sb.append(((Map) add_columns_values.get(rows_caption))
							.get(columns[i].getCode()));
				else {
					String fnb_value = RegExUtil.replaceParam(columns[i]
							.getColumValue(), ((Map) add_columns_values
							.get(rows_caption)));

					sb.append(JavaScriptParser.getInstance().evalFunction(
							fnb_value));
				}
				sb.append("</td>");
			}
			sb.append("</tr>");

			index++;

		}
		sb.append("<tbody>");
		sb.append("</table>");
		return sb.toString();
	}

	
	public String toHtml(List dataList, List allList, String[] paramValues) {
		String table = "<table class=\"page\" width=\"100%\"><tr><td class=\"reportHeader\">"
			+ getReportHeader(allList,paramValues) + "</td></tr><tr><td>";
	table += this.getReportBody(allList,paramValues);
	return table += "</td></tr><tr><td>" + getReportFooter(allList,paramValues)
			+ "</td></tr></table>";
		
	}

	public String formatContext(List data, List allList, String[] paramValues) {
		// TODO Auto-generated method stub
		return toHtml(data,allList,paramValues);
	}

	public String getTemplateVm() {
		// TODO Auto-generated method stub
		return this.templatefilename;
	}
}
