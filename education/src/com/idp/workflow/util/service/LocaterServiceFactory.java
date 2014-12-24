package com.idp.workflow.util.service;

import com.idp.workflow.itf.service.ILocater;

/**
 * 获取服务创建者
 * 
 * @author panfei
 * 
 */
public class LocaterServiceFactory {

	private static ILocater locater;

	public static synchronized void setInstance(ILocater temp) {
		locater = temp;
	}

	public static ILocater GetInstance() {
		return locater;
	}
}
