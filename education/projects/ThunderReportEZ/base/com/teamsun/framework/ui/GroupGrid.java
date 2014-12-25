package com.teamsun.framework.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import org.w3c.dom.NodeList;

import com.teamsun.framework.ui.ListGrid;
import com.teamsun.framework.ui.SimpleFormat;
import com.teamsun.framework.util.RegExUtil;
import com.teamsun.thunderreport.javascript.JavaScriptParser;
import com.teamsun.thunderreport.parse.Column;

public class GroupGrid extends ListGrid {
	private List afterList = new ArrayList();

	private int rownum=0;
	
	public String toHtml(List data, List allData, String[] paramValues) {

		String table = "<table class=\"page\" width=\"100%\"><tr><td class=\"reportHeader\">";
		table += getReportHeader(paramValues) + "</td></tr><tr><td>"
				+ getReportBody(data, allData, paramValues)
				+ "</td></tr><tr><td>" + getReportFooter(paramValues)
				+ "</td></tr></table>";
		return table;
	}

	private String getReportHeader(String[] paramValues) {
		String reportHeader = "";
		if (this.reportHeaderOutType != null) {
			if (this.reportHeaderOutType.equals("function")) {
				reportHeader = RegExUtil.replaceCon(this.reportHeaderOutValue,
						this.conditions, paramValues);
				//reportHeader = "<script>" + reportHeader + "</script>";
			} else {
				reportHeader = this.reportHeaderOutValue;
			}
		}
		return reportHeader;
	}

	private String getReportFooter(String[] paramValues) {
		String reportFooter = "";
		if (this.reportFooterOutType != null) {
			if (this.reportFooterOutType.equals("function")) {
				reportFooter = RegExUtil.replaceCon(this.reportFooterValue,
						this.conditions, paramValues);
				reportFooter = "<script>" + reportFooter + "</script>";
			} else {
				reportFooter = this.reportFooterValue;
			}
		}
		return reportFooter;
	}

	private String getReportBody(List dataList, List allData,
			String[] paramValues) {
		DOMDocument document = new DOMDocument();
		DOMElement root = (DOMElement) new DOMElement("table");
		document.appendChild(root);
		root.addAttribute("border", "1px");
		root.addAttribute("width", "100%");
		root.addAttribute("bordercolor", "#333333");
		root.addAttribute("cellspacing", "0");
		root.addAttribute("cellpadding", "0");
		root.addAttribute("style", "border-collapse:collapse;");
		// 表头 --start
		DOMElement header = (DOMElement) new DOMElement("thead");
		root.appendChild(header);
		if (this.reportBodyFunTitleType != null) {
			if (this.reportBodyFunTitleType.equals("function")) {
				DOMElement head_tr = (DOMElement) new DOMElement("tr");
				DOMElement th = (DOMElement) new DOMElement("th");
				th.addAttribute("class", "listColumnTitle");
				th.addAttribute("colspan", String.valueOf(this.cols.length));
				String headStr = RegExUtil.replaceCon(
						this.reportBodyFunTitleValue, this.conditions,
						paramValues);
				th.addText("<script>" + headStr + "</script>");
				head_tr.appendChild(th);
				header.appendChild(head_tr);
			} else {
				String headStr = RegExUtil.replaceCon(
						this.reportBodyFunTitleValue, this.conditions,
						paramValues);
				header.addComment(headStr);
			}
		} else {
			DOMElement head_tr = (DOMElement) new DOMElement("tr");
			for (int i = 0; i < this.cols.length; i++) {
				DOMElement th = (DOMElement) new DOMElement("th");
				th.addAttribute("class", "listColumnTitle");
				th.addText(this.cols[i].getName());
				head_tr.appendChild(th);
				header.appendChild(head_tr);
			}
		}// 表头 --end

		int[] startArr = new int[this.afterList.size()];
		int[] endArr = new int[this.afterList.size()];
		for (int g = 0; g < startArr.length; g++) {
			startArr[g] = 1;
			endArr[g] = 1;
		}
		int rowPosEnd = 1;

		// 表体--start
		DOMElement body = new DOMElement("tbody");
		root.appendChild(body);
//		if (dataList != null && dataList.size() != 0) {
//			TreeMap columnMap=new TreeMap();
//			int row_count = dataList.size();
//			int col_count = this.cols.length;
//		}
		if (dataList != null && dataList.size() != 0) {
			int row_count = dataList.size();
			int col_count = this.cols.length;
			Map[] dataMap = new Map[row_count];
			for (int i = 0; i < row_count; i++) {
				dataMap[i] = (Map) dataList.get(i);
			}

			for (int i = 0; i < row_count; i++) {
				
				DOMElement cur_tr = new DOMElement("tr");
				body.appendChild(cur_tr);
				cur_tr.addAttribute("type", "standard");
				DOMElement cur_td = null;
				String code = "";
				String td_text = "";
				String td_value = "";
				for (int j = 0; j < col_count; j++) {
					code = this.cols[j].getCode();
					Object o = dataMap[i].get(code);
					if (o == null)
						o = "";
					td_value = td_text = o.toString();
					String group_text = td_text;

					cur_td = new DOMElement("td");
					cur_tr.appendChild(cur_td);

					if (this.cols[j].getType().equals("dim")) {
						if (j == 0) {
							cur_td.addAttribute("groupvalue", group_text);
						} else {
							DOMElement pre_element = (DOMElement) cur_td
									.getPreviousSibling();
							String pre_value = pre_element
									.attributeValue("groupvalue");
							group_text = pre_value + "_" + td_text;
							cur_td.addAttribute("groupvalue", group_text);
						}
					}
					cur_td.addAttribute("value", td_text);
					if (this.cols[j].getTextAlign() != null) {
						cur_td.addAttribute("align", this.cols[j]
								.getTextAlign());
					} else {
						if (this.cols[j].getType().equals("measure")) {
							cur_td.addAttribute("align", "right");
						} else {
							cur_td.addAttribute("align", "left");
						}
					}
					if (this.cols[j].getType().equals("measure")) {
						if (rownum % 2 == 0) {
							cur_td.addAttribute("class", "tr_odd");
						} else {
							cur_td.addAttribute("class", "tr_even");
						}
					}
					dealHiddenColumn(this.cols[j],cur_td);
					td_value=getCellValue(this.cols[j],td_value,dataMap[i],paramValues);
					cur_td.addText(td_value);
				}
				
				if (i > 0) {
					DOMElement pre_tr = (DOMElement) cur_tr
							.getPreviousSibling();
					for (int k = 0; k < this.afterList.size(); k++) {
						int a = Integer.parseInt(this.afterList.get(k)
								.toString());
						if (a > 0) {
							String[] tA = this.cols[a].getSumby().split(",");
							boolean be = true;
							for (int m = 0; m < tA.length; m++) {
								DOMElement pre_td = (DOMElement) pre_tr
										.getChildNodes().item(m);
								cur_td = (DOMElement) cur_tr.getChildNodes()
										.item(m);
								be = be
										&& (cur_td.attributeValue("value")
												.equals(pre_td
														.attributeValue("value")));
							}
							// add sum row--start
							if (!be) {
								
								endArr[k] = rowPosEnd + 1;
								DOMElement before_element = null;
								if (this.cols[a].getSumpos().equals("after")) {
									before_element = (DOMElement) document
											.selectSingleNode("/table/tbody/tr["
													+ rowPosEnd + "]");

								} else {
									before_element = (DOMElement) document
											.selectSingleNode("/table/tbody/tr["
													+ startArr[k] + "]");
								}
								DOMElement ext_tr = new DOMElement("tr");
								body.insertBefore(ext_tr, before_element);
								ext_tr.addAttribute("type", "ext");
								int l;
								for (l = 0; l < tA.length; l++) {
									DOMElement ext_td = new DOMElement("td");
									DOMElement pre_td = (DOMElement) pre_tr
											.getChildNodes().item(l);
									ext_td.addAttribute("groupvalue", pre_td
											.attributeValue("groupvalue"));
									ext_td.addAttribute("value", pre_td
											.attributeValue("value"));
									ext_td.addText(pre_td.getText());
									ext_tr.appendChild(ext_td);
								}
								DOMElement ext_td = new DOMElement("td");
								ext_tr.appendChild(ext_td);
								ext_td.setText(this.cols[a].getSumname());
								DOMElement ext_pre_td = (DOMElement) ext_td
										.getPreviousSibling();
								String ext_pre_td_value = ext_pre_td
										.attributeValue("groupvalue");
								ext_td.addAttribute("groupvalue",
										ext_pre_td_value + "_"
												+ this.cols[a].getSumname());
								ext_td.addAttribute("value", ext_pre_td_value
										+ "_" + this.cols[a].getSumname());
								l=l+1;
								int c=l;
								Map xmap=new HashMap();
								while (c < this.cols.length) {
									if (this.cols[c].getType().equals("measure")) {
										if (this.cols[c].getOutputtype() == null
												|| !this.cols[c].getOutputtype().equals("function")) {
											td_value = ""+sum(getColumns(document, c,startArr[k],endArr[k]));
											xmap.put(this.cols[c].getCode(),td_value);
										}
									}
									c++;
								}
								
								while (l < this.cols.length) {
									ext_td = new DOMElement("td");
									ext_tr.appendChild(ext_td);
									if (this.cols[l].getType().equals("dim")) {
										ext_pre_td = (DOMElement) ext_td
												.getPreviousSibling();
										ext_pre_td_value = ext_pre_td
												.attributeValue("groupvalue");
										ext_td
												.addAttribute(
														"groupvalue",
														ext_pre_td_value
																+ "_col["
																+ (rowPosEnd - 1)
																+ "]");
										ext_td
												.addAttribute(
														"value",
														ext_pre_td_value
																+ "_col["
																+ (rowPosEnd - 1)
																+ "]");
										ext_td.setText("");
									} else {
										if (this.cols[l].getOutputtype() != null
												&& this.cols[l].getOutputtype().equals("function")) {
											td_value=getCellValue(this.cols[l],"",xmap,paramValues);
										}										
										else {
											td_value = ""+sum(getColumns(document, l,startArr[k],endArr[k]));
											td_value=formatStr(this.cols[l],td_value);
										}										

										ext_td.addText(td_value);
										if (rownum % 2 == 0) {
											ext_td.addAttribute("class", "tr_odd");
										} else {
											ext_td.addAttribute("class", "tr_even");
										}
									}
									dealHiddenColumn(this.cols[l],ext_td);
									//-----------align 样式定制-----------------------------
									if (this.cols[l].getTextAlign() != null) {
										ext_td.addAttribute("align", this.cols[l]
												.getTextAlign());
									} else {
										if (this.cols[l].getType().equals("measure")) {
											ext_td.addAttribute("align", "right");
										} else {
											ext_td.addAttribute("align", "left");
										}
									}
									//-----------align 样式定制-----------------------------
									l++;
								}
		
								startArr[k] = rowPosEnd + 1;
								for (int h = 0; h <= k; h++) {
									startArr[h] = rowPosEnd + 1;
								}
								rownum++;
								rowPosEnd++;// sum 行加入，加1
							}// add sumrow--end
						}
					}
				}
				rownum++;
				rowPosEnd++; // 普通行插入，加1
			}
		}// 表体--end
		insertlastSum(document, this.afterList, startArr, endArr, rowPosEnd);
		insertTotalCount(document, allData);// 添加分组的列的总计
		insertTotalSum(document);// 添加总计行		for(int c=this.cols.length-1;c>=0;c--){
            if(this.cols[c].getHidden()!=null && this.cols[c].getHidden().equals("1")){
                      NodeList trList = body.getChildNodes();
                      for(int r=0;r<trList.getLength();r++){
                               DOMElement curTR = (DOMElement)trList.item(r);
                               DOMElement curTD = (DOMElement)curTR.getChildNodes().item(c);
                               curTR.remove(curTD);
                      }
                }
       }

		// 合并
		for (int i = this.cols.length - 1; i >= 0; i--) {
			if (this.cols[i].getType() != null
					&& this.cols[i].getType().equals("dim")) {
				if (this.cols[i].getGroup() != null
						&& this.cols[i].getGroup().equals("1")) {
					tdAcrossMerge(document, i + 1, 1);
				}
			}
		}
		String ss = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		String reportBody = document.asXML().substring(ss.length(),
				document.asXML().length()).trim();
		if (this.reportBodyFunTitleType != null
				&& !this.reportBodyFunTitleType.equals("function")) {
			int start = reportBody.indexOf("<thead>");
			int end = reportBody.indexOf("</thead>");
			String tr_content = reportBody.substring(start + 11, end - 3);
			String s1 = reportBody.substring(0, start + 7);
			String s2 = reportBody.substring(end + 8);
			reportBody = s1 + tr_content + "</thead>" + s2;
		}
		reportBody = reportBody.replaceAll("&lt;", "<");
		reportBody = reportBody.replaceAll("&gt;", ">");
		reportBody = reportBody.replaceAll("&amp;", "&");
		return reportBody;
	}

	private double sum(List li) {
		double sum = 0;
		for (int i = 0; i < li.size(); i++) {
			if(li.get(i)!=null&&!li.get(i).equals("")){
				double a = Double.parseDouble(li.get(i).toString());
				sum += a;
			}
		}
		return sum;
	}

	public String getGroupCount(Column[] columns, int countIndex, List dataList) {
		int groupCount = 0;
		Object flagObject = new Object();
		for (int i = 0; i < dataList.size(); i++) {
			Map dataMap = (Map) dataList.get(i);
			Object value = dataMap.get(columns[countIndex].getCode());
			if (!value.equals(flagObject)) {
				groupCount++;
				flagObject = value;
			}
		}
		return groupCount + "";
	}

	private void insertTotalCount(Document doc, List allData) {
		DOMElement body = (DOMElement) doc.selectSingleNode("/table/tbody");
		DOMElement last_tr = new DOMElement("tr");
		last_tr.addAttribute("type", "ext");
		DOMElement last_td = null;
		int flag = 0;
		for (int i = 0; i < this.cols.length; i++) {
			if (this.cols[i].getAggrfunc() != null) {
				if (this.cols[i].getAggrfunc().equals("GROUPCOUNT")) {
					last_td = new DOMElement("td");
					String td_value = "" + getGroupCount(this.cols, i, allData);
					if (i == 0) {
						last_td.addText(td_value);
						last_td.addAttribute("groupvalue", "group-count");
						last_td.addAttribute("value", "group-count-" + (i + 1));
					} else {
						DOMElement pre_last_td = (DOMElement) last_td
								.getPreviousSibling();
						String sdx = pre_last_td.attributeValue("groupvalue");
						last_td.addText(td_value);
						last_td.addAttribute("value", "group-count-" + (i + 1));
						last_td.addAttribute("groupvalue", sdx + "_" + (i + 1));
					}
					last_tr.appendChild(last_td);
					flag = 1;
				}
			}
		}
		if (flag == 1) {
			if (this.cols[0].getSumpos() != null
					&& this.cols[0].getSumpos().equals("before")) {
				body.insertBefore(last_tr, body.getFirstChild());
			} else {
				body.appendChild(last_tr);
			}
		}
	}

	private void insertlastSum(DOMDocument doc, List list, int[] startArr,
			int[] endArr, int rowPoseEnd) {
		DOMElement body = (DOMElement) doc.selectSingleNode("/table/tbody");
		for (int k = 0; k < list.size(); k++) {
			int a = Integer.parseInt(list.get(k).toString());
			if (a > 0) {
				String[] strArr = this.cols[a].getSumby().split(",");
				boolean be = true;
				if (be) {
					
					endArr[k] = rowPoseEnd + 1;
					DOMElement ext_tr = new DOMElement("tr");
					ext_tr.addAttribute("type", "ext");
					DOMElement before_tr = null;
					if (this.cols[a].getSumpos().equals("after")) {
						before_tr = (DOMElement) doc
								.selectSingleNode("/table/tbody/tr["
										+ rowPoseEnd + "]");
					} else {
						before_tr = (DOMElement) doc
								.selectSingleNode("/table/tbody/tr["
										+ startArr[k] + "]");
					}
					body.insertBefore(ext_tr, before_tr);
					int l;
					for (l = 0; l < strArr.length; l++) {

						DOMElement ext_td = new DOMElement("td");
						ext_tr.appendChild(ext_td);
						DOMElement pre_tr = (DOMElement) doc
								.selectSingleNode("/table/tbody/tr["
										+ (rowPoseEnd - 1) + "]");
						DOMElement pre_td = (DOMElement) pre_tr.getChildNodes()
								.item(l);
						ext_td.addAttribute("groupvalue", pre_td
								.attributeValue("groupvalue"));
						ext_td.addText(pre_td.getText());
					}
					DOMElement ext_td = new DOMElement("td");
					ext_tr.appendChild(ext_td);
					ext_td.addText(this.cols[a].getSumname());
					DOMElement pre_ext_td = (DOMElement) ext_td
							.getPreviousSibling();
					String pre_ext_td_value = pre_ext_td
							.attributeValue("groupvalue");
					ext_td.addAttribute("groupvalue", pre_ext_td_value + "_"
							+ this.cols[a].getSumname());
					ext_td.addAttribute("value", pre_ext_td_value + "_"
							+ this.cols[a].getSumname());
					l=l+1;
					int c=l;
					Map xmap=new HashMap();
					while (c < this.cols.length) {
						if (this.cols[c].getType().equals("measure")) {
							if (this.cols[c].getOutputtype() == null|| !this.cols[c].getOutputtype().equals("function")) {
								String td_value = ""+sum(getColumns(doc, c,startArr[k],endArr[k]));
								xmap.put(this.cols[c].getCode(),td_value);
							}
						}
						c++;
					}
					
					while (l < this.cols.length) {
						ext_td = new DOMElement("td");
						ext_tr.appendChild(ext_td);
						String td_value="";
						if (this.cols[l].getType().equals("dim")) {							
							pre_ext_td = (DOMElement) ext_td
							.getPreviousSibling();
							pre_ext_td_value = pre_ext_td
									.attributeValue("groupvalue");
							ext_td.addAttribute("groupvalue", pre_ext_td_value
									+ "_col[" + l + 1 + "]");
							ext_td.addAttribute("value", pre_ext_td_value
									+ "_col[" + l + 1 + "]");
							ext_td.addText("");
						} else {
							if (this.cols[l].getOutputtype() != null
									&& this.cols[l].getOutputtype().equals("function")) {
								td_value= getCellValue(this.cols[l], td_value, xmap,null);
							} 
							else {
								td_value = ""+sum(getColumns(doc, l,startArr[k],endArr[k]));
								td_value=formatStr(this.cols[l],td_value);
							}										
							ext_td.addAttribute("align", "center");
							ext_td.addText(td_value);
							if (rownum % 2 == 0) {
								ext_td.addAttribute("class", "tr_odd");
							} else {
								ext_td.addAttribute("class", "tr_even");
							}
						}
						dealHiddenColumn(this.cols[l],ext_td);
					
//						-----------align 样式定制-----------------------------
						if (this.cols[l].getTextAlign() != null) {
							ext_td.addAttribute("align", this.cols[l]
									.getTextAlign());
						} else {
							if (this.cols[l].getType().equals("measure")) {
								ext_td.addAttribute("align", "right");
							} else {
								ext_td.addAttribute("align", "left");
							}
						}
						//-----------align 样式定制-----------------------------
						l++;
					}
					rownum++;
					rowPoseEnd++;
					startArr[k] = rowPoseEnd;
				}
			}
		}
	}
	/**
	 * 处理隐藏列
	 * @param _col
	 * @param _td
	 */
    private void dealHiddenColumn(Column _col,DOMElement _td){
		if("1".equals(_col.getHidden())){
			_td.addAttribute("class", "hiddenColumn");
		}
    }
	private void insertTotalSum(Document doc) {
		DOMElement body = (DOMElement) doc.selectSingleNode("/table/tbody");
		DOMElement last_tr = new DOMElement("tr");
		last_tr.addAttribute("type", "ext");
		DOMElement last_td = null;
		Map xmap = new HashMap();
		for (int i = 0; i < this.cols.length; i++) {
			if (this.cols[i].getType().equals("measure")) {
				last_td = new DOMElement("td");
				if (this.cols[i].getOutputtype() != null
						&& this.cols[i].getOutputtype().equals("function")) {
					continue;
				}
				String td_value = ""
						+ sum(getColumns(doc, i, 1, body.nodeCount() + 1));
				xmap.put(this.cols[i].getCode(), td_value);
			}
		}
		for (int i = 0; i < this.cols.length; i++) {
			if (this.cols[i].getType().equals("dim")) {
				last_td = new DOMElement("td");
				last_tr.appendChild(last_td);
				if (i == 0) {
					last_td.addText(this.cols[i].getSumname());
					last_td.addAttribute("groupvalue", "total-sum");
					last_td.addAttribute("value", "total-sum-" + (i + 1));
				} else {
					DOMElement pre_last_td = (DOMElement) last_td
							.getPreviousSibling();
					String sdx = pre_last_td.attributeValue("groupvalue");
					last_td.addText("");
					last_td.addAttribute("value", "total-sum-" + (i + 1));
					last_td.addAttribute("groupvalue", sdx + "_" + (i + 1));
				}
			}
			if (this.cols[i].getType().equals("measure")) {
				last_td = new DOMElement("td");
				String td_value = "";
				if (this.cols[i].getOutputtype() != null
						&& this.cols[i].getOutputtype().equals("function")) {
					String fnb_value = this.cols[i].getColumValue();
					fnb_value = RegExUtil.replaceParam(fnb_value, xmap);
					td_value = JavaScriptParser.getInstance().evalFunction(
							fnb_value).toString();
				} else {
					td_value = ""
							+ sum(getColumns(doc, i, 1, body.nodeCount() + 1));
				}
				String formatStr = this.cols[i].getFormat() == null ? ""
						: this.cols[i].getFormat();
				if (!formatStr.equals("")) {
					td_value = SimpleFormat.formatStr(td_value, formatStr);
				} else {
					td_value = SimpleFormat.formatStr(td_value, "#.##");
				}
				if (rownum % 2 == 0) {
					last_td.addAttribute("class", "tr_odd");
				} else {
					last_td.addAttribute("class", "tr_even");
				}
				dealHiddenColumn(this.cols[i],last_td);
				
//				-----------align 样式定制-----------------------------
				if (this.cols[i].getTextAlign() != null) {
					last_td.addAttribute("align", this.cols[i]
							.getTextAlign());
				} else {
					if (this.cols[i].getType().equals("measure")) {
						last_td.addAttribute("align", "right");
					} else {
						last_td.addAttribute("align", "left");
					}
				}
				//-----------align 样式定制-----------------------------
				last_td.addText(td_value);				
				last_tr.appendChild(last_td);
			}
		}
		rownum++;
		if (this.cols[0].getSumpos() != null
				&& this.cols[0].getSumpos().equals("before")) {
			body.insertBefore(last_tr, body.getFirstChild());
		} else {
			if (this.cols[0].getSummary() != null
					&& this.cols[0].getSummary().equals("1")) { // 这里考虑完全没有合计只有分组的表
				body.appendChild(last_tr);
			}
		}
	}

	private List getColumns(Document doc, int index, int start, int end) {
		List li = new ArrayList();
		for (int i = start; i < end; i++) {
			DOMElement tr_element = (DOMElement) doc
					.selectSingleNode("/table/tbody/tr[" + (i) + "]");
			DOMElement td_element = (DOMElement) doc
					.selectSingleNode("/table/tbody/tr[" + (i) + "]/td["
							+ (index + 1) + "]");
			if (!tr_element.attributeValue("type").equals("ext")) {
				li.add(td_element.attributeValue("value"));
			}
		}
		return li;
	}

	private int tdAcrossMerge(Document doc, int index, int start) {
		DOMElement body = (DOMElement) doc.selectSingleNode("/table/tbody");
		int start1 = tdMerge(doc, index, start);
		if (start1 > 1 && start1 < body.nodeCount())
			start1 = tdAcrossMerge(doc, index, start1);
		return start1;
	}

	private int tdMerge(Document doc, int index, int start) {
		DOMElement body = (DOMElement) doc.selectSingleNode("/table/tbody");
		int counter = start;
		DOMElement start_td = (DOMElement) body
				.selectSingleNode("/table/tbody/tr[" + start + "]/td["
						+ (index) + "]");
		if (start_td == null) {
			return 1;
		}
		String start_groupvalue = start_td.attributeValue("groupvalue");
		String next_groupvalue = "";
		do {
			DOMElement next_tr = (DOMElement) body
					.selectSingleNode("/table/tbody/tr[" + (counter + 1) + "]");
			if (next_tr == null) {
				break;
			}
			DOMElement next_td = (DOMElement) body
					.selectSingleNode("/table/tbody/tr[" + (counter + 1)
							+ "]/td[" + (index) + "]");
			next_groupvalue = next_td.attributeValue("groupvalue");
			if (start_groupvalue.equals(next_groupvalue)) {

				if (start_td.hasAttribute("rowspan")) {
					int rowspan = Integer.parseInt(start_td
							.attributeValue("rowspan")) + 1;
					start_td.setAttribute("rowspan", "" + rowspan);
				} else {
					start_td.addAttribute("rowspan", "2");
				}
				next_tr.remove(next_td);
			}
			counter++;
		} while (start_groupvalue.equals(next_groupvalue));
		return counter;
	}
	
	private String formatStr(Column col, String oriValue){
		String formatStr = col.getFormat() == null ? "" : col.getFormat();
		if (!formatStr.equals("")) {
			oriValue = SimpleFormat.formatStr(oriValue, formatStr);
		} else {
			oriValue = SimpleFormat.formatStr(oriValue, "#.##");
		}
		return oriValue;
	}
	
	private String getCellValue(Column col, String oriValue, Map dataMap,
			String[] paramValues) {

		if (col.getOutputtype() != null
				&& col.getOutputtype().equals("function")) {
			oriValue = col.getColumValue();
			oriValue = RegExUtil.replaceCon(oriValue, this.conditions,
					paramValues);
			oriValue = RegExUtil.replaceParam(oriValue, dataMap);
			oriValue = JavaScriptParser.getInstance().evalFunction(
					oriValue).toString();
		}
		return formatStr(col,oriValue);
	}

	public void setCols(Column[] cols) {
		this.cols = cols;
		for (int i = 0; i < this.cols.length; i++) {
			if (cols[i].getSummary() != null
					&& cols[i].getSummary().equals("1")) {
				String sumby = cols[i].getSumby();
				if (sumby.equals("")) {
					this.afterList.add("0");
				} else {
					String[] str = sumby.split(",");
					this.afterList.add("" + str.length);
				}
			}
		}
		java.util.Collections.sort(this.afterList, new MyComparator());
	}

	public static void main(String[] args) {
		// 每列必须有type----measure,dim
		// 每列必须有summary----0,1
		List li = new ArrayList();

		Column col = new Column();
		col.setName("country");
		col.setCode("country");
		col.setType("dim");
		col.setGroup("1");
		col.setSumby("");
		col.setSummary("1");
		col.setSumpos("after");
		col.setSumname("总计");

		Column col1 = new Column();
		col1.setName("province");
		col1.setCode("province");
		col1.setType("dim");
		col1.setGroup("1");
		col1.setSumby("col");
		col1.setSummary("1");
		col1.setSumpos("after");
		col1.setSumname("合计");

		Column col2 = new Column();
		col2.setName("city");
		col2.setCode("city");
		col2.setType("dim");
		col2.setSumby("col,col1");
		col2.setSummary("1");
		col2.setSumpos("after");
		col2.setSumname("小计");

		Column col3 = new Column();
		col3.setName("sales1");
		col3.setCode("sales1");
		col3.setType("measure");
		col3.setSummary("0");

		Column col4 = new Column();
		col4.setName("sales2");
		col4.setCode("sales2");
		col4.setType("measure");
		col4.setSummary("0");

		Column col5 = new Column();
		col5.setName("sales3");
		col5.setCode("sales3");
		col5.setType("measure");
		col5.setSummary("0");

		Column col6 = new Column();
		col6.setName("sales4");
		col6.setCode("sales4");
		col6.setSummary("0");
		col6.setType("measure");

		Column col7 = new Column();
		col7.setName("sales5");
		col7.setCode("sales5");
		col7.setType("measure");
		col7.setSummary("0");

		Column[] cols = { col, col1, col2, col3, col4, col5, col6, col7 };

		HashMap a = new HashMap();
		a.put("country", "信函");
		a.put("province", "北京");
		a.put("city", "西城区");
		a.put("sales1", "300");
		a.put("sales2", "300");
		a.put("sales3", "300");
		a.put("sales4", "300");
		a.put("sales5", "300");
		li.add(0, a);

		HashMap b = new HashMap();
		b.put("country", "信函");
		b.put("province", "北京");
		b.put("city", "东城区");
		b.put("sales1", "200");
		b.put("sales2", "200");
		b.put("sales3", "200");
		b.put("sales4", "200");
		b.put("sales5", "200");
		li.add(1, b);

		HashMap c = new HashMap();
		c.put("country", "信函");
		c.put("province", "上海");
		c.put("city", "浦东");
		c.put("sales1", "100");
		c.put("sales2", "100");
		c.put("sales3", "100");
		c.put("sales4", "100");
		c.put("sales5", "100");
		li.add(2, c);

		HashMap d = new HashMap();
		d.put("country", "信函");
		d.put("province", "上海");
		d.put("city", "浦东");
		d.put("sales1", "220");
		d.put("sales2", "220");
		d.put("sales3", "220");
		d.put("sales4", "220");
		d.put("sales5", "220");
		li.add(3, d);

		HashMap e = new HashMap();
		e.put("country", "邮件");
		e.put("province", "北京");
		e.put("city", "西城区");
		e.put("sales1", "220");
		e.put("sales2", "220");
		e.put("sales3", "220");
		e.put("sales4", "220");
		e.put("sales5", "220");
		li.add(4, e);

		HashMap f = new HashMap();
		f.put("country", "邮件");
		f.put("province", "北京");
		f.put("city", "东城区");
		f.put("sales5", "220");
		f.put("sales1", "220");
		f.put("sales2", "220");
		f.put("sales3", "220");
		f.put("sales4", "220");
		li.add(5, f);

		HashMap g = new HashMap();
		g.put("country", "邮件");
		g.put("province", "上海");
		g.put("city", "浦东");
		g.put("sales1", "220");
		g.put("sales2", "220");
		g.put("sales3", "220");
		g.put("sales4", "220");
		g.put("sales5", "220");
		li.add(6, g);

		HashMap h = new HashMap();
		h.put("country", "邮件");
		h.put("province", "上海");
		h.put("city", "浦西");
		h.put("sales1", "220");
		h.put("sales2", "220");
		h.put("sales3", "220");
		h.put("sales4", "220");
		h.put("sales5", "220");
		li.add(7, h);

		HashMap h1 = new HashMap();
		h1.put("country", "邮件");
		h1.put("province", "上海");
		h1.put("city", "浦西");
		h1.put("sales1", "220");
		h1.put("sales2", "220");
		h1.put("sales3", "220");
		h1.put("sales4", "220");
		h1.put("sales5", "220");
		li.add(8, h1);

		HashMap h2 = new HashMap();
		h2.put("country", "邮件");
		h2.put("province", "江苏");
		h2.put("city", "南京");
		h2.put("sales1", "200");
		h2.put("sales2", "200");
		h2.put("sales3", "200");
		h2.put("sales4", "200");
		h2.put("sales5", "200");
		li.add(9, h2);

		HashMap h3 = new HashMap();
		h3.put("country", "邮件");
		h3.put("province", "江苏");
		h3.put("city", "苏州");
		h3.put("sales1", "210");
		h3.put("sales2", "210");
		h3.put("sales3", "210");
		h3.put("sales4", "210");
		h3.put("sales5", "210");
		li.add(10, h3);

		HashMap h4 = new HashMap();
		h4.put("country", "邮件");
		h4.put("province", "江苏");
		h4.put("city", "苏州");
		h4.put("sales1", "220");
		h4.put("sales2", "220");
		h4.put("sales3", "220");
		h4.put("sales4", "220");
		h4.put("sales5", "220");
		li.add(11, h4);
		ListGrid fs = ListGridFactory.getGrid(cols);
		fs.setCols(cols);
		// fs.setDataList(li);
		// String s = fs.toHtml();
		// System.out.println(s);
	}

	public String formatContext(List data, List allList, String[] paramValues) {
		return this.toHtml(data, allList, paramValues);
	}

	public String getTemplateVm() {
		// TODO Auto-generated method stub
		return this.templatefilename;
	}
}
