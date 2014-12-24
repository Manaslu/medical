package com.idap.dataprocess.dataset.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.dataprocess.dataset.entity.DataSetMaintainLog;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @功能描述：数据集 记录修改日志
 * @创建日期：2014-4-18 11:47:21
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Service("dataSetMaintainLogService")
@Transactional
public class DataSetMaintainLogServiceImpl extends DefaultBaseService<DataSetMaintainLog, Long> {
    private static final Log logger = LogFactory.getLog(DataSetMaintainLogServiceImpl.class);

    @Resource(name = "dataSetMaintainLogDao")
    public void setBaseDao(IBaseDao<DataSetMaintainLog, Long> baseDao) {
        super.setBaseDao(baseDao);
    }

    @Resource(name = "dataSetMaintainLogDao")
    public void setPagerDao(IPagerDao<DataSetMaintainLog> pagerDao) {
        super.setPagerDao(pagerDao);
    }

}
