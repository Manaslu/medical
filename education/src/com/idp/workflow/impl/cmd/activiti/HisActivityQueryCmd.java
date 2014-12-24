package com.idp.workflow.impl.cmd.activiti;

import java.io.Serializable;
import java.util.List;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;

/**
 * 历史活动查询命令
 * 
 * @author panfei
 * 
 */
public class HisActivityQueryCmd implements Command<Void>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8319184581725497719L;

	private String taskDefinitionKey;

	private VOCallBack<HistoricActivityInstance> vocallBack;

	public HisActivityQueryCmd(String taskDefinitionKey,
			VOCallBack<HistoricActivityInstance> callBack) {
		this.taskDefinitionKey = taskDefinitionKey;
		this.vocallBack = callBack;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		List<HistoricActivityInstanceEntity> rethistasklist = Context
				.getCommandContext().getDbSqlSession()
				.findInCache(HistoricActivityInstanceEntity.class);
		if (rethistasklist != null) {
			for (HistoricActivityInstanceEntity temp : rethistasklist) {
				if (taskDefinitionKey.equals(temp.getActivityId())) {
					vocallBack.setRetVO(temp);
					break;
				}
			}
		}
		return null;
	}

}
