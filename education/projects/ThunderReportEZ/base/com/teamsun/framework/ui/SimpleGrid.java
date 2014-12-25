package com.teamsun.framework.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.teamsun.framework.util.RegExUtil;
import com.teamsun.thunderreport.javascript.JavaScriptParser;
import com.teamsun.thunderreport.parse.Column;
import com.teamsun.thunderreport.parse.Sql;

public class SimpleGrid extends ListGrid {

	private String getReportHeader(String[] paramValues) {
		String reportHeader = "";
		if (this.reportHeaderOutType != null) {
			if (this.reportHeaderOutType.equals("function")) {
				reportHeader = RegExUtil.replaceCon(this.reportHeaderOutValue,
						this.conditions, paramValues);
				reportHeader = JavaScriptParser.getInstance().evalFunction(
						reportHeader).toString();
			} else {
				reportHeader = this.reportHeaderOutValue;
			}
		}
		return reportHeader;
	}

	private String getReportFooter(String[] paramValues) {
		String reportFooter = "";
		if (this.reportFooterOutType != null) {
			if (this.reportFooterOutType.equals("function")) {
				reportFooter = RegExUtil.replaceCon(this.reportFooterValue,
						this.conditions, paramValues);
				reportFooter = JavaScriptParser.getInstance().evalFunction(
						reportFooter).toString();
			} else {
				reportFooter = this.reportFooterValue;
			}
		}
		return reportFooter;
	}

	private String getReportBody(List dataList,String[] paramValues) {
		StringBuffer sb = new StringBuffer();
		int cols = this.cols.length;
		int rows = dataList.size();
		Map[] mapArr = new Map[rows];
		for (int i = 0; i < rows; i++) {
			mapArr[i] = (Map) dataList.get(i);
		}
		sb
				.append("<table width=\"100%\" border=\"1px\" cellpadding=\"0\" cellspacing=\"0\" class=\"thunderreport\" bordercolorlight=\"#333333\"  bordercolordark=\"#efefef\">\n<thead>\n");
		if (this.reportBodyFunTitleType != null) {
			if (this.reportBodyFunTitleType.equals("function")) {
				String fnt_value = RegExUtil.replaceCon(
						this.reportBodyFunTitleValue, this.conditions,
						paramValues);
				sb.append(JavaScriptParser.getInstance().evalFunctionFullText(
						fnt_value));
			} else {
				String fnt_value = RegExUtil.replaceCon(
						this.reportBodyFunTitleValue, this.conditions,
						paramValues);
				sb.append(fnt_value);
			}
		} else {
			sb.append("<tr class=\"tr_head\">");
			for (int i = 0; i < cols; i++) {
				String colName = this.cols[i].getName();
				sb.append("<td noWrap=\"true\">" + colName + "</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</thead>\n");

		if (dataList.size() == 0 || dataList == null) {
			sb.append("<tr id=\"norecordtr\"><td colspan='" + cols
					+ "' align='center'>没有数据</td></tr></table>");
			return sb.toString();
		}

		sb.append("<tbody>\n");
		for (int i = 0; i < rows; i++) {
			if (i % 2 == 0)
				sb.append("<tr class=\"tr_odd\">");
			else
				sb.append("<tr class=\"tr_even\">");
			
			for (int j = 0; j < cols; j++) {
				Map dataMap = mapArr[i];
				if (this.cols[j].getOutputtype() != null
						&& this.cols[j].getOutputtype().equals("function")) {
					String fnb_value = this.cols[j].getColumValue();
					if (this.conditions != null && paramValues != null) {
						fnb_value = RegExUtil.replaceCon(this.cols[j]
								.getColumValue(), this.conditions,
								paramValues);
					}
					Object attrValue = dataMap.get(this.cols[j].getCode()
							.toUpperCase());
					if (attrValue == null) {
						attrValue = "";
					} else {
						fnb_value = RegExUtil.replaceParam(fnb_value, dataMap);
//						String alignStr = this.cols[j].getType() == null ? ""
//								: " align=\""
//										+ (this.cols[j].getType()
//												.equalsIgnoreCase("measure") ? "right"
//												: "left") + "\" ";
						String alignStr = this.cols[j].getTextAlign();
						if(alignStr==null){
							alignStr="center";
//							if(this.cols[j].getType().equalsIgnoreCase("measure")){
//								alignStr="right";
//							}else{
//								alignStr="left";
//							}								
						}
						alignStr="align=\""+alignStr+"\"";
						sb.append("<td noWrap=\"true\" "
								+ alignStr
								+ ">"
								+ JavaScriptParser.getInstance().evalFunction(
										fnb_value) + "</td>");
					}
				} else if (this.cols[j].getOutputtype() != null
						&& this.cols[j].getOutputtype().equals("print")) {
//					String alignStr = this.cols[j].getType() == null ? ""
//							: " align=\""
//									+ (this.cols[j].getType().equalsIgnoreCase(
//											"measure") ? "right" : "left")
//									+ "\" ";
					String alignStr = this.cols[j].getTextAlign();
					if(alignStr==null){
//						if(this.cols[j].getType().equalsIgnoreCase("measure")){
//							alignStr="right";
//						}else{
//							alignStr="left";
//						}		
						alignStr="center";
					}
					alignStr="align=\""+alignStr+"\"";
					sb.append("<td noWrap=\"true\" " + alignStr + ">"
							+ this.cols[j].getCode() + "</td>");
				} else {
					Object attrValue = dataMap.get(this.cols[j].getCode()
							.toUpperCase());
					if (attrValue == null
							|| attrValue.toString().trim().equals("")) {
						attrValue = "&nbsp;";
					}
					String formatStr = this.cols[j].getFormat() == null ? ""
							: this.cols[j].getFormat();
					if (!formatStr.equals("")) {
						attrValue = SimpleFormat.formatStr(
								attrValue.toString(), formatStr);
					}
//					String alignStr = this.cols[j].getType() == null ? ""
//							: " align=\""
//									+ (this.cols[j].getType().equalsIgnoreCase(
//											"measure") ? "right" : "left")
//									+ "\" ";
					String alignStr = this.cols[j].getTextAlign();
					if(alignStr==null){
//						if(this.cols[j].getType().equalsIgnoreCase("measure")){
//							alignStr="right";
//						}else{
//							alignStr="left";
//						}
						alignStr="center";
					}
					alignStr="align=\""+alignStr+"\"";
					sb.append("<td noWrap=\"true\" " + alignStr + ">"
							+ attrValue.toString() + "</td>");
				}
			}
			sb.append("</tr>\n");			
		}
		String str=getTotalCountTr(dataList);
		sb.append(str);
		str=getSumTr(dataList);
		sb.append(str);
		sb.append("</tbody>\n</table>\n");
		return sb.toString();
	}
	
	public String getTotalCountTr(List dataList){
		int cols = this.cols.length;
		int rows = dataList.size();
		int flag=0;
		String total_tr="<tr class=\"tr_odd\">";
		for (int j = 0; j < cols; j++) {
			if(this.cols[j].getAggrfunc()!=null&&this.cols[j].getAggrfunc().equals("COUNT")){
				flag=1;
				total_tr+="<td noWrap=\"true\">"+rows+"</td>";
			}else{
				total_tr+="<td noWrap=\"true\">&nbsp;</td>";
			}
		}
		total_tr+="</tr>";
		if(flag==1){
			return total_tr;
		}
		return "";
	}
	
	public String getSumTr(List dataList){
		int cols = this.cols.length;
		int rows = dataList.size();
		int flag=0;
		
		String total_tr="<tr class=\"tr_odd\">";
		for (int j = 0; j < cols; j++) {
			if(this.cols[j].getAggrfunc()!=null&&this.cols[j].getAggrfunc().equals("SUM")){
				flag=1;
				break;
			}				
		}
		if(flag==1){
			String[] sumTrValue= getColSum(dataList);
			for (int j = 0; j < cols; j++) {
				String alignstr=this.cols[j].getTextAlign();
				if(alignstr!=null&&!alignstr.equals("")){
				  total_tr+="<td align="+alignstr+" noWrap=\"true\">"+sumTrValue[j]+"</td>";
				}else{
					  total_tr+="<td noWrap=\"true\">"+sumTrValue[j]+"</td>";
					
				}
			}
		}
		total_tr+="</tr>";
		if(flag==1){
			return total_tr;
		}
		return "";		
	}
	public String[] getColSum(List dataList){		
		int cols = this.cols.length;
		int rows = dataList.size();
		double[] colValue=new double[cols];
		String[] retValue=new String[cols];
		for(int i=0;i<rows;i++){
			Map dataMap=(Map)dataList.get(i);
			for(int j=0;j<cols;j++){
				if(this.cols[j].getType()!=null&&this.cols[j].getType().equals("measure")){
					colValue[j]+=Double.parseDouble(dataMap.get(this.cols[j].getCode().toUpperCase()).toString());
				}
			}
		}
		for (int j = 0; j < cols; j++) {
			if (this.cols[j].getType() != null
					&& this.cols[j].getType().equals("measure")) {
				retValue[j] = new Double(colValue[j]).toString();
				String formatStr = this.cols[j].getFormat() == null ? ""
						: this.cols[j].getFormat();
				String aggrFuncStr = this.cols[j].getAggrfunc() == null ? ""
						: this.cols[j].getAggrfunc();
				
				if(!aggrFuncStr.equals("")){
					retValue[j]=processFun(aggrFuncStr,colValue);
				}
				
				if (!formatStr.equals("")) {
					retValue[j] = SimpleFormat
							.formatStr(retValue[j], formatStr);
				}
				
			}else if(this.cols[j].getSumname() != null) {
				retValue[j] = this.cols[j].getSumname();
				continue;
			}else{
				retValue[j] = "&nbsp;";
			}
		}
		return retValue;
	}
	
	private String processFun(String funExp,double[] colValue){
		if(funExp.indexOf("DIVIDE")>-1){
			int i=funExp.indexOf("DIVIDE");
			int j=funExp.indexOf(",");
			String colo=funExp.substring(i+7, j);
			String col1=funExp.substring(j+1, funExp.length()-1);
			double valueo=0,value1=0;
			for(int k=0;k<this.cols.length;k++){
				if(colo.toUpperCase().equals(this.cols[k].getCode().toUpperCase())){
					valueo=colValue[k];
				}
				if(col1.toUpperCase().equals(this.cols[k].getCode().toUpperCase())){
					value1=colValue[k];
				}
			}
			return new Double((valueo/value1)*100).toString();
		}
		return "";
	}
	

	public String toHtml(List dataList,List allData,String[] paramValues) {

		String table = "<table width=\"100%\" class=\"page\">";

		String header = getReportHeader(paramValues);
		if (header != null && !"".equals(header)) {
			table += "<tr><td class=\"reportHeader\">" + header + "</td></tr>";
		}
		table += "<tr><td>" + getReportBody(dataList,paramValues) + "</td></tr>";
		String footer = getReportFooter(paramValues);
		if (footer != null && !footer.equals("")) {
			table += "<tr><td>" + header + "</td></tr>";
		}
		return table + "</table>";
	}

	public static void main(String[] args) {
		Column col = new Column();
		col.setName("国家");
		col.setCode("country");
		col.setColumValue("{country}");

		Column col1 = new Column();
		col1.setName("省");
		col1.setCode("province");
		col1.setColumValue("{province}");

		Column col2 = new Column();
		col2.setName("城市");
		col2.setCode("city");
		col2.setColumValue("{city}");

		Column col3 = new Column();
		col3.setName("sales3");
		col3.setCode("sales3");
		col3.setColumValue("{sales3}");

		Column col4 = new Column();
		col4.setName("sales4");
		col4.setColumValue("{sales4}");
		col4.setCode("sales4");

		Column col5 = new Column();
		col5.setName("sales5");
		col5.setColumValue("{sales5}");
		col5.setCode("sales5");

		Column col6 = new Column();
		col6.setName("sales6");
		col6.setColumValue("{sales6}");
		col6.setCode("sales6");

		Column col7 = new Column();
		col7.setName("sales7");
		col7.setColumValue("{sales7}");
		col7.setCode("sales7");
		col7.setType("measure");
		col7.setFormat("#,###");

		Column col8 = new Column();
		col8.setName("sales8");
		col8.setColumValue("{sales8}");
		col8.setCode("sales8");

		Column[] cols = { col, col1, col2, col3, col4, col5, col6, col7, col8 };

		List li = new ArrayList();
		HashMap a = new HashMap();
		a.put("country", "信函");
		a.put("province", "北京");
		a.put("city", "西城区");
		a.put("sales3", "300");
		a.put("sales4", "300");
		a.put("sales5", "300");
		a.put("sales6", "300");
		a.put("sales7", "3323200");
		a.put("sales8", "3323200");
		li.add(0, a);

		HashMap b = new HashMap();
		b.put("country", "信函");
		b.put("province", "北京");
		b.put("city", "东城区");
		b.put("sales3", "200");
		b.put("sales4", "200");
		b.put("sales5", "200");
		b.put("sales6", "200");
		b.put("sales7", "200");
		b.put("sales8", "200");
		li.add(1, b);

		HashMap c = new HashMap();
		c.put("country", "信函");
		c.put("province", "上海");
		c.put("city", "浦东");
		c.put("sales3", "100");
		c.put("sales4", "100");
		c.put("sales5", "100");
		c.put("sales6", "100");
		c.put("sales7", "100");
		c.put("sales8", "100");
		li.add(2, c);

		HashMap d = new HashMap();
		d.put("country", "信函");
		d.put("province", "上海");
		d.put("city", "浦东");
		d.put("sales3", "220");
		d.put("sales4", "220");
		d.put("sales5", "220");
		d.put("sales6", "220");
		d.put("sales7", "220");
		d.put("sales8", "220");
		li.add(3, d);

		HashMap e = new HashMap();
		e.put("country", "邮件");
		e.put("province", "北京");
		e.put("city", "西城区");
		e.put("sales3", "220");
		e.put("sales4", "220");
		e.put("sales5", "220");
		e.put("sales6", "220");
		e.put("sales7", "220");
		e.put("sales8", "220");
		li.add(4, e);

		HashMap f = new HashMap();
		f.put("country", "邮件");
		f.put("province", "北京");
		f.put("city", "东城区");
		f.put("sales3", "220");
		f.put("sales4", "220");
		f.put("sales5", "220");
		f.put("sales6", "220");
		f.put("sales7", "220");
		f.put("sales8", "220");
		li.add(5, f);

		HashMap g = new HashMap();
		g.put("country", "邮件");
		g.put("province", "上海");
		g.put("city", "浦东");
		g.put("sales3", "220");
		g.put("sales4", "220");
		g.put("sales5", "220");
		g.put("sales6", "220");
		g.put("sales7", "220");
		g.put("sales8", "220");
		li.add(6, g);

		HashMap h = new HashMap();
		h.put("country", "邮件");
		h.put("province", "上海");
		h.put("city", "浦西");
		h.put("sales3", "220");
		h.put("sales4", "220");
		h.put("sales5", "220");
		h.put("sales6", "220");
		h.put("sales7", "220");
		h.put("sales8", "220");
		li.add(7, h);

		HashMap h1 = new HashMap();
		h1.put("country", "邮件");
		h1.put("province", "上海");
		h1.put("city", "浦西");
		h1.put("sales3", "220");
		h1.put("sales4", "220");
		h1.put("sales5", "220");
		h1.put("sales6", "220");
		h1.put("sales7", "220");
		h1.put("sales8", "220");
		li.add(8, h1);

		HashMap h2 = new HashMap();
		h2.put("country", "邮件");
		h2.put("province", "上海");
		h2.put("city", "浦西");
		h2.put("sales3", "220");
		h2.put("sales4", "220");
		h2.put("sales5", "220");
		h2.put("sales6", "220");
		h2.put("sales7", "220");
		h2.put("sales8", "220");
		li.add(9, h2);

		HashMap h3 = new HashMap();
		h3.put("country", "邮件");
		h3.put("province", "上海");
		h3.put("city", "浦东x");
		h3.put("sales3", "220");
		h3.put("sales4", "220");
		h3.put("sales5", "220");
		h3.put("sales6", "220");
		h3.put("sales7", "220");
		h3.put("sales8", "220");
		li.add(10, h3);

		HashMap h4 = new HashMap();
		h4.put("country", "邮件");
		h4.put("province", "上海");
		h4.put("city", "浦东x");
		h4.put("sales3", "220");
		h4.put("sales4", "220");
		h4.put("sales5", "220");
		h4.put("sales6", "220");
		h4.put("sales7", "220");
		h4.put("sales8", "220");
		li.add(11, h4);

		ListGrid sg = ListGridFactory.getGrid(cols);
		sg.setReportBodyFunTitleType("");
		sg
				.setReportBodyFunTitleValue("<tr class=\"tr_head\"><td rowspan=\"3\">单位名称</td><td colspan=\"4\">业务总量</td>"
						+ "<td colspan=\"4\">业务收入</td></tr><tr class=\"tr_head\"><td colspan=\"2\">本月</td><td colspan=\"2\">本年累计</td>"
						+ "<td colspan=\"2\">本月</td><td colspan=\"2\">本年累计</td>"
						+ "</tr><tr class=\"tr_head\"><td>实绩</td><td>占总量比重</td><td>实绩</td><td>占总量比重</td>"
						+ "<td>实绩</td><td>占总量比重</td><td>实绩</td><td>占总量比重</td></tr>");
		sg.setCols(cols);
		//sg.setDataList(li);
		//System.out.print(sg.toHtml());
	}

	public String formatContext(List data,List allData,String[] paramValues) {
		//this.setDataList(data);
		return this.toHtml(data,allData,paramValues);
	}	

	public String getTemplateVm() {
		// TODO Auto-generated method stub
		return this.templatefilename;
	}
}
