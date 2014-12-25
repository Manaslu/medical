package com.teamsun.thunderreport.velocity;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;


public class TempletBuilder {

	public static final String LIST_GRID_TEMPLET = "template.vm";

	private static TempletBuilder templetBuilder;

	public static TempletBuilder getInstancce() {
		if (templetBuilder == null) {
			templetBuilder = new TempletBuilder();
		}
		return templetBuilder;
	}

	VelocityEngine ve;

	public void init(String templetDir) {
		ve = new VelocityEngine();
		InputStream is = TempletBuilder.class.getClassLoader().getResourceAsStream("com/teamsun/thunderreport/velocity/ve.properties");
		Properties p = new Properties();

		try {
			p.load(is);
			System.out.println("templetDir----->:"+templetDir);
			p.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, templetDir);
			ve.init(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StringWriter getTempletText(String templetName,
			VelocityContextService contextService) {

		Template t = null;
		StringWriter writer = new StringWriter();
		try {
			t = ve.getTemplate(templetName);
			VelocityContext context = new VelocityContext();
			contextService.setVelocityContextProperties(context);
			t.merge(context, writer);
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer;

	}
	
	public String getFusionChartLayout(String templetName,
			VelocityContextService contextService) {
		Template t = null;
		try {
			t = ve.getTemplate(templetName);
			VelocityContext context = new VelocityContext();
			contextService.setVelocityContextProperties(context);
			StringWriter writer = new StringWriter();
			t.merge(context, writer);
			return new String(writer.toString());
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		} catch (ParseErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 */
	public static interface VelocityContextService {

		public void setVelocityContextProperties(VelocityContext context);

	}
}
