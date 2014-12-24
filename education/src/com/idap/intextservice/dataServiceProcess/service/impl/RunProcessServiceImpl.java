package com.idap.intextservice.dataServiceProcess.service.impl;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.intextservice.dataServiceProcess.entity.RunProcess;
import com.idap.intextservice.dataServiceProcess.service.IRunProcessService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * ###################################################
 * 
 * @创建日期：2014-5-22
 * @开发人员：huhanjiang
 * @功能描述：运行流程实现类
 * @修改日志： #######################################
 */

@Service("runProcessService")
@Transactional
public class RunProcessServiceImpl extends DefaultBaseService<RunProcess, Long> implements
		IRunProcessService {

	@Resource(name = "runProcessDao")
	public void setBaseDao(IBaseDao<RunProcess, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "runProcessDao")
	public void setPagerDao(IPagerDao<RunProcess> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	
	public Pager<RunProcess> query(Pager<RunProcess>  pager, Map<String, Object> params) {
		return	this.findByPager(pager, params);
		    
	}
	
}
