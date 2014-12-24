package com.idap.danalysis.service.impl;

 
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.danalysis.entity.ParaHistory;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-6-7 16:09:36
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("paraHistoryService")
public class ParaHistoryServiceImpl extends DefaultBaseService<ParaHistory, String>
		  {
	@Resource(name = "paraHistoryDao")
	public void setBaseDao(IBaseDao<ParaHistory, String> baseDao) {
		super.setBaseDao(baseDao);
	}
	
 
	@Resource(name = "paraHistoryDao")
	public void setPagerDao(IPagerDao<ParaHistory> pagerDao) {
		super.setPagerDao(pagerDao);
	}
 
	  
 
}
