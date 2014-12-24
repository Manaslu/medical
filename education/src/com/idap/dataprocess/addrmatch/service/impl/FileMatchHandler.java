package com.idap.dataprocess.addrmatch.service.impl;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idap.dataprocess.addrmatch.MatchException;
import com.idap.dataprocess.addrmatch.entity.MatchParameters;
import com.idap.dataprocess.addrmatch.utils.XmlAnalysisBean;
import com.idap.dataprocess.addrmatch.utils.XmlReadBean;

public class FileMatchHandler extends DefaultMatchHandler {

	private static final Logger logger = LoggerFactory
			.getLogger(FileMatchHandler.class);
	private MatchParameters params;

	public FileMatchHandler(MatchParameters params) {
		this.params = params;
	}

	@Override
	public void sendMessage() throws MatchException {
		try {
			addresMatchServer.sendMessage(XmlReadBean
					.generalFileMatchXml(params));
		} catch (Exception e) {
			logger.error("发送文件待匹配消息到地址匹配服务器失败...");
			throw new MatchException(e.getMessage());
		}
	}

	@Override
	public Object getMessage() throws MatchException {
		BufferedReader messageBuffer = null;
		try {
			messageBuffer = addresMatchServer.getMessage();
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
			return XmlAnalysisBean.fileMatchAnalysis(lineBuffer.toString());
		} catch (Exception e) {
			logger.error("", e);
			throw new MatchException(e.getMessage());
		} finally {
			try {
				messageBuffer.close();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}

	public String toString() {
		return XmlReadBean.generalFileMatchXml(this.params);
	}

	

}
