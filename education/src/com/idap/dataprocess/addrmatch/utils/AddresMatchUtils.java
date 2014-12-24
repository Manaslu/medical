package com.idap.dataprocess.addrmatch.utils;

import java.util.List;

import com.idap.dataprocess.definition.entity.DataDefinitionAttr;

public class AddresMatchUtils {
	public static String getInputFileName(String seqNum) {
		return "ADDR_INPUT_" + seqNum + ".txt";
	}

	public static String getOutputFileName(String seqNum) {
		return "ADDR_OUTPUT_" + seqNum + ".txt";
	}

	public static int getColumnIndex(List<DataDefinitionAttr> attrs, String attrName) {
		int columnIndex = -1;
		for (DataDefinitionAttr attr : attrs) {
			if (attr.getColumnName().equals(attrName)) {
				columnIndex++;
				break;
			}
		}
		return columnIndex;
	}
}
