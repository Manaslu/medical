package com.idap.dataprocess.assess.dao;

import java.util.Map;

public interface DataSetContourDao {
    /**
     * @创建日期：2014-3-3 12:01:15
     * @开发人员：李广彬
     * @功能描述：执行评估存储过程
     * @param dataSetId 数据集主键
     * @return 
     *          in dataSetId 数据集ID VARCHAR2 
     *          out executeState INTEGER --执行结果码 0:成功 非0 ：失败
     *          out executeStep VARCHAR2 --执行结果描述 'OK':成功 否则为错误描述
     */
    public Map<String, Object> executeAssess(String dataSetId);
}
