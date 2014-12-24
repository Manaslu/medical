package com.idp.sysmgr.user.dao;

import org.springframework.stereotype.Repository;

import com.idp.sysmgr.user.entity.User;


@Repository("authenticationDao")
public interface IAuthenticationDao {
	public User authentication(String userName);
}
