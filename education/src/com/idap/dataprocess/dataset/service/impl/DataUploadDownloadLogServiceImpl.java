package com.idap.dataprocess.dataset.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.dataprocess.dataset.entity.DataUploadDownloadLog;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-18 11:47:21
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Service("dataUploadDownloadLogService")
@Transactional
public class DataUploadDownloadLogServiceImpl extends DefaultBaseService<DataUploadDownloadLog, String> {

    @Resource(name = "dataUploadDownloadLogDao")
    public void setBaseDao(IBaseDao<DataUploadDownloadLog, String> baseDao) {
        super.setBaseDao(baseDao);
    }

    @Resource(name = "dataUploadDownloadLogDao")
    public void setPagerDao(IPagerDao<DataUploadDownloadLog> pagerDao) {
        super.setPagerDao(pagerDao);
    }

}
