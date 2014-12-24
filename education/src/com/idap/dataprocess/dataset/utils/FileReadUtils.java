package com.idap.dataprocess.dataset.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.idp.pub.utils.DateUtil;

public class FileReadUtils {

	// -----------------------------------已使用----------------------------------
	/**
	 * 文件读取-返回分隔符
	 * 
	 * @param split
	 * @return
	 */
	public static String transSplit(String split) {
		if ("tab".equals(split)) {
			return "\t";
		} else if ("semicolon".equals(split)) {
			return ";";
		} else if ("comma".equals(split)) {
			return ",";
		} else if ("space".equals(split)) {
			return " ";
		}
		return "^".equals(split) ? "\\" + split : "[" + split + "]";
	}

	/**
	 * 文件读取-返回分隔符
	 * 
	 * @param str
	 * @return
	 */
	public static String transString(String str) {
		if ("tab".equals(str)) {
			return "\t";
		} else if ("semicolon".equals(str)) {
			return ";";
		} else if ("comma".equals(str)) {
			return ",";
		} else if ("space".equals(str)) {
			return " ";
		}
		return str;
	}

	/**
	 * 已使用 解析EXCEL CELL格式(xlsx)
	 * 
	 * @param cell
	 * @return
	 */
	public static String convertCell(XSSFCell cell, XSSFFormulaEvaluator evaluator) {
		NumberFormat formater = NumberFormat.getInstance();
		formater.setGroupingUsed(false);
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		switch (cell.getCellType()) {

		case XSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				Date date = HSSFDateUtil.getJavaDate(d);
				cellValue = DateUtil.dateTimeToStrDefault(date);
			} else {
				cellValue = formater.format(cell.getNumericCellValue());
			}
			break;
		case XSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			cellValue = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = Boolean.valueOf(cell.getBooleanCellValue()).toString();
			break;
		case XSSFCell.CELL_TYPE_ERROR:
			cellValue = String.valueOf(cell.getErrorCellValue());
			break;
		case XSSFCell.CELL_TYPE_FORMULA:
			org.apache.poi.ss.usermodel.CellValue cellval = evaluator.evaluate(cell);
			// System.out.println(cellval.formatAsString());
			cellValue = getCellStringVal(cellval.formatAsString());
			break;
		default:
			cellValue = "";
		}
		return cellValue;
	}

	/**
	 * 已使用 解析EXCEL CELL格式(xls)
	 * 
	 * @param cell
	 * @return
	 */
	public static String convertCell(HSSFCell cell, HSSFFormulaEvaluator evaluator) {
		NumberFormat formater = NumberFormat.getInstance();
		formater.setGroupingUsed(false);
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				double d = cell.getNumericCellValue();
				Date date = HSSFDateUtil.getJavaDate(d);
				cellValue = DateUtil.dateTimeToStrDefault(date);
			} else {
				cellValue = formater.format(cell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = Boolean.valueOf(cell.getBooleanCellValue()).toString();
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			cellValue = String.valueOf(cell.getErrorCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			CellValue cellval = evaluator.evaluate(cell);
			// System.out.println(getCellStringVal(cellval.formatAsString()));
			cellValue = getCellStringVal(cellval.formatAsString());
			break;
		default:
			cellValue = "";
		}
		return cellValue;
	}

	private static String getCellStringVal(String cellval) {
		if (cellval.startsWith("\"") && cellval.endsWith("\"")) {
			cellval = cellval.substring(1, cellval.length() - 1);
		}
		return cellval;
	}

	/**
	 * 已使用：
	 * 
	 * @Description 读取txt文件编码
	 * @author
	 * @param {filePath:文件路径}
	 * @return
	 * @version 1.0
	 */
	public static String getFileEncoding(File sourceFile) {
		return FileCharsetDetector.detectFileCharset(sourceFile);
	}

	// ----------------------------------已下为旧内容暂未使用---------------------------------

	/**
	 * 判断是否是日期
	 * 
	 * @param dateValue
	 * @return
	 */
	public static boolean isDate(String dateValue) {
		if (dateValue != null && !"".equals(dateValue)) {
			Pattern p = Pattern.compile("^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$");
			Matcher m = p.matcher(dateValue);
			if (!m.find()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getFormatDate(Date date) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = "";
		dateString = sf.format(date);
		return dateString;
	}

	/**
	 * 对读取的值进行处理
	 * 
	 * @param value
	 * @return
	 */
	public static String cleanStr(String value) {
		if (value == null)
			value = "";
		return value.replaceAll(" ", "").replaceAll("\n", "");
	}

	/**
	 * 返回文本中列的最大长度
	 * 
	 * @param filePath
	 * @param split
	 * @return
	 */
	public static Map<String, String> getTxtMaxLen(String filePath, String split) {
		Map<String, String> map = new TreeMap<String, String>();
		int len = 0;
		if (filePath.toUpperCase().endsWith(".TXT") || filePath.toUpperCase().endsWith(".DAT")
				|| filePath.toUpperCase().endsWith(".CSV")) {
			LineNumberReader lnReader = null;
			try {
				lnReader = new LineNumberReader(new FileReader(filePath));
				while (true) {
					String lineStr = lnReader.readLine();
					if (lineStr == null) {
						break;
					}
					if (lineStr != null && lineStr.length() != 0) {
						lineStr += " ";
						String[] fields = lineStr.split(split);
						String num = map.get(fields.length + "");
						if (num == null || "".equals(num)) {
							num = "0";
						}
						map.put(fields.length + "", (Integer.valueOf(num) + 1) + "");
						if (fields.length > len) {
							// if(lnReader.getLineNumber() != 1)
							// retMap.put(lnReader.getLineNumber()+"",
							// lineStr.split(split).length+"");
							len = fields.length;
						}
					}
				}
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			} finally {
				if (null != lnReader) {
					try {
						lnReader.close();
					} catch (IOException e) {
					}
				}
			}
		} else if (filePath.toUpperCase().endsWith(".XLSX")) {
			InputStream is = null;
			XSSFWorkbook workbook = null;
			try {
				is = new FileInputStream(filePath);
				workbook = new XSSFWorkbook(is);
				XSSFSheet sheets = workbook.getSheetAt(0);

				for (int i = 0; i < sheets.getLastRowNum(); i++) {
					int col = sheets.getRow(i).getLastCellNum();
					if (col > len) {
						len = col;
					}
				}
				map.put(len + "", (sheets.getLastRowNum() + 1) + "");
			} catch (Exception e) {

			} finally {
				try {
					if (is != null)
						is.close();

				} catch (IOException e) {
				}
			}
		} else if (filePath.toUpperCase().endsWith(".XLS") || filePath.toUpperCase().endsWith(".ET")) {
			InputStream is = null;
			HSSFWorkbook workbook = null;
			try {
				is = new FileInputStream(filePath);
				workbook = new HSSFWorkbook(is);
				HSSFSheet sheets = workbook.getSheetAt(0);
				int row = sheets.getLastRowNum() + 1;
				for (int i = 0; i < row; i++) {
					int col = sheets.getRow(i).getLastCellNum();
					if (col > len) {
						len = col;
					}
				}
				map.put(len + "", row + "");
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (IOException e) {
				}
			}
		} else if (filePath.toUpperCase().endsWith(".DBF")) {
			// InputStream inputStream = null;
			// try {
			// inputStream = new FileInputStream(filePath);
			// DBFReader dbfreader = new DBFReader(inputStream);
			// len = dbfreader.getFieldCount();
			// map.put(len+"", (dbfreader.getRecordCount())+"");
			// }catch (FileNotFoundException e) {
			//
			// } catch (IOException e) {
			//
			// } finally {
			// try {
			// if(inputStream!=null) inputStream.close();
			// } catch (Exception e) {
			//
			// }
			// }
		}
		String str = "系统对文件检查结果：<br>";
		String strOption = "<option value='" + len + "'>" + len + "</option>";
		int row = 0;
		for (String s : map.keySet()) {
			if (!s.equals(len + "")) {
				strOption += "<option value='" + s + "'>" + s + "</option>";
			}
			str += "列数为【 " + s + " 】的共【 " + map.get(s) + " 】条 <br>";
			row += Integer.valueOf(map.get(s));
		}

		map.put("sysMsg", str);
		map.put("conlength", len + "");
		map.put("strOption", strOption);
		map.put("maxlength", len + "_" + row);
		map.put("length", len + "");// 最大列数
		return map;
	}

	public static void main(String[] args) {
		String str = "邓玉华	130822198211116415	203	兴隆县李家营乡赵家店村		 ";
		String[] fields = str.split("\t");
		System.out.println(fields.length);
		String[] kkk = "a|b".split(transSplit("|"));
		System.out.println("a|b".split(transSplit("|")));

		try {
			File file = new File("d:\\Users\\libin\\Desktop\\testFile\\清洗数据win.txt");
			InputStream in;
			in = new java.io.FileInputStream(file);
			byte[] b = new byte[30];
			in.read(b);
			in.close();
			if (b[0] == -17 && b[1] == -69 && b[2] == -65)
				System.out.println(file.getName() + "：编码为UTF-8");
			else
				System.out.println(file.getName() + "：可能是GBK，也可能是其他编码");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
