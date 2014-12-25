package com.teamsun.framework.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.dom4j.Document;
import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMElement;
import com.teamsun.framework.util.RegExUtil;
import com.teamsun.thunderreport.parse.Condition;
import com.teamsun.thunderreport.parse.Dim;
import com.teamsun.thunderreport.parse.Title;
import com.teamsun.thunderreport.parse.Measure;

public class BackupOfCrossGrid extends ListGrid
{
	private Dim[] rowDims;

	private Dim[] colDims;
	
	private Title[] title;

	private Measure[] measures;

	private String measureCrossWith;

	private boolean isMeasureDisplay;

	public void setConditions(Condition[] conditions)
	{
		this.conditions = conditions;
	}



	public void setReportFooterOutType(String reportFooterOutType)
	{
		this.reportFooterOutType = reportFooterOutType;
	}

	public void setReportFooterValue(String reportFooterValue)
	{
		this.reportFooterValue = reportFooterValue;
	}

	public void setReportHeaderOutType(String reportHeaderOutType)
	{
		this.reportHeaderOutType = reportHeaderOutType;
	}

	public void setReportHeaderOutValue(String reportHeaderOutValue)
	{
		this.reportHeaderOutValue = reportHeaderOutValue;
	}

	public void setColDims(Dim[] colDims)
	{
		this.colDims = colDims;
	}
    public void setTitle(Title[] title){
    	this.title=title;
    }
	public void setMeasures(Measure[] measures)
	{
		this.measures = measures;
		this.isMeasureDisplay = Boolean.valueOf(measures[0].display)
				.booleanValue();
		this.measureCrossWith = measures[0].crosswith;
	}

	public void setRowDims(Dim[] rowDims)
	{
		this.rowDims = rowDims;
	}



	private String join(String[] strarr)
	{
		String u = "";
		for (int i = 0; i < strarr.length; i++)
		{
			if (i != 0)
				u += "__";
			u += strarr[i];
		}
		return u;
	}

	private List tdXmap(TreeMap xMap)
	{
		List li = new ArrayList();
		Iterator it = xMap.keySet().iterator();
		while (it.hasNext())
		{
			String s = it.next().toString();
			String[] arr = s.split("__");
			li.add(arr);
		}
		List li1 = new ArrayList();
		int[] startcounters = new int[this.rowDims.length];
		for (int i = 0; i < startcounters.length; i++)
		{
			startcounters[i] = 0;
		}
		int rowposEnd = 0;
		for (int i = 0; i < li.size(); i++)
		{
			String[] cur = (String[]) li.get(i);
			String key = join(cur);
			li1.add(key);
			if (i > 0)
			{
				for (int a = this.rowDims.length - 1; a >= 0; a--)
				{
					if (this.rowDims[a].getSummary() == null
							|| !this.rowDims[a].getSummary().equals("1"))
					{
						continue;
					}
					String[] pre = (String[]) li.get(i - 1);
					int b = a - 1;
					boolean be = true;
					while (b >= 0)
					{
						be = be && cur[b].equals(pre[b]);
						b--;
					}
					if (!be)
					{
						String ss = "";
						for (int j = 0; j < pre.length; j++)
						{
							if (j < a)
							{
								ss += pre[j] + "__";
								continue;
							} else
							{
								if (j == a)
								{
									ss += this.rowDims[a].getSumname();
								} else
								{
									ss += "__" + " ";
								}
							}
						}
						if (this.rowDims[a].getSumpos() != null
								&& this.rowDims[a].getSumpos().equals("before"))
						{
							li1.add(startcounters[a], ss);
						} else
						{
							li1.add(rowposEnd, ss);
						}
						rowposEnd++;
						for (int k = a; k < this.rowDims.length; k++)
							startcounters[k] = rowposEnd;
					}
				}
			}
			rowposEnd++;
		}
		for (int j = this.rowDims.length - 1; j >= 0; j--)
		{
			String[] key_arr = (String[]) li.get(li.size() - 1);
			if (this.rowDims[j].getSummary() == null
					|| !this.rowDims[j].getSummary().equals("1"))
			{
				continue;
			} else
			{
				String lastkey = "";
				if (j == 0)
				{
					for (int n = 0; n < this.rowDims.length; n++)
					{
						if (n == 0)
						{
							lastkey += this.rowDims[n].getSumname();
						} else
						{
							lastkey += "__" + " ";
						}
					}
					if (this.rowDims[j].getSumpos() != null
							&& this.rowDims[j].getSumpos().equals("before"))
					{
						li1.add(0, lastkey);
					} else
					{
						li1.add(lastkey);
					}
				} else
				{
					int m;
					for (m = 0; m < j; m++)
					{
						if (m != 0)
							lastkey += "__";
						lastkey += key_arr[m].toString();
					}
					for (int l = m; l < this.rowDims.length; l++)
					{
						if (l == j)
						{
							lastkey += "__" + this.rowDims[l].getSumname();
						} else
						{
							lastkey += "__" + " ";
						}
					}
					if (this.rowDims[j].getSumpos() != null
							&& this.rowDims[j].getSumpos().equals("before"))
					{
						li1.add(startcounters[j], lastkey);
					} else
					{
						li1.add(rowposEnd, lastkey);
					}
					rowposEnd++;
					for (int k = j; k < this.rowDims.length; k++)
						startcounters[k] = rowposEnd;
				}
			}
		}
		return li1;
	}

	private List tdYmap2(LinkedHashMap yMap)
	{
		List li = new ArrayList();
		Iterator it = yMap.keySet().iterator();
		while (it.hasNext())
		{
			String s = it.next().toString();
			String[] arr = s.split("__");
			li.add(arr);
		}
		List li1 = new ArrayList();
		int[] startcounters = new int[this.colDims.length];
		for (int i = 0; i < startcounters.length; i++)
		{
			startcounters[i] = 0;
		}
		int rowposEnd = 0;
		for (int i = 0; i < li.size(); i++)
		{
			String[] cur = (String[]) li.get(i);
			String key = join(cur);
			li1.add(key);
			if (i > 0)
			{
				for (int a = this.colDims.length - 1; a >= 0; a--)
				{
					if (this.colDims[a].getSummary() == null
							|| !this.colDims[a].getSummary().equals("1"))
					{
						continue;
					}
					String[] pre = (String[]) li.get(i - 1);
					int b = a - 1;
					boolean be = true;
					while (b >= 0)
					{
						be = be && cur[b].equals(pre[b]);
						b--;
					}
					if (!be)
					{
						String ss = "";
						for (int j = 0; j < pre.length; j++)
						{
							if (j < a)
							{
								ss += pre[j] + "__";
								continue;
							} else
							{
								if (j == a)
								{
									ss += this.colDims[a].getSumname();
								} else
								{
									ss += "__" + " ";
								}
							}
						}
						if (this.colDims[a].getSumpos() != null
								&& this.colDims[a].getSumpos().equals("before"))
						{
							li1.add(startcounters[a], ss);
						} else
						{
							li1.add(rowposEnd, ss);
						}
						rowposEnd++;
						for (int k = a; k < this.colDims.length; k++)
							startcounters[k] = rowposEnd;
					}
				}
			}
			rowposEnd++;
		}
		for (int j = this.colDims.length - 1; j >= 0; j--)
		{
			String[] key_arr = (String[]) li.get(li.size() - 1);
			if (this.colDims[j].getSummary() == null
					|| !this.colDims[j].getSummary().equals("1"))
			{
				continue;
			} else
			{
				String lastkey = "";
				if (j == 0)
				{
					for (int n = 0; n < this.colDims.length; n++)
					{
						if (n == 0)
						{
							lastkey += this.colDims[n].getSumname();
						} else
						{
							lastkey += "__" + " ";
						}
					}
					if (this.colDims[j].getSumpos() != null
							&& this.colDims[j].getSumpos().equals("before"))
					{
						li1.add(0, lastkey);
					} else
					{
						li1.add(lastkey);
					}
				} else
				{
					int m;
					for (m = 0; m < j; m++)
					{
						if (m != 0)
							lastkey += "__";
						lastkey += key_arr[m].toString();
					}
					for (int l = m; l < this.colDims.length; l++)
					{
						if (l == j)
						{
							lastkey += "__" + this.colDims[l].getSumname();
						} else
						{
							lastkey += "__" + " ";
						}
					}
					if (this.colDims[j].getSumpos() != null
							&& this.colDims[j].getSumpos().equals("before"))
					{
						li1.add(startcounters[j], lastkey);
					} else
					{
						li1.add(rowposEnd, lastkey);
					}
					rowposEnd++;
					for (int k = j; k < this.colDims.length; k++)
						startcounters[k] = rowposEnd;
				}
			}
		}
		return li1;
	}

	// measure cross coldim
	private String toHtmlX()
	{
		return "";
		
	}

	// measure cross rowdim
	private String toHtmlY(List dataList,String[] paramValues)
	{
		// 度量:key---- county_city_product_season_0 , value ----度量值

		Map sMap = new HashMap();
		// 行维坐标:key---- county_city ,value---"1"
		Map xMap = new TreeMap();
		// 列维坐标:key---- product_season ,value---"1"
		Map yMap = new LinkedHashMap();
		//行维坐标_列维坐标：key---country_city_product_season,value---"1"
		Map sMap4 = new HashMap();

		// assemble key的map ----start
		for (int k = 0; k < dataList.size(); k++)
		{
			Map map = (Map) dataList.get(k);
			String x = "";
			String y = "";
			// 确保行维和列维在xml文件中的排列顺序准确
			for (int i = 0; i < this.rowDims.length; i++)
			{
				String rowDimName = this.rowDims[i].getCode();
				String rowDimValue = map.get(rowDimName).toString();
				if (i != 0)
					x += "__";
				x += rowDimValue;

			}
			xMap.put(x, "1");
			for (int i = 0; i < this.colDims.length; i++)
			{
				String colDimName = this.colDims[i].getCode();
				String colDimValue = map.get(colDimName).toString();
				if (i != 0)
					y += "__";
				y += colDimValue;
			}
			yMap.put(y, "1");
			sMap4.put(x + "__" + y, "1");
			for (int i = 0; i < this.measures.length; i++)
			{
				String m = y + "__" + i;
				String path = x + "__" + m;
				String measureName = this.measures[i].getCode();
				String measureValue = map.get(measureName).toString();
				sMap.put(path, measureValue);
			}
		}// assemble key的map ----end

		List lie = tdYmap2((LinkedHashMap) yMap);
		List liwe = this.tdXmap((TreeMap) xMap);
		DOMDocument document = new DOMDocument();
		DOMElement root = (DOMElement) document.addElement("table");
		DOMElement tr = null;
		root.addAttribute("style", "border-collapse:collapse;");
		root.addAttribute("border", "1px solid #efefef");
		root.addAttribute("width", "100%");
		root.addAttribute("bordercolor", "#999999");
		root.addAttribute("cellspacing", "0");
		root.addAttribute("cellpadding", "3");
		root.addAttribute("class", "thunderreport");

		// 列维的表头--start
		int rowspan = colDims.length;
		if (this.isMeasureDisplay == true)
		{
			rowspan = rowspan + 1;
		}
		for (int h = 0; h < rowspan; h++)
		{
			DOMElement colhead_tr = new DOMElement("tr");
			if (h == 0)
			{
				
			  if(this.title!=null){	
				for(int icount=0;icount<this.title.length;icount++){
					DOMElement colheadtd = new DOMElement("td");
					colhead_tr.appendChild(colheadtd);
					colheadtd.setText(this.title[icount].getTitleText());
					colheadtd.addAttribute("class", "tr_head");
				}
			  }else{
				  DOMElement colheadtd = new DOMElement("td");
				  colhead_tr.appendChild(colheadtd);
            		colheadtd.addAttribute("colspan",
					String.valueOf(rowDims.length)).addAttribute("rowspan",
					String.valueOf(rowspan)).addAttribute("class",
					"listColumnTitle").addAttribute("value", "*#*#*#*");
					colheadtd.addAttribute("class", "tr_head");
			  }
			}
			root.appendChild(colhead_tr);
		}
		Iterator it0 = lie.iterator();
		int je = 0;
		
		while (it0.hasNext())
		{  
			String[] coltd_arr = it0.next().toString().split("__");
			System.out.println("coltd_arr="+coltd_arr[0]);
			for (int h = 0; h < colDims.length; h++)
			{
				DOMElement colhead_tr = (DOMElement) document
						.selectSingleNode("/table/tr[" + (h + 1) + "]");
				for (int y = 0; y < this.measures.length; y++)
				{
					String groupvalue = "";
					DOMElement colheader_td = new DOMElement("td");
					colheader_td.addAttribute("colspan", "1");
					colheader_td.addAttribute("class", "tr_head");
					if(coltd_arr.length>1){
					   colheader_td.addText(coltd_arr[h]);
					}
					colhead_tr.appendChild(colheader_td);
					if(coltd_arr.length>1){
					if (coltd_arr[h] == null || coltd_arr[h].equals(" "))
					{
						coltd_arr[h] = (je + 1) + "-" + (h + 1);
					}
					}
					DOMElement colhead_pre_tr = (DOMElement) colhead_tr
							.getPreviousSibling();
					if (colhead_pre_tr != null)
					{
						DOMElement colhead_pre_td = (DOMElement) colhead_pre_tr
								.getChildNodes().item(h);
						groupvalue += colhead_pre_td
								.attributeValue("groupvalue")
								+ "__" + coltd_arr[h];
					} else
					{
						groupvalue += coltd_arr[h];
					}
					
					colheader_td.addAttribute("groupvalue", groupvalue);
				}
			}
			je++;
		}

		if (this.isMeasureDisplay == true)
		{
			DOMElement measure_tr = (DOMElement) document
					.selectSingleNode("/table/tr[" + (rowspan) + "]");
			for (int u = 0; u < lie.size(); u++)
			{   	 
				
				for (int y = 0; y < this.measures.length; y++)
				{
					DOMElement measure_td = new DOMElement("td");
					measure_td.addText(this.measures[y].getName());
					measure_tr.appendChild(measure_td);
				}
				
			}
		}// 列维的表头--end

		// 行维及表的主体部分----start
		int row = rowspan;
         int cnum1=0;
     
		for (Iterator it = liwe.iterator(); it.hasNext();)
		{   
			cnum1++;
			tr = new DOMElement("tr");
			root.appendChild(tr);

			String xkey = (String) it.next();
			String[] keyArr = xkey.split("__");// "country_city"
			DOMElement td = null;

			// 每一行的行维的表头显示 ---start
			for (int i = 0; i < this.rowDims.length; i++)
			{
				String groupvalue = "";
				td = new DOMElement("td");
				tr.appendChild(td);
				String td_text = keyArr[i];
				if (keyArr[i] == null || keyArr[i].equals(" "))
				{
					// 合计小计行的值肯定为空，加一个值为了后面合并的判断
					keyArr[i] = (row + 1) + "-" + (i + 1);
				}
				for (int k = 0; k <= i; k++)
				{
					if (k != 0)
						groupvalue += "__";
					groupvalue += keyArr[k];
				}
				td.addAttribute("value", keyArr[i]);
				td.addAttribute("groupvalue", groupvalue);
				if(i>1){
					if(cnum1%2==0){
						td.addAttribute("class", "tr_even");
					}else{
						td.addAttribute("class", "tr_odd");
					}
					
				}
				if(keyArr[i].equals("小计")||keyArr[i].equals("合计")||keyArr[i].equals("总计")){
					td.addAttribute("style", "FONT-WEIGHT:bold");
				}
//				if(this.rowDims[i].getName().equals("RANK")){
//					System.out.println(this.rowDims[i].getSummary());
//					System.out.println("text==== "+td_text);
//				}
				if(this.rowDims[i].getSummary().equals("0")){
					//System.out.println(this.rowDims[i].getCode());
					if(td_text.equals("")||td_text==null||td_text.equals(" ")){
						//System.out.println("111111");
						td_text="--";
					}
				}
				td.addAttribute("rowspan",
						"1").addText(td_text);
				
		

			}// 每一行的行维的表头显示 ---end

			// 画表体数据--start
			String key = "";
		
			for (Iterator itt = lie.iterator(); itt.hasNext();)
			{   
				String ykey = (String) itt.next();
				for (int k = 0; k < this.measures.length; k++)
				{   
					
					key = xkey + "__" + ykey + "__" + String.valueOf(k);
					
					if (yMap.containsKey(ykey) && xMap.containsKey(xkey))
					{
						Object value = sMap.get(key);
						if (value == null)
						{
							value = "0";
						}
						td = new DOMElement("td");
						tr.appendChild(td);
					    td.addAttribute("class", "listColumn");
					    if(cnum1%2==0){
							td.addAttribute("class", "tr_even");
						}else{
							td.addAttribute("class", "tr_odd");
						}
						td.addText(value.toString());
					}
					//行维有小计合计项
					if (yMap.containsKey(ykey) && !xMap.containsKey(xkey))
					{
						td = new DOMElement("td");
						tr.appendChild(td);
						td.addAttribute("class", "listColumn");
						List li = getSumList(sMap4, "xkey", xkey, ykey);
						if(cnum1%2==0){
							td.addAttribute("class", "tr_even");
						}else{
							td.addAttribute("class", "tr_odd");
						}
						if (li.size() == 0)
						{   
							
							td.addText("0");
							td.addAttribute("style", "FONT-WEIGHT:bold");
						} else
						{
//							double value = 0.0;
//							for (int i = 0; i < li.size(); i++)
//							{
//								value += Double
//										.parseDouble(sMap.get(
//												li.get(i).toString() + "_"
//														+ String.valueOf(k))
//												.toString());
//							}
							int value=0;
							
							for (int i = 0; i < li.size(); i++){
								value+=Integer.parseInt(sMap.get(
												li.get(i).toString() + "__"
														+ String.valueOf(k))
												.toString());
							}
						
							td.addText("" + value);
							td.addAttribute("style", "FONT-WEIGHT:bold");
						}
					
					}
					//列维有小计合计项
					if (!yMap.containsKey(ykey) && xMap.containsKey(xkey))
					{
						td = new DOMElement("td");
						tr.appendChild(td);
						td.addAttribute("class", "listColumn");
						if(cnum1%2==0){
							td.addAttribute("class", "tr_even");
						}else{
							td.addAttribute("class", "tr_odd");
						}
						List li = getSumList(sMap4, "ykey", xkey, ykey);
						if (li.size() == 0)
						{   
							td.addText("0");
						} else
						{
//							double value = 0.0;
//							for (int i = 0; i < li.size(); i++)
//							{
//								value += Double
//										.parseDouble(sMap.get(
//												li.get(i).toString() + "_"
//														+ String.valueOf(k))
//												.toString());
//							}
						    //int value=0;
							String value="";
							for (int i = 0; i < li.size(); i++){
//								value+=Integer.parseInt(sMap.get(
//												li.get(i).toString() + "__"
//														+ String.valueOf(k))
//												.toString());
							    value+=sMap.get(li.get(i).toString() + "__"
										+ String.valueOf(k)).toString();
							}
						
							td.addText("" + value);
						}
					}
//					行维和列维同时都是合计项
					if (!yMap.containsKey(ykey) && !xMap.containsKey(xkey))
					{
						td = new DOMElement("td");
						tr.appendChild(td);
						
						//td.addAttribute("class", "listColumn");
			
						String[] xkstr=xkey.split("__");
						String xstr="";
						for(int i=0;i<xkstr.length-1;i++){
						  if(!xkstr[i].equals(" ")&&xkstr[i]!=null){	
							if(i==0){
							  xstr+=xkstr[i];
							}else{
							   if(!xkstr[i].equals("合计")&&(!xkstr[i].equals("小计"))){
							     xstr=xstr+"__"+xkstr[i]; 	
							   }
							}
						  }
						}
						int sumcount=0;
						int sumallcount=0;
						
						for(Iterator iter = sMap.entrySet().iterator();iter.hasNext();){
							Map.Entry entry = (Map.Entry) iter.next();
                            String tempstr=entry.getKey().toString();
                            String[] xkstr1=tempstr.split("__");
    						String xstr1="";
                            for(int i=0;i<xkstr1.length-2;i++){
      						  if(!xkstr1[i].equals(" ")&&xkstr1[i]!=null){	
      							if(i==0){
      							  xstr1+=xkstr1[i];
      							}else{
      							     xstr1=xstr1+"__"+xkstr1[i]; 	
      							}
      						  }
      						}
//                            System.out.println(xstr);
//                            System.out.println("====="+xstr);
							if(xstr1.indexOf(xstr)!=-1){	
									if(xstr.split("__").length==1){
										if(xstr.equals(xstr1.split("__")[0])){
											sumcount=sumcount+Integer.parseInt(entry.getValue().toString());
										}
									}else{
										sumcount=sumcount+Integer.parseInt(entry.getValue().toString());
									}
							}else{
//								System.out.println(xstr);
//								System.out.println("+++++"+xstr1);
								if(xstr.split("__").length>1){
									if((xstr.split("__")[0]+"__"+xstr.split("__")[1]).equals((xstr1.split("__")[0]+"__"+xstr1.split("__")[1]))){
										sumcount=sumcount+Integer.parseInt(entry.getValue().toString());
									}
//									System.out.println(xstr);
//									System.out.println(xstr.split("_")[0]+"_"+xstr.split("_")[1]);
									
								}
							}
//							System.out.println(xkey);
//							System.out.println("------"+entry.getKey().toString());
						   sumallcount=sumallcount+Integer.parseInt(entry.getValue().toString());
						   
// System.out.println("Key:"+entry.getKey());
//	System.out.println("Value:"+entry.getValue());
						} 
	
                     if(xkey.indexOf("总计")!=-1&&xstr.equals("总计")	){
                    	 td.addText(String.valueOf(sumallcount));
                     }else{
                    	 if(xstr.equals("合计")){
                    		 td.addText(String.valueOf(sumallcount));
                    	 }else{
                    	     td.addText(String.valueOf(sumcount));
                    	 }
                     }

						if(cnum1%2==0){
							td.addAttribute("class", "tr_even");
						}else{
							td.addAttribute("class", "tr_odd");
						}
						td.addAttribute("style", "FONT-WEIGHT:bold");
					}
					
				}
			}// 画表体数据--end
			row++;

			
		}// 行维及表的主体部分----end
		// 合并行维--start
		for (int i = this.rowDims.length - 1; i >= 0; i--)
		{
            //  this.rowDims[i].setGroup("1");
			if (this.rowDims[i].getGroup() != null
					&& this.rowDims[i].getGroup().equals("1"))
			{
				tdAcrossMerge(document, i + 1, rowspan + 1);
			}

		}//合并行维--end
		//合并列维--start
		for(int i=1;i<=this.colDims.length;i++)
		{
			tdYMerge(document,i);
		}//合并列维--end
		String ss = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		String table = document.asXML().substring(ss.length(),
				document.asXML().length()).trim();
		return table;
	}
	
	private int tdAcrossMerge(Document doc, int index, int start)
	{
		DOMElement body = (DOMElement) doc.getRootElement();
		int start1 = tdXMerge(doc, index, start);
		if (start1 > 1 && start1 < body.nodeCount())
			start1 = tdAcrossMerge(doc, index, start1);
		return start1;
	}

	private void tdYMerge(Document doc,int rownum)
	{
		DOMElement root = (DOMElement) doc.getRootElement();
		DOMElement cur_tr = (DOMElement) root.selectSingleNode("/table/tr["+rownum+"]");
		Iterator it=cur_tr.elementIterator();
     
		while(it.hasNext())
		{   
			DOMElement cur_td=(DOMElement)it.next();
			DOMElement pre_td=(DOMElement) cur_td.getPreviousSibling();
			if(pre_td!=null)
			{
			//	String pre_groupvalue=pre_td.attributeValue("groupvalue");
			//	if(cur_groupvalue.equals(pre_groupvalue))
			//	{
			//		int colspan=Integer.parseInt(pre_td.attributeValue("colspan"))+1;
			//		pre_td.addAttribute("colspan", colspan+"");
			//		it.remove();
			//	}
			}
		}		
	}

	private int tdXMerge(Document doc, int index, int start)
	{
		// start 从rowspan+1 开始计数， index 是要合并的列号

		DOMElement body = (DOMElement) doc.getRootElement();
		int counter = start;
		DOMElement start_td = (DOMElement) body.selectSingleNode("/table/tr["
				+ start + "]/td[" + (index) + "]");
		String start_groupvalue = start_td.attributeValue("groupvalue");
		String next_groupvalue = "";
		do
		{
			DOMElement next_tr = (DOMElement) body
					.selectSingleNode("/table/tr[" + (counter + 1) + "]");
			if (next_tr == null)
			{
				break;
			}
			DOMElement next_td = (DOMElement) body
					.selectSingleNode("/table/tr[" + (counter + 1) + "]/td["
							+ (index) + "]");
			next_groupvalue = next_td.attributeValue("groupvalue");
			if (start_groupvalue.equals(next_groupvalue))
			{

				if (start_td.hasAttribute("rowspan"))
				{
					int rowspan = Integer.parseInt(start_td
							.attributeValue("rowspan")) + 1;
					start_td.setAttribute("rowspan", "" + rowspan);
				} else
				{
					start_td.addAttribute("rowspan", "2");
				}
				next_tr.remove(next_td);
			}
			counter++;
		} while (start_groupvalue.equals(next_groupvalue));
		return counter;
	}

	private List getSumList(Map smap, String flag, String xkey, String ykey)
	{
		List li = new ArrayList();
		if (flag.equals("ykey"))
		{
			String[] ykeyarr = ykey.split("__");
			int a = ykeyarr.length;
			String startkey = xkey;
			for (int i = 0; i < a; i++)
			{
				if (!ykeyarr[i].equals(" ")&&!ykeyarr[i].equals(this.colDims[i].getSumname()))
				{
					startkey += "__" + ykeyarr[i];
				}

			}
			String endString = "";
			Iterator it = smap.keySet().iterator();
			while (it.hasNext())
			{
				String cString = it.next().toString();
				if (RegExUtil.regEquals(startkey, cString, endString))
				{
					li.add(cString);
				}
			}

		}
		if (flag.equals("xkey"))
		{
			String[] xkeyArr = xkey.split("__");
			
			int a = 0;
			for(int i=0;i<xkeyArr.length;i++)
			{
				if(xkeyArr[i].equals(" ")||xkeyArr[i].equals(this.rowDims[i].getSumname()))
				{
					continue;
				}	
				a++;
			}
			
			String start = "";
			for (int i = 0; i < a; i++)
			{
				if (i != 0)
					start += "__";
				start += xkeyArr[i];
			}
			String end = ykey;
			Iterator it = smap.keySet().iterator();
			while (it.hasNext())
			{
				String cString = it.next().toString();
				if (RegExUtil.regEquals(start, cString, end))
				{
					li.add(cString);
				}
			}
		}
		return li;
	}

	private String getReportHeader(List allList,String[] paramValues)
	{
		String reportHeader = "";
		if (this.reportHeaderOutType != null)
		{	
			if( this.reportHeaderOutType.equals("function"))
			{
				reportHeader = RegExUtil.replaceCon(this.reportHeaderOutValue,
						this.conditions, paramValues);
				reportHeader = "<script>" + reportHeader + "</script>";
			}else{
				reportHeader=this.reportHeaderOutValue;
			}
		}
		return reportHeader;
	}

	private String getReportFooter(List allList,String[] paramValues)
	{
		String reportFooter = "";
		if (this.reportFooterOutType != null)
		{	
			if(this.reportFooterOutType.equals("function"))
			{
				reportFooter = RegExUtil.replaceCon(this.reportFooterValue,
						this.conditions, paramValues);
				reportFooter = "<script>" + reportFooter + "</script>";
			}else{
				reportFooter=this.reportFooterValue;
			}
		}
		return reportFooter;
	}

	public String toHtml(List allList,String[] paramValues)
	{
		String table = "<table class=\"page\" width=\"100%\"><tr><td class=\"reportHeader\">"
				+ getReportHeader(allList,paramValues) + "</td></tr><tr><td>";
		if (this.measureCrossWith != null
				&& this.measureCrossWith.equals("ColDims"))
		{
			table += toHtmlX();
		} else
		{
			table += toHtmlY(allList,paramValues);
			
		}
		return table += "</td></tr><tr><td>" + getReportFooter(allList,paramValues)
				+ "</td></tr></table>";
	}
	
	public String toHtml(List dataList,List allList,String[] paramValues){
		
		return "";
	}
	

	public String formatContext(List data, List allList, String[] paramValues) {
		// TODO Auto-generated method stub
		return toHtml(allList,paramValues);
	}

	public String getTemplateVm() {
		// TODO Auto-generated method stub
		return this.templatefilename;
	}
	public static void main(String[] args)
	{
		Dim rowdim1 = new Dim();
		rowdim1.setName("洲");
		rowdim1.setSummary("1");
		rowdim1.setSumname("总计");
		rowdim1.setSumpos("after");
		rowdim1.setSumby("");
		rowdim1.setGroup("1");
		rowdim1.setCode("region");
		rowdim1.setDimValue("{region}");

		Dim rowdim2 = new Dim();
		rowdim2.setName("地区");
		rowdim2.setCode("district");
		rowdim2.setSummary("1");
		rowdim2.setGroup("1");
		rowdim2.setSumby("region");
		rowdim2.setSumname("合计");
		rowdim2.setSumpos("after");
		rowdim2.setDimValue("{district}");

		Dim rowdim3 = new Dim();
		rowdim3.setName("国家");
		rowdim3.setCode("country");
		rowdim3.setSumpos("after");
		rowdim3.setSummary("1");
		rowdim3.setSumby("district,region");
		rowdim3.setSumname("小计");
		rowdim3.setDimValue("{country}");

		Dim[] rowdims =
		{ rowdim1, rowdim2, rowdim3 };

		Dim coldim = new Dim();
		coldim.setName("客户类型");
		coldim.setCode("store type");
		coldim.setDimValue("{store type}");
		coldim.setSummary("1");
		coldim.setGroup("1");
		coldim.setSumpos("after");
		coldim.setSumname("合计");

		Dim coldim1 = new Dim();
		coldim1.setName("客户");
		coldim1.setCode("cust type");
		coldim1.setGroup("1");
		coldim1.setDimValue("{cust type}");
		coldim1.setSummary("1");
		coldim1.setSumpos("after");
		coldim1.setSumname("小计");

		Dim[] coldims =
		{ coldim, coldim1 };

		Measure measure1 = new Measure();
		measure1.setName("unit sales");
		measure1.setCode("unit sales");
		measure1.setMeasureValue("{unit sales}");
		measure1.setDisplay("true");

		Measure measure2 = new Measure();
		measure2.setName("amount sales");
		measure2.setCode("amount sales");
		measure2.setMeasureValue("{amount sales}");
		measure2.setDisplay("ture");

		Measure[] measures =
		{ measure1, measure2 };

		List li = new ArrayList();

		Map d11 = new HashMap();
		d11.put("region", "亚洲");
		d11.put("country", "china");
		d11.put("district", "east");
		d11.put("store type", "online");
		d11.put("cust type", "big customer");
		d11.put("unit sales", String.valueOf("100"));
		d11.put("amount sales", String.valueOf("200"));

		Map d111 = new HashMap();
		d111.put("region", "亚洲");
		d111.put("country", "china");
		d111.put("district", "east");
		d111.put("store type", "online");
		d111.put("cust type", "small customer");
		d111.put("unit sales", String.valueOf("100"));
		d111.put("amount sales", String.valueOf("200"));
		li.add(d111);

		Map d21 = new HashMap();
		d21.put("region", "亚洲");
		d21.put("country", "china");
		d21.put("district", "east");
		d21.put("store type", "franchise");
		d21.put("cust type", "small customer");
		d21.put("unit sales", String.valueOf("100"));
		d21.put("amount sales", String.valueOf("200"));

		Map d211 = new HashMap();
		d211.put("region", "亚洲");
		d211.put("country", "china");
		d211.put("district", "east");
		d211.put("store type", "franchise");
		d211.put("cust type", "big customer");
		d211.put("unit sales", String.valueOf("100"));
		d211.put("amount sales", String.valueOf("200"));

		li.add(d211);
		Map d31 = new HashMap();
		d31.put("region", "亚洲");
		d31.put("country", "china");
		d31.put("district", "east");
		d31.put("store type", "superstore");
		d31.put("cust type", "small customer");
		d31.put("unit sales", String.valueOf("100"));
		d31.put("amount sales", String.valueOf("200"));

		Map d311 = new HashMap();
		d311.put("region", "亚洲");
		d311.put("country", "china");
		d311.put("district", "east");
		d311.put("store type", "superstore");
		d311.put("cust type", "big customer");
		d311.put("unit sales", String.valueOf("100"));
		d311.put("amount sales", String.valueOf("200"));
		li.add(d311);

		Map d11w = new HashMap();
		d11w.put("region", "亚洲");
		d11w.put("country", "india");
		d11w.put("district", "east");
		d11w.put("store type", "online");
		d11w.put("cust type", "big customer");
		d11w.put("unit sales", String.valueOf("100"));
		d11w.put("amount sales", String.valueOf("200"));
		li.add(d11w);

		Map d111w = new HashMap();
		d111w.put("region", "亚洲");
		d111w.put("country", "india");
		d111w.put("district", "east");
		d111w.put("store type", "online");
		d111w.put("cust type", "small customer");
		d111w.put("unit sales", String.valueOf("100"));
		d111w.put("amount sales", String.valueOf("200"));
		li.add(d111w);

		Map d21w = new HashMap();
		d21w.put("region", "亚洲");
		d21w.put("country", "india");
		d21w.put("district", "east");
		d21w.put("store type", "franchise");
		d21w.put("cust type", "small customer");
		d21w.put("unit sales", String.valueOf("100"));
		d21w.put("amount sales", String.valueOf("200"));
		li.add(d21w);

		Map d211w = new HashMap();
		d211w.put("region", "亚洲");
		d211w.put("country", "india");
		d211w.put("district", "east");
		d211w.put("store type", "franchise");
		d211w.put("cust type", "big customer");
		d211w.put("unit sales", String.valueOf("100"));
		d211w.put("amount sales", String.valueOf("200"));
		li.add(d211w);

		Map d31w = new HashMap();
		d31w.put("region", "亚洲");
		d31w.put("country", "india");
		d31w.put("district", "east");
		d31w.put("store type", "superstore");
		d31w.put("cust type", "small customer");
		d31w.put("unit sales", String.valueOf("100"));
		d31w.put("amount sales", String.valueOf("200"));
		li.add(d31w);

		Map d311w = new HashMap();
		d311w.put("region", "亚洲");
		d311w.put("country", "india");
		d311w.put("district", "east");
		d311w.put("store type", "superstore");
		d311w.put("cust type", "big customer");
		d311w.put("unit sales", String.valueOf("100"));
		d311w.put("amount sales", String.valueOf("200"));
		li.add(d311w);

		Map d12 = new HashMap();
		d12.put("region", "亚洲");
		d12.put("country", "japan");
		d12.put("district", "west亚洲");
		d12.put("store type", "online");
		d12.put("cust type", "big customer");
		d12.put("unit sales", String.valueOf("100"));
		d12.put("amount sales", String.valueOf("200"));

		Map d121 = new HashMap();
		d121.put("region", "亚洲");
		d121.put("country", "japan");
		d121.put("district", "west亚洲");
		d121.put("store type", "online");
		d121.put("cust type", "small customer");
		d121.put("unit sales", String.valueOf("100"));
		d121.put("amount sales", String.valueOf("200"));
		li.add(d121);

		Map d22 = new HashMap();
		d22.put("region", "亚洲");
		d22.put("country", "japan");
		d22.put("district", "west亚洲");
		d22.put("store type", "franchise");
		d22.put("cust type", "big customer");
		d22.put("unit sales", String.valueOf("100"));
		d22.put("amount sales", String.valueOf("200"));

		Map d221 = new HashMap();
		d221.put("region", "亚洲");
		d221.put("country", "japan");
		d221.put("district", "west亚洲");
		d221.put("store type", "franchise");
		d221.put("cust type", "small customer");
		d221.put("unit sales", String.valueOf("100"));
		d221.put("amount sales", String.valueOf("200"));

		li.add(d221);
		Map d32 = new HashMap();
		d32.put("region", "亚洲");
		d32.put("country", "japan");
		d32.put("district", "west亚洲");
		d32.put("store type", "superstore");
		d32.put("cust type", "big customer");
		d32.put("unit sales", String.valueOf("100"));
		d32.put("amount sales", String.valueOf("200"));

		Map d321 = new HashMap();
		d321.put("region", "亚洲");
		d321.put("country", "japan");
		d321.put("district", "west亚洲");
		d321.put("store type", "superstore");
		d321.put("cust type", "small customer");
		d321.put("unit sales", String.valueOf("100"));
		d321.put("amount sales", String.valueOf("200"));
		li.add(d321);

		Map d12w = new HashMap();
		d12w.put("region", "亚洲");
		d12w.put("country", "korea");
		d12w.put("district", "west亚洲");
		d12w.put("store type", "online");
		d12w.put("cust type", "big customer");
		d12w.put("unit sales", String.valueOf("100"));
		d12w.put("amount sales", String.valueOf("200"));
		li.add(d12w);

		Map d121w = new HashMap();
		d121w.put("region", "亚洲");
		d121w.put("country", "korea");
		d121w.put("district", "west亚洲");
		d121w.put("store type", "online");
		d121w.put("cust type", "small customer");
		d121w.put("unit sales", String.valueOf("100"));
		d121w.put("amount sales", String.valueOf("200"));
		li.add(d121w);

		Map d22w = new HashMap();
		d22w.put("region", "亚洲");
		d22w.put("country", "korea");
		d22w.put("district", "west亚洲");
		d22w.put("store type", "franchise");
		d22w.put("cust type", "big customer");
		d22w.put("unit sales", String.valueOf("100"));
		d22w.put("amount sales", String.valueOf("200"));
		li.add(d22w);

		Map d221w = new HashMap();
		d221w.put("region", "亚洲");
		d221w.put("country", "korea");
		d221w.put("district", "west亚洲");
		d221w.put("store type", "franchise");
		d221w.put("cust type", "small customer");
		d221w.put("unit sales", String.valueOf("100"));
		d221w.put("amount sales", String.valueOf("200"));
		li.add(d221w);

		Map d32w = new HashMap();
		d32w.put("region", "亚洲");
		d32w.put("country", "korea");
		d32w.put("district", "west亚洲");
		d32w.put("store type", "superstore");
		d32w.put("cust type", "big customer");
		d32w.put("unit sales", String.valueOf("100"));
		d32w.put("amount sales", String.valueOf("200"));
		li.add(d32w);

		Map d321w = new HashMap();
		d321w.put("region", "亚洲");
		d321w.put("district", "west亚洲");
		d321w.put("country", "korea");
		d321w.put("store type", "superstore");
		d321w.put("cust type", "small customer");
		d321w.put("unit sales", String.valueOf("100"));
		d321w.put("amount sales", String.valueOf("200"));
		li.add(d321w);

		Map d41 = new HashMap();
		d41.put("region", "europe");
		d41.put("country", "france");
		d41.put("district", "westeurope");
		d41.put("store type", "online");
		d41.put("cust type", "big customer");
		d41.put("unit sales", String.valueOf("100"));
		d41.put("amount sales", String.valueOf("200"));

		Map d411 = new HashMap();
		d411.put("region", "europe");
		d411.put("country", "france");
		d411.put("district", "westeurope");
		d411.put("store type", "online");
		d411.put("cust type", "small customer");
		d411.put("unit sales", String.valueOf("100"));
		d411.put("amount sales", String.valueOf("200"));

		li.add(d411);
		Map d51 = new HashMap();
		d51.put("region", "europe");
		d51.put("country", "france");
		d51.put("district", "westeurope");
		d51.put("store type", "franchise");
		d51.put("cust type", "big customer");
		d51.put("unit sales", String.valueOf("100"));
		d51.put("amount sales", String.valueOf("200"));

		Map d511 = new HashMap();
		d511.put("region", "europe");
		d511.put("country", "france");
		d511.put("district", "westeurope");
		d511.put("store type", "franchise");
		d511.put("cust type", "small customer");
		d511.put("unit sales", String.valueOf("100"));
		d511.put("amount sales", String.valueOf("200"));

		li.add(d511);
		Map d61 = new HashMap();
		d61.put("region", "europe");
		d61.put("country", "france");
		d61.put("district", "westeurope");
		d61.put("store type", "superstore");
		d61.put("cust type", "big customer");
		d61.put("unit sales", String.valueOf("100"));
		d61.put("amount sales", String.valueOf("200"));

		Map d611 = new HashMap();
		d611.put("region", "europe");
		d611.put("country", "france");
		d611.put("district", "westeurope");
		d611.put("store type", "superstore");
		d611.put("cust type", "small customer");
		d611.put("unit sales", String.valueOf("100"));
		d611.put("amount sales", String.valueOf("200"));

		li.add(d611);
		Map d42 = new HashMap();
		d42.put("region", "europe");
		d42.put("country", "ireland");
		d42.put("district", "westeurope");
		d42.put("store type", "online");
		d42.put("cust type", "big customer");
		d42.put("unit sales", String.valueOf("100"));
		d42.put("amount sales", String.valueOf("200"));

		Map d421 = new HashMap();
		d421.put("region", "europe");
		d421.put("country", "ireland");
		d421.put("district", "westeurope");
		d421.put("store type", "online");
		d421.put("cust type", "small customer");
		d421.put("unit sales", String.valueOf("100"));
		d421.put("amount sales", String.valueOf("200"));

		li.add(d421);
		Map d52 = new HashMap();
		d52.put("region", "europe");
		d52.put("country", "ireland");
		d52.put("district", "westeurope");
		d52.put("store type", "franchise");
		d52.put("cust type", "big customer");
		d52.put("unit sales", String.valueOf("100"));
		d52.put("amount sales", String.valueOf("200"));

		Map d521 = new HashMap();
		d521.put("region", "europe");
		d521.put("country", "ireland");
		d521.put("district", "westeurope");
		d521.put("store type", "franchise");
		d521.put("cust type", "small customer");
		d521.put("unit sales", String.valueOf("100"));
		d521.put("amount sales", String.valueOf("200"));

		li.add(d521);
		Map d62 = new HashMap();
		d62.put("region", "europe");
		d62.put("country", "ireland");
		d62.put("district", "westeurope");
		d62.put("store type", "superstore");
		d62.put("cust type", "big customer");
		d62.put("unit sales", String.valueOf("100"));
		d62.put("amount sales", String.valueOf("200"));

		Map d621 = new HashMap();
		d621.put("region", "europe");
		d621.put("country", "ireland");
		d621.put("district", "westeurope");
		d621.put("store type", "superstore");
		d621.put("cust type", "small customer");
		d621.put("unit sales", String.valueOf("100"));
		d621.put("amount sales", String.valueOf("200"));

		li.add(d621);
		Map d71 = new HashMap();
		d71.put("region", "americas");
		d71.put("country", "usa");
		d71.put("district", "north");
		d71.put("store type", "online");
		d71.put("cust type", "big customer");
		d71.put("unit sales", String.valueOf("100"));
		d71.put("amount sales", String.valueOf("200"));

		Map d711 = new HashMap();
		d711.put("region", "americas");
		d711.put("country", "usa");
		d711.put("district", "north");
		d711.put("store type", "online");
		d711.put("cust type", "small customer");
		d711.put("unit sales", String.valueOf("100"));
		d711.put("amount sales", String.valueOf("200"));

		li.add(d711);
		Map d81 = new HashMap();
		d81.put("region", "americas");
		d81.put("country", "usa");
		d81.put("district", "north");
		d81.put("store type", "franchise");
		d81.put("cust type", "big customer");
		d81.put("unit sales", String.valueOf("100"));
		d81.put("amount sales", String.valueOf("200"));

		Map d811 = new HashMap();
		d811.put("region", "americas");
		d811.put("country", "usa");
		d811.put("district", "north");
		d811.put("store type", "franchise");
		d811.put("cust type", "small customer");
		d811.put("unit sales", String.valueOf("100"));
		d811.put("amount sales", String.valueOf("200"));

		li.add(d811);
		Map d91 = new HashMap();
		d91.put("region", "americas");
		d91.put("country", "usa");
		d91.put("district", "north");
		d91.put("store type", "superstore");
		d91.put("cust type", "big customer");
		d91.put("unit sales", String.valueOf("100"));
		d91.put("amount sales", String.valueOf("200"));

		Map d911 = new HashMap();
		d911.put("region", "americas");
		d911.put("country", "usa");
		d911.put("district", "north");
		d911.put("store type", "superstore");
		d911.put("cust type", "small customer");
		d911.put("unit sales", String.valueOf("100"));
		d911.put("amount sales", String.valueOf("200"));
		li.add(d911);

		Map d71w = new HashMap();
		d71w.put("region", "americas");
		d71w.put("country", "canada");
		d71w.put("district", "north");
		d71w.put("store type", "online");
		d71w.put("cust type", "big customer");
		d71w.put("unit sales", String.valueOf("100"));
		d71w.put("amount sales", String.valueOf("200"));

		li.add(d71w);
		Map d711w = new HashMap();
		d711w.put("region", "americas");
		d711w.put("country", "canada");
		d711w.put("district", "north");
		d711w.put("store type", "online");
		d711w.put("cust type", "small customer");
		d711w.put("unit sales", String.valueOf("100"));
		d711w.put("amount sales", String.valueOf("200"));

		li.add(d711w);
		Map d81w = new HashMap();
		d81w.put("region", "americas");
		d81w.put("country", "canada");
		d81w.put("district", "north");
		d81w.put("store type", "franchise");
		d81w.put("cust type", "big customer");
		d81w.put("unit sales", String.valueOf("100"));
		d81w.put("amount sales", String.valueOf("200"));
		li.add(d81w);
		Map d811w = new HashMap();
		d811w.put("region", "americas");
		d811w.put("country", "canada");
		d811w.put("district", "north");
		d811w.put("store type", "franchise");
		d811w.put("cust type", "small customer");
		d811w.put("unit sales", String.valueOf("100"));
		d811w.put("amount sales", String.valueOf("200"));

		li.add(d811w);
		Map d91w = new HashMap();
		d91w.put("region", "americas");
		d91w.put("country", "canada");
		d91w.put("district", "north");
		d91w.put("store type", "superstore");
		d91w.put("cust type", "big customer");
		d91w.put("unit sales", String.valueOf("100"));
		d91w.put("amount sales", String.valueOf("200"));
		li.add(d91w);
		Map d911w = new HashMap();
		d911w.put("region", "americas");
		d911w.put("country", "canada");
		d911w.put("district", "north");
		d911w.put("store type", "superstore");
		d911w.put("cust type", "small customer");
		d911w.put("unit sales", String.valueOf("100"));
		d911w.put("amount sales", String.valueOf("200"));
		li.add(d911w);

		Map d72 = new HashMap();
		d72.put("region", "americas");
		d72.put("country", "brazil");
		d72.put("district", "south");
		d72.put("store type", "online");
		d72.put("cust type", "big customer");
		d72.put("unit sales", String.valueOf("100"));
		d72.put("amount sales", String.valueOf("200"));

		Map d721 = new HashMap();
		d721.put("region", "americas");
		d721.put("country", "brazil");
		d721.put("district", "south");
		d721.put("store type", "online");
		d721.put("cust type", "small customer");
		d721.put("unit sales", String.valueOf("100"));
		d721.put("amount sales", String.valueOf("200"));

		li.add(d721);
		Map d82 = new HashMap();
		d82.put("region", "americas");
		d82.put("country", "brazil");
		d82.put("district", "south");
		d82.put("store type", "franchise");
		d82.put("cust type", "big customer");
		d82.put("unit sales", String.valueOf("100"));
		d82.put("amount sales", String.valueOf("200"));

		Map d821 = new HashMap();
		d821.put("region", "americas");
		d821.put("country", "brazil");
		d821.put("district", "south");
		d821.put("store type", "franchise");
		d821.put("cust type", "small customer");
		d821.put("unit sales", String.valueOf("100"));
		d821.put("amount sales", String.valueOf("200"));

		li.add(d821);
		Map d92 = new HashMap();
		d92.put("region", "americas");
		d92.put("country", "brazil");
		d92.put("district", "south");
		d92.put("store type", "superstore");
		d92.put("cust type", "big customer");
		d92.put("unit sales", String.valueOf("100"));
		d92.put("amount sales", String.valueOf("200"));

		Map d921 = new HashMap();
		d921.put("region", "americas");
		d921.put("country", "brazil");
		d921.put("district", "south");
		d921.put("store type", "superstore");
		d921.put("cust type", "small customer");
		d921.put("unit sales", String.valueOf("100"));
		d921.put("amount sales", String.valueOf("200"));
		li.add(d921);

		Map d72w = new HashMap();
		d72w.put("region", "americas");
		d72w.put("country", "argentina");
		d72w.put("district", "south");
		d72w.put("store type", "online");
		d72w.put("cust type", "big customer");
		d72w.put("unit sales", String.valueOf("100"));
		d72w.put("amount sales", String.valueOf("200"));
		li.add(d72w);
		Map d721w = new HashMap();
		d721w.put("region", "americas");
		d721w.put("country", "argentina");
		d721w.put("district", "south");
		d721w.put("store type", "online");
		d721w.put("cust type", "small customer");
		d721w.put("unit sales", String.valueOf("100"));
		d721w.put("amount sales", String.valueOf("200"));

		li.add(d721w);
		Map d82w = new HashMap();
		d82w.put("region", "americas");
		d82w.put("country", "argentina");
		d82w.put("district", "south");
		d82w.put("store type", "franchise");
		d82w.put("cust type", "big customer");
		d82w.put("unit sales", String.valueOf("100"));
		d82w.put("amount sales", String.valueOf("200"));
		li.add(d82w);
		Map d821w = new HashMap();
		d821w.put("region", "americas");
		d821w.put("country", "argentina");
		d821w.put("district", "south");
		d821w.put("store type", "franchise");
		d821w.put("cust type", "small customer");
		d821w.put("unit sales", String.valueOf("100"));
		d821w.put("amount sales", String.valueOf("200"));
		li.add(d821w);

		Map d92w = new HashMap();
		d92w.put("region", "americas");
		d92w.put("country", "argentina");
		d92w.put("district", "south");
		d92w.put("store type", "superstore");
		d92w.put("cust type", "big customer");
		d92w.put("unit sales", String.valueOf("100"));
		d92w.put("amount sales", String.valueOf("200"));
		li.add(d92w);

		Map d921w = new HashMap();
		d921w.put("region", "americas");
		d921w.put("country", "argentina");
		d921w.put("district", "south");
		d921w.put("store type", "superstore");
		d921w.put("cust type", "small customer");
		d921w.put("unit sales", String.valueOf("100"));
		d921w.put("amount sales", String.valueOf("200"));
		li.add(d921w);

		li.add(d11);
		li.add(d21);
		li.add(d31);
		li.add(d41);
		li.add(d51);
		li.add(d61);
		li.add(d71);
		li.add(d81);
		li.add(d91);
		li.add(d12);
		li.add(d22);
		li.add(d32);
		li.add(d42);
		li.add(d52);
		li.add(d62);
		li.add(d72);
		li.add(d82);
		li.add(d92);

		BackupOfCrossGrid cg = new BackupOfCrossGrid();
		//cg.setDataList(li);
		cg.setColDims(coldims);
		cg.setMeasures(measures);
		cg.setRowDims(rowdims);
		String ss = cg.toHtml(li,null);
		System.out.println(ss);
	}
}
