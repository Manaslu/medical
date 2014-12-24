package com.idp.workflow.vo.pub;

/**
 * 支持的工作流类型 和一些全局的参数定义
 * 
 * @author panfei
 * 
 */
public interface IWorkFlowTypes {

	/**
	 * 工作流插件类型
	 */
	final static String Type_Activiti = "activiti";

	/**
	 * 权限类型
	 */
	final static String OrgType_Role = "role";

	final static String OrgType_UserGroup = "usergroup";

	/**
	 * 工作流定义状态
	 */
	final static int SuspensionState_Alive = 1;

	final static int SuspensionState_Suspended = 2;

	/**
	 * sql查询排序类型
	 * 
	 * @author panfei
	 * 
	 */
	public enum OrderType {
		ASC, DESC
	}

	/**
	 * 任务检查类型
	 * 
	 * @author panfei
	 * @category AutoClaim：自动抢占认领 HandAssignee：手工认领 MySelfEnd：只能自己回退
	 *           SuperiorEnd:只能上级回退 AnonymousEnd：任何人回退
	 */
	public enum CheckType {
		None, AutoClaim, HandAssignee, MySelfBack, SuperiorBack, AnonymousBack
	}

	/**
	 * 回退、驳回类型
	 * 
	 * @author panfei
	 * @category LastTask:回退到上一个任务，BeginTask：直接回退到发起人
	 */
	public enum RollBackType {
		LastTask, FirstTask
	}

	/**
	 * 资源后缀规则
	 */
	// String Res_Name = "-xml";

	// String Pic_Name = "-pic";

	/**
	 * 元数据在spring配置文件的书写规范
	 */
	String Meta_Name = "metadata";

	/**
	 * 用户标识
	 */
	String operationUser = "operuser";

	/**
	 * 工作流状态
	 */
	String signalStatus = "signalstatus";

	/**
	 * 单据vo
	 */
	String billVO = "billvo";

	/**
	 * 评论
	 */
	String remark = "remark";

	/**
	 * 会签信息
	 */
	String signature = "signature";

	/**
	 * 任务信息
	 */
	String taskInfo = "taskinfo";

	/**
	 * 任务权限服务
	 */
	String identityChecker = "identityChecker";

	/**
	 * 服务任务
	 */
	String activityType_ServicesTask = "serviceTask";

	/**
	 * 用户任务
	 */
	String activityType_UserTask = "userTask";

	/**
	 * 开始事件
	 */
	String activityType_startEvent = "startEvent";

	/**
	 * 结束事件
	 */
	String activityType_endEvent = "endEvent";

	/**
	 * 判断分支统称
	 */
	String activityType_Gateway = "Gateway";

	/**
	 * 单行分支
	 */
	String activityType_exclusiveGateway = "exclusiveGateway";

	/**
	 * 并行分支
	 */
	String activityType_parallelGateway = "parallelGateway";

	/**
	 * 子流程
	 */
	String activityType_subProcess = "subProcess";

	/**
	 * 任务检查类型
	 * 
	 * @see CheckType
	 */
	String taskCheckType = "taskchecktype";

	/**
	 * 任务回退类型
	 * 
	 * @see CheckType
	 */
	String rollBackType = "rollbacktype";

	/**
	 * 工作流状态
	 * 
	 * @author panfei
	 * 
	 */
	public enum SignalStatus {
		ForWardRunning, BeforeActiveRunning, BackWardRunning, NotRunning
	}

	/**
	 * 服务任务的参数存储属性名称
	 */
	String configParamsName = "configParams";

	/**
	 * 路径搜索查找类型
	 */
	String searchType_OnlyServiceTask = "serviceTask";

	String searchType_OnlyUserTask = "userTask";

	String searechType_All = "all";

	/**
	 * 路径默认条件
	 */
	String transition_Def = "default";

	/**
	 * 通过判断条件
	 */
	String passDefualt = "ALL";
}
