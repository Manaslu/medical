package com.idap.intextservice.repository.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.repository.entity.KnowledgebaseLabel;
import com.idp.pub.dao.impl.DefaultBaseDao;


 
/**
* @###################################################
* @功能描述： 关联标签管理dao
* @创建日期：2014-5-8 15:34:52
* @开发人员：bai
* @修改日志：
* @###################################################
*/
@Repository("KnowledgebaseLabelDao")
public class KnowledgebaseLabelDao  extends DefaultBaseDao<KnowledgebaseLabel,String>{ 
	public static final String NAME_SPACE="com.idap.intextservice.repository.entity.KnowledgebaseLabel"; 
	@Override
	public String getNamespace() {
		return NAME_SPACE;
	} 
}

                    
             

