package com.idp.workflow.impl.service.activiti;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.service.identity.IAccessController;
import com.idp.workflow.vo.identity.UserVO;

/**
 * 匿名访问权限控制
 * 
 * @author panfei
 * 
 */
public class AnonymousAccessControllImpl implements IAccessController {

	@Override
	public String canPass(UserVO currentPersion, UserVO beforePersion,
			UserVO afterPersion, Object params) throws WfBusinessException {
		// do nothing
		return currentPersion.getUserID();
	}

}
