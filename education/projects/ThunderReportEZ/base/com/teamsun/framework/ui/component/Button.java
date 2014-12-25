package com.teamsun.framework.ui.component;

import java.util.Iterator;
import java.util.Set;

public class Button extends AbstractComponent {

	private String name;

	private String value;

	/**
	 * 构造函数：button()
	 * 
	 * @param name
	 *            按钮名字
	 * @param value
	 *            按钮的值
	 * 
	 */
	public Button(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String toHtmlString() {
		String htmlString = "";
		String propString = "";
		if (propMap.isEmpty()) {
			htmlString += "<input type=\"button\" id=\"" + name + "\" name=\""
					+ name + "\"   value=\"" + value + "\"/>";
		} else {
			Set propset = propMap.keySet();
			Iterator propIter = propset.iterator();
			while (propIter.hasNext()) {
				String propName = (String) propIter.next();
				String propValue = (String) this.propMap.get(propName);
				propString += " " + propName + "=\"" + propValue + "\"";
			}
			htmlString += "<input type=\"button\" id=\"" + name + "\" name=\""
					+ name + "\" value=\"" + value + "\"" + propString + "/>";

		}

		return htmlString;
	}

	public static void main(String[] args) {
		Button button = new Button("zhu", "zhu");
		// button.addProp("sss", "value");
		button.addProp("dd", "dd");
		String s = button.toHtmlString();
		System.out.println(s);
	}
}
