package com.idap.web.sysmgr.institution.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.institution.entity.Institution;
import com.idp.sysmgr.institution.entity.InstitutionTree;
import com.idp.sysmgr.institution.service.IInstitutionService;

/**
 * @创建日期：2014-5-8 16:36:42
 * @开发人员：huhanjiang
 * @功能描述：机构管理实现
 * @修改日志：
 */

@Controller
@RequestMapping(value = "/institution")
public class InstitutionController extends BaseController<Institution, Long>{
 
	@Resource(name = "institutionService")
	public void setBaseService(IBaseService<Institution, Long> baseService) {
		super.setBaseService(baseService);
	}
	
	@Resource(name="institutionTreeService")
	public IBaseService<InstitutionTree,String> institutionTreeService;
	
	@Resource(name="institutionService")
	public IBaseService<Institution,String> institutionService;
	
	@Resource(name = "institutionService")
	private IInstitutionService institutionServicse;
	
	//查询本级及下级的所有机构
	@RequestMapping(params = "list=true",method = RequestMethod.GET)
	@ResponseBody
	public List<InstitutionTree> findInstitutionTree(@RequestParam("params") String values){
		List<InstitutionTree> institutions = institutionTreeService.findList(JsonUtils.toMap(values));
		return institutions;
	}
	
	
	//查询下级机构
	@RequestMapping(params = "lists=true",method = RequestMethod.GET)
	@ResponseBody
	public List<InstitutionTree> findInstitutionTreeNode(String id,String orgCd){

		InstitutionTree institutionTree = new InstitutionTree();
		//为了使下级机构登陆的时候正确显示。第一次调用的时候id始终为空
		if("".equals(id) || null == id){
			if("AA0000000000".equals(orgCd)){
				id = "99999";
				institutionTree.setpId(id);
			}else{
				institutionTree.setpId(id);
				institutionTree.setId(orgCd);
				
			}
		}else{
			institutionTree.setpId(id);
		}
		
		
		List<InstitutionTree> institutions = institutionServicse.getNodes(institutionTree);
		
		for(InstitutionTree obj : institutions){
			obj.setIsParent(true);
		}
		
		return institutions;
	}
	
	@RequestMapping(params = "institutionList=true",method = RequestMethod.GET)
	@ResponseBody
	public List<Institution> findList(@RequestParam("params") String values){
		List<Institution> institutions = institutionService.findList(JsonUtils.toMap(values));
		return institutions;
	}
	
	
	@RequestMapping(params = "ifLowerOrgcd=true", method = RequestMethod.GET)
	@ResponseBody
	public List<Institution> ifLowerLevelOrgcd(
			@RequestParam("params") String cureentOrgCd, String targetOrgCd) {
		HashMap<String, String> map = new HashMap();
		map.put("cureentOrgCd", cureentOrgCd); // cureentorgCd
		map.put("targetOrgCd", targetOrgCd);

		List<Institution> institutions = institutionServicse
				.ifLowerLevelOrgcd(map);

		return institutions;
	}
	
	
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseBody
	protected Map<String, Object> remove(@RequestParam("params") String values) {
		Map<String, Object> results = Constants.MAP();
		try {
			String orgCd = "";
			String[] orgcds=values.split(",");
			//循环删除所选机构及其下级机构
			for(int i = 0;i<orgcds.length;i++){
				orgCd = orgcds[i];
				Map<String, Object> institutionMap = new HashMap<String, Object>();
				institutionMap.put("orgCd", orgCd);
				this.institutionService.delete(institutionMap);
			}
			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}
	
}
