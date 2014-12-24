package com.idap.web.init;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.idap.dataprocess.rule.entity.RuleScript;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.sysmgr.institution.entity.Institution;
import com.idp.sysmgr.menu.entity.MenuTree;

public class Initialize {
	private Log logger = LogFactory.getLog(getClass());

	private IBaseService<MenuTree, Long> menuTreeService;

	private IBaseService<Institution, Long> institutionService;

	private IBaseService<RuleScript, Long> ruleScriptService;

	public void init() {
		logger.info("开始加载系统菜单..... ");
		// logger.info(JsonUtils.toJson(this.menuTreeService.findList(Constants
		// .MAP())));
		logger.info("系统菜单完成加载..... ");
		logger.info("");
		logger.info(JsonUtils.toJson(this.ruleScriptService.findList(Constants
				.MAP())));
		logger.info("");
	}

	public void destroy() {

	}

	public IBaseService<MenuTree, Long> getMenuTreeService() {
		return menuTreeService;
	}

	public void setMenuTreeService(IBaseService<MenuTree, Long> menuTreeService) {
		this.menuTreeService = menuTreeService;
	}

	public IBaseService<Institution, Long> getInstitutionService() {
		return institutionService;
	}

	public void setInstitutionService(
			IBaseService<Institution, Long> institutionService) {
		this.institutionService = institutionService;
	}

	public IBaseService<RuleScript, Long> getRuleScriptService() {
		return ruleScriptService;
	}

	public void setRuleScriptService(
			IBaseService<RuleScript, Long> ruleScriptService) {
		this.ruleScriptService = ruleScriptService;
	}
}
