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
public class TechContactTask extends CommonTaskGadget {

	/**
	 * 技术联系人
	 */
	private static final long serialVersionUID = -8421362258190778799L;
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
		String techExamPsn = (String)billVO.get("nextUserId");
		String id = context.getTaskInfoVO().getProcessInstanceId();
		Demand demand = new Demand();
		demand.setId(id);
		demand.setTechExamPsn(techExamPsn);
		//存在并且是8，那么证明是终止
		if("8".equals(billVO.get("approState"))){
			demand.setApproState((String)billVO.get("approState"));
		}else{
			demand.setApproState("4");
		}
		demandService.update(demand);
		System.out.println("把需求列表里面的状态改为--------->技术领导待审批");
		//在节点信息号里添加相应信息
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
		Map<String,Object> billVO = (Map<String,Object>)context.getBillVO();//wangwu
		context.getDentityChecker().setAssigneedUserId((String)billVO.get("nextUserId"));
	}

	@Override
	public void backForWard(WfRunTimeContext context)
			throws WfBusinessException {
		String id = context.getTaskInfoVO().getProcessInstanceId();
		Demand demand = new Demand();
		demand.setId(id);
		demand.setApproState("3");
		demandService.update(demand);
		System.out.println("把需求列表里面的状态改为--------->待接收");
	}

}
