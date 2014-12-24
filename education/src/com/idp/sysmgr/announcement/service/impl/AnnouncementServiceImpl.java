package com.idp.sysmgr.announcement.service.impl;



import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.sysmgr.announcement.dao.AnnouncementDao;
import com.idp.sysmgr.announcement.entity.Announcement;
import com.idp.sysmgr.announcement.service.IAnnouncementService;

/**
 * ###################################################
 * 
 * @创建日期：2014-5-13 09:36:42
 * @开发人员：huhanjiang
 * @功能描述：公告管理实现
 * @修改日志： ###################################################
 */
@Service("announcementService")
@Transactional
public class AnnouncementServiceImpl extends DefaultBaseService<Announcement, Long> implements
		IAnnouncementService {

	@Resource(name = "announcementDao")
	public void setBaseDao(IBaseDao<Announcement, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "announcementDao")
	public void setPagerDao(IPagerDao<Announcement> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	
	@Resource(name = "announcementDao")
	private AnnouncementDao announcementDao;
	
	public Pager<Announcement> query(Pager<Announcement>  pager, Map<String, Object> params) {
		return	this.findByPager(pager, params);
		    
	}
	//批量更新最后有效日期小于当前日期的公告状态，改为下线
	public Announcement updateState(Announcement entity) {
		Assert.notNull(announcementDao, "必须设置基础接口IBaseDao的实现类");
		return this.announcementDao.updateState(entity);
	}
}
