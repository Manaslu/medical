package com.teamsun.thunderreport.parse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.teamsun.framework.util.RegExUtil;

public class XmlParser {

	public Document reportXml;

	// xml存放的路径

	public String path;

	public String id;

	public String title;

	public String filename;

	public String getId() {
		Attribute attr = (Attribute) reportXml.selectSingleNode("//Report/@id");
		if (attr == null) {
			return "";
		} else {
			return attr.getValue();
		}
	}

	public String getTitle() {
		Attribute attr = (Attribute) reportXml
				.selectSingleNode("//Report/@title");
		if (attr == null) {
			return "";
		} else {
			return attr.getValue();
		}
	}

	/**
	 * 构造函数
	 * 
	 * 
	 * 
	 * 
	 * @param xmlFilename
	 */
	public XmlParser(String xmlFilename) {
		try {
			reportXml = this.loadXML(xmlFilename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造函数
	 * 
	 * 
	 * 
	 * 
	 * @param xmlFilename
	 */
	public XmlParser(String xmlFilename, String filename) {
		try {
			reportXml = this.loadXML(xmlFilename);
			this.filename = filename;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public List getHelpSpace() {
		return null;
	}

	/**
	 * 获得xml中的ListGrid
	 * 
	 * @return
	 */
	public ListGrid[] getListGrid() {

		List listGrids = reportXml.selectNodes("//ListGrid");
		ListGrid[] returnVal;
		if (listGrids.size() == 0) {
			return null;
		} else {
			returnVal = new ListGrid[listGrids.size()];
		}

		int j = 0;
		for (Iterator i = listGrids.iterator(); i.hasNext();) {
			Element tempGrid = (Element) i.next();
			returnVal[j] = new ListGrid();
			returnVal[j].setId(getAttrValue(tempGrid, "id"));
			Element sql = tempGrid.element("Sql");
			Sql oSql = new Sql();
			oSql.setDb(getAttrValue(sql, "db"));
			oSql.setId(getAttrValue(sql, "id"));
			oSql.setSqlText(getElementText(sql));
			returnVal[j].setSql(oSql);
			// 取reportheader
			Element reportHeader = tempGrid.element("ReportHeader");
			if (reportHeader != null) {
				returnVal[j].setReportHeaderOutType(getAttrValue(reportHeader,
						"outputtype"));
				// returnVal[j].setReportHeaderOutValue(RegExUtil.replaceAllUtil(
				// reportHeader.getText(), "\\s", ""));
				returnVal[j]
						.setReportHeaderOutValue(reportHeader.getTextTrim());
			}

			// 处理reportbody中的内容
			Element reportBody = tempGrid.element("ReportBody");
			Element funcTitle = reportBody.element("FuncTitle");

			if (funcTitle != null) {
				returnVal[j].setReportBodyFunTitleType(getAttrValue(funcTitle,
						"outputtype"));
				returnVal[j]
						.setReportBodyFunTitleValue(getElementText(funcTitle));
			}

			// 处理columns对象
			Element columns = reportBody.element("Columns");
			List column = columns.elements();
			Column[] columnsArray = new Column[column.size()];
			int k = 0;
			for (Iterator t = column.iterator(); t.hasNext();) {
				columnsArray[k] = new Column();
				Element tempColumn = (Element) t.next();
				columnsArray[k].setColumValue(getElementText(tempColumn));
				columnsArray[k].setGroup(getAttrValue(tempColumn, "group"));
				columnsArray[k].setName(getAttrValue(tempColumn, "name"));
				columnsArray[k].setFormat(getAttrValue(tempColumn, "format"));
				columnsArray[k].setType(getAttrValue(tempColumn, "type"));
				columnsArray[k].setCode(getAttrValue(tempColumn, "code"));
				columnsArray[k].setOutputtype(getAttrValue(tempColumn,
						"outputtype"));
				columnsArray[k].setSumname(getAttrValue(tempColumn, "sumname"));
				columnsArray[k].setSummary(getAttrValue(tempColumn, "summary"));
				columnsArray[k].setSumby(getAttrValue(tempColumn, "sumby"));
				columnsArray[k].setSumpos(getAttrValue(tempColumn, "sumpos"));
				columnsArray[k].setHidden(getAttrValue(tempColumn, "hidden"));
				k++;
			}
			returnVal[j].setColumns(columnsArray);

			// 处理reportfooter
			Element reportFooter = tempGrid.element("ReportFooter");
			if (reportFooter != null) {
				returnVal[j].setReportFooterOutType(getAttrValue(reportFooter,
						"outputtype"));
				returnVal[j].setReportFooterValue(getElementText(reportFooter));
			}

			j++;
		}

		return returnVal;
	}

	public FusionLineChart getFusionLineChart(Map conds) {

		List elements = reportXml
				.selectNodes("//Report/DataUI/Fusionchart");
		FusionLineChart fusionLineChart = new FusionLineChart(elements, conds,
				this.filename);
		fusionLineChart.setFusionchartLayout(this.getFusionChartLayoutEx());
		return fusionLineChart;
	}
	
	public FusionChartFactory getFusionChartFactory(Map conds) {

		List elements = reportXml
				.selectNodes("//Report/DataUI/Fusionchart");
		FusionChartFactory fusionChartFactory = new FusionChartFactory(elements, conds,
				this.filename);
		fusionChartFactory.setFusionchartLayout(this.getFusionChartLayoutEx());
		return fusionChartFactory;
	}
	

	/**
	 * 获得xml中的Condition
	 * 
	 * @return
	 */
	public Condition[] getCondition() {

		List listCons = reportXml.selectNodes("//Cond");
		Condition[] returnVal;
		if (listCons.size() == 0) {
			return null;
		} else {
			returnVal = new Condition[listCons.size()];
		}

		int j = 0;
		for (Iterator i = listCons.iterator(); i.hasNext();) {
			returnVal[j] = new Condition();
			Element tempCondition = (Element) i.next();
			returnVal[j].setAuthflag(getAttrValue(tempCondition, "authFlag"));
			returnVal[j].setControlname(getAttrValue(tempCondition,
					"controlname"));
			returnVal[j].setId(getAttrValue(tempCondition, "id"));
			returnVal[j].setParaname(getAttrValue(tempCondition, "paraname"));
			returnVal[j].setTitle(getAttrValue(tempCondition, "title"));
			returnVal[j].setWrap(getAttrValue(tempCondition, "wrap"));
			returnVal[j].setLevel(getAttrValue(tempCondition, "level"));
			returnVal[j].setRefkey(getAttrValue(tempCondition, "refkey"));
			returnVal[j].setReftable(getAttrValue(tempCondition, "reftable"));
			returnVal[j].setReftable(getAttrValue(tempCondition, "needvalue"));
			j++;
		}

		return returnVal;
	}

	/**
	 * 获得element的某个属性值
	 * 
	 * 
	 * 
	 * 
	 * @param element
	 * @param attrName
	 * @return
	 */
	public String getAttrValue(Element element, String attrName) {
		if (element == null) {
			return null;
		}
		Attribute attr = (Attribute) element.attribute(attrName);
		if (attr != null) {
			return RegExUtil.replaceAllUtil(attr.getValue().trim(), "\\s", " ");
		}
		return null;
	}

	/**
	 * 获得element的某个属性值
	 * 
	 * 
	 * 
	 * @param element
	 * @param attrName
	 * @return
	 */
	public String getElementText(Element element) {
		return element == null ? null : RegExUtil.replaceAllUtil(element
				.getText(), "\\s", " ");
	}

	public CrossGridEx[] getCrossGridEx() {
		List listCrossGrid = reportXml.selectNodes("//CrossGridEx");
		CrossGridEx[] returnVal;
		if (listCrossGrid.size() == 0) {
			return null;
		} else {
			returnVal = new CrossGridEx[listCrossGrid.size()];
		}

		int j = 0;
		for (Iterator i = listCrossGrid.iterator(); i.hasNext();) {
			returnVal[j] = new CrossGridEx();
			Element tempCrossGrid = (Element) i.next();
			returnVal[j].setId(getAttrValue(tempCrossGrid, "id"));
			Element sql = tempCrossGrid.element("Sql");
			Sql oSql = new Sql();
			oSql.setDb(getAttrValue(sql, "db"));
			oSql.setId(getAttrValue(sql, "id"));
			oSql.setSqlText(getElementText(sql));
			returnVal[j].setSql(oSql);
			// 处理reportHeader
			Element reportHeader = tempCrossGrid.element("ReportHeader");
			if (reportHeader != null) {
				returnVal[j].setReportHeaderOutType(getAttrValue(reportHeader,
						"outputtype"));
				returnVal[j].setReportHeaderOutValue(RegExUtil.replaceAllUtil(
						reportHeader.getText(), "\\s", ""));
			}

			Element reportBody = tempCrossGrid.element("ReportBody");

			Element rowDim = reportBody.element("RowDims");
			if (rowDim != null) {
				List tempRowDim = rowDim.elements();
				Dim[] dim = null;
				if (tempRowDim != null) {
					dim = new Dim[tempRowDim.size()];
					int d1 = 0;
					for (Iterator d2 = tempRowDim.iterator(); d2.hasNext();) {
						Element rD = (Element) d2.next();
						dim[d1] = new Dim();
						dim[d1].setDimValue(getElementText(rD));
						dim[d1].setCode(getAttrValue(rD, "code"));
						dim[d1].setName(getAttrValue(rD, "name"));
						dim[d1].setSumname(getAttrValue(rD, "sumname"));
						dim[d1].setSummary(getAttrValue(rD, "summary"));
						dim[d1].setSumby(getAttrValue(rD, "sumby"));
						dim[d1].setSumpos(getAttrValue(rD, "sumpos"));
						d1++;
					}
				}
				returnVal[j].setRowDim(dim);
			}

			Element colDim = reportBody.element("ColDims");

			if (colDim != null) {
				List tempColDim = colDim.elements();
				Dim[] dim = null;
				if (tempColDim != null) {
					dim = new Dim[tempColDim.size()];
					int d1 = 0;
					for (Iterator d2 = tempColDim.iterator(); d2.hasNext();) {
						Element colElement = (Element) d2.next();
						dim[d1] = new Dim();
						dim[d1].setCode(getAttrValue(colElement, "code"));
						dim[d1].setDimValue(getElementText(colElement));
						dim[d1].setName(getAttrValue(colElement, "name"));
						dim[d1].setSumname(getAttrValue(colElement, "sumname"));
						dim[d1].setSummary(getAttrValue(colElement, "summary"));
						dim[d1].setSumby(getAttrValue(colElement, "sumby"));
						dim[d1].setSumpos(getAttrValue(colElement, "sumpos"));
						d1++;
					}
				}
				returnVal[j].setColDim(dim);
			}

			Element measures = reportBody.element("Measures");
			if (measures != null) {
				String crosswith = getAttrValue(measures, "crosswith");
				List tempMeasure = measures.elements();
				Measure[] mea = null;
				if (tempMeasure != null) {
					mea = new Measure[tempMeasure.size()];
					int m1 = 0;
					for (Iterator m2 = tempMeasure.iterator(); m2.hasNext();) {
						Element measureElement = (Element) m2.next();
						mea[m1] = new Measure();
						mea[m1].setCode(getAttrValue(measureElement, "code"));
						mea[m1].setMeasureValue(getElementText(measureElement));
						mea[m1].setName(getAttrValue(measureElement, "name"));

						mea[m1].setDisplay(getAttrValue(measureElement,
								"display"));
						mea[m1]
								.setFormat(getAttrValue(measureElement,
										"format"));
						mea[m1].setCrosswith(crosswith);
						mea[m1].setAggrType(getAttrValue(measureElement,
								"aggrfunc"));

						m1++;
					}
				}
				returnVal[j].setMeasures(mea);
			}

			Element columns = reportBody.element("Columns");
			if (columns != null) {
				List colum_s = columns.elements("Column");
				Column[] cs = null;
				if (colum_s != null && colum_s.size() > 0) {
					cs = new Column[colum_s.size()];
					for (int k = 0; k < colum_s.size(); k++) {
						Element e = (Element) colum_s.get(k);
						cs[k] = new Column();
						cs[k].setCode(e.attributeValue("code"));
						cs[k].setFormat(e.attributeValue("formate"));
						cs[k].setName(e.attributeValue("name"));
						cs[k].setType(e.attributeValue("type"));
						cs[k].setOutputtype(e.attributeValue("outputtype"));
						cs[k].setColumValue(e.getText());
					}
				}

				returnVal[j].setColumns(cs);
			}

			// 处理reportFooter
			Element reportFooter = tempCrossGrid.element("ReportFooter");
			if (reportFooter != null) {
				returnVal[j].setReportFooterOutType(getAttrValue(reportFooter,
						"outputtype"));
				returnVal[j].setReportFooterValue(getElementText(reportFooter));
			}

			j++;
		}

		return returnVal;
	}

	/**
	 * 获得xml中的CrossGrid对象
	 * 
	 * @return
	 */
	public CrossGrid[] getCrossGrid() {
		List listCrossGrid = reportXml.selectNodes("//CrossGrid");
		CrossGrid[] returnVal;
		if (listCrossGrid.size() == 0) {
			return null;
		} else {
			returnVal = new CrossGrid[listCrossGrid.size()];
		}

		int j = 0;
		for (Iterator i = listCrossGrid.iterator(); i.hasNext();) {
			returnVal[j] = new CrossGrid();
			Element tempCrossGrid = (Element) i.next();
			returnVal[j].setId(getAttrValue(tempCrossGrid, "id"));
			Element sql = tempCrossGrid.element("Sql");
			Sql oSql = new Sql();
			oSql.setDb(getAttrValue(sql, "db"));
			oSql.setId(getAttrValue(sql, "id"));
			oSql.setSqlText(getElementText(sql));
			returnVal[j].setSql(oSql);
			// 处理reportHeader
			Element reportHeader = tempCrossGrid.element("ReportHeader");
			if (reportHeader != null) {
				returnVal[j].setReportHeaderOutType(getAttrValue(reportHeader,
						"outputtype"));
				returnVal[j].setReportHeaderOutValue(RegExUtil.replaceAllUtil(
						reportHeader.getText(), "\\s", ""));
			}

			Element reportBody = tempCrossGrid.element("ReportBody");

			Element rowDim = reportBody.element("RowDims");
			if (rowDim != null) {
				List tempRowDim = rowDim.elements();
				Dim[] dim = null;
				if (tempRowDim != null) {
					dim = new Dim[tempRowDim.size()];
					int d1 = 0;
					for (Iterator d2 = tempRowDim.iterator(); d2.hasNext();) {
						Element rD = (Element) d2.next();
						dim[d1] = new Dim();
						dim[d1].setDimValue(getElementText(rD));
						dim[d1].setCode(getAttrValue(rD, "code"));
						dim[d1].setName(getAttrValue(rD, "name"));
						dim[d1].setSumname(getAttrValue(rD, "sumname"));
						dim[d1].setSummary(getAttrValue(rD, "summary"));
						dim[d1].setSumby(getAttrValue(rD, "sumby"));
						dim[d1].setSumpos(getAttrValue(rD, "sumpos"));
						d1++;
					}
				}
				returnVal[j].setRowDim(dim);
			}

			Element colDim = reportBody.element("ColDims");

			if (colDim != null) {
				List tempColDim = colDim.elements();
				Dim[] dim = null;
				if (tempColDim != null) {
					dim = new Dim[tempColDim.size()];
					int d1 = 0;
					for (Iterator d2 = tempColDim.iterator(); d2.hasNext();) {
						Element colElement = (Element) d2.next();
						dim[d1] = new Dim();
						dim[d1].setCode(getAttrValue(colElement, "code"));
						dim[d1].setDimValue(getElementText(colElement));
						dim[d1].setName(getAttrValue(colElement, "name"));
						dim[d1].setSumname(getAttrValue(colElement, "sumname"));
						dim[d1].setSummary(getAttrValue(colElement, "summary"));
						dim[d1].setSumby(getAttrValue(colElement, "sumby"));
						dim[d1].setSumpos(getAttrValue(colElement, "sumpos"));
						d1++;
					}
				}
				returnVal[j].setColDim(dim);
			}

			Element measures = reportBody.element("Measures");
			if (measures != null) {
				String crosswith = getAttrValue(measures, "crosswith");
				List tempMeasure = measures.elements();
				Measure[] mea = null;
				if (tempMeasure != null) {
					mea = new Measure[tempMeasure.size()];
					int m1 = 0;
					for (Iterator m2 = tempMeasure.iterator(); m2.hasNext();) {
						Element measureElement = (Element) m2.next();
						mea[m1] = new Measure();
						mea[m1].setCode(getAttrValue(measureElement, "code"));
						mea[m1].setMeasureValue(getElementText(measureElement));
						mea[m1].setName(getAttrValue(measureElement, "name"));

						mea[m1].setDisplay(getAttrValue(measureElement,
								"display"));
						mea[m1]
								.setFormat(getAttrValue(measureElement,
										"format"));
						mea[m1].setCrosswith(crosswith);
						mea[m1].setAggrType(getAttrValue(measureElement,
								"aggrfunc"));

						m1++;
					}
				}
				returnVal[j].setMeasures(mea);
			}
			// 处理reportFooter
			Element reportFooter = tempCrossGrid.element("ReportFooter");
			if (reportFooter != null) {
				returnVal[j].setReportFooterOutType(getAttrValue(reportFooter,
						"outputtype"));
				returnVal[j].setReportFooterValue(getElementText(reportFooter));
			}

			j++;
		}

		return returnVal;
	}

	public Chart[] getChart() {
		List listCharts = reportXml.selectNodes("//Chart");
		Chart[] returnVal;
		if (listCharts.size() == 0) {
			return null;
		} else {
			returnVal = new Chart[listCharts.size()];
		}

		int j = 0;
		for (Iterator i = listCharts.iterator(); i.hasNext();) {
			returnVal[j] = new Chart();

			Element tempChart = (Element) i.next();
			returnVal[j].setCaption(getAttrValue(tempChart, "caption"));
			returnVal[j].setHeight(getAttrValue(tempChart, "height"));
			returnVal[j].setWidth(getAttrValue(tempChart, "width"));
			returnVal[j].setLegend(getAttrValue(tempChart, "legend"));
			returnVal[j].setIs3d(getAttrValue(tempChart, "disp3d"));
			returnVal[j].setType(getAttrValue(tempChart, "type"));
			returnVal[j].setId(getAttrValue(tempChart, "id"));

			// 处理sql
			Element sql = tempChart.element("Sql");
			Sql oSql = new Sql();
			oSql.setDb(getAttrValue(sql, "db"));
			oSql.setId(getAttrValue(sql, "id"));
			oSql.setSqlText(getElementText(sql));
			returnVal[j].setSql(oSql);

			// 处理rowdim
			Element rowDim = tempChart.element("RowDims");

			if (rowDim != null) {
				List tempDim = rowDim.elements();
				if (tempDim.size() == 0) {
					returnVal[j].setRowDim(null);
				} else {
					Element dimElement = (Element) tempDim.get(0);
					Dim dim = new Dim();
					dim.setDimValue(getElementText(dimElement));
					dim.setName(getAttrValue(dimElement, "name"));
					returnVal[j].setRowDim(dim);
				}
			} else {
				returnVal[j].setRowDim(null);
			}

			// 处理coldim
			Element colDim = tempChart.element("ColDims");
			if (colDim != null) {
				List tempDim = colDim.elements();
				if (tempDim.size() == 0) {
					returnVal[j].setColDim(null);
				} else {
					Element dimElement = (Element) tempDim.get(0);
					Dim dim = new Dim();
					dim.setDimValue(getElementText(dimElement));
					dim.setName(getAttrValue(dimElement, "name"));
					returnVal[j].setColDim(dim);
				}
			} else {
				returnVal[j].setColDim(null);
			}

			Element measures = tempChart.element("Measures");
			if (measures != null) {
				String crosswith = getAttrValue(measures, "crosswith");
				List tempMeasure = measures.elements();
				Measure[] mea = null;
				if (tempMeasure != null) {
					mea = new Measure[tempMeasure.size()];
					int m1 = 0;
					for (Iterator m2 = tempMeasure.iterator(); m2.hasNext();) {
						Element measureElement = (Element) m2.next();
						mea[m1] = new Measure();
						mea[m1].setMeasureValue(getElementText(measureElement));
						mea[m1].setName(getAttrValue(measureElement, "name"));
						mea[m1]
								.setFormat(getAttrValue(measureElement,
										"format"));
						mea[m1].setDisplay(getAttrValue(measureElement,
								"display"));
						mea[m1]
								.setFormat(getAttrValue(measureElement,
										"format"));
						mea[m1].setCrosswith(crosswith);
						m1++;
					}
				}
				returnVal[j].setMeasures(mea[0]);
			}

			j++;
		}

		return returnVal;
	}

	/**
	 * 获得xml中的layout
	 * 
	 * @return
	 */
	public Layout getLayout() {
		Element layout = (Element) reportXml
				.selectSingleNode("//Report/Layout");
		if (layout == null) {
			return null;
		}
		Layout returnVal = new Layout();
		returnVal.setOutputtype(getAttrValue(layout, "outputtype"));
		List tempElments = layout.elements();
		if (tempElments.size() == 0) {
			returnVal.setValue(null);
		} else {
			returnVal.setValue(((Element) tempElments.get(0)).asXML());
		}
		return returnVal;
	}

	/**
	 * 获得xml中的layout
	 * 
	 * @return
	 */
	public String getFusionChartLayoutEx() {
		Element layout = (Element) reportXml
				.selectSingleNode("//Report/FusionChartLayout");
		if (layout != null)
			return ((Element) layout.elementIterator().next()).asXML();
		else
			return null;
	}

	/**
	 * 取得fusion chart的布局
	 * 
	 * @return
	 */
	public String getFusionChartLayout() {
		Element layout = (Element) reportXml
				.selectSingleNode("//Report/Layout");
		return ((Element) layout.elementIterator().next()).asXML();
	}

	/**
	 * 获得xml中的数据源的相关对象信息
	 * 
	 * @return
	 */
	public HashMap getBO() {

		List listDatasrcs = reportXml.selectNodes("//BO");
		HashMap returnVal;
		if (listDatasrcs.size() == 0) {
			return null;
		} else {
			returnVal = new HashMap();
		}

		for (Iterator i = listDatasrcs.iterator(); i.hasNext();) {
			BO tempdb = new BO();
			Element tempDatasrcs = (Element) i.next();
			String id = getAttrValue(tempDatasrcs, "id");
			tempdb.setId(id);
			tempdb.setIndexText(getAttrValue(tempDatasrcs, "index"));
			tempdb.setSqlText(getElementText(tempDatasrcs));
			tempdb.setDb(getAttrValue(tempDatasrcs, "db"));
			returnVal.put(id, tempdb);
		}

		return returnVal;
	}

	public List generateSql(List conditonValue, List condition) {
		return null;
	}

	/**
	 * 产生文件名列表
	 * 
	 * 
	 * 
	 * 
	 * @param conditonValue
	 * @return
	 */
	public List generateFileNameList(List conditonValue) {
		String filename = "r_";
		List returnValue = new ArrayList();

		// 如果报表中的所有datasrc都没有条件

		if (conditonValue == null || conditonValue.size() == 0) {
			returnValue.add(filename + this.getId() + "_");
			return returnValue;
		}

		List temp1Value = (List) conditonValue.get(0);
		if (temp1Value.size() != 0) {
			for (Iterator i = temp1Value.iterator(); i.hasNext();) {
				returnValue.add(filename + i.next().toString() + "_");
			}
		} else {
			returnValue.add(filename);
		}

		for (int i = 1; i < conditonValue.size(); i++) {
			List temp2 = (List) conditonValue.get(i);
			returnValue = genDescar(returnValue, temp2);
		}
		return returnValue;
	}

	/**
	 * 两个List生成笛卡尔积
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public List genDescar(List value1, List value2) {
		List returnVal = new ArrayList();
		if (value2 != null && value2.size() != 0) {
			for (Iterator i = value1.iterator(); i.hasNext();) {
				String val = (String) i.next();
				for (Iterator j = value2.iterator(); j.hasNext();) {
					returnVal.add(val + j.next().toString() + "_");
				}
			}
		} else {
			for (Iterator i = value1.iterator(); i.hasNext();) {
				String val = (String) i.next();
				returnVal.add(val + "_");
			}
		}
		return returnVal;

	}

	/**
	 * 根据参数数组生成文件名
	 * 
	 * 
	 * 
	 * 
	 * @param con
	 * @return
	 */
	public String genFileName(Condition[] con) {
		String returnVal = "r_";
		for (int i = 0; i < con.length; i++) {
			returnVal += con[i].getParaname() + "_";
		}
		return returnVal.substring(0, returnVal.length() - 1);
	}

	/**
	 * 初始化xml文件
	 * 
	 * @param xmlfile
	 * @return
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public Document loadXML(String xmlfile) throws FileNotFoundException,
			DocumentException {
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new FileInputStream(xmlfile));
		return doc;
	}

	public static void main(String[] argv) {

	}

}
