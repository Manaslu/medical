package com.idap.dataprocess.dataset.service;

import java.util.List;

import com.idap.dataprocess.dataset.service.batch.ParameterSetter;
import com.idap.dataprocess.dataset.service.batch.SqlSetter;

public interface BatchExecutor {
	public void batchUpdate(List<Object[]> dataList, ParameterSetter setter);

	public void batchUpdate(List<Object[]> dataList, SqlSetter setter);

}
