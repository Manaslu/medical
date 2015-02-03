package com.idp.sysmgr.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idp.pub.service.entity.IUser;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.pub.utils.MD5Utils;
import com.idp.pub.utils.StringUtils;
import com.idp.sysmgr.auth.service.IAuthenticationService;
import com.idp.sysmgr.menu.dao.MenuTreeDao;
import com.idp.sysmgr.menu.entity.MenuTree;
import com.idp.sysmgr.user.dao.IAuthenticationDao;
import com.idp.sysmgr.user.entity.User;
import com.idp.sysmgr.user.service.IUserService;

@Service("authenticationService")
@Transactional
public class AuthenticationService extends DefaultBaseService<User, String>
		implements IAuthenticationService {

	@Resource(name = "userDao")
	private IAuthenticationDao authenticationDao;

	@Resource(name = "userService")
	private IUserService userService;
	
	public IUser authentication(String userName, String password)
			throws Exception {
		User user = this.authenticationDao.authentication(userName);
		
		// 通过用户名查询不存在,则抛出异常
		if (StringUtils.isEmpty(user)) {
			throw new Exception("对不起，用户名不存在");
		}

		// 校验用户密码
//		if (StringUtils.isEmpty(password)|| !MD5Utils.md5(password).equals(user.getPassword())) {
		if (StringUtils.isEmpty(password)||  !password .equals(user.getPassword())) {
			throw new Exception("用户名或密码错误，请重新输入!");
		}

		if ("2".equals(user.getState())) {// 用户状态 为2的情况下
			throw new Exception("该用户已经注销，请与系统管理员联系!");
		}

		List<MenuTree> results = this.menuTreeDao.findUserMenu(user.getId()
				.toString());

		if (results.isEmpty()) {
			throw new Exception("该用户无任何功能权限,请与系统管理员联系!");
		}
		
		//查询该用户是不是超级管理员,是的话把inRoleId设置好
//		List<User> listUser = this.userService.getFlowType(""+user.getId());
//		for(User u:listUser){
//			if("admin".equals(u.getInRoleId())){
//				user.setInRoleId("admin");
//			}
//		}
		
		return user;
	}

	@Resource(name = "menuTreeDao")
	private MenuTreeDao menuTreeDao;

	@Override
	public List<MenuTree> findUserMenu(String userId) {
		List<MenuTree> results = this.menuTreeDao.findUserMenu(userId);
		for (MenuTree menu : results) {
			menu.setChildren(null);
		}
		List<MenuTree> nodeList = new ArrayList<MenuTree>();
		for (MenuTree node1 : results) {
			boolean mark = false;
			for (MenuTree node2 : results) {
				if (node1.getParendId() != null
						&& node1.getParendId().equals(node2.getMenuId())) {
					mark = true;
					if (node2.getChildren() == null)
						node2.setChildren((new ArrayList<MenuTree>()));
					node2.getChildren().add(node1);
					break;
				}
			}
			if (!mark) {
				nodeList.add(node1);
			}
		}
		return nodeList;
	}
	
	@Override
	public List<MenuTree> findRoleMenu(String userId) {
		List<MenuTree> results = this.menuTreeDao.findRoleMenu(userId);
		for (MenuTree menu : results) {
			menu.setChildren(null);
		}
		List<MenuTree> nodeList = new ArrayList<MenuTree>();
		for (MenuTree node1 : results) {
			boolean mark = false;
			for (MenuTree node2 : results) {
				if (node1.getParendId() != null
						&& node1.getParendId().equals(node2.getMenuId())) {
					mark = true;
					if (node2.getChildren() == null)
						node2.setChildren((new ArrayList<MenuTree>()));
					node2.getChildren().add(node1);
					break;
				}
			}
			if (!mark) {
				nodeList.add(node1);
			}
		}
		return nodeList;
	}
	
}
