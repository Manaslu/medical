package com.idp.workflow.impl.service.activiti;

import java.util.HashMap;
import java.util.Map;

import com.idp.workflow.exception.pub.WfNotSupprotException;
import com.idp.workflow.itf.service.identity.IAccessController;
import com.idp.workflow.vo.pub.IWorkFlowTypes.CheckType;

/**
 * 权限访问工程
 * 
 * @author panfei
 * 
 */
public class AccessControllerFactory {

	private static AccessControllerFactory instance = null;

	private Map<CheckType, IAccessController> resgiter = new HashMap<CheckType, IAccessController>();

	static {
		instance = new AccessControllerFactory();
	}

	private AccessControllerFactory() {
		resgiter.put(CheckType.SuperiorBack, new SuperiorAccessControlImpl());
		resgiter.put(CheckType.AnonymousBack, new AnonymousAccessControllImpl());
		resgiter.put(CheckType.MySelfBack, new MySelfAccessControlllmpl());
	}

	public static AccessControllerFactory getInstance() {
		return instance;
	}

	public IAccessController createAccessController(CheckType type)
			throws WfNotSupprotException {

		IAccessController controller = resgiter.get(type);
		if (controller == null) {
			throw new WfNotSupprotException("对回退/驳回任务检查权限方式选择，传入未支持类型!");
		}
		return controller;
	}
}
