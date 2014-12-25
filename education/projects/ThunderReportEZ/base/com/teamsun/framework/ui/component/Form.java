package com.teamsun.framework.ui.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Form extends AbstractComponent {
	private String action;

	private String method;

	private String name;

	private String content = "";

	private List component;

	/**
	 * 构造函数：Form()
	 * 
	 * @param action
	 *            表单动作 "URL"
	 * @param method
	 *            表单方法 "get" or "post"
	 * @param name
	 *            表单名字
	 * @param content
	 *            表单包含的内容
	 * 
	 */
	public Form(String action, String method, String name, String content) {
		this.action = action;
		this.method = method;
		this.name = name;
		this.content = content;
		this.component = new ArrayList();
	}

	public Form(String action, String name, String method) {
		this.action = action;
		this.method = method;
		this.name = name;
		this.component = new ArrayList();
	}

	public String toHtmlString() {
		String str = "";
		String propString = "";
		String elementString = "";
		if (this.component.size() > 0) {
			Iterator it = this.component.iterator();
			StringBuffer sb = new StringBuffer();
			while (it.hasNext()) {
				AbstractComponent component = (AbstractComponent) it.next();
				sb.append(component.toHtmlString());
			}
			elementString += sb.toString();
		}
		String contentStr = elementString + this.content;
		str += "<form action=\"" + this.action + "\" method=\"" + this.method
				+ "\" id=\"" + this.name + "\" name=\"" + this.name;
		if (this.propMap.size() == 0) {
			str += "\">" + contentStr + "</form>";
		} else {
			Set keys = this.propMap.keySet();
			Iterator propIter = keys.iterator();
			while (propIter.hasNext()) {
				String propName = (String) propIter.next();
				String propValue = (String) this.propMap.get(propName);
				propString += " " + propName + "=\"" + propValue + "\"";
			}
			str += propString + ">" + contentStr + "</form>";
		}
		return str;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * addComponent:在form中加各种元素
	 * 
	 * @param component
	 */
	public void addComponent(AbstractComponent component) {
		this.component.add(component);
	}

	public static void main(String args[]) {
		Form a = new Form("q", "q", "s", "s");
		a.addComponent(new HiddenField("aa", "aa"));
		a.addComponent(new HiddenField("bb", "bb"));
		int s = a.component.size();
		System.out.print(a.toHtmlString());

	}
}
