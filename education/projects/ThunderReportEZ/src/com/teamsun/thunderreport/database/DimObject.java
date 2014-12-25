package com.teamsun.thunderreport.database;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * 此对象用来描述所有代码表数据 在系统启动前全部加载入内容
 */
public class DimObject implements DimDataService {

	/**
	 * sql
	 */
	private String sqltext;

	/**
	 * 在配置文件中表示的选取数据集的名字
	 */
	private String ref_name;

	private JdbcTemplate template;

	public String getSqltext() {
		return sqltext;
	}

	public void setSqltext(String sqltext) {
		this.sqltext = sqltext;
	}

	public String getRef_name() {
		return ref_name;
	}

	public void setRef_name(String ref_name) {
		this.ref_name = ref_name;
	}

	public JdbcTemplate getTemplate() {
		return template;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public String[][] loadData() {
		String[][] res = null;
		List result = this.template.queryForList(this.sqltext, new Object[] {});
		if (result != null && result.size() > 0) {
			res = new String[2][result.size()];
			for (int i = 0; i < result.size(); i++) {
				Map m = (Map) result.get(i);
				res[0][i] = m.get("ID").toString().trim();
				if (m.containsKey("NAME"))
					res[1][i] = m.get("NAME").toString().trim();
				else
					res[1][i] = "";
			}
		}
		return res;
	}

}
