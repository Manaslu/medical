package com.teamsun.thunderreport.core;

import java.util.List;

import com.teamsun.thunderreport.parse.Sql;

/**
 * 数据展示服务，接收客户数据，生成其期望的样式
 * 
 */
public interface RepresentContextService {

	public String formatContext(List data,List allList,String[] paramValues);

	public Sql getSql();

	/**
	 * 模板文件名 放在这里不合适，待修改 TODO
	 * 
	 * @return
	 */
	public String getTemplateVm();
}
