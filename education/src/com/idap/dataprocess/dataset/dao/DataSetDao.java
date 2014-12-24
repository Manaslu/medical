package com.idap.dataprocess.dataset.dao;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.idap.dataprocess.dataset.entity.DataSet;
import com.idap.dataprocess.definition.dao.CountDao;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-18 11:47:20
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
@Repository("dataSetDao")
public class DataSetDao extends DefaultBaseDao<DataSet, String> implements CountDao {
    private static final Log logger = LogFactory.getLog(DataSetDao.class);
    public static final String NAME_SPACE = DataSet.class.getName();

    @Override
    public String getNamespace() {
        return NAME_SPACE;
    }

    @Override
    public Integer count(Map<String,Object> params) {
        return this.getSqlSession().selectOne(this.sqlKey(DefaultBaseDao.KEY_FIND_BY_PAGER_COUNT), params);
    }

}
