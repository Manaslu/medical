package com.idap.danalysis.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.danalysis.entity.ParaIndustry;
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
@Service("paraIndustryService")
public class ParaIndustryServiceImpl extends DefaultBaseService<ParaIndustry, String>
		  {
	@Resource(name = "paraIndustryDao")
	public void setBaseDao(IBaseDao<ParaIndustry, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "paraIndustryDao")
	public void setPagerDao(IPagerDao<ParaIndustry> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    

}
