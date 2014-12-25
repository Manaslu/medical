package com.teamsun.thunderreport.thread.support;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

import com.teamsun.thunderreport.core.XmlParser;

public class ConfigurationFileServiceImpl implements ConfigurationFileService {

	private String dir = ".";
	private String preGenDir=".";

	public String getXmlPath(){
		
		String classpath = this.getClass().getClassLoader().getResource("/").getPath();
		String WEB_INF = classpath.substring(0, classpath.indexOf("WEB-INF")+7);
		String realPath =WEB_INF + System.getProperty("file.separator") + "rptxml"  ;
		
		return realPath;
	}
	
	public File[] listAllConfigurationFiles() {
		File dir = new File(this.getXmlPath());
		File[] results = dir.listFiles(new FileFilter() {

			public boolean accept(File pathname) {
				return pathname.getName().endsWith("xml");
			}

		});
		return results;
	}
	
	public File[] listAllPreConfigurationFiles() {
		File dir = new File(this.preGenDir);
		File[] results = dir.listFiles(new FileFilter() {

			public boolean accept(File pathname) {
				return pathname.getName().endsWith("xml");
			}

		});
		return results;
	}


	public File getFileByName(final String filename) {
		File dir = new File(this.getXmlPath());
		System.out.println("报表配置文件名-------------->1:"+this.getXmlPath()+System.getProperty("file.separator")+filename);
		File result = new File(this.getXmlPath()+System.getProperty("file.separator")+filename);
		
		return result;
		
 
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public File getFileByReportId(String reportId) {

		File[] fs = this.listAllConfigurationFiles();
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			XmlParser xp = new XmlParser(f.getAbsolutePath());
			if (xp.getId().equals(reportId)) {
				return f;
			}
		}
		return null;
	}
	
	public File getPreFileByReportId(String reportId) {

		File[] fs = this.listAllPreConfigurationFiles();
		for (int i = 0; i < fs.length; i++) {
			File f = fs[i];
			XmlParser xp = new XmlParser(f.getAbsolutePath());
			if (xp.getId().equals(reportId)) {
				return f;
			}
		}
		return null;
	}	

	public File[] listAllConfigurationFiles(FilenameFilter filter) {
		if (filter == null)
			return this.listAllConfigurationFiles();

		File dir = new File(this.getXmlPath());
		File[] results = dir.listFiles(filter);
		return results;

	}
	public File[] listAllPreConfigurationFiles(FilenameFilter filter) {
		if (filter == null)
			return this.listAllPreConfigurationFiles();

		File dir = new File(this.getXmlPath());
		File[] results = dir.listFiles(filter);
		return results;

	}

	public String getPreGenDir() {
		return preGenDir;
	}

	public void setPreGenDir(String preGenDir) {
		this.preGenDir = preGenDir;
	}

}
