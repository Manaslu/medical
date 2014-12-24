package com.idap.dataprocess.addrmatch.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.idap.dataprocess.addrmatch.MatchException;
import com.idap.dataprocess.addrmatch.service.MatchHandler;

public abstract class DefaultMatchHandler implements MatchHandler {
	private static final Log logger = LogFactory
			.getLog(DefaultMatchHandler.class);

	protected AddresMatchServer addresMatchServer;

	@Override
	public Object match() throws MatchException {
		try {
			if (createConnection()) {
				sendMessage();
				return getMessage();
			}
		} catch (Exception ex) {
			throw new MatchException("连接地址匹配服务器失败");
		} finally {
			addresMatchServer.shutDownConnection();
			logger.info("CLOSE连接地址匹配服务器成功...");
		}
		return null;
	}

	@Override
	public boolean createConnection()throws MatchException {
		try {
			this.addresMatchServer.connection();
			logger.debug("连接地址匹配服务器成功...");
			return true;
		} catch (MatchException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public abstract void sendMessage() throws MatchException;

	@Override
	public abstract Object getMessage() throws MatchException;

	@Override
	public void shutDownConnection() {
		addresMatchServer.shutDownConnection();
	}

	@Override
	public void setServer(AddresMatchServer server) {
		this.addresMatchServer = server;
	}
}
