package com.idap.processmgr.special.service;

public interface ProcessesService {
	/**
	 * 获取所有的成果数量
	 * @param id
	 * @return
	 */
	public Integer findAllCount(String id);
	/**
	 * 获取所有的已确认的成果数量
	 * @param id
	 * @return
	 */
	public Integer findCountOfConfirm(String id);
}
