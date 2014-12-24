package com.idap.dataprocess.addrmatch.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idap.dataprocess.addrmatch.utils.XmlAnalysisBean;
import com.idap.dataprocess.addrmatch.utils.XmlReadBean;

/**
 * JSBDM
 * 
 * @author yanghl
 * @since Feb 11, 2011
 * @version 1.0
 */
public class MatchStateHandler extends DefaultMatchHandler {
	private static final Logger logger = LoggerFactory
			.getLogger(MatchStateHandler.class);
	private String taskId = "";

	public MatchStateHandler(String taskId) {
		this.taskId = taskId;
	}

	public void sendMessage() {

		try {
			this.addresMatchServer.sendMessage(XmlReadBean
					.generalFileMatchStateXml(taskId));
		} catch (Exception e) {
			logger.error("发送文件匹配状态消息到地址匹配服务器失败...");
		}
	}

	public Map<String, String> getMessage() {
		BufferedReader messageBuffer = null;
		try {
			messageBuffer = this.addresMatchServer.getMessage();
			StringBuffer lineBuffer = new StringBuffer();
			String line;
			while (((line = messageBuffer.readLine()) != null)
					&& (line.trim().length() != 0)) {
				lineBuffer.append(line.trim());
				if ("</root>".equals(line.trim())) {
					break;
				}
			}
			logger.info(lineBuffer.toString());
			return XmlAnalysisBean
					.fileMatchStateAnalysis(lineBuffer.toString());
		} catch (Exception e) {
			logger.error("", e);
			return null;
		} finally {
			try {
				messageBuffer.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}
}
