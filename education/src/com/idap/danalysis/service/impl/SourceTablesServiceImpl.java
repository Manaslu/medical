package com.idap.danalysis.service.impl;

 

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idap.danalysis.entity.SourceTables;
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
@Service("sourceTablesService")
public class SourceTablesServiceImpl extends DefaultBaseService<SourceTables, String>
		  {
	@Resource(name = "sourceTablesDao")
	public void setBaseDao(IBaseDao<SourceTables, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "sourceTablesDao")
	public void setPagerDao(IPagerDao<SourceTables> pagerDao) {
		super.setPagerDao(pagerDao);
	}
    

}
