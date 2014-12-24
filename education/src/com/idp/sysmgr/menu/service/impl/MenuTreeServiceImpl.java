package com.idp.sysmgr.menu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.pub.utils.JsonUtils;
import com.idp.pub.utils.StringUtils;
import com.idp.sysmgr.menu.entity.MenuTree;
import com.idp.sysmgr.menu.service.IMenuTreeService;

@Service("menuTreeService")
@Transactional
public class MenuTreeServiceImpl extends DefaultBaseService<MenuTree, String>
		implements IMenuTreeService {
	@Resource(name = "menuTreeDao")
	public void setBaseDao(IBaseDao<MenuTree, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public List<MenuTree> findList(Map<String, Object> params) {
		List<MenuTree> results = this.getBaseDao().find(params);
		for (MenuTree menu : results) {
			menu.setChildren(null);
		}
		List<MenuTree> nodeList = new ArrayList<MenuTree>();
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
				nodeList.add(node1);
			}
		}
		return nodeList;
	}

	public void initMenu(List<MenuTree> menus, String parentId) {
		String menu_prefix = "";
		if (parentId.equals("99999")) {
			menu_prefix = "1";
		} else {
			menu_prefix = parentId;
		}
		MenuTree menu = null;
		for (int index = 0; index < menus.size(); index++) {
			menu = menus.get(index);
			if (StringUtils.isEmpty(menu.getMenuDesc())) {
				menu.setMenuDesc(menu.getName());
			}
			if (StringUtils.isEmpty(menu.getMenuId())) {
				menu.setMenuId(menu_prefix + "0" + (index + 1));
			}
			menu.setIndexNo((index + 1) + "");
			menu.setParendId(parentId);
			this.save(menu);
			if (!menu.getChildren().isEmpty()) {
				this.initMenu(menu.getChildren(), menu.getMenuId());
			}
		}
	}

	public static void main(String[] args) {
		String json = "[{\"name\": \"首页\",\"action\": \"index\",\"template\": \"templates/index/index.html\",\"children\": [{\"name\": \"任务列表\",\"action\": \"task\",\"redirectTo\": \"list\",\"children\": [{\"name\": \"任务列表\",\"action\": \"list\",\"template\": \"templates/index/task/list.html\",\"children\": []}]}]}]";
		MenuTree[] maps = (MenuTree[]) JsonUtils.jsonToEntity(json,
				MenuTree[].class);
		for (MenuTree tree : maps) {
			System.out.println(JsonUtils.toJson(tree));
		}
	}
}
