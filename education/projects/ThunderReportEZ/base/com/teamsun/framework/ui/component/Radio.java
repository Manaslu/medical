package com.teamsun.framework.ui.component;

import java.util.Iterator;
import java.util.Set;

public class Radio extends AbstractComponent {
	// private String id;
	private String name;

	private String value;

	private boolean checked;

	private String text;

	public Radio(String name, String value, String text, boolean checked) {
		this.name = name;
		// this.id = name;
		this.text = text;
		this.value = value;
		this.checked = checked;
	}

	public String toHtmlString() {
		String htmlString = "";
		String propString = "";
		if (propMap.isEmpty()) {
			htmlString += "<input type=radio name=\"" + name + "\" value=\""
					+ value + "\"";
			if (checked) {
				htmlString += " checked=\"true\"";
			} else
				htmlString += "";
			htmlString += "/>" + text;
		} else {
			Set propSet = propMap.keySet();
			Iterator propIter = propSet.iterator();
			while (propIter.hasNext()) {
				String propName = propIter.next().toString();
				String propValue = propMap.get(propName).toString();
				propString += " " + propName + "=\"" + propValue + "\"";
			}
			htmlString += "<input type=radio name=\"" + name + "\" value=\""
					+ value + "\"" + propString;
			if (checked) {
				htmlString += " checked";
			} else
				htmlString += "";
			htmlString += " />" + text;
		}
		return htmlString;
	}

	public static void main(String[] args) {
		Radio radio = new Radio("ra", "ra", "test", true);
		System.out.print(radio.toHtmlString());
	}
}
