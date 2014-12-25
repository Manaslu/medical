package com.teamsun.thunderreport.javascript;

/**
 * 
 * 调哟js函数
 * 
 */
public interface CallFunctionService {

	/**
	 * 函数名
	 * 
	 * @return
	 */
	public String getFunctionName();

	/**
	 * 函数参数
	 * 
	 * @return
	 */
	public Object[] getFunctionParams();

}
