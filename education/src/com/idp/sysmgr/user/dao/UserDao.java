package com.idp.sysmgr.user.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.idp.pub.constants.Constants;
import com.idp.pub.dao.impl.DefaultBaseDao;
import com.idp.sysmgr.user.entity.User;

@Repository("userDao")
public class UserDao extends DefaultBaseDao<User, Long> implements
		IAuthenticationDao {

	@Override
	public String getNamespace() {
		return User.class.getName();
	}

	public List<User> find(String key, Map<String, Object> params) {
		return this.getSqlSession().selectList(this.sqlKey(key), params);
	}
	//查询下一个代办人（工作流那用到）
	public List<User> gets(User id) {
		return get("getByIds", id);

	}

	public List<User> get(String key, User id) {
		return this.getSqlSession().selectList(sqlKey(key), id);

	}
	
	/**
	 * 
	 */
	public User authentication(String userName) {
		Map<String, Object> params = Constants.MAP();
		params.put("logName", userName);
		return this.unique(params);
	}
	//查询技术联系人
	public List<User> getTechConTactList(Map<String, Object> map) {
		return this.getSqlSession().selectList(this.getNamespace().concat(".").concat("queryTechContact"), map);
	}

	public List<User> getFlowType(String userId) {
		return this.getSqlSession().selectList("getFlowType", userId);
	}

}
