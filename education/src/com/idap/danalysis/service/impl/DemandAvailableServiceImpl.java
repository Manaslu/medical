package com.idap.danalysis.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.danalysis.entity.DemandAvailable;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-6-6 16:09:36
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("demandAvailableService")
public class DemandAvailableServiceImpl extends DefaultBaseService<DemandAvailable, String>
		  {
	@Resource(name = "demandAvailableDao")
	public void setBaseDao(IBaseDao<DemandAvailable, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "demandAvailableDao")
	public void setPagerDao(IPagerDao<DemandAvailable> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    

}
