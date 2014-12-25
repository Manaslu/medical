package com.teamsun.thunderreport.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.teamsun.thunderreport.core.XmlParser;

/**
 * 缓存可以考虑用别的缓存框架替换
 * 
 */
public class DimDataFactory {

	private static final Log log = LogFactory.getLog(DimDataFactory.class);

	private static final Map cache = java.util.Collections
			.synchronizedMap(new HashMap());

	private List dimObjects = new ArrayList();
	
	private int dateDimLength=7;
	private String minDate="";
	private String maxDate="";

	public List getDimObjects() {
		return dimObjects;
	}

	public void setDimObjects(List dimObjects) {
		this.dimObjects = dimObjects;
	}

	/**
	 * 根据refname取得所有的操作值
	 * 
	 * @param ref_name
	 * @return
	 */
	public String[] getIds(String ref_name) {
		if (cache.containsKey(ref_name))
			return ((String[][]) cache.get(ref_name))[0];
		else
			return new String[] {};
	}

	/**
	 * 根据refname取得所有的显示值
	 * 
	 * @param ref_name
	 * @return
	 */
	public String[] getNames(String ref_name) {
		if (cache.containsKey(ref_name))
			return ((String[][]) cache.get(ref_name))[1];
		else
			return new String[] {};
	}

	public void loadData() {
//		System.out.println("weblogic's classpath is: /home/weblogic/Oracle/Middleware/user_projects/domains/myDomain/ ,shit!!");
//		String classpath=DimDataFactory.class.getResource("/").getPath();
//		int index=classpath.toLowerCase().indexOf("classes");
//		//String realPath=classpath.substring(1,index);
//		System.out.println("classpath--->"+classpath);
//		String realPath = classpath.substring(0,index);//-->unix格式
//		XmlParser parser = new XmlParser(realPath+"syscache.xml");
//		System.out.println("realPath--->"+realPath);
//		
//		this.dimObjects = parser.getDimObjects();
//		for (int i = 0; i < this.dimObjects.size(); i++) {
//			DimDataService dmo = (DimDataService) this.dimObjects.get(i);
//			long time = System.currentTimeMillis();
//			cache.put(dmo.getRef_name(), dmo.loadData());
//			log.info("加载代码数据：" + dmo.getRef_name() + " 耗时："
//					+ (System.currentTimeMillis() - time));
//		}
//		initDateDim();
	}
	
	/*初始化日期维度*/
	public void initDateDim(){
		String[][] date=new String[2][dateDimLength];
		for (int i = 1; i <= this.dateDimLength; i++) {	
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, i-dateDimLength-1);
			Date d = c.getTime();
			String dateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(d);
			date[0][i-1]=dateStr;
			date[1][i-1]=dateStr;
		}
		this.minDate=date[0][0];
		this.maxDate=date[0][this.dateDimLength-1];
		cache.put("begindate", date);
		cache.put("enddate", date.clone());
	}
	
	
	public static void clear() {
		cache.clear();
	}

	public int getDateDimLength() {
		return dateDimLength;
	}

	public void setDateDimLength(int dateDimLength) {
		this.dateDimLength = dateDimLength;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

}
