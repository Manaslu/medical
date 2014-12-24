package com.idap.dataprocess.addrmatch.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSBDM
 * @author yanghl
 * @since Feb 11, 2011
 * @version 1.0 
 */
public class XmlAnalysisBean {
	private static final Logger logger = LoggerFactory.getLogger(XmlAnalysisBean.class);

	/**
	 * 解析单条匹配返回的XML字符串
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> singleMatchAnalysis(String xmlStr) throws DocumentException {
		List<Map<String, String>> singleResult = new ArrayList<Map<String,String>>();
		Document document = DocumentHelper.parseText(xmlStr);
		List<Element> errList = document.selectNodes("//MatchError");
		logger.debug("=========" + errList.size());
		if(errList != null && errList.size() > 0){
			Element err = (Element) errList.get(0);
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", err.attributeValue("ErrorType"));
			map.put("scrp", err.attributeValue("ErrorScrp"));
			singleResult.add(map);
			return singleResult;
		}
		List<Element> retList = document.selectNodes("//SinMatchRes");
		for(Iterator<Element> iterator = retList.iterator(); iterator.hasNext();){
			Element ret = iterator.next();
			Map<String, String> map = new HashMap<String, String>();
			map.put("dict", ret.attributeValue("DictID"));
			map.put("post", ret.attributeValue("PostCode"));
			map.put("addrid", ret.attributeValue("AddrID"));
			map.put("diviname", ret.attributeValue("DiviName"));
			map.put("addrres", ret.attributeValue("AddrRes"));
			singleResult.add(map);
		}
        return singleResult;
    }
	
	/**
	 * 解析文件匹配返回的XML字符串
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static String fileMatchAnalysis(String xmlStr) throws DocumentException {
		Document document = DocumentHelper.parseText(xmlStr);
		List<Element> errList = document.selectNodes("//MatchError");
		logger.debug("=========" + errList.size());
		if(errList != null && errList.size() > 0){
			Element err = (Element) errList.get(0);
			return err.attributeValue("ErrorScrp");
		}
		List<Element> retList = document.selectNodes("//CreateTaskSuccess");
		Element ret = retList.get(0);
        return ret.attributeValue("TaskID");
    }
	
	/**
	 * 解析文件匹配状态返回的XML字符串
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> fileMatchStateAnalysis(String xmlStr) throws DocumentException {
		Document document = DocumentHelper.parseText(xmlStr);
		List<Element> errList = document.selectNodes("//MatchError");
		logger.debug("=========" + errList.size());
		if(errList != null && errList.size() > 0){
			Element err = (Element) errList.get(0);
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", err.attributeValue("ErrorType"));
			map.put("scrp", err.attributeValue("ErrorScrp"));
			return map;
		}
		List<Element> retList = document.selectNodes("//MatchState");
		Element ret = retList.get(0);
		Map<String, String> map = new HashMap<String, String>();
		map.put("lineCount", ret.attributeValue("MatchCount"));
		map.put("matchlen", ret.attributeValue("MatchLen"));
		map.put("srcfilelen", ret.attributeValue("SrcFileLen"));
		map.put("isend", ret.attributeValue("IsEnd"));
		return map;
    }
	
	/**
	 * 解析快速录入返回的XML字符串
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> fastInputAnalysis(String xmlStr) throws DocumentException {
		List<Map<String, String>> fastInputResult = new ArrayList<Map<String,String>>();
		Document document = DocumentHelper.parseText(xmlStr);
		List<Element> errList = document.selectNodes("//MatchError");
		logger.debug("=========" + errList.size());
		if(errList != null && errList.size() > 0){
			Element err = (Element) errList.get(0);
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", err.attributeValue("ErrorType"));
			map.put("scrp", err.attributeValue("ErrorScrp"));
			fastInputResult.add(map);
			return fastInputResult;
		}
		List<Element> retList = document.selectNodes("//AddressRes");
		for(Iterator<Element> iterator = retList.iterator(); iterator.hasNext();){
			Element ret = iterator.next();
			Map<String, String> map = new HashMap<String, String>();
			map.put("addrres", ret.attributeValue("AddressStr"));	//快速录入返回的地址
			map.put("nodeid", ret.attributeValue("FastNodeID"));	//结果对应的节点ID
			map.put("dict", ret.attributeValue("DictID"));			//字典ID(苏州市为320500)
			map.put("addrid", ret.attributeValue("AddrID"));		//地址ID
			fastInputResult.add(map);
		}
        return fastInputResult;
    }
	
	/**
	 * 获得一个节点的子节点的XML字符串
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, String>> getChildrenAnalysis(String xmlStr) throws DocumentException {
		List<Map<String, String>> getChildrenResult = new ArrayList<Map<String,String>>();
		Document document = DocumentHelper.parseText(xmlStr);
		List<Element> errList = document.selectNodes("//MatchError");
		logger.debug("=========" + errList.size());
		if(errList != null && errList.size() > 0){
			Element err = (Element) errList.get(0);
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", err.attributeValue("ErrorType"));
			map.put("scrp", err.attributeValue("ErrorScrp"));
			getChildrenResult.add(map);
			return getChildrenResult;
		}
		List<Element> retList = document.selectNodes("//NodeChild");
		for(Iterator<Element> iterator = retList.iterator(); iterator.hasNext();){
			Element ret = iterator.next();
			Map<String, String> map = new HashMap<String, String>();
			map.put("addrres", ret.attributeValue("NodeName"));		//子节点名
			map.put("nodeid", ret.attributeValue("FastNodeID"));	//子节点ID
			map.put("dict", ret.attributeValue("DictID"));			//子节点所在字典ID(苏州市为320500)
			map.put("addrid", ret.attributeValue("AddrID"));		//地址ID
			getChildrenResult.add(map);
		}
        return getChildrenResult;
    }
	
	/**
	 * 生成文件匹配规则
	 * @param xmlStr
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> generalMatchRule(String xmlStr) throws DocumentException{
		HashMap<String, String> map = new HashMap<String, String>();
		Document document = DocumentHelper.parseText(xmlStr);
		List<Element> retList = document.selectNodes("//FileMatch");
		for(Iterator<Element> iterator = retList.iterator(); iterator.hasNext();){
			Element ret = iterator.next();
			map.put("SrcFile", ret.attributeValue("SrcFile"));
			map.put("AimFile", ret.attributeValue("AimFile"));
			map.put("AddrColumn", ret.attributeValue("AddrColumn"));
			map.put("PostCodeColumn", ret.attributeValue("PostCodeColumn"));
			map.put("DivisionColumn", ret.attributeValue("DivisionColumn"));
			map.put("Separator", ret.attributeValue("Separator"));
			map.put("MatchType", ret.attributeValue("MatchType"));
			map.put("OutputLevel", ret.attributeValue("OutputLevel"));
			//map.put("MatchScore", ret.attributeValue("MatchScore"));
			map.put("Priority", ret.attributeValue("Priority"));
		}
		return map;
	}
	
	/**
	 * 获取节点值
	 * @param xmlStr
	 * @param nodeStr
	 * @return
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static String getValueByNodeStr(String xmlStr, String nodeStr) throws DocumentException {
		Document document = DocumentHelper.parseText(xmlStr);
		List<Element> retList = document.selectNodes("//FileMatch");
		Element ret = retList.get(0);
		return ret.attributeValue(nodeStr);
    }
}
