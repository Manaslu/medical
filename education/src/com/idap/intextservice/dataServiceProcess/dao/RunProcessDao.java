package com.idap.intextservice.dataServiceProcess.dao;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.dataServiceProcess.entity.RunProcess;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * #############################
 * 
 * @创建日期：2014-5-22
 * @开发人员：huhanjiang
 * @功能描述：运行流程dao
 * @修改日志： ###################
 */

@Repository("runProcessDao")
public class RunProcessDao extends DefaultBaseDao<RunProcess, Long> {
	public String getNamespace() {
		return RunProcess.class.getName();
	}
}
