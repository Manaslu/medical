package com.teamsun.framework.ui.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SelectBox extends AbstractComponent
{
	private String name="";

	private String func;

	private String key;

	private Map value;



	public SelectBox(String name, Map value, String key, String func)
	{
		this.name = name;
		this.func = func;
		this.key = key;
		this.value = value;
	}

	public String toHtmlString()
	{
		String htmlString = "";
		String propString = "";
		StringBuffer  optionString = new StringBuffer();
		if (propMap.isEmpty())
		{
			propString += "";
		} else
		{
			Set propSet = propMap.keySet();
			Iterator propIter = propSet.iterator();
			while (propIter.hasNext())
			{
				String propName = propIter.next().toString();
				String propValue = propMap.get(propName).toString();
				propString += " " + propName + "=\"" + propValue + "\" ";
			}
		}
		if (this.func != null)
		{
			htmlString += "<select id=\"" + this.name + "\" name=\"" + name
					+ "\" onChange=\"" + func + "\"" + propString + ">\n";
		} else
		{
			htmlString += "<select id=\"" + this.name + "\" name=\"" + name
					+ "\"" + propString + ">\n";
		}

			Set enteySet = this.value.entrySet();
			Iterator itt = enteySet.iterator();
			while (itt.hasNext())
			{
				Map.Entry entry = (Map.Entry) itt.next();
				String attr_key=entry.getKey().toString();
				String attr_value=entry.getValue().toString();
				if(this.key!=null&&attr_key!=null&&this.key.equals(attr_key))
				{
					optionString.append("<option value=\"").append(attr_key).append("\" selected=\"true\">").append(attr_value).append("</option>\n");
				}
				else{
					optionString.append("<option value=\"").append(attr_key).append("\">").append(attr_value).append("</option>\n");
				}
				
			}
		htmlString+=optionString.toString();
		htmlString += "</select>";
		return htmlString;
	}

	public String getFunc()
	{
		return func;
	}

	public void setFunc(String func)
	{
		this.func = func;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Map getValue()
	{
		return value;
	}

	public void setValue(Map value)
	{
		this.value = value;
	}

	public static void main(String[] args)
	{
		Map s=new HashMap();
		s.put("1", "www");
		s.put("2", "www");
		s.put("3", "www");
		SelectBox sbox=new SelectBox("hello", s, null,null);
		String ssss=sbox.toHtmlString();
		System.out.println(ssss);
	}

}
