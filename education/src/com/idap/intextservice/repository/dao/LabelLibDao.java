package com.idap.intextservice.repository.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.idap.intextservice.repository.entity.KnowledgeBase;
import com.idap.intextservice.repository.entity.LabelLib;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述： 标签库管理dao
 * @创建日期：2014-5-8 15:34:52
 * @开发人员：bai
 * @修改日志：
 * @###################################################
 */
@Repository("LabelLibDao")
public class LabelLibDao extends DefaultBaseDao<LabelLib, Long> {
	public static final String NAME_SPACE = "com.idap.intextservice.repository.entity.LabelLib";

	@Override
	public String getNamespace() {
		return NAME_SPACE;
	}

	// 查询标签
	public List<LabelLib> queryLabelId(String name) {

		return get("getByName", name);

	}

	// 查询标签名
	public List<LabelLib> queryLabelName(String name) {

		return get("getByName", name);

	}

	public List<LabelLib> get(String key, String id) {
		return this.getSqlSession().selectList(sqlKey(key), id);

	}

}
