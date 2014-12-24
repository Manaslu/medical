
package com.idap.processmgr.special.service.impl;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.processmgr.special.entity.Achievement;
import com.idap.processmgr.special.service.AchievementService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;
@Transactional
@Service("achievementService")
public class AchievementServiceImpl extends DefaultBaseService<Achievement, String> implements AchievementService{

	@Resource(name = "achievementDao")
	public void setBaseDao(IBaseDao<Achievement, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "achievementDao")
	public void setPagerDao(IPagerDao<Achievement> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	
	@Override
	public Pager<Achievement> findByPager(Pager<Achievement> pager, Map<String, Object> params) {
		return super.getPagerDao().findByPager(pager, params);
	}
}