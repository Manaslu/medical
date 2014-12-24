package com.idp.workflow.event.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.itf.listener.ActionListener;

/**
 * 事件的分发和注册
 * 
 * @author panfei
 * 
 */
public class ActionEventDispatcher {

	private static ActionEventDispatcher eventDispatcher = null;

	/**
	 * 事件注册点
	 */
	static {
		eventDispatcher = new ActionEventDispatcher();
	}

	public static ActionEventDispatcher getInstance() {
		return eventDispatcher;
	}

	private Map<Class<? extends ActionEvent>, List<ActionListener<ActionEvent>>> listenermap = new HashMap<Class<? extends ActionEvent>, List<ActionListener<ActionEvent>>>();

	private Map<String, List<ActionListener<ActionEvent>>> listenermap2 = new HashMap<String, List<ActionListener<ActionEvent>>>();

	/**
	 * 在实际正常使用下不可能出现，因为只会在启动初始化一次，但考虑到junit4测试情况，多个测试用例执行不会释放资源，会导致重复添加listener，
	 * 特此添加此方法
	 */
	public void clearActionListeners() {
		listenermap.clear();
		listenermap2.clear();
	}

	@SuppressWarnings("unchecked")
	public void registerActionListener(Class<? extends ActionEvent> classname,
			ActionListener<? extends ActionEvent> actionlistenr) {
		if (!listenermap.containsKey(classname)) {
			List<ActionListener<ActionEvent>> listenerlist = new ArrayList<ActionListener<ActionEvent>>();
			listenerlist.add((ActionListener<ActionEvent>) actionlistenr);
			listenermap.put(classname, listenerlist);
		} else {
			List<ActionListener<ActionEvent>> listenerlist = listenermap
					.get(classname);
			listenerlist.add((ActionListener<ActionEvent>) actionlistenr);
		}
	}

	@SuppressWarnings("unchecked")
	public void registerActionListener(String actionName,
			ActionListener<? extends ActionEvent> actionlistenr) {
		if (!listenermap2.containsKey(actionName)) {
			List<ActionListener<ActionEvent>> listenerlist = new ArrayList<ActionListener<ActionEvent>>();
			listenerlist.add((ActionListener<ActionEvent>) actionlistenr);
			listenermap2.put(actionName, listenerlist);
		} else {
			List<ActionListener<ActionEvent>> listenerlist = listenermap2
					.get(actionName);
			listenerlist.add((ActionListener<ActionEvent>) actionlistenr);
		}
	}

	public void dispatchEvent(ActionEvent event) throws WfBusinessException {

		List<ActionListener<ActionEvent>> listenerlist2 = this.listenermap2
				.get(event.getActionCode());

		if (listenerlist2 != null) {
			for (ActionListener<ActionEvent> temp : listenerlist2) {
				temp.handleEvent(event);
			}
		}

		List<ActionListener<ActionEvent>> listenerlist = this.listenermap
				.get(event.getClass());
		if (listenerlist != null) {
			for (ActionListener<ActionEvent> temp : listenerlist) {
				temp.handleEvent(event);
			}
		}
	}

}
