package com.idp.sysmgr.menu.service.impl;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.sysmgr.menu.entity.Menu;

/**
 * ###################################################
 * 
 * @创建日期：2014-5-04 16:36:42
 * @开发人员：hu
 * @功能描述：菜单管理实现
 * @修改日志： ###################################################
 */

@Service("menuService")
@Transactional
public class MenuServiceImpl extends DefaultBaseService<Menu, Long> {

	@Resource(name = "menuDao")
	public void setBaseDao(IBaseDao<Menu, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "menuDao")
	public void setPagerDao(IPagerDao<Menu> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	
	public Pager<Menu> query(Pager<Menu>  pager, Map<String, Object> params) {
		return	this.findByPager(pager, params);
		    
	}
	
}
