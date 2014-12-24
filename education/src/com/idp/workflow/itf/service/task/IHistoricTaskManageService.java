package com.idp.workflow.itf.service.task;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.InvationCallBacker;
import com.idp.workflow.vo.service.task.HistoricTaskVO;
import com.idp.workflow.vo.service.task.TaskInfoVO;

/**
 * 历史任务管理
 * 
 * @author panfei
 * 
 */
public interface IHistoricTaskManageService extends InvationCallBacker {

	/**
	 * 创建一个新的历史任务vo
	 * 
	 * @param taskDefinitionCode
	 *            任务定义编码
	 * @return
	 * @throws WfBusinessException
	 */
	HistoricTaskVO createHistoricTaskVO(String processInstanceId,
			String taskDefinitionCode, String actid) throws WfBusinessException;

	/**
	 * 新增历史任务记录
	 * 
	 * @param processInstanceId
	 * @param taskvo
	 * @throws WfBusinessException
	 */
	HistoricTaskVO saveHistoricTaskInfo(TaskInfoVO taskvo)
			throws WfBusinessException;

	/**
	 * 新增历史任务记录
	 * 
	 * @param hisvo
	 * @throws WfBusinessException
	 */
	void saveHistoricTaskInfo(HistoricTaskVO hisvo) throws WfBusinessException;

	/**
	 * 修改历史任务记录
	 * 
	 * @param hisvo
	 * @throws WfBusinessException
	 */
	void updateHistoricTaskInfo(HistoricTaskVO hisvo)
			throws WfBusinessException;

	/**
	 * 删除历史任务记录
	 * 
	 * @param hisvo
	 * @throws WfBusinessException
	 */
	void deleteHistoricTaskInfo(HistoricTaskVO hisvo)
			throws WfBusinessException;
}
