package com.idp.sysmgr.user.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.sysmgr.user.entity.UserRoleRela;
import com.idp.sysmgr.user.service.IUserRoleRelaService;


/**
 * /** ########################
 * 
 * @创建日期：2014-5-13 15:31:42
 * @开发人员：huhanjiang
 * @功能描述：用户角色权限实现
 * @修改日志： ###################
 */
@Service("userRoleRelaService")
@Transactional
public class UserRoleRelaServiceImpl extends DefaultBaseService<UserRoleRela, Long>   implements   IUserRoleRelaService{
	@Resource(name = "userRoleRelaDao")
	public void setBaseDao(IBaseDao<UserRoleRela, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "userRoleRelaDao")
	public void setPagerDao(IPagerDao<UserRoleRela> pagerDao) {
		super.setPagerDao(pagerDao);
	}
	public UserRoleRela getById (long id){
		return  this.getById(id);
	}
	public Pager<UserRoleRela> query(Pager<UserRoleRela>  pager, Map<String, Object> params) {
	
		 
		return	this.findByPager(pager, params);
		    
	}
}
