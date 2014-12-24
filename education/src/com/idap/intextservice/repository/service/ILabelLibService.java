package com.idap.intextservice.repository.service;

import java.util.HashMap;
import java.util.List;

import com.idap.intextservice.repository.entity.LabelLib;
import com.idp.pub.constants.Constants;

/**
* @###################################################
* @功能描述：
* @创建日期：2014-5-8 15:34:52
* @开发人员：bai
* @修改日志：
* @###################################################
*/
public interface ILabelLibService   { 
	 
		public List<HashMap<java.lang.String, java.lang.String>> generaterPrimaryKey();
		 
		public String queryLabelId(String name) ;
 
		public List<LabelLib> query() ;
		public List<LabelLib> queryNameList (String name) ;  
			
}


