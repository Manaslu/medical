package com.idap.dataprocess.rule.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.idap.dataprocess.rule.entity.SubRuleScript;
import com.idp.pub.dao.impl.DefaultBaseDao;


/**
* @###################################################
* @功能描述：
* @创建日期：2014-7-7 15:39:21
* @开发人员：李广彬
* @修改日志：
* @###################################################
*/
@Repository("subRuleScriptDao")
public class SubRuleScriptDao  extends DefaultBaseDao<SubRuleScript,String>{
	private static final Log logger = LogFactory.getLog(SubRuleScriptDao.class);
	public static final String NAME_SPACE=SubRuleScript.class.getName();
	
	@Override
	public String getNamespace() {
		return NAME_SPACE;
	}
	
}


