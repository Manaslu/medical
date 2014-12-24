package com.idap.dataprocess.definition.entity;

import java.util.Date;
import java.util.List;

import com.idp.pub.utils.DateUtil;

/**
 * @###################################################
 * @创建日期：2014-4-4 9:02:25
 * @开发人员：李广彬
 * @功能描述：
 * @修改日志：
 * @###################################################
 */
public class DataDefinition implements java.io.Serializable {
    private Long id; //
    private String tabName; //
    private String dataDefId; //
    private String dataDefName; //
    private String dataDefDesc; //
    private String businessType; //
    private String createUser; //
    private String createUserName; //
    private Date createDate; //
    private String createDateStr;// [附加属性]
    private String startTime;// [附加属性]开始时间
    private String endTime;// [附加属性]结束时间
    private String oldDataDefId; //
    private String groupId; //
    private Integer dataSetCount; // 数据集数量

    private List<DataDefinitionAttr> columns;

    /** default constructor */
    public DataDefinition() {
    }

    public String getOldDataDefId() {
        return oldDataDefId;
    }

    public Integer getDataSetCount() {
        return dataSetCount;
    }

    public void setDataSetCount(Integer dataSetCount) {
        this.dataSetCount = dataSetCount;
    }

    public void setOldDataDefId(String oldDataDefId) {
        this.oldDataDefId = oldDataDefId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public List<DataDefinitionAttr> getColumns() {
        return columns;
    }

    public void setColumns(List<DataDefinitionAttr> columns) {
        this.columns = columns;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataDefId() {
        return this.dataDefId;
    }

    public void setDataDefId(String dataDefId) {
        this.dataDefId = dataDefId;
    }

    public String getDataDefName() {
        return this.dataDefName;
    }

    public void setDataDefName(String dataDefName) {
        this.dataDefName = dataDefName;
    }

    public String getDataDefDesc() {
        return this.dataDefDesc;
    }

    public void setDataDefDesc(String dataDefDesc) {
        this.dataDefDesc = dataDefDesc;
    }

    public String getBusinessType() {
        return this.businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDateStr() {
        return this.createDate != null ? DateUtil.dateTimeToStrDefault(this.createDate) : "";
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
        this.createDateStr = this.getCreateDateStr();
    }

    public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
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