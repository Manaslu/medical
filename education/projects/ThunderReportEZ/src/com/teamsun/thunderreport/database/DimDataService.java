package com.teamsun.thunderreport.database;

public interface DimDataService {

	/**
	 * 格式化数据 [0]=ID [1]=NAME; id是实际数据，name是显示数据，可以为空，但数组长度必须保持一致
	 * 
	 * @return
	 */
	public String[][] loadData();

	/**
	 * 配置文件中写的 ref_name
	 * 
	 * @return
	 */
	public String getRef_name();

}
