package com.idp.sysmgr.menu.entity;

import java.io.Serializable;
import java.util.List;

import com.idp.pub.utils.JsonUtils;

public class MenuTree implements Serializable {

	private static final long serialVersionUID = 5395369361323315775L;

	private String menuId;// 菜单Id

	private String parendId;// 父菜单Id

	private String menuDesc;// 菜单描述

	private String name;// 菜单名称

	private String action;//

	private String template;// 模板链接

	private String redirectTo;// 转向

	private String params;

	private String indexNo;

	private List<MenuTree> children;// 子菜单

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}

	public String getRedirectTo() {
		return redirectTo;
	}

	public void setRedirectTo(String redirectTo) {
		this.redirectTo = redirectTo;
	}

	public String getParendId() {
		return parendId;
	}

	public void setParendId(String parendId) {
		this.parendId = parendId;
	}

	public String toString() {
		return JsonUtils.toJson(this);
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getIndexNo() {
		return indexNo;
	}

	public void setIndexNo(String indexNo) {
		this.indexNo = indexNo;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}
}
