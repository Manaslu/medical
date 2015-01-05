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
import com.idp.sysmgr.user.entity.User;
import com.idp.workflow.exception.pub.WfBusinessException;
import com.idp.workflow.runtime.activiti.CommonTaskGadget;
import com.idp.workflow.util.spring.ServiceBeanFactory;
import com.idp.workflow.vo.service.WfRunTimeContext;
@SuppressWarnings("unchecked")
public class BusCheckTask extends CommonTaskGadget {

	/**
	 * 业务领导审批
	 */
	
	private static final long serialVersionUID = 8384759742493867082L;
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
	//待执行用户
	User user = new User();
	@Override
	public void goForWard(WfRunTimeContext context) throws WfBusinessException {
		String id = context.getTaskInfoVO().getProcessInstanceId();
		Demand demand = new Demand();
		demand.setId(id);
		demand.setApproState("3");
		demandService.update(demand);
	 
		//在节点信息号里添加相应信息
		Map<String, Object> billVO = (Map<String, Object>) context.getBillVO();
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
		//第一步先传递过来当前用户
		Map<String,Object> billVO = (Map<String,Object>)context.getBillVO();//lisi或者liwu
		//根据上次任务的userId还有本次任务的角色查询出当前任务的执行者,我要根据zhangsan查询出lisi
		//本次任务的角色编号是JSLDSP(技术领导审批)
		//设定本次执行任务的人
		context.getDentityChecker().setAssigneedUserId((String)billVO.get("nextUserId"));
	}

	@Override
	public void backForWard(WfRunTimeContext context)
			throws WfBusinessException {
		//业务领导审批执行此么 
		System.out.println("回退2");
		System.out.println(">>>>>>>");
	}

}
