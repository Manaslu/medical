package com.hjtp.incas.chart.helper;

import java.util.HashMap;
import java.util.Iterator;
/**
 * <p>����������õ���������</p>
 * @author qingbao-gao
 * @version version1.0
 * <p>Date:2010-03-03</p>
 */
public class ExportHelperBean
{
	  private ChartMetadatas chartMetadatas;
	  private String stream;
	  private HashMap<String, Object> exportParameters = null;

	  public ExportHelperBean()
	  {
	    this.exportParameters = new HashMap<String, Object>();

	    this.exportParameters.put("exportfilename", "FusionCharts");
	    this.exportParameters.put("exportaction", "save");
	    //this.exportParameters.put("exportargetwindow", "_self");
	    this.exportParameters.put("exportformat", "XLS");
	  }

	  public ChartMetadatas getMetadatas()
	  {
	    return this.chartMetadatas;
	  }

	  public void setMetadatas(ChartMetadatas metadatas)
	  {
	    this.chartMetadatas = metadatas;
	  }

	  public String getStream()
	  {
	    return this.stream;
	  }

	  public void setStream(String stream)
	  {
	    this.stream = stream;
	  }

	  public HashMap<String, Object> getExportParameters()
	  {
	    return new HashMap<String, Object>(this.exportParameters);
	  }

	  public Object getExportParameterValue(String key)
	  {
	    return this.exportParameters.get(key);
	  }

	  public void setExportParameters(HashMap<String, Object> exportParameters)
	  {
	    this.exportParameters = exportParameters;
	  }

	  public void addExportParameter(String parameterName, Object value)
	  {
	    this.exportParameters.put(parameterName.toLowerCase(), value);
	  }

	  public void addExportParametersFromMap(HashMap<String, String> moreParameters)
	  {
	    this.exportParameters.putAll(moreParameters);
	  }

	  public String getParametersAndMetadataAsQueryString()
	  {
	    String queryParams = "";
	    queryParams = queryParams + "?width=" + this.chartMetadatas.getWidth();
	    queryParams = queryParams + "&height=" + this.chartMetadatas.getHeight();
	    queryParams = queryParams + "&bgcolor=" + this.chartMetadatas.getBgColor();

	    Iterator iter = this.exportParameters.keySet().iterator();

	    while (iter.hasNext()) {
	      String key = (String)iter.next();
	      String value = (String)this.exportParameters.get(key);
	      queryParams = queryParams + "&" + key + "=" + value;
	    }

	    return queryParams;
	  }

	  public String getMetadataAsQueryString(String filePath, boolean isError, boolean isHTML)
	  {
	    String queryParams = "";
	    if (isError) {
	      queryParams = queryParams + ((isHTML) ? "<BR>" : "&") + "width=0";
	      queryParams = queryParams + ((isHTML) ? "<BR>" : "&") + "height=0";
	    }
	    else {
	      queryParams = queryParams + ((isHTML) ? "<BR>" : "&") + "width=" + this.chartMetadatas.getWidth();
	      queryParams = queryParams + ((isHTML) ? "<BR>" : "&") + "height=" + this.chartMetadatas.getHeight();
	    }

	    queryParams = queryParams + ((isHTML) ? "<BR>" : "&") + "DOMId=" + this.chartMetadatas.getDOMId();
	    if (filePath != null) {
	      queryParams = queryParams + ((isHTML) ? "<BR>" : "&") + "fileName=" + filePath;
	    }

	    return queryParams;
	  }
}
