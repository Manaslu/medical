package com.hjtp.incas.chart.helper;
/**
 * <p>解析数据流获得的图片属性</p>
 * @author aingbao-gao
 * * @version version1.0
 * <p>Date:2010-03-03</p>
 */
public class ChartMetadatas 
{

	  private int width = -1;
	  private int height = -1;
	  private String bgColor;
	  private String DOMId = "";

	  public int getWidth()
	  {
	       return this.width;
	  }

	  public void setWidth(int width)
	  {
	       this.width = width;
	  }

	  public int getHeight()
	  {
	    return this.height;
	  }

	  public void setHeight(int height)
	  {
	    this.height = height;
	  }

	  public String getBgColor()
	  {
	    return this.bgColor;
	  }

	  public void setBgColor(String bgColor)
	  {
	    this.bgColor = bgColor;
	  }

	  public String getDOMId()
	  {
	    return this.DOMId;
	  }

	  public void setDOMId(String id)
	  {
	    this.DOMId = id;
	  }
}
