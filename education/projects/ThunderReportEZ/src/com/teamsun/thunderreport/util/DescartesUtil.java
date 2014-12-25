package com.teamsun.thunderreport.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DescartesUtil {

	/**
	 * 产生文件名列表
	 * 
	 * 
	 * 
	 * 
	 * @param conditonValue
	 * @return
	 */
	public static List generateFileNameList(List conditionValue, String id) {
		String filename = "r_";
		List returnValue = new ArrayList();

		// 如果报表中的所有datasrc都没有条件

		if (conditionValue == null || conditionValue.size() == 0) {
			returnValue.add(filename + id + "_");
			return returnValue;
		}

		List temp1Value = (List) conditionValue.get(0);
		if (temp1Value.size() != 0) {
			for (Iterator i = temp1Value.iterator(); i.hasNext();) {
				returnValue.add(filename + i.next().toString() + "_");
			}
		} else {
			returnValue.add(filename);
		}

		for (int i = 1; i < conditionValue.size(); i++) {
			List temp2 = (List) conditionValue.get(i);
			returnValue = genDescar(returnValue, temp2);
		}
		return returnValue;
	}

	public static List generateFileNameList(List conditionValue) {
		String filename = "r_";
		List returnValue = new ArrayList();

		// 如果报表中的所有datasrc都没有条件

		List temp1Value = (List) conditionValue.get(0);
		if (temp1Value.size() != 0) {
			for (Iterator i = temp1Value.iterator(); i.hasNext();) {
				returnValue.add(filename + i.next().toString() + "_");
			}
		} else {
			returnValue.add(filename);
		}

		for (int i = 1; i < conditionValue.size(); i++) {
			List temp2 = (List) conditionValue.get(i);
			returnValue = genDescar(returnValue, temp2);
		}
		return returnValue;

	}

	/**
	 * 两个List生成笛卡尔积
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static List genDescar(List value1, List value2) {

		List returnVal = new ArrayList();
		if (value2 != null && value2.size() != 0) {
			for (Iterator i = value1.iterator(); i.hasNext();) {
				String val = (String) i.next();
				for (Iterator j = value2.iterator(); j.hasNext();) {
					returnVal.add(val + j.next().toString() + "_");
				}
			}
		} else {
			for (Iterator i = value1.iterator(); i.hasNext();) {
				String val = (String) i.next();
				returnVal.add(val);
			}
		}
		return returnVal;

	}

}
