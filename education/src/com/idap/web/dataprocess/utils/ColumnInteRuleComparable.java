package com.idap.web.dataprocess.utils;

import java.util.Comparator;

import com.idap.dataprocess.rule.entity.ColumnInteRule;

public class ColumnInteRuleComparable implements Comparator<ColumnInteRule> {
	// 对象的排序方式[升、降]
	@Override
	public int compare(ColumnInteRule arg1, ColumnInteRule arg2) {
		return arg1.getBatchNo().compareTo(arg2.getBatchNo());
	}

}