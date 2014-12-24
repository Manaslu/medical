package com.idp.workflow.impl.cmd.activiti;

import java.io.Serializable;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

import com.idp.workflow.vo.service.task.HistoricTaskVO;

/**
 * 主键申请命令
 * 
 * @author panfei
 * 
 */
public class PrimayKeyRequestCmd implements Command<Void>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7770523640544007960L;

	private HistoricTaskVO histaskVO;

	public PrimayKeyRequestCmd(HistoricTaskVO histaskvo) {
		this.histaskVO = histaskvo;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		IdGenerator idGenerator = Context.getProcessEngineConfiguration()
				.getIdGenerator();
		histaskVO.setId(idGenerator.getNextId());
		return null;
	}

}
