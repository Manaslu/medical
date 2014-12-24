package com.idap.intextservice.repository.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.intextservice.repository.dao.LabelLibDao;
import com.idap.intextservice.repository.entity.LabelLib;
import com.idap.intextservice.repository.service.ILabelLibService;
import com.idp.pub.constants.Constants;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @功能描述：标签管理
 * @创建日期：2014-5-8 15:34:52
 * @开发人员：BAI
 * @修改日志：
 * @###################################################
 */
@Service("labelLibService")
@Transactional
public class LabelLibServiceImpl extends DefaultBaseService<LabelLib, String>
		implements ILabelLibService {

	@Resource(name = "LabelLibDao")
	private LabelLibDao dao;

	@Resource(name = "LabelLibDao")
	public void setBaseDao(IBaseDao<LabelLib, String> baseDao) {
		super.setBaseDao(baseDao);
	}

	// 分页
	@Resource(name = "LabelLibDao")
	public void setPagerDao(IPagerDao<LabelLib> pagerDao) {
		super.setPagerDao(pagerDao);
	}

	// @Resource(name="LabelLibDao")
	public List<LabelLib> query() {

		return super.findList(Constants.MAP());

	}

	public Integer delete(Map<String, Object> params) {
		return super.delete(params);

	}

	@Override
	public String queryLabelId(String name) {
		List<LabelLib> labelList = this.dao.queryLabelId(name);
		String labelLib = "";
		if (null != labelList) {
			labelLib = labelList.get(0).getLabelId();
		}

		return labelLib;
	}

	 
	public List<LabelLib> queryNameList (String name) {
		List<LabelLib> labelList = this.dao.queryLabelName(name); 
		/*//int repetitionNameNumber = 0;
		if (null != labelList) {
			repetitionNameNumber = labelList.size();
			 
		}*/

		return labelList;
	}

	@Override
	public List<HashMap<String, String>> generaterPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}

}
