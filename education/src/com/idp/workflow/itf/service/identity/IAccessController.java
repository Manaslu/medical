package com.idp.workflow.itf.service.identity;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.vo.identity.UserVO;

/**
 * 权限是否可以执行判断
 * 
 * @author panfei
 * 
 */
public interface IAccessController {

	/**
	 * 判断当前操作员是否可以回退已完成的任务
	 * 
	 * @param currentPersion
	 *            当前操作员
	 * @param beforePersion
	 *            上一个执行人
	 * @param afterPersion
	 *            下一个待办人
	 * @return 真正任务实际驳回者
	 * @throws WfBusinessException
	 */
	String canPass(UserVO currentPersion, UserVO beforePersion,
			UserVO afterPersion, Object params) throws WfBusinessException;
}
