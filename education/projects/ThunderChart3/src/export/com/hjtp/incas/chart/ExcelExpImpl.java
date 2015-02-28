package com.hjtp.incas.chart;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.hjtp.incas.chart.helper.ExportHelperBean;
import com.hjtp.incas.chart.helper.FusionChartsImage;

public class ExcelExpImpl extends FileExp implements IFusionCharts{

	public void export(ExportHelperBean exportHelperBean, OutputStream os) 
	{
		  try 
		  {
			  FusionChartsImage fusionChartsImage=new FusionChartsImage();
			  BufferedImage chart=fusionChartsImage.getChartImage(exportHelperBean);
			  ByteArrayOutputStream byteArrayOut=savePicture(chart);
		      HSSFWorkbook wb = new HSSFWorkbook();
		      HSSFSheet sheet = wb.createSheet("dpChart");
			  //sheet.setColumnWidth(arg0, arg1)
			  //row.setHeight((short)height); 
		      int height=chart.getHeight();
		      int width=chart.getWidth();
			  HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			  int rowL = height/20;
			  int colL = width/70;
			   
			  HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255,(short) 0, 1, (short)colL,rowL);
			
			  int a = wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG);
			  patriarch.createPicture(anchor, a);

			  byteArrayOut.flush();
			  byteArrayOut.close();
			  wb.write(os);
		  }
		  catch (Exception e) 
		  {
		      e.printStackTrace();
		  }
	}

}
