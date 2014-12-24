package com.idp.sysmgr.announcement.dao;


import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.DefaultBaseDao;
import com.idp.sysmgr.announcement.entity.Announcement;

/**
 * #############################
 * 
 * @创建日期：2014-5-13 09:25:42
 * @开发人员：hu
 * @功能描述：公告管理dao
 * @修改日志： #############################
 */
 
@Repository("announcementDao")
public class AnnouncementDao extends DefaultBaseDao<Announcement, Long> {
	public String getNamespace() {
		return Announcement.class.getName();
	}
	//批量更新最后有效日期小于当前日期的公告状态，改为下线
	public Announcement updateState(Announcement entity) {
		updates("updateState", entity);
		return entity;
	}

	public Announcement updates(String key, Announcement entity) {
		this.getSqlSession().update(this.sqlKey(key), entity);
		return entity;
	}
}
