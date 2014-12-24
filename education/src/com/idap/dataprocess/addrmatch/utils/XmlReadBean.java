package com.idap.dataprocess.addrmatch.utils;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.idap.dataprocess.addrmatch.entity.MatchParameters;

/**
 * JSBDM
 * 
 * @author yanghl
 * @since Feb 11, 2011
 * @version 1.0
 */
public class XmlReadBean {
	private static final Logger logger = LoggerFactory
			.getLogger(XmlReadBean.class);

	public static String formatDateToString(String pattern, Date date) {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 生成文件匹配XML字符串命令
	 * 
	 * @param srcFile
	 *            源文件地址
	 * @param aimFile
	 *            目标文件地址
	 * @param addrColumn
	 *            地址列
	 * @param postCodeColumn
	 *            邮编列
	 * @param divisionColumn
	 *            行政区划列
	 * @param separator
	 *            分隔符
	 * @param matchType
	 *            匹配类型
	 * @param outputLevel
	 *            输出级别
	 * @param matchScore
	 *            匹配范围
	 * @param priority
	 *            匹配优先级
	 * @return
	 */
	public static String generalFileMatchXml(MatchParameters matchParams) {
		String xmlString = "";
		Document document = returnPopDocument("FileMatch.xml");
		Attribute createTimeAttribute = (Attribute) document
				.selectNodes("/root/AddrCmd/@CreateTime").iterator().next();
		createTimeAttribute.setValue(formatDateToString("yyyy-MM-dd HH:mm",
				new Date()));
		Element fileMatchElement = (Element) document
				.selectNodes("//FileMatch").iterator().next();
		fileMatchElement.attribute("SrcFile").setValue(
				matchParams.getInputFile());
		fileMatchElement.attribute("AimFile").setValue(
				matchParams.getOutputFile());
		if (StringUtils.isEmpty(matchParams.getAddrColumn())) {
			matchParams.setAddrColumn("-1");
		}
		if (StringUtils.isEmpty(matchParams.getPostCodeColumn())) {
			matchParams.setPostCodeColumn("-1");
		}

		if (StringUtils.isEmpty(matchParams.getDivisionColumn())) {
			matchParams.setDivisionColumn("-1");
		}
		fileMatchElement.attribute("AddrColumn").setValue(
				matchParams.getAddrColumn());
		fileMatchElement.attribute("PostCodeColumn").setValue(
				matchParams.getPostCodeColumn());
		fileMatchElement.attribute("DivisionColumn").setValue(
				matchParams.getDivisionColumn());
		fileMatchElement.attribute("Separator").setValue(
				matchParams.getSeparator());
		fileMatchElement.attribute("MatchType").setValue(
				matchParams.getMatchType());
		fileMatchElement.attribute("OutputLevel").setValue(
				matchParams.getOutputLevel());
		// fileMatchElement.attribute("MatchScore").setValue(matchScore);
		fileMatchElement.attribute("Priority").setValue("-1"); // 文件匹配-1,快速录入2
		fileMatchElement.attribute("DivLimit").setValue("-1");
		xmlString = document.asXML();
		logger.info("文件匹配XML字符串：" + xmlString);
		return xmlString.substring(xmlString.indexOf("<root>"),
				xmlString.length());
	}

	/**
	 * 生成文件匹配状态XML字符串命令
	 * 
	 * @param taskId
	 *            任务ID
	 * @return
	 */
	public static String generalFileMatchStateXml(String taskId) {
		String xmlString = "";
		Document document = returnPopDocument("FileMatchState.xml");
		Attribute createTimeAttribute = (Attribute) document
				.selectNodes("/root/AddrCmd/@CreateTime").iterator().next();
		createTimeAttribute.setValue(formatDateToString("yyyy-MM-dd HH:mm",
				new Date()));
		Element fileMatchElement = (Element) document
				.selectNodes("//FileMatchState").iterator().next();
		fileMatchElement.attribute("TaskID").setValue(taskId);
		xmlString = document.asXML();
		logger.info("文件匹配状态XML字符串：" + xmlString);
		return xmlString.substring(xmlString.indexOf("<root>"),
				xmlString.length());
	}

	/**
	 * 生成单条匹配XML字符串命令
	 * 
	 * @param srcAddress
	 *            待匹配地址
	 * @param division
	 *            行政区划代码
	 * @param prePostCode
	 *            邮编
	 * @param matchType
	 *            匹配类型(匹配地址--1 匹配邮编--2)
	 * @param outputLevel
	 *            输出级别(省--0 市--1 区--2 街道--3 门牌--4 单元室--5)
	 * @param priority
	 *            优先级(地址优先--0 行政区划优先--1 邮编优先--2)
	 * @return
	 */
	public static String generalSingleMatchXml(String srcAddress,
			String division, String prePostCode, String matchType,
			String outputLevel, String priority) {
		String xmlString = "";
		Document document = returnPopDocument("SinleMatch.xml");
		Attribute createTimeAttribute = (Attribute) document
				.selectNodes("/root/AddrCmd/@CreateTime").iterator().next();
		createTimeAttribute.setValue(formatDateToString("yyyy-MM-dd HH:mm",
				new Date()));
		Element fileMatchElement = (Element) document
				.selectNodes("//SingleMatch").iterator().next();
		fileMatchElement.attribute("SrcAddress").setValue(srcAddress);
		fileMatchElement.attribute("Division").setValue(division);
		fileMatchElement.attribute("PrePostCode").setValue(prePostCode);
		fileMatchElement.attribute("MatchType").setValue(matchType);
		fileMatchElement.attribute("OutputLevel").setValue(outputLevel);
		fileMatchElement.attribute("Priority").setValue(priority);
		// 地址匹配范围
		fileMatchElement.attribute("DivLimit").setValue("-1");
		xmlString = document.asXML();
		logger.info("单条匹配XML字符串：" + xmlString);
		return xmlString.substring(xmlString.indexOf("<root>"),
				xmlString.length());
	}

	/**
	 * 生成快速录入XML字符串命令
	 * 
	 * @param prevRes
	 *            上次匹配的结果ID, 若为0表示从头开始匹配
	 * @param srcAddr
	 *            要继续匹配的地址(是否全地址?)
	 * @param dictID
	 *            字典ID(苏州市为320500)
	 * @return
	 */
	public static String generalFastInputXml(String prevRes, String srcAddr,
			String dictID) {
		String xmlString = "";
		Document document = returnPopDocument("FastInput.xml");
		document.setXMLEncoding("GBK");
		Attribute createTimeAttribute = (Attribute) document
				.selectNodes("/root/AddrCmd/@CreateTime").iterator().next();
		createTimeAttribute.setValue(formatDateToString("yyyy-MM-dd HH:mm",
				new Date()));
		Element fileMatchElement = (Element) document
				.selectNodes("//FastInput").iterator().next();
		fileMatchElement.attribute("PrevRes").setValue(prevRes);
		fileMatchElement.attribute("SrcAddr").setValue(srcAddr);
		fileMatchElement.attribute("DictID").setValue("-1");// 快速录入范围
		fileMatchElement.attribute("MatchType").setValue("0");// 快速录入范围
		fileMatchElement.attribute("DivLimit").setValue("-1");// 快速录入范围
		xmlString = document.asXML();
		logger.info("快速录入XML字符串：" + xmlString);
		return xmlString.substring(xmlString.indexOf("<root>"),
				xmlString.length());
	}

	/**
	 * 获得一个节点的子节点XML字符串命令
	 * 
	 * @param fastNodeID
	 *            节点ID
	 * @param dictID
	 *            字典ID(苏州市为320500)
	 * @return
	 */
	public static String generalGetChildrenXml(String fastNodeID, String dictID) {
		String xmlString = "";
		Document document = returnPopDocument("GetChildren.xml");
		document.setXMLEncoding("GBK");
		Attribute createTimeAttribute = (Attribute) document
				.selectNodes("/root/AddrCmd/@CreateTime").iterator().next();
		createTimeAttribute.setValue(formatDateToString("yyyy-MM-dd HH:mm",
				new Date()));
		Element fileMatchElement = (Element) document
				.selectNodes("//GetChildren").iterator().next();
		fileMatchElement.attribute("FastNodeID").setValue(fastNodeID);

		fileMatchElement.attribute("DictID").setValue(dictID);
		xmlString = document.asXML();
		logger.info("获得一个节点的子节点XML字符串：" + xmlString);
		return xmlString.substring(xmlString.indexOf("<root>"),
				xmlString.length());
	}

	public static Document returnPopDocument(String name) {
		SAXReader reader = new SAXReader();
		Document document = null;
		File file = new File(name);
		try {
			String path = XmlReadBean.class.getPackage().getName()
					.replace(".", "/").replace("utils", "resources");

			String fileName = "/" + path + "/" + name;
			logger.info("FILEPATH: " + fileName);
			if (!file.exists()) {
				XmlReadBean.class.getPackage().getName();
				InputStream stream = XmlReadBean.class
						.getResourceAsStream(fileName);
				document = reader.read(stream);
			} else {
				document = reader.read(file);
			}
		} catch (DocumentException e) {
			logger.error("", e);
		}
		return document;
	}

	public static void main(String[] args) {
		SAXReader reader = new SAXReader();
		Document document = null;
		File file = new File("FileMatch.xml");
		try {
			String path = XmlReadBean.class.getPackage().getName()
					.replace(".", "/").replace("utils", "resources");
			if (!file.exists()) {
				XmlReadBean.class.getPackage().getName();
				InputStream stream = XmlReadBean.class.getResourceAsStream("/"
						+ path + "/" + "FileMatch.xml");
				document = reader.read(stream);
			} else {
				document = reader.read(file);
			}
		} catch (DocumentException e) {
			logger.error("", e);
		}
	}
}
