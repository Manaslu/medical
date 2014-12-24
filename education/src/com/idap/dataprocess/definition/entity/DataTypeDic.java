package com.idap.dataprocess.definition.entity;

/**
 * @###################################################
 * @创建日期：2014-4-4 16:16:59
 * @开发人员：李广彬
 * @功能描述：
 * @修改日志：
 * @###################################################
 */
public class DataTypeDic implements java.io.Serializable {
    private String dataTypeId; //
    private String dataTypeName; //

    /** default constructor */
    public DataTypeDic() {
    }

    public String getDataTypeId() {
        return this.dataTypeId;
    }

    public void setDataTypeId(String dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getDataTypeName() {
        return this.dataTypeName;
    }

    public void setDataTypeName(String dataTypeName) {
        this.dataTypeName = dataTypeName;
    }
}