package com.idap.dataprocess.definition.dao;

import com.idap.dataprocess.definition.entity.DataDefinition;

/**
 * @###################################################
 * @创建日期：2014-4-4 9:18:48
 * @开发人员：李广彬
 * @功能描述：数据定义
 * @修改日志：
 * @###################################################
 */

public interface DataDefinitionDao {

    /**
     * 级联查询－查询数据定义时，同进查出所有的字段信息列表
     * 
     * @param dataDefId
     * @return
     */
    public DataDefinition queryDefinitionCascade(String dataDefId);

}
