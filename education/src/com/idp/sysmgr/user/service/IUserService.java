package com.idp.sysmgr.user.service;


import java.util.List;
import java.util.Map;

import com.idp.sysmgr.exception.BusinessException;
import com.idp.sysmgr.user.entity.User;

public interface IUserService {

	public User updatePwd(String userid, String pwd, String npwd)
			throws BusinessException;
	//查询下一个代办人（工作流那用到）
	List<User> getByIds(User entity);
	
	//批量插入用户分配的角色
	public void batchUpdate(List<Object[]> batchArgs);
	//查询技术联系人
	public List<User> getTechContactList(Map<String, Object> map);
	//查询申请人的省份和内置角色id
	public List<User> getFlowType(String userId);
	public List<User> findList(Map<String, Object> params);
}


