// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 
// Source File Name:   ExportBean.java

package com.fusioncharts.exporter.beans;

import java.util.*;

// Referenced classes of package com.fusioncharts.exporter.beans:
//            ChartMetadata

public class ExportBean
{

    public ExportBean()
    {
        exportParameters = null;
        exportParameters = new HashMap();
        exportParameters.put("exportfilename", "FusionCharts");
        exportParameters.put("exportaction", "save");
       // exportParameters.put("exportargetwindow", "_self");
        exportParameters.put("exportformat", "PDF");
    }

    public ChartMetadata getMetadata()
    {
        return metadata;
    }

    public void setMetadata(ChartMetadata metadata)
    {
        this.metadata = metadata;
    }

    public String getStream()
    {
        return stream;
    }

    public void setStream(String stream)
    {
        this.stream = stream;
    }

    public HashMap getExportParameters()
    {
        return new HashMap(exportParameters);
    }

    public Object getExportParameterValue(String key)
    {
        return exportParameters.get(key);
    }

    public void setExportParameters(HashMap exportParameters)
    {
        this.exportParameters = exportParameters;
    }

    public void addExportParameter(String parameterName, Object value)
    {
        exportParameters.put(parameterName.toLowerCase(), value);
    }

    public void addExportParametersFromMap(HashMap moreParameters)
    {
        exportParameters.putAll(moreParameters);
    }

    public String getParametersAndMetadataAsQueryString()
    {
        String queryParams = "";
        queryParams = (new StringBuilder(String.valueOf(queryParams))).append("?width=").append(metadata.getWidth()).toString();
        queryParams = (new StringBuilder(String.valueOf(queryParams))).append("&height=").append(metadata.getHeight()).toString();
        queryParams = (new StringBuilder(String.valueOf(queryParams))).append("&bgcolor=").append(metadata.getBgColor()).toString();
        for(Iterator iter = exportParameters.keySet().iterator(); iter.hasNext();)
        {
            String key = (String)iter.next();
            String value = (String)exportParameters.get(key);
            queryParams = (new StringBuilder(String.valueOf(queryParams))).append("&").append(key).append("=").append(value).toString();
        }

        return queryParams;
    }

    public String getMetadataAsQueryString(String filePath, boolean isError, boolean isHTML)
    {
        String queryParams = "";
        if(isError)
        {
            queryParams = (new StringBuilder(String.valueOf(queryParams))).append(isHTML ? "<BR>" : "&").append("width=0").toString();
            queryParams = (new StringBuilder(String.valueOf(queryParams))).append(isHTML ? "<BR>" : "&").append("height=0").toString();
        } else
        {
            queryParams = (new StringBuilder(String.valueOf(queryParams))).append(isHTML ? "<BR>" : "&").append("width=").append(metadata.getWidth()).toString();
            queryParams = (new StringBuilder(String.valueOf(queryParams))).append(isHTML ? "<BR>" : "&").append("height=").append(metadata.getHeight()).toString();
        }
        queryParams = (new StringBuilder(String.valueOf(queryParams))).append(isHTML ? "<BR>" : "&").append("DOMId=").append(metadata.getDOMId()).toString();
        if(filePath != null)
            queryParams = (new StringBuilder(String.valueOf(queryParams))).append(isHTML ? "<BR>" : "&").append("fileName=").append(filePath).toString();
        return queryParams;
    }

    private ChartMetadata metadata;
    private String stream;
    private HashMap exportParameters;
}