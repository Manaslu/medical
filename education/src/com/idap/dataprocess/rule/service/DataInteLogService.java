package com.idap.dataprocess.rule.service;

import java.util.List;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.rule.entity.DataInteLog;
import com.idp.pub.exception.BusinessException;

public interface DataInteLogService {

	/**
	 * 根据源数据集的Id,获取目标数据集
	 * 
	 * @param osetId
	 *            源数据集的Id
	 * @param processType
	 *            加工类型("auto","manual")
	 * @return
	 * @throws BusinessException
	 */
	public List<DataSet> findGoalSet(String osetId, String processType)
			throws BusinessException;

	//查看报告
	public List<DataInteLog> findReport(DataInteLog entity)
			throws BusinessException;
	
	/**
	 * 根据目标数据集的Id,回溯源数据集
	 * 
	 * @param gsetId
	 *            目标数据集的Id,
	 * @param processType
	 *            加工类型("auto","manual")
	 * @return
	 * @throws BusinessException
	 */
	public List<DataInteLog> findByResult(String gsetId, String processType)
			throws BusinessException;

}
