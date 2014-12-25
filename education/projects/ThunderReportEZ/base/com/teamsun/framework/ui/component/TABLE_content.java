package com.teamsun.framework.ui.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TABLE_content extends AbstractComponent {
	private String content;

	private List rows = new ArrayList();

	/**
	 * 构造函数：TABLE_conent()
	 * 
	 * @param content
	 *            table中的字符串内容
	 */
	public TABLE_content(String content) {
		this.content = content;
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

	public String toHtmlString() {
		String htmlString = "";
		String trString = "";
		String propString = "";
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
			htmlString += "<table>" + content + trString + "</table>";
		} else {
			Set propset = propMap.keySet();
			Iterator propIter = propset.iterator();
			while (propIter.hasNext()) {
				String propName = (String) propIter.next();
				String propValue = (String) this.propMap.get(propName);
				propString += " " + propName + "=\"" + propValue + "\"";
			}
			htmlString += "<table" + propString + ">" + content + trString
					+ "</table>";
		}
		return htmlString;
	}

	public static void main(String[] args) {
		String content = "<tr><td>aaa</td></tr>";
		TD td1 = new TD("aaa");
		TD td2 = new TD("ccc");
		List tdList = new ArrayList();
		tdList.add(0, td1);
		tdList.add(1, td2);
		TR_cells tr1 = new TR_cells(tdList);
		TR_cells tr2 = new TR_cells(tdList);
		TABLE_content table = new TABLE_content(content);
		table.addRow(tr1);
		table.addRow(tr1);
		String htmlString = table.toHtmlString();
		System.out.print(htmlString);
	}

}
