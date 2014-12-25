package com.teamsun.thunderreport.thread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.teamsun.thunderreport.core.RepresentContextService;
import com.teamsun.thunderreport.parse.Condition;
import com.teamsun.thunderreport.parse.Layout;
import com.teamsun.thunderreport.core.bean.Page;
import com.teamsun.framework.ui.CrossGrid;
import com.teamsun.framework.ui.CrossGridEx;
import com.teamsun.framework.ui.ListGrid;
import com.teamsun.thunderreport.util.ApplicationPro;
import com.teamsun.thunderreport.util.FileUtil;
import com.teamsun.thunderreport.velocity.TempletBuilder;
import com.teamsun.thunderreport.velocity.TempletBuilder.VelocityContextService;

/**
 * 每一张报表是一个工作单元
 * 
 * 此工作单元确定了查询条件，并且数据全部生成完毕 此单元负责->根据给定的sql查询数据库，将返回的记录拼状html文件，并生成。3个动作在一个线程完成。
 * 
 * 所做操作：1、解析参数值文件名 2、替换sql语句 3、查询数据库 4、分页动作 5、调用
 * 
 */
public class WorkUnit implements ProcessCallBack {

	private static final Log log = LogFactory.getLog(WorkUnit.class);

	/**
	 * 以 _ 来分割所有的参数值
	 */
	private String destfilename;

	private Condition[] conditions;

	private JdbcTemplate jdbcTemplate;

	private RepresentContextService[] contextServices;
	
	private String exportSql="";
	
	private boolean pageFlag=true;

	/**
	 * 保存两个数据库的表名；key=物理数据库表名 value=缓存数据库表名
	 */
	private Map tables;

	/**
	 * 实际目录
	 */
	private String realDir;
	
	/*
	 * XML文件名称
	 */
	private String dir;
	/*
	 * 查询条件
	 */
	public String[] values=null;
	
	private Layout layout;
		

	/**
	 * 解析文件名
	 * 
	 * @return
	 */
	private String[] parserFilename() {

		String f = this.destfilename.replaceAll("^r_", "").replaceAll("_$", "");
		String[] result = f.split("_");
		if (result == null || result.length == 0 || this.conditions == null) {
			return new String[] { f };
		}
		if (result.length < this.conditions.length) {
			String[] res = new String[this.conditions.length];
			System.arraycopy(result, 0, res, 0, result.length);
			return res;
		}
		return result;
	}

	/**
	 * 生成实际的文件：利用FileOutputStream
	 * 
	 */
	private String createRealFile(String dir, String filename, String text) {

		FileOutputStream fos = null;
		try {
			if (!new File(dir).exists())
				new File(dir).mkdirs();
			String absfile = dir + System.getProperty("file.separator")
					+ filename + ".html";
			fos = new FileOutputStream(new File(absfile));
			fos.write(text.getBytes("utf-8"));
			fos.flush();
			log.info("文件创建完毕:--" + absfile);
			return absfile;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "";
	}

	/**
	 * 用给定的参数类替换sql语句中的变量，生成真正的sql语句
	 * 
	 * @param cons
	 * @param values
	 * @param oldSql
	 * @return 实际的sql语句
	 */
	protected String replaceSQL(Condition[] cons, String[] values, String oldSql) {

		if (cons == null)
			return oldSql;

		String result = oldSql;

		if (this.tables != null) {
			Iterator it = this.tables.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();

				result = result.replaceAll("#" + key.trim() + "#",
						(String) this.tables.get(key));
			}
		}

		for (int x = 0; x < cons.length; x++) {
			// TODO 这个con的判断可能有问题
			Condition con = cons[x];
			if (null == con.getDefaultvalue()
					|| "".equals(con.getDefaultvalue())) {
				if (null == values[x] || "".equals(values[x])) {
					result = result.replaceAll("\\[[^\\[]*#"
							+ con.getParaname() + "#[^]]*\\]", "");
				} else {
					result = result.replaceAll("#" + con.getParaname() + "#",
							values[x].trim());
				}
			} else {
				result = result.replaceAll("#" + con.getParaname() + "#",
						values[x].trim());
			}
		}
		result = result.replaceAll("\\[", "").replaceAll("\\]", "");
		log.info("数据库执行SQL：[" + result + "]");
		return result;
	}
	/*
	private void replaceTableTitle(Condition[] cons, String[] values){
		ListGrid lg = (ListGrid)this.contextService;
		String text = lg.getTabletitle().getText();
		for (int x = 0; x < cons.length; x++) {
			
			Condition con = cons[x];
			if (null == con.getDefaultvalue()
					|| "".equals(con.getDefaultvalue())) {
				if (null == values[x] || "".equals(values[x])) {
					text = text.replaceAll("\\[[^\\[]*#"
							+ con.getParaname() + "#[^]]*\\]", "");
				} else {
					text = text.replaceAll("#" + con.getParaname() + "#",
							values[x].trim());
				}
			} else {
				text = text.replaceAll("#" + con.getParaname() + "#",
						values[x].trim());
			}
		}
		lg.getTabletitle().setText(text);	
	}
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setConditions(Condition[] conditions) {
		this.conditions = conditions;
	}

	public void setDestfilename(String destfilename) {
		this.destfilename = destfilename;
	}

	public void setRealDir(String realDir) {
		this.realDir = realDir;
	}
	
	/*
	 * 单个文件查询
	 */
	public String doProcessText(Object param) {
		String returnVal="";
		for(int i=0;i<this.contextServices.length;i++){
			returnVal=createUiService(param,contextServices[i]);
		}
		return returnVal;
	}
	/*
	 * 单个文件查询
	 */
	public String createUiService(Object param,RepresentContextService contextService) {
		Page page = null;
		if (param != null) {
			page = (Page) param;
		}
		//String[] values = this.parserFilename();
		ListGrid lg = (ListGrid)contextService;
		lg.setConditions(this.conditions);
		
		//lg.setParamValues(values);
		//this.replaceTableTitle(this.conditions, values);//替换tabletitle里面的内容			
		
		String sql = this.replaceSQL(this.conditions, values,
				contextService.getSql().getSqlText());
		// 格式化sql语句
		List data = this.jdbcTemplate.queryForList(sql);
		List subData=null;
		int maxDataSize=Integer.parseInt(ApplicationPro
				.getValue("application.maxdatasize"));
		
		int maxExcelDataSize=Integer.parseInt(ApplicationPro
				.getValue("application.maxExcelDataSize"));
		
		if(data.size()==0){
			String html="<span>系统提示：您选择的条件范围内没有数据</span>";
			this.pageFlag=false;
			return html;
		}
		if(data.size()>maxDataSize&&data.size()<maxExcelDataSize){			
			String filename = this.destfilename;
			String text = contextService.formatContext(data,data,values);
			FileUtil.addZip(text, this.realDir,filename,"xls");
			String html=FileUtil.getZipHtmlCode("page/"+this.dir+"/"+this.destfilename);			
			return html;
		}
		if(data.size()<page.getPagesize()||(contextService instanceof CrossGrid)||(contextService instanceof CrossGridEx)){
			this.pageFlag=false;
		}
		
		if(data.size()>maxExcelDataSize){
			int i=0;
			FileUtil fu=new FileUtil(this.realDir,this.destfilename);
			while (true) {
				int upIndex = (i + 1) * maxExcelDataSize;
				if (upIndex > data.size())
					upIndex = data.size();
				subData = data.subList(i * maxExcelDataSize, upIndex);
				String text = contextService.formatContext(subData, data,
						values);
				fu.putFile(text, this.destfilename + i);
				i++;
				if(upIndex==data.size())
					break;
			}
			fu.flush(this.destfilename);		
			String html=FileUtil.getZipHtmlCode("page/"+this.dir+"/"+this.destfilename);			
			return html;
		}
		
		if (page != null) {
			page.setRecordcount(data.size());
			subData = data.subList(page.getPagesize() * (page.getPageindex() - 1),
					page.getIndexpagesize() + page.getPagesize()
							* (page.getPageindex() - 1));
		}
	
		final String text = contextService.formatContext(subData,data,values);
		return this.layout.replace(lg.getId(), text);
		
	}
	
	public String doExport(Object param) {
		String returnVal="";
		for(int i=0;i<this.contextServices.length;i++){
			returnVal=createExportUiService(param,contextServices[i]);
		}
		return returnVal;
		
	}
	public String doExportCvs(){
//		String[] values = this.parserFilename();
		ListGrid lg = (ListGrid)contextServices[0];
		lg.setConditions(this.conditions);
		
		//lg.setParamValues(values);
		//this.replaceTableTitle(this.conditions, values);//替换tabletitle里面的内容			
		
		String sql = this.replaceSQL(this.conditions, values,
				lg.getSql().getSqlText());
		// 格式化sql语句
		List data = this.jdbcTemplate.queryForList(sql);
		List subData=null;
		int maxDataSize=Integer.parseInt(ApplicationPro
				.getValue("application.maxdatasize"));
		
		int maxExcelDataSize=Integer.parseInt(ApplicationPro
				.getValue("application.maxExcelDataSize"));
		String text = ",,"+lg.getReportHeaderOutValue()+"\r\n"+lg.getColNames()+"\r\n";
		if((lg instanceof CrossGrid)||(lg instanceof CrossGridEx)){
			text+=FileUtil.getCsvExportText(data);
		}else{
			text+=FileUtil.getCsvExportText(data,lg.getCols());
		}
		
		if(data.size()>maxDataSize){			
			String filename = this.destfilename;			
			FileUtil.addZip(text, this.realDir,filename,"csv");
			String html=FileUtil.getZipHtmlCode("page/"+this.dir+"/"+filename);			
			return html;
		}else{
			return text;
		}
	}
	
	private String createExportUiService(Object param,RepresentContextService contextService){
		ListGrid lg = (ListGrid)contextService;
		lg.setConditions(this.conditions);
		//lg.setParamValues(values);	
		//this.replaceTableTitle(this.conditions, values);//替换tabletitle里面的内容

		String sql = this.replaceSQL(this.conditions, values,
				contextService.getSql().getSqlText());
		List data = this.jdbcTemplate.queryForList(sql);
		String text = contextService.formatContext(data,data,values);
		text="<table border=\"1\" width=\"100%\">"+text+"</table>";
		return this.layout.replace(lg.getId(), text);
	}
	
	/*预生成*/
	public void doProcess(Object param) throws Exception {
		
		String[] values = this.parserFilename();
		ListGrid lg = (ListGrid)this.contextServices[0];		
		lg.setConditions(this.conditions);
		//lg.setParamValues(values);	
		
		//this.replaceTableTitle(this.conditions, values);//替换tabletitle里面的内容
		
		String sql = this.replaceSQL(this.conditions, values,
				this.contextServices[0].getSql().getSqlText());
		// 格式化sql语句
		List data = this.jdbcTemplate.queryForList(sql);
		
		int maxDataSize=Integer.parseInt(ApplicationPro
				.getValue("application.maxdatasize"));
		int maxExcelDataSize=Integer.parseInt(ApplicationPro
				.getValue("application.maxExcelDataSize"));
		
		if (data.size() == 0)
			return;
		
		//如果数据条数大于1000条则预生成zip的压缩文件,并生成同名的html文件
		if(data.size()>maxDataSize&&data.size()<maxExcelDataSize){			
			String filename = this.destfilename;
			String text = this.contextServices[0].formatContext(data,data,values);
			FileUtil.addZip(text, this.realDir,filename,"xls");
			String html=FileUtil.getZipHtmlCode("page/"+this.dir+"/"+this.destfilename);
			FileUtil.createHtmlFile(this.realDir, this.destfilename);	
			
		}
		
		if(data.size()>maxExcelDataSize){
			int i=0;
			FileUtil fu=new FileUtil(this.realDir,this.destfilename);
			while (true) {
				int upIndex = (i + 1) * maxExcelDataSize;
				if (upIndex > data.size())
					upIndex = data.size();
				List subData = data.subList(i * maxExcelDataSize, upIndex);
				String text = this.contextServices[0].formatContext(subData, data,
						values);
				fu.putFile(text, this.destfilename + i);
				i++;
				if(upIndex==data.size())
					break;
			}
			
			fu.flush(this.destfilename);		
			FileUtil.createHtmlFile(this.realDir, this.destfilename);						
		}
		final Page page=new Page();
		page.setPagesize(Integer.parseInt(ApplicationPro
				.getValue("application.pagesize")));
		
		page.setRecordcount(data.size());				
		for(int i=1;i<=page.getPagecount();i++){
			page.setPageindex(i);
			List pageData = data.subList(page.getPagesize() * (page.getPageindex() - 1),
					page.getIndexpagesize() + page.getPagesize()
							* (page.getPageindex() - 1));
			final String text = this.contextServices[0].formatContext(pageData,data,values);			
			StringWriter sw = TempletBuilder.getInstancce().getTempletText(
					"template3.vm", new VelocityContextService() {
						public void setVelocityContextProperties(
								VelocityContext context) {
							context.put("table", text);
							context.put("parameters", WorkUnit.this.conditions);
							context.put("page", page);
							context.put("file", WorkUnit.this.destfilename);
						}

					});
			
			this.createRealFile(this.realDir, this.destfilename+((i==1)?"":(i+"")), sw.toString());
			/*
			try{
				Thread.sleep(1000*10);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			*/
		}		
		
	}
	
	
	public void setContextService(RepresentContextService[] contextService) {
		this.contextServices = contextService;
	}

	public Map getTables() {
		return tables;
	}

	public void setTables(Map tables) {
		this.tables = tables;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getExportSql() {
		return exportSql;
	}

	public void setExportSql(String exportSql) {
		this.exportSql = exportSql;
	}

	public String[] getValues() {
		return values;
	}

	public void setValues(String[] values) {
		this.values = values;
	}

	public final boolean isPageFlag() {
		return pageFlag;
	}

	public void setPageFlag(boolean pageFlag) {
		this.pageFlag = pageFlag;
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}


}
