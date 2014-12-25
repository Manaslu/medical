package com.teamsun.framework.ui.component;

public class HiddenField extends AbstractComponent
{
	private String name;
	
	private String value;
	
	public HiddenField(String name,String value)
	{
		this.name=name;
		this.value=value;
	}
	
	public String toHtmlString()
	{
		String str="";
		//不能添加属性
		this.propMap.clear();
		str+="<input name=\""+this.name+"\" type=\"hidden\" value=\""+this.value+ "\" id=\""+this.name+"\"/>";
		return str;
	}
	
	public static void main(String[] args)
	{
		String s=new HiddenField("sww","saa").toHtmlString();
		System.out.print(s);
	}
}
