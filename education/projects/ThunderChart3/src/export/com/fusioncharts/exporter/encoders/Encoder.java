// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   Encoder.java

package com.fusioncharts.exporter.encoders;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import javax.imageio.stream.FileImageOutputStream;

public interface Encoder
{

    public abstract void encode(BufferedImage bufferedimage, OutputStream outputstream)
        throws Throwable;

    public abstract void encode(BufferedImage bufferedimage, OutputStream outputstream, float f)
        throws Throwable;

    public abstract void encode(BufferedImage bufferedimage, OutputStream outputstream, float f, String s)
        throws Throwable;

    public abstract void encode(BufferedImage bufferedimage, FileImageOutputStream fileimageoutputstream)
        throws Throwable;

    public abstract void encode(BufferedImage bufferedimage, FileImageOutputStream fileimageoutputstream, float f)
        throws Throwable;

    public abstract void encode(BufferedImage bufferedimage, FileImageOutputStream fileimageoutputstream, float f, String s)
        throws Throwable;
}