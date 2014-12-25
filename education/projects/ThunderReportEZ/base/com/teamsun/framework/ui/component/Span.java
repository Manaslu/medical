package com.teamsun.framework.ui.component;


import java.util.Iterator;
import java.util.Set;

public class Span extends AbstractComponent {
	private String content;

	private String id;

	/**
	 * 构造函数 Span()
	 * 
	 * @param id
	 *            标识
	 * @param content
	 *            内容
	 */
	public Span(String id, String content) {
		this.content = content;
		this.id = id;
	}

	public String toHtmlString() {
		String htmlString = "";
		String propString = "";
		if (propMap.isEmpty()) {
			htmlString += "<span id=\"" + id + "\">" + content + "</span>";
		} else {
			Set propSet = propMap.keySet();
			Iterator propIter = propSet.iterator();
			while (propIter.hasNext()) {
				String propName = propIter.next().toString();
				String propValue = propMap.get(propName).toString();
				propString += " " + propName + "\"" + propValue + "\"";
			}
			htmlString += "<span id=\"" + id + "\"" + propString + ">" + content
					+ "</span>";
		}
		return htmlString;
	}

	public static void main(String[] args) {
		Span div = new Span("div", "1111");
		String htmlString = div.toHtmlString();
		System.out.print(htmlString);
	}

}

