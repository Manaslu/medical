package com.teamsun.framework.util;

import java.io.IOException;
import java.net.URL;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.springframework.core.io.Resource;

public class SqlReader
{
	private Resource resource;

	public Resource getResource()
	{
		return resource;
	}

	public void setResource(Resource resource)
	{
		this.resource = resource;
	}

	public Configuration getSqlConfig()
	{
		ConfigurationFactory factory = new ConfigurationFactory();
		Configuration config=null;
		try
		{
			URL url = getResource().getURL();
			factory.setConfigurationURL(url);
			config = factory.getConfiguration();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (ConfigurationException e)
		{
			e.printStackTrace();
		}
		return config;
	}

	public SqlReader()
	{

	}
	public static void main(String[] args)
	{

	}
}
