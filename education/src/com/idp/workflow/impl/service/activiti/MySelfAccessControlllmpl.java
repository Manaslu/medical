package com.idp.workflow.impl.service.activiti;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.identity.IAccessController;
import com.idp.workflow.util.common.StringUtil;
import com.idp.workflow.vo.identity.UserVO;
 
/**
 * 个人回退自己完成的任务
 * 
 * @author panfei
 * 
 */
public class MySelfAccessControlllmpl implements IAccessController {

	@Override
	public String canPass(UserVO currentPersion, UserVO beforePersion,
			UserVO afterPersion, Object params) throws WfBusinessException {
		if (currentPersion == null
				|| StringUtil.isEmpty(currentPersion.getUserID())) {
			throw new WfBusinessException("当前操作员不能为空！");
		}

		if (beforePersion == null
				|| StringUtil.isEmpty(beforePersion.getUserID())) {
			throw new WfBusinessException("上一个任务完成者不能为空，不能回退任务！");
		}

		if (!currentPersion.getUserID().equals(beforePersion.getUserID())) {
			throw new WfBusinessException("当前操作人员非上一个任务完成者，不能回退任务！当前操作人人员为："
					+ currentPersion.getUserID() + "，上一个任务完成者为："
					+ beforePersion.getUserID());
		}
		return currentPersion.getUserID();
	}
}
