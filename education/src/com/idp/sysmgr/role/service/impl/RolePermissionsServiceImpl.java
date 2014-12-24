package com.idp.sysmgr.role.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.sysmgr.role.entity.RolePermissions;
import com.idp.sysmgr.role.service.RolePermissionsService;

/**
 * /** ########################
 * 
 * @创建日期：2014-5-12 15:31:42
 * @开发人员：huhanjiang
 * @功能描述：角色权限实现
 * @修改日志： ###################
 */
@Service("rolePermissionsService")
@Transactional
public class RolePermissionsServiceImpl extends
		DefaultBaseService<RolePermissions, Long> implements
		RolePermissionsService {

	@Resource(name = "rolePermissionsDao")
	public void setBaseDao(IBaseDao<RolePermissions, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "rolePermissionsDao")
	public void setPagerDao(IPagerDao<RolePermissions> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	public Pager<RolePermissions> query(Pager<RolePermissions> pager,
			Map<String, Object> params) {
		return this.findByPager(pager, params);

	}

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	//批量插入角色分配的菜单
	@Override
	public void batchUpdate(List<Object[]> batchArgs) {
		String sql = "insert into t02_function_role(role_id,menu_id,state) values(?,?,?)";
		this.jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	@Override
	public void deleteMenu(List<Object[]> batchArgs) {
		String sql = "delete from t02_function_role where role_id=? and menu_id = ?";
		this.jdbcTemplate.batchUpdate(sql, batchArgs);
	}
}
