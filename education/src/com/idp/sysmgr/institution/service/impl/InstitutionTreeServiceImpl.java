package com.idp.sysmgr.institution.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idp.pub.dao.IBaseDao;
import com.idp.pub.service.impl.DefaultBaseService;
import com.idp.sysmgr.institution.entity.InstitutionTree;

/**
 * 
 * @创建日期：2014-5-14
 * @开发人员：huhanjiang
 * @功能描述：机构树实现
 * 
 */

@Service("institutionTreeService")
@Transactional
public class InstitutionTreeServiceImpl extends
		DefaultBaseService<InstitutionTree, String> {


	@Resource(name = "institutionTreeDao")
	public void setBaseDao(IBaseDao<InstitutionTree, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public List<InstitutionTree> findList(Map<String, Object> params) {
		List<InstitutionTree> results = this.getBaseDao().find(params);
		List<InstitutionTree> nodeList = new ArrayList<InstitutionTree>();
		for (InstitutionTree node1 : results) {
			boolean mark = false;
			for (InstitutionTree node2 : results) {
				if (node1.getpId() != null
						&& node1.getpId().equals(node2.getId())) {
					mark = true;
					if (node2.getChildren() == null)
						node2.setChildren((new ArrayList<InstitutionTree>()));
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

}