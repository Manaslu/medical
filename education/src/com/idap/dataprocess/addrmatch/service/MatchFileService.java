package com.idap.dataprocess.addrmatch.service;

import com.idap.dataprocess.addrmatch.MatchException;
import com.idap.dataprocess.rule.entity.DataLogState;
import com.idap.dataprocess.rule.entity.TableInteRule;

public interface MatchFileService {

	public String createInputMatchFile() throws MatchException;

	public void importMatchedFile() throws MatchException;

	public void rollback();

	public void updateState(String logId, DataLogState state);

	public void setTableInteRule(TableInteRule inteRule);

	public int columnIndex(String attrName);
}
