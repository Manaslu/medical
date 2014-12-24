package com.idap.dataprocess.rule.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.idap.dataprocess.rule.entity.SubScriptPara;
import com.idp.pub.dao.impl.DefaultBaseDao;


/**
* @###################################################
* @功能描述：
* @创建日期：2014-7-7 15:39:20
* @开发人员：李广彬
* @修改日志：
* @###################################################
*/
@Repository("subScriptParaDao")
public class SubScriptParaDao  extends DefaultBaseDao<SubScriptPara,String>{
	private static final Log logger = LogFactory.getLog(SubScriptParaDao.class);
	public static final String NAME_SPACE=SubScriptPara.class.getName();
	
	@Override
	public String getNamespace() {
		return NAME_SPACE;
	}
	
}


