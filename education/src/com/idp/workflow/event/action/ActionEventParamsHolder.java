package com.idp.workflow.event.action;

import com.idp.workflow.util.common.StringUtil;

/**
 * 动作参数上下文
 * 
 * @author panfei
 * 
 */
public class ActionEventParamsHolder {

	private static ThreadLocal<ActionEventParams> actionEventParamsThreadLocal = new ThreadLocal<ActionEventParams>();

	public static ActionEventParams getCurrentActionEventParams() {
		ActionEventParams retobj = actionEventParamsThreadLocal.get();
		return retobj;
	}

	public static void setCurrentActionEventParams(ActionEventParams params) {
		actionEventParamsThreadLocal.set(params);
	}

	public static void setCurrentActionEventParams(String taskStageCode,
			String taskStageName, String passTaskCodeName) {
		ActionEventParams params = new ActionEventParams();
		params.setTaskStageCode(taskStageCode);
		params.setTaskStageName(taskStageName);
		if (!StringUtil.isEmpty(passTaskCodeName)) {
			params.setPassTaskCodeName(passTaskCodeName);
		}
		actionEventParamsThreadLocal.set(params);
	}

	public static void setCurrentActionEventParams(String passTaskCodeName) {
		getCurrentActionEventParams().setPassTaskCodeName(passTaskCodeName);
	}

	public static void clear() {
		actionEventParamsThreadLocal.remove();
	}
}
