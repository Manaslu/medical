package com.hjtp.incas.chart.helper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * <p>����flash�����������ͼƬ����</p>
 * @author qingbao-gao
 * @version version1.0
 * <p>Date:2010-03-03 AM 09:58</p>
 */
public class FusionChartsImage 
{
	public BufferedImage getChartImage(ExportHelperBean exportHelperBean)
	{
		BufferedImage chart = null;
	    int width = 0;
	    int height = 0;
	    String bgcolor = "";
	    ChartMetadatas chartMetadatas=new ChartMetadatas();
	    chartMetadatas=exportHelperBean.getMetadatas();
	    width = chartMetadatas.getWidth();
	    height =chartMetadatas.getHeight();

	    if ((width == 0) || (height == 0))
	    {
	    }

	    bgcolor = chartMetadatas.getBgColor();
	    if ((bgcolor == null) || (bgcolor == "") || (bgcolor == null)) {
	         bgcolor = "FFFFFF";
	    }

	    Color bgColor = new Color(Integer.parseInt(bgcolor, 16));
	    String data=exportHelperBean.getStream();
	    if (data == null)
	    {
	    }

	    
	    try
	    {
	        String[] rows = new String[height + 1];
	        rows = data.split(";");

	        chart = new BufferedImage(width, height, 5);
	        
	        Graphics gr = chart.createGraphics();
	        gr.setColor(bgColor);
	        gr.fillRect(0, 0, width, height);

	        int index = 0;
	        for (int i = 0; i < rows.length; ++i)
	        {
		        String[] pixels = rows[i].split(",");
		        index = 0;
		        for (int j = 0; j < pixels.length; ++j)
		        {
		            String[] clrs = pixels[j].split("_");
		            String c = clrs[0];
		            int r = Integer.parseInt(clrs[1]);
	
		            if ((c != null) && (c.length() > 0) && (c != "")) 
		            {
			            if (c.length() < 6)
			            {
			                StringBuffer str = new StringBuffer(c);
			                for (int p = c.length() + 1; p <= 6; ++p) 
			                {
			                    str.insert(0, "0");
			                }
			                c = str.toString();
			            }
			            for (int k = 1; k <= r; ++k)
			            {
			                gr.setColor(new Color(Integer.parseInt(c, 16)));
			                gr.fillRect(index, i, 1, 1);
			                ++index;
			            }
		            }
		            else 
		            {
		                index += r;
		            }
		        }
	        }

	    }
	    catch (Exception e) 
	    {
	    }

	    return chart;
	}
}
