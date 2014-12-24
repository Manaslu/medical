package com.idp.pub.service.entity;

public interface ILog {
	public String userId();// 用户Id

	public String remark();// 其它信息

	public String url();// 访问的资源地址

	public String ip();// 访问用户的 ip

	public String operator();// 执行操作
}
