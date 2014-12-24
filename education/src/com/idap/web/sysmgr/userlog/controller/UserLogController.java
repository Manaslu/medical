package com.idap.web.sysmgr.userlog.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.journal.entity.UserLog;

/**
 * /** ########################
 * 
 * @创建日期：2014-5-12
 * @开发人员：huhanjiang
 * @功能描述：用户操作日志
 * @修改日志： ###################
 */

@Controller
@RequestMapping(value = "/journal")
public class UserLogController extends BaseController<UserLog, Long> {
	@Resource(name = "userLogService")
	public void setBaseService(IBaseService<UserLog, Long> baseService) {
		super.setBaseService(baseService);
	}
}
