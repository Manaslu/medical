package com.teamsun.thunderreport.thread.support;

import java.io.File;
import java.io.FilenameFilter;

public interface ConfigurationFileService {

	/**
	 * 获取配置文件信息
	 * 
	 * @return
	 */
	public File[] listAllConfigurationFiles(FilenameFilter filter);

	/**
	 * 获取全部配置文件信息
	 * 
	 * @return
	 */
	public File[] listAllConfigurationFiles();

	/**
	 * 根据给定的文件得到配置文件信息
	 * 
	 * 
	 * @param filename
	 * @return
	 */
	public File getFileByName(String filename);

	/**
	 * 根据给定的报表id获取配置文件
	 * 
	 * @param reportId
	 * @return
	 */
	public File getFileByReportId(String reportId);
	
	/**
	 * 根据给定的报表id获取预生成的报表配置文件
	 * 
	 * @param reportId
	 * @return
	 */	
	public File getPreFileByReportId(String reportId);
	
	/**
	 * 获取预生成配置文件信息
	 * 
	 * @return
	 */
	public File[] listAllPreConfigurationFiles(FilenameFilter filter);

}
