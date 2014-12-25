package com.teamsun.framework.ui.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TR_content extends AbstractComponent {
	private String content;

	private List cells = new ArrayList();

	/**
	 * 构造函数
	 * 
	 * @param content
	 *            TR的内容（字符串）
	 */
	public TR_content(String content) {
		this.content = content;
	}

	public String toHtmlString() {
		String htmlString = "";
		String propString = "";
		String tdString = "";
		// 得到TD对象集合返回html字符串
		if (cells.isEmpty()) {
			tdString = "";
		} else {
			Iterator tdIter = cells.iterator();
			while (tdIter.hasNext()) {
				tdString += ((TD) tdIter.next()).toHtmlString();
			}
		}
		// 添加属性
		if (propMap.isEmpty()) {
			htmlString += "<tr>" + content + tdString + "</tr>";
		} else {
			Set propset = propMap.keySet();
			Iterator propIter = propset.iterator();
			while (propIter.hasNext()) {
				String propName = (String) propIter.next();
				String propValue = (String) this.propMap.get(propName);
				propString += " " + propName + "=\"" + propValue + "\"";
			}
			htmlString += "<tr" + propString + ">" + content + tdString
					+ "</tr>";
		}
		return htmlString;
	}

	public List getCells() {
		return cells;
	}

	public void addCell(TD td) {
		cells.add(td);
	}

	public void addCells(List tds) {
		cells.addAll(tds);
	}

	public static void main(String[] args) {
		String content = "<td>a</td><td>b</td>";
		TD td1 = new TD("aaa");
		TR_content tr = new TR_content(content);
		tr.addCell(td1);
		tr.addCell(td1);
		String htmlString = tr.toHtmlString();
		System.out.print(htmlString);
	}

}
