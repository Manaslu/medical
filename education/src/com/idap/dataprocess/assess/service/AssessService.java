package com.idap.dataprocess.assess.service;

import java.util.List;
import java.util.Map;

import com.idap.dataprocess.assess.entity.CompleteSituation;
import com.idap.dataprocess.assess.entity.DataSetContour;

/**
 * @###################################################
 * 
 * @创建日期：2014-04-01 12:01:15
 * @开发人员：李广彬
 * @功能描述：
 * @修改日志：
 * @###################################################
 */
public interface AssessService {
    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：执行数据轮廓的存储过程 写 1、数据集轮廓表 2、整合日志表
     * @param dataSetId 数据集名称
     * @return 返回数据轮廓报告主键
     * @throws Exception
     */
    public Map<String, Object> executeAssess(String dataSetId);

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：字段分项统计数据
     * @param dataSetName 数据集名称
     * @return
     * @throws Exception
     */
    public List<DataSetContour> queryColAssessInfo(String dataSetName);

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：评估统计 完整度整理
     * @param dataSetName 数据集名称
     * @return
     * @throws Exception
     */
    public List<CompleteSituation> assessStatistics(String dataSetId);

}
