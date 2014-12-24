package com.idp.sysmgr.announcement.service;

import com.idp.sysmgr.announcement.entity.Announcement;



/**
 * ###################################################
 * 
 * @创建日期：2014-5-13 09:36:42
 * @开发人员：huhanjiang
 * @功能描述：公告管理接口
 * @修改日志： ###################################################
 */

public interface IAnnouncementService {
	
	//批量更新最后有效日期小于当前日期的公告状态，改为下线
	Announcement updateState(Announcement entity);
}
