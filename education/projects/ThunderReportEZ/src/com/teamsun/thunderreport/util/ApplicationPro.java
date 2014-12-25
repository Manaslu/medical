package com.teamsun.thunderreport.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationPro {
	
	private static Properties properties;

	public static void load(String filename) {

		if (properties == null) {
			properties = new Properties();
			try {
				properties.load(new FileInputStream(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private String propertiesName;

	public String getPropertiesName() {
		return propertiesName;
	}

	public void setPropertiesName(String propertiesName) {
		this.propertiesName = propertiesName;
	}

	public static String getValue(String key) {
		return properties.getProperty(key);
	}

}
