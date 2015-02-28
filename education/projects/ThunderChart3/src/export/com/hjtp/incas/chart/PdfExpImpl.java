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
			// ���ĵ�����            
			doc.open();            
			//��������
			//��������
			//BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
			BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);//������������
			Font fontChinese = new Font(bfChinese, 12, Font.NORMAL, Color.GREEN);
			doc.add(new Paragraph("PDFͼƬ�ļ���ϵ�������",fontChinese)); 
			byte[]bt=imageToBytes(chart,"jpeg");
			Image img=Image.getInstance(bt); 
			//����ͼƬ
			doc.add(img); 
			
			//���� Table ��� 
			Table aTable = new Table(3); 
			int width[] = {25,25,50}; 
			aTable.setWidths(width);//����ÿ����ռ���� 
			aTable.setWidth(90); // ռҳ���� 90% 

			aTable.setAlignment(Element.ALIGN_CENTER);//������ʾ 
			aTable.setAlignment(Element.ALIGN_MIDDLE);//���������ʾ 
			aTable.setAutoFillEmptyCells(true); //�Զ����� 
			aTable.setBorderWidth(1); //�߿��� 
			aTable.setBorderColor(new Color(0, 125, 255)); //�߿���ɫ 
			aTable.setPadding(2);//�ľ࣬��Ч����֪��ʲô��˼�� 
			aTable.setSpacing(3);//����Ԫ��֮��ļ�� 
			aTable.setBorder(2);//�߿� 

			//���ñ�ͷ 
			/** 
			* cell.setHeader(true);�ǽ��õ�Ԫ����Ϊ��ͷ��Ϣ��ʾ�� 
			* cell.setColspan(3);ָ���˸õ�Ԫ��ռ3�У� 
			* Ϊ�����ӱ�ͷ��Ϣʱ��Ҫע�����һ����ͷ��Ϣ�������֮�� 
			* ������� endHeaders()���������򵱱���ҳ�󣬱�ͷ��Ϣ��������ʾ 
			*/ 
			Cell haderCell = new Cell("����ͷ"); 
			haderCell.setHeader(true); 
			haderCell.setColspan(3); 
			aTable.addCell(haderCell); 
			aTable.endHeaders(); 

			Cell cell = new Cell(new Phrase("����һ�����Ե� 3*3 Table ����", fontChinese )); 
			cell.setVerticalAlignment(Element.ALIGN_TOP); 
			cell.setBorderColor(new Color(255, 0, 0)); 
			cell.setRowspan(2); 
			aTable.addCell(cell); 

			aTable.addCell(new Cell("#1")); 
			aTable.addCell(new Cell("#2")); 
			aTable.addCell(new Cell("#3")); 
			aTable.addCell(new Cell("#4")); 
			Cell cell3 = new Cell(new Phrase("һ����������", fontChinese )); 
			cell3.setColspan(3); 
			cell3.setVerticalAlignment(Element.ALIGN_CENTER); 
			aTable.addCell(cell3); 

			doc.add(aTable); 
           
			// �ر��ĵ������ͷ���Դ            
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
