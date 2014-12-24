package com.idap.dataprocess.clean.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.idap.dataprocess.clean.entity.DataCleanReport;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述：数据清洗报告
 * @创建日期：2014-5-28 11:20:16
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Repository("dataCleanRepoDao")
public class DataCleanReportDao extends DefaultBaseDao<DataCleanReport, String> {
    private static final Log logger = LogFactory.getLog(DataCleanReportDao.class);
    public static final String NAME_SPACE = DataCleanReport.class.getName();

    @Override
    public String getNamespace() {
        return NAME_SPACE;
    }

}
