package com.idp.sysmgr.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.pub.utils.MD5Utils;
import com.idp.sysmgr.exception.BusinessException;
import com.idp.sysmgr.user.dao.UserDao;
import com.idp.sysmgr.user.entity.User;
import com.idp.sysmgr.user.service.IUserService;

@Transactional
@Service("userService")
public class UserServiceImpl extends DefaultBaseService<User, Long> implements
		IUserService {
	@Resource(name = "userDao")
	public void setBaseDao(IBaseDao<User, Long> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Resource(name = "userDao")
	public void setPagerDao(IPagerDao<User> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	@Resource(name = "userDao")
	private UserDao userDao;

	// 查询下一个代办人（工作流那用到）
	@Override
	public List<User> getByIds(User id) {
		Assert.notNull(userDao, "必须设置基础接口IBaseDao的实现类");
		return this.userDao.gets(id);
	}

	public Pager<User> query(Pager<User> pager, Map<String, Object> params) {
		return this.findByPager(pager, params);

	}

	public User updatePwd(String userid, String pwd, String npwd)
			throws BusinessException {
		User user = this.getById(Long.valueOf(userid));
		if (!user.getPassword().equals(MD5Utils.md5(pwd))) {
			throw new BusinessException("用户密码输入错误，请重新输入!");
		}
		user.setPassword(MD5Utils.md5(npwd));
		this.update(user);
		return user;
	}

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	// 批量插入用户分配的角色
	@Override
	public void batchUpdate(List<Object[]> batchArgs) {
		String sql = "insert into t02_user_role_rela(user_id,role_id,is_main) values(?,?,?)";
		this.jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	@Override
	public List<User> getTechContactList(Map<String, Object> map) {
		return userDao.getTechConTactList(map);
	}

	@Override
	public List<User> getFlowType(String userId) {
		return this.userDao.getFlowType(userId);
	}
	
	@Override
	public List<User> findList(Map<String, Object> params) {
		return this.userDao.find(params);
	}

}
