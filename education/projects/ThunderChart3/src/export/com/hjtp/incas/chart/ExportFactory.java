package com.hjtp.incas.chart;


public   class ExportFactory {

	private static ExportFactory  m_instance = null;

	private ExportFactory() {
	}
	
	public static ExportFactory getInstance()
	{
		if(m_instance == null)
			m_instance = new ExportFactory();
		return m_instance;
	}
	/**
	 * <p>����ַ���,���ļ������ж�</p>
	 * @param type:�����ļ�����
	 * @author qingbao-gao
	 * <p>Date:2010-03-03 AM 09:36</p>
	 */
	public IFusionCharts getFusionCharts(String type) 
	{
		IFusionCharts iFusionCharts = null;
		type=type.trim();
		type=type.toUpperCase();
		if (type.equals("PDF"))
		{
			iFusionCharts = new PdfExpImpl();
		} 
		else 
		if (type.equals("CSV")||type.equals("XLS")) 
		{
			iFusionCharts = new ExcelExpImpl();
		} 
		else 
		if(type.equals("WORD")||type.equals("DOC")||type.equals("RTF"))
		{
			iFusionCharts =new WordExpImpl();
		}
		else 
		if(type.equals("PNG")||type.equals("JPEG")||type.equals("GIF")||type.equals("BMP"))
		{
			iFusionCharts =new ImagesExpImpl();
		}
		else
		{
			iFusionCharts =new ImagesExpImpl();
		}
		return iFusionCharts;
	}

}
