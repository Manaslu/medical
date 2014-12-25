package com.teamsun.thunderreport.thread;

import java.io.File;
import java.io.FilenameFilter;
import java.io.StringWriter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.teamsun.thunderreport.core.RepresentContextService;
import com.teamsun.thunderreport.core.XmlParser;
import com.teamsun.thunderreport.core.bean.BO;
import com.teamsun.thunderreport.parse.Condition;
import com.teamsun.thunderreport.parse.Layout;
import com.teamsun.thunderreport.core.bean.Page;
import com.teamsun.thunderreport.parse.Sql;
import com.teamsun.thunderreport.database.DimDataFactory;
import com.teamsun.thunderreport.database.TransformDBSource;
import com.teamsun.thunderreport.thread.FileCounter.FinallyAction;
import com.teamsun.thunderreport.thread.support.ConfigurationFileService;
import com.teamsun.thunderreport.util.ApplicationPro;
import com.teamsun.thunderreport.util.BeanLoader;
import com.teamsun.thunderreport.util.DescartesUtil;
import com.teamsun.thunderreport.velocity.TempletBuilder;
import com.teamsun.thunderreport.velocity.TempletBuilder.VelocityContextService;

import edu.emory.mathcs.backport.java.util.concurrent.RejectedExecutionException;
import edu.emory.mathcs.backport.java.util.concurrent.atomic.AtomicBoolean;

public class WorkUnitFactory {

	private static Log log = LogFactory.getLog(WorkUnitFactory.class);

	/**
	 * 配置文件提供
	 */
	private ConfigurationFileService fileservice;

	/**
	 * 线程池
	 */
	private ThreadResourcePool resourcePool;

	/**
	 * hsqldb 连接
	 */
	private JdbcTemplate jdbcTemplate;

	/**
	 * 预生成文件存放目录
	 */
	private String realDir;
	

	/**
	 * 预生成数据
	 */
	private TransformDBSource transformDBSource;

	/**
	 * 代码表数据
	 * 
	 */
	private DimDataFactory dimDataFactory;

	/**
	 * 是否在预生成中
	 */
	private AtomicBoolean run = new AtomicBoolean(false);

	/**
	 * 描述当前线程状况
	 */
	private ThreadContentBean threadContentBean;
	
	/**
	 * 线程终止时间
	 * @return
	 */
	public String threadStopTime="18:06:00";
	
	public Thread currentThread=null;

	public boolean isRun() {
		return run.get();
	}
	
	public void startMonitor(){
		Thread checkThread = new Thread(new Runnable() {
			public void run() {
				System.out.println("begin check thread");
				//String time = ApplicationPro.getValue("application.threadInterruptTime");
				String time=WorkUnitFactory.this.threadStopTime;
				Date curDate = new Date();
				String dateStr = (new SimpleDateFormat("yyyyMMdd"))
						.format(curDate);
				time = dateStr + " " + time;
				Date d = null;
				try {
					d = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				log.info("********Monitor Thread start**********");
				while (true) {
					long diff = ((new Date()).getTime() - d.getTime());
					
					if (diff < 0) {						
						try {
							Thread.sleep(1000*30);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}
					/*从8点开始5个小时内不能再次启动预生成的程序*/
					if (diff >= 0&&diff < 5*60*60*1000) {
						if (WorkUnitFactory.this.run.get() == true) {
							
							WorkUnitFactory.this.resourcePool.shutdownNow();
							if(WorkUnitFactory.this.currentThread.isAlive()){
								log.info("*******shutdown generate file thread*************");
								WorkUnitFactory.this.currentThread.stop();
								WorkUnitFactory.this.run.set(false);
							}							
						}	
						try {
							Thread.sleep(1000*30);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}					
				}
			}
		});
		//checkThread.setDaemon(true);
		//checkThread.start();
	}

	/**
	 * 预生成 启动一个新线程执行预生成操作，此线程在处理过程中会被FileCounter阻塞，直到该文件所有的子文件全部生成完毕
	 * 
	 * @param filter
	 */
	public void doAction(final FilenameFilter filter) {

		Thread mainThread=new Thread(new Runnable() {

			public void run() {
				
				try {					
					WorkUnitFactory.this.threadContentBean = new ThreadContentBean();
					WorkUnitFactory.this.threadContentBean.setBegintime(System
							.currentTimeMillis());
					WorkUnitFactory.this.run.set(true);
					// 获取所有预生成配置文件
					File[] files = WorkUnitFactory.this.fileservice
							.listAllPreConfigurationFiles(filter);
					WorkUnitFactory.this.threadContentBean
							.setFilecount(files.length);
					
					
					if (files != null && files.length > 0) {
						for (int j = 0; j < files.length; j++) {						
							File file = files[j];
							WorkUnitFactory.this.threadContentBean
									.setFilename(file.getName());

							// 对每个文件进行解析
							XmlParser parser = new XmlParser(file
									.getAbsolutePath());
							// 得到每个配置文件的参数对象
							Condition[] conditions = parser.getCondition();
							// 得到所有的参数值 list中每个元素也是list，顺序和condition顺序一致
							List params = WorkUnitFactory.this
									.getConditionValues(conditions);
							// 
							// 得到每个实际要生成的文件名 r_ 开头 以_分割每个参数
							List paramfilenames = DescartesUtil
									.generateFileNameList(params, parser
											.getId());

							// 取得每个配置文件的表现层对象
							RepresentContextService[] rcs = parser
									.getRepresentContextService();
							// BO
							BO[] bos = parser.getBo();
							// key= bo里写的表名称 value=实际内存数据库的表名称
							final Map botables = new HashMap();
							for (int i = 0; i < bos.length; i++) {
								BO bo = bos[i];

								if (!BeanLoader.containsBean(bo.getId())) {
									log.error("BO ID=" + bo.getId()
											+ " 在配置文件中不存在！");
									continue;
								}

								JdbcTemplate jdbcTemplate = (JdbcTemplate) BeanLoader
										.getBean(bo.getId());
								WorkUnitFactory.this.transformDBSource
										.setTemplate(jdbcTemplate);
								String realtable = WorkUnitFactory.this.transformDBSource
										.transformDB(replaceBoSql(bo.getSqltext()), bo
												.getIndex());
								botables.put(bo.getTable(), realtable);
							}

							log.info(file.getName() + "内存数据库创建完毕");

							/**
							 * 表示文成的文件数量 当数量为0时操作下一张配置文件，同时缓存数据库清空
							 */
							FileCounter fc = new FileCounter(paramfilenames
									.size());
							WorkUnitFactory.this.threadContentBean
									.setSubfilecount(paramfilenames.size());
							String dir = file.getName().replaceAll(".xml", "");

							for (int i = 0; i < paramfilenames.size(); i++) {
								String paramfilename = paramfilenames.get(i)
										.toString();
								WorkUnit wu = new WorkUnit();
								wu.setTables(botables);
								wu.setContextService(rcs);
								wu.setDestfilename(paramfilename);
								wu.setJdbcTemplate(WorkUnitFactory.this.jdbcTemplate);
								wu.setConditions(conditions);
								wu.setRealDir(WorkUnitFactory.this.realDir
										+ System.getProperty("file.separator")
										+ dir);
								try{
									WorkUnitFactory.this.resourcePool.doProcess(fc,wu);
								}catch(RejectedExecutionException e){
									log.error("报表生成时间限制,线程强行终止！");
									break;
								}
								
							}
							
							
							
							/**
							 * 暂停当前线程 等待文件生成完毕 删除缓存数据库表
							 */
							fc.doCountWith0(new FinallyAction() {

								public void doAction(Object param) {
									transformDBSource
											.dropTables((String[]) botables
													.values().toArray(
															new String[botables
																	.values()
																	.size()]));
								}

							}, null);

							WorkUnitFactory.this.threadContentBean
									.setFinishcount(j);
							
						}
					}

				} finally {					
					WorkUnitFactory.this.run.set(false);
				}
			}

		});
		
		
		this.currentThread=mainThread;
		mainThread.start();		
		
		

	}

	/**
	 * 单个文件查询
	 * 
	 * @param filename
	 * @param conditions
	 */
	public String doAction(String filename, final Map conds,final Page page,final boolean back) {
		File f = this.fileservice.getFileByName(filename);
		if (f == null) {
			return null;
		}
		final XmlParser xmlParser = new XmlParser(f.getAbsolutePath());
		Condition[] conditions = xmlParser.getCondition();
		String[] values=null;
		if (conditions != null && conds != null)
			values=new String[conditions.length];
			for (int k = 0; k < conditions.length; k++) {				
				if (conds.containsKey(conditions[k].getParaname().trim()))
					values[k]=conds.get(conditions[k].getParaname().trim()).toString();
				else
					values[k]=null;
			}

		//List filenames = DescartesUtil.generateFileNameList(dl);
		//if (filenames.size() > 1)
		//	return "";
		
		String dir = filename.replaceAll(".xml", "");
		RepresentContextService[] rcs = xmlParser.getRepresentContextService();
		Layout layout=xmlParser.getLayout();
		Sql sql = rcs[0].getSql();
		String template=rcs[0].getTemplateVm();
		WorkUnit wu = new WorkUnit();
		wu.setConditions(conditions);
		wu.setContextService(rcs);
		wu.setLayout(layout);
		wu.setRealDir(WorkUnitFactory.this.realDir
				+ System.getProperty("file.separator")
				+ dir);
		wu.setDir(dir);
		wu.setDestfilename(getDestFileName(values));
		wu.setValues(values);
		page.setPagesize(Integer.parseInt(ApplicationPro
				.getValue("application.pagesize")));
		String id=sql.getId();
		String db=sql.getDb();
		if(id==null||id.equals("")){
			if(db==null||db.equals(""))
				id="template_teradata";
			else
				id=db;
		}
		wu.setJdbcTemplate((JdbcTemplate) BeanLoader.getBean(id));
		
		final String html = wu.doProcessText(page);
		if(html.indexOf("<script")==0){
			return html;
		}
		final boolean pageFlag=wu.isPageFlag();
		if(template==null||template.equals("")) template="template.vm";
		
		StringWriter sw = TempletBuilder.getInstancce().getTempletText(
				template, new VelocityContextService() {

					public void setVelocityContextProperties(
							VelocityContext context) {
						context.put("table", html);
						context.put("parameters", conds);
						context.put("page", page);
						context.put("file", xmlParser.getFilename());
						context.put("back", new Boolean(back));
						context.put("pageFlag",new Boolean(pageFlag));
					}

				});
		return new String(sw.toString());
	}
	private String getDestFileName(String[] values){
		String str="";
		for(int i=0;i<values.length;i++){
			if(i>0) str+="_";
			str+=values[i];
		}
		return str;
	}
	/*
	 * 单个文件(Excel,cvs)导出
	 */
	public String doExport(String filename, final Map conds,String fileType) {
		File f = this.fileservice.getFileByName(filename);
		if (f == null) {
			return null;
		}
		final XmlParser xmlParser = new XmlParser(f.getAbsolutePath());
		Condition[] conditions = xmlParser.getCondition();
		String[] values=null;
		if (conditions != null && conds != null) {
			values = new String[conditions.length];
			for (int k = 0; k < conditions.length; k++) {
				if (conds.containsKey(conditions[k].getParaname().trim()))
					values[k] = conds.get(conditions[k].getParaname().trim())
							.toString();
				else
					values[k] = null;
			}
		}

		//List filenames = DescartesUtil.generateFileNameList(dl);
		//if (filenames.size() > 1)
		//	return "";
		String dir = filename.replaceAll(".xml", "");
		RepresentContextService[] rcs = xmlParser.getRepresentContextService();
		Layout layout=xmlParser.getLayout();		
		Sql sql = rcs[0].getSql();
		WorkUnit wu = new WorkUnit();
		wu.setConditions(conditions);
		wu.setContextService(rcs);
		wu.setLayout(layout);
		wu.setRealDir(WorkUnitFactory.this.realDir
				+ System.getProperty("file.separator")
				+ dir);
		wu.setDir(dir);
		wu.setDestfilename(getDestFileName(values));	
		wu.setValues(values);
		String id=sql.getId();
		String db=sql.getDb();
		if(id==null||id.equals("")){
			if(db==null||db.equals(""))
				id="template_teradata";
			else
				id=db;
		}
		wu.setJdbcTemplate((JdbcTemplate) BeanLoader.getBean(id));
		String strData = "";
		if(fileType.equals("xls")) 
			strData=wu.doExport(null);
		else
			strData=wu.doExportCvs();

		final String data=strData;
		return data;
	}
	
	/*
	 * url预生成文件
	 */
	public void doAction(final String[] fileid,final Map param) {

		Thread t=new Thread(new Runnable() {

			public void run() {
				
				try {
					WorkUnitFactory.this.threadContentBean = new ThreadContentBean();
					WorkUnitFactory.this.threadContentBean.setBegintime(System
							.currentTimeMillis());
					WorkUnitFactory.this.run.set(true);
					WorkUnitFactory.this.threadContentBean
					.setFilecount(fileid.length);
					Page page=new Page();
					page.setPagesize(Integer.parseInt(ApplicationPro
							.getValue("application.pagesize")));
					// 获取预生成配置文件
					for(int i = 0;i<fileid.length;i++){
					       File file = WorkUnitFactory.this.fileservice
					       .getPreFileByReportId(fileid[i]);
							WorkUnitFactory.this.threadContentBean
									.setFilename(file.getName());

							// 对文件进行解析
							XmlParser parser = new XmlParser(file
									.getAbsolutePath());
							// 得到每个配置文件的参数对象
							Condition[] conditions = parser.getCondition();
							// 得到所有的参数值 list中每个元素也是list，顺序和condition顺序一致
							List params = new ArrayList();
							List dimPara = WorkUnitFactory.this.getConditionValues(conditions);
                            for(int j = 0;j<conditions.length;j++){
                            	Object paraValue=param.get(conditions[j].getParaname());
                            	if(paraValue!=null&&(!paraValue.equals(""))){
                            		List l=new ArrayList();
                            		if(paraValue.toString().indexOf(",")>-1){
                            			String[] paraArr=paraValue.toString().split(",");                            			
                            			for(int k=0;k<paraArr.length;k++){
                            				l.add(paraArr[i]);
                            			}                            			
                            		}else{
                            			l.add(paraValue);
                            		}
                            		params.add(l);
                            	}else{
                            		if(j<dimPara.size()) params.add(dimPara.get(j));
                            	}                            	
                            }
                            
							// 
							// 得到每个实际要生成的文件名 r_ 开头 以_分割每个参数
							List paramfilenames = DescartesUtil
									.generateFileNameList(params, parser
											.getId());

							// 取得每个配置文件的表现层对象
							RepresentContextService[] rcs = parser
									.getRepresentContextService();
							// BO
							BO[] bos = parser.getBo();
							// key= bo里写的表名称 value=实际内存数据库的表名称
							
							final Map botables = new HashMap();
							for (int m = 0; m < bos.length; m++) {
								BO bo = bos[m];

								if (!BeanLoader.containsBean(bo.getId())) {
									log.error("BO ID=" + bo.getId()
											+ " 在配置文件中不存在！");
									continue;
								}

								JdbcTemplate jdbcTemplate = (JdbcTemplate) BeanLoader
										.getBean(bo.getId());
								WorkUnitFactory.this.transformDBSource
										.setTemplate(jdbcTemplate);
								String realtable = WorkUnitFactory.this.transformDBSource
										.transformDB(replaceBoSql(bo.getSqltext()), bo
												.getIndex());
								botables.put(bo.getTable(), realtable);
							}
							
							log.info(file.getName() + "内存数据库创建完毕");

							/**
							 * 表示文成的文件数量 当数量为0时操作下一张配置文件，同时缓存数据库清空

							 */
							FileCounter fc = new FileCounter(paramfilenames
									.size());
							WorkUnitFactory.this.threadContentBean
									.setSubfilecount(paramfilenames.size());
							String dir = file.getName().replaceAll(".xml", "");

							for (int n = 0; n < paramfilenames.size(); n++) {
								String paramfilename = paramfilenames.get(n)
										.toString();
								WorkUnit wu = new WorkUnit();
								wu.setTables(botables);
								wu.setContextService(rcs);
								wu.setDestfilename(paramfilename);
								wu.setJdbcTemplate(WorkUnitFactory.this.jdbcTemplate);
								wu.setConditions(conditions);
								wu.setRealDir(WorkUnitFactory.this.realDir
										+ System.getProperty("file.separator")
										+ dir);
								WorkUnitFactory.this.resourcePool.doProcess(fc,wu);
							}

							/**
							 * 暂停当前线程 等待文件生成完毕 删除缓存数据库表
							 */
							fc.doCountWith0(new FinallyAction() {

								public void doAction(Object param) {
									System.out.println("开始删除表！");
									transformDBSource
											.dropTables((String[]) botables
													.values().toArray(
															new String[botables
																	.values()
																	.size()]));
								}

							}, null);

							//WorkUnitFactory.this.threadContentBean
									//.setFinishcount(j);
						
					

				} 
				}finally {
					WorkUnitFactory.this.run.set(false);
				}
			}			

		});
		this.currentThread=t;
		t.start();

	
	}

	public Condition[] getConditions(String filename) {
		File f = this.fileservice.getFileByName(filename);
		if (f == null) {
			return null;
		}
		XmlParser xmlParser = new XmlParser(f.getAbsolutePath());
		// 获取所有的条件信息
		Condition[] conditions = xmlParser.getCondition();
		return conditions;

	}

	/**
	 * 根据给定的条件生成全部值 默认参数顺序一致
	 * 
	 * 
	 * @param conditions
	 * @return
	 */
	private List getConditionValues(Condition[] conditions) {
		List dl = new ArrayList();
		Condition beginDateCon=null;
		if (conditions != null)
			for (int k = 0; k < conditions.length; k++) {
				List l = new ArrayList();
				if(conditions[k].getRefervalue()!=null&&conditions[k].getRefervalue().equals("begindate")){
					beginDateCon=conditions[k];
					continue;
				}
				if(conditions[k].getRefervalue()!=null&&conditions[k].getRefervalue().equals("enddate")){
					beginDateCon=conditions[k];
					l=getDateDescart(beginDateCon,conditions[k]);
					dl.add(l);
					continue;
				}
				String[] ids = this.dimDataFactory.getIds(conditions[k]
						.getRefervalue());
				for (int i = 0; i < ids.length; i++) {
					l.add(ids[i]);
				}
				dl.add(l);
			}
		return dl;
	}
	private List getDateDescart(Condition beginDateCon,Condition endDateCon){
		String[] beginDate=this.dimDataFactory.getIds(beginDateCon.getRefervalue());
		String[] endDate=this.dimDataFactory.getIds(beginDateCon.getRefervalue());
		List dateList = new ArrayList();
		for(int i=0;i<beginDate.length;i++){
			for(int j=0;j<endDate.length;j++){
				try {
					Date d0 = new SimpleDateFormat("yyyy-MM-dd").parse(beginDate[i]);
					Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate[j]);
					if((d1.getTime()-d0.getTime())>=0){
						dateList.add(beginDate[i]+"_"+beginDate[j]);
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return dateList;
	}
	
	private String replaceBoSql(String sql){
		String minDate=this.dimDataFactory.getMinDate();
		String maxDate=this.dimDataFactory.getMaxDate();
		if(sql.indexOf("#beginday#")>-1){
			sql=sql.replaceAll("#beginday#", minDate);
		}
		if(sql.indexOf("#endday#")>-1){
			sql=sql.replaceAll("#endday#", maxDate);
		}
		return sql;
	}
	
	public boolean freshXml(){
		try{
		dimDataFactory.loadData();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public void setRealDir(String realDir) {
		this.realDir = realDir;
	}

	public void setTransformDBSource(TransformDBSource transformDBSource) {
		this.transformDBSource = transformDBSource;
	}

	public void setDimDataFactory(DimDataFactory dimDataFactory) {
		this.dimDataFactory = dimDataFactory;
	}

	public void setFileservice(ConfigurationFileService fileservice) {
		this.fileservice = fileservice;
	}

	public void setResourcePool(ThreadResourcePool resourcePool) {
		this.resourcePool = resourcePool;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public ThreadContentBean getThreadContentBean() {
		return threadContentBean;
	}

	public static void main(String[] args) {
		System.out.println(System.getProperties());
	}

	public String getThreadStopTime() {
		return threadStopTime;
	}

	public void setThreadStopTime(String threadStopTime) {
		this.threadStopTime = threadStopTime;
	}

}
