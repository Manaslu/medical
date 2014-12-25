package com.teamsun.framework.ui.component;

import java.util.Iterator;
import java.util.Set;

public class CheckBox extends AbstractComponent {
	private String id;

	private String name;

	private String value;

	private boolean checked;

	/**
	 * 构造函数：checkBox()
	 * 
	 * @param name
	 *            checkBox名字
	 * @param value
	 *            checkBox的值
	 * @param checked
	 *            是否被选中
	 */
	public CheckBox(String name, String value, boolean checked) {
		this.id = name;
		this.name = name;
		this.checked = checked;
		this.value = value;
	}

	public String toHtmlString() {
		String htmlString = "";
		String propString = "";
		if (propMap.isEmpty()) {
			htmlString += "<input type=checkbox id=\"" + id + "\" name=\""
					+ name + "\" value=\"" + value + "\"";
			if (checked) {
				htmlString += " checked";
			} else
				htmlString += "";
			htmlString += "/>";
		} else {
			Set propSet = propMap.keySet();
			Iterator propIter = propSet.iterator();
			while (propIter.hasNext()) {
				String propName = propIter.next().toString();
				String propValue = propMap.get(propName).toString();
				propString += " " + propName + "=\"" + propValue + "\"";
			}
			htmlString += "<input type=checkbox id=\"" + id + "\" name=\""
					+ name + "\" value=\"" + value + "\"" + propString;
			if (checked) {
				htmlString += " checked";
			} else
				htmlString += "";
			htmlString += " />";
		}
		return htmlString;
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked()
	{
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}

	public static void main(String[] args) {
		CheckBox checkbox = new CheckBox("cb", "choose", true);
		checkbox.addProp("ss", "ddd");
		String htmlString = checkbox.toHtmlString();
		System.out.print(htmlString);
	}

}