
package com.idap.processmgr.special.service.impl;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.processmgr.special.dao.ProcessesDao;
import com.idap.processmgr.special.entity.Processes;
import com.idap.processmgr.special.service.ProcessesService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;
@Transactional
@Service("processesService")
public class ProcessesServiceImpl extends DefaultBaseService<Processes, String> implements ProcessesService{

	@Resource(name = "processesDao")
	public void setBaseDao(IBaseDao<Processes, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "processesDao")
	public void setPagerDao(IPagerDao<Processes> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	@Resource(name = "processesDao")
	public ProcessesDao processesDao;
	
	@Override
	public Pager<Processes> findByPager(Pager<Processes> pager, Map<String, Object> params) {
		return super.getPagerDao().findByPager(pager, params);
	}

	@Override
	public Integer findAllCount(String id) {
		return processesDao.findAllCount(id);
	}

	@Override
	public Integer findCountOfConfirm(String id) {
		return processesDao.findCountOfConfirm(id);
	}
}