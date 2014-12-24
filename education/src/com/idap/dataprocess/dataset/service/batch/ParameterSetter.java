package com.idap.dataprocess.dataset.service.batch;

import java.util.List;

import com.idap.dataprocess.definition.entity.DataDefinitionAttr;

public interface ParameterSetter extends Setter {
	// 获取表的属性定义
	public List<DataDefinitionAttr> getAttrs();

	// 获取表名
	public String getTableName();
}
