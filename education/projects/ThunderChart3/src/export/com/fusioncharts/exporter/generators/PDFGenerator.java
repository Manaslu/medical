// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   PDFGenerator.java

package com.fusioncharts.exporter.generators;

import com.fusioncharts.exporter.beans.ChartMetadata;
import com.fusioncharts.exporter.beans.ExportBean;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.zip.Deflater;

public class PDFGenerator
{
	private static final Log logger = LogFactory.getLog(PDFGenerator.class);

    public PDFGenerator(String data, ChartMetadata metadata)
    {
    	 
        pageIndex = 0;
        pagesData = new ArrayList();
        setBitmapData(data, metadata);
    }

    public void setBitmapData(String imageData_FCFormat, ChartMetadata metadata)
    {
        ExportBean exportBean = new ExportBean();
        exportBean.setStream(imageData_FCFormat);
        exportBean.setMetadata(metadata);
        pagesData.add(pageIndex, exportBean);
        pageIndex++;
    }

    private byte[] addImageToPDF(int id, boolean isCompressed)
    {
        byte imagePDFBytes[] = (byte[])null;
        ByteArrayOutputStream imagePDFBAOS = new ByteArrayOutputStream();
        try
        {
            int imgObjNo = 6 + id * 3;
            byte bitmapImage[] = getBitmapData24(id);
            byte imgBinary[] = isCompressed ? compress(bitmapImage) : bitmapImage;
            int length = imgBinary.length;
            ChartMetadata metadata = ((ExportBean)pagesData.get(id)).getMetadata();
            String imgObj = (new StringBuilder(String.valueOf(imgObjNo))).append(" 0 obj\n<<\n/Subtype /Image /ColorSpace /DeviceRGB /BitsPerComponent 8 /HDPI 72 /VDPI 72 ").append(isCompressed ? "/Filter /FlateDecode " : "").append("/Width ").append(metadata.getWidth()).append(" /Height ").append(metadata.getHeight()).append(" /Length ").append(length).append(" >>\nstream\n").toString();
            String imgObj2 = "endstream\nendobj\n";
            imagePDFBAOS.write(imgObj.getBytes());
            imagePDFBAOS.write(imgBinary);
            imagePDFBAOS.write(imgObj2.getBytes());
            imagePDFBytes = imagePDFBAOS.toByteArray();
            imagePDFBAOS.close();
        }
        catch(IOException e)
        {
            logger.info((new StringBuilder("Exception while parsing image data for PDF: ")).append(e.toString()).toString());
        }
        return imagePDFBytes;
    }

    private byte[] getBitmapData24(int id)
    {
        byte imageData[] = (byte[])null;
        ByteArrayOutputStream imageData24OS = new ByteArrayOutputStream();
        StringTokenizer rows = new StringTokenizer(((ExportBean)pagesData.get(id)).getStream(), ";");
        logger.info("Parsing image data");
        StringTokenizer pixels = null;
        String pixelData[] = (String[])null;
        String color = null;
        int repeat = 0;
        String bgColor = ((ExportBean)pagesData.get(id)).getMetadata().getBgColor();
        if(bgColor == null || bgColor.equals(""))
        {
            ((ExportBean)pagesData.get(id)).getMetadata().setBgColor("FFFFFF");
            bgColor = "FFFFFF";
        }
        while(rows.hasMoreElements()) 
            for(pixels = new StringTokenizer((String)rows.nextElement(), ","); pixels.hasMoreElements();)
            {
                pixelData = ((String)pixels.nextElement()).split("_");
                color = pixelData[0];
                repeat = Integer.parseInt(pixelData[1]);
                if(color == null || color.equals(""))
                    color = bgColor;
                if(color.length() < 6)
                    color = (new StringBuilder(String.valueOf("000000".substring(0, 6 - color.length())))).append(color).toString();
                byte rgbBytes[] = hexToBytes(color);
                byte repeatedBytes[] = repeatBytes(rgbBytes, repeat);
                try
                {
                    imageData24OS.write(repeatedBytes);
                    imageData24OS.flush();
                }
                catch(IOException e)
                {
                    logger.info((new StringBuilder("Exception while writing image data for PDF: ")).append(e.toString()).toString());
                }
            }

        imageData = imageData24OS.toByteArray();
        try
        {
            imageData24OS.close();
        }
        catch(IOException e)
        {
            logger.info((new StringBuilder("Exception while closing stream for PDF: ")).append(e.toString()).toString());
        }
        logger.info("Image data parsed successfully");
        return imageData;
    }

    public byte[] getPDFObjects(boolean isCompressed)
    {
        logger.info("Creating PDF specific objects.");
        ByteArrayOutputStream PDFBytesOS = new ByteArrayOutputStream();
        byte pdfBytes[] = (byte[])null;
        String strTmpObj = "";
        ArrayList xRefList = new ArrayList();
        xRefList.add(0, "xref\n0 ");
        xRefList.add(1, "0000000000 65535 f \n");
        strTmpObj = "%PDF-1.3\n%{FC}\n";
        try
        {
            PDFBytesOS.write(strTmpObj.getBytes());
            strTmpObj = "1 0 obj<<\n/Author (FusionCharts)\n/Title (FusionCharts)\n/Creator (FusionCharts)\n>>\nendobj\n";
            xRefList.add(calculateXPos((new StringBuilder()).append(PDFBytesOS.size()).toString()));
            PDFBytesOS.write(strTmpObj.getBytes());
            strTmpObj = "2 0 obj\n<< /Type /Catalog /Pages 3 0 R >>\nendobj\n";
            xRefList.add(calculateXPos((new StringBuilder()).append(PDFBytesOS.size()).toString()));
            PDFBytesOS.write(strTmpObj.getBytes());
            strTmpObj = "3 0 obj\n<<  /Type /Pages /Kids [";
            for(int i = 0; i < pageIndex; i++)
                strTmpObj = (new StringBuilder(String.valueOf(strTmpObj))).append((i + 1) * 3 + 1).append(" 0 R\n").toString();

            strTmpObj = (new StringBuilder(String.valueOf(strTmpObj))).append("] /Count ").append(pageIndex).append(" >>\nendobj\n").toString();
            xRefList.add(calculateXPos((new StringBuilder()).append(PDFBytesOS.size()).toString()));
            PDFBytesOS.write(strTmpObj.getBytes());
            ChartMetadata metadata = null;
            logger.info("Gathering data for  each page");
            for(int itr = 0; itr < pageIndex; itr++)
            {
                metadata = ((ExportBean)pagesData.get(itr)).getMetadata();
                int iWidth = metadata.getWidth();
                int iHeight = metadata.getHeight();
                strTmpObj = (new StringBuilder(String.valueOf((itr + 2) * 3 - 2))).append(" 0 obj\n<<\n/Type /Page /Parent 3 0 R \n/MediaBox [ 0 0 ").append(iWidth).append(" ").append(iHeight).append(" ]\n/Resources <<\n/ProcSet [ /PDF ]\n/XObject <</R").append(itr + 1).append(" ").append((itr + 2) * 3).append(" 0 R>>\n>>\n/Contents [ ").append((itr + 2) * 3 - 1).append(" 0 R ]\n>>\nendobj\n").toString();
                xRefList.add(calculateXPos((new StringBuilder()).append(PDFBytesOS.size()).toString()));
                PDFBytesOS.write(strTmpObj.getBytes());
                xRefList.add(calculateXPos((new StringBuilder()).append(PDFBytesOS.size()).toString()));
                PDFBytesOS.write(getXObjResource(itr).getBytes());
                byte imgPDFBytes[] = addImageToPDF(itr, isCompressed);
                xRefList.add(calculateXPos((new StringBuilder()).append(PDFBytesOS.size()).toString()));
                PDFBytesOS.write(imgPDFBytes);
            }

            String xRef0 = (new StringBuilder(String.valueOf((String)xRefList.get(0)))).append(xRefList.size() - 1).append("\n").toString();
            xRefList.set(0, xRef0);
            String trailer = getTrailer(PDFBytesOS.size(), xRefList.size() - 1);
            PDFBytesOS.write(arrayToString(xRefList, "").getBytes());
            PDFBytesOS.write(trailer.getBytes());
            String EOF = "%%EOF\n";
            PDFBytesOS.write(EOF.getBytes());
            pdfBytes = PDFBytesOS.toByteArray();
            logger.info("PDF data created successfully");
        }
        catch(IOException e)
        {
            logger.info((new StringBuilder("Exception while writing PDF data: ")).append(e.toString()).toString());
        }
        return pdfBytes;
    }

    private String arrayToString(ArrayList a, String separator)
    {
        StringBuffer result = new StringBuffer();
        if(a.size() > 0)
        {
            result.append((String)a.get(0));
            for(int i = 1; i < a.size(); i++)
            {
                result.append(separator);
                result.append((String)a.get(i));
            }

        }
        return result.toString();
    }

    private String getXObjResource(int itr)
    {
        ChartMetadata metadata = ((ExportBean)pagesData.get(itr)).getMetadata();
        return (new StringBuilder(String.valueOf((itr + 2) * 3 - 1))).append(" 0 obj\n<< /Length ").append(24 + (new StringBuilder()).append(metadata.getWidth()).append(metadata.getHeight()).toString().length()).append(" >>\nstream\nq\n").append(metadata.getWidth()).append(" 0 0 ").append(metadata.getHeight()).append(" 0 0 cm\n/R").append(itr + 1).append(" Do\nQ\nendstream\nendobj\n").toString();
    }

    private String calculateXPos(String posn)
    {
        String paddedStr = (new StringBuilder(String.valueOf("0000000000".substring(0, 10 - posn.length())))).append(posn).toString();
        return (new StringBuilder(String.valueOf(paddedStr))).append(" 00000 n \n").toString();
    }

    private String getTrailer(int xrefpos, int numxref)
    {
        return (new StringBuilder("trailer\n<<\n/Size ")).append(numxref).append("\n/Root 2 0 R\n/Info 1 0 R\n>>\nstartxref\n").append(xrefpos).append("\n").toString();
    }

    private byte[] compress(byte data[])
    {
        logger.info("Compressing the image data");
        byte compressedData[] = (byte[])null;
        Deflater compressor = new Deflater();
        compressor.setLevel(9);
        compressor.setInput(data);
        compressor.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        byte buf[] = new byte[1024];
        int count;
        for(; !compressor.finished(); bos.write(buf, 0, count))
            count = compressor.deflate(buf);

        try
        {
            bos.close();
        }
        catch(IOException ioexception) { }
        compressedData = bos.toByteArray();
        logger.info("Image data compressed");
        return compressedData;
    }

    private byte[] hexToBytes(String hex)
    {
        return hexToBytes(hex.toCharArray());
    }

    private byte[] hexToBytes(char hex[])
    {
        int length = 3;
        byte raw[] = new byte[length];
        for(int i = 0; i < length; i++)
        {
            int high = Character.digit(hex[i * 2], 16);
            int low = Character.digit(hex[i * 2 + 1], 16);
            int value = high << 4 | low;
            raw[i] = (byte)((value & 0xff) > 127 ? value - 256 : value);
        }

        return raw;
    }

    private byte[] repeatBytes(byte bytes[], int repeat)
    {
        byte repeatedBytes[] = new byte[bytes.length * repeat];
        int counter = 0;
        for(int i = 0; i < repeat; i++)
        {
            for(int j = 0; j < bytes.length; j++)
                repeatedBytes[counter++] = bytes[j];

        }

        return repeatedBytes;
    }

   
    int pageIndex;
    ArrayList pagesData;
}