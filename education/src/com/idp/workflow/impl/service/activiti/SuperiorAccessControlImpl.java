package com.idp.workflow.impl.service.activiti;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.identity.IAccessController;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.vo.identity.UserVO;

/**
 * 只有上级才能驳回已完成的任务
 * 
 * @author panfei
 * 
 */
public class SuperiorAccessControlImpl implements IAccessController {

	@Override
	public String canPass(UserVO currentPersion, UserVO beforePersion,
			UserVO afterPersion, Object params) throws WfBusinessException {
		if (currentPersion == null
				|| StringUtil.isEmpty(currentPersion.getUserID())) {
			throw new WfBusinessException("当前操作员不能为空！");
		}

		if (afterPersion == null
				|| StringUtil.isEmpty(afterPersion.getUserID())) {
			throw new WfBusinessException("未找到待办人员，待办人员不能为空或流程已经结束，不能驳回任务！");
		}

		if (!currentPersion.getUserID().equals(afterPersion.getUserID())) {
			throw new WfBusinessException("当前操作人员非下一个待办上级，不能驳回任务！");
		}

		if (beforePersion == null
				|| StringUtil.isEmpty(beforePersion.getUserID())) {
			throw new WfBusinessException(
					"在上级驳回本级任务模式下，工作流程已经结束，无待办任务上级，不能驳回任务！");
		}

		return beforePersion.getUserID();
	}
}
