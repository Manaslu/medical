package com.idap.web.danalysis.controller;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idap.danalysis.entity.AnalysisProcessSolidify;
import com.idap.danalysis.service.IAnalysisProcessSolidifyService;
import com.idap.danalysis.service.IDataMapService;
import com.idap.dataprocess.dataset.service.DataSetContentService;
import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.role.service.RolePermissionsService;

@Controller
@RequestMapping(value = "/analysisProcess")
public class AnalysisProcessController extends BaseController<AnalysisProcessSolidify, String> {
	
	public static final String QUERY_COLUMNS = "queryColumns";
	public static final String COPY_SCRIPT = "copyScript";
	public static final String EXECUTE_SCRIPT = "executeScript";
	public static final String DROP_SCRIPT = "dropScript";
	
	@Resource(name = "analysisProcessSolidifyService")
	public void setBaseService(IBaseService<AnalysisProcessSolidify, String> baseService) {
		super.setBaseService(baseService);
	}
	
	@Resource(name = "dataMapService")
	private IDataMapService dataMapService;
	
	@Resource(name = "rolePermissionsService")
	private RolePermissionsService rolePermissionsService;
	
	@Resource(name = "analysisProcessSolidifyService")
	private IAnalysisProcessSolidifyService analysisProcessSolidifyService;
	
	@RequestMapping(method = RequestMethod.GET, params = "findTable=true")
	@ResponseBody
	protected List<Map<String, Object>> queryDataMap(@RequestParam("params") String values) {
		Map<String, Object> params = JsonUtils.toMap(values);
		List<Map<String,Object>> list = dataMapService.findList(params);
		return list;
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "findColumns=true")
	@ResponseBody
	protected List<Map<String, Object>> queryColumns(@RequestParam("params") String values) {
		Map<String, Object> params = JsonUtils.toMap(values);
		List<Map<String,Object>> list = dataMapService.findColumns(QUERY_COLUMNS, params);
		return list;
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "executeScript=true")
	@ResponseBody
	protected List<Map<String, Object>> executeScript(@RequestParam("params") String values) {
		Map<String, Object> params = JsonUtils.toMap(values);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String tableName = dataMapService.executeScript(EXECUTE_SCRIPT, params);
		String id = (String) params.get("id");
		String procedure = (String) params.get("procedure");
		String sql = "insert into t04_analysis_log(id,start_date,end_date,exec_stats,error_msg) values (?,?,?,?,?)";
		int count = this.dataMapService.queryForInt("select count(1) from t04_analysis_log where id = ?", id);
		List<Object[]> args = new ArrayList<Object[]>();
		if(StringUtils.isNotBlank(tableName)){
			Object[] objs = new Object[5];
			objs[0] = id;
			objs[1] = new Date();
			objs[2] = new Date();
			objs[3] = "成功";
			objs[4] = "";
			args.add(objs);
			
			//插入分析主题/固化流程执行日志
			if(count == 0)this.dataMapService.executeSQL(sql, args);
			//插入分析结果输出列表
			String uuid = buildId(null);
			String sql2 = "insert into t04_analysis_result_output(output_ID,id,start_date,end_date,output_table_name) values(?,?,?,?,?)";
			List<Object[]> args2 = new ArrayList<Object[]>();
			Object[] objs2 = new Object[5];
			objs2[0] = uuid;
			objs2[1] = id;
			objs2[2] = new Date();
			objs2[3] = new Date();
			objs2[4] = tableName;
			args2.add(objs2);
			
			this.dataMapService.executeSQL(sql2, args2);
			
			//添加固化表启动成功状态
			String sql3 = "update T04_ANALYSIS_PROCESS_SOLIDIFY set RUN_STATUS = ? where id = ?";
			List<Object[]> args3 = new ArrayList<Object[]>();
			Object[] objs3 = new Object[2];
			objs3[0] = 1;
			objs3[1] = id;
			args3.add(objs3);
			
			this.dataMapService.executeSQL(sql3, args3);
		}else{
			Object[] objs = new Object[5];
			objs[0] = id;
			objs[1] = new Date();
			objs[2] = new Date();
			objs[3] = "失败";
			objs[4] = "流水号为"+id+"的固化脚本执行后未返回表名";
			args.add(objs);
			
			//插入分析主题/固化流程执行日志
			if(count == 0)this.dataMapService.executeSQL(sql, args);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("error", "固化脚本"+procedure+"执行后未返回结果");
			result.add(map);
		}
		
		return result;
	}
	
	@RequestMapping(method = RequestMethod.GET, params = "copyScript=true")
	@ResponseBody
	protected void copyScript(@RequestParam("params") String values) {
		Map<String, Object> params = JsonUtils.toMap(values);
		dataMapService.copyScript(COPY_SCRIPT, params);
	}
	
	@RequestMapping(method = RequestMethod.PUT, params = "addRolePermission=true")
	@ResponseBody
	protected void addRolePermission(@RequestParam("params") String values) {
		Map<String, Object> params = JsonUtils.toMap(values);
		Long roleId = Long.parseLong(params.get("roleId")+"");
		Long menuId = Long.parseLong(params.get("menuId")+"");
		String state = (String) params.get("state");
		
		List<Object[]> list = new ArrayList<Object[]>();
		Object[] role = new Object[3];
		role[0] = roleId;
		role[1] = menuId;
		role[2] = state;
		
		list.add(role);
		
		rolePermissionsService.batchUpdate(list);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, params = "removeRolePermission=true")
	@ResponseBody
	protected void removeRolePermission(@RequestParam("params") String values) {
		Map<String, Object> params = JsonUtils.toMap(values);
		Long roleId = Long.parseLong(params.get("roleId")+"");
		Long menuId = Long.parseLong(params.get("menuId")+"");
		
		List<Object[]> list = new ArrayList<Object[]>();
		Object[] role = new Object[2];
		role[0] = roleId;
		role[1] = menuId;
		
		list.add(role);
		
		if(roleId!=null && menuId!=null)
		rolePermissionsService.deleteMenu(list);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	protected Map<String, Object> remove(@RequestParam("params") String values) {
		Map<String, Object> results = Constants.MAP();
		Map<String, Object> params = JsonUtils.toMap(values);
		try {
			this.dataMapService.copyScript(DROP_SCRIPT, params);
			this.analysisProcessSolidifyService.delete(params);
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	
	@RequestMapping(params = "exportTables=true", method = RequestMethod.GET)
	@ResponseBody
	protected List<Map<String,Object>> exportTablesList(@RequestParam("params") String values) {
		Map<String, Object> params = JsonUtils.toMap(values);
		String id = (String) params.get("id");
		String sql = "select output_table_name as outputTableName from t04_analysis_result_output where id = ?";
		List list = this.dataMapService.qeuryForList(sql, id);
		return list;
	}
}
