package com.idap.web.processmgr.processes.controller;

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
import com.idap.processmgr.special.entity.Processes;
import com.idap.processmgr.special.service.impl.DemandServiceImpl;
import com.idap.processmgr.special.service.impl.NodeAnnexServiceImpl;
import com.idap.processmgr.special.service.impl.ProcessesServiceImpl;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;
import com.idp.workflow.itf.service.IWorkFlowProxy;

@Controller
@RequestMapping(value = "/processes")
public class ProcessesController extends BaseController<Processes, String>{

	@Resource(name = "processesService")
	public void setBaseService(IBaseService<Processes, String> baseService) {
		super.setBaseService(baseService);
	}
	
	@Resource(name = "processesService")
	public ProcessesServiceImpl processesService;
	
	@Resource(name = "demandService")
	public DemandServiceImpl demandService;
	
	@Resource(name = "nodeAnnexService")
	public NodeAnnexServiceImpl nodeAnnexService;
	
	@Resource
	private IWorkFlowProxy iworkFlowProxy;
	
	/**
	 * 修改成果
	 * @param entity
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	protected Map<String, Object> update(Processes entity) {
		Map<String, Object> results = Constants.MAP();
		try {
			String fruitCode = entity.getFruitCode();
			super.update(entity);
			if(!(null == entity.getFiles())){
				List<Map<String,Object>> files = entity.getFiles();
				for(Map<String,Object> file : files){
					String fileDir = (String)file.get("filePath");
					String fileName = (String)file.get("orgFileName");
					NodeAnnex nodeAnnex = new NodeAnnex();
					nodeAnnex.setFruitCode(fruitCode);
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
	 * 通过技术员获取需求列表
	 * @param values
	 * @return
	 */
	@RequestMapping(params = "getByContact=true", method = RequestMethod.GET)
	@ResponseBody
	public List<Demand> getByContact(@RequestParam("params") String values) {
		Map<String, Object> results = JsonUtils.toMap(values);
		Map<String, String> map = new HashMap<String, String>();
		try {
			String contact = (String) results.get("contact");
			String requName = (String) results.get("requName");
			map.put("contact", contact);
			map.put("requName", requName);
			List<Demand> list = demandService.getByContact(map);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除一个成果，包括此成果对应的附件也删除
	 */
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	protected Map<String, Object> remove(@RequestParam("params") String values) {
		Map<String, Object> results = JsonUtils.toMap(values);
		try {
			nodeAnnexService.delete(results);
			//删除成果
			super.remove(values);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	
	/**
	 * 保存成果
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	protected Map<String, Object> create(Processes entity) {
		Map<String, Object> results = Constants.MAP();
		try {
			entity.setPublicState("0");
			super.create(entity);
			String fruitCode = entity.getFruitCode();
			//返回成果的主键到页面
			results.put("fruitCode", fruitCode);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	
	/**
	 * 查看是否还有未确认的成果
	 * @param values
	 */

	@RequestMapping(params = "getFruitOfConfirm=true", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getFruitOfConfirm(@RequestParam("params") String values) {
		Map<String, Object> results = Constants.MAP();
		try {
			Map<String, Object> map = JsonUtils.toMap(values);
			String id = (String) map.get("id"); // 实例id
			int allCount = processesService.findAllCount(id);
			int allCountOfConfirm = processesService.findCountOfConfirm(id);
			if(allCount == allCountOfConfirm){
				results.put(Constants.MESSAGE, Constants.TRUE);	
			}else{
				results.put(Constants.MESSAGE, Constants.FALSE);
			}
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	
}
