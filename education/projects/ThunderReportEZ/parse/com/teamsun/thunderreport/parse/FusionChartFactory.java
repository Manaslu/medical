package com.teamsun.thunderreport.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 针对fusion chart折线图编写
 * 
 */
public class FusionChartFactory {

	private Map finallycharts = new HashMap();

	private Map finallyHtml = new HashMap();
	
	private String fusionchartLayout;

	public Collection getFusionChartExs() {
		return this.finallycharts.values();
	}

	public FusionChartFactory(List elements, Map conds, String filename) {
		for (int i = 0; i < elements.size(); i++) {
			Element element = (Element) elements.get(i);
			String chartType=element.attributeValue("type");
			String id = element.attributeValue("id");
			FusionChartEx chartEx=null;;
			if(chartType.equals("line")){
				chartEx = new FusionLineChartEx(element, conds,
					filename);
			}
			if(chartType.equals("pie")){
				chartEx = new FusionPieChartEx(element, conds,
					filename);
			}
			chartEx.setFilename(filename);
			this.finallycharts.put(id, chartEx);
		}
	}

	public FusionChartEx getFusionChartEx(String chartid) {
		return (FusionChartEx) this.finallycharts.get(chartid);
	}

	public String getLayout(String chartid){
		return null;
	}


	public static void main(String[] args) throws FileNotFoundException,
			DocumentException {

		SAXReader xmlReader = new SAXReader();
		Document d = xmlReader.read(new FileInputStream(new File(
				"c:\\rptxml\\fusioncharttest.xml")));
		// System.out.println(d.asXML());
		final List data = new ArrayList(300);
		for (int i = 0; i < 100; i++) {
			Map map = new HashMap(4);
			Calendar cal = java.util.Calendar.getInstance();
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);

			map.put("U0", new Long(cal.getTime().getTime()));
			map.put("U1", "华胜天成");
			map.put("U2", new Integer(i));
			map.put("U3", new Integer(i + 2));

			data.add(map);
		}

		for (int i = 0; i < 100; i++) {
			Map map = new HashMap(4);
			Calendar cal = java.util.Calendar.getInstance();
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);

			map.put("U0", new Long(cal.getTime().getTime()));
			map.put("U1", "中国邮政");
			map.put("U2", new Integer(i - 3));
			map.put("U3", new Integer(i + 6));

			data.add(map);
		}

		for (int i = 0; i < 100; i++) {
			Map map = new HashMap(4);
			Calendar cal = java.util.Calendar.getInstance();
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);

			map.put("U0", new Long(cal.getTime().getTime()));
			map.put("U1", "新疆电信");
			map.put("U2", new Integer(i - 8));
			map.put("U3", new Integer(i + 12));

			data.add(map);
		}

		List l = d.selectNodes("//Report/DataUI/Fusionlinechart");
		System.out.println(l.size());

		Map m = new HashMap();
		m.put("Latn_id", "23,33,44");
		m.put("Bil_Date", "434343");
		m.put("Latn_id_disp", "A,B,C");

		FusionChartFactory fusionLineChart = new FusionChartFactory(l, m, "");
		Collection c = fusionLineChart.getFusionChartExs();
		System.out.println(c.size());

		FusionLineChartEx ex = (FusionLineChartEx) c.iterator().next();
		System.out.println(ex.selected());
		System.out.println(ex.selectConditionDisplayName());
		System.out.println(ex.selectConditionValueName());
		System.out.println(ex.selectFieldDisplays().length);

		String txt = ((FusionLineChartEx) c.iterator().next())
				.formateFusionChartXml(new DataService() {

					public List doData(Sql idSql) {
						return data;
					}

				});
		System.out.println(txt);

		// fusionLineChart.formateFusionchartXml(new DataService() {
		//
		// public List doData(Sql idSql) {
		//
		// return data;
		//
		// }
		//
		// });
	}

	public Map getFinallyHtml() {
		return finallyHtml;
	}

	public String getFusionchartLayout() {
		return fusionchartLayout;
	}

	public void setFusionchartLayout(String fusionchartLayout) {
		this.fusionchartLayout = fusionchartLayout;
	}

}
