package com.idap.danalysis.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.danalysis.entity.SourceIndexs;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @创建日期：2014-6-12 16:09:36
 * @开发人员：wangwei
 * @功能描述：
 * @修改日志：
 * @###################################################
 */

@Transactional
@Service("sourceIndexsService")
public class SourceIndexsServiceImpl extends DefaultBaseService<SourceIndexs, String>
		  {
	@Resource(name = "sourceIndexsDao")
	public void setBaseDao(IBaseDao<SourceIndexs, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "sourceIndexsDao")
	public void setPagerDao(IPagerDao<SourceIndexs> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    

}
