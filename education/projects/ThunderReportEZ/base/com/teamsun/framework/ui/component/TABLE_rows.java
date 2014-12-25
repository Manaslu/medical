package com.teamsun.framework.ui.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TABLE_rows extends AbstractComponent {
	private List rows;

	/**
	 * 构造函数：TABLE_rows()
	 * 
	 * @param rows
	 *            TABLE的行的List
	 */
	public TABLE_rows(List rows) {
		this.rows = rows;
	}

	public String toHtmlString() {
		String htmlString = "";
		String propString = "";
		String trString = "";
		// 得到tr对象集合返回的字符串
		if (rows.isEmpty()) {
			trString += "";
		} else {
			Iterator trIter = rows.iterator();
			while (trIter.hasNext()) {
				trString += ((TR_cells) trIter.next()).toHtmlString();
			}
		}
		// 添加属性
		if (propMap.isEmpty()) {
			htmlString += "<table>" + trString + "</table>";
		} else {
			Set propSet = propMap.keySet();
			Iterator propIter = propSet.iterator();
			while (propIter.hasNext()) {
				String propName = propIter.next().toString();
				String propValue = this.propMap.get(propName).toString();
				propString += " " + propName + "=\"" + propValue + "\"";
			}
			htmlString += "<table" + propString + ">" + trString + "</table>";
		}
		return htmlString;
	}

	public List getRows() {
		return rows;
	}

	public void addRow(TR_cells tr) {
		rows.add(tr);
	}

	public void addRows(List trs) {
		rows.addAll(trs);
	}

	public static void main(String[] args) {
		TD td1 = new TD("aaa");
		TD td2 = new TD("bbb");
		td1.addProp("id", "001");
		List TDlist = new ArrayList();
		TDlist.add(0, td1);
		TDlist.add(1, td2);
		TR_cells tr1 = new TR_cells(TDlist);
		TR_cells tr2 = new TR_cells(TDlist);
		tr1.addProp("id", "001");
		tr1.addCell(td1);
		List TRlist = new ArrayList();
		TRlist.add(0, tr1);
		TRlist.add(1, tr2);
		TABLE_rows table = new TABLE_rows(TRlist);
		table.addRow(tr1);
		table.addRows(TRlist);
		String toHtmlString = table.toHtmlString();
		System.out.print(toHtmlString);
	}
}
