package com.teamsun.framework.ui;

import com.teamsun.thunderreport.parse.Column;


public class ListGridFactory
{

	private static boolean getType(Column[] cols)
	{
		boolean typeflag = false;
		for (int i = 0; i < cols.length; i++)
		{
			if (cols[i].getGroup() != null && cols[i].getGroup().equals("1"))
			{
				typeflag = true;
				break;
			}
		}

		return typeflag;
	}

	public static ListGrid getGrid(Column[] cols)
	{
		boolean type = getType(cols);
		if (type)
		{
			return new GroupGrid();
		} else
		{
			return new SimpleGrid();
		}

	}
	public static ListGrid getGrid(com.teamsun.thunderreport.parse.ListGrid parselistGrid)
	{
		boolean type = getType(parselistGrid.getColumns());
		com.teamsun.framework.ui.ListGrid listGrid =null;
		if (type)
		{
			listGrid= new GroupGrid();
		} else
		{
			listGrid= new SimpleGrid();
		}
		Column[] cols = parselistGrid.getColumns();
		String gridId = parselistGrid.getId();

		 ListGridFactory
				.getGrid(cols);

		listGrid.setCols(cols);
		/*
		listGrid.setConditions(cons);
		listGrid.setDataList(dataList);
		listGrid.setParamValues(values);
		*/
		listGrid.setId(parselistGrid.getId());
		listGrid.setSql(parselistGrid.getSql());
		listGrid.setReportBodyFunTitleType(parselistGrid
				.getReportBodyFunTitleType());
		listGrid.setReportBodyFunTitleValue(parselistGrid
				.getReportBodyFunTitleValue());
		listGrid.setReportFooterOutType(parselistGrid
				.getReportFooterOutType());
		listGrid.setReportFooterValue(parselistGrid.getReportFooterValue());
		listGrid.setReportHeaderOutType(parselistGrid
				.getReportHeaderOutType());
		listGrid.setReportHeaderOutValue(parselistGrid
				.getReportHeaderOutValue());
		listGrid.setTemplatefilename(parselistGrid.getTemplatefilename());
		return listGrid;
	}	
	
	public static CrossGrid getUiCrossGrid(com.teamsun.thunderreport.parse.CrossGrid parseCrossGrid){
		com.teamsun.framework.ui.CrossGrid crossGrid =new CrossGrid();
		crossGrid.setColDims(parseCrossGrid.getColDim());
		crossGrid.setRowDims(parseCrossGrid.getRowDim());
		crossGrid.setSql(parseCrossGrid.getSql());
		crossGrid.setTitle(parseCrossGrid.getTitle());
		crossGrid.setMeasures(parseCrossGrid.getMeasures());
		crossGrid.setId(parseCrossGrid.getId());
		crossGrid.setReportFooterOutType(parseCrossGrid
				.getReportFooterOutType());
		crossGrid.setReportFooterValue(parseCrossGrid.getReportFooterValue());
		crossGrid.setReportHeaderOutType(parseCrossGrid
				.getReportHeaderOutType());
		crossGrid.setReportHeaderOutValue(parseCrossGrid
				.getReportHeaderOutValue());
        crossGrid.setTemplatefilename(parseCrossGrid.getTemplatefilename());
		return crossGrid;
		
	}
	public static CrossGridEx getUiCrossGridEx(com.teamsun.thunderreport.parse.CrossGridEx parseCrossGridEx){
		com.teamsun.framework.ui.CrossGridEx crossGrid =new CrossGridEx();
		crossGrid.setColDims(parseCrossGridEx.getColDim());
		crossGrid.setRowDims(parseCrossGridEx.getRowDim());
		crossGrid.setSql(parseCrossGridEx.getSql());
		crossGrid.setId(parseCrossGridEx.getId());
		crossGrid.setMeasures(parseCrossGridEx.getMeasures());
		crossGrid.setColumns(parseCrossGridEx.getColumns());
		crossGrid.setReportFooterOutType(parseCrossGridEx
				.getReportFooterOutType());
		crossGrid.setReportFooterValue(parseCrossGridEx.getReportFooterValue());
		crossGrid.setReportHeaderOutType(parseCrossGridEx
				.getReportHeaderOutType());
		crossGrid.setReportHeaderOutValue(parseCrossGridEx
				.getReportHeaderOutValue());

		return crossGrid;
		
	}	
}
