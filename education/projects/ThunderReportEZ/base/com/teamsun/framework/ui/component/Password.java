package com.teamsun.framework.ui.component;

import java.util.Iterator;
import java.util.Set;

public class Password extends AbstractComponent
{
	private String name;

	private String value;

	private int datalength;

	/**
	 * 构造函数：Password()
	 * @param name       密码框的名
	 * @param value      密码值
	 * @param datalength 密码最大长度
	 */
	public Password(String name, String value, int datalength)
	{
		this.name = name;
		this.value = value;
		this.datalength = datalength;
	}

	public String toHtmlString()
	{
		String htmlString = "";
		String propString = "";
		if (propMap.isEmpty())
		{
			htmlString += "<input id=\"" + this.name
					+ "\" type=\"password\" name=\"" + name + "\" value=\""
					+ value + "\" maxlength=\"" + datalength + "\" />";
		} else
		{
			Set propSet = propMap.keySet();
			Iterator propIter = propSet.iterator();
			while (propIter.hasNext())
			{
				String propName = (String) propIter.next();
				String propValue = (String) this.propMap.get(propName);
				propString += " " + propName + "=\"" + propValue + "\"";
			}
			htmlString += "<input id=\"" + this.name
					+ "\" type=\"password\" name=\"" + name + "\" value=\""
					+ value + "\" maxlength=\"" + datalength + "\""
					+ propString + "/>";

		}
		return htmlString;
	}

	public static void main(String[] args)
	{
		Password textBox = new Password("textbox", "textbox", 11);
		String htmlString = textBox.toHtmlString();
		System.out.print(htmlString);
	}

}
