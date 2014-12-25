package com.teamsun.thunderreport.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.jdbc.core.JdbcTemplate;

import com.teamsun.thunderreport.parse.CrossGrid;
import com.teamsun.framework.ui.ListGridFactory;
import com.teamsun.framework.util.RegExUtil;
import com.teamsun.thunderreport.core.bean.BO;
import com.teamsun.thunderreport.parse.Column;
import com.teamsun.thunderreport.parse.Condition;
import com.teamsun.thunderreport.parse.CrossGridEx;
import com.teamsun.thunderreport.parse.Dim;
import com.teamsun.thunderreport.parse.Layout;
import com.teamsun.thunderreport.parse.Measure;
import com.teamsun.thunderreport.parse.Title;
import com.teamsun.thunderreport.core.bean.Footer;
import com.teamsun.thunderreport.core.bean.Header;
import com.teamsun.thunderreport.parse.Sql;
import com.teamsun.thunderreport.core.bean.TableTitle;
import com.teamsun.thunderreport.parse.ListGrid;
import com.teamsun.thunderreport.database.DimDataService;
import com.teamsun.thunderreport.util.BeanLoader;

/**
 * 解析 xml 配置文件
 * 
 */
public class XmlParser implements ParserService {

	private Document reportXml;

	private String filename;
	
	public RepresentContextService[] getRepresentContextService() {
		List crossGrid = reportXml.selectNodes("//CrossGrid");
		if(crossGrid.size() != 0){
			return getCrossGrid();
		}
		List listGridEx = reportXml.selectNodes("//CrossGridEx");
		if(listGridEx.size() != 0){
			return getCrossGridEx();
		}		
		List listGrid = reportXml.selectNodes("//ListGrid");
		if(listGrid.size() != 0){
			return getListGrid();
		}

		return null;
							
	}
		
	
	public com.teamsun.framework.ui.ListGrid[] getListGrid(){
		List listGrids = reportXml.selectNodes("//ListGrid");
		com.teamsun.framework.ui.ListGrid[] returnVal1;
		ListGrid returnVal;
		if (listGrids.size() == 0) {
			return null;
		} else {
			returnVal1 = new com.teamsun.framework.ui.ListGrid[listGrids.size()];
		}

		int j = 0;
		for (Iterator i = listGrids.iterator(); i.hasNext();) {
			Element tempGrid = (Element) i.next();

			returnVal = new ListGrid();
			returnVal.setId(getAttrValue(tempGrid, "id"));
			Element sql = tempGrid.element("Sql");
			returnVal.setTemplatefilename(getAttrValue(tempGrid,"gridtemplname"));
			Sql oSql = new Sql();
			oSql.setDb(getAttrValue(sql, "db"));
			oSql.setId(getAttrValue(sql, "id"));
			oSql.setSqlText(getElementText(sql));
			returnVal.setSql(oSql);
			// 取reportheader
			Element reportHeader = tempGrid.element("ReportHeader");
			if (reportHeader != null) {
				returnVal.setReportHeaderOutType(getAttrValue(reportHeader,
						"outputtype"));
				// returnVal.setReportHeaderOutValue(RegExUtil.replaceAllUtil(
				// reportHeader.getText(), "\\s", ""));
				returnVal.setReportHeaderOutValue(reportHeader.getTextTrim());
			}

			// 处理reportbody中的内容
			Element reportBody = tempGrid.element("ReportBody");
			Element funcTitle = reportBody.element("FuncTitle");

			if (funcTitle != null) {
				returnVal.setReportBodyFunTitleType(getAttrValue(funcTitle,
						"outputtype"));
				returnVal.setReportBodyFunTitleValue(getElementText(funcTitle));
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
				columnsArray[k]
						.setAggrfunc(getAttrValue(tempColumn, "aggrfunc"));
				columnsArray[k].setHidden(getAttrValue(tempColumn, "hidden"));
				columnsArray[k].setTextAlign(getAttrValue(tempColumn, "textalign"));
				k++;
			}
			returnVal.setColumns(columnsArray);

			// 处理reportfooter
			Element reportFooter = tempGrid.element("ReportFooter");
			if (reportFooter != null) {
				returnVal.setReportFooterOutType(getAttrValue(reportFooter,
						"outputtype"));
				returnVal.setReportFooterValue(getElementText(reportFooter));
			}

			returnVal1[j] = ListGridFactory.getGrid(returnVal);
			j++;
		}
		return returnVal1;
	}
	
	
	
	/**
	 * @param elementlic
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
	
	/*
	 * @param element
	 * @param attrName
	 * @return
	 */
	public String getElementText(Element element) {
		return element == null ? null : RegExUtil.replaceAllUtil(element
				.getText(), "\\s", " ");
	}
	

	/**
	 * 获得xml中的CrossGrid对象
	 * 
	 * @return
	 */
	public com.teamsun.framework.ui.ListGrid[] getCrossGrid() {
		List listCrossGrid = reportXml.selectNodes("//CrossGrid");
		com.teamsun.framework.ui.CrossGrid[] returnVal1;
		CrossGrid[] returnVal;
		if (listCrossGrid.size() == 0) {
			return null;
		} else {
			returnVal = new CrossGrid[listCrossGrid.size()];
			returnVal1=new com.teamsun.framework.ui.CrossGrid[listCrossGrid.size()];
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
            Element rowTitle = reportBody.element("RowTitle");
            if(rowTitle!=null){
            	List tempRowTitle=rowTitle.elements();
            	Title[] title=null;
            	if(tempRowTitle!=null){
            		title= new Title[tempRowTitle.size()];
            		int d1=0;
            		for(Iterator d2=tempRowTitle.iterator();d2.hasNext();){
            			Element rD=(Element)d2.next();
            			title[d1] = new Title();
            			title[d1].setTitleText(getElementText(rD));
            			d1++;
            		}
            	}
            	
            	
            	returnVal[j].setTitle(title);
            }
            
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
						dim[d1].setGroup(getAttrValue(rD,"group"));
						dim[d1].setSumname(getAttrValue(rD, "sumname"));
						dim[d1].setSummary(getAttrValue(rD, "summary"));
						dim[d1].setSumby(getAttrValue(rD, "sumby"));
						dim[d1].setSumpos(getAttrValue(rD, "sumpos"));
						dim[d1].setOutputtype(getAttrValue(rD, "outputtype"));
						dim[d1].setDimValue(getElementText(rD));
						dim[d1].setKeycode(getAttrValue(rD,"keycode"));
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
						dim[d1].setOutputtype(getAttrValue(colElement, "outputtype"));
						dim[d1].setDimValue(getElementText(colElement));
						dim[d1].setKeycode(getAttrValue(colElement,"keycode"));
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
						mea[m1].setStyle(getAttrValue(measureElement,
								"style"));

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
			returnVal[j].setTemplatefilename(getAttrValue(tempCrossGrid,"gridtemplname"));
			returnVal1[j]=ListGridFactory.getUiCrossGrid(returnVal[0]);
			j++;			
		}
		return returnVal1;
	}


	/*
	public RepresentContextService getRepresentContextService() {

		if (this.reportXml.selectSingleNode("//ListGrid") != null) {
			Element element = (Element) this.reportXml
					.selectSingleNode("//ListGrid");
			ListGrid lg = new ListGrid();
			Sql sql = new Sql();
			sql.setDb(element.element("Sql").attributeValue("db"));
			sql.setId(element.element("Sql").attributeValue("id"));
			sql.setSql(element.element("Sql").getText());
			lg.setSql(sql);

			Element reportHeader = element.element("ReportHeader");
			lg.setHeader(new Header(reportHeader.attributeValue("outputtype"),
					reportHeader.getTextTrim()));

			Element reportFooter = element.element("ReportFooter");
			lg.setFooter(new Footer(reportFooter.attributeValue("outputtype"),
					reportFooter.getTextTrim()));

			lg.setId(element.attributeValue("id"));

			Element et = element.element("ReportBody").element("FuncTitle");
			lg.setTabletitle(new TableTitle(et.attributeValue("outputtype"), et
					.getTextTrim()));
			System.out.println(lg.getTabletitle().getText());

			List column = element.element("ReportBody").element("Columns")
					.elements();
			Column[] columnsArray = new Column[column.size()];
			for (int k = 0; k < column.size(); k++) {
				Element el = (Element) column.get(k);
				columnsArray[k] = new Column();
				columnsArray[k].setText(el.getTextTrim());
				columnsArray[k].setGroup(Integer.parseInt(el
						.attributeValue("group")));
				columnsArray[k].setAlign(el.attributeValue("align"));
				columnsArray[k].setFormat(el.attributeValue("format"));
				columnsArray[k].setCode(el.attributeValue("code"));
				columnsArray[k].setOutputtype(el.attributeValue("outputtype"));
			}

			lg.setColumns(columnsArray);

			return lg;
		}

		return null;
	}
*/
	public XmlParser(String absolutePath) {

		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new FileInputStream(absolutePath));
			this.reportXml = doc;
			File file = new File(absolutePath);
			this.filename = file.getName();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 配置文件id
	 * 
	 * @return
	 */
	public String getId() {
		Attribute attr = (Attribute) reportXml.selectSingleNode("//Report/@id");
		if (attr == null) {
			return "";
		} else {
			return attr.getValue();
		}
	}

	/**
	 * 配置文件title
	 * 
	 * @return
	 */
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
	 * 获取实际查询项目
	 * 
	 * @return
	 */
	public Sql getSql() {

		return new Sql();
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
			return new Condition[] {};
		} else {
			returnVal = new Condition[listCons.size()];
		}

		int j = 0;
		for (Iterator i = listCons.iterator(); i.hasNext();) {
			returnVal[j] = new Condition();
			Element tempCondition = (Element) i.next();
			returnVal[j].setId(tempCondition.attributeValue("id"));
			returnVal[j].setParaname(tempCondition.attributeValue("paraname"));
			returnVal[j].setTitle(tempCondition.attributeValue("title"));
			returnVal[j].setDefaultvalue(tempCondition
					.attributeValue("defaultvalue"));
			returnVal[j].setRefervalue(tempCondition
					.attributeValue("needvalue"));
			returnVal[j].setRefervalue(tempCondition.attributeValue("ref_name"));
			j++;
		}

		return returnVal;
	}

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public BO[] getBo() {
		List listDatasrcs = reportXml.selectNodes("//BO");
		BO[] bos;
		if (listDatasrcs.size() == 0)
			return new BO[] {};
		else
			bos = new BO[listDatasrcs.size()];

		int index = 0;
		for (Iterator i = listDatasrcs.iterator(); i.hasNext();) {
			bos[index] = new BO();
			Element tempDatasrcs = (Element) i.next();
			bos[index].setId(tempDatasrcs.attributeValue("id"));
			bos[index].setIndex(tempDatasrcs.attributeValue("index"));
			bos[index].setSqltext(tempDatasrcs.getText());
			bos[index].setDatabase(tempDatasrcs.attributeValue("db"));
			bos[index].setTable(tempDatasrcs.attributeValue("table"));
			index++;
		}
		return bos;
	}
	

	public List getDimObjects() {
		List beanList = reportXml.selectNodes("//bean");
		List returnList = new ArrayList();
		try {
			for (Iterator i = beanList.iterator(); i.hasNext();) {
				Element tempDatasrc = (Element) i.next();
				String className = tempDatasrc.attributeValue("class");
				// System.out.println(className);

				Class clas = Class.forName(className);
				Hashtable hashTable = this.initTags(clas);
				DimDataService dimImp = (DimDataService) clas.newInstance();
				String subName = className.substring(className.lastIndexOf(".") + 1);
				Iterator innerIter = tempDatasrc.elementIterator();
				
			    while(innerIter.hasNext()){
					Element innerTemp = (Element)innerIter.next();
					String name = innerTemp.attributeValue("name");
					String value = innerTemp.attributeValue("value");
						
					if(value==null){
						 value = innerTemp.elementText("value");						
					}
					
					Method m = (Method)hashTable.get(name.toLowerCase());
					 if (m != null){
						 try{
		                  /**调用类的setter函数*/
		                	Object obj[]={value};
		                	if("template".equals(name.toLowerCase())){		                		
		                		String refTemplate=innerTemp.attributeValue("ref");
		                		JdbcTemplate template=(JdbcTemplate)BeanLoader.getBean(refTemplate);
		                		obj[0] =  template ;
		                	}
		                    m.invoke(dimImp, obj);
		                }catch (Exception ex){
		                	ex.printStackTrace();
		                    System.out.println("invoke setter on " + m.getName()+ " error: " + ex.toString());		                  
		                }
		            }
				 }
				
			   returnList.add(dimImp);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	
	 private  Hashtable initTags(Class className){
	    	Hashtable setMethods= null;
	       
	        setMethods = new Hashtable();
	        
	        Class cls = className;
	      
	        Method methods[] = cls.getMethods();
	        
	        /**定义正则表达式，从方法中过滤出setter 函数*/
	        String ss ="set(\\w+)";
	        String tag;
	        for(int i = 0; i < methods.length; ++i) {
	            Method m = methods[i];
	            String methodName = m.getName();
	            if (Pattern.matches(ss, methodName )) {
	                tag = methodName.replaceAll("set", "").toLowerCase().trim();
	                setMethods.put(tag, m);
	                //System.out.println(methodName + "  match");
	             }	           
	        }
	        return setMethods;
	    }
	 
	 public com.teamsun.framework.ui.ListGrid[] getCrossGridEx() {
			List listCrossGrid = reportXml.selectNodes("//CrossGridEx");
			com.teamsun.framework.ui.CrossGridEx[] returnVal1;
			CrossGridEx[] returnVal;
			if (listCrossGrid.size() == 0) {
				return null;
			} else {
				returnVal = new CrossGridEx[listCrossGrid.size()];
				returnVal1=new com.teamsun.framework.ui.CrossGridEx[listCrossGrid.size()];
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
				
				returnVal1[j]=ListGridFactory.getUiCrossGridEx(returnVal[0]);
				
				j++;
			}

			return returnVal1;
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

	public String getFilename() {
		return filename;
	}
	
	public static void main(String[] args){
		XmlParser xp=new XmlParser("D:\\ThunderReport\\Version1.3\\Development\\ThunderReport\\web\\WEB-INF\\applicationProp.xml");
		List l=xp.getDimObjects();
		System.out.println("nihao");
	}
}
