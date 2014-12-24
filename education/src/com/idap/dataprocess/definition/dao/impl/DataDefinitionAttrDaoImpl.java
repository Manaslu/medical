package com.idap.dataprocess.definition.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.idap.dataprocess.definition.dao.CountDao;
import com.idap.dataprocess.definition.dao.DataMatchAttrDao;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idp.pub.constants.Constants;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-17 9:56:48
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Repository("dataDefinitionAttrDao")
public class DataDefinitionAttrDaoImpl extends DefaultBaseDao<DataDefinitionAttr, String> implements CountDao,
        DataMatchAttrDao {
    private static final Log logger = LogFactory.getLog(DataDefinitionAttrDaoImpl.class);
    public static final String NAME_SPACE = DataDefinitionAttr.class.getName();
    public static final String KEY_INSERT_BATCH = "insertBatch";

    @Override
    public String getNamespace() {
        return NAME_SPACE;
    }

    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：统计数量－统计指定数据集的字段数量
     * @传入参数：
     * @param params 查询所有参数
     * @return 记录数
     */
    @Override
    public Integer count(Map<String,Object> params) {
        return this.getSqlSession().selectOne(this.sqlKey(DefaultBaseDao.KEY_FIND_BY_PAGER_COUNT), params);
    }

    @Override
    public List<DataDefinitionAttr> findMatchAttr() {
        return this.find("findMatchAttr", Constants.MAP());
    }

}
