package com.idap.dataprocess.definition.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.dataprocess.definition.entity.DataTypeDic;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-21 15:55:54
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Service("dataTypeDicService")
@Transactional
public class DataTypeDicServiceImpl extends DefaultBaseService<DataTypeDic, String> {
    private static final Log logger = LogFactory.getLog(DataTypeDicServiceImpl.class);

    @Resource(name = "dataTypeDicDao")
    public void setBaseDao(IBaseDao<DataTypeDic, String> baseDao) {
        super.setBaseDao(baseDao);
    }

    @Resource(name = "dataTypeDicDao")
    public void setPagerDao(IPagerDao<DataTypeDic> pagerDao) {
        super.setPagerDao(pagerDao);
    }

}
