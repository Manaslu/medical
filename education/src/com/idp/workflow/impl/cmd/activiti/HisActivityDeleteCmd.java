package com.idp.workflow.impl.cmd.activiti;

import java.io.Serializable;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * 历史活动环节批量删除命令
 * 
 * @author panfei
 * 
 */
public class HisActivityDeleteCmd implements Command<Void>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6059591460189887597L;

	private String[] hisActivityIds = null;

	public HisActivityDeleteCmd(String... hisActivityIds) {
		this.hisActivityIds = hisActivityIds;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		if (hisActivityIds != null && hisActivityIds.length > 0) {
			for (String tempid : hisActivityIds) {
				// commandContext.getHistoricActivityInstanceEntityManager()
				// .deleteHistoricActivityInstance(tempid);
				// update for 5.14 by panfei
				commandContext.getDbSqlSession().delete(
						"deleteHistoricActivityInstance", tempid);
			}
		}
		return null;
	}

}
