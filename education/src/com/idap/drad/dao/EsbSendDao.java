package com.idap.drad.dao;

import org.springframework.stereotype.Repository;
                       
import com.idap.drad.entity.EsbSend;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
* @###################################################
* @功能描述:
* @创建日期:2014-4-21 14:39:43
* @开发人员：bai
* @修改日志：
* @###################################################
*/      
  
@Repository("EsbSendDao")
public class EsbSendDao    extends DefaultBaseDao<EsbSend,String> {

	 
	 
public static final String NAME_SPACE="com.idap.drad.entity.EsbSend";
	  
@Override
	public String getNamespace() {
		return  NAME_SPACE;
		  
	} 
}



