package com.idap.danalysis.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.danalysis.entity.ParaProvince;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-6-14 16:09:36
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("paraProvinceService")
public class ParaProvinceServiceImpl extends DefaultBaseService<ParaProvince, String>
		  {
	@Resource(name = "paraProvinceDao")
	public void setBaseDao(IBaseDao<ParaProvince, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "paraProvinceDao")
	public void setPagerDao(IPagerDao<ParaProvince> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    

}
