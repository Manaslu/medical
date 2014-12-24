package com.idap.dataprocess.assess.entity;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.idap.dataprocess.definition.entity.DataDefinitionAttr;

/**
 * /**
 * 
 * @###################################################
 * @功能描述：
 * @创建日期：2014-4-22 9:12:44
 * @开发人员：李广彬
 * @修改日志：
 * @###################################################
 */
public class CompleteSituation implements java.io.Serializable {
	private String completeSituation; //完成情况
	private String columns; //逗号分的列名称
	private String columnsName; //逗号分的字段名称
	private int columnsSize = 0; //逗号分隔的字段数
	private int columnsTotalSize = 0; //逗号分隔的字段数
	private Long count = 0l; //去null 后的记录数之和
	private String ratio;// 属性填充率
	private String filterRatio;//字段完整情况 占比

	private Long totalCount = 0l;// 总记录数 多个字段之和
//	private Long filterCount = 0l;//

	public CompleteSituation() {

	}
	
	public CompleteSituation(int size) {
		columnsTotalSize=size;
	}

	public String getColumnsName() {
		return columnsName;
	}

	public void setColumnsName(String columnsName) {
		this.columnsName = columnsName;
	}

	public void setFilterRatio(String filterRatio) {
		this.filterRatio = filterRatio;
	}

//	public Long getFilterCount() {
//		return filterCount;
//	}
//
//	public void setFilterCount(Long filterCount) {
//		this.filterCount = filterCount;
//	}
//	
	public String getCompleteSituation() {
		return completeSituation;
	}

	public void setCompleteSituation(String completeSituation) {
		this.completeSituation = completeSituation;
	}

	public String getColumns() {
		return columns;
	}

	public void addColumns(String columnName) {
		if (StringUtils.isNotBlank(this.columns)) {
			this.columns += "," + columnName;
		} else {
			this.columns = columnName;
		}
	}

	public void addColumnsName(String colShowName) {
		if (StringUtils.isNotBlank(this.columnsName)) {
			this.columnsName += "," + colShowName;
		} else {
			this.columnsName = colShowName;
		}
		columnsSize++;
	}

	public void converColumns(List<DataDefinitionAttr> columnsList) {
		// <String,DataDefinitionAttr> List<DataDefinitionAttr> columnsList
		if (StringUtils.isNotBlank(this.columns)) {
			String[] cols = this.columns.split(",");
			for (DataDefinitionAttr col : columnsList) {
				for (String colName : cols) {
					if (colName.toUpperCase().equals(col.getColumnName().toUpperCase())) {
						addColumnsName(col.getColumnDesc());
						break;
					}
				}
			}
		}
	}

	public Long getCount() {
		return count;
	}

	public void addCount(Long count) {
		this.count += count;
	}

	public String getRatio() {
		if (totalCount.equals(0l)) {
			return "0";
		}
		Double ratioInt = (count * 0.1 * 1000 / totalCount);
		return String.format("% 50.2f", ratioInt);
	}

	//非null记录数占所有非null的比值
	public String getFilterRatio() {
		if (columnsTotalSize==0) {
			return "0";
		}
		Double ratioInt = (columnsSize * 0.1 * 1000 / columnsTotalSize);
		return String.format("% 50.2f", ratioInt);
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void addTotalCount(Long totalCount) {
		this.totalCount += totalCount;
	}

	public int getColumnsSize() {
		return columnsSize;
	}

	public void setColumnsSize(int columnsSize) {
		this.columnsSize = columnsSize;
	}

}