package com.idap.dataprocess.definition.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.idap.dataprocess.definition.dao.DataDefinitionDao;
import com.idap.dataprocess.definition.entity.DataDefinition;
import com.idp.pub.dao.impl.DefaultBaseDao;

/**
 * @###################################################
 * @创建日期：2014-4-4 9:18:48
 * @开发人员：李广彬
 * @功能描述：
 * @修改日志：
 * @###################################################
 */
@Repository("dataDefinitionDao")
public class DataDefinitionDaoImpl extends DefaultBaseDao<DataDefinition, Long> implements DataDefinitionDao {
    private static final Log logger = LogFactory.getLog(DataDefinitionDaoImpl.class);
    public static final String NAME_SPACE = DataDefinition.class.getName();
    public static final String QUERY_DEFINITION_CASCADE = "queryDefinitionCascade";

    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：级联查询 同时查询数据定义和字段信息
     * @传入参数：
     * @param dataDefId 数据定义主键
     * @return 数据定义和字段属性信息
     */
    public DataDefinition queryDefinitionCascade(String dataDefId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("dataDefId", dataDefId);
        return this.getSqlSession().selectOne(sqlKey(QUERY_DEFINITION_CASCADE), params);
    }

    @Override
    public String getNamespace() {
        return NAME_SPACE;
    }

}
