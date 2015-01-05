package com.idap.processmgr.workflow.inside.taskListener;

import java.util.Map;

import com.idap.processmgr.special.entity.Demand;
import com.idap.processmgr.special.entity.NodeAnnex;
import com.idap.processmgr.special.entity.NodeInfo;
import com.idap.processmgr.special.entity.RequTrack;
import com.idap.processmgr.special.service.impl.DemandServiceImpl;
import com.idap.processmgr.special.service.impl.NodeAnnexServiceImpl;
import com.idap.processmgr.special.service.impl.NodeInfoServiceImpl;
import com.idap.processmgr.special.service.impl.RequTrackServiceImpl;
import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.runtime.activiti.CommonTaskGadget;
import com.idp.workflow.util.spring.ServiceBeanFactory;
import com.idp.workflow.vo.service.WfRunTimeContext;
@SuppressWarnings("unchecked")
public class TechConfirmTask extends CommonTaskGadget {

	/**
	 * 技术员需求确认
	 */
	private static final long serialVersionUID = 4591716443495742047L;
	//注入service的方法
	ServiceBeanFactory serviceBeanFactory = ServiceBeanFactory.GetInstance();
	//需求管理
	DemandServiceImpl demandService = (DemandServiceImpl)serviceBeanFactory.lookup("demandService");
	//节点附件
	NodeAnnexServiceImpl nodeAnnexService = (NodeAnnexServiceImpl)serviceBeanFactory.lookup("nodeAnnexService");
	//节点信息
	NodeInfoServiceImpl nodeInfoService = (NodeInfoServiceImpl)serviceBeanFactory.lookup("nodeInfoService");
	//需求跟踪
	RequTrackServiceImpl requTrackService = (RequTrackServiceImpl)serviceBeanFactory.lookup("requTrackService");
	//需求跟踪
	RequTrack requTrack = new RequTrack();
	//节点信息
	NodeInfo nodeInfo = new NodeInfo();
	//节点附件
	NodeAnnex nodeAnnex = new NodeAnnex();
	@Override
	public void goForWard(WfRunTimeContext context) throws WfBusinessException {
		Map<String, Object> billVO = (Map<String, Object>) context.getBillVO();
		String id = context.getTaskInfoVO().getProcessInstanceId();
		String dataPassType = (String)billVO.get("dataPassType");
		String contact = (String)billVO.get("contact");
		Demand demand = new Demand();
		demand.setId(id);
		demand.setApproState("6");
		demand.setDataPassType(dataPassType);
		//保存这个用户信息主要是给成果管理查询当前用户的需求而用的
		demand.setContact(contact);
		demandService.update(demand);
 
		requTrack = (RequTrack)billVO.get("requTrack");
		nodeInfo = (NodeInfo)billVO.get("nodeInfo"); 
		nodeAnnex = (NodeAnnex)billVO.get("nodeAnnex");
//		requTrackService.save(requTrack);
//		nodeAnnexService.save(nodeAnnex);
//		nodeInfoService.save(nodeInfo);
	}

	@Override
	public void beforeActivite(WfRunTimeContext context)
			throws WfBusinessException {
		Map<String,Object> billVO = (Map<String,Object>)context.getBillVO();//dagang
		context.getDentityChecker().setAssigneedUserId((String)billVO.get("nextUserId"));
	}

	@Override
	public void backForWard(WfRunTimeContext context)
			throws WfBusinessException {
		String id = context.getTaskInfoVO().getProcessInstanceId();
		Demand demand = new Demand();
		demand.setId(id);
		demand.setApproState("5");
		demandService.update(demand);
		System.out.println("把需求列表里面的状态改为--------->待技术员确认");
	}

}
