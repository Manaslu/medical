package com.idap.dataprocess.dataset.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.definition.entity.DataDefinition;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idp.pub.entity.Pager;
import com.idp.pub.service.IBaseService;

/**
 * @###################################################
 * @创建日期：2014-4-4 16:57:49
 * @开发人员：李广彬
 * @功能描述：
 * @修改日志：
 * @###################################################
 */
public interface DataSetService extends IBaseService<DataSet, String> {
	/**
	 * @创建日期：2014-3-3 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：删除数据集记录、对应的数据集表
	 * @param entity 实体对像
	 * @return 操作记录条数
	 */
	public Integer deleteCascade(DataSet entity) throws Exception;

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：级联保存：（实验数据上传） 1.保存数据定义 -》写上传日志 1.信息组织给存储过程（上传）
	 * @param bo 数据定义和相应字段信息
	 * @param uploadFile 上传文件
	 * @param dataSet 数据集信息
	 * @param splitSeparate 分隔符
	 * @param firstIsTitle 第一行是否是表头
	 */
	public DataSet saveUploadCascade(DataDefinition bo, File uploadFile, DataSet dataSet, String splitSeparate,
			String firstIsTitle) throws Exception;

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：实验数据下载 对应存储过程生成下载文件 回写文件路径 （zip 压缩文件）
	 * @param tableName 表名称
	 * @param cols 字段信息
	 * @param basePath 下载的基准路径
	 * @return 下载文件地址
	 */
	public String dataSetDownload(String tableName, List<DataDefinitionAttr> cols, String basePath) throws IOException;

	/**
	 * @创建日期：2014-04-01 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：统计数据集个数
	 * @传入参数：
	 */
	public Integer count(Map<String, Object> params);

	/**
	 * @创建日期：2014-07-17 12:01:15
	 * @开发人员：李广彬
	 * @功能描述：为接口数据提供的数据集转换查询
	 * @param tableName 表名称
	 * @param cols 字段信息
	 * @param basePath 下载的基准路径
	 * @return 下载文件地址
	 */
	public Pager<DataSet> findByPagerForDsContour(Pager<DataSet> pager, Map<String, Object> params);

}
