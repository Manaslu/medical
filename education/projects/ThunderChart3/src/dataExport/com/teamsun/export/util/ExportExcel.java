package com.teamsun.export.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.contrib.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;

public class ExportExcel {

	private HSSFWorkbook workBook;

	private HSSFSheet sheet;

	private LinkedHashMap<String, ColumnInfoVO> dataList;// 二维数组，第一维是行，第二维是每行的数据数组\

	private List<ChartMetaData> chartMetaDataList;//图形数组
	private OutputStream os;

	private short dataStartCol = 0;// 数据的起始列位置

	private int dataStartRow = 5;// 数据的起始行位置
	
	private short chartSstartCol = 5;

	private int chartStartRow = 5;// 数据的起始行列位置
	

	
	public short getDataStartCol() {
		return dataStartCol;
	}
	public void setDataStartCol(short dataStartCol) {
		this.dataStartCol = dataStartCol;
	}
	
	public List<ChartMetaData> getChartMetaDataList() {
		return chartMetaDataList;
	}
	public void setChartMetaDataList(List<ChartMetaData> chartMetaDataList) {
		this.chartMetaDataList = chartMetaDataList;
	}
	public short getChartSstartCol() {
		return chartSstartCol;
	}
	public void setChartSstartCol(short chartSstartCol) {
		this.chartSstartCol = chartSstartCol;
	}
	public int getChartStartRow() {
		return chartStartRow;
	}
	public void setChartStartRow(int chartStartRow) {
		this.chartStartRow = chartStartRow;
	}
	public void setDataStartRow(short dataStartRow) {
		this.dataStartRow = dataStartRow;
	}
	public ExportExcel(List _chartMetaDataList, String _sheetName,
			OutputStream _os) {
		super();
		this.chartMetaDataList = _chartMetaDataList;
		this.workBook = new HSSFWorkbook();
		if(_sheetName.indexOf("(")>0)_sheetName=_sheetName.substring(0, _sheetName.indexOf("("));
		this.sheet = workBook.createSheet(_sheetName);
		this.os = _os;
	}
	public ExportExcel(LinkedHashMap _dataList, String _sheetName,
			OutputStream _os) {
		super();
		this.dataList = _dataList;
		this.workBook = new HSSFWorkbook();
		if(_sheetName.indexOf("(")>0)_sheetName=_sheetName.substring(0, _sheetName.indexOf("("));
		this.sheet = workBook.createSheet(_sheetName);
		this.os = _os;
	}
	public ExportExcel(List _chartMetaDataList,LinkedHashMap _dataList, String _sheetName,
			OutputStream _os) {
		super();
		this.chartMetaDataList = _chartMetaDataList;
		this.dataList = _dataList;
		this.workBook = new HSSFWorkbook();
		if(_sheetName.indexOf("(")>0)_sheetName=_sheetName.substring(0, _sheetName.indexOf("("));
		this.sheet = workBook.createSheet(_sheetName);
		this.os = _os;
	}
	/**
	 * 创建表头
	 * 
	 * @return
	 */
	private void createTableHeader() {
		HSSFHeader header = sheet.getHeader();
		header.setLeft(this.workBook.getSheetName(0));

		

	}

	/**
	 * 根据行列坐标的cell创建内容
	 * 
	 * @param rowNum
	 * @param colNum
	 * @param text
	 * @param type
	 */
	private void setCellValue(short rowNum, short colNum, String text,
			String type,HSSFCellStyle style) {
		HSSFRow row;
		HSSFCell cell;
//		if (StringUtils.isEmpty(text))
//			return;
//	
		
		rowNum = (short) (rowNum + dataStartRow);
		colNum = (short) (colNum + dataStartCol);
		row = HSSFCellUtil.getRow(rowNum, sheet);
		cell = HSSFCellUtil.getCell(row, colNum);
		
		if (type == null || "number".equals(type)) {// 默认为数字
			try {
				cell.setCellValue(new Double(text));
				
			} catch (Exception e) {
				//e.printStackTrace();
				cell.setCellValue(new HSSFRichTextString(text));			
			}
		} else {
			cell.setCellValue(new HSSFRichTextString(text));			
		}
		if(style!=null){
			cell.setCellStyle(style);
			System.out.println(text);
		}

	}

	/**
	 * 创建整个Excel表
	 * 
	 * @throws SQLException
	 * 
	 */
	private void createExcelSheet() {
		
		int i = 0;
		if(dataStartRow>1)//只有在有图形时，为了将图形与数据格开才+5
		dataStartRow=dataStartRow+5;
		for (Iterator ite = dataList.keySet().iterator(); ite.hasNext();) {
			ColumnInfoVO civ = dataList.get(ite.next());
			setCellValue((short) 0, (short) i, civ.getColumnCName(), "string",getTitleStyle());
			for (short j = 1; j <= civ.getLineData().size(); j++) {
				setCellValue(j, (short) i, civ.getLineData().get(j - 1)
						.toString(), civ.getColumnType(),getDataStyle());
			}
			i++;

		}

	}
	
	/**
	 * @author wangwei@teamsun.com.cn
	 * 只导出grid数据时的起始行为0
	 */
	private void createExcelSheet4Grid(){
		int i = 0;
		dataStartRow=0;
		for (Iterator ite = dataList.keySet().iterator(); ite.hasNext();) {
			ColumnInfoVO civ = dataList.get(ite.next());
			setCellValue((short) 0, (short) i, civ.getColumnCName(), "string",getTitleStyle());
			for (short j = 1; j <= civ.getLineData().size(); j++) {
				setCellValue(j, (short) i, civ.getLineData().get(j - 1)
						.toString(), civ.getColumnType(),getDataStyle());
			}
			i++;
		}
	}
	
    private HSSFCellStyle getTitleStyle(){
    	
    	   HSSFCellStyle titleStyle = workBook.createCellStyle();
    	   titleStyle.setBorderBottom((short)1);// 设置底部双线
    	   titleStyle.setBorderLeft((short)1);// 设置左边边框
    	   titleStyle.setBorderRight((short)1);
    	   titleStyle.setBorderTop((short)1);
    	   titleStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index); // 设置单元格颜色
    	   titleStyle.setFillPattern(HSSFCellStyle.BORDER_THIN);//设置单元格为点状
    	   titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
    	   return titleStyle;
    }
    private HSSFCellStyle getDataStyle(){
    	
 	   HSSFCellStyle titleStyle = workBook.createCellStyle();
 	   titleStyle.setBorderBottom((short)1);// 设置底部双线
 	   titleStyle.setBorderLeft((short)1);// 设置左边边框
 	   titleStyle.setBorderRight((short)1);
 	   titleStyle.setBorderTop((short)1);
 	   titleStyle.setFillForegroundColor(HSSFColor.WHITE.index); // 设置单元格颜色
 	   titleStyle.setFillPattern(HSSFCellStyle.BORDER_THIN);//设置单元格为点状
 	   titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
 	   return titleStyle;
 }
	/**
	 * 导出表格
	 * 
	 * @param sheet
	 * @param os
	 * @throws IOException
	 * 
	 */
	public void expordExcel() throws IOException {
		createExcelSheet();
		sheet.setGridsPrinted(true);
		HSSFFooter footer = sheet.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of "
				+ HSSFFooter.numPages());
		workBook.write(os);
		os.flush();
		os.close();
	}
	
	/**
	 * 只导出grid数据
	 * @author wangwei@temsun.com.cn
	 * @throws IOException
	 */
	public void expordGridExcel() throws IOException {
		createExcelSheet4Grid();
		sheet.setGridsPrinted(true);
		HSSFFooter footer = sheet.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of "
				+ HSSFFooter.numPages());
		workBook.write(os);
		os.flush();
		os.close();
	}
	
	public void expordDataAndChart() throws IOException{
		createTableHeader();
		
		createChartSheet();
		createExcelSheet();
		sheet.setGridsPrinted(true);
		HSSFFooter footer = sheet.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of "
				+ HSSFFooter.numPages());
		workBook.write(os);
		os.flush();
		os.close();
	}
	/**
	 * 导出图形
	 * 
	 * @param sheet
	 * @param os
	 * @throws IOException
	 * 
	 */
	public void expordChartExcel() throws IOException {
		createTableHeader();
		createChartSheet();
		HSSFFooter footer = sheet.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of "
				+ HSSFFooter.numPages());
		workBook.write(os);
		os.flush();
		os.close();
	}

    public void createChartSheet(){
    	HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); 
		String type="hori";
        int leftStartRowL=0;//纵向
        int leftStratCol=0;//横向
        int rightStartRowL=0;
        int rightStratCol=0;
		for(int i=0;i<chartMetaDataList.size();i++){
			ChartMetaData cmd=chartMetaDataList.get(i);
			  int height=cmd.getHeight();
		      int width=cmd.getWidth();
			  int rowL = height/20;
			  int col = width/70;
			  if("hori".equals(type)){
				  if(dataStartRow<(short)rowL)
					  dataStartRow=(short)rowL;
			  }else{
				  dataStartRow=dataStartRow+rowL;
			  }
			  
			  
			HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,255,255,(short) leftStratCol,leftStartRowL,(short)(rightStratCol+col),rightStartRowL+rowL); 
			patriarch.createPicture(anchor , workBook.addPicture(cmd.getBaos().toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
			 if("hori".equals(type)){
				  rightStratCol=rightStratCol+col+1;
				  leftStratCol=leftStratCol+col+1;
			  }else{
				 
				     rightStartRowL=rightStartRowL+rowL+3;
					 leftStartRowL=leftStartRowL+rowL+3;
			  }
			
		}
    }
	public OutputStream getOs() {
		return os;
	}

	public void setOs(OutputStream os) {
		this.os = os;
	}

	public static void main(String[] args) {

	}

	public HSSFWorkbook getWorkBook() {
		return workBook;
	}

	public void setWorkBook(HSSFWorkbook workBook) {
		this.workBook = workBook;
	}

}
