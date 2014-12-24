package com.idap.web.sysmgr.menu.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idp.pub.constants.Constants;
import com.idp.pub.service.IBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.web.controller.BaseController;
import com.idp.sysmgr.menu.dao.MenuTreeDao;
import com.idp.sysmgr.menu.entity.Menu;
import com.idp.sysmgr.menu.entity.MenuTree;
import com.idp.sysmgr.menu.service.IMenuTreeService;

@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController<Menu, Long> {

	@Resource(name = "menuService")
	public void setBaseService(IBaseService<Menu, Long> baseService) {
		super.setBaseService(baseService);
	}

	@Resource(name = "menuTreeService")
	private IBaseService<MenuTree, String> menuTreeService;

	@Resource(name = "menuTreeService")
	private IMenuTreeService imenuTreeService;

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	
	@Resource(name = "menuTreeDao")
	private MenuTreeDao menuTreeDao;
	
	@RequestMapping(params = "list=true", method = RequestMethod.GET)
	@ResponseBody
	public List<MenuTree> findList(@RequestParam("params") String values) {
		
		Map<String, Object> map = JsonUtils.toMap(values);
		String userId = map.get("userId").toString();
		String dealId = map.get("dealId").toString();
		List<MenuTree> menus = new ArrayList<MenuTree>();
		Map<String, Object> maps = new HashMap<String, Object>();
		
//		
		//等于1的时候，是角色配置，展示相应的菜单，等于2的时候是查看角色配置的菜单
		if(!dealId.equals("admin") && !dealId.equals("2")){
			List<MenuTree> results = this.menuTreeDao.findRoleMenu(userId);
			for (MenuTree menu : results) {
				menu.setChildren(null);
			}
			
			for (MenuTree node1 : results) {
				boolean mark = false;
				for (MenuTree node2 : results) {
					if (node1.getParendId() != null
							&& node1.getParendId().equals(node2.getMenuId())) {
						mark = true;
						if (node2.getChildren() == null)
							node2.setChildren((new ArrayList<MenuTree>()));
						node2.getChildren().add(node1);
						break;
					}
				}
				if (!mark) {
					menus.add(node1);
				}
			}
		}else if(dealId.equals("admin") && !dealId.equals("2")){
			menus = menuTreeService.findList(maps);
		}else{
			 menus = menuTreeService.findList(JsonUtils.toMap(values));
		}
		
		return menus;
	}
	
	

	@RequestMapping(params = "initMenu=true", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initMenu(@RequestParam("menus") String values) {
		Map<String, Object> results = Constants.MAP();
		try {
			String dsql = "delete from t02_function_role t where t.role_id=1";
			this.jdbcTemplate.execute(dsql);
			dsql = "truncate table t02_sys_menu";
			this.jdbcTemplate.execute(dsql);
			MenuTree[] menus = (MenuTree[]) JsonUtils.jsonToEntity(values,
					MenuTree[].class);
			this.imenuTreeService.initMenu(Arrays.asList(menus), "99999");
			String sql = "insert into t02_function_role select 1,menu_id,sysdate,sysdate,1 from t02_sys_menu";
			this.jdbcTemplate.execute(sql);

			results.put(Constants.SUCCESS, Constants.TRUE);
		} catch (Exception e) {
			results.put(Constants.SUCCESS, Constants.FALSE);
			results.put(Constants.MESSAGE, e.getMessage());
		}
		return results;
	}

}
