package com.idp.sysmgr.menu.entity;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {

	private static final long serialVersionUID = 4736511926578194636L;

	private Long menuId; // 菜单id
	private Long parMenuId; // 菜单上级id
	private String menuTitle; // 菜单标题
	private String menuName; // 菜单名称
	private String menuDesc; // 菜单描述
	private String state; // 是否叶节点
	private String roleCheckId; // 权限校验标识
	private String menuType; // 菜单类型
	private Long indexNo; // 显示顺序
	private String dealId; // 是否特殊处理
	private String action; //
	private String redirectTo; //
	private String template; //
	private String params;

	private List<Menu> children;
	private String userId;//[附加，用于查询当前登录人所能访问的菜单]
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRedirectTo() {
		return redirectTo;
	}

	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Long getParMenuId() {
		return parMenuId;
	}

	public void setParMenuId(Long parMenuId) {
		this.parMenuId = parMenuId;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRoleCheckId() {
		return roleCheckId;
	}

	public void setRoleCheckId(String roleCheckId) {
		this.roleCheckId = roleCheckId;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public Long getIndexNo() {
		return indexNo;
	}

	public void setIndexNo(Long indexNo) {
		this.indexNo = indexNo;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
