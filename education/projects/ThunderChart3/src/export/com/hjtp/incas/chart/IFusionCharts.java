package com.hjtp.incas.chart;

import java.io.OutputStream;

import com.hjtp.incas.chart.helper.ExportHelperBean;

public interface IFusionCharts {
	public void export(ExportHelperBean exportHelperBean,OutputStream os) ;
}
