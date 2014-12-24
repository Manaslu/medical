package com.idp.workflow.event.action;

import java.util.Map;

/**
 * 基础动作事件
 * 
 * @author panfei
 * 
 */
public class ActionEvent {

	public static final String ACTIONCODE_FORWARDBEFORE = "forward_before";

	public static final String ACTIONCODE_FORWARDAFTER = "forward_after";

	public static final String ACTIONCODE_BACKWARDBEFORE = "backward_before";

	public static final String ACTIONCODE_BACKWARDAFTER = "backward_after";

	public static final String ACTIONCODE_ACTIVEBEFORE = "active_before";

	public static final String ACTIONCODE_ACTIVEAFTER = "active_after";

	public static final String ACTIONCODE_REJECTBEFORE = "reject_before";

	public static final String ACTIONCODE_REJECTAFTER = "reject_after";

	private Object source;

	private String actionCode;

	private Map<String, Object> paramObjects;

	public ActionEvent(Object source, String actionCode,
			Map<String, Object> paramObjects) {
		this.setSource(source);
		this.setActionCode(actionCode);
		this.setParamObjects(paramObjects);
	}

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public Map<String, Object> getParamObjects() {
		return paramObjects;
	}

	public void setParamObjects(Map<String, Object> paramObjects) {
		this.paramObjects = paramObjects;
	}
}
