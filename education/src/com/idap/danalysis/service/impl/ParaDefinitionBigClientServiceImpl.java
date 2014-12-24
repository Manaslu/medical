package com.idap.danalysis.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.danalysis.entity.ParaDefinitionBigClient;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-6-13 16:09:36
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("paraDefinitionBigClientService")
public class ParaDefinitionBigClientServiceImpl extends DefaultBaseService<ParaDefinitionBigClient, String>
		  {
	@Resource(name = "paraDefinitionBigClientDao")
	public void setBaseDao(IBaseDao<ParaDefinitionBigClient, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "paraDefinitionBigClientDao")
	public void setPagerDao(IPagerDao<ParaDefinitionBigClient> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    

}
