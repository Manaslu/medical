package com.teamsun.framework.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
 
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

import com.teamsun.thunderreport.parse.Chart;

public class ChartGenerator {

	/**
	 * @param args
	 */
	public static String generateChart(String fileName,Chart oChart,List oChartDataList) {
		fileName=fileName+".png";
		if (oChart.getType().equalsIgnoreCase(
				DefaultChartSetting.CHART_TYPE_BAR)
				|| oChart.getType().equalsIgnoreCase(
						DefaultChartSetting.CHART_TYPE_BAR_3D)) {
			fileName = generateBarChart(fileName,oChart,oChartDataList);
		} else if (oChart.getType().equalsIgnoreCase(
				DefaultChartSetting.CHART_TYPE_PIE)
				|| oChart.getType().equalsIgnoreCase(
						DefaultChartSetting.CHART_TYPE_PIE_3D)) {
			fileName = generatePieChart(fileName,oChart,oChartDataList);
		} else if (oChart.getType().equalsIgnoreCase(
				DefaultChartSetting.CHART_TYPE_CURVE)
				|| oChart.getType().equalsIgnoreCase(
						DefaultChartSetting.CHART_TYPE_CURVE_3D)) {
			fileName = generateLineChart(fileName,oChart,oChartDataList);
		}
		return fileName;
	}

	public static String generateChart(Chart oChart,List oChartDataList, HttpSession session,
			PrintWriter pw) {
		String fileName = DefaultChartSetting.ERROR_FILE;
		if (oChart.getType().equalsIgnoreCase(
				DefaultChartSetting.CHART_TYPE_BAR)
				|| oChart.getType().equalsIgnoreCase(
						DefaultChartSetting.CHART_TYPE_BAR_3D)) {
			fileName = generateBarChart(oChart,oChartDataList, session, pw);
		} else if (oChart.getType().equalsIgnoreCase(
				DefaultChartSetting.CHART_TYPE_PIE)
				|| oChart.getType().equalsIgnoreCase(
						DefaultChartSetting.CHART_TYPE_PIE_3D)) {
			fileName = generatePieChart(oChart,oChartDataList, session, pw);
		} else if (oChart.getType().equalsIgnoreCase(
				DefaultChartSetting.CHART_TYPE_CURVE)
				|| oChart.getType().equalsIgnoreCase(
						DefaultChartSetting.CHART_TYPE_CURVE_3D)) {
			fileName = generateLineChart(oChart,oChartDataList, session, pw);
		}
		return fileName;
	}

	private static String generateBarChart(String oFileName,Chart oChart,List oChartDataList) {
		String caption = (oChart.getCaption() == null ? DefaultChartSetting.caption
				: oChart.getCaption());
		String xname = (oChart.getColDim() == null ? DefaultChartSetting.ColDim.name
				: oChart.getColDim().name);
		String yname = (oChart.getRowDim() == null ? DefaultChartSetting.rowDim.name
				: oChart.getRowDim().name);
		String filename = (oFileName == null ? DefaultChartSetting.filename
				: oFileName);
		Font titleFont = DefaultChartSetting.titleFont;
		boolean legend = oChart.getLegend() == null ? DefaultChartSetting.legend
				: (oChart.getLegend().equalsIgnoreCase("true") ? true : false);

		int[] size=new int[2];
		CategoryDataset bardata = ChartDataTransfomer
				.DataToCategoryDataset(oChart,oChartDataList,size);
		int width = oChart.width == null ? ((size[0]*size[1])*DefaultChartSetting.eachwidth+DefaultChartSetting.width) : Integer.parseInt(oChart.getWidth());
		int height = oChart.height == null ? DefaultChartSetting.height
				: Integer.parseInt(oChart.getHeight());
		try {
			JFreeChart chart;
			if (oChart.getType().equalsIgnoreCase(
					DefaultChartSetting.CHART_TYPE_BAR_3D)) {
				chart = ChartFactory.createBarChart3D(caption, // chart title
						xname, yname, bardata, // data
						PlotOrientation.VERTICAL, legend, false, false);
			} else {
				chart = ChartFactory.createBarChart(caption, // chart title

						xname, yname, bardata, // data
						PlotOrientation.VERTICAL, legend, false, false);
			}
			// 设置图片标题的字体和大小
			if (caption != null) {
				TextTitle _title = new TextTitle(caption);
				_title.setFont(titleFont);
				chart.setTitle(_title);
			}
			chart.setBackgroundPaint(DefaultChartSetting.backgroundcolor);
			CategoryPlot plot = chart.getCategoryPlot();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setAxisLineVisible(true);
			domainAxis.setTickLabelsVisible(true);
			/*
			 * plot.setDomainAxis(domainAxis); ValueAxis rangeAxis =
			 * plot.getRangeAxis(); //设置最高的一个 Item 与图片顶端的距离
			 * rangeAxis.setUpperMargin(0.15); //设置最低的一个 Item 与图片底端的距离
			 * rangeAxis.setLowerMargin(0.15); plot.setRangeAxis(rangeAxis);
			 * //把生成的图片放到临时目录 BarRenderer3D renderer = new BarRenderer3D();
			 * renderer.setBaseOutlinePaint(Color.BLACK); //设置 Wall 的颜色
			 * 
			 * renderer.setWallPaint(Color.gray); //设置每种柱的颜色
			 * renderer.setToolTipGeneratorerer.setItemMargin(0.1); //显示每个柱的数值，并修改该数值的字体属性<BR>
			 * renderer.setItemLabelGenerator(new
			 * StandardCategoryItemLabelGenerator());
			 * plot.setRenderer(renderer); //设置柱的透明度<BR>
			 * plot.setForegroundAlpha(0.65f); //设置x、y的显示位置
			 * 
			 * plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			 * plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
			 */
			File file = new File(filename);
			ChartUtilities.saveChartAsPNG(file, chart, width, height);
			filename="<image src='./"+filename.substring(filename.lastIndexOf("\\")+1)+"' width="+width+" height="+height+"/>";
		} catch (Exception e) {
			e.printStackTrace();
			filename = DefaultChartSetting.ERROR_FILE;
		}
		return filename;
	}

	private static String generateBarChart(Chart oChart,List oChartDataList, HttpSession session,
			PrintWriter pw) {
		String caption = (oChart.getCaption() == null ? DefaultChartSetting.caption
				: oChart.getCaption());
		String xname = (oChart.getColDim() == null ? DefaultChartSetting.ColDim.name
				: oChart.getColDim().name);
		String yname = (oChart.getRowDim() == null ? DefaultChartSetting.rowDim.name
				: oChart.getRowDim().name);
		// String
		// filename=(oChart.getFileName()==null?DefaultChartSetting.filename:oChart.getFileName());
		Font titleFont = DefaultChartSetting.titleFont;
		boolean legend = oChart.getLegend() == null ? DefaultChartSetting.legend
				: (oChart.getLegend().equalsIgnoreCase("true") ? true : false);

		int[] size=new int[2];
		CategoryDataset bardata = ChartDataTransfomer
				.DataToCategoryDataset(oChart,oChartDataList,size);
		int width = oChart.width == null ? ((size[0]*size[1])*DefaultChartSetting.eachwidth+DefaultChartSetting.width) : Integer.parseInt(oChart.getWidth());
		int height = oChart.height == null ? DefaultChartSetting.height
				: Integer.parseInt(oChart.getHeight());
		String filename = null;
		// String unitSytle = "{0}={1}({2})";
		try {
			JFreeChart chart;
			if (oChart.getType().equalsIgnoreCase(
					DefaultChartSetting.CHART_TYPE_BAR_3D)) {
				chart = ChartFactory.createBarChart3D(caption, // chart title
						xname, yname, bardata, // data
						PlotOrientation.VERTICAL, legend, false, false);
			} else {
				chart = ChartFactory.createBarChart(caption, // chart title

						xname, yname, bardata, // data
						PlotOrientation.VERTICAL, legend, false, false);
			}
			// 设置图片标题的字体和大小
			if (caption != null) {
				TextTitle _title = new TextTitle(caption);
				_title.setFont(titleFont);
				chart.setTitle(_title);
			}
			chart.setBackgroundPaint(DefaultChartSetting.backgroundcolor);
			CategoryPlot plot = chart.getCategoryPlot();
			CategoryAxis domainAxis = plot.getDomainAxis();
			domainAxis.setAxisLineVisible(true);
			domainAxis.setTickLabelsVisible(true);
			plot.setDomainAxis(domainAxis);
			ValueAxis rangeAxis = plot.getRangeAxis();
			// 设置最高的一个 Item 与图片顶端的距离
			rangeAxis.setUpperMargin(0.15);
			// 设置最低的一个 Item 与图片底端的距离
			rangeAxis.setLowerMargin(0.15);
			plot.setRangeAxis(rangeAxis);
			BarRenderer3D renderer = new BarRenderer3D();
			renderer.setBaseOutlinePaint(Color.BLACK);
			// 设置 Wall 的颜色

			renderer.setWallPaint(Color.gray);
			// 设置每种柱的颜色
			renderer
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			// 设置每个x所包含的平行柱的之间距离

			renderer.setItemMargin(0.1);
			// 显示每个柱的数值，并修改该数值的字体属性<BR>
			renderer
					.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			plot.setRenderer(renderer);
			// 设置柱的透明度<BR>
			plot.setForegroundAlpha(0.65f);
			// 设置x、y的显示位置

			plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
			plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

			// 把生成的图片放到临时目录
			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());

			// 设置图片名称前缀
			ServletUtilities.setTempFilePrefix("chart-");

			// 图片长度，图片高度

			filename = ServletUtilities.saveChartAsPNG(chart, width, height,
					info, session);
			ChartUtilities.writeImageMap(pw, filename, info, false);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			filename = "public_error_600x350.png";
		}
		return filename;
	}

	private static String generateLineChart(Chart oChart,List oChartDataList, HttpSession session,
			PrintWriter pw) {
		String caption = (oChart.getCaption() == null ? DefaultChartSetting.caption
				: oChart.getCaption());
		String xname = (oChart.getColDim() == null ? DefaultChartSetting.ColDim.name
				: oChart.getColDim().name);
		String yname = (oChart.getRowDim() == null ? DefaultChartSetting.rowDim.name
				: oChart.getRowDim().name);
		// String
		// filename=(oChart.getFileName()==null?DefaultChartSetting.filename:oChart.getFileName());
		Font titleFont = DefaultChartSetting.titleFont;
		boolean legend = oChart.getLegend() == null ? DefaultChartSetting.legend
				: (oChart.getLegend().equalsIgnoreCase("true") ? true : false);

		int[] size=new int[2];
		CategoryDataset bardata = ChartDataTransfomer
				.DataToCategoryDataset(oChart,oChartDataList,size);
		int width = oChart.width == null ? ((size[0]*size[1])*DefaultChartSetting.eachwidth+DefaultChartSetting.width): Integer.parseInt(oChart.getWidth());
		int height = oChart.height == null ? DefaultChartSetting.height
				: Integer.parseInt(oChart.getHeight());
		String filename = null;
		// String unitSytle = "{0}={1}({2})";
		try {
			JFreeChart chart;
			if (oChart.getType().equalsIgnoreCase(
					DefaultChartSetting.CHART_TYPE_CURVE_3D)) {
				chart = ChartFactory.createLineChart3D(caption, // chart title
						xname, // x-label
						yname, // y-label
						bardata, // data
						PlotOrientation.VERTICAL, legend, true, false);
			} else {
				chart = ChartFactory.createLineChart(caption, // chart title
						xname, // x-label
						yname, // y-label
						bardata, // data
						PlotOrientation.VERTICAL, legend, true, false);
			}
			// 设置图片标题的字体和大小
			if (caption != null) {
				TextTitle _title = new TextTitle(caption);
				_title.setFont(titleFont);
				chart.setTitle(_title);
			}
			chart.setBackgroundPaint(DefaultChartSetting.backgroundcolor);
			CategoryPlot polt = (CategoryPlot) chart.getPlot();
			polt.setBackgroundPaint(DefaultChartSetting.backgroundcolor);
			// polt.setBackgroundPaint(new GradientPaint(0.0F, 0.0F,
			// Color.white, 1000F, 0.0F, Color.gray));

			polt.setRangeGridlinePaint(Color.gray);
			NumberAxis numberaxis = (NumberAxis) polt.getRangeAxis();
			numberaxis
					.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			// 设置最高的一个 Item 与图片顶端的距离
			numberaxis.setUpperMargin(0.15);
			// 设置最低的一个 Item 与图片底端的距离
			numberaxis.setLowerMargin(0.15);
			// 具体每条线的画法
			LineAndShapeRenderer renderer = (LineAndShapeRenderer) polt
					.getRenderer();
			renderer
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			renderer.setShapesVisible(true);
			renderer.setDrawOutlines(true);
			renderer.setUseFillPaint(true);
			renderer.setItemLabelsVisible(true);
			renderer
					.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setFillPaint(Color.white);
			// 线每点显示字体

			renderer.setItemLabelFont(titleFont);

			// 把生成的图片放到临时目录
			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());

			// 设置图片名称前缀
			ServletUtilities.setTempFilePrefix("chart-");

			// 图片长度，图片高度

			filename = ServletUtilities.saveChartAsPNG(chart, width, height,
					info, session);

			ChartUtilities.writeImageMap(pw, filename, info, false);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			filename = "public_error_600x350.png";
		}
		return filename;
	}

	private static String generateLineChart(String oFileName,Chart oChart,List oChartDataList) {
		String caption = (oChart.getCaption() == null ? DefaultChartSetting.caption
				: oChart.getCaption());
		String xname = (oChart.getColDim() == null ? DefaultChartSetting.ColDim.name
				: oChart.getColDim().name);
		String yname = (oChart.getRowDim() == null ? DefaultChartSetting.rowDim.name
				: oChart.getRowDim().name);
		String filename = (oFileName == null ? DefaultChartSetting.filename
				: oFileName);
		Font titleFont = DefaultChartSetting.titleFont;
		boolean legend = oChart.getLegend() == null ? DefaultChartSetting.legend
				: (oChart.getLegend().equalsIgnoreCase("true") ? true : false);

		int[] size=new int[2];
		CategoryDataset bardata = ChartDataTransfomer
				.DataToCategoryDataset(oChart,oChartDataList,size);
		int width = oChart.width == null ? ((size[0]*size[1])*DefaultChartSetting.eachwidth+DefaultChartSetting.width): Integer.parseInt(oChart.getWidth());
		int height = oChart.height == null ? DefaultChartSetting.height
				: Integer.parseInt(oChart.getHeight());
		
		// String filename = null;
		// String unitSytle = "{0}={1}({2})";
		try {
			JFreeChart chart;
			if (oChart.getType().equalsIgnoreCase(
					DefaultChartSetting.CHART_TYPE_CURVE_3D)) {
				chart = ChartFactory.createLineChart3D(caption, // chart title
						xname, // x-label
						yname, // y-label
						bardata, // data
						PlotOrientation.VERTICAL, legend, true, false);
			} else {
				chart = ChartFactory.createLineChart(caption, // chart title
						xname, // x-label
						yname, // y-label
						bardata, // data
						PlotOrientation.VERTICAL, legend, true, false);
			}
			// 设置图片标题的字体和大小
			if (caption != null) {
				TextTitle _title = new TextTitle(caption);
				_title.setFont(titleFont);
				chart.setTitle(_title);
			}
			chart.setBackgroundPaint(DefaultChartSetting.backgroundcolor);
			CategoryPlot polt = (CategoryPlot) chart.getPlot();
			polt.setBackgroundPaint(DefaultChartSetting.backgroundcolor);
			// polt.setBackgroundPaint(new GradientPaint(0.0F, 0.0F,
			// Color.white, 1000F, 0.0F, Color.gray));
			polt.setRangeGridlinePaint(Color.gray);
			NumberAxis numberaxis = (NumberAxis) polt.getRangeAxis();
			numberaxis
					.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			// 设置最高的一个 Item 与图片顶端的距离
			numberaxis.setUpperMargin(0.15);
			// 设置最低的一个 Item 与图片底端的距离
			numberaxis.setLowerMargin(0.15);
			// 具体每条线的画法
			LineAndShapeRenderer renderer = (LineAndShapeRenderer) polt
					.getRenderer();
			renderer
					.setToolTipGenerator(new StandardCategoryToolTipGenerator());
			renderer.setShapesVisible(true);
			renderer.setDrawOutlines(true);
			renderer.setUseFillPaint(true);
			renderer.setItemLabelsVisible(true);
			renderer
					.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setFillPaint(Color.white);
			// 线每点显示字体

			renderer.setItemLabelFont(titleFont);
			File file = new File(filename);
			ChartUtilities.saveChartAsPNG(file, chart, width, height);
			filename="<image src='./"+filename.substring(filename.lastIndexOf("\\")+1)+"' width="+width+" height="+height+"/>";
		} catch (Exception e) {
			e.printStackTrace();
			filename = DefaultChartSetting.ERROR_FILE;
		}
		return filename;
	}

	private static String generatePieChart(Chart oChart,List oChartDataList, HttpSession session,
			PrintWriter pw) {
		String caption = (oChart.getCaption() == null ? DefaultChartSetting.caption
				: oChart.getCaption());
		// String
		// xname=(oChart.getColDim()==null?DefaultChartSetting.ColDim.name:oChart.getColDim().name);
		// String
		// yname=(oChart.getRowDim()==null?DefaultChartSetting.rowDim.name:oChart.getRowDim().name);
		// String
		// filename=(oChart.getFileName()==null?DefaultChartSetting.filename:oChart.getFileName());
		Font titleFont = DefaultChartSetting.titleFont;
		boolean legend = oChart.getLegend() == null ? DefaultChartSetting.legend
				: (oChart.getLegend().equalsIgnoreCase("true") ? true : false);

		int width = oChart.width == null ? DefaultChartSetting.width: Integer
				.parseInt(oChart.getWidth());
		int height = oChart.height == null ? DefaultChartSetting.height
				: Integer.parseInt(oChart.getHeight());
		PieDataset piedata = ChartDataTransfomer.DataToPieDataset(oChart,oChartDataList);

		String filename = null;
		String unitSytle = "{0}={1}({2})";
		try {
			JFreeChart chart;
			if (oChart.getType().equalsIgnoreCase(
					DefaultChartSetting.CHART_TYPE_PIE_3D)) {
				chart = ChartFactory.createPieChart3D(caption, // chart title
						piedata, // data
						legend, // include legend
						false, false);
			} else {
				chart = ChartFactory.createPieChart(caption, // chart title
						piedata, // data
						legend, // include legend
						false, false);
			}
			// 设置图片的背景色

			chart.setBackgroundPaint(DefaultChartSetting.backgroundcolor);
			// 设置透明度，好像对servlet没有用

			chart.setBackgroundImageAlpha(0.5f);

			// 设置图片标题的字体和大小
			if (caption != null) {
				TextTitle _title = new TextTitle(caption);
				_title.setFont(titleFont);
				chart.setTitle(_title);
			}
			PiePlot plot = (PiePlot) chart.getPlot();

			plot.setNoDataMessage("无对应的数据，请重新查询。");
			plot.setNoDataMessagePaint(Color.red);

			// 指定 section 轮廓线的厚度(OutlinePaint不能为null)
			plot.setOutlineStroke(new BasicStroke(0));

			// 设置第一个 section 的开始位置，默认是12点钟方向
			plot.setStartAngle(90);
			plot.setToolTipGenerator(new StandardPieToolTipGenerator(
					unitSytle, NumberFormat.getNumberInstance(),
					new DecimalFormat("0.00%")));

			// 指定图片的透明度
			if (oChart.getType().equalsIgnoreCase(
					DefaultChartSetting.CHART_TYPE_PIE_3D)) {
				plot.setForegroundAlpha(0.65f);
			}

			// 引出标签显示样式
			plot.setToolTipGenerator(new StandardPieToolTipGenerator(unitSytle,
					NumberFormat.getNumberInstance(),
					new DecimalFormat("0.00%")));

			// 图例显示样式
			plot.setToolTipGenerator(new StandardPieToolTipGenerator(
					unitSytle, NumberFormat.getNumberInstance(),
					new DecimalFormat("0.00%")));

			// 把生成的图片放到临时目录
			ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());

			// 设置图片名称前缀
			ServletUtilities.setTempFilePrefix("chart-");

			// 图片长度，图片高度

			filename = ServletUtilities.saveChartAsPNG(chart, width, height,
					info, session);

			ChartUtilities.writeImageMap(pw, filename, info, false);
			pw.flush();

		} catch (Exception e) {
			filename = "public_error_600x350.png";
		}
		return filename;
	}

	private static String generatePieChart(String oFileName,Chart oChart,List oChartDataList) {
		String caption = (oChart.getCaption() == null ? DefaultChartSetting.caption
				: oChart.getCaption());
		// String
		// xname=(oChart.getColDim()==null?DefaultChartSetting.ColDim.name:oChart.getColDim().name);
		// String
		// yname=(oChart.getRowDim()==null?DefaultChartSetting.rowDim.name:oChart.getRowDim().name);
		String filename = (oFileName == null ? DefaultChartSetting.filename
				: oFileName);
		Font titleFont = DefaultChartSetting.titleFont;
		boolean legend = oChart.getLegend() == null ? DefaultChartSetting.legend
				: (oChart.getLegend().equalsIgnoreCase("true") ? true : false);

		int width = oChart.width == null ? DefaultChartSetting.width : Integer
				.parseInt(oChart.getWidth());
		int height = oChart.height == null ? DefaultChartSetting.height
				: Integer.parseInt(oChart.getHeight());
		PieDataset piedata = ChartDataTransfomer.DataToPieDataset(oChart,oChartDataList);

		// String filename = null;
		String unitSytle = "{0}={1}({2})";
		try {
			JFreeChart chart;
			if (oChart.getType().equalsIgnoreCase(
					DefaultChartSetting.CHART_TYPE_PIE_3D)) {
				chart = ChartFactory.createPieChart3D(caption, // chart title
						piedata, // data
						legend, // include legend
						false, false);
			} else {
				chart = ChartFactory.createPieChart(caption, // chart title
						piedata, // data
						legend, // include legend
						false, false);
			}
			// 设置图片的背景色

			chart.setBackgroundPaint(DefaultChartSetting.backgroundcolor);
			// 设置透明度，好像对servlet没有用

			chart.setBackgroundImageAlpha(0.5f);

			// 设置图片标题的字体和大小
			if (caption != null) {
				TextTitle _title = new TextTitle(caption);
				_title.setFont(titleFont);
				chart.setTitle(_title);
			}
			PiePlot plot = (PiePlot) chart.getPlot();

			plot.setNoDataMessage("无对应的数据，请重新查询。");
			plot.setNoDataMessagePaint(Color.red);

			// 指定 section 轮廓线的厚度(OutlinePaint不能为null)
			plot.setOutlineStroke(new BasicStroke(0));

			// 设置第一个 section 的开始位置，默认是12点钟方向  plot.setToolTipGenerator(new StandardPieToolTipGenerator(
			plot.setStartAngle(90);
			plot.setToolTipGenerator(new StandardPieToolTipGenerator(
					unitSytle, NumberFormat.getNumberInstance(),
					new DecimalFormat("0.00%")));

			// 指定图片的透明度
			if (oChart.getType().equalsIgnoreCase(
					DefaultChartSetting.CHART_TYPE_PIE_3D)) {
				plot.setForegroundAlpha(0.65f);
			}
			// 引出标签显示样式
			plot.setToolTipGenerator(new StandardPieToolTipGenerator(unitSytle,
					
					NumberFormat.getNumberInstance(),
					new DecimalFormat("0.00%")));

			// 图例显示样式
			plot.setToolTipGenerator(new StandardPieToolTipGenerator(
					unitSytle, NumberFormat.getNumberInstance(),
					new DecimalFormat("0.00%")));

			File file = new File(filename);
			ChartUtilities.saveChartAsPNG(file, chart, width, height);
			filename="<image src='./"+filename.substring(filename.lastIndexOf("\\")+1)+"' width="+width+" height="+height+"/>";
		} catch (Exception e) {
			e.printStackTrace();
			filename = DefaultChartSetting.ERROR_FILE;
		}
		return filename;
	}

	public static void main(String[] args) {
		//System.out.println(ChartDataTransfomer.getInBracketStr("{asdsa} sds \n"));
		/*
		Chart oChart = new Chart();
		oChart.setCaption("题头");
		oChart.setHeight("300");
		oChart.setLegend("true");
		//oChart.setWidth("100");

		Dim oRowDim = new Dim();
		oRowDim.name = "收入";
		oRowDim.dimValue = "rowcode";
		Dim oColDim = new Dim();
		oColDim.name = "col名称";
		oColDim.dimValue = "colcode";
		Measure oMeasure = new Measure();
		oMeasure.name = "收入（元）";
		oMeasure.measureValue = "MeasureCode";
		oChart.setColDim(oColDim);
		oChart.setRowDim(oRowDim);
		oChart.setMeasures(oMeasure);

		List ochartdataList = new ArrayList();

		HashMap oHashMap1 = new HashMap();
		oHashMap1.put("rowcode", "地市1");
		oHashMap1.put("colcode", "收入1");
		oHashMap1.put("MeasureCode", new Integer(12));
		ochartdataList.add(oHashMap1);

		HashMap oHashMap2 = new HashMap();
		oHashMap2.put("rowcode", "地市1");
		oHashMap2.put("colcode", "收入2");
		oHashMap2.put("MeasureCode", "10");
		ochartdataList.add(oHashMap2);

		HashMap oHashMap4 = new HashMap();
		oHashMap4.put("rowcode", "地市2");
		oHashMap4.put("colcode", "收入1");
		oHashMap4.put("MeasureCode", "17");
		ochartdataList.add(oHashMap4);

		HashMap oHashMap3 = new HashMap();
		oHashMap3.put("rowcode", "地市2");
		oHashMap3.put("colcode", "收入2");
		oHashMap3.put("MeasureCode", "11");
		ochartdataList.add(oHashMap3);

		HashMap oHashMap5 = new HashMap();
		oHashMap5.put("rowcode", "地市1");
		oHashMap5.put("colcode", "收入2");
		oHashMap5.put("MeasureCode", "11");
		ochartdataList.add(oHashMap5);

		HashMap oHashMap6 = new HashMap();
		oHashMap6.put("rowcode", "地市1");
		oHashMap6.put("colcode", "收入1");
		oHashMap6.put("MeasureCode", new Integer(12));
		ochartdataList.add(oHashMap6);

		HashMap oHashMap7 = new HashMap();
		oHashMap7.put("rowcode", "地市3");
		oHashMap7.put("colcode", "收入1");
		oHashMap7.put("MeasureCode", new Integer(30));
		ochartdataList.add(oHashMap7);
		
		HashMap oHashMap8 = new HashMap();
		oHashMap8.put("rowcode", "地市3");
		oHashMap8.put("colcode", "收入2");
		oHashMap8.put("MeasureCode", new Integer(30));
		ochartdataList.add(oHashMap8);
		
        for(int i=0;i<200;i++){
    		HashMap oHashMap = new HashMap();
    		oHashMap.put("rowcode", "地市"+(i % 16));
    		oHashMap.put("colcode", "收入"+(i % 6));
    		oHashMap.put("MeasureCode", new Integer(20+i % 30));
    		ochartdataList.add(oHashMap);
        }

		oChart.setType(DefaultChartSetting.CHART_TYPE_CURVE_3D);
		System.out.println(ChartGenerator.generateChart("c:\\testfile\\TEST1线性图_3D.png",oChart,ochartdataList));

		oChart.setType(DefaultChartSetting.CHART_TYPE_CURVE);
		System.out.println(ChartGenerator.generateChart("c:\\testfile\\TEST1线性图.png",oChart,ochartdataList));

		oChart.setType(DefaultChartSetting.CHART_TYPE_BAR);
		System.out.println(ChartGenerator.generateChart("c:\\testfile\\TEST1柱状图.png",oChart,ochartdataList));

		oChart.setType(DefaultChartSetting.CHART_TYPE_BAR_3D);
		System.out.println(ChartGenerator.generateChart("c:\\testfile\\TEST1柱状图_3D.png",oChart,ochartdataList));


		oChart.setType(DefaultChartSetting.CHART_TYPE_PIE);
		System.out.println(ChartGenerator.generateChart("c:\\testfile\\TEST1饼状图.png",oChart,ochartdataList));


		oChart.setType(DefaultChartSetting.CHART_TYPE_PIE_3D);
		System.out.println(ChartGenerator.generateChart("c:\\testfile\\TEST1饼状图_3D.png",oChart,ochartdataList));
		*/
	}
}
