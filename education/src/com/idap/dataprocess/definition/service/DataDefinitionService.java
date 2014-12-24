package com.idap.dataprocess.definition.service;

import com.idap.dataprocess.definition.entity.DataDefinition;

/**
 * @###################################################
 * @创建日期：2014-4-4 11:42:09
 * @开发人员：李广彬
 * @功能描述：数据定义功能
 * @修改日志：
 * @###################################################
 */
public interface DataDefinitionService {

    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：级联查询：一次性查出数据定义信息 数据定义基本信息、数据项
     * @传入参数：
     */
    public DataDefinition queryDefinitionCascade(String id) throws Exception;
    
    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：级联保存：数据定义、字段信息 级联保存
     * @传入参数：
     */
    public DataDefinition saveDefinitionCascade(DataDefinition bo);
    
    /**
     * @创建日期：2014-04-01 12:01:15
     * @开发人员：李广彬
     * @功能描述：级联更新：保存前删除所有的字段重新保存
     * @传入参数：
     */
    public DataDefinition updateDefinitionCascade(DataDefinition bo);
    

}
