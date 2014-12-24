package com.idap.dataprocess.definition.entity;


/**
 * @###################################################
 * @功能描述：概念模型
 * @创建日期：2014-5-8 18:12:48
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
public class ConceptModelDic implements java.io.Serializable {
    private String conceptModelId; //
    private String conceptModelName; //
    private String conceptModelDesc; //
    private String dataType; //
    private Integer length; //
    private String startTime;// [附加属性]开始时间
    private String endTime;// [附加属性]结束时间

    /** default constructor */
    public ConceptModelDic() {
    }

    public String getConceptModelId() {
        return this.conceptModelId;
    }

    public void setConceptModelId(String conceptModelId) {
        this.conceptModelId = conceptModelId;
    }

    public String getConceptModelName() {
        return this.conceptModelName;
    }

    public void setConceptModelName(String conceptModelName) {
        this.conceptModelName = conceptModelName;
    }

    public String getConceptModelDesc() {
        return this.conceptModelDesc;
    }

    public void setConceptModelDesc(String conceptModelDesc) {
        this.conceptModelDesc = conceptModelDesc;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getLength() {
        return this.length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}