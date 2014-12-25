package com.teamsun.framework.ui.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class HtmlElement {

	private String tagName;

	private String content;

	private HashMap prop = new HashMap();

	private String className;

	/**
	 * 
	 * @param tagName
	 *            html标签名
	 * 
	 * @param content
	 *            值
	 * 
	 */
	public HtmlElement(String tagName, String content) {
		super();
		if (tagName == null || tagName.equals("")) {
			// 标签名必填

		}
		this.tagName = tagName.toLowerCase();
		this.content = content;
	}

	/**
	 * 
	 * @param name
	 * @param value
	 * @return -1----添加失败，1----添加成功
	 */
	public int addProp(String name, String value) {
		if (this.prop.keySet().contains(name)) {
			return -1;
		}
		this.prop.put(name, value);
		return 1;
	}

	/**
	 * 
	 * @param name
	 */
	public void deleteProp(String name) {
		// this.props.remove(name);
		this.prop.remove(name);
	}

	/**
	 * 
	 * @return
	 */
	public String tohtmlString() {

		String htmlString = "<" + this.tagName + ">" + content + "</"
				+ this.tagName + ">";
		if (this.className != null && !this.className.equals("")) {
			htmlString = "<" + this.tagName + " class=\"" + this.className
					+ "\">" + content + "</" + this.tagName + ">";
		}
		String propString = " ";
		int pos = htmlString.indexOf(">");
		String subBeforePos = htmlString.substring(0, pos);
		String subAfterPos = htmlString.substring(pos);
		if (!this.prop.isEmpty()) {
			Set propSet = this.prop.keySet();
			Iterator propIter = propSet.iterator();
			while (propIter.hasNext()) {
				String propName = (String) propIter.next();
				String propValue = (String) this.prop.get(propName);
				propString += propName + "=\"" + propValue + "\" ";
			}
			htmlString = subBeforePos + propString + subAfterPos;
		}
		return htmlString.toString();
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTagName() {
		return tagName;
	}

	public static void main(String[] args) {
		HtmlElement test = new HtmlElement("A", "123");
		test.addProp("target", "blank");
		test.addProp("href", "test.jsp?w=3");
		String s = test.tohtmlString();
		System.out.println("thmlstring1 = " + s);
		test.deleteProp("target");
		String s2 = test.tohtmlString();
		System.out.println("thmlstring 2= " + s2);
		test.addProp("name", "hujiong");
		test.setClassName("sw");
		test.setContent("asdfsdafsad");
		System.out.println("thmlstring 3= " + test.tohtmlString());
	}
}
