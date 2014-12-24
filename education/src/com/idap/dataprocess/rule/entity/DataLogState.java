package com.idap.dataprocess.rule.entity;

public enum DataLogState {
	LOG_INIT("init"), // 初始化

	LOG_RUNNING("running"), // 运行中或匹配中

	LOG_FINISHED("finished"), // 执行完成或匹配完成

	LOG_FAILURE("failure");// 执行失败

	private String state;

	private DataLogState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public static void main(String[] args) {
		System.out.println(LOG_INIT.getState());
	}
}
