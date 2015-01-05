package com.idap.web.processmgr.demand.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.processmgr.special.entity.Demand;
import com.idap.processmgr.special.entity.NodeAnnex;
import com.idap.processmgr.special.entity.NodeInfo;
import com.idap.processmgr.special.entity.RequTrack;
import com.idap.processmgr.special.service.impl.DemandServiceImpl;
import com.idap.processmgr.special.service.impl.NodeAnnexServiceImpl;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.user.entity.User;
import com.idp.sysmgr.user.service.IUserService;
import com.idp.workflow.itf.service.IWorkFlowEngineService;
import com.idp.workflow.itf.service.IWorkFlowProxy;
import com.idp.workflow.itf.service.proinst.IProInstanceMangeService;
import com.idp.workflow.itf.service.task.ITaskQueryService;
import com.idp.workflow.vo.pub.IWorkFlowTypes.CheckType;
import com.idp.workflow.vo.pub.IWorkFlowTypes.RollBackType;
import com.idp.workflow.vo.service.WfParametersVO;
import com.idp.workflow.vo.service.proinst.ProcessInstanceVO;
import com.idp.workflow.vo.service.task.HistoricTaskVO;
import com.idp.workflow.vo.service.task.TaskIMageVO;
import com.idp.workflow.vo.service.task.TaskVO;

@Controller
@RequestMapping(value = "/demand")
public class DemandController extends BaseController<Demand, String> {
	@Resource(name = "userService")
	private IUserService userService;
	@Resource
	private IWorkFlowProxy iworkFlowProxy;

	// 工作流实例管理
	private IProInstanceMangeService proinstanceMsg;

	// 工作流任务查询
	private ITaskQueryService taskQuery;
	
	// 工作流引擎
	private IWorkFlowEngineService workengine;

	@Resource(name = "demandService")
	public void setBaseService(IBaseService<Demand, String> baseService) {
		super.setBaseService(baseService);
	}
	
	@Resource(name = "demandService")
	public DemandServiceImpl demandService;
	
	@Resource(name = "nodeAnnexService")
	public NodeAnnexServiceImpl nodeAnnexService;
	
	/**
	 * 发起申请并启动一个流程
	 */
	@SuppressWarnings("unused")
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> create(Demand entity) {
		Map<String, Object> results = Constants.MAP();

		try {
			String techConfirmPsn = entity.getTechConfirmPsn();
			if(null == techConfirmPsn){//说明是正常流程
				// 开启一个流程实例
				proinstanceMsg = iworkFlowProxy.getWorkFlowManageService()
						.getProInstanceMangeService();

				ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
				WfParametersVO wfParametersVO = new WfParametersVO();
				// 获取要执行任务用户id
				wfParametersVO.putBillVO(entity.getUserId());
				wfParametersVO.putVariables("goon", "1");
				processInstanceVO = proinstanceMsg.startProcessInstanceByCode(
						entity.getFlowType() + "Apply", null, wfParametersVO);

				// 获取实例id
				String processInstanceId = processInstanceVO.getId();
				entity.setId(processInstanceId);
				entity.setApproState("1");
				entity.setAddRecord("0");
				super.create(entity);

				taskQuery = iworkFlowProxy.getWorkFlowQueryService()
						.getTaskQueryService();
				TaskVO taskVO = new TaskVO();
				// 判断发起哪个流程
				if (entity.getFlowType().equals("inside")) {
					taskVO = taskQuery.queryTaskVOByTaskDefineCode(
							processInstanceId, "busBegin");
				} else if (entity.getFlowType().equals("outside")) {
					taskVO = taskQuery.queryTaskVOByTaskDefineCode(
							processInstanceId, "proTechBegin");
				} else if (entity.getFlowType().equals("tech")) {
					taskVO = taskQuery.queryTaskVOByTaskDefineCode(
							processInstanceId, "techBegin");
				}
				results.put("demandId", processInstanceId);
			}else{
				String id = entity.getRequCode();
				entity.setId(id);
				entity.setApproState("7");
				entity.setAddRecord("1");
				super.create(entity);
				results.put("demandId", id);
			}
			results.put(Constants.SUCCESS, Constants.TRUE);			
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}


	
	/**
	 * 删除一个需求，包括流程实例也删除
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	protected Map<String, Object> remove(@RequestParam("params") String values) {
		Map<String, Object> results = JsonUtils.toMap(values);
		try {
			// 删除流程实例
			iworkFlowProxy.getWorkFlowManageService()
					.getProInstanceMangeService()
					.deleteProcessInstance((String) results.get("id"), "");
			nodeAnnexService.delete(results);
			super.remove(values);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	
	/**
	 * 修改一个需求(不改变流程走向)
	 * @param entity
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> update(Demand entity) {
		Map<String, Object> results = Constants.MAP();
		try {
			String id = entity.getId();
			super.update(entity);
			if(!(null == entity.getFiles())){
				List<Map<String,Object>> files = entity.getFiles();
				for(Map<String,Object> file : files){
					String fileDir = (String)file.get("filePath");
					String fileName = (String)file.get("orgFileName");
					NodeAnnex nodeAnnex = new NodeAnnex();
					nodeAnnex.setId(id);
					nodeAnnex.setFileDir(fileDir);
					nodeAnnex.setFileName(fileName);
					nodeAnnexService.save(nodeAnnex);
				}
			}
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	
	/**
	 * 执行下一个任务
	 * @param values
	 */
	@RequestMapping(params = "doNextTask=true", method = RequestMethod.GET)
	@ResponseBody
	public void doNextTask(@RequestParam("params") String values) {
		try {
			Map<String, Object> map = JsonUtils.toMap(values);
			Map<String, Object> mapObj = new HashMap<String, Object>();

			// 获取数据
			String id = (String) map.get("id"); // 实例id
			String opPer = (String) map.get("opPer");// 当前执行人
			String remarks = (String) map.get("remarks");// 备注
			String message = (String) map.get("message");//会签信息
			String dataPassType = (String) map.get("dataPassType");//数据传送方式
			if (map.get("nextUserId") != null) {
				String nextUserId = (String) map.get("nextUserId");// 下一个执行人
				mapObj.put("nextUserId", nextUserId);
			}
			String taskDefineCode = (String) map.get("taskDefineCode");// 流程定义编码
			Long nodeSeq = Long.valueOf((String) map.get("nodeSeq"));
			String nodeId = iworkFlowProxy.getWorkFlowQueryService()
					.getTaskQueryService()
					.queryTaskVOByTaskDefineCode(id, taskDefineCode).getId();
			// 对需求跟踪表操作
			RequTrack requTrack = new RequTrack();
			requTrack.setNodeId(nodeId);
			requTrack.setId(id);
			requTrack.setOpPer(opPer);
			requTrack.setRemarks(remarks);

			// 对 节点附件表获取并设置数据
			NodeAnnex nodeAnnex = new NodeAnnex();
			nodeAnnex.setFileDir("获取的文件路径");
			nodeAnnex.setFileName("获取的文件名称");

			// 对节点信息表获取并设置数据 
			NodeInfo nodeInfo = new NodeInfo();
			nodeInfo.setNodeId(nodeId);
			nodeInfo.setId(id);
			nodeInfo.setNodeApproval("节点审批信息");
			nodeInfo.setSignInfo("会签信息");
			nodeInfo.setRemarks(remarks);
			nodeInfo.setNodeValid("0"); // 默认都是生效的0生效，1失效
			nodeInfo.setNodeSeq(nodeSeq); // 这是第nodeSeq个节点

			mapObj.put("requTrack", requTrack);
			mapObj.put("nodeAnnex", nodeAnnex);
			mapObj.put("nodeInfo", nodeInfo);
			mapObj.put("dataPassType", dataPassType);
			mapObj.put("contact", opPer);
			workengine = iworkFlowProxy.getWorkFlowEngineService();

			WfParametersVO wfParametersVO = new WfParametersVO();
			wfParametersVO.putBillVO(mapObj);
			wfParametersVO.putReMark(remarks);
			wfParametersVO.putSignature(message);
			workengine.signal(opPer, id, wfParametersVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 终止一个流程，流程执行到最后一步，流程实例结束，但是保存需求,更改状态为8
	 * @param values
	 */

	@RequestMapping(params = "doTerminateTask=true", method = RequestMethod.GET)
	@ResponseBody
	public void doTerminateTask(@RequestParam("params") String values) {
		try {
			Map<String, Object> map = JsonUtils.toMap(values);
			Map<String, Object> mapObj = new HashMap<String, Object>();
			String id = (String) map.get("id"); // 实例id
			String opPer = (String) map.get("opPer");// 当前执行人
			//传递状态参数8到后台
			workengine = iworkFlowProxy.getWorkFlowEngineService();
			WfParametersVO wfParametersVO = new WfParametersVO();
			wfParametersVO.putVariables("goon", "0");
			mapObj.put("approState", "8");
			wfParametersVO.putBillVO(mapObj);
			workengine.signal(opPer, id, wfParametersVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 驳回或者回退
	 * @param values
	 */
	@RequestMapping(params = "doRejectTask=true", method = RequestMethod.GET)
	@ResponseBody
	public void doRejectTask(@RequestParam("params") String values) {
		try {
			Map<String, Object> map = JsonUtils.toMap(values);
			// 获取数据
			String id = (String) map.get("id"); // 实例id
			String remarks = (String) map.get("remarks");// 备注
			String per = (String) map.get("per");//要执行的任务的用户

			WfParametersVO wfParametersVO = new WfParametersVO();
			wfParametersVO.putReMark(remarks);
			wfParametersVO.putRollBackType(RollBackType.LastTask);
			wfParametersVO.putTaskCheckType(CheckType.SuperiorBack);
			// 查询历史taskVO
//			Collection<HistoricTaskVO> hisTasklists = iworkFlowProxy
//					.getWorkFlowQueryService().getHistoricTaskQueryService()
//					.queryHistoricTaskVOs(id);
//			Iterator<HistoricTaskVO> iterator = hisTasklists.iterator();
//			String lastPer = "";
//			for (int i = 0; i < hisTasklists.size(); i++) {
//				if (iterator.hasNext()) {
//					lastPer = iterator.next().getOwnerId();
//				}
//			}
			workengine = iworkFlowProxy.getWorkFlowEngineService();
			workengine.rollBack(per, id, wfParametersVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 技术联系人回退，回退到初始发起人
	 * 
	 * @param values
	 */
	@RequestMapping(params = "rejectTechContactTask=true", method = RequestMethod.GET)
	@ResponseBody
	public void rejectTechContactTask(@RequestParam("params") String values) {
		try {
			Map<String, Object> map = JsonUtils.toMap(values);
			String id = (String) map.get("id"); // 获取流程实例id
			String opPer = (String) map.get("opPer");// 当前执行人
			String remarks = (String) map.get("remarks");// 备注

			WfParametersVO wfParametersVO = new WfParametersVO();
			wfParametersVO.putReMark(remarks);
			wfParametersVO.putRollBackType(RollBackType.FirstTask);
			wfParametersVO.putTaskCheckType(CheckType.SuperiorBack);
			workengine = iworkFlowProxy.getWorkFlowEngineService();
			workengine.rollBack(opPer, id, wfParametersVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存附件文件相关信息
	 * @param values
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "doSaveAnnex=true", method = RequestMethod.GET)
	@ResponseBody
	public void doSaveAnnex(@RequestParam("params") String values) {
		try {
			Map<String, Object> map = JsonUtils.toMap(values);
			String id = (String) map.get("id"); // 实例id
			String fruitCode = (String) map.get("fruitCode");//成果编号
			List<Map<String,Object>> files = (List<Map<String,Object>>)map.get("files");
			for(Map<String,Object> file : files){
				String fileDir = (String)file.get("filePath");
				String fileName = (String)file.get("orgFileName");
				NodeAnnex nodeAnnex = new NodeAnnex();
				nodeAnnex.setId(id);
				nodeAnnex.setFruitCode(fruitCode);
				nodeAnnex.setFileDir(fileDir);
				nodeAnnex.setFileName(fileName);
				nodeAnnexService.save(nodeAnnex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	
	/**
	 * 点击查看等操作，查询下一个执行用户
	 * 
	 * @param values
	 * @return
	 */
	@RequestMapping(params = "getNextUser=true", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getNextUser(@RequestParam("params") String values) {
		Map<String, Object> results = JsonUtils.toMap(values);
		try {
			// 传递页面的当前操作人的用户id，还有角色编号
			int id = Integer.valueOf((String) results.get("id"));
			String inRoleId = (String) results.get("inRoleId");
			User user = new User();
			user.setId((long) id);
			user.setInRoleId(inRoleId);

			List<User> list = userService.getByIds(user);

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 点击查看等操作，查询集团联系人
	 * 
	 * @param values
	 * @return
	 */
	@RequestMapping(params = "getGroupTechContact=true", method = RequestMethod.GET)
	@ResponseBody
	public Demand getGroupTechContact(@RequestParam("params") String values) {
		Map<String, Object> results = JsonUtils.toMap(values);
		try {
			// 传递页面的当前操作人的用户id，还有角色编号
			String id = (String) results.get("id");
			Demand demand = new Demand();
			demand.setId(id);
			demand = demandService.getGroupTechContact(id);
			return demand;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 点击查看等操作，查询省内联系人
	 * @param values
	 * @return
	 */
	@RequestMapping(params = "getProvContact=true", method = RequestMethod.GET)
	@ResponseBody
	public Demand getProvContact(@RequestParam("params") String values) {
		Map<String, Object> results = JsonUtils.toMap(values);
		try {
			// 传递页面的当前操作人的用户id，还有角色编号
			String id = (String) results.get("id");
			Demand demand = new Demand();
			demand.setId(id);
			demand = demandService.getProvContacts(id);
			return demand;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 点击查看等操作，查询技术联系人
	 * 
	 * @param values
	 * @return
	 */
	@RequestMapping(params = "getTectContact=true", method = RequestMethod.GET)
	@ResponseBody
	public Demand getTectContact(@RequestParam("params") String values) {
		Map<String, Object> results = JsonUtils.toMap(values);
		try {
			// 传递页面的当前操作人的用户id，还有角色编号
			String id = (String) results.get("id");
			Demand demand = new Demand();
			demand.setId(id);
			demand = demandService.getTechContact(id);
			return demand;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 点击新增按钮查询自动生成机构编号
	 * 
	 * @param demandId
	 * @return
	 */
	@RequestMapping(params = "getRequCode=true", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getRequCode(String demandId) {
		Map<String, String> map = new HashMap<String, String>();
		// 获取一个序列
		String seq = demandService.getReqCodeSeq();
		String insideRequCode = "Z" + seq;
		String outsideRequCode = "K" + seq;
		String techRequCode = "J" + seq;
		map.put("inside", insideRequCode);
		map.put("outside", outsideRequCode);
		map.put("tech", techRequCode);
		return map;
		// if(demandId){
		// return server.queryDemandType(demandId);
		// }else{}
	}
	
	/**
	 * 查看用户是否有发起申请权限
	 * @param demandId
	 * @return
	 */
	@RequestMapping(params = "getFlowType=true", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getFlowType(String userId) {
		Map<String, String> map = new HashMap<String, String>();
		// 获取一个序列
		List<User> lists = userService.getFlowType(userId);
		String reqType = "";
		for(User list : lists){
			//集团的技术申请角色--->技术需求
			if("JSSQ".equals(list.getInRoleId()) && "99".equals(list.getProvCode())){
					reqType += "tech";
			//业务申请角色--->专题、报表取数、统计
			}else if("YWSQ".equals(list.getInRoleId())){
					reqType += "inside";
			//省内，技术申请角色--->跨机构取数,技术需求
			}else if("JSSQ".equals(list.getInRoleId()) && !("99".equals(list.getProvCode()))){
					reqType += "tech,outside";
			}
		}
		map.put("reqType", reqType);
		return map;
	}
	/**
	 * 查询所有需求信息
	 * @param demandId
	 * @return
	 */
	@RequestMapping(params = "getAllContacts=true", method = RequestMethod.GET)
	@ResponseBody
	public Demand getAllContacts(String demandId) {
		Demand demand = demandService.getAllContacts(demandId);
		return demand;
	}
	
	/**
	 * 查询历史任务
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(params = "getHisTasks=true", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getHisTasks(@RequestParam("params") String values) {
		//查两个表，一个是历史任务表，还有个事动态任务表，不然当前正执行中的任务查不到
		Map<String, Object> results = JsonUtils.toMap(values);
		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> logList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		//map1-3主要是上面的需求阶段，执行阶段，成果阶段
		Map<String, Object> map1 = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		//map4是当前任务
		Map<String, Object> map4 = new HashMap<String, Object>();
			try {
				// 传递实例ID（流程ID）
				String id = (String) results.get("id");
				map1.put("name","需求阶段");
				map1.put("state", "active");
				map2.put("name","执行阶段");
				map2.put("state", "");
				map3.put("name", "成果阶段");
				map3.put("state","");
				Collection<HistoricTaskVO> hisTasklists = iworkFlowProxy.getWorkFlowQueryService()
								.getHistoricTaskQueryService()
									.queryHistoricTaskVOs(id);
				//这里怎么治通过id获取当前任务
//				Collection<TaskVO> taskVO = iworkFlowProxy.getWorkFlowQueryService()
//														  		.getTaskQueryService().queryToDoTasksByUserId(userId, null, id);//获取当前任务
				Collection<TaskIMageVO> taskIMageVO = iworkFlowProxy.getWorkFlowQueryService()
				  											.getTaskQueryService()
				  												.queryCurrentTaskVO(id);
				String s = "";
				//获取流程定义id
				for(HistoricTaskVO v : hisTasklists){
					s = v.getProcessDefinitionId();//流程定义Id "insideApply:1:859558ad-0d66-11e4-afe0-f04da2bbacce"
					break;
				}
				if(null == taskIMageVO || (taskIMageVO.size()) < 1){
					map1.put("state", "success");
					map2.put("state", "success");
					map3.put("state", "success");
					if("insideApply:1:859558ad-0d66-11e4-afe0-f04da2bbacce".equals(s)){//专题、报表取数、统计
						//设定所有的list2的值
						Map<String,Object> mapi1 = new HashMap<String, Object>();
						Map<String,Object> mapi2 = new HashMap<String, Object>();
						Map<String,Object> mapi3 = new HashMap<String, Object>();
						Map<String,Object> mapi4 = new HashMap<String, Object>();
						Map<String,Object> mapi5 = new HashMap<String, Object>();
						Map<String,Object> mapi6 = new HashMap<String, Object>();
						mapi1.put("name", "busBegin");
						mapi1.put("state", "success");
						mapi2.put("name", "busCheck");
						mapi2.put("state", "success");
						mapi3.put("name", "techContact");
						mapi3.put("state", "success");
						mapi4.put("name", "techCheck");
						mapi4.put("state", "success");
						mapi5.put("name", "techConfirm");
						mapi5.put("state", "success");
						mapi6.put("name", "fruitConfirm");
						mapi6.put("state", "success");
						list2.add(mapi1);
						list2.add(mapi2);
						list2.add(mapi3);
						list2.add(mapi4);
						list2.add(mapi5);
						list2.add(mapi6);
					}else if("outsideApply:1:85d21391-0d66-11e4-afe0-f04da2bbacce".equals(s)){//跨机构取数
						Map<String,Object> mapu1 = new HashMap<String, Object>();
						Map<String,Object> mapu2 = new HashMap<String, Object>();
						Map<String,Object> mapu3 = new HashMap<String, Object>();
						Map<String,Object> mapu4 = new HashMap<String, Object>();
						Map<String,Object> mapu5 = new HashMap<String, Object>();
						Map<String,Object> mapu6 = new HashMap<String, Object>();
						Map<String,Object> mapu7 = new HashMap<String, Object>();
						mapu1.put("name", "proTechBegin");
						mapu1.put("state", "success");
						mapu2.put("name", "proTechCheck");
						mapu2.put("state", "success");
						mapu3.put("name", "proContact");
						mapu3.put("state", "success");
						mapu4.put("name", "groupContact");
						mapu4.put("state", "success");
						mapu5.put("name", "groupCheck");
						mapu5.put("state", "success");
						mapu6.put("name", "groupConfirm");
						mapu6.put("state", "success");
						mapu7.put("name", "fruitConfirm");
						mapu7.put("state", "success");
						list2.add(mapu1);
						list2.add(mapu2);
						list2.add(mapu3);
						list2.add(mapu4);
						list2.add(mapu5);
						list2.add(mapu6);
						list2.add(mapu7);
					}else if("techApply:1:85fdde85-0d66-11e4-afe0-f04da2bbacce".equals(s)){//技术需求
						Map<String,Object> mapt1 = new HashMap<String, Object>();
						Map<String,Object> mapt2 = new HashMap<String, Object>();
						Map<String,Object> mapt3 = new HashMap<String, Object>();
						Map<String,Object> mapt4= new HashMap<String, Object>();
						mapt1.put("name", "techBegin");
						mapt1.put("state", "success");
						mapt2.put("name", "techCheck");
						mapt2.put("state", "success");
						mapt3.put("name", "techConfirm");
						mapt3.put("state", "success");
						mapt4.put("name", "fruitConfirm");
						mapt4.put("state", "success");
						list2.add(mapt1);
						list2.add(mapt2);
						list2.add(mapt3);
						list2.add(mapt4);
					}
				}
				String pdId = "";
				String tdCode = "";
				String userId = "";
				if(null != taskIMageVO ){
					for(TaskIMageVO v:taskIMageVO){
						pdId = v.getProcessDefinitionId();//流程定义Id "insideApply:1:859558ad-0d66-11e4-afe0-f04da2bbacce"
						tdCode = v.getTaskDefinitionCode();//任务定义代码
						Map<String,Object> mapt = new HashMap<String, Object>();
						mapt.put("logName",v.getAssignerId());
						userId =  ((User)userService.findList(mapt).toArray()[0]).getUserName();
						break;
					}
				}
//				insideApply:1:18404
//				outsideApply:1:18408
//				techApply:1:18412
				if("insideApply:1:859558ad-0d66-11e4-afe0-f04da2bbacce".equals(pdId)){//专题、报表取数、统计
					//初始化所有的map
					Map<String,Object> mapi1 = new HashMap<String, Object>();
					Map<String,Object> mapi2 = new HashMap<String, Object>();
					Map<String,Object> mapi3 = new HashMap<String, Object>();
					Map<String,Object> mapi4 = new HashMap<String, Object>();
					Map<String,Object> mapi5 = new HashMap<String, Object>();
					Map<String,Object> mapi6 = new HashMap<String, Object>();
					mapi1.put("name", "busBegin");
					mapi1.put("state", "");
					mapi1.put("author","");
					mapi1.put("date", "");
					mapi2.put("name", "busCheck");
					mapi3.put("name", "techContact");
					mapi4.put("name", "techCheck");
					mapi5.put("name", "techConfirm");
					mapi6.put("name", "fruitConfirm");
					if("busBegin".equals(tdCode)){
						mapi1.put("state", "active");
						mapi1.put("author", userId);
					}else if("busCheck".equals(tdCode)){
						mapi1.put("state", "success");
						mapi2.put("state", "active");
						mapi2.put("author", userId);
					}else if("techContact".equals(tdCode)){
						mapi1.put("state", "success");
						mapi2.put("state", "success");
						mapi3.put("state", "active");
						mapi3.put("author", userId);
					}else if("techCheck".equals(tdCode)){
						mapi1.put("state", "success");
						mapi2.put("state", "success");
						mapi3.put("state", "success");
						mapi4.put("state", "active");
						mapi4.put("author", userId);						
					}else if("techConfirm".equals(tdCode)){
						mapi1.put("state", "success");
						mapi2.put("state", "success");
						mapi3.put("state", "success");
						mapi4.put("state", "success");
						mapi5.put("state", "active");
						mapi5.put("author", userId);						
					}else if("fruitConfirm".equals(tdCode)){
						mapi1.put("state", "success");
						mapi2.put("state", "success");
						mapi3.put("state", "success");
						mapi4.put("state", "success");
						mapi5.put("state", "success");
						mapi6.put("state", "active");
						mapi6.put("author", userId);
						map1.put("state", "success");
						map2.put("state", "active");
					}
					list2.add(mapi1);
					list2.add(mapi2);
					list2.add(mapi3);
					list2.add(mapi4);
					list2.add(mapi5);
					list2.add(mapi6);
				}else if("outsideApply:1:85d21391-0d66-11e4-afe0-f04da2bbacce".equals(pdId)){//跨机构取数
					Map<String,Object> mapu1 = new HashMap<String, Object>();
					Map<String,Object> mapu2 = new HashMap<String, Object>();
					Map<String,Object> mapu3 = new HashMap<String, Object>();
					Map<String,Object> mapu4 = new HashMap<String, Object>();
					Map<String,Object> mapu5 = new HashMap<String, Object>();
					Map<String,Object> mapu6 = new HashMap<String, Object>();
					Map<String,Object> mapu7 = new HashMap<String, Object>();
					mapu1.put("name", "proTechBegin");
					mapu1.put("state", "");
					mapu1.put("author","");
					mapu1.put("date", "");
					mapu2.put("name", "proTechCheck");
					mapu3.put("name", "proContact");
					mapu4.put("name", "groupContact");
					mapu5.put("name", "groupCheck");
					mapu6.put("name", "groupConfirm");
					mapu7.put("name", "fruitConfirm");
					if("proTechBegin".equals(tdCode)){
						mapu1.put("state","active");
						mapu1.put("author",userId);
					}else if("proTechCheck".equals(tdCode)){
						mapu1.put("state","success");
						mapu2.put("state","active");
						mapu2.put("author",userId);
					}else if("proContact".equals(tdCode)){
						mapu1.put("state","success");
						mapu2.put("state","success");
						mapu3.put("state","active");
						mapu3.put("author",userId);
					}else if("groupContact".equals(tdCode)){
						mapu1.put("state","success");
						mapu2.put("state","success");
						mapu3.put("state","success");
						mapu4.put("state","active");
						mapu4.put("author",userId);
					}else if("groupCheck".equals(tdCode)){
						mapu1.put("state","success");
						mapu2.put("state","success");
						mapu3.put("state","success");
						mapu4.put("state","success");
						mapu5.put("state","active");
						mapu5.put("author",userId);
					}else if("groupConfirm".equals(tdCode)){
						mapu1.put("state","success");
						mapu2.put("state","success");
						mapu3.put("state","success");
						mapu4.put("state","success");
						mapu5.put("state","success");
						mapu6.put("state","active");
						mapu6.put("author",userId);
					}else if("fruitConfirm".equals(tdCode)){
						mapu1.put("state","success");
						mapu2.put("state","success");
						mapu3.put("state","success");
						mapu4.put("state","success");
						mapu5.put("state","success");
						mapu6.put("state","success");
						mapu7.put("state","active");
						mapu7.put("author",userId);
						map1.put("state", "success");
						map2.put("state", "active");
					}
					list2.add(mapu1);
					list2.add(mapu2);
					list2.add(mapu3);
					list2.add(mapu4);
					list2.add(mapu5);
					list2.add(mapu6);
					list2.add(mapu7);
				}else if("techApply:1:85fdde85-0d66-11e4-afe0-f04da2bbacce".equals(pdId)){//技术需求
					Map<String,Object> mapt1 = new HashMap<String, Object>();
					Map<String,Object> mapt2 = new HashMap<String, Object>();
					Map<String,Object> mapt3 = new HashMap<String, Object>();
					Map<String,Object> mapt4= new HashMap<String, Object>();
					mapt1.put("name", "techBegin");
					mapt1.put("state", "");
					mapt1.put("author","");
					mapt1.put("date", "");
					mapt2.put("name", "techCheck");
					mapt3.put("name", "techConfirm");
					mapt4.put("name", "fruitConfirm");
					if("techBegin".equals(tdCode)){
						mapt1.put("state","active");
						mapt1.put("author",userId);
					}else if("techCheck".equals(tdCode)){
						mapt1.put("state","success");
						mapt2.put("state","active");
						mapt2.put("author",userId);						
					}else if("techConfirm".equals(tdCode)){
						mapt1.put("state","success");
						mapt2.put("state","success");
						mapt3.put("state","active");
						mapt3.put("author",userId);						
					}else if("fruitConfirm".equals(tdCode)){
						mapt1.put("state","success");
						mapt2.put("state","success");
						mapt3.put("state","success");
						mapt4.put("state","active");
						mapt4.put("author",userId);
						map1.put("state", "success");
						map2.put("state", "active");
					}
					list2.add(mapt1);
					list2.add(mapt2);
					list2.add(mapt3);
					list2.add(mapt4);
				}
				for(HistoricTaskVO v : hisTasklists){
					//需求跟踪列表
					Map<String,Object> map_ = new HashMap<String, Object>();
					//任务定义编码  busBegin
					Date date = v.getStartTime(); //执行时间
					String author = v.getAssignerId();//执行人的登陆名
					String remark = v.getRemark();//处理信息
					String signature = v.getSignature();//会签信息
					String actionType = v.getActionType();//流程走动类型
					String taskName = v.getPresentStageTaskName();//获取任务名称
					map_.put("date", date);
					
					Map<String,Object> maph = new HashMap<String, Object>();
					maph.put("logName",author);
					author =  ((User)userService.findList(maph).toArray()[0]).getUserName();
					
					map_.put("author",author);
					map_.put("desc",remark);
					map_.put("signature", signature);
					map_.put("name",taskName);//流程阶段
					if(HistoricTaskVO.ACTIONTYPE_FORWARD.equals(actionType)){
						map_.put("operate","提交");
					}else if(HistoricTaskVO.ACTIONTYPE_REJECT.equals(actionType)){
						map_.put("operate","驳回");
					}
					logList.add(map_);
				}
				list1.add(map1);
				list1.add(map2);
				list1.add(map3);
				map.put("step1", list1);
				map.put("step2",list2);
				map.put("logList", logList);
				return map;
			}catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
}


