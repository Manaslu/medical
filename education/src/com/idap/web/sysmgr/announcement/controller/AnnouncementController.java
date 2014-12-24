package com.idap.web.sysmgr.announcement.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idp.pub.entity.Pager;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.announcement.entity.Announcement;
import com.idp.sysmgr.announcement.service.IAnnouncementService;

/**
 * /** ########################
 * 
 * @创建日期：2014-5-13 09:21:42
 * @开发人员：huhanjiang
 * @功能描述：公告管理控制器
 * @修改日志： ################
 */

@Controller 
@RequestMapping(value = "/announcement")
public class AnnouncementController extends BaseController<Announcement, Long>{

	@Resource(name = "announcementService")
	public void setBaseService(IBaseService<Announcement, Long> baseService) {
		super.setBaseService(baseService);
	}
	
	@Resource(name = "announcementService")
	private IAnnouncementService announcementService;

	
	@RequestMapping(params = "paging=true", method = RequestMethod.GET)
	@ResponseBody
	protected Pager<Announcement> findByPager(Pager<Announcement> pager,
			@RequestParam("params") String values) {
		pager = this.getBaseService().findByPager(pager,
				JsonUtils.toMap(values));
		Announcement announcement = new Announcement();
		//1：代表待审核，2：代表发布，3：代表驳回，4：代表下线 ,5代表草稿
		//批量更新最后有效日期小于当前日期的公告状态，改为下线
		announcement.setAnnoState("4");
		this.announcementService.updateState(announcement);
		return pager;
	}
	
}
