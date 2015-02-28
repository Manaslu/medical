// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   ChartMetadata.java

package com.fusioncharts.exporter.beans;


public class ChartMetadata
{

    public ChartMetadata()
    {
        width = -1;
        height = -1;
        DOMId = "";
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getBgColor()
    {
        return bgColor;
    }

    public void setBgColor(String bgColor)
    {
        this.bgColor = bgColor;
    }

    public String getDOMId()
    {
        return DOMId;
    }

    public void setDOMId(String id)
    {
        DOMId = id;
    }

    private int width;
    private int height;
    private String bgColor;
    private String DOMId;
}