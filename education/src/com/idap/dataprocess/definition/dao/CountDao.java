package com.idap.dataprocess.definition.dao;

import java.util.Map;

/**
 * @###################################################
 * @创建日期：2014-4-4 9:18:48
 * @开发人员：李广彬
 * @功能描述：查询数据接口
 * @修改日志：
 * @###################################################
 */

public interface CountDao {
    /**
     * 根据条件统计数量
     * 
     * @param params
     * @return
     */
    public Integer count(Map<String, Object> params);

}
