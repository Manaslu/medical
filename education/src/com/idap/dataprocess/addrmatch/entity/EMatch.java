package com.idap.dataprocess.addrmatch.entity;

import java.io.Serializable;

public enum EMatch implements Serializable {
	MATCHED_POST(0, "MATCHED_POST", "邮政编码"), // 邮政编码

	MATCHED_POST_PRECISION(1, "MATCHED_POST_PRECISION", "邮政编码精度"), // 邮政编码精度

	MATCHED_DIST_CD(2, "MATCHED_DIST_CD", "行政区划代码"), // 行政区划代码

	MATCHED_DIST_NAME(3, "MATCHED_DIST_NAME", "行政区划名称"), // 行政区划名称

	MATCHED_ADDR(4, "MATCHED_ADDR", "匹配后地址"), // 匹配后地址

	MATCHED_ADDR_ID(5, "MATCHED_ADDR_ID", " 地址ID"), // 地址ID

	MATCHED_DEGRE(6, "MATCHED_DEGRE", "置信度"), // 置信度

	MATCHED_DEPTH(7, "MATCHED_DEPTH", "匹配深度"), // 匹配深度

	MATCHED_SCORE(8, "MATCHED_SCORE", "匹配分数"), // 有效分数

	MATCHED_SKIP_INFO(9, "MATCHED_SKIP_INFO", "跳级地址信息"), // 跳级地址信息

	MATCHED_APPEND_FLAG(10, "MATCHED_APPEND_FLAG", "追加标记"), // 追加标记

	UNMATCHED_ADDR(11, "UNMATCHED_ADDR", "未匹配地址信息"), // 未匹配地址信息

	MATCHED_ORG_NAME(12, "MATCHED_ORG_NAME", "组织机构名称"), // 组织机构名称

	MATCHED_POST_IS_UPGRADE(13, "MATCHED_POST_IS_UPGRADE", "邮编是否更新"), // 邮编是否更新

	MATCHED_DELIVERED(14, "MATCHED_DELIVERED", "可投递性");// 可投递性

	private int index;// txt文件中所有列的序号,从0开始

	private String columnName;// 列名

	private String columnDesc;// 列的描述

	// 字段需要持久化到数据库的字段
	private static EMatch[] PERSISTENCE_COLUMNS = new EMatch[] { MATCHED_POST,
			MATCHED_POST_PRECISION, MATCHED_DIST_CD, MATCHED_DIST_NAME,
			MATCHED_ADDR, MATCHED_ADDR_ID, MATCHED_DEGRE, MATCHED_DEPTH,
			MATCHED_SCORE, MATCHED_DELIVERED };

	public int getIndex() {
		return index;
	}

	public boolean isPersistence() {
		for (EMatch e : PERSISTENCE_COLUMNS) {
			if (e.equals(this)) {
				return true;
			}
		}
		return false;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public static EMatch getEMatch(int index) {
		EMatch[] ches = EMatch.values();
		EMatch result = null;
		for (EMatch c : ches) {
			if (c.getIndex() == index) {
				result = c;
				break;
			}
		}
		return result;
	}

	public static EMatch transfer(String columnName) {
		EMatch match = null;
		for (EMatch e : EMatch.values()) {
			if (columnName.toUpperCase().equals(e.getColumnName())) {
				match = e;
				break;
			}
		}
		return match;
	}

	private EMatch(int index, String columnName, String columnDesc) {
		this.index = index;
		this.columnName = columnName;
		this.columnDesc = columnDesc;
	}

	public static void main(String[] args) {
		Integer.valueOf("1");
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnDesc() {
		return columnDesc;
	}

	public void setColumnDesc(String columnDesc) {
		this.columnDesc = columnDesc;
	}

}
