package com.idap.dataprocess.clean.service;

import java.util.List;

import com.idap.dataprocess.clean.entity.DataCleanReport;

/**
 * @###################################################
 * 
 * @创建日期：2014-04-01 12:01:15
 * @开发人员：李广彬
 * @功能描述：
 * @###################################################
 */
public interface CleanService {
    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：根据RuleId查询清洗报告
     * @param ruleId 
     * @return 
     */
    public List<DataCleanReport> findCleanReport(String ruleId) throws Exception;

}
