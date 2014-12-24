package com.idp.sysmgr.role.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.sysmgr.role.dao.RoleDao;
import com.idp.sysmgr.role.entity.Role;
import com.idp.sysmgr.role.service.IRoleService;

/**
 * ###################################################
 * 
 * @创建日期：2014-4-21 16:36:42
 * @开发人员：hu
 * @功能描述：角色管理实现
 * @修改日志： ###################################################
 */

@Service("roleService")
@Transactional
public class RoleServiceImpl extends DefaultBaseService<Role, Long> implements
		IRoleService {

	@Resource(name = "roleDao")
	public void setBaseDao(IBaseDao<Role, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "roleDao")
	public void setPagerDao(IPagerDao<Role> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	@Resource(name = "roleDao")
	private RoleDao roleDao;
	
	public Pager<Role> query(Pager<Role>  pager, Map<String, Object> params) {
		return	this.findByPager(pager, params);
		    
	}
	
	//查询用户配置的角色
	@Override
	public List<Role> getRole(Role id) {
		Assert.notNull(roleDao, "必须设置基础接口IBaseDao的实现类");
		return this.roleDao.gets(id);
	}
}
