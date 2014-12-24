package com.idap.dataprocess.clean.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.dataprocess.clean.entity.DataCleanReport;
import com.idap.dataprocess.clean.service.CleanService;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.exception.BusinessException;

/**
 * @###################################################
 * @功能描述：清洗报告
 * @创建日期：2014-5-28 11:28:50
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Service("cleanService")
@Transactional
public class CleanServiceImpl implements CleanService {
    private static final Log logger = LogFactory.getLog(CleanServiceImpl.class);

    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：根据RuleId查询清洗报告
     * @param ruleId 
     * @return 
     */
    @Override
    public List<DataCleanReport> findCleanReport(String ruleId) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ruleId", ruleId);
        return dao.find(params);
    }

    @Resource(name = "dataCleanRepoDao")
    private IBaseDao<DataCleanReport, String> dao;

}
