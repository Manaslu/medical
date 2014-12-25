package com.teamsun.framework.ui.component;

import java.util.Iterator;
import java.util.Set;

public class File extends AbstractComponent
{
   private String name ;
   private String value;
   private int    datalength;
   
   /**
    * 构造函数：File()
    * @param name
    * @param value
    * @param datalength
    */
   public File(String name,String value,int datalength)
   {
	   this.name = name;
	   this.value = value;
	   this.datalength = datalength;
   }
	public String toHtmlString() {
	    String htmlString = "";
	    String propString = "";
	    if(propMap.isEmpty())
	    {
	    	htmlString += "<input id=\""+this.name+"\" type=\"file\" name=\""+name+"\" value=\""+value+"\" maxlength=\""+datalength+"\" />";
	    }
	    else
	    {
	    	Set propSet = propMap.keySet();
	    	Iterator propIter = propSet.iterator();
	    	while(propIter.hasNext())
	    	{
	    		String propName=(String)propIter.next();
				String propValue=(String) this.propMap.get(propName);
				propString+=" "+propName+"=\""+propValue+"\"";
	    	}
	    	htmlString +="<input id=\""+this.name+"\" type=\"file\" name=\""+name+"\" value=\""+value+"\" maxlength=\""+datalength+"\""+propString+"/>";
	    	
	    }
		return htmlString;
	}
	
	/**
	 * @return the datalength
	 */
	public int getDatalength()
	{
		return datalength;
	}
	/**
	 * @param datalength the datalength to set
	 */
	public void setDatalength(int datalength)
	{
		this.datalength = datalength;
	}
	public static void main(String[] args)
	{
		File textBox = new File("textbox","textbox",11);
		String htmlString = textBox.toHtmlString();
		System.out.print(htmlString);
	}
	
}


