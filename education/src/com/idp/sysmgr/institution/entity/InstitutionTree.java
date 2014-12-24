package com.idp.sysmgr.institution.entity;

import java.io.Serializable;
import java.util.List;

import com.idp.pub.utils.JsonUtils;

/**
 * 
 * @创建日期：2014-5-14
 * @开发人员：huhanjiang
 * @功能描述：机构树
 * 
 */

public class InstitutionTree implements Serializable {

	private static final long serialVersionUID = 5395369361323315776L;

	private String id;// 机构Id
	private String pId;// 父机构Id
	private String name;// 机构名称
	private String orgCd; // 机构编码
	private Boolean isParent; // 是否有下级节点
	private Long provCode; // 省市代码
	private List<InstitutionTree> children;// 子机构菜单

	public Long getProvCode() {
		return provCode;
	}

	public void setProvCode(Long provCode) {
		this.provCode = provCode;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public String getOrgCd() {
		return orgCd;
	}

	public void setOrgCd(String orgCd) {
		this.orgCd = orgCd;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<InstitutionTree> getChildren() {
		return children;
	}

	public void setChildren(List<InstitutionTree> children) {
		this.children = children;
	}

	public String toString() {
		return JsonUtils.toJson(this);
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof InstitutionTree)) {
			return false;
		}

		InstitutionTree temp = (InstitutionTree) obj;

		return this.orgCd.equals(temp.getOrgCd());
	}

	public int hashCode() {
		return this.orgCd.hashCode();
	}

}
