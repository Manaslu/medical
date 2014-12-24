package com.idp.workflow.itf.listener;

import com.idp.workflow.event.action.ActionEvent;
import com.idp.workflow.exception.pub.WfBusinessException;

/**
 * 动作事件接受接口
 * 
 * @author panfei
 * 
 */
public interface ActionListener<T extends ActionEvent> {

	void handleEvent(T event) throws WfBusinessException;
}
