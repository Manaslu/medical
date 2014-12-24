package com.idap.web.dataprocess.rule.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.rule.entity.DataInteLog;
import com.idap.dataprocess.rule.service.DataInteLogService;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.service.IBaseService;
import com.idp.pub.web.controller.BaseController;

/**
 * 1. 查询数据清洗的日志, <br>
 * <li>params.processType="clean" <li>params.opUser="1" <li>
 * params.processType="clean" <li>params.ruleType="clean"
 * <p>
 * 2. 查询数据排重的日志,params.ruleType="unique"
 * <p>
 * 3. 查询数据重构的日志,params.ruleType="rebuild"
 * <p>
 * 4. 查询数据整合的日志,params.ruleType="merge"
 * <p>
 * params.ruleType参数值可参考
 * <ul>
 * <li>CLEAN_RULE("clean"), // 清洗规则
 * 
 * <li>UNIQUE_RULE("unique"), // 排重规则
 * 
 * <li>REBUILD_RULE("rebuild"), // 重构规则
 * 
 * <li>ADDRESS_RULE("address"), // 地址匹配规则
 * 
 * <li>POST_RULE("post"), // 邮编匹配规则
 * 
 * <li>MERGE_RULE("merge");// 合并规则
 * </ul>
 * 
 * @see com.idap.dataprocess.rule.entity.RuleType
 * 
 * @author Raoxy
 * 
 */
@Controller
@RequestMapping(value = "/dataInteLog")
public class DataInteLogController extends BaseController<DataInteLog, String> {
	@Resource(name = "dataInteLogService")
	public void setBaseService(IBaseService<DataInteLog, String> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "dataInteLogService")
	public DataInteLogService dataInteLogService;

	/**
	 * 通过源数据集的ID，获取所有目标数据集
	 * <p>
	 * 在数据服务流程管理中使用
	 * 
	 * @param osetId
	 * @return
	 */
	@RequestMapping(params = "goal=true", method = RequestMethod.GET)
	@ResponseBody
	public List<DataSet> findGoalSet(String osetId) {
		List<DataSet> results = null;
		try {
			results = this.dataInteLogService.findGoalSet(osetId,
					DataInteLog.PROCESS_TYPE_MANUAL);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return results;
	}
}
