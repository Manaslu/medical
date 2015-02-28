// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine// Available From: http://www.reflections.ath.cx// Decompiler options: packimports(3) // Source File Name:   BasicEncoder.javapackage com.fusioncharts.exporter.encoders;import java.awt.image.BufferedImage;import java.io.IOException;import java.io.OutputStream;import java.util.Iterator;import javax.imageio.*;import javax.imageio.stream.FileImageOutputStream;import javax.imageio.stream.ImageOutputStream;import javax.servlet.jsp.JspWriter;// Referenced classes of package com.fusioncharts.exporter.encoders://            Encoderpublic class BasicEncoder    implements Encoder{    public BasicEncoder()    {        defaultFormat = "JPEG";    }    public void encode(BufferedImage bufferedImage, OutputStream outputStream, float quality)        throws Throwable    {        encode(bufferedImage, outputStream, quality, defaultFormat);    }    public void encode(BufferedImage bufferedImage, OutputStream outputStream)        throws Throwable    {        encode(bufferedImage, outputStream, 1.0F);    }    public void encode(BufferedImage bufferedImage, OutputStream outputStream, float quality, String format)        throws Throwable    {        ImageOutputStream ios = null;        try        {            Iterator writers = ImageIO.getImageWritersByFormatName(format);            ImageWriter writer = (ImageWriter)writers.next();            javax.imageio.ImageWriteParam iwp = writer.getDefaultWriteParam();            ios = ImageIO.createImageOutputStream(outputStream);            writer.setOutput(ios);            writer.write(null, new IIOImage(bufferedImage, null, null), iwp);            ios.close();        }        catch(IllegalArgumentException e)        {            if(ios != null)                ios.close();            throw new Throwable();        }        catch(IOException e)        {            if(ios != null)                ios.close();            throw new Throwable();        }    }    public void encode(BufferedImage bufferedImage, JspWriter out, float quality, String format)        throws Throwable    {        ImageOutputStream ios = null;        try        {            Iterator writers = ImageIO.getImageWritersByFormatName(format);            ImageWriter writer = (ImageWriter)writers.next();            javax.imageio.ImageWriteParam iwp = writer.getDefaultWriteParam();            ios = ImageIO.createImageOutputStream(out);            writer.setOutput(ios);            writer.write(null, new IIOImage(bufferedImage, null, null), iwp);            ios.close();        }        catch(IllegalArgumentException e)        {            if(ios != null)                ios.close();            throw new Throwable();        }        catch(IOException e)        {            if(ios != null)                ios.close();            throw new Throwable();        }    }    public void encode(BufferedImage bufferedImage, FileImageOutputStream fileImageOutputStream, float quality)        throws Throwable    {        encode(bufferedImage, fileImageOutputStream, quality, defaultFormat);    }    public void encode(BufferedImage bufferedImage, FileImageOutputStream fileImageOutputStream)        throws Throwable    {        encode(bufferedImage, fileImageOutputStream, 1.0F);    }    public void encode(BufferedImage bufferedImage, FileImageOutputStream fileImageOutputStream, float quality, String format)        throws Throwable    {        Iterator writers = ImageIO.getImageWritersByFormatName(format);        ImageWriter writer = (ImageWriter)writers.next();        javax.imageio.ImageWriteParam iwp = writer.getDefaultWriteParam();        writer.setOutput(fileImageOutputStream);        writer.write(null, new IIOImage(bufferedImage, null, null), iwp);        fileImageOutputStream.close();    }    String defaultFormat;}