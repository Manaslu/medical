package com.teamsun.thunderreport.thread;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class XmlSumFilter implements javax.servlet.Filter {

	Properties properties;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		String file = request.getParameter("file");
		String sum = request.getParameter("sum");
		String index = request.getParameter("index");

		CachedResponseWrapper crw = new CachedResponseWrapper(
				(HttpServletResponse) response);
		filterChain.doFilter(request, crw);

		if (crw.getStatus() == HttpServletResponse.SC_OK
				&& this.properties.containsKey(file) && sum != null
				&& sum.equals("true") && index.equals("2")) {

			org.dom4j.io.SAXReader r = new SAXReader();
			try {
				Document d = r.read(new ByteArrayInputStream(crw
						.getResponseData()));
				String numberSuffix;
				if(d.selectSingleNode("/chart/@numberSuffix")!=null)
					numberSuffix  = d.selectSingleNode("/chart/@numberSuffix").getText();
				else 
					numberSuffix="";
				List els = d.selectNodes("/chart//dataset");
				List newsets = new ArrayList();
				
				for (int i = 0; i < els.size(); i++) {
					Element e = (Element) els.get(i);
					List setls = e.selectNodes("set");
					for (int j = 0; j < setls.size(); j++) {
						Element el = (Element) setls.get(j);
						String value = el.attributeValue("value");
						if(value!=null){
						 double v = Double.parseDouble(value);
					
						 if (i > 0&& j<=(newsets.size()-1)) {
							Double nl = (Double)newsets.get(j);
							nl = new Double(nl.doubleValue() + v);
						//	DecimalFormat df=new DecimalFormat("#.00"); 
							newsets.remove(j);
							newsets.add(j,nl);
						 } else {
							 //DecimalFormat df=new DecimalFormat("#.00"); 
							 newsets.add(new Double(v));
							// System.out.println("======="+df.format(new Double(v)));
						 }
						}
					}
					
					d.getRootElement().remove(e);
				}
				
				for (int i = 0; i < newsets.size(); i++) {
					Double nl = (Double)newsets.get(i);
					DecimalFormat df=new DecimalFormat("0.00"); 
					String n2=df.format(nl.doubleValue());
					newsets.remove(i);
					newsets.add(i,n2);
				}

				Element dataset = d.getRootElement().addElement("dataset");
				dataset.addAttribute("seriesName", "合计");
				for (int i = 0; i < newsets.size(); i++) {
					Element se = dataset.addElement("set");
					se.addAttribute("value", newsets.get(i).toString());
					se.addAttribute("toolText", "合计 "
							+ newsets.get(i).toString() + numberSuffix);
				}
				
				response.setCharacterEncoding("utf-8");
				response.getWriter().write(d.asXML());
				System.out.println(d.asXML());
			} catch (DocumentException e) {
				e.printStackTrace();
			}

		} else {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(new String(crw.getResponseData(),"utf-8"));
		}

	}

	public void init(FilterConfig config) throws ServletException {

		String filename = config.getInitParameter("filename");
		//tomcat 版本
		//filename = config.getServletContext().getRealPath("WEB-INF") + System.getProperty("file.separator") + filename;
		
		//weblogic 版本
		String classpath = this.getClass().getClassLoader().getResource("/").getPath();
		String WEB_INF = classpath.substring(0, classpath.indexOf("WEB-INF")+7);
		filename = WEB_INF+System.getProperty("file.separator")+filename;
		
		properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(filename)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		org.dom4j.io.SAXReader r = new SAXReader();
		try {
			Document d = r.read(new File("C:\\test4.xml"));
			String numberSuffix = d.selectSingleNode("/chart/@numberSuffix")
					.getText();
			List els = d.selectNodes("/chart//dataset");
			List newsets = new ArrayList();

			for (int i = 0; i < els.size(); i++) {
				Element e = (Element) els.get(i);
				List setls = e.selectNodes("set");

				for (int j = 0; j < setls.size(); j++) {
					Element el = (Element) setls.get(j);
					String value = el.attributeValue("value");
					double v = Double.parseDouble(value);
					if (newsets.size() > j) {
						Double nl = (Double) newsets.get(j);
						nl = new Double(nl.doubleValue() + v);
						newsets.remove(j);
						newsets.add(j, nl);
					} else {
						newsets.add(new Double(v));
					}
				}
				d.getRootElement().remove(e);
			}
			Element dataset = d.getRootElement().addElement("dataset");
			dataset.addAttribute("seriesName", "合计");
			for (int i = 0; i < newsets.size(); i++) {
				Element se = dataset.addElement("set");
				se.addAttribute("value", newsets.get(i).toString());
				se.addAttribute("toolText", "合计 " + newsets.get(i).toString()
						+ numberSuffix);
			}

			FileOutputStream fos = new FileOutputStream("C:\\xxxx.xml");
			fos.write(d.asXML().getBytes());
			fos.flush();
			fos.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
