package com.teamsun.framework.ui.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TR_cells extends AbstractComponent {
	private List cells;

	/**
	 * 构造函数
	 * @param cells TD的列表
	 */
	public TR_cells(List cells) {
		this.cells = cells;
	}

	public String toHtmlString() {
		String htmlString = "";
		String propString = "";
		String tdString = "";
		//得到TD对象集合返回html字符串       
		if (cells.isEmpty()) {
			tdString = "";
		} else {
			Iterator tdIter = cells.iterator();
			while (tdIter.hasNext()) {
				tdString += ((TD) tdIter.next()).toHtmlString();
			}
		}
		//添加属性
		if (propMap.isEmpty()) {
			htmlString += "<tr>" + tdString + "</tr>";
		}

		else {
			Set propset = propMap.keySet();
			Iterator propIter = propset.iterator();
			while (propIter.hasNext()) {
				String propName = (String) propIter.next();
				String propValue = (String) this.propMap.get(propName);
				propString += " " + propName + "=\"" + propValue + "\"";
			}
			htmlString += "<tr" + propString + ">" + tdString + "</tr>";
		}
		return htmlString;
	}

	//返回TD对象的集合
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
		TD td1 = new TD("test1");
		TD td2 = new TD("test2");
		td1.addProp("111", "value");
		List TDlist = new ArrayList();
		TDlist.add(0, td1);
		TDlist.add(1, td2);
		TR_cells tr = new TR_cells(TDlist);
		tr.addProp("aaa", "value");
		tr.addCell(td1);
		tr.addCells(TDlist);
		List tds = tr.getCells();
		Iterator td = tds.iterator();
		String cellsString = "";
		while (td.hasNext()) {
			cellsString += ((TD) td.next()).toHtmlString();
		}
		String htmlString = tr.toHtmlString();
		System.out.print(cellsString);
	}
}
