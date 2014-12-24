package com.idap.dataprocess.definition.dao;

import org.springframework.stereotype.Repository;

import com.idap.dataprocess.definition.entity.ConceptModelDic;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述：概念模型
 * @创建日期：2014-4-17 11:12:30
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Repository("conceptModelDicDao")
public class ConceptModelDicDao extends DefaultBaseDao<ConceptModelDic, String> {
    public static final String NAME_SPACE = ConceptModelDic.class.getName();

    @Override
    public String getNamespace() {
        return NAME_SPACE;
    }

}
