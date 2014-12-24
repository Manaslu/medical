package com.idap.danalysis.service.impl;

 
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.danalysis.entity.SyncAuditor;
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
@Service("syncAuditorService")
public class SyncAuditorServiceImpl extends DefaultBaseService<SyncAuditor, String>
		  {
	@Resource(name = "syncAuditorDao")
	public void setBaseDao(IBaseDao<SyncAuditor, String> baseDao) {
		super.setBaseDao(baseDao);
	}
	
 
	@Resource(name = "syncAuditorDao")
	public void setPagerDao(IPagerDao<SyncAuditor> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    

}
