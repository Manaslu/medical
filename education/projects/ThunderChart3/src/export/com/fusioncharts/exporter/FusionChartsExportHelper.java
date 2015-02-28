// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   FusionChartsExportHelper.java

package com.fusioncharts.exporter;

import com.fusioncharts.exporter.beans.ChartMetadata;
import com.fusioncharts.exporter.beans.ExportBean;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.servlet.http.HttpServletRequest;

public class FusionChartsExportHelper
{
	private static final Log logger = LogFactory.getLog(FusionChartsExportHelper.class);

    public FusionChartsExportHelper()
    {
    }

    public static ExportBean parseExportRequestStream(HttpServletRequest exportRequestStream)
    {
        ExportBean exportBean = new ExportBean();
        String stream = exportRequestStream.getParameter("stream");
        String parameters = exportRequestStream.getParameter("parameters");
        ChartMetadata metadata = new ChartMetadata();
        String strWidth = exportRequestStream.getParameter("meta_width");
        metadata.setWidth(Integer.parseInt(strWidth));
        String strHeight = exportRequestStream.getParameter("meta_height");
        metadata.setHeight(Integer.parseInt(strHeight));
        String bgColor = exportRequestStream.getParameter("meta_bgColor");
        String DOMId = exportRequestStream.getParameter("meta_DOMId");
        metadata.setDOMId(DOMId);
        metadata.setBgColor(bgColor);
        exportBean.setMetadata(metadata);
        exportBean.setStream(stream);
        HashMap exportParamsFromRequest = bang(parameters);
        exportBean.addExportParametersFromMap(exportParamsFromRequest);
        return exportBean;
    }

    public static String getExporterFilePath(String strFormat)
    {
        String exporterSuffix = (String)handlerAssociationsMap.get(strFormat.toUpperCase()) == null ? strFormat : (String)handlerAssociationsMap.get(strFormat.toUpperCase());
        String path = (new StringBuilder(String.valueOf(RESOURCEPATH))).append(EXPORTHANDLER).append(exporterSuffix.toUpperCase()).append(".jsp").toString();
        System.out.println("++++++++"+path);
        return path;
    }

    public static HashMap bang(String strParams)
    {
        HashMap params = new HashMap();
        for(StringTokenizer stPipe = new StringTokenizer(strParams, "|"); stPipe.hasMoreTokens();)
        {
            String keyValue = stPipe.nextToken();
            String keyValueArr[] = keyValue.split("=");
            if(keyValueArr.length > 1)
                params.put(keyValueArr[0].toLowerCase(), keyValueArr[1]);
        }

        return params;
    }

    public static HashMap getMimeTypes()
    {
        return mimeTypes;
    }

    public static String getMimeTypeFor(String format)
    {
        return (String)mimeTypes.get(format);
    }

    public static String getExtensionFor(String format)
    {
        return (String)extensions.get(format);
    }

    public static String getUniqueFileName(String filePath, String extension)
    {
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString();
        String uniqueFileName = (new StringBuilder(String.valueOf(filePath))).append(".").append(extension).toString();
        do
        {
            uniqueFileName = filePath;
            if(!FILESUFFIXFORMAT.equalsIgnoreCase("TIMESTAMP"))
            {
                uniqueFileName = (new StringBuilder(String.valueOf(uniqueFileName))).append(uid).append("_").append(Math.random()).toString();
            } else
            {
                SimpleDateFormat sdf = new SimpleDateFormat("dMyHms");
                String date = sdf.format(Calendar.getInstance().getTime());
                uniqueFileName = (new StringBuilder(String.valueOf(uniqueFileName))).append(uid).append("_").append(date).append("_").append(Calendar.getInstance().getTimeInMillis()).toString();
            }
            uniqueFileName = (new StringBuilder(String.valueOf(uniqueFileName))).append(".").append(extension).toString();
        } while((new File(uniqueFileName)).exists());
        return uniqueFileName;
    }

    private static HashMap mimeTypes;
    private static HashMap extensions;
    private static HashMap handlerAssociationsMap;
    public static String EXPORTHANDLER = "FCExporter_";
    public static String RESOURCEPATH = "Resources/";
    public static String SAVEPATH = "./";
    public static String HTTP_URI = "http://yourdomain.com/";
    public static String TMPSAVEPATH = "";
    public static boolean OVERWRITEFILE = false;
    public static boolean INTELLIGENTFILENAMING = true;
    public static String FILESUFFIXFORMAT = "TIMESTAMP";

    static 
    {
        mimeTypes = new HashMap();
        extensions = new HashMap();
        handlerAssociationsMap = new HashMap();
        handlerAssociationsMap.put("PDF", "PDF");
        handlerAssociationsMap.put("JPEG", "IMG");
        handlerAssociationsMap.put("JPG", "IMG");
        handlerAssociationsMap.put("PNG", "IMG");
        handlerAssociationsMap.put("GIF", "IMG");
        mimeTypes.put("jpg", "image/jpeg");
        mimeTypes.put("jpeg", "image/jpeg");
        mimeTypes.put("png", "image/png");
        mimeTypes.put("gif", "image/gif");
        mimeTypes.put("pdf", "application/pdf");
        extensions.put("jpeg", "jpg");
        extensions.put("jpg", "jpg");
        extensions.put("png", "png");
        extensions.put("gif", "gif");
        extensions.put("pdf", "pdf");
        Properties props = new Properties();
        try
        {
            props.load(FusionChartsExportHelper.class.getResourceAsStream("fusioncharts_export.properties"));
            EXPORTHANDLER = props.getProperty("EXPORTHANDLER", "FCExporter_");
            RESOURCEPATH = props.getProperty("RESOURCEPATH", (new StringBuilder("Resources")).append(File.separator).toString());
            SAVEPATH = props.getProperty("SAVEPATH", "./");
            HTTP_URI = props.getProperty("HTTP_URI", "http://yourdomain.com/");
            TMPSAVEPATH = props.getProperty("TMPSAVEPATH", "");
            String OVERWRITEFILESTR = props.getProperty("OVERWRITEFILE", "false");
            OVERWRITEFILE = (new Boolean(OVERWRITEFILESTR)).booleanValue();
            String INTELLIGENTFILENAMINGSTR = props.getProperty("INTELLIGENTFILENAMING", "true");
            INTELLIGENTFILENAMING = (new Boolean(INTELLIGENTFILENAMINGSTR)).booleanValue();
            FILESUFFIXFORMAT = props.getProperty("FILESUFFIXFORMAT", "TIMESTAMP");
        }
        catch(NullPointerException e)
        {
            logger.info("NullPointer: Properties file not FOUND");
        }
        catch(FileNotFoundException e)
        {
            logger.info("Properties file not FOUND");
        }
        catch(IOException e)
        {
            logger.info("IOException: Properties file not FOUND");
        }
    }
}