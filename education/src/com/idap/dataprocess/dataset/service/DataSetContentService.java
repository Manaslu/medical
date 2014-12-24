package com.idap.dataprocess.dataset.service;

import java.util.List;
import java.util.Map;

import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idp.pub.entity.Pager;

/**
 * @###################################################
 * @创建日期：2014-04-01 12:01:15
 * @开发人员：李广彬
 * @功能描述：
 * @修改日志：
 * @###################################################
 */
public interface DataSetContentService {

	public Pager<Map<String, Object>> queryMergeContent(
			Map<String, Object> params, Pager<Map<String, Object>> pager);

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：分页查询 数据集内容
	 * @param page
	 *            分页信息
	 * @param dataSetName
	 *            数据集名称
	 * @param params
	 *            查询条件
	 */
	public Pager<List> queryContent(String tableName,
			Map<String, Object> params, Pager<Map<String, Object>> pager);

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：物理删除数据集
	 * @param tableName
	 *            表名
	 */
	public int dropTable(String tableName);

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：统计表记录数
	 * @param tableName
	 *            表名
	 */
	public int count(String tableName);

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：插入指定字段、指定数据值
	 * @param columns
	 *            所有字段
	 * @param values
	 *            字段对应的值
	 */
	Integer save(List<DataDefinitionAttr> columns, List<Object> values);

}
