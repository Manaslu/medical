package com.idap.danalysis.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.danalysis.entity.IndexMaintain;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-5-14 16:09:36
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("indexMaintainService")
public class IndexMaintainServiceImpl extends DefaultBaseService<IndexMaintain, String>
		  {
	@Resource(name = "indexMaintainDao")
	public void setBaseDao(IBaseDao<IndexMaintain, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "indexMaintainDao")
	public void setPagerDao(IPagerDao<IndexMaintain> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    

}
