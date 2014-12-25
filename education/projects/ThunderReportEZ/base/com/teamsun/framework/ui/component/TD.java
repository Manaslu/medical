package com.teamsun.framework.ui.component;

import java.util.Iterator;
import java.util.Set;

public class TD extends AbstractComponent {
	private String content;

	/**
	 * 构造函数：TD()
	 * 
	 * @param content
	 *            TD中的内容
	 */
	public TD(String content) {
		this.content = content;
	}

	public String toHtmlString() {
		String htmlString = "";
		String propString = "";
		if (propMap.isEmpty()) {
			htmlString = "<td>" + content + "</td>";
		} else {
			Set propset = propMap.keySet();
			Iterator propIter = propset.iterator();
			while (propIter.hasNext()) {
				String propName = (String) propIter.next();
				String propValue = (String) this.propMap.get(propName);
				propString += " " + propName + "=\"" + propValue + "\"";
			}
			htmlString += "<td" + propString + ">" + content + "</td>";
		}
		return htmlString;
	}

	public static void main(String[] args) {
		TD td = new TD("aaa");
		td.addProp("id", "001");
		String htmlString = td.toHtmlString();
		System.out.print(htmlString);
	}

}
