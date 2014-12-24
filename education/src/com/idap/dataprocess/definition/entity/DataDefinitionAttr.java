package com.idap.dataprocess.definition.entity;

/**
 * @###################################################
 * 
 * @创建日期：2014-4-4 14:58:30
 * @开发人员：李广彬
 * @功能描述：
 * @修改日志：
 * @###################################################
 */
public class DataDefinitionAttr implements java.io.Serializable {
    private String id; //
    private String dataDefId; //
    private String columnId; //
    private String columnName; //
    private String columnDesc; //
    private String dataType; //
    private Integer length; //
    private String isNull; //
    private String isPk; //
    private String conceptModelId; //
    private String conceptModelName; //
    private Integer colNum; //
    private String isDisplay; //
    private Integer displayColNum; //
    private String startTime;// [附加属性]开始时间
    private String endTime;// [附加属性]结束时间

    public String getConceptModelName() {
        return conceptModelName;
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Integer getDisplayColNum() {
        return displayColNum;
    }

    public void setDisplayColNum(Integer displayColNum) {
        this.displayColNum = displayColNum;
    }

    public void setConceptModelName(String conceptModelName) {
        this.conceptModelName = conceptModelName;
    }

    public Integer getColNum() {
        return colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    /** default constructor */
    public DataDefinitionAttr() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataDefId() {
        return this.dataDefId;
    }

    public void setDataDefId(String dataDefId) {
        this.dataDefId = dataDefId;
    }

    public String getColumnId() {
        return this.columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnDesc() {
        return this.columnDesc;
    }

    public void setColumnDesc(String columnDesc) {
        this.columnDesc = columnDesc;
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

    public String getIsNull() {
        return this.isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getIsPk() {
        return this.isPk;
    }

    public void setIsPk(String isPk) {
        this.isPk = isPk;
    }

    public String getConceptModelId() {
        return this.conceptModelId;
    }

    public void setConceptModelId(String conceptModelId) {
        this.conceptModelId = conceptModelId;
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