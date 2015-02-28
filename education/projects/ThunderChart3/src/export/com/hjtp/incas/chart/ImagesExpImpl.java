package com.hjtp.incas.chart;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.hjtp.incas.chart.helper.ExportHelperBean;
import com.hjtp.incas.chart.helper.FusionChartsImage;

public class ImagesExpImpl extends FileExp implements IFusionCharts{

	public void export(ExportHelperBean exportHelperBean ,OutputStream os) 
	{
		  try 
		  {
			  FusionChartsImage fusionChartsImage=new FusionChartsImage();
			  BufferedImage chart=fusionChartsImage.getChartImage(exportHelperBean);
			  ImageIO.write(chart, "jpeg", os);
		  }
		  catch (Exception e) 
		  {
		      e.printStackTrace();
		  }
	}

}
