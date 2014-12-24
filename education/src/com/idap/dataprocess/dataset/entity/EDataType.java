package com.idap.dataprocess.dataset.entity;

public enum EDataType {
	VARCHAR2("VARCHAR2", "字符串", 0), // 字符串类型

	NUMBER("NUMBER", "数字", 1), // 数字类型

	DATE("DATE", "日期", 2), // 日期类型

	TIMESTAMP("TIMESTAMP", "日期时间", 3);

	private String type;

	private String desc;

	private int index;// 序列號

	private EDataType(String type, String desc, int index) {
		this.type = type;
		this.desc = desc;
		this.index = index;
	}

	public static EDataType transfer(String type) {
		EDataType result = null;
		for (EDataType t : EDataType.values()) {
			if (t.getType().equalsIgnoreCase(type)) {
				result = t;
				break;
			}
		}
		return result;
	}

	public String getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
