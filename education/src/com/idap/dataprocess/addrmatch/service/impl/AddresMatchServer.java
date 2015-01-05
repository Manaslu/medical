package com.idap.dataprocess.addrmatch.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.idap.dataprocess.addrmatch.MatchException;
 
@Service("addresMatchServer")
public class AddresMatchServer {
	private Log logger = LogFactory.getLog(getClass());

	@Resource(name = "matchConstants")
	private MatchConstants matchConstants;

	private Socket addrSocket;

	private OutputStreamWriter forMatchOutWriter;

	private BufferedReader resultBufferReader;

	/**
	 * Create Socket Connection
	 * 
	 * @throws Exception
	 *             exception
	 */
	public void connection() throws MatchException {
		try {
			logger.info("地址匹配服务器连接:{" + this.matchConstants.getAddrSocketIp()
					+ ":" + this.matchConstants.getAddrSocketPort() + "}");
			addrSocket = new Socket(matchConstants.getAddrSocketIp(),
					matchConstants.getAddrSocketPort());
		} catch (Exception e) {
			logger.debug("地址匹配服务器连接失败");
			throw new MatchException("地址匹配服务器连接失败");
		}
	}

	/**
	 * Send Message To Server
	 * 
	 * @param sendMessage
	 * @throws Exception
	 */
	public void sendMessage(String sendMessage) throws MatchException {
		try {
			forMatchOutWriter = new OutputStreamWriter(
					addrSocket.getOutputStream());
			forMatchOutWriter.write(sendMessage);
			forMatchOutWriter.flush();
		} catch (Exception e) {
			throw new MatchException("发送协议信息失败");
		}
	}

	/**
	 * Get Message From Server
	 * 
	 * @return
	 * @throws Exception
	 */
	public BufferedReader getMessage() throws MatchException {
		try {
			return new BufferedReader(new InputStreamReader(
					addrSocket.getInputStream()));
		} catch (Exception e) {
			throw new MatchException("接收消息失败");
		}
	}

	/**
	 * ShutDown Connection
	 */
	public void shutDownConnection() {
		try {
			if (forMatchOutWriter != null)
				forMatchOutWriter.close();
			if (resultBufferReader != null)
				resultBufferReader.close();
			if (addrSocket != null)
				addrSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MatchConstants getMatchConstants() {
		return matchConstants;
	}
}
