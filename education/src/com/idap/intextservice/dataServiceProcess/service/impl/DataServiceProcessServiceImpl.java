package com.idap.intextservice.dataServiceProcess.service.impl;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.intextservice.dataServiceProcess.entity.DataServiceProcess;
import com.idap.intextservice.dataServiceProcess.service.IDataServiceProcessService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * ###################################################
 * 
 * @创建日期：2014-5-21 14:56:42
 * @开发人员：huhanjiang
 * @功能描述：数据服务流程管理实现类
 * @修改日志： #######################################
 */

@Service("dataServiceProcessService")
@Transactional
public class DataServiceProcessServiceImpl extends DefaultBaseService<DataServiceProcess, Long> implements
		IDataServiceProcessService {

	@Resource(name = "dataServiceProcessDao")
	public void setBaseDao(IBaseDao<DataServiceProcess, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "dataServiceProcessDao")
	public void setPagerDao(IPagerDao<DataServiceProcess> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	
	public Pager<DataServiceProcess> query(Pager<DataServiceProcess>  pager, Map<String, Object> params) {
		return	this.findByPager(pager, params);
		    
	}
	
}
