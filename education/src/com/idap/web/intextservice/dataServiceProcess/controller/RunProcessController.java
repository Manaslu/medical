package com.idap.web.intextservice.dataServiceProcess.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.dataprocess.rule.entity.DataInteLog;
import com.idap.dataprocess.rule.service.DataInteLogService;
import com.idap.intextservice.dataServiceProcess.entity.RunProcess;
import com.idap.intextservice.dataServiceProcess.service.DataFlowExecutor;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;

@Controller
@RequestMapping(value = "/runProcess")
public class RunProcessController extends BaseController<RunProcess, String>{

	@Resource(name = "runProcessService")
	public void setBaseService(IBaseService<RunProcess, String> baseService) {
		super.setBaseService(baseService);
	}
	
	@Resource(name = "dataFlowExecutor")
	public DataFlowExecutor dataFlowExecutor;
	
	
	@Resource(name = "dataInteLogService")
	public DataInteLogService dataInteLogService;

	
	//查看报告
	@RequestMapping(params = "findReport=true", method = RequestMethod.GET)
	@ResponseBody
	protected List<DataInteLog> findReport(@RequestParam("params") String values) {
		String processId = (String) JsonUtils.toMap(values).get("processId");
		DataInteLog dataInteLog1 = new DataInteLog();
		dataInteLog1.setProcessId(processId);
		return this.dataInteLogService.findReport(dataInteLog1);
	}
	
	@RequestMapping(params = "runPro=true",method = RequestMethod.GET)
	@ResponseBody
	public void runFlowRuleCust(@RequestParam("params") String values){

		Map<String, Object> runProcess = JsonUtils.toMap(values);
		String runProcId = (String) runProcess.get("runProcId");
		try {
			dataFlowExecutor.execute(runProcId);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

}
