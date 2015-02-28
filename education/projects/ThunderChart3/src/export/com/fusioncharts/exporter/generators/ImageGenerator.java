// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   ImageGenerator.java

package com.fusioncharts.exporter.generators;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.teamsun.export.util.ChartMetaData;

public class ImageGenerator
{
	private static final Log logger = LogFactory.getLog(ImageGenerator.class);
    public ImageGenerator()
    {
    }

    public static BufferedImage getChartImage(ChartMetaData metadata)
    {
        logger.info("Creating the Chart image");
        int width = 0;
        int height = 0;
        String bgcolor = "";
        width = metadata.getWidth();
        height = metadata.getHeight();
        if(width == 0 || height == 0)
            logger.info("Image width/height not provided.");
        bgcolor = metadata.getBgColor();
        if(bgcolor == null || bgcolor == "" || bgcolor == null)
            bgcolor = "FFFFFF";
        Color bgColor = new Color(Integer.parseInt(bgcolor, 16));
        if(metadata.getStream() == null)
            logger.info("Image Data not supplied.");
        BufferedImage chart = null;
        try
        {
            String rows[] = new String[height + 1];
            rows = metadata.getStream().split(";");
            chart = new BufferedImage(width, height, 5);
            Graphics gr = chart.createGraphics();
            gr.setColor(bgColor);
            gr.fillRect(0, 0, width, height);
            int ri = 0;
            for(int i = 0; i < rows.length; i++)
            {
                String pixels[] = rows[i].split(",");
                ri = 0;
                for(int j = 0; j < pixels.length; j++)
                {
                    String clrs[] = pixels[j].split("_");
                    String c = clrs[0];
                    int r = Integer.parseInt(clrs[1]);
                    if(c != null && c.length() > 0 && c != "")
                    {
                        if(c.length() < 6)
                        {
                            StringBuffer str = new StringBuffer(c);
                            for(int p = c.length() + 1; p <= 6; p++)
                                str.insert(0, "0");

                            c = str.toString();
                        }
                        for(int k = 1; k <= r; k++)
                        {
                            gr.setColor(new Color(Integer.parseInt(c, 16)));
                            gr.fillRect(ri, i, 1, 1);
                            ri++;
                        }

                    } else
                    {
                        ri += r;
                    }
                }

            }

            logger.info("Image created successfully");
        }
        catch(Exception e)
        {
            logger.info((new StringBuilder("Image data is not in proper format:")).append(e.toString()).toString());
        }
        return chart;
    }

  
}