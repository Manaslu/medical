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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
import com.teamsun.framework.util.RegExUtil;
import com.teamsun.thunderreport.javascript.JavaScriptParser;
import com.teamsun.thunderreport.parse.Condition;
import com.teamsun.thunderreport.parse.Dim;
import com.teamsun.thunderreport.parse.Title;
import com.teamsun.thunderreport.parse.Measure;
 
public class CrossGrid extends ListGrid {
	 	private Dim[] rowDims;
	 
	 	private Dim[] colDims;
	 
	 	private Title[] title;	 	
	 
	 	private Measure[] measures;
	 
	 	private String measureCrossWith;
	 
	 	private boolean isMeasureDisplay;
	 
	 	public void setConditions(Condition[] conditions) {
	  		this.conditions = conditions;
	 	}
	 
	 	public void setReportFooterOutType(String reportFooterOutType) {
	  		this.reportFooterOutType = reportFooterOutType;
	 	}
	 
	 	public void setReportFooterValue(String reportFooterValue) {
	  		this.reportFooterValue = reportFooterValue;
	 	}
	 
	 	public void setReportHeaderOutType(String reportHeaderOutType) {
	  		this.reportHeaderOutType = reportHeaderOutType;
	 	}
	 
	 	public void setReportHeaderOutValue(String reportHeaderOutValue) {
	  		this.reportHeaderOutValue = reportHeaderOutValue;
	 	}
	 
	 	public void setColDims(Dim[] colDims) {
	  		this.colDims = colDims;
	 	}
	 
	  public void setTitle(Title[] title) {
	  		this.title = title;
	  }
	 
		public void setMeasures(Measure[] measures) {
			  this.measures = measures;
			  this.isMeasureDisplay = Boolean.valueOf(measures[0].display)
			    .booleanValue();
			  this.measureCrossWith = measures[0].crosswith;
		}
		 
		public void setRowDims(Dim[] rowDims) {
				this.rowDims = rowDims;
		}
 
		private String join(String[] strarr) {
				String u = "";
				for (int i = 0; i < strarr.length; i++) {
						if (i != 0)
								u += "__";
						u += strarr[i];
				}
				return u;
		}
 

 
		private String buildMap(int dimPos, Dim[] dims, Map dataMap, TreeMap tMap,HashMap[] kMap) {
			  String col = dims[dimPos].getCode();
			  String key = "";
			  if(dataMap.get(col)==null) key = " " ;
			  else key = dataMap.get(col).toString();
			  //String key = dataMap.get(col).toString();
			 	if (key == null)
			  		key = " ";
			 	
			 	String keycode = dims[dimPos].getKeycode();
			 	if(keycode!=null){
			 		String keyValue = "";
			 		if(dataMap.get(keycode)==null) keyValue = " ";
			 		else keyValue = dataMap.get(keycode).toString();
			 		//String keyValue = dataMap.get(keycode).toString();
				 	if(kMap[dimPos]==null) kMap[dimPos]=new HashMap();
				 	kMap[dimPos].put(key,keyValue);
			 	}
			  TreeMap child = null;
			  if (tMap.get(key) == null) {
			   		child = new TreeMap();
			   		tMap.put(key, child);
			  } 
			  else {
			   		child = (TreeMap) tMap.get(key);
			  }
			  dimPos++;
			  if (dimPos == dims.length) {
			   		return key;
			  }
			  return key + "__" + buildMap(dimPos, dims, dataMap, child,kMap);
		}
 
		private void traversal(String path, List list, TreeMap tMap) {
				Iterator it = tMap.keySet().iterator();
				
				if (tMap.isEmpty()) {
						list.add(path);
						System.out.println(path);
						return;
				}
				while (it.hasNext()) {
						String key = it.next().toString();
						TreeMap child = (TreeMap) tMap.get(key);
						String innerPath = path + key;
						if (!child.isEmpty())
								innerPath += "__";
						traversal(innerPath, list, child);
				}
		}
        private String getFuncValue(Dim dim,String name,String key,String[] paramValues){
        	Map dataMap=new HashMap();
        	dataMap.put(dim.getCode(), name);
        	dataMap.put(dim.getKeycode(), key);
        	String oriValue="";
        	if (dim.getOutputtype() != null
    				&& dim.getOutputtype().equals("function")) {
    			oriValue = dim.getDimValue();
    			oriValue = RegExUtil.replaceCon(oriValue, this.conditions,
    					paramValues);
    			oriValue = RegExUtil.replaceParam(oriValue, dataMap);
    			oriValue = JavaScriptParser.getInstance().evalFunction(
    					oriValue).toString();
    		}else{
    			oriValue=name;
    		}
        	return oriValue;
        }
		private DOMElement buildColTitleCell(String[] sArr,int rowNo,int m,Map keyMap,String[] paramValues){
				DOMElement tdH = new DOMElement("td");
				tdH.addAttribute("class", "cg_col_title");
		
      	if (rowNo < this.colDims.length) {
      		String si = ""; // self id;
			String pi = ""; // parent id;
			String y = "";
	      	String sTitle = "";
			if (sArr.length == 1 && sArr[0].equals("")) {
				sTitle = this.colDims[0].getSumname();
			} else {
				int end = (sArr.length>rowNo)?rowNo:sArr.length;

				if (rowNo < sArr.length){
					if(keyMap==null)
						sTitle = sArr[rowNo];
					else
						sTitle = getFuncValue(this.colDims[rowNo],sArr[rowNo],keyMap.get(sArr[rowNo]).toString(),paramValues);						
					si = sArr[rowNo];
				}
				else {
					sTitle = this.colDims[sArr.length - 1].getSumname();
				}
				
				for (int t = 0; t < end; t++) {
					if (t > 0) {
						pi += "__";
					}
					pi += sArr[t];
				}
				for (int t = 0; t < sArr.length; t++) {
					if (t > 0) {
						y += "__";
					}
					y += sArr[t];
				}
			}
			tdH.addText(sTitle);
			
			
			
			tdH.addAttribute("y", y);
			tdH.addAttribute("x", rowNo + "");
			tdH.addAttribute("si", si);
			tdH.addAttribute("pi", pi);

		} else {
			if (this.measures.length > 1)
				tdH.addText(this.measures[m].getName());
		}
		return tdH;
	}
		
		// --
		private void insertColTitleCell(DOMElement tr,String[] sArr,int rowNo,int crossCol,Map keyMap,String[] paramValues){
				if (crossCol == 0) {
						for (int m = 0; m < this.measures.length; m++) {
								tr.appendChild(buildColTitleCell(sArr, rowNo, m,keyMap,paramValues));
						}
				} 
				else {
						tr.appendChild(buildColTitleCell(sArr, rowNo, 0,keyMap,paramValues));
				}
		} 
		
 		//返回不相等的位置
 		private boolean compareCoord(String[] s1Arr,String[] s2Arr,boolean[] diffDims){
 				String s1 = "";
 				String s2 = "";
 				boolean ret = true;
 				for(int i=0;i<s1Arr.length-1;i++){
 						s1 += s1Arr[i];
 						s2 += s2Arr[i];
 						diffDims[i] = (s1.equals(s2));
 						ret = ret && diffDims[i];
 				}
 				return ret;
 		}
 		
 		private String getSumColID(String[] sArr,int pos){
 				String retS = "";
 				for(int i=0;i<=pos;i++){
 						if(i>0)
 							retS += "__";
 						retS += sArr[i];
 				}
 				return retS;
 		}
 		
 		private int doInsertSumCoord(List list, Dim dim,int[] insertPos,int end,String s,int dimSuffix){
				String sumPos = dim.getSumpos();
 				int nStart = dimSuffix+1;
 				if(sumPos !=null)
						sumPos = sumPos.toUpperCase();
				else
						sumPos = "AFTER";
				
				if(sumPos.equals("BEFORE")){
						list.add(insertPos[dimSuffix],s);
						insertPos[dimSuffix] = end+1;
						while(nStart<insertPos.length){
								insertPos[nStart] += 1;
								nStart++;
						}		
						return 1;
				}
				else if(sumPos.equals("BOTH")){
						if(end==-1)
								list.add(s);
						else
								list.add(end,s);
						list.add(insertPos[dimSuffix],s);
						insertPos[dimSuffix] = end+2;
						while(nStart<insertPos.length){
								insertPos[nStart] += 2;
								nStart++;
						}	
						return 2;
				}
				else{
						if(end==-1)
								list.add(s);
						else
								list.add(end,s);
						insertPos[dimSuffix] = end+1;
						return 1;
				}
 		}
 		
		private void insertSumCoord2List(List list,Dim[] dims){
				int nPos	= 0;
				int[] insertPos = new int[dims.length];
				boolean[] diffDims = new boolean[dims.length-1];	
				
				String startCoord = list.get(nPos).toString(); 
				
				for(;;){
						String[] startArr = startCoord.split("__");
						String endCoord = list.get(nPos).toString();
						String[] endArr = endCoord.split("__");
						
						if(!compareCoord(startArr,endArr,diffDims)){
								for(int i=diffDims.length-2;i>=0;i--){
										if(!diffDims[i] && dims[i+1].getSummary().equals("1")){
												String sumColID = getSumColID(startArr,i);
												nPos += doInsertSumCoord(list, dims[i+1],insertPos,nPos,sumColID,i+1);
										}
								}	
						}
						
						nPos ++;
						startCoord = endCoord;
						if(nPos>=list.size()){
							   for(int i=diffDims.length-2;i>=0;i--){
										if (dims[i+1].getSummary().equals("1")){
												String sumColID = getSumColID(endArr,i);
												nPos = doInsertSumCoord(list, dims[i+1],insertPos,-1,sumColID,i+1);
										}
								}	
								break;
						}
				}
			  if(dims[0].getSummary()!=null){	
				if (dims[0].getSummary().equals("1")){
						nPos = doInsertSumCoord(list, dims[0],insertPos,-1,"",0);
				}}
		}
		
		//insert titles for every row;
 		private DOMElement insertRowTitle(DOMElement dom,String xcoord,String[] rdimDesc,int crossCol,int m,Map[] dimsMap,String[] paramValues){
				DOMElement tr = new DOMElement("tr");
				tr.addAttribute("x", xcoord);
				dom.appendChild(tr);
				
				
				int r = 0;
				for(;;){
					DOMElement td = new DOMElement("td");
					td.addAttribute("y", r + "");
					td.addAttribute("class", "cg_row_title");
					String dimName = "";
					
					if(rdimDesc[r].equals("")){
						dimName = this.rowDims[r].getSumname();
						td.setAttribute("sum", r+"");
						td.addText(dimName);
						tr.appendChild(td);
						break;
					}
					else{
						if(dimsMap[r]!=null)	
							dimName = getFuncValue(this.rowDims[r],rdimDesc[r],dimsMap[r].get(rdimDesc[r]).toString(),paramValues);
						else
							dimName = rdimDesc[r];
						td.addText(dimName);
						tr.appendChild(td);
					}
					r++;
					if(r==rdimDesc.length)
						break;
				}
				int i =r;
				while(r<this.rowDims.length-1){
					DOMElement td = new DOMElement("td");
					td.addAttribute("y", (i-1) + "");
					td.addAttribute("class", "cg_row_title");
					td.setAttribute("sum", (i-1)+"");				
					td.addText(this.rowDims[i].getSumname());
					
					tr.appendChild(td);
					r++;
				}
				
				/*
				 * for (int r = 0; r < this.rowDims.length; r++) {
						DOMElement td = new DOMElement("td");
						td.addAttribute("y", r + "");
						td.addAttribute("class", "cg_row_title");
						String dimName = "";
						if(rdimDesc.length==1 && rdimDesc[0].equals("")){
								dimName = this.rowDims[0].getSumname();
								td.setAttribute("sum", "0");}
						else{
								if(r<rdimDesc.length){
										if(dimsMap[r]!=null)	
											dimName = getFuncValue(this.rowDims[r],rdimDesc[r],dimsMap[r].get(rdimDesc[r]).toString(),paramValues);
										else
											dimName = rdimDesc[r];
								}
								else{
										dimName = this.rowDims[rdimDesc.length-1].getSumname();
										td.setAttribute("sum", (rdimDesc.length-1)+"");}
						}
						td.addText(dimName);
						tr.appendChild(td);
				}
				*/

				if(crossCol==1){
						if(m>-1 && this.measures.length>1){
								DOMElement td = new DOMElement("td");
								td.addAttribute("class", "cg_row_title");
								td.addText(this.measures[m].name);
								tr.appendChild(td);
						}
				}
				return tr;
		}
										
		 private String getValueFromDataMap(Map dataMap,String coord){
					if(dataMap.containsKey(coord))
							return dataMap.get(coord).toString();
					return "";
		}
 		
	 //
		private void dealNormalColVertAggr(String coord,String rowDimSumPos,int rowDimSuffix,int c,int m,DOMElement dataTD,Map tdMap,DOMElement[][][] vertTotalTD,float[][][] vertTotal){
				if(rowDimSumPos.equals("BEFORE")){
						if(vertTotalTD[rowDimSuffix][c][m]!=null){
								vertTotalTD[rowDimSuffix][c][m].setText(measureFormat(m,vertTotal[rowDimSuffix][c][m]+""));
								vertTotalTD[rowDimSuffix][c][m].setAttribute("class", "sumtd");
								vertTotal[rowDimSuffix][c][m] = 0;
								vertTotalTD[rowDimSuffix][c][m] = null;
						}
						vertTotalTD[rowDimSuffix][c][m] = dataTD;
				}
				else if(rowDimSumPos.equals("BOTH")){
						if(vertTotalTD[rowDimSuffix][c][m]!=null){
								String tdText = measureFormat(m,vertTotal[rowDimSuffix][c][m]+"");
								vertTotalTD[rowDimSuffix][c][m].setText(tdText);
								dataTD.setText(tdText);
								vertTotal[rowDimSuffix][c][m] = 0;
								vertTotalTD[rowDimSuffix][c][m] = null;
						}
						else
							vertTotalTD[rowDimSuffix][c][m] = dataTD;
				}
				else{
						String tdText = measureFormat(m,vertTotal[rowDimSuffix][c][m]+"");
						dataTD.setText(tdText);
						vertTotal[rowDimSuffix][c][m] = 0;
				}
		}

		//????????????????е??
		private void dealNormalRowHoriAggr(String coord,String colDimSumPos,int colDimSuffix,int c,int m,DOMElement dataTD,Map tdMap,float[][][] vertTotal,DOMElement[][] horiTotalTD,float[][] horiTotal){
				if(colDimSumPos.equals("BEFORE")){
						if(horiTotalTD[colDimSuffix][m]!=null){
									//??????????

									float vvv = horiTotal[colDimSuffix][m];
									horiTotalTD[colDimSuffix][m].setText(measureFormat(m,vvv+""));
									horiTotalTD[colDimSuffix][m].setAttribute("class", "sumtd");
									
									int tc = Integer.parseInt(horiTotalTD[colDimSuffix][m].getAttribute("y"));
									for(int r=0;r<this.rowDims.length;r++){
											vertTotal[r][tc][m] += vvv;
									}
									horiTotal[colDimSuffix][m] = 0;
									horiTotalTD[colDimSuffix][m] = null;
						}
						horiTotalTD[colDimSuffix][m] = dataTD;
				}
				else if(colDimSumPos.equals("BOTH")){
							if(tdMap.containsKey(coord)){
									float vvv = horiTotal[colDimSuffix][m];
									String tdText = measureFormat(m,vvv+"");
									DOMElement xTd = ((DOMElement)tdMap.get(coord));
									xTd.setText(tdText);
						 			tdMap.remove(coord);
						 			dataTD.setText(tdText);
						 			int tc = Integer.parseInt(xTd.getAttribute("y"));
									for(int r=0;r<this.rowDims.length;r++){
											vertTotal[r][tc][m] += vvv;
											vertTotal[r][c][m] += vvv;
									}
						 			horiTotal[colDimSuffix][m] = 0;
						 	}
						 	else
						 			tdMap.put(coord,dataTD);
				}
				else{
						float vvv = horiTotal[colDimSuffix][m];
						dataTD.setText(measureFormat(m,vvv+""));
						for(int r=0;r<this.rowDims.length;r++){
								vertTotal[r][c][m] += vvv;
						}
						horiTotal[colDimSuffix][m] = 0;
				}
		}
		
		private void dealNormalDataCell(Map dataMap,String coord,int c,int m,DOMElement dataTD,float[][] horiTotal,float[][][] vertTotal){
				float dataValue = 0;
				String tdText = getValueFromDataMap(dataMap,coord);
				dataTD.setText(measureFormat(m,tdText));
				if(!tdText.equals(""))
						dataValue = Float.parseFloat(tdText);
		
				for(int h=0;h<horiTotal.length;h++){
						horiTotal[h][m] += dataValue;
				}
				for(int r=0;r<vertTotal.length;r++){
						vertTotal[r][c][m] += dataValue;
				}
		}	
		
		private void dealDataTDCell(int colDimSuffix,int rowDimSuffix,String coord,String rowDimSumPos,String colDimSumPos,
				int c,int m,DOMElement dataTD,Map tdMap,DOMElement[][][] vertTotalTD,float[][][] vertTotal,
				DOMElement[][] horiTotalTD,float[][] horiTotal,Map dataMap){

				if(colDimSuffix<this.colDims.length&&rowDimSuffix<this.rowDims.length){
						//dealNormalColVertAggr(coord,rowDimSumPos,rowDimSuffix,c,m,dataTD,tdMap,vertTotalTD,vertTotal);
				}
				if(colDimSuffix<this.colDims.length&&rowDimSuffix==this.rowDims.length){
						dealNormalRowHoriAggr(coord,colDimSumPos,colDimSuffix,c,m,dataTD,tdMap,vertTotal,horiTotalTD,horiTotal);
				}
				
				if(colDimSuffix==this.colDims.length&&rowDimSuffix<this.rowDims.length){
						dealNormalColVertAggr(coord,rowDimSumPos,rowDimSuffix,c,m,dataTD,tdMap,vertTotalTD,vertTotal);
				}
				if(colDimSuffix==this.colDims.length&&rowDimSuffix==this.rowDims.length){
						dealNormalDataCell(dataMap,coord,c,m,dataTD,horiTotal,vertTotal);
				}
		}

	  private String toHtmlY(List dataList, String[] paramValues) {
			  TreeMap xdimsMap = new TreeMap();
			  HashMap[] xdimsKeyMap = new HashMap[this.rowDims.length];
			  TreeMap ydimsMap = new TreeMap();  
			  HashMap[] ydimsKeyMap = new HashMap[this.colDims.length];
			  Map dataMap = new HashMap();
			  List newXList=new ArrayList();
			  List yList = new ArrayList();

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
			  
	 			
			  // reorganize data for easy to get
			  for (int k = 0; k < dataList.size(); k++) {
			  		Map map = (Map) dataList.get(k);
			 
			  		String x = buildMap(0, this.rowDims, map, xdimsMap,xdimsKeyMap);
			   		String y = buildMap(0, this.colDims, map, ydimsMap,ydimsKeyMap);
			   		if(!newXList.contains(x)) newXList.add(x);
			   		if(!yList.contains(y)) yList.add(y);
			   	
			   		for (int i = 0; i < this.measures.length; i++) {
			    			String measureName = this.measures[i].getCode();
			    			String measureValue = "";
			    			if(map.get(measureName)==null||map.get(measureName).equals("")) measureValue = "";
			    			else measureValue = map.get(measureName).toString();
			    			//String measureValue = map.get(measureName).toString();
			    			dataMap.put(x + "###" + y + "^^^" + i, measureValue);
			   		}
			  }
	 
			  //initiate grid title
			  int rowSpan = this.colDims.length;
			  int colStart = this.rowDims.length;
			  int crossCol = 0;
			  if (this.measureCrossWith!=null&&this.measureCrossWith.toUpperCase().equals("COLDIMS")) { //
					  if(this.measures.length>1)
				  		colStart += 1;
					  crossCol = 1;
			  }
			  else if(this.measures.length>1){
			   		rowSpan += 1;
			  }
			  //this.traversal("", newXList, xdimsMap);
			  
			  DOMElement trH = null;
			  DOMElement tdH = null;
			
			  insertSumCoord2List(yList,this.colDims);
		  		
			  for (int i = 0; i < rowSpan; i++) {
				   	trH = new DOMElement("tr");
				   	trH.addAttribute("class","tr_head");
				   	root.appendChild(trH);
			 
				   	if (i == 0) {
						    // loop insert cell for every rowdim
						    for (int j = 0; j < this.rowDims.length; j++) {
								    tdH = new DOMElement("td");
								    tdH.addAttribute("class", "cg_col_title");
								    tdH.addAttribute("rowSpan", rowSpan + "");
								    tdH.addText(this.rowDims[j].getName());
								    trH.appendChild(tdH);
						    }
						    if (crossCol == 1 && this.measures.length>1) {
										tdH = new DOMElement("td");
										tdH.addAttribute("class", "cg_col_title");
										tdH.addAttribute("rowSpan", rowSpan + "");
										tdH.addText("指标");
										trH.appendChild(tdH);
						    }
				   	}
						
				 		for(int yPos=0;yPos<yList.size();yPos++){
								String endYcoord = yList.get(yPos).toString();
								String[] endYArr = endYcoord.split("__");
								int ksuffix = i;
								if (i==this.colDims.length)
									ksuffix = i-1;
								insertColTitleCell(trH,endYArr,i,crossCol,ydimsKeyMap[ksuffix],paramValues);
						}
				}
				//end grid title
	 
	  		
	  	
	  		insertSumCoord2List(newXList,this.rowDims);
	  	
	  		float[][][] vertTotal = new float[this.rowDims.length][yList.size()][this.measures.length]; //???????к?????ж??С?

	  		DOMElement[][][] vertTotalTD = new DOMElement[this.rowDims.length][yList.size()][this.measures.length];
	  		
	 			int xStart = 0;
	  		int xPos = 0;
	  		
	  		Map tdMap = new HashMap();
	  		for(;;){
	  				String startXcoord = newXList.get(xStart).toString();
	  				String endXcoord = newXList.get(xPos).toString();
	  				String[] startXArr = startXcoord.split("__");
						String[] endXArr = endXcoord.split("__");
						int rowDimSuffix = 0;
						if(!endXcoord.equals(""))
								rowDimSuffix = endXArr.length;
						
						String rowDimSumPos = "AFTER";
						if(rowDimSuffix<this.rowDims.length)
							rowDimSumPos = this.rowDims[rowDimSuffix].getSumpos().toUpperCase();
						
						float[][] horiTotal = new float[this.colDims.length][this.measures.length];			//?洢??????????????
						DOMElement[][] horiTotalTD = new DOMElement[this.colDims.length][this.measures.length];  //????????λ???????TD,?????????λ
								
						if (crossCol == 1) {
								for (int m = 0; m < this.measures.length; m++) {
										tr = insertRowTitle(root,endXcoord,endXArr,crossCol,m,xdimsKeyMap,paramValues);
										for (int c = 0; c < yList.size(); c++) {
												String ycoord = yList.get(c).toString();
												String[] yArr = ycoord.split("__");
				
												int colDimSuffix = 0;
												if(!ycoord.equals(""))
														colDimSuffix = yArr.length;
										
												String colDimSumPos = "AFTER";
												if(colDimSuffix<this.colDims.length)
														colDimSumPos = this.colDims[colDimSuffix].getSumpos().toUpperCase();
												String coord = endXcoord+"###"+ycoord+"^^^"+m;
												
												DOMElement dataTD = new DOMElement("td");
												dataTD.addAttribute("class", "cg_data");
												dataTD.addAttribute("y",c+"");
												
												dealDataTDCell(colDimSuffix,rowDimSuffix,coord,rowDimSumPos,colDimSumPos,c,m,
														dataTD,tdMap,vertTotalTD,vertTotal,
														horiTotalTD,horiTotal,dataMap);
												
												tr.appendChild(dataTD);
										}
										
										for(int h=0;h<horiTotal.length;h++){
												if(horiTotalTD[h][m]!=null){
														horiTotalTD[h][m].setText(measureFormat(m,horiTotal[h][m]+""));
														int tc = Integer.parseInt(horiTotalTD[h][m].getAttribute("y"));
														for(int r=0;r<this.rowDims.length;r++){
																vertTotal[r][tc][m] += horiTotal[h][m];
														}
												}
										}
								}
						} 
						else {
								tr =insertRowTitle(root,endXcoord,endXArr,crossCol,-1,xdimsKeyMap,paramValues);
								for (int c = 0; c < yList.size(); c++) {
										String ycoord = yList.get(c).toString();
										String[] yArr = ycoord.split("__");
										int colDimSuffix = 0;
										if(!ycoord.equals(""))
												colDimSuffix = yArr.length;
										
										String colDimSumPos = "AFTER";

										if(colDimSuffix<this.colDims.length)
												colDimSumPos = this.colDims[colDimSuffix].getSumpos().toUpperCase();
										
										for (int m = 0; m < this.measures.length; m++) {
												DOMElement dataTD = new DOMElement("td");
												dataTD.addAttribute("class", "cg_data");
												dataTD.addAttribute("y",c+"");
												String coord = endXcoord+"###"+ycoord+"^^^"+m;
												
												dealDataTDCell(colDimSuffix,rowDimSuffix,coord,rowDimSumPos,colDimSumPos,c,m,
														dataTD,tdMap,vertTotalTD,vertTotal,
														horiTotalTD,horiTotal,dataMap);
												
												tr.appendChild(dataTD);
										}
								}
								
								for(int h=0;h<this.colDims.length;h++){
									  if(this.colDims[h].getSummary()!=null){
										if(this.colDims[h].getSummary().equals("1")){
											for(int m=0;m<this.measures.length;m++){
													if(horiTotalTD[h][m]!=null){
															horiTotalTD[h][m].setText(measureFormat(m,horiTotal[h][m]+""));
															int tc = Integer.parseInt(horiTotalTD[h][m].getAttribute("y"));
															for(int r=0;r<this.rowDims.length;r++){
																	vertTotal[r][tc][m] += horiTotal[h][m];
															}
													}
											}
										}}
								}
						}
			  				
	  				xPos ++;
	  				if(xPos==newXList.size()){
	  						for(int r=0;r<vertTotal.length;r++){
	  								for(int c=0;c<yList.size();c++)
												for(int m=0;m<this.measures.length;m++)
														if(vertTotalTD[r][c][m] !=null)
															if(r==0&&this.rowDims[0].getSumpos().toUpperCase().equals("BEFORE")||r>0)
																vertTotalTD[r][c][m].setText(measureFormat(m,vertTotal[r][c][m]+""));
								
	  						}
	  						break;
	  				}
	  		}

	  		  columnMerge(colStart,root,rowSpan);
	  		  rowsMerge(rowSpan,root);
			  String table=document.getRootElement().asXML();
			  table = table.replaceAll("&lt;", "<");
			  table = table.replaceAll("&gt;", ">");
			  table = table.replaceAll("&amp;", "&");
			  return table;
	  }
	  private String  measureFormat (int m, String value){
		  	String formatStr = this.measures[m].getFormat() == null ? ""
					: this.measures[m].getFormat();
			if (!formatStr.equals("")) {
				return SimpleFormat.formatStr(
						value, formatStr);
			}
			return value;
	  }
	  private void columnMerge(int start,DOMElement dom,int nTitleLine){
			int tstart = start;
			DOMElement[] tdArr = new DOMElement[this.colDims.length];
			DOMElement[] trArr = new DOMElement[this.colDims.length];
			NodeList[] nlArr = new NodeList[this.colDims.length];
			int nTR = 0;
			if(nTitleLine==1)
					return;
		
			for (int c = 1; c <= this.colDims.length; c++){
				DOMElement tr = (DOMElement) dom.selectSingleNode("/table/tr[" +c+ "]");
				String sStr = tr.asXML();
				trArr[c-1] = tr;
				nlArr[c-1] = tr.getChildNodes();
				tdArr[c-1] = (DOMElement)nlArr[c-1].item(nlArr[c-1].getLength()-1);
				String ssss = tdArr[c-1].asXML();
				String s = ssss;
			}
			nTR = nlArr[0].getLength()-tstart;
			if(this.colDims.length>1){
				int rowSpan = 1;
				for(int r=0;r<nTR;r++){
					int tdStart = tdArr.length-1;
					int tdPos = tdStart-1;
					for(;;){
						if(tdArr[tdStart].getAttribute("si").equals(tdArr[tdPos].getAttribute("si"))&&"".equals(tdArr[tdStart].getAttribute("si"))){
							rowSpan++;
							DOMElement prevTD = (DOMElement)tdArr[tdStart].getPreviousSibling();
							trArr[tdStart].remove(tdArr[tdStart]);
							tdArr[tdStart] = prevTD;
							//String ssss = prevTD.asXML();
							tdStart = tdPos;
							tdPos--;
						}
						else{
							tdArr[tdStart].addAttribute("rowSpan",rowSpan+"");
							for(int tmp=0;tmp<=tdStart;tmp++)
								tdArr[tmp] = (DOMElement)tdArr[tmp].getPreviousSibling();
							rowSpan = 1;
							
							break;
						}
						if(tdStart==0){
							tdArr[0].addAttribute("rowSpan",rowSpan+"");
							tdArr[0] = (DOMElement)tdArr[0].getPreviousSibling();
							rowSpan = 1;
							break;
						}
						//tdPos--;
						
					}
				}
			}
			for (int c = 1; c <= this.colDims.length; c++) {
				String domStr = dom.asXML();
				DOMElement tr = (DOMElement) dom.selectSingleNode("/table/tr[" + c
						+ "]");
				domStr = tr.asXML();
				NodeList nl = tr.getChildNodes();
				if (c > 1)
					tstart = 0;
				DOMElement sNode = (DOMElement) nl.item(tstart);
				int colSpan = 1;
				for (;;) {
					DOMElement eNode = (DOMElement) sNode.getNextSibling();
					String eNodeXml = "";
					if (eNode != null)
						eNodeXml = eNode.asXML();
					if (eNode == null) {
						sNode.addAttribute("colSpan", colSpan + "");
						sNode.addAttribute("align", "center");
						break;
					}
	
					if (sNode.getAttribute("si").equals(eNode.getAttribute("si"))&&
						sNode.getAttribute("pi").equals(eNode.getAttribute("pi"))){
						colSpan++;
						tr.remove(eNode);
					} else {
						sNode.addAttribute("colSpan", colSpan + "");
						sNode.addAttribute("align", "center");
						sNode = eNode;
						colSpan = 1;
					}
				}
			}
		}

		private int[] retrowspans(String[] scoord,String[] ecoord,int[] rerowspan){
			for (int i = 0; i < scoord.length; i++) {
				if(i>=scoord.length||i>=ecoord.length)
					break;
				if (scoord[i].equals(ecoord[i]))
					rerowspan[i]++;
				else
					break;
			}
			return rerowspan;
		}
		private boolean isSame(String[] s1Arr,String[] s2Arr,int level){
				boolean[] same = new boolean[level+1];
				for(int i=0;i<=level;i++){
						String s2Value = new String();
						if(i>=s2Arr.length)
							s2Value = "";
						else
							s2Value = s2Arr[i];
						if(!s1Arr[i].equals(s2Value))
							return false;
				}
				return true;
		}
		private void rowsMerge(int rowSpan,DOMElement dom){
			DOMElement table = (DOMElement) dom.selectSingleNode("/table");
			NodeList trs = table.getChildNodes();
			for (int n = rowSpan; n < trs.getLength(); n++) {
				DOMElement targetTr = (DOMElement) trs.item(n);
				NodeList targetTds = targetTr.getChildNodes();
				String xcoord = targetTr.getAttribute("x").toString();
				String[] xArr = xcoord.split("__");
				if(xcoord.equals("")){
					DOMElement stdDom = (DOMElement)targetTds.item(0);
					for(int r=0;r < this.rowDims.length-1;r++){
						DOMElement removeTD = (DOMElement)stdDom.getNextSibling();
						targetTr.remove(removeTD);
					}
					stdDom.addAttribute("colSpan", this.rowDims.length+"");
					continue;
				}
				if(xArr.length<this.rowDims.length-1){
					DOMElement stdDom = (DOMElement)targetTds.item(xArr.length);
					for(int r=xArr.length;r < this.rowDims.length-1;r++){
						DOMElement removeTD = (DOMElement)stdDom.getNextSibling();
						targetTr.remove(removeTD);
					}
					stdDom.addAttribute("colSpan", (this.rowDims.length-xArr.length)+"");
				}
				else{
					
				}
			}
			for(int r=this.rowDims.length-1;r>=0;r--){
					int nStart = rowSpan;
					int nTR = nStart+1;
					int nrowSpan =1;
					for(;;){
						if(nStart>=trs.getLength()-1||nTR>=trs.getLength())
							break;
						DOMElement startTR = (DOMElement) trs.item(nStart);
						DOMElement curTR = (DOMElement) trs.item(nTR);
						String startCoord = startTR.getAttribute("x").toString();
						String endCoord = curTR.getAttribute("x").toString();
						String[] startArr = startCoord.split("__");
						if(startArr.length<r+1){
							nStart++;
							nTR = nStart+1;
							nrowSpan=1;
							continue;
						}
						String[] endArr = endCoord.split("__");
						if(isSame(startArr,endArr,r)){
							 nrowSpan++;
							((DOMElement)startTR.getChildNodes().item(r)).setAttribute("rowSpan", nrowSpan+"");
							curTR.remove((DOMElement)curTR.getChildNodes().item(r));
							nTR++;
						}else{
							nStart+=nrowSpan;
							nTR = nStart+1;
							nrowSpan=1;
						}
						
						
					}
			}
		}
		
		private String getReportHeader(List allList, String[] paramValues) {
				String reportHeader = "";
				if (this.reportHeaderOutType != null) {
						if (this.reportHeaderOutType.equals("function")) {
								reportHeader = RegExUtil.replaceCon(this.reportHeaderOutValue,
								  this.conditions, paramValues);
								reportHeader = "<script>" + reportHeader + "</script>";
						} else {
								reportHeader = this.reportHeaderOutValue;
						}
				}
				return reportHeader;
		}
 
		private String getReportFooter(List allList, String[] paramValues) {
				String reportFooter = "";
				if (this.reportFooterOutType != null) {
						if (this.reportFooterOutType.equals("function")) {
						  	reportFooter = RegExUtil.replaceCon(this.reportFooterValue,this.conditions, paramValues);
						  	reportFooter = "<script>" + reportFooter + "</script>";
						}else {
						    reportFooter = this.reportFooterValue;
						}
				}
				return reportFooter;
		}
 
		public String toHtml(List allList, String[] paramValues) {
				String table = "<table class=\"page\" width=\"100%\"><tr><td class=\"reportHeader\">"
				  + getReportHeader(allList, paramValues) + "</td></tr><tr><td>";
				 table += toHtmlY(allList, paramValues);
				 return table += "</td></tr><tr><td>"
				  + getReportFooter(allList, paramValues) + "</td></tr></table>";
		}
		public String toHtml(List dataList, List allList, String[] paramValues) {
			 
			  return "";
		}
		
		public String formatContext(List data, List allList, String[] paramValues) {
				// TODO Auto-generated method stub
				return toHtml(allList, paramValues);
		}
 
 		public String getTemplateVm() {
  			// TODO Auto-generated method stub
  			return this.templatefilename;
 		}
 
 		 public static void main(String[] args) {
// 			JavaScriptParser.init("D:\\ThunderReport\\Version1.4\\Development\\ThunderReport\\web\\WEB-INF\\include\\reportserver.js");
 			 Dim rowdim1 = new Dim();
 			  rowdim1.setName("州名");
 			  rowdim1.setSummary("1");
 			  rowdim1.setSumname("州名");
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
 			  rowdim2.setSumname("地区");
 			  rowdim2.setSumpos("after");
 			  rowdim2.setDimValue("{district}");
 			 
 			  Dim rowdim3 = new Dim();
 			  rowdim3.setName("国家");
 			  rowdim3.setCode("country");
 			  rowdim3.setSumpos("before");
 			  rowdim3.setSummary("1");
 			  rowdim3.setSumby("district,region");
 			  rowdim3.setSumname("国家");
 			  rowdim3.setDimValue("{country}");
 			 
 			  Dim[] rowdims = { rowdim1, rowdim2, rowdim3 };
 			 
 			  Dim coldim = new Dim();
 			  coldim.setName("StoreType");
 			  coldim.setCode("store type");
 			  coldim.setDimValue("{store type}");
 			  coldim.setSummary("1");
 			  coldim.setGroup("1");
 			  coldim.setSumpos("after");
 			  coldim.setSumname("Total");
 			 
 			  Dim coldim1 = new Dim();
 			  coldim1.setName("客户类型");
 			  coldim1.setCode("cust type");
 			  coldim1.setGroup("1");
 			  coldim1.setDimValue("{cust type}");
 			  coldim1.setSummary("1");
 			  coldim1.setSumpos("BOTH");
 			  coldim1.setSumname("客户类型");
 			  coldim1.setKeycode("cust type");
// 			  coldim1.setDimValue("doCacheHref({'filename':'monitor_03_01_02.xml','custtype':'{cust type}'},'{cust type}')");
// 			  coldim1.setOutputtype("function");
 			  
 			  Dim coldim2 = new Dim();
 			  coldim2.setName("客户关系");
 			  coldim2.setCode("cust relation");
 			  coldim2.setGroup("1");
 			  coldim2.setDimValue("{cust relation}");
 			  coldim2.setSummary("1");
 			  coldim2.setSumpos("after");
 			  coldim2.setSumname("客户关系");
 			 
 			  Dim[] coldims = { coldim, coldim1};
 			 
 			  Measure measure1 = new Measure();
 			  measure1.setName("unit sales");
 			  measure1.setCode("unit sales");
 			  measure1.setMeasureValue("{unit sales}");
 			  measure1.setDisplay("true");
 			  measure1.setFormat("0.00");
 			 
 			  Measure measure2 = new Measure();
 			  measure2.setName("amount sales");
 			  measure2.setCode("amount sales");
 			  measure2.setMeasureValue("{amount sales}");
 			  measure2.setDisplay("ture");
 			  measure2.setFormat("0.000");
 			 
 			  Measure measure3 = new Measure();
 			  measure3.setName("price");
 			  measure3.setCode("price");
 			  measure3.setMeasureValue("{price}");
 			  measure3.setDisplay("ture");
 			  Measure[] measures = { measure1,measure2};
 			 
 			  List li = new ArrayList();
 			 
 			  Map d11 = new HashMap();  
 			  d11.put("region", "亚洲");
 			  d11.put("country", "china");
 			  d11.put("district", "east");
 			  d11.put("store type", "online");
 			  d11.put("cust type", "big customer");
 			  d11.put("cust relation", "good");
 			  d11.put("unit sales", String.valueOf("100"));
 			  d11.put("amount sales", String.valueOf("200"));
 			  d11.put("price", String.valueOf("200"));
 			  
 			  
 			 
 			  Map d111 = new HashMap();
 			  d111.put("region", "亚洲");
 			  d111.put("country", "china");
 			  d111.put("district", "east");
 			  d111.put("store type", "online");
 			  d111.put("cust type", "small customer");
 			  d111.put("unit sales", String.valueOf("100"));
 			  d111.put("amount sales", String.valueOf("200"));
 			  d111.put("price", String.valueOf("200"));
 			  li.add(d111);
 			 
 			  Map d21 = new HashMap();
 			  d21.put("region", "亚洲");
 			  d21.put("country", "china");
 			  d21.put("district", "east");
 			  d21.put("store type", "franchise");
 			  d21.put("cust type", "small customer");
 			  d21.put("unit sales", String.valueOf("100"));
 			  d21.put("amount sales", String.valueOf("200"));
 			  d21.put("price", String.valueOf("200"));
 			 
 			  Map d211 = new HashMap();
 			  d211.put("region", "亚洲");
 			  d211.put("country", "china");
 			  d211.put("district", "east");
 			  d211.put("store type", "franchise");
 			  d211.put("cust type", "big customer");
 			  d211.put("unit sales", String.valueOf("100"));
 			  d211.put("amount sales", String.valueOf("200"));
 			  d211.put("price", String.valueOf("200"));
 			 
 			  li.add(d211);
 			  Map d31 = new HashMap();
 			  d31.put("region", "亚洲");
 			  d31.put("country", "china");
 			  d31.put("district", "east");
 			  d31.put("store type", "superstore");
 			  d31.put("cust type", "small customer");
 			  d31.put("unit sales", String.valueOf("100"));
 			  d31.put("amount sales", String.valueOf("200"));
 			  d31.put("price", String.valueOf("200"));
 			  
 			  Map d311 = new HashMap();
 			  d311.put("region", "亚洲");
 			  d311.put("country", "china");
 			  d311.put("district", "east");
 			  d311.put("store type", "superstore");
 			  d311.put("cust type", "big customer");
 			  d311.put("unit sales", String.valueOf("100"));
 			  d311.put("amount sales", String.valueOf("200"));
 			  d311.put("price", String.valueOf("200"));
 			  li.add(d311);
 			 
 			  Map d11w = new HashMap();
 			  d11w.put("region", "亚洲");
 			  d11w.put("country", "india");
 			  d11w.put("district", "east");
 			  d11w.put("store type", "online");
 			  d11w.put("cust type", "big customer");
 			  d11w.put("unit sales", String.valueOf("100"));
 			  d11w.put("amount sales", String.valueOf("200"));
 			  d11w.put("price", String.valueOf("200"));
 			  li.add(d11w);
 			 
 			  Map d111w = new HashMap();
 			  d111w.put("region", "亚洲");
 			  d111w.put("country", "india");
 			  d111w.put("district", "east");
 			  d111w.put("store type", "online");
 			  d111w.put("cust type", "small customer");
 			  d111w.put("unit sales", String.valueOf("100"));
 			  d111w.put("amount sales", String.valueOf("200"));
 			  d111w.put("price", String.valueOf("200"));
 			  li.add(d111w);
 			 
 			  Map d21w = new HashMap();
 			  d21w.put("region", "亚洲");
 			  d21w.put("country", "india");
 			  d21w.put("district", "east");
 			  d21w.put("store type", "franchise");
 			  d21w.put("cust type", "small customer");
 			  d21w.put("unit sales", String.valueOf("100"));
 			  d21w.put("amount sales", String.valueOf("200"));
 			  d21w.put("price", String.valueOf("200"));
 			  li.add(d21w);
 			 
 			  Map d211w = new HashMap();
 			  d211w.put("region", "亚洲");
 			  d211w.put("country", "india");
 			  d211w.put("district", "east");
 			  d211w.put("store type", "franchise");
 			  d211w.put("cust type", "big customer");
 			  d211w.put("unit sales", String.valueOf("100"));
 			  d211w.put("amount sales", String.valueOf("200"));
 			  d211w.put("price", String.valueOf("200"));
 			  li.add(d211w);
 			 
 			  Map d31w = new HashMap();
 			  d31w.put("region", "亚洲");
 			  d31w.put("country", "india");
 			  d31w.put("district", "east");
 			  d31w.put("store type", "superstore");
 			  d31w.put("cust type", "small customer");
 			  d31w.put("unit sales", String.valueOf("100"));
 			  d31w.put("amount sales", String.valueOf("200"));
 			  d31w.put("price", String.valueOf("200"));
 			  li.add(d31w);
 			 
 			  Map d311w = new HashMap();
 			  d311w.put("region", "亚洲");
 			  d311w.put("country", "india");
 			  d311w.put("district", "east");
 			  d311w.put("store type", "superstore");
 			  d311w.put("cust type", "big customer");
 			  d311w.put("unit sales", String.valueOf("100"));
 			  d311w.put("amount sales", String.valueOf("200"));
 			  d311w.put("price", String.valueOf("200"));
 			  li.add(d311w);
 			 
 			  Map d12 = new HashMap();
 			  d12.put("region", "亚洲");
 			  d12.put("country", "japan");
 			  d12.put("district", "west亚");
 			  d12.put("store type", "online");
 			  d12.put("cust type", "big customer");
 			  d12.put("unit sales", String.valueOf("100"));
 			  d12.put("amount sales", String.valueOf("200"));
 			  d12.put("price", String.valueOf("200"));
 			 
 			  Map d121 = new HashMap();
 			  d121.put("region", "亚洲");
 			  d121.put("country", "japan");
 			  d121.put("district", "west亚");
 			  d121.put("store type", "online");
 			  d121.put("cust type", "small customer");
 			  d121.put("unit sales", String.valueOf("100"));
 			  d121.put("amount sales", String.valueOf("200"));
 			  d121.put("price", String.valueOf("200"));
 			  li.add(d121);
 			 
 			  Map d22 = new HashMap();
 			  d22.put("region", "亚洲");
 			  d22.put("country", "japan");
 			  d22.put("district", "west亚");
 			  d22.put("store type", "franchise");
 			  d22.put("cust type", "big customer");
 			  d22.put("unit sales", String.valueOf("100"));
 			  d22.put("amount sales", String.valueOf("200"));
 			  d22.put("price", String.valueOf("200"));
 			 
 			  Map d221 = new HashMap();
 			  d221.put("region", "亚洲");
 			  d221.put("country", "japan");
 			  d221.put("district", "west亚");
 			  d221.put("store type", "franchise");
 			  d221.put("cust type", "small customer");
 			  d221.put("unit sales", String.valueOf("100"));
 			  d221.put("amount sales", String.valueOf("200"));
 			  d221.put("price", String.valueOf("200"));
 			  
 			  li.add(d221);
 			  Map d32 = new HashMap();
 			  d32.put("region", "亚洲");
 			  d32.put("country", "japan");
 			  d32.put("district", "west亚");
 			  d32.put("store type", "superstore");
 			  d32.put("cust type", "big customer");
 			  d32.put("unit sales", String.valueOf("100"));
 			  d32.put("amount sales", String.valueOf("200"));
 			  d32.put("price", String.valueOf("200"));
 			 
 			  Map d321 = new HashMap();
 			  d321.put("region", "亚洲");
 			  d321.put("country", "japan");
 			  d321.put("district", "west亚");
 			  d321.put("store type", "superstore");
 			  d321.put("cust type", "small customer");
 			  d321.put("unit sales", String.valueOf("100"));
 			  d321.put("amount sales", String.valueOf("200"));
 			  d321.put("price", String.valueOf("200"));
 			  li.add(d321);
 			 
 			  Map d12w = new HashMap();
 			  d12w.put("region", "亚洲");
 			  d12w.put("country", "korea");
 			  d12w.put("district", "west亚");
 			  d12w.put("store type", "online");
 			  d12w.put("cust type", "big customer");
 			  d12w.put("unit sales", String.valueOf("100"));
 			  d12w.put("amount sales", String.valueOf("200"));
 			  d12w.put("price", String.valueOf("200"));
 			  li.add(d12w);
 			 
 			  Map d121w = new HashMap();
 			  d121w.put("region", "亚洲");
 			  d121w.put("country", "korea");
 			  d121w.put("district", "west亚");
 			  d121w.put("store type", "online");
 			  d121w.put("cust type", "small customer");
 			  d121w.put("unit sales", String.valueOf("100"));
 			  d121w.put("amount sales", String.valueOf("200"));
 			  d121w.put("price", String.valueOf("200"));
 			  li.add(d121w);
 			 
 			  Map d22w = new HashMap();
 			  d22w.put("region", "亚洲");
 			  d22w.put("country", "korea");
 			  d22w.put("district", "west亚");
 			  d22w.put("store type", "franchise");
 			  d22w.put("cust type", "big customer");
 			  d22w.put("unit sales", String.valueOf("100"));
 			  d22w.put("amount sales", String.valueOf("200"));
 			  d22w.put("price", String.valueOf("200"));
 			  li.add(d22w);
 			 
 			  Map d221w = new HashMap();
 			  d221w.put("region", "亚洲");
 			  d221w.put("country", "korea");
 			  d221w.put("district", "west亚");
 			  d221w.put("store type", "franchise");
 			  d221w.put("cust type", "small customer");
 			  d221w.put("unit sales", String.valueOf("100"));
 			  d221w.put("amount sales", String.valueOf("200"));
 			  d221w.put("price", String.valueOf("200"));
 			  li.add(d221w);
 			 
 			  Map d32w = new HashMap();
 			  d32w.put("region", "亚洲");
 			  d32w.put("country", "korea");
 			  d32w.put("district", "west亚");
 			  d32w.put("store type", "superstore");
 			  d32w.put("cust type", "big customer");
 			  d32w.put("unit sales", String.valueOf("100"));
 			  d32w.put("amount sales", String.valueOf("200"));
 			  d32w.put("price", String.valueOf("200"));
 			  li.add(d32w);
 			 
 			  Map d321w = new HashMap();
 			  d321w.put("region", "亚洲");
 			  d321w.put("district", "west亚");
 			  d321w.put("country", "korea");
 			  d321w.put("store type", "superstore");
 			  d321w.put("cust type", "small customer");
 			  d321w.put("unit sales", String.valueOf("100"));
 			  d321w.put("amount sales", String.valueOf("200"));
 			  d321w.put("price", String.valueOf("200"));
 			  li.add(d321w);
 			 
 			  Map d41 = new HashMap();
 			  d41.put("region", "europe");
 			  d41.put("country", "france");
 			  d41.put("district", "westeurope");
 			  d41.put("store type", "online");
 			  d41.put("cust type", "big customer");
 			  d41.put("unit sales", String.valueOf("100"));
 			  d41.put("amount sales", String.valueOf("200"));
 			  d41.put("price", String.valueOf("200"));
 			 
 			  Map d411 = new HashMap();
 			  d411.put("region", "europe");
 			  d411.put("country", "france");
 			  d411.put("district", "westeurope");
 			  d411.put("store type", "online");
 			  d411.put("cust type", "small customer");
 			  d411.put("unit sales", String.valueOf("100"));
 			  d411.put("amount sales", String.valueOf("200"));
 			  d411.put("price", String.valueOf("200"));
 			 
 			  li.add(d411);
 			  Map d51 = new HashMap();
 			  d51.put("region", "europe");
 			  d51.put("country", "france");
 			  d51.put("district", "westeurope");
 			  d51.put("store type", "franchise");
 			  d51.put("cust type", "big customer");
 			  d51.put("unit sales", String.valueOf("100"));
 			  d51.put("amount sales", String.valueOf("200"));
 			  d51.put("price", String.valueOf("200"));
 			 
 			  Map d511 = new HashMap();
 			  d511.put("region", "europe");
 			  d511.put("country", "france");
 			  d511.put("district", "westeurope");
 			  d511.put("store type", "franchise");
 			  d511.put("cust type", "small customer");
 			  d511.put("unit sales", String.valueOf("100"));
 			  d511.put("amount sales", String.valueOf("200"));
 			  d511.put("price", String.valueOf("200"));
 			 
 			  li.add(d511);
 			  Map d61 = new HashMap();
 			  d61.put("region", "europe");
 			  d61.put("country", "france");
 			  d61.put("district", "westeurope");
 			  d61.put("store type", "superstore");
 			  d61.put("cust type", "big customer");
 			  d61.put("unit sales", String.valueOf("100"));
 			  d61.put("amount sales", String.valueOf("200"));
 			  d61.put("price", String.valueOf("200"));
 			 
 			  Map d611 = new HashMap();
 			  d611.put("region", "europe");
 			  d611.put("country", "france");
 			  d611.put("district", "westeurope");
 			  d611.put("store type", "superstore");
 			  d611.put("cust type", "small customer");
 			  d611.put("unit sales", String.valueOf("100"));
 			  d611.put("amount sales", String.valueOf("200"));
 			  d611.put("price", String.valueOf("200"));
 			 
 			  li.add(d611);
 			  Map d42 = new HashMap();
 			  d42.put("region", "europe");
 			  d42.put("country", "ireland");
 			  d42.put("district", "westeurope");
 			  d42.put("store type", "online");
 			  d42.put("cust type", "big customer");
 			  d42.put("unit sales", String.valueOf("100"));
 			  d42.put("amount sales", String.valueOf("200"));
 			  d42.put("price", String.valueOf("200"));
 			 
 			  Map d421 = new HashMap();
 			  d421.put("region", "europe");
 			  d421.put("country", "ireland");
 			  d421.put("district", "westeurope");
 			  d421.put("store type", "online");
 			  d421.put("cust type", "small customer");
 			  d421.put("unit sales", String.valueOf("100"));
 			  d421.put("amount sales", String.valueOf("200"));
 			  d421.put("price", String.valueOf("200"));
 			 
 			  li.add(d421);
 			  Map d52 = new HashMap();
 			  d52.put("region", "europe");
 			  d52.put("country", "ireland");
 			  d52.put("district", "westeurope");
 			  d52.put("store type", "franchise");
 			  d52.put("cust type", "big customer");
 			  d52.put("unit sales", String.valueOf("100"));
 			  d52.put("amount sales", String.valueOf("200"));
 			  d52.put("price", String.valueOf("200"));
 			 
 			  Map d521 = new HashMap();
 			  d521.put("region", "europe");
 			  d521.put("country", "ireland");
 			  d521.put("district", "westeurope");
 			  d521.put("store type", "franchise");
 			  d521.put("cust type", "small customer");
 			  d521.put("unit sales", String.valueOf("100"));
 			  d521.put("amount sales", String.valueOf("200"));
 			  d521.put("price", String.valueOf("200"));
 			 
 			  li.add(d521);
 			  Map d62 = new HashMap();
 			  d62.put("region", "europe");
 			  d62.put("country", "ireland");
 			  d62.put("district", "westeurope");
 			  d62.put("store type", "superstore");
 			  d62.put("cust type", "big customer");
 			  d62.put("unit sales", String.valueOf("100"));
 			  d62.put("amount sales", String.valueOf("200"));
 			  d62.put("price", String.valueOf("200"));
 			 
 			  Map d621 = new HashMap();
 			  d621.put("region", "europe");
 			  d621.put("country", "ireland");
 			  d621.put("district", "westeurope");
 			  d621.put("store type", "superstore");
 			  d621.put("cust type", "small customer");
 			  d621.put("unit sales", String.valueOf("100"));
 			  d621.put("amount sales", String.valueOf("200"));
 			  d621.put("price", String.valueOf("200"));
 			 
 			  li.add(d621);
 			  Map d71 = new HashMap();
 			  d71.put("region", "americas");
 			  d71.put("country", "usa");
 			  d71.put("district", "north");
 			  d71.put("store type", "online");
 			  d71.put("cust type", "big customer");
 			  d71.put("unit sales", String.valueOf("100"));
 			  d71.put("amount sales", String.valueOf("200"));
 			  d71.put("price", String.valueOf("200"));
 			 
 			  Map d711 = new HashMap();
 			  d711.put("region", "americas");
 			  d711.put("country", "usa");
 			  d711.put("district", "north");
 			  d711.put("store type", "online");
 			  d711.put("cust type", "small customer");
 			  d711.put("unit sales", String.valueOf("100"));
 			  d711.put("amount sales", String.valueOf("200"));
 			  d711.put("price", String.valueOf("200"));
 			 
 			  li.add(d711);
 			  Map d81 = new HashMap();
 			  d81.put("region", "americas");
 			  d81.put("country", "usa");
 			  d81.put("district", "north");
 			  d81.put("store type", "franchise");
 			  d81.put("cust type", "big customer");
 			  d81.put("unit sales", String.valueOf("100"));
 			  d81.put("amount sales", String.valueOf("200"));
 			  d81.put("price", String.valueOf("200"));
 			 
 			  Map d811 = new HashMap();
 			  d811.put("region", "americas");
 			  d811.put("country", "usa");
 			  d811.put("district", "north");
 			  d811.put("store type", "franchise");
 			  d811.put("cust type", "small customer");
 			  d811.put("unit sales", String.valueOf("100"));
 			  d811.put("amount sales", String.valueOf("200"));
 			  d811.put("price", String.valueOf("200"));
 			 
 			  li.add(d811);
 			  Map d91 = new HashMap();
 			  d91.put("region", "americas");
 			  d91.put("country", "usa");
 			  d91.put("district", "north");
 			  d91.put("store type", "superstore");
 			  d91.put("cust type", "big customer");
 			  d91.put("unit sales", String.valueOf("100"));
 			  d91.put("amount sales", String.valueOf("200"));
 			  d91.put("price", String.valueOf("200"));
 			 
 			  Map d911 = new HashMap();
 			  d911.put("region", "americas");
 			  d911.put("country", "usa");
 			  d911.put("district", "north");
 			  d911.put("store type", "superstore");
 			  d911.put("cust type", "small customer");
 			  d911.put("unit sales", String.valueOf("100"));
 			  d911.put("amount sales", String.valueOf("200"));
 			  d911.put("price", String.valueOf("200"));
 			  li.add(d911);
 			 
 			  Map d71w = new HashMap();
 			  d71w.put("region", "americas");
 			  d71w.put("country", "canada");
 			  d71w.put("district", "north");
 			  d71w.put("store type", "online");
 			  d71w.put("cust type", "big customer");
 			  d71w.put("unit sales", String.valueOf("100"));
 			  d71w.put("amount sales", String.valueOf("200"));
 			  d71w.put("price", String.valueOf("200"));
 			 
 			  li.add(d71w);
 			  Map d711w = new HashMap();
 			  d711w.put("region", "americas");
 			  d711w.put("country", "canada");
 			  d711w.put("district", "north");
 			  d711w.put("store type", "online");
 			  d711w.put("cust type", "small customer");
 			  d711w.put("unit sales", String.valueOf("100"));
 			  d711w.put("amount sales", String.valueOf("200"));
 			  d711w.put("price", String.valueOf("200"));
 			 
 			  li.add(d711w);
 			  Map d81w = new HashMap();
 			  d81w.put("region", "americas");
 			  d81w.put("country", "canada");
 			  d81w.put("district", "north");
 			  d81w.put("store type", "franchise");
 			  d81w.put("cust type", "big customer");
 			  d81w.put("unit sales", String.valueOf("100"));
 			  d81w.put("amount sales", String.valueOf("200"));
 			  d81w.put("price", String.valueOf("200"));
 			  li.add(d81w);
 			  Map d811w = new HashMap();
 			  d811w.put("region", "americas");
 			  d811w.put("country", "canada");
 			  d811w.put("district", "north");
 			  d811w.put("store type", "franchise");
 			  d811w.put("cust type", "small customer");
 			  d811w.put("unit sales", String.valueOf("100"));
 			  d811w.put("amount sales", String.valueOf("200"));
 			  d811w.put("price", String.valueOf("200"));
 			  li.add(d811w);
 			  Map d91w = new HashMap();
 			  d91w.put("region", "americas");
 			  d91w.put("country", "canada");
 			  d91w.put("district", "north");
 			  d91w.put("store type", "superstore");
 			  d91w.put("cust type", "big customer");
 			  d91w.put("unit sales", String.valueOf("100"));
 			  d91w.put("amount sales", String.valueOf("200"));
 			  d91w.put("price", String.valueOf("200"));
 			  li.add(d91w);
 			  Map d911w = new HashMap();
 			  d911w.put("region", "americas");
 			  d911w.put("country", "canada");
 			  d911w.put("district", "north");
 			  d911w.put("store type", "superstore");
 			  d911w.put("cust type", "small customer");
 			  d911w.put("unit sales", String.valueOf("100"));
 			  d911w.put("amount sales", String.valueOf("200"));
 			  d911w.put("price", String.valueOf("200"));
 			  li.add(d911w);
 			 
 			  Map d72 = new HashMap();
 			  d72.put("region", "americas");
 			  d72.put("country", "brazil");
 			  d72.put("district", "south");
 			  d72.put("store type", "online");
 			  d72.put("cust type", "big customer");
 			  d72.put("unit sales", String.valueOf("100"));
 			  d72.put("amount sales", String.valueOf("200"));
 			  d72.put("price", String.valueOf("200"));
 			 
 			  Map d721 = new HashMap();
 			  d721.put("region", "americas");
 			  d721.put("country", "brazil");
 			  d721.put("district", "south");
 			  d721.put("store type", "online");
 			  d721.put("cust type", "small customer");
 			  d721.put("unit sales", String.valueOf("100"));
 			  d721.put("amount sales", String.valueOf("200"));
 			  d721.put("price", String.valueOf("200"));
 			 
 			  li.add(d721);
 			  Map d82 = new HashMap();
 			  d82.put("region", "americas");
 			  d82.put("country", "brazil");
 			  d82.put("district", "south");
 			  d82.put("store type", "franchise");
 			  d82.put("cust type", "big customer");
 			  d82.put("unit sales", String.valueOf("100"));
 			  d82.put("amount sales", String.valueOf("200"));
 			  d82.put("price", String.valueOf("200"));
 			 
 			  Map d821 = new HashMap();
 			  d821.put("region", "americas");
 			  d821.put("country", "brazil");
 			  d821.put("district", "south");
 			  d821.put("store type", "franchise");
 			  d821.put("cust type", "small customer");
 			  d821.put("unit sales", String.valueOf("100"));
 			  d821.put("amount sales", String.valueOf("200"));
 			  d821.put("price", String.valueOf("200"));
 			 
 			  li.add(d821);
 			  Map d92 = new HashMap();
 			  d92.put("region", "americas");
 			  d92.put("country", "brazil");
 			  d92.put("district", "south");
 			  d92.put("store type", "superstore");
 			  d92.put("cust type", "big customer");
 			  d92.put("unit sales", String.valueOf("100"));
 			  d92.put("amount sales", String.valueOf("200"));
 			  d92.put("price", String.valueOf("200"));
 			 
 			  Map d921 = new HashMap();
 			  d921.put("region", "americas");
 			  d921.put("country", "brazil");
 			  d921.put("district", "south");
 			  d921.put("store type", "superstore");
 			  d921.put("cust type", "small customer");
 			  d921.put("unit sales", String.valueOf("100"));
 			  d921.put("amount sales", String.valueOf("200"));
 			  d921.put("price", String.valueOf("200"));
 			  li.add(d921);
 			 
 			  Map d72w = new HashMap();
 			  d72w.put("region", "americas");
 			  d72w.put("country", "argentina");
 			  d72w.put("district", "south");
 			  d72w.put("store type", "online");
 			  d72w.put("cust type", "big customer");
 			  d72w.put("unit sales", String.valueOf("100"));
 			  d72w.put("amount sales", String.valueOf("200"));
 			  d72w.put("price", String.valueOf("200"));
 			  
 			  li.add(d72w);
 			  Map d721w = new HashMap();
 			  d721w.put("region", "americas");
 			  d721w.put("country", "argentina");
 			  d721w.put("district", "south");
 			  d721w.put("store type", "online");
 			  d721w.put("cust type", "small customer");
 			  d721w.put("unit sales", String.valueOf("100"));
 			  d721w.put("amount sales", String.valueOf("200"));
 			  d721w.put("price", String.valueOf("200"));
 			 
 			  li.add(d721w);
 			  Map d82w = new HashMap();
 			  d82w.put("region", "americas");
 			  d82w.put("country", "argentina");
 			  d82w.put("district", "south");
 			  d82w.put("store type", "franchise");
 			  d82w.put("cust type", "big customer");
 			  d82w.put("unit sales", String.valueOf("100"));
 			  d82w.put("amount sales", String.valueOf("200"));
 			  d82w.put("price", String.valueOf("200"));
 			  
 			  li.add(d82w);
 			  Map d821w = new HashMap();
 			  d821w.put("region", "americas");
 			  d821w.put("country", "argentina");
 			  d821w.put("district", "south");
 			  d821w.put("store type", "franchise");
 			  d821w.put("cust type", "small customer");
 			  d821w.put("unit sales", String.valueOf("100"));
 			  d821w.put("amount sales", String.valueOf("200"));
 			  d821w.put("price", String.valueOf("200"));
 			  li.add(d821w);
 			 
 			  Map d92w = new HashMap();
 			  d92w.put("region", "americas");
 			  d92w.put("country", "argentina");
 			  d92w.put("district", "south");
 			  d92w.put("store type", "superstore");
 			  d92w.put("cust type", "big customer");
 			  d92w.put("unit sales", String.valueOf("100"));
 			  d92w.put("amount sales", String.valueOf("200"));
 			  d92w.put("price", String.valueOf("200"));
 			  li.add(d92w);
 			 
 			  Map d921w = new HashMap();
 			  d921w.put("region", "americas");
 			  d921w.put("country", "argentina");
 			  d921w.put("district", "south");
 			  d921w.put("store type", "superstore");
 			  d921w.put("cust type", "small customer");
 			  d921w.put("unit sales", String.valueOf("100"));
 			  d921w.put("amount sales", String.valueOf("200"));
 			  d921w.put("price", String.valueOf("200"));
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
 			 
 		 
 		  CrossGrid cg = new CrossGrid();
 		  cg.conditions=new Condition[0];
 		  // cg.setDataList(li);
 		  cg.setColDims(coldims);
 		  cg.setMeasures(measures);
 		  cg.setRowDims(rowdims);
 		  //cg.setMeasureCrossWith("COLDIMS");
 		  String ss = cg.toHtml(li, new String[] { "1111" });
 		  System.out.println(ss);
 		 }
 		 
 		 public String getMeasureCrossWith() {
 		  return measureCrossWith;
 		 }
 		 

 		 public void setMeasureCrossWith(String measureCrossWith) {
 		  this.measureCrossWith = measureCrossWith;
 		 }
 		}

 		 
