package com.idap.dataprocess.dataset.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.idap.dataprocess.dataset.utils.file.UnicodeReader;
import com.idap.dataprocess.dataset.vo.UploadFileContent;

public class FileReadContent {
	private static String FRIST_IS_TITLE = "1";

	/**
	 * 从文本文件 中读取指定条数的记录 1.此方法仅供读取前台样本数据
	 * 
	 * @param file 欲读取的文本文件
	 * @param separate 文本文件分割隔 具体内容见：FileReadUtils.transSplit 方法
	 * @param firstIsTitle 第一行是否是标题 0：否 1：是
	 * @param lineCount 前台样式数据读取量
	 * @return UploadFileContent {title:[],conent:[]}
	 * @throws IOException
	 */
	public static UploadFileContent readTxtFile(final File file, final String splitSeparate, final String firstIsTitle,
			final int lineCount) throws IOException {
		return readTxtFile(file, splitSeparate, firstIsTitle, lineCount, 0);
	}

	public static UploadFileContent readTxtFile(final File file, final String splitSeparate, final String firstIsTitle,
			final int lineCount, final int colSize) throws IOException {
		// Map {title:[],conent:[]}
		UploadFileContent resultContent = new UploadFileContent();
		BufferedReader lnReader = null;
		try {
			lnReader = new BufferedReader(new UnicodeReader(new FileInputStream(file),
					FileReadUtils.getFileEncoding(file)));
			resultContent = readTxtFileSegment(lnReader, splitSeparate, firstIsTitle, lineCount, colSize);
		} catch (IOException e) {
			throw e;
		} finally {
			lnReader.close();
		}
		return resultContent;
	}

	/**
	 * 读取文本文件的片段 可分批读取文件内的数据
	 * 
	 * @param lnReader 文本文件输入流
	 * @param separate 文本文件分割隔 具体内容见：FileReadUtils.transSplit 方法
	 * @param firstIsTitle 第一行是否是标题 0：否 1：是
	 * @param lineCount 分批读取条数
	 * @param colSize 列数
	 * @return
	 * @throws IOException
	 */
	public static UploadFileContent readTxtFileSegment(final BufferedReader lnReader, final String splitSeparate,
			final String firstIsTitle, final int lineCount, final int colSize) throws IOException {
		UploadFileContent resultContent = new UploadFileContent();
		String regSplitSeparate = FileReadUtils.transSplit(splitSeparate);
		List<String> title = new ArrayList<String>();
		List<List<String>> conent = new ArrayList<List<String>>();

		String temp =new String( lnReader.readLine().getBytes());
		// 计算第一行内容数据
		int contentColumnSize = 0;// 列数
		if (colSize > 0) {
			contentColumnSize = colSize;
		} else {
			// 读第一行非空数据
			if (StringUtils.isBlank(temp)) {
				while (temp != null && StringUtils.isNotBlank(temp)) {
					temp = lnReader.readLine();
				}
			}

			contentColumnSize = StringUtils.countMatches(temp, FileReadUtils.transString(splitSeparate)) + 1;// 列数
		}
		int startLine = 0;
		while (temp != null) {
			if (StringUtils.isNotBlank(temp)) {// 去空行
				List<String> arrs = new ArrayList<String>();
				String[] items = temp.split(regSplitSeparate);// 列内容
				arrs.addAll(Arrays.asList(items));
				contentFilter(contentColumnSize, items.length, arrs, firstIsTitle, title, conent, startLine);
				startLine++;
				if (conent.size() >= lineCount) {// 读取指定条数
					break;
				}
			}
			temp = lnReader.readLine();
		}

		resultContent.setTitle(title);
		resultContent.setSplitSeparate(splitSeparate);
		resultContent.setContent(conent);
		resultContent.setFirstIsTitle(firstIsTitle);
		return resultContent;
	}

	/**
	 * 读取excel97文件内容
	 * 
	 * @param file 欲读取的文件
	 * @param firstIsTitle 第一行是否是标题 0：否 1：是
	 * @param lineCount 读取量
	 * @param colSize 列数
	 * @return UploadFileContent {title:[],conent:[]}
	 * @throws IOException
	 */
	public static UploadFileContent readXlsFile(final File file, final String firstIsTitle, final int lineCount,
			final int colSize) throws FileNotFoundException, IOException {
		UploadFileContent resultContent = new UploadFileContent();
		HSSFWorkbook workbook = null;
		workbook = new HSSFWorkbook(new FileInputStream(file));
		HSSFFormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		HSSFSheet sheets = workbook.getSheetAt(0);
		resultContent = readXlsFileSegment(sheets, firstIsTitle, 0, lineCount, colSize, evaluator);
		return resultContent;
	}

	/**
	 * excel97文件内容读取片段 从第startLine行开始读取lineCount条数据
	 * 
	 * @param sheets 欲读取的工作簿
	 * @param firstIsTitle 第一行是否是标题 0：否 1：是
	 * @param startLine 从第startLine行开始读取lineCount条数据
	 * @param lineCount 读取量
	 * @return UploadFileContent {title:[],conent:[]}
	 * @throws IOException
	 */
	public static UploadFileContent readXlsFileSegment(final HSSFSheet sheets, final String firstIsTitle,
			int startLine, int lineCount, final int colSize, HSSFFormulaEvaluator evaluator) {
		UploadFileContent resultContent = new UploadFileContent();
		List<String> title = new ArrayList<String>();
		List<List<String>> conent = new ArrayList<List<String>>();
		int rows = sheets.getLastRowNum();
		int contentColumnSize = 0;// 列数
		if (colSize > 0) {
			contentColumnSize = colSize;
		} else {
			// 读第一行非空数据
			for (int i = 0; i <= rows; i++) {
				HSSFRow row = sheets.getRow(i);
				if (row.getLastCellNum() > 0) {
					contentColumnSize = row.getLastCellNum();
					break;
				}
			}
		}
		int currColNum = 0;
		for (; startLine <= rows; startLine++) {
			HSSFRow row = sheets.getRow(startLine);
			if (row != null) {
				int colNum = row.getLastCellNum();
				// 过滤空行
				if (colNum > 0) {
					List<String> arrs = new ArrayList<String>();
					for (int j = 0; j < colNum; j++) {
						HSSFCell cell = row.getCell(j);
						arrs.add(FileReadUtils.convertCell(cell, evaluator));
					}
					currColNum = arrs.size();
					contentFilter(contentColumnSize, currColNum, arrs, firstIsTitle, title, conent, startLine);

					if (conent.size() >= lineCount) {// 读取指定条数
						break;
					}
				}
			}
		}
		resultContent.setTitle(title);
		resultContent.setContent(conent);
		resultContent.setFirstIsTitle(firstIsTitle);
		return resultContent;
	}

	/**
	 * 读取excel2010文件内容
	 * 
	 * @param file 欲读取的文件
	 * @param firstIsTitle 第一行是否是标题 0：否 1：是
	 * @param lineCount 读取量
	 * @param colSize 列数
	 * @return UploadFileContent {title:[],conent:[]}
	 * @throws IOException
	 */
	public static UploadFileContent readXlsxFile(final File file, final String firstIsTitle, final int lineCount,
			final int colSize) throws FileNotFoundException, IOException {
		UploadFileContent resultContent = new UploadFileContent();
		XSSFWorkbook workbook = null;
		workbook = new XSSFWorkbook(new FileInputStream(file));
		XSSFFormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		XSSFSheet sheets = workbook.getSheetAt(0);
		resultContent = readXlsxFileSegment(sheets, firstIsTitle, 0, lineCount, colSize, evaluator);
		return resultContent;
	}

	/**
	 * excel2010 文件内容读取片段 从第startLine行开始读取lineCount条数据
	 * 
	 * @param sheets 欲读取的工作簿
	 * @param firstIsTitle 第一行是否是标题 0：否 1：是
	 * @param startLine 从第startLine行开始读取lineCount条数据
	 * @param lineCount 读取量
	 * @return UploadFileContent {title:[],conent:[]}
	 * @throws IOException
	 */
	public static UploadFileContent readXlsxFileSegment(final XSSFSheet sheets, final String firstIsTitle,
			int startLine, int lineCount, final int colSize, XSSFFormulaEvaluator evaluator) {
		UploadFileContent resultContent = new UploadFileContent();
		List<String> title = new ArrayList<String>();
		List<List<String>> conent = new ArrayList<List<String>>();
		int rows = sheets.getLastRowNum();
		int contentColumnSize = 0;// 列数
		if (colSize > 0) {
			contentColumnSize = colSize;
		} else {
			// 读第一行非空数据
			for (int i = 0; i <= rows; i++) {
				XSSFRow row = sheets.getRow(i);
				if (row.getLastCellNum() > 0) {
					contentColumnSize = row.getLastCellNum();
					break;
				}
			}
		}
		int currColNum = 0;
		for (; startLine <= rows; startLine++) {
			XSSFRow row = sheets.getRow(startLine);
			if (row != null) {
				int colNum = row.getLastCellNum();
				// 过滤空行
				if (colNum > 0) {
					List<String> arrs = new ArrayList<String>();
					for (int j = 0; j < colNum; j++) {
						XSSFCell cell = row.getCell(j);
						arrs.add(FileReadUtils.convertCell(cell, evaluator));
					}
					currColNum = arrs.size();
					contentFilter(contentColumnSize, currColNum, arrs, firstIsTitle, title, conent, startLine);
					if (conent.size() >= lineCount) {// 读取指定条数
						break;
					}
				}
			}
		}
		resultContent.setTitle(title);
		resultContent.setContent(conent);
		resultContent.setFirstIsTitle(firstIsTitle);
		return resultContent;
	}

	/**
	 * 为excel、text 提供内容补全 空行不加入数据集内 内容会根据总列表初始化为不存在的列补空白字符
	 * 
	 * @param contentColumnSize 总列表 为数据集的字段数
	 * @param currColNum 当前内容列数
	 * @param columnsContentList 当前列内空
	 * @param firstIsTitle 是否是标题
	 * @param title 存放表头列表
	 * @param conent 存放内容列表 []
	 * @param startLine 开始行
	 */
	private static void contentFilter(int contentColumnSize, int currColNum, List<String> columnsContentList,
			String firstIsTitle, List<String> title, List<List<String>> conent, int startLine) {
		// 1.当前行是行为空行 所有都为空字符串 认为是空记录
		boolean isnull = true;
		for (String str : columnsContentList) {
			if (StringUtils.isNotBlank(str)) {
				isnull = false;
				break;
			}
		}
		if (!isnull) {
			// 2.记录补全 当有10列 内容只有8列 剩下的2列补空白字符
			if (contentColumnSize > currColNum) {
				for (int j = 0; (j < contentColumnSize - currColNum); j++) {
					columnsContentList.add("");
				}
			}
			if (contentColumnSize < currColNum) {// 去除比指定列数多的记录
				Iterator<String> c_it = columnsContentList.iterator();
				int redInd = 0;
				while (c_it.hasNext()) {
					redInd++;
					if (contentColumnSize < redInd) {
						c_it.next();
						c_it.remove();
					} else {
						c_it.next();
					}
				}
			}

			// 3.区分第一行是不是表头
			if (firstIsTitle.equals(FRIST_IS_TITLE) && startLine == 0) {// 将表头放存放
				title.addAll(columnsContentList);
			} else {// 内容区
				conent.add(columnsContentList);
			}
		}
	}

	/**
	 * 暂时未使用 字符型长度默认为200 长度
	 * 
	 * @param contentArr　一条记录的内容
	 * @param columnLen　列长度
	 */
	private static List<Integer> contentLengthTest(List<String> contentArr, List<Integer> columnLen) {
		for (int i = 0; i < contentArr.size(); i++) {
			if ((i + 1) <= columnLen.size()) {
				if (contentArr.get(i).length() > columnLen.get(i)) {
					columnLen.set(i, contentArr.get(i).length());
				}
			} else {
				columnLen.add(contentArr.get(i).length());
			}
		}
		return columnLen;
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {

		int contentColumnSize = StringUtils.countMatches("李四12	370702	261021	青岛市城阳赵家岭村469号		18611280052	1	1	1	1	22		",
				"\t") + 1;// 列数
		String[] aa = StringUtils.split("a	b	c   d       ", "\t");
		List kk = new ArrayList();
		kk.add("1");
		kk.add("2");
		kk.add("3");
		kk.add("4");

		System.out.println(StringUtils.substring("123456789012345678901234567890", 0, 20));

		Iterator<String> c_it = kk.iterator();
		int redInd = 0;
		while (c_it.hasNext()) {
			redInd++;
			if (2 < redInd) {
				c_it.next();
				c_it.remove();
			} else {
				c_it.next();
			}
		}

		kk.add("5");
		// for(String str:list){
		// list.remove(str);
		// }

		//
		// System.out.println("汇款人姓名||||||".split("[|]").length);
		// System.out.println(Arrays.asList("a|b||".split("|")).size());
		// List<String> arrs = Arrays.asList("a|b||".split("|"));
		// System.out.println(arrs.size());
		//
		//
		// StringUtils.countMatches("汇款人姓名||||||", "|");
		// System.out.println(StringUtils.countMatches("汇款人姓名||||||", "|"));
		// String splitSeparate = "tab";
		// String firstIsTitle = "1";
		// try {
		File file = new File("d:\\test.txt");
		UploadFileContent rs = null;
		// rs=readTxtFile(file,FileReadUtils.transSplit(splitSeparate),firstIsTitle);
		file = new File("d:\\test.xls");
		rs = readXlsFile(file, "1", 1000, 0);
		System.out.println("----------------");
		file = new File("d:\\test.et");
		rs = readXlsFile(file, "1", 1000, 0);
		System.out.println("----------------");
		file = new File("d:\\test.xlsx");
		rs = readXlsxFile(file, "1", 1000, 0);
		// System.out.println(rs.toString());
		// //
		//
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
