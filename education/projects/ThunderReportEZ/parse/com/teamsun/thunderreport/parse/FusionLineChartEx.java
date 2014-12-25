package com.teamsun.thunderreport.parse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.map.ListOrderedMap;
import org.dom4j.Element;



public class FusionLineChartEx extends FusionChartEx {

	

	private String chartid;

	
	private Map xaxismap;



	/**
	 * 所有参数


	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	
	public String getParams() {
		String result = "";
		for (Iterator it = condsValues.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			result += key + "=" + this.condsValues.get(key).toString() + "&";
		}
		if (result.endsWith("&"))
			result = result.replaceAll("&$", "");
		return result;
	}
/****
 * 转换并对齐list数据
 * @author qizh
 * @param data:数据库查出来的list,catepara: list中的横轴参数名字,cond:分组用的参数名,value:值的参数名  paraset: x轴坐标的名称顺序列表，一般来说是一个treeset 
 */
	public List AlterList(List data,String catepara,String cond,String value,Set paraset){
		List destlist=new ArrayList();
		Map xmap=this.getXaxismap();
		 if(data.size()!=0){
			 Map testmap=(Map)data.get(0);
				List mapcontentlist=new ArrayList();
				for(int l=0;l<testmap.keySet().size();l++){
					if(!testmap.keySet().toArray()[l].equals(value)&&!testmap.keySet().toArray()[l].equals(catepara)&&!testmap.keySet().toArray()[l].equals(cond)){
					mapcontentlist.add(testmap.keySet().toArray()[l]);
					}
				}
		for(int j=0;j<paraset.size();j++){
			for(int i=0;i<xmap.size();i++){
				Map premap=new ListOrderedMap();
				premap.put(cond, paraset.toArray()[j].toString().trim());
				premap.put(catepara, xmap.get(String.valueOf(i)));
			    destlist.add(premap);
			}
		}
		
	  for(int k=0;k<destlist.size();k++){	
		for(int m=0;m<data.size();m++){
			Map tempmap=(Map)data.get(m);
			Map curmap=(Map)destlist.get(k);
			if(tempmap.get(catepara).toString().trim().equals(curmap.get(catepara))&&tempmap.get(cond).toString().trim().equals(curmap.get(cond).toString().trim())){
				for(int i=0;i<mapcontentlist.size();i++){
					curmap.put(mapcontentlist.get(i), tempmap.get(mapcontentlist.get(i)));
				}
				curmap.put(value, tempmap.get(value));
			}
		}
	  }
//		for(int k=0;k<destlist.size();k++){
//			System.out.println(destlist.get(k));
//		}
//		System.out.println("----------------------------------");
//		 
//		for(int k=0;k<data.size();k++){
//			System.out.println(data.get(k));
//		}
		 }
		 
		return destlist;
	}
//-----------------没有paraset的情况

	public List AlterList(List data,String catepara,String cond,String value){
		List destlist=new ArrayList();
		Map xmap=this.getXaxismap();
	  if(data.size()!=0){
		Map testmap=(Map)data.get(0);
			List mapcontentlist=new ArrayList();
			for(int l=0;l<testmap.keySet().size();l++){
				if(!testmap.keySet().toArray()[l].equals(value)&&!testmap.keySet().toArray()[l].equals(catepara)&&!testmap.keySet().toArray()[l].equals(cond)){
				mapcontentlist.add(testmap.keySet().toArray()[l]);
				}
			}
		Set altset=new TreeSet();
		for(int j=0;j<data.size();j++){
		  
			Map testmap1=(Map)data.get(j);
			if(testmap1.get(cond)!=null){
			  altset.add(testmap1.get(cond));
			}
			
        }
		for(int j=0;j<altset.size();j++){
			for(int i=0;i<xmap.size();i++){
				Map premap=new ListOrderedMap();
				premap.put(cond, altset.toArray()[j].toString().trim());
				premap.put(catepara, xmap.get(String.valueOf(i)));
			    destlist.add(premap);
			}
		}
		 for(int k=0;k<destlist.size();k++){	
				for(int m=0;m<data.size();m++){
					Map tempmap=(Map)data.get(m);
					Map curmap=(Map)destlist.get(k);
					if(tempmap.get(catepara).toString().trim().equals(curmap.get(catepara))&&tempmap.get(cond).toString().trim().equals(curmap.get(cond).toString().trim())){
						for(int i=0;i<mapcontentlist.size();i++){
							curmap.put(mapcontentlist.get(i), tempmap.get(mapcontentlist.get(i)));
						}
						curmap.put(value, tempmap.get(value));
					}
				}
		}

//		for(int k=0;k<destlist.size();k++){
//			System.out.println(destlist.get(k));
//		}
//		System.out.println("----------------------------------");
//		 
//		for(int k=0;k<data.size();k++){
//			System.out.println(data.get(k));
//		}
	  }
		return destlist;
	}
//-----------------------
//	public List AlterListTest(List data,String catepara,String cond,String value){
//		List destlist=new ArrayList();
//		Map xmap=this.getXaxismap();
//		System.out.println(catepara+"==="+cond+"===="+value);
//		for(int i=0;i<data.size();i++){
//			System.out.println(data.get(i));
//		}
//		return destlist;
//	}
	
	
	/**
	 * 模板文件名字
	 * 
	 * @return
	 */
	public String getLayoutFileName() {
		String filename = this.fusionchart.attributeValue("layoutfilename");
		if (filename == null || filename.equals("")) {
			return "fusionchartlayout.vm";
		} else
			return filename;
	}

	/**
	 * 除去复选条件外的其他条件值


	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	public String getParamsExceptCheckedReference() {
		Element chart = fusionchart.element("chart");
		Element datasets = chart.element("datasets");
		String cond_value = datasets.attributeValue("cond_value");
		String cond_disp = datasets.attributeValue("cond_disp");
		String result = "";
		if (this.selected())
			for (Iterator it = condsValues.keySet().iterator(); it.hasNext();) {
				String key = it.next().toString();
				if (cond_value == null || cond_value.equals("")) {
					result += key + "=" + this.condsValues.get(key).toString()
							+ "&";
				} else {
					if (!key.equals(cond_value) && !key.equals(cond_disp))
						result += key + "="
								+ this.condsValues.get(key).toString() + "&";
				}
			}
		else
			for (Iterator it = condsValues.keySet().iterator(); it.hasNext();) {
				String key = it.next().toString();
				result += key + "=" + this.condsValues.get(key).toString()
						+ "&";
			}

		;

		return result;
	}


	/**
	 * 得到复选条件显示值的参数名


	 * 
	 * 
	 * 
	 * 
	 * @return
	 */


	/**
	 * 是否有合计显示


	 * 
	 * 
	 * @return
	 */
	public boolean sumselected() {
		Element chart = this.fusionchart.element("chart");
		String s = chart.attributeValue("sumselected");
		if (s != null && !s.equals("")) {
			//return Boolean.parseBoolean(s);
			return Boolean.valueOf(s).booleanValue();
		}
		return false;
	}



	/**
	 * 得到所有的复选参数的值 (数组)
	 * 
	 * @return
	 */
	public String[] selectFieldValues() {
		if (this.selected()) {
			Object value = this.condsValues
					.get(this.selectConditionValueName());
			//System.out.println(value.toString());
			return value.toString().split("\\,");
		} else {
			return new String[] {};
		}
	}

	/**
	 * 得到所有的复选参数的显示值(数组)
	 * 
	 * @return
	 */
	public String[] selectFieldDisplays() {
		if (this.selected()) {
			Object value = this.condsValues.get(this
					.selectConditionDisplayName());
			return value.toString().split("\\,");
		} else {
			return new String[] {};
		}
	}

	public String getChartid() {
		return chartid;
	}

	public FusionLineChartEx(Element element, Map condsValues, String filename) {
		this.condsValues = condsValues;
		this.fusionchart = element;
		this.filename = filename;
		chartid = fusionchart.attribute("id").getStringValue().trim();
	}

	private void addCategoriesValues(Element categories, List categories_set) {
		int counter=0;
		Map mapp=new HashMap();
		for (Iterator it = categories_set.iterator(); it.hasNext();) {
			Element e = categories.addElement("category");
			String astr=it.next().toString();
			e.addAttribute("label", astr);
			mapp.put(String.valueOf(counter), astr);
			counter++;
		}
		this.setXaxismap(mapp);
	}
	private void addCategoriesValuesSpecial(Element categories, List categories_set) {
		int counter=0;
		Map mapp=new HashMap();
		for (Iterator it = categories_set.iterator(); it.hasNext();) {
			Element e = categories.addElement("category");
			String astr=it.next().toString();
			e.addAttribute("label",astr);
            mapp.put(String.valueOf(counter), astr);
			counter++;
		}
		this.setXaxismap(mapp);
	}
	// private void addDatasetValues(Element dataset, List data, String
	// refer_key,
	// String refer_value, String real_key) {
	// for (int i = 0; i < data.size(); i++) {
	// Map map = (Map) data.get(i);
	// if (map.get(refer_key).toString().equals(refer_value)) {
	// Element e = dataset.addElement("set");
	// e.addAttribute("value", map.get(real_key).toString().trim());
	// }
	// }
	// }

	/**
	 * 每个dataset元素填充子元素


	 * 
	 * 
	 * 
	 * 
	 * @param dataset
	 * @param key1
	 * @param key2
	 */
	private void addDatasetValues(Element dataset, List data, String key,
			String numberSuffix, String value,String format) {
//		System.out.println("=========111111111=========");
		
        String formatt = "0.00";
        if(format!=null&&!"".equals(format))
        	formatt = format;
		for (int i = 0; i < data.size(); i++) {
			Map map = (Map) data.get(i);
			Element e = dataset.addElement("set");
			DecimalFormat df=new DecimalFormat(formatt); 
			e.addAttribute("value", df.format(new Double(Double.parseDouble(map.get(key).toString().trim()))));
			
			if (numberSuffix == null)
				e.addAttribute("toolText", value + " "
						+ df.format(new Double(Double.parseDouble(map.get(key).toString().trim()))));
			else
				e.addAttribute("toolText", value + " "
						+ df.format(new Double(Double.parseDouble(map.get(key).toString().trim()))) + numberSuffix);
		}
	}

	/**
	 * 
	 * @param dataset
	 *            要增加节点的元素
	 * @param data
	 *            数据列表
	 * @param key0
	 *            参照的值字段，纵坐标显示数据的字段
	 * @param key
	 *            分组的字段


	 * 
	 * 
	 * 
	 * @param value
	 *            分组的值


	 * 
	 * 
	 * 
	 */
	private void addDatasetValues(Element dataset, List data, String key0,
			String key, String cond, String numberSuffix, String value,String format) {
//		System.out.println("=========222222222=========");
		String formatt = "0.00";
        if(format!=null&&!"".equals(format))
        	formatt = format;
       
		for (int i = 0; i < data.size(); i++) {
		   Map map = (Map) data.get(i);
		   if(map.get(key)!=null){
			if (map.get(key).toString().trim().equals(cond)
					|| map.get(key).toString().trim().equals(value)) {
				Element e = dataset.addElement("set");
				
				if(map.get(key0)!=null){
				 DecimalFormat df=new DecimalFormat(formatt); 
				 e.addAttribute("value", df.format(new Double(Double.parseDouble(map.get(key0).toString().trim()))));
				 if (numberSuffix == null)
					e.addAttribute("toolText", value + " "
							+ df.format(new Double(Double.parseDouble(map.get(key0).toString().trim()))));
				 else
					e.addAttribute("toolText", value + " "
							+ df.format(new Double(Double.parseDouble(map.get(key0).toString().trim()))) + numberSuffix);
				}
			}
		 }else{
	//		 System.out.println("elseelse");
			 Element e = dataset.addElement("set");
		 }
		}
	}

	/**
	 * 
	 * @param dataset
	 *            要增加节点的元素
	 * @param data
	 *            数据列表
	 * @param key0
	 *            参照的值字段，纵坐标显示数据的字段
	 * @param key
	 *            分组的字段


	 * 
	 * 
	 * 
	 * @param value
	 *            分组的值


	 * 
	 * 
	 * 
	 */
	private void addDatasetValues(String numberSuffix, Element dataset,
			List data, String key0, String key, String value, String displayname,String format) {
//		System.out.println("=========333333333=========");
		String formatt = "0.00";
        if(format!=null&&!"".equals(format))
        	formatt = format;
		for (int i = 0; i < data.size(); i++) {
			Map map = (Map) data.get(i);
			if (map.get(key).toString().trim().equals(value)) {
				Element e = dataset.addElement("set");
				DecimalFormat df=new DecimalFormat(formatt); 
				e.addAttribute("value", df.format(new Double(Double.parseDouble(map.get(key0).toString().trim()))));
				if (numberSuffix == null)
					e.addAttribute("toolText", displayname
							
							+ " " + df.format(new Double(Double.parseDouble(map.get(key0).toString().trim()))));
				else
					e.addAttribute("toolText", displayname
							+ " "
							+ df.format(new Double(Double.parseDouble(map.get(key0).toString().trim())))
							+ numberSuffix);
			}
		}
	}

	private void addDatasetValues(String numberSuffix, Element dataset,
			List data, String key0, String key, String value,String format) {
//		System.out.println("=========444444444=========");
		String formatt = "0.00";
        if(format!=null&&!"".equals(format))
        	formatt = format;
		for (int i = 0; i < data.size(); i++) {
			Map map = (Map) data.get(i);
			if (map.get(key).toString().trim().equals(value)) {
				Element e = dataset.addElement("set");
			   if(map.get(key0)!=null){	
				DecimalFormat df=new DecimalFormat(formatt); 
				e.addAttribute("value", df.format(new Double(Double.parseDouble(map.get(key0).toString().trim()))));
				if (numberSuffix == null)
					e.addAttribute("toolText", value + " "
							+ df.format(new Double(Double.parseDouble(map.get(key0).toString().trim()))));
				else
					e.addAttribute("toolText", value + " "
							+ df.format(new Double(Double.parseDouble(map.get(key0).toString().trim()))) + numberSuffix);
			   }
			}
			
		}
	}

	public String formateFusionChartXml(DataService dataService) {

		Element fchart = (Element) fusionchart.clone();
		Element esql = fchart.element("Sql");
		// 拼状 sql
		Sql oSql = new Sql();
		oSql.setDb(esql.attributeValue("db"));
		oSql.setId(esql.attributeValue("id"));
		oSql.setSqlText(esql.getTextTrim());

		Element chart = fchart.element("chart");

		String numberSuffix = chart.attributeValue("numberSuffix");
		String snumberSuffix = chart.attributeValue("snumberSuffix");
        String format = chart.attributeValue("format");
		Element categ = (Element) chart.element("categories");
		String categ_ref = categ.attributeValue("reference").trim();

		Element datasets = (Element) chart.element("datasets");
		String display_name = datasets.attributeValue("cond_disp");
		List data = dataService.doData(oSql);
      // System.out.println(categ_ref);

		
       if(categ_ref.equals("ORG_NAME")){
    	   List categories_ = new ArrayList();

   		/**
   		 * 横坐标值



   		 * 
   		 * 
   		 * 
   		 */
   		for (int i = 0; i < data.size(); i++) {
   			Map map = (Map) data.get(i);
   			categories_.add(map.get(categ_ref));
   		}
   		
   		categ.remove(categ.attribute("reference"));
   		/**
   		 * 生成所有横坐标数值



   		 * 
   		 * 
   		 * 
   		 */
   		addCategoriesValuesSpecial(categ, categories_); 
       }else{
		List categories_ = new ArrayList();

		
		/**
		 * 横坐标值


		 * 
		 * 
		 * 
		 */
		for (int i = 0; i < data.size(); i++) {
			Map map = (Map) data.get(i);
			categories_.add(map.get(categ_ref));
		}
		
		categ.remove(categ.attribute("reference"));
		/**
		 * 生成所有横坐标数值


		 * 
		 * 
		 * 
		 */
		addCategoriesValues(categ, categories_);
       }
		List dataset_list = datasets.elements();

		// 当可以复选时
		if (this.selected()) {

			if (dataset_list.size() > 1) {// 如果有多个度量值，就显示多个折线；折线幅度随着复选内容变化而变化；忽略datasets
				// reference字段

				for (int i = 0; i < dataset_list.size(); i++) {
					Element e = (Element) dataset_list.get(i);
					String seriesName = e.attributeValue("seriesName").trim();
					String dataset_ref = e.attributeValue("value").trim();
					String renderAs = e.attributeValue("renderAs");
					String datasets_ref_group = datasets
							.attributeValue("ref_group");
					String datasets_reference = datasets
							.attributeValue("reference");
					String datasets_color=datasets.attributeValue("color");
					if (datasets_ref_group != null
							&& !datasets_ref_group.equals("")
							&& datasets_ref_group.equals("true")
							&& !datasets_reference.equals("")) {

						// reference 字段和条件字段不一致，图形要按reference的字段分组显示(n条折线)
						Set ref_group_set = new HashSet();
						Map prdid = new HashMap();
						for (int j = 0; j < data.size(); j++) {
							Map map = (Map) data.get(j);
							ref_group_set.add(map.get(datasets_reference));
							
							if(!prdid.containsKey(datasets_reference)){
							  prdid.put(map.get(datasets_reference), map.get(display_name));
							}
						}
						// 分组取得所有reference字段
					
						for (Iterator iterator = ref_group_set.iterator(); iterator
								.hasNext();) {
							String name = (String) iterator.next().toString();
							String prdname2 = (String)prdid.get(Integer.valueOf(name)).toString()+seriesName;
							if (renderAs != null && !"".equals(renderAs)) {
								String parentYAxis = e.attributeValue("parentYAxis").trim();
								String[] rs = renderAs.split("\\|");
								
								for (int j = 0; j < rs.length; j++) {
									Element edataset = chart
											.addElement("dataset");
									edataset.addAttribute("parentYAxis",
											parentYAxis);
									edataset.addAttribute("seriesName",
											prdname2);
									edataset.addAttribute("renderAs", rs[j]);

									if (parentYAxis.equals("S")) {
										List alist=AlterList(data,categ_ref,  datasets_reference,dataset_ref);
										addDatasetValues(snumberSuffix,
												edataset, alist, dataset_ref,
												datasets_reference, name,
												prdname2,format);
									}
									else{
										List alist=AlterList(data,categ_ref,  datasets_reference,dataset_ref);
										addDatasetValues(numberSuffix,
												edataset, alist, dataset_ref,
												datasets_reference, name,
												prdname2,format);
									}
								}
							}

							else {
								List alist=AlterList(data,categ_ref,  datasets_reference,dataset_ref);
								Element edataset = chart.addElement("dataset");
								edataset.addAttribute("seriesName", name);
								addDatasetValues(numberSuffix, edataset, alist,
										dataset_ref, datasets_reference, name,format
										);
							}
						}

					}

					else {
//						System.out.println("a011111");
//						List alist=AlterListTest(data,categ_ref,dataset_ref,seriesName);
						Element edataset = chart.addElement("dataset");
						edataset.addAttribute("seriesName", seriesName);
						if(datasets_color!=null){
							edataset.addAttribute("color", datasets_color.trim());
						}
						this.addDatasetValues(edataset, data, dataset_ref,
								numberSuffix, seriesName,format);
					}
				}
			} else { // 如果只有一个度量值，datasets reference不能为空
				Element e = (Element) dataset_list.get(0);
				String dataset_value = e.attributeValue("value").trim();
				String datasets_reference = datasets
						.attributeValue("reference");
				String datasets_cond_value = datasets
						.attributeValue("cond_value");
				String datasets_cond_disp = datasets
						.attributeValue("cond_disp");
				String datasets_ref_group = datasets
						.attributeValue("ref_group");
				String datasets_color=datasets.attributeValue("color");
				if (datasets_ref_group != null
						&& !datasets_ref_group.equals("")
						&& datasets_ref_group.equals("true")
						&& !datasets_reference.equals("")) {
					// reference 字段和条件字段不一致，图形要按reference的字段分组显示(n条折线)
					Set ref_group_set = new HashSet();
					for (int i = 0; i < data.size(); i++) {
						Map map = (Map) data.get(i);
						ref_group_set.add(map.get(datasets_reference));
					}
					// 分组取得所有reference字段
					List alist=AlterList(data,categ_ref,  datasets_reference,dataset_value);
					for (Iterator iterator = ref_group_set.iterator(); iterator
							.hasNext();) {
						String name = (String) iterator.next();
						Element edataset = chart.addElement("dataset");
						edataset.addAttribute("seriesName", name);
						if(e.attributeValue("parentYAxis")!=null){
							 String parentYAxis = e.attributeValue("parentYAxis").trim();
							 edataset.addAttribute("parentYAxis", parentYAxis);
						}
						addDatasetValues(numberSuffix, edataset, alist,
								dataset_value, datasets_reference, name,format);
					}

				} else {

					String param = this.condsValues.get(datasets_cond_value)
							.toString();
					String value = this.condsValues.get(datasets_cond_disp)
							.toString();
					String[] params;
					String[] values;
					if (param.indexOf(",") > 0) {
						params = param.split("\\,");
						values = value.split("\\,");
					} else {
						params = new String[] { param };
						values = new String[] { value };
					}
					 List alist=AlterList(data, categ_ref, datasets_reference,dataset_value);

					for (int i = 0; i < params.length; i++) {
                     
						Element edataset = chart.addElement("dataset");
				//		System.out.println("params[i]="+params[i]);
				//		System.out.println("values[i]="+values[i]);
						edataset.addAttribute("seriesName", values[i]);
						if(datasets_color!=null){
							edataset.addAttribute("color", datasets_color.trim());
						}
						if(e.attributeValue("parentYAxis")!=null){
							 String parentYAxis = e.attributeValue("parentYAxis").trim();
							 edataset.addAttribute("parentYAxis", parentYAxis);
						}
						this.addDatasetValues(edataset, alist, dataset_value,
								datasets_reference, params[i], numberSuffix,
								values[i],format);
						
					}
				}

			}
		} else {
			String datasets_reference = datasets.attributeValue("reference");
			// 当datasets reference不为空时，按此字段分组显示



			if (datasets_reference != null && !datasets_reference.equals("")) {

				Element e = (Element) dataset_list.get(0);
				String dataset_value = e.attributeValue("value").trim();
				Set set = new HashSet();
				for (int i = 0; i < data.size(); i++) {
					Map map = (Map) data.get(i);
					set.add(map.get(datasets_reference).toString().trim());
				}
				List alist=AlterList(data,categ_ref,datasets_reference,dataset_value,set);
				for (Iterator it = set.iterator(); it.hasNext();) {
					Element edataset = chart.addElement("dataset");
					String value = it.next().toString();
					edataset.addAttribute("seriesName", value);
					if(e.attributeValue("parentYAxis")!=null){
						 String parentYAxis = e.attributeValue("parentYAxis").trim();
						 edataset.addAttribute("parentYAxis", parentYAxis);
							if (parentYAxis.equals("S")) {
								this.addDatasetValues(snumberSuffix, edataset, alist,
										dataset_value, datasets_reference, value,format);
							}else{
								this.addDatasetValues(numberSuffix, edataset, alist,
										dataset_value, datasets_reference, value,format);
							}
					}
//					this.addDatasetValues(numberSuffix, edataset, data,
//							dataset_value, datasets_reference, value,format);
					this.addDatasetValues(numberSuffix, edataset, alist,
							dataset_value, datasets_reference, value,format);
				}

			} else {
				// 当datasets reference为空，顺序显示全部


				
				for (int i = 0; i < dataset_list.size(); i++) {
					Element e = (Element) dataset_list.get(i);
					String seriesName = e.attributeValue("seriesName").trim();
					String dataset_ref = e.attributeValue("value").trim();
					String datasets_color = datasets.attributeValue("color");
					// String renderAs = e.attributeValue("renderAs");
					// //
					// String[] rs = renderAs.split("|");
					// for (int j = 0; j < rs.length; j++) {
					// Element edataset = chart.addElement("dataset");
					// edataset.addAttribute("seriesName", seriesName);
					// edataset.addAttribute("renderAs", rs[j]);
					// this.addDatasetValues(edataset, data,
					// dataset_ref,numberSuffix,seriesName);
					// }
//					System.out.println("a0222222");
//					List alist=AlterListTest(data,categ_ref,dataset_ref,seriesName);
					Element edataset = chart.addElement("dataset");
					edataset.addAttribute("seriesName", seriesName);
					if(e.attributeValue("showValues")!=null){
						edataset.addAttribute("showValues", e.attributeValue("showValues").trim());
					}
					if(datasets_color!=null){
						edataset.addAttribute("color", datasets_color.trim());
					}
					if(e.attributeValue("parentYAxis")!=null){
						 String parentYAxis = e.attributeValue("parentYAxis").trim();
						 edataset.addAttribute("parentYAxis", parentYAxis);
						if (parentYAxis.equals("S")) {
							 this.addDatasetValues(edataset, data, dataset_ref,
										snumberSuffix, seriesName,format);
						}else{
								this.addDatasetValues(edataset, data, dataset_ref,
										numberSuffix, seriesName,format);
						}
					}else{
					
					this.addDatasetValues(edataset, data, dataset_ref,
							numberSuffix, seriesName,format);
					}
				}
			}
		}

		chart.remove(datasets);
		return chart.asXML();

	}





	public Map getXaxismap() {
		return xaxismap;
	}

	public void setXaxismap(Map xaxismap) {
		this.xaxismap = xaxismap;
	}

}
