package com.hjtp.incas.chart;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import com.hjtp.incas.chart.helper.ExportHelperBean;
import com.hjtp.incas.chart.helper.FusionChartsImage;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class PdfExpImpl extends FileExp implements IFusionCharts{
	public void export(ExportHelperBean exportHelperBean, OutputStream os) 
	{

		try
		{
			FusionChartsImage fusionChartsImage=new FusionChartsImage();
			BufferedImage chart=fusionChartsImage.getChartImage(exportHelperBean);
			Document doc = new Document(PageSize.A4);       
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); 
			PdfWriter.getInstance(doc, outputStream);            
			// 打开文档对象            
			doc.open();            
			//加入文字
			//加入文字
			//BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);//设置中文字体
			Font fontChinese = new Font(bfChinese, 12, Font.NORMAL, Color.GREEN);
			doc.add(new Paragraph("PDF图片文件混合导出例子",fontChinese)); 
			byte[]bt=imageToBytes(chart,"jpeg");
			Image img=Image.getInstance(bt); 
			//插入图片
			doc.add(img); 
			
			//设置 Table 表格 
			Table aTable = new Table(3); 
			int width[] = {25,25,50}; 
			aTable.setWidths(width);//设置每列所占比例 
			aTable.setWidth(90); // 占页面宽度 90% 

			aTable.setAlignment(Element.ALIGN_CENTER);//居中显示 
			aTable.setAlignment(Element.ALIGN_MIDDLE);//纵向居中显示 
			aTable.setAutoFillEmptyCells(true); //自动填满 
			aTable.setBorderWidth(1); //边框宽度 
			aTable.setBorderColor(new Color(0, 125, 255)); //边框颜色 
			aTable.setPadding(2);//衬距，看效果就知道什么意思了 
			aTable.setSpacing(3);//即单元格之间的间距 
			aTable.setBorder(2);//边框 

			//设置表头 
			/** 
			* cell.setHeader(true);是将该单元格作为表头信息显示； 
			* cell.setColspan(3);指定了该单元格占3列； 
			* 为表格添加表头信息时，要注意的是一旦表头信息添加完了之后， 
			* 必须调用 endHeaders()方法，否则当表格跨页后，表头信息不会再显示 
			*/ 
			Cell haderCell = new Cell("表格表头"); 
			haderCell.setHeader(true); 
			haderCell.setColspan(3); 
			aTable.addCell(haderCell); 
			aTable.endHeaders(); 

			Cell cell = new Cell(new Phrase("这是一个测试的 3*3 Table 数据", fontChinese )); 
			cell.setVerticalAlignment(Element.ALIGN_TOP); 
			cell.setBorderColor(new Color(255, 0, 0)); 
			cell.setRowspan(2); 
			aTable.addCell(cell); 

			aTable.addCell(new Cell("#1")); 
			aTable.addCell(new Cell("#2")); 
			aTable.addCell(new Cell("#3")); 
			aTable.addCell(new Cell("#4")); 
			Cell cell3 = new Cell(new Phrase("一行三列数据", fontChinese )); 
			cell3.setColspan(3); 
			cell3.setVerticalAlignment(Element.ALIGN_CENTER); 
			aTable.addCell(cell3); 

			doc.add(aTable); 
           
			// 关闭文档对象，释放资源            
			doc.close();      
			outputStream.writeTo(os);
			outputStream.flush();
			outputStream.close();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
