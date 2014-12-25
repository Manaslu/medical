package com.teamsun.thunderreport.database;

import org.springframework.jdbc.core.JdbcTemplate;

import com.teamsun.thunderreport.core.bean.BO;
import com.teamsun.thunderreport.util.BeanLoader;

/**
 * 包装 TransformDBSource。通过BO的数据库数据，利用 TransformDBSource 进行数据转换。
 * 
 */
public class TransformDBFactory {

	private TransformDBSource transformDBSource;

	/**
	 * 
	 * @param bos
	 * @return table names
	 */
	public String[] transformDBByBO(BO[] bos) {

		String[] result = new String[bos.length];
		for (int i = 0; i < bos.length; i++) {
			BO bo = bos[i];
			JdbcTemplate template = (JdbcTemplate) BeanLoader.getBean(bo
					.getId());
			if (template == null)
				template = (JdbcTemplate) BeanLoader.getBean(bo.getDatabase());

			result[i] = this.transformDBSource.transformDB(bo.getSqltext(), bo
					.getIndex());

		}
		return result;
	}
}
