package com.idap.intextservice.dataServiceProcess.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.dataServiceProcess.entity.DataServiceProcess;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * ###################################################
 * 
 * @创建日期：2014-5-21 14:46:12
 * @开发人员：huhanjiang
 * @功能描述：数据服务流程管理dao
 * @修改日志： ###################################################
 */

@Repository("dataServiceProcessDao")
public class DataServiceProcessDao extends DefaultBaseDao<DataServiceProcess, Long> {
	public String getNamespace() {
		return DataServiceProcess.class.getName();
	}
}
