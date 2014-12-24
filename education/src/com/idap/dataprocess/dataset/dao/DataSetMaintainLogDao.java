package com.idap.dataprocess.dataset.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.idap.dataprocess.dataset.entity.DataSetMaintainLog;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-18 11:47:21
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Repository("dataSetMaintainLogDao")
public class DataSetMaintainLogDao extends DefaultBaseDao<DataSetMaintainLog, Long> {
    private static final Log logger = LogFactory.getLog(DataSetMaintainLogDao.class);
    public static final String NAME_SPACE = DataSetMaintainLog.class.getName();

    @Override
    public String getNamespace() {
        return NAME_SPACE;
    }

}
