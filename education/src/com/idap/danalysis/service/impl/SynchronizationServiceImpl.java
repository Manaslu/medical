package com.idap.danalysis.service.impl;

 
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.danalysis.entity.Synchronization;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
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
@Service("synchronizationService")
public class SynchronizationServiceImpl extends DefaultBaseService<Synchronization, String>
		  {
	@Resource(name = "synchronizationDao")
	public void setBaseDao(IBaseDao<Synchronization, String> baseDao) {
		super.setBaseDao(baseDao);
	}
	
 
	@Resource(name = "synchronizationDao")
	public void setPagerDao(IPagerDao<Synchronization> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    

}
