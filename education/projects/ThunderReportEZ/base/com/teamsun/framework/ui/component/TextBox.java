package com.teamsun.framework.ui.component;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.StringEscapeUtils;

public class TextBox extends AbstractComponent {
	private String name;

	private String value;

	private int datalength;

	private int inputcols;

	/**
	 * 构造函数
	 * 
	 * @param name
	 *            文本框的名字
	 * @param value
	 *            文本框的内容
	 * @param datalength
	 *            文本框的最大长度
	 * 
	 */
	public TextBox(String name, String value, int datalength, int inputcols) {
		this.name = name;
		this.value = value;
		this.datalength = datalength;
		this.inputcols = inputcols;
	}

	public String toHtmlString() {
		String htmlString = "";
		String propString = "";
		if (propMap.isEmpty()) {
			htmlString += "<input type=text id=\"" + name + "\" name=\"" + name
					+ "\" value=\"" + StringEscapeUtils.escapeHtml(value)
					+ "\" maxlength=\"" + datalength + "\" size=\"" + inputcols
					+ "\" />";
		} else {
			Set propSet = propMap.entrySet();
			Iterator propIter = propSet.iterator();
			while (propIter.hasNext()) {
				Entry propEntry = (Map.Entry) propIter.next();
				String propName = propEntry.getKey().toString();
				String propValue = propEntry.getValue().toString();
				propString += " " + propName + "=\"" + propValue + "\"";
			}
			htmlString += "<input type=text id=\"" + name + "\" name=\"" + name
					+ "\" value=\"" + StringEscapeUtils.escapeHtml(value)
					+ "\" maxlength=\"" + datalength + "\" size=\"" + inputcols
					+ "\"" + propString + "/>";

		}
		return htmlString;
	}

	public static void main(String[] args) {
		TextBox textBox = new TextBox("textbox", "textbox", 11, 20);
		textBox.addProp("ok", "nono");
		textBox.addProp("ok", "nono");
		String htmlString = textBox.toHtmlString();
		System.out.print(htmlString);
	}

}
