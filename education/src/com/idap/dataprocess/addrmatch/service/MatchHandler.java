package com.idap.dataprocess.addrmatch.service;

import com.idap.dataprocess.addrmatch.MatchException;
import com.idap.dataprocess.addrmatch.service.impl.AddresMatchServer;

public interface MatchHandler {
	/**
	 * 匹配
	 * 
	 * @return
	 */

	public Object match() throws MatchException;
	/**
	 * 创建连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean createConnection()throws MatchException;

	/**
	 * 发生消息
	 * 
	 * @throws Exception
	 */
	public void sendMessage() throws MatchException;

	/**
	 * 接收消息
	 * 
	 * @return
	 * @throws Exception
	 */
	public Object getMessage() throws MatchException;

	/**
	 * 关闭连接
	 */
	public void shutDownConnection();

	/**
	 * 设置服务器
	 * 
	 * @param ip
	 * @param port
	 */
	public void setServer(AddresMatchServer server);
}
