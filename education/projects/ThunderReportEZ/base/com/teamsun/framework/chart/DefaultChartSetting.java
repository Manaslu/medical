package com.teamsun.framework.chart;

import java.awt.Color;
import java.awt.Font;

import com.teamsun.thunderreport.parse.Dim;
import com.teamsun.thunderreport.parse.Measure;

public class DefaultChartSetting {
	public static final String CHART_TYPE_PIE="pie";
	public static final String CHART_TYPE_PIE_3D="pie_3d";
	public static final String CHART_TYPE_BAR="bar";
	public static final String CHART_TYPE_BAR_3D="bar_3d";
	public static final String CHART_TYPE_CURVE="curve";
	public static final String CHART_TYPE_CURVE_3D="curve_3d";
	
	public static final String ERROR_FILE="Error.PNG";
	public static final Font titleFont = new Font("黑体",Font.CENTER_BASELINE,16);
	public static final int eachwidth = 20;
	public static final int width = 380;
	public static final int height = 400;
	public static final boolean legend = true;
	public static final boolean tooltip = true;
	public static final Color backgroundcolor=Color.WHITE;
	public static final Measure measures;
	public static final Dim   rowDim;
	public static final Dim   ColDim;
	public static final String filename="";
	public static final String type="";
	public static final String caption="";
	public static final String unitSytle = "{0}={1}({2})";
	static{
		measures=new Measure();
		measures.name="";
		measures.measureValue="";
		measures.crosswith="";
		
		rowDim=new Dim();
		rowDim.name="";
		rowDim.dimValue="";
		
		ColDim=new Dim();
		ColDim.name="";
		ColDim.dimValue="";
	}
}
