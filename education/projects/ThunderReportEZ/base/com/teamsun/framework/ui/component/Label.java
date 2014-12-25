package com.teamsun.framework.ui.component;

import java.util.Iterator;
import java.util.Set;

public class Label extends AbstractComponent {
	private String id;

	private String value;

	private String lFor;

	/**
	 * 构造函数：Lable()
	 * 
	 * @param id
	 *            Lable的标识
	 * @param value
	 *            Lable的值
	 * @param lFor
	 *            与其它标签id的关联值
	 */
	public Label(String id, String value, String lFor) {
		this.id = id;
		this.value = value;
		this.lFor = lFor;
	}

	public String toHtmlString() {
		String htmlString = "";
		String propString = "";
		if (propMap.isEmpty()) {
			htmlString = "<label id=\"" + id + "\" for=\"" + lFor + "\">"
					+ value + "</label>";
		} else {
			Set propSet = propMap.keySet();
			Iterator propIter = propSet.iterator();
			while (propIter.hasNext()) {
				String propName = propIter.next().toString();
				String propValue = propMap.get(propName).toString();
				propString += " " + propName + "=\"" + propValue + "\"";
			}
			htmlString += "<label id=\"" + id + "\" for=\"" + lFor + "\""
					+ propString + ">" + value + "</label>";
		}
		return htmlString;
	}

	public static void main(String[] args) {
		Button button = new Button("test", "test");
		button.addProp("id", "button");
		Label label = new Label("label", "test", "button");
		System.out.print(label.toHtmlString() + button.toHtmlString());
	}

}
