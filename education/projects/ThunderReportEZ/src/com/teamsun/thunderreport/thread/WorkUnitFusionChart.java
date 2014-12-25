package com.teamsun.thunderreport.thread;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.teamsun.framework.util.RegExUtil;
import com.teamsun.thunderreport.parse.Condition;
import com.teamsun.thunderreport.parse.DataService;
import com.teamsun.thunderreport.parse.FusionChartEx;
import com.teamsun.thunderreport.parse.FusionChartFactory;
import com.teamsun.thunderreport.parse.FusionLineChart;
import com.teamsun.thunderreport.parse.FusionLineChartEx;
import com.teamsun.thunderreport.parse.Sql;
import com.teamsun.thunderreport.parse.XmlParser;
import com.teamsun.thunderreport.thread.support.ConfigurationFileService;
import com.teamsun.thunderreport.velocity.TempletBuilder;
import com.teamsun.thunderreport.velocity.TempletBuilder.VelocityContextService;
import com.teamsun.thunderreport.util.ApplicationPro;
import com.teamsun.thunderreport.util.BeanLoader;

public class WorkUnitFusionChart {

	private static final Log log = LogFactory.getLog(WorkUnitFusionChart.class);
	/**
	 * 配置文件服务接口，通过此接口获取所有报表配置文件
	 * 
	 * 
	 * 
	 */
	private ConfigurationFileService fileService;


	/**
	 * 解析文件名
	 * 
	 * 
	 * 
	 * 
	 * @return
	 */
	private String[] parserFilename(String filename, Condition[] conditions) {
		String f = filename.replaceAll("^r_", "").replaceAll("__$", "");
		String[] result = f.split("_");
		if (result == null || result.length == 0 || conditions == null) {
			return new String[] { f };
		}
		if (result.length < conditions.length) {
			String[] res = new String[conditions.length];
			System.arraycopy(result, 0, res, 0, result.length);
			return res;
		}
		return result;
	}

	public Condition[] getConditions(String file) {
		File f = this.fileService.getFileByName(file);
		if (f == null) {
			return null;
		}

		XmlParser xmlParser = new XmlParser(f.getAbsolutePath());
		// 获取所有的条件信息
		Condition[] conditions = xmlParser.getCondition();
		return conditions;
	}

	private File checkFile(String file) {
		File f = this.fileService.getFileByName(file);
		if (f == null) {
			log.error("[" + file + "]对应的配置文件不存在");
			return null;
		}
		return f;
	}

	/**
	 * 返回页面布局
	 * 
	 * 目前只处理一个fusionchart对应一个xml的情况，如果将来出现一个页面有多个图形的情况，
	 * 可以考虑将图形layout独立。建立新的vm文件，只负责图形layout，全部生成后再拼装到html页面中。
	 * 
	 * @param file
	 * @return
	 */
	public String getResponseLayout(String file, Map conds) {
		File f = this.checkFile(file);
		if (f == null)
			return "";

		XmlParser xmlParser = new XmlParser(f.getAbsolutePath(), file);
		FusionLineChart flc = xmlParser.getFusionLineChart(conds);
		TempletBuilder builder = TempletBuilder.getInstancce();
		Iterator it = flc.getFusionChartExs().iterator();
		while (it.hasNext()) {
			final FusionLineChartEx fex = (FusionLineChartEx) it.next();

			if (fex.selected()) {
				return builder.getFusionChartLayout(fex.getLayoutFileName(),
						new VelocityContextService() {

							public void setVelocityContextProperties(
									VelocityContext context) {
								context.put("selected", new Boolean(fex
										.selected()));
								context.put("sumselected", new Boolean(fex
										.sumselected()));
								context.put("chartid", fex.getChartid());
								context.put("checkbox",
										replaceFusionChartLayout(fex));
								context
										.put(
												"url",
												ApplicationPro
														.getValue("application.serverurl")
														+ "ServletFusionxmlService?index=2&chartid="
														+ fex.getChartid()
														+ "&file="
														+ fex.getFilename()
														+ "&" + fex.getParams());
							}

						});

			} else {
				return builder.getFusionChartLayout(fex.getLayoutFileName(),
						new VelocityContextService() {

							public void setVelocityContextProperties(
									VelocityContext context) {
								context.put("selected", new Boolean(false));
								context.put("sumselected", new Boolean(false));
								context.put("chartid", fex.getChartid());
								context.put("checkbox", "");
								context
										.put(
												"url",
												ApplicationPro
														.getValue("application.serverurl")
														+ "ServletFusionxmlService?index=2&chartid="
														+ fex.getChartid()
														+ "&file="
														+ fex.getFilename()
														+ "&" + fex.getParams());
							}

						});
			}
		}

		return "";
	}

	public String replaceFusionChartLayout(FusionLineChartEx fex) {

		String c_name = "checkbox_";
		String html = "";
		
		for (int i = 0; i < fex.selectFieldValues().length; i++) {
		  
			html += "<input type=\"checkbox\" disabled name=\"" + c_name
					+ fex.getChartid() + "\" value=\""
					+ fex.selectFieldValues()[i] + "\" text=\""
					+ fex.selectFieldDisplays()[i]
					+ "\" checked=\"true\" onclick=\"javascript:updateChart('"
					+ fex.getChartid() + "','" + c_name + fex.getChartid()
					+ "','" + ApplicationPro.getValue("application.serverurl")
					+ "ServletFusionxmlService?file=" + fex.getFilename()
					+ "&chartid=" + fex.getChartid() + "&index=2&"
					+ fex.getParamsExceptCheckedReference() + "','"
					+ fex.selectConditionValueName() + "'" + ",'"
					+ fex.selectConditionDisplayName() + "');\">"
					+ fex.selectFieldDisplays()[i] + "</input>\n";
			
		}
	
		return html;
	}

	/**
	 * 返回fusion chart xml数据
	 * 
	 * @param file
	 * @param chartid
	 * @param conds
	 * @return
	 */
	public String getResponseXml(final String file, String chartid, Map conds) {

		File f = this.checkFile(file);
		if (f == null)
			return "";

		XmlParser xmlParser = new XmlParser(f.getAbsolutePath(), file);
		final Condition[] conditions = xmlParser.getCondition();

		List dl = new ArrayList();
		if (conditions != null && conds != null)
			for (int k = 0; k < conditions.length; k++) {
				List lp = new ArrayList();
				if (conds.containsKey(conditions[k].getParaname().trim())) {
					lp.add((String) conds.get(conditions[k].getParaname()
							.trim()));
					dl.add(lp);
				}
			}

		final List filenames = xmlParser.generateFileNameList(dl);
		final String[] values = this.parserFilename(
				filenames.get(0).toString(), conditions);

		FusionChartFactory flc = xmlParser.getFusionChartFactory(conds);

		if (!flc.getFusionChartEx(chartid).selected()) {
			// 图形非复选的时候 参数重新编写，遇到,xx,yy,=> 9999

			for (int i = 0; i < values.length; i++) {
				if (values[i].split("\\,").length > 1) {
					if(!conditions[i].getParaname().equals("Latn_Comp_Id")){
					values[i] = "99999";
					}
				}
			}

			log.info("图形非复选，修改后的参数："
					+ flc.getFusionChartEx(chartid).getCondsValues());
		} else {
			// 图形复选的时候，要判断复选参数。除了复选参数外，其他的参数(全部，就是xx,yy这种格式)要换成 99999

			FusionChartEx charEx = flc.getFusionChartEx(chartid);
			for (int i = 0; i < values.length; i++) {

				Condition c = conditions[i];
				if (!c.getParaname()
						.equals(charEx.selectConditionDisplayName())
						&& !c.getParaname().equals(
								charEx.selectConditionValueName())) {
					if(values[i]!=null){
						if (values[i].split("\\,").length > 1) {
							values[i] = "99999";
						}
					}
				}
			}

		}

		return flc.getFusionChartEx(chartid).formateFusionChartXml(
				new DataService() {

					public List doData(Sql idSql) {
						String sql = replaceSQL(conditions, values, idSql
								.getSqlText());
					//	System.out.println("sql:" + sql);
					//	System.out.println("db:" + idSql.db);
						long d = System.currentTimeMillis();
						WorkUnitFusionChart workUnitFusionChart = new WorkUnitFusionChart();
						String id=idSql.getId();
						String db=idSql.getDb();
						if(id==null||id.equals("")){
							if(db==null||db.equals(""))
								id="template_teradata";
							else
								id=db;
						}
						JdbcTemplate jdbcTemplate = (JdbcTemplate) BeanLoader
						.getBean(id);
						
						
						List data = jdbcTemplate.queryForList(sql);
						
						return data;
						}
						
					}

				);
	}

	/**
	 * 用给定的参数类替换sql语句中的变量，生成真正的sql语句
	 * 
	 * @param cons
	 * @param values
	 * @param oldSql
	 * @return 实际的sql语句
	 */
	private String replaceSQL(Condition[] cons, String[] values, String oldSql) {

		if (cons == null)
			return oldSql;

		String result = oldSql;

		for (int x = 0; x < cons.length; x++) {

			Condition con = cons[x];
			if (null == con.getNeedvalue() || "".equals(con.getNeedvalue())) {
				if (null == values[x] || "".equals(values[x])) {
					result = result.replaceAll("\\[[^\\[]*#"
							+ con.getParaname() + "#[^]]*\\]", "");
				} else {
					result = result.replaceAll("#" + con.getParaname() + "#",
							values[x].trim());
				}
			} else {
				result = RegExUtil.replaceAllUtil(result, "#"
						+ con.getParaname() + "#", values[x].trim());
			}
		}
		result = result.replaceAll("\\[", "").replaceAll("\\]", "");
		log.info("图形宏：[" + result + "]");
		return result;
	}

	public ConfigurationFileService getFileService() {
		return fileService;
	}

	public void setFileService(ConfigurationFileService fileService) {
		this.fileService = fileService;
	}



}
