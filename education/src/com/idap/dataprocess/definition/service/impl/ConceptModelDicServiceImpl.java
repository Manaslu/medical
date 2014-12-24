package com.idap.dataprocess.definition.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idap.dataprocess.definition.entity.ConceptModelDic;
import com.idp.pub.dao.IBaseDao;
import com.idp.pub.dao.IPagerDao;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.service.impl.DefaultBaseService;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-21 15:55:52
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Service("conceptModelDicService")
@Transactional
public class ConceptModelDicServiceImpl extends DefaultBaseService<ConceptModelDic, String> {
    private static final Log logger = LogFactory.getLog(ConceptModelDicServiceImpl.class);

    @Resource(name = "conceptModelDicDao")
    public void setBaseDao(IBaseDao<ConceptModelDic, String> baseDao) {
        super.setBaseDao(baseDao);
    }

    @Resource(name = "conceptModelDicDao")
    public void setPagerDao(IPagerDao<ConceptModelDic> pagerDao) {
        super.setPagerDao(pagerDao);
    }

}
