package com.teamsun.framework.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelView {
	// 
	/** 设置cell编码解决中文高位字节截断 */
	private static short XLS_ENCODING = HSSFWorkbook.ENCODING_UTF_16;

	/** 定制日期格式. */
	// private static String DATE_FORMAT = " yyyy-mm-dd h:mm";
	/** 定制浮点数格式. */
	// private static String NUMBER_FORMAT = " #,##0.00 ";
	/** The xls file name. */
	private String xlsFileName;

	/** The workbook. */
	private HSSFWorkbook workbook;

	/** The sheet. */
	private HSSFSheet sheet;

	/** The row. */
	private HSSFRow row;

	/**
	 * 初始化Excel.
	 * 
	 * @param fileName
	 *            导出文件名
	 * 
	 */
	public ExcelView(String fileName) {
		this.xlsFileName = fileName;
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
	}

	public ExcelView() {
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
	}

	/**
	 * 导出Excel文件.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public void exportXLS() throws Exception {
		try {
			FileOutputStream fOut = new FileOutputStream(xlsFileName);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			throw new Exception(" 生成导出Excel文件出错! ", e);
		} catch (IOException e) {
			throw new Exception(" 写入Excel文件出错! ", e);
		}

	}

	/**
	 * 增加一行.
	 * 
	 * @param index
	 *            行号
	 */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	/**
	 * 设置单元格.
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 * 
	 */
	public void setCell(int index, String value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格.
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 * 
	 */
	public void setCell(int index, int value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格.
	 * 
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 * 
	 */
	public void setCell(int index, double value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		// HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		// HSSFDataFormat format = workbook.createDataFormat();
		// cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); //
		// 设置cell样式为定制的浮点数格式

		// cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
	}

	public static void main(String[] arg) throws Exception {

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("new sheet");

		// Create a row and put some cells in it. Rows are 0 based.
		HSSFRow row = sheet.createRow((short) 0);

		// Create a cell and put a date value in it. The first cell is not
		// styled
		// as a date.
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue(new Date());

		// we style the second cell as a date (and time). It is important to
		// create a new cell style from the workbook otherwise you can end up
		// modifying the built in style and effecting not only this cell but
		// other cells.
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		cell = row.createCell((short) 1);
		cell.setCellValue(new Date());
		cell.setCellStyle(cellStyle);

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		wb.write(fileOut);
		System.out.println("fileName:" + fileOut);
		fileOut.close();

	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

}
