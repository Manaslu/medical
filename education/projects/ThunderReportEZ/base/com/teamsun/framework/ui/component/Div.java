package com.teamsun.framework.ui.component;

import java.util.Iterator;
import java.util.Set;

public class Div extends AbstractComponent {
	private String content;

	private String id;

	/**
	 * 构造函数 Div()
	 * 
	 * @param id
	 *            层的标识
	 * @param content
	 *            层的内容
	 */
	public Div(String id, String content) {
		this.content = content;
		this.id = id;
	}

	public String toHtmlString() {
		String htmlString = "";
		String propString = "";
		if (propMap.isEmpty()) {
			htmlString += "<div id=\"" + id + "\">" + content + "</div>";
		} else {
			Set propSet = propMap.keySet();
			Iterator propIter = propSet.iterator();
			while (propIter.hasNext()) {
				String propName = propIter.next().toString();
				String propValue = propMap.get(propName).toString();
				propString += " " + propName + "\"" + propValue + "\"";
			}
			htmlString += "<div id=\"" + id + "\"" + propString + ">" + content
					+ "</div>";
		}
		return htmlString;
	}

	public static void main(String[] args) {
		Button button = new Button("button", "button");
		Div div = new Div("div", button.toHtmlString());
		String htmlString = div.toHtmlString();
		System.out.print(htmlString);
	}

}
