package com.teamsun.framework.ui.component;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;

public class TextArea extends AbstractComponent {
	private int rows;
	private String name;
	private String value;
	private int  cols;
	
	/**
	 * 构造函数：TextArea() 新建一个大文本域
	 * @param width   宽度
	 * @param rows    行数
	 * @param name    名字
	 * @param value   文本域内容
	 */
	public TextArea(int cols,int rows,String name,String value)
	{
		this.cols=cols;
		this.rows  = rows;
		this.value = value;
		this.name  = name;
	}
	
	public String toHtmlString() {
		String htmlString = "";
		String propString = "";
		if(propMap.isEmpty())
		{
			htmlString +="<textarea id=\""+this.name+"\" style=\"width:100%\" rows=\""+rows+"\"  name=\""+name+"\">"+StringEscapeUtils.escapeHtml(this.value)+"</textarea>";
		}
		else
		{
			Set propSet       = propMap.keySet();
			Iterator propIter = propSet.iterator();
			while(propIter.hasNext())
			{
				String propName  = (String)propIter.next();
				String propValue = (String) this.propMap.get(propName);
				propString+=" "+propName+"=\""+propValue+"\"";
			}
			htmlString += "<textarea id=\""+this.name+"\" cols=\""+cols+"\" rows=\""+rows+"\" name=\""+name+"\""+propString+">"+StringEscapeUtils.escapeHtml(this.value)+"</textarea>";
		}
		return htmlString;
	}
	public static void main(String[] args)
	{
		TextArea textarea = new TextArea(4,4,"text","text");
		String htmlString = textarea.toHtmlString();
		System.out.print(htmlString);
	}
}
