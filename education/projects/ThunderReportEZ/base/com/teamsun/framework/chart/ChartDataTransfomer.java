package com.teamsun.framework.chart;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.teamsun.framework.util.RegExUtil;
import com.teamsun.thunderreport.parse.Chart;

public class ChartDataTransfomer {
	
	
	public static CategoryDataset DataToCategoryDataset(Chart oChart,
			List oList, int[] size) {
		String oColDimValue = oChart.getRowDim() == null ? "" : oChart
				.getRowDim().dimValue;
		oColDimValue=RegExUtil.getInBracketStr(oColDimValue);
		String oRowDimValue = oChart.getColDim() == null ? "" : oChart
				.getColDim().dimValue;
		oRowDimValue=RegExUtil.getInBracketStr(oRowDimValue);
		String measureValue = oChart.getMeasures().measureValue;
		measureValue=RegExUtil.getInBracketStr(measureValue);

		HashMap oRowKeyHashMap = new HashMap();
		HashMap oColumnKeyHashMap = new HashMap();
		HashMap oDataHashMap = new HashMap();

		int j = 0;
		int k = 0;
		for (int i = 0; i < oList.size(); i++) {
			Map oHashMap = (Map) oList.get(i);
			String oRowValue = oHashMap.get(oRowDimValue) == null ? oChart
					.getMeasures().name : (String) oHashMap.get(oRowDimValue);
			String oColValue = oHashMap.get(oColDimValue) == null ? oChart
					.getMeasures().name : (String) oHashMap.get(oColDimValue);
			double oMeasureValue = Double.parseDouble(oHashMap
					.get(measureValue).toString());
			String keyStr = "";
			if (oRowKeyHashMap.containsKey(oRowValue)) {
				if (oColumnKeyHashMap.containsKey(oColValue)) {
					keyStr = oRowKeyHashMap.get(oRowValue) + ","
							+ oColumnKeyHashMap.get(oColValue);
				} else {
					keyStr = oRowKeyHashMap.get(oRowValue) + "," + k;
					oColumnKeyHashMap.put(oColValue, new Integer(k));
					k++;
				}
			} else {
				if (oColumnKeyHashMap.containsKey(oColValue)) {
					keyStr = j + "," + oColumnKeyHashMap.get(oColValue);
					oRowKeyHashMap.put(oRowValue, new Integer(j));
					j++;
				} else {
					keyStr = j + "," + k;
					oColumnKeyHashMap.put(oColValue, new Integer(k));
					oRowKeyHashMap.put(oRowValue, new Integer(j));
					j++;
					k++;
				}
			}
			if (oDataHashMap.containsKey(keyStr)) {
				oMeasureValue += ((Double) oDataHashMap.get(keyStr))
						.doubleValue();
				oDataHashMap.put(keyStr, new Double(oMeasureValue));
			} else {
				oDataHashMap.put(keyStr, new Double(oMeasureValue));
			}
		}
		double[][] data = new double[j][k];
		String[] rowKeys = new String[j];
		String[] columnKeys = new String[k];

		Iterator rowKeyIterator = oRowKeyHashMap.keySet().iterator();
		while (rowKeyIterator.hasNext()) {
			String tempStr = (String) rowKeyIterator.next();
			rowKeys[((Integer) oRowKeyHashMap.get(tempStr)).intValue()] = tempStr;
		}

		Iterator columnKeyIterator = oColumnKeyHashMap.keySet().iterator();
		while (columnKeyIterator.hasNext()) {
			String tempStr = (String) columnKeyIterator.next();
			columnKeys[((Integer) oColumnKeyHashMap.get(tempStr)).intValue()] = tempStr;
		}

		Iterator dataKeyIterator = oDataHashMap.keySet().iterator();
		while (dataKeyIterator.hasNext()) {
			String tempStrKey = (String) dataKeyIterator.next();
			String[] tempStr = tempStrKey.split(",");
			data[Integer.parseInt(tempStr[0])][Integer.parseInt(tempStr[1])] = ((Double) oDataHashMap
					.get(tempStrKey)).doubleValue();
		}

		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				rowKeys, columnKeys, data);

		size[0] = j;
		size[1] = k;
		return dataset;
	}

	public static PieDataset DataToPieDataset(Chart oChart, List oList) {
		DefaultPieDataset dataset = new DefaultPieDataset();

		String dimValue = oChart.getRowDim().dimValue;
		dimValue=RegExUtil.getInBracketStr(dimValue);
		String measureValue = oChart.getMeasures().measureValue;
		measureValue=RegExUtil.getInBracketStr(measureValue);

		HashMap oDataSet = new HashMap();

		for (int i = 0; i < oList.size(); i++) {
			Map oHashMap = (Map) oList.get(i);
			if (oDataSet.containsKey((String) oHashMap.get(dimValue))) {
				oDataSet.put((String) oHashMap.get(dimValue),
						new Double(((Double) oDataSet.get((String) oHashMap
								.get(dimValue))).doubleValue()
								+ Double.parseDouble(oHashMap.get(measureValue)
										.toString())));
			} else {
				oDataSet.put((String) oHashMap.get(dimValue), new Double(Double
						.parseDouble(oHashMap.get(measureValue).toString())));
			}
		}
		Iterator oDataSetIterator = oDataSet.keySet().iterator();
		while (oDataSetIterator.hasNext()) {
			String tempStr = (String) oDataSetIterator.next();
			dataset.setValue(tempStr, (Double) oDataSet.get(tempStr));
		}
		return dataset;
	}
}
