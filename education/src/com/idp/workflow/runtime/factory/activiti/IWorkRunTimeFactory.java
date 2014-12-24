package com.idp.workflow.runtime.factory.activiti;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

import com.idp.workflow.exception.pub.WfNotSupprotException;
import com.idp.workflow.runtime.activiti.ClassDelegteRunTime;
import com.idp.workflow.runtime.activiti.IWorkFlowRunTimeDelegater;
import com.idp.workflow.runtime.activiti.UserTaskRunTime;

/**
 * 工作流任务执行单元创建工厂
 * 
 * @author panfei
 * 
 */
public class IWorkRunTimeFactory {

	private static final String key_Class = "ClassDelegate";

	private static final String key_User = "UserTaskActivityBehavior";

	private static Map<String, IWorkFlowRunTimeDelegater> cache = new HashMap<String, IWorkFlowRunTimeDelegater>();

	static {
		cache.put(key_Class, new ClassDelegteRunTime());
		cache.put(key_User, new UserTaskRunTime());
	}

	/**
	 * 创建方法
	 * 
	 * @param actimpl
	 * @return
	 * @throws WfNotSupprotException
	 */
	public static IWorkFlowRunTimeDelegater createWorkFlowRunTimeDelegater(
			ActivityImpl actimpl) throws WfNotSupprotException {
		if (actimpl != null) {
			ActivityBehavior actBehavior = actimpl.getActivityBehavior();
			if (actBehavior instanceof ClassDelegate) {
				return cache.get(key_Class);
			}

			if (actBehavior instanceof UserTaskActivityBehavior) {
				return cache.get(key_User);
			}
		}
		throw new WfNotSupprotException("不支持创建此类型实例");
	}
}
