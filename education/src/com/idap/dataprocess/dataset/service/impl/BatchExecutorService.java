package com.idap.dataprocess.dataset.service.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Service;

import com.idap.dataprocess.dataset.entity.EDataType;
import com.idap.dataprocess.dataset.service.BatchExecutor;
import com.idap.dataprocess.dataset.service.batch.ParameterSetter;
import com.idap.dataprocess.dataset.service.batch.Setter;
import com.idap.dataprocess.dataset.service.batch.SqlSetter;
import com.idap.dataprocess.definition.entity.DataDefinitionAttr;
import com.idp.pub.utils.DateUtil;

@Service("batchExecutor")
public class BatchExecutorService implements BatchExecutor {

	@Override
	public void batchUpdate(List<Object[]> dataList, ParameterSetter setter) {
		final List<DataDefinitionAttr> attrs = setter.getAttrs();
		String batchSql = createInsertSql(setter);
		logger.info("batchSql:" + batchSql);
		logger.info(dataList);
		jdbcTemplate.batchUpdate(batchSql, dataList, setter.batchSize(),
				new ParameterizedPreparedStatementSetter<Object[]>() {
					@Override
					public void setValues(PreparedStatement ps, Object[] values)
							throws SQLException {
						for (int index = 0; index < values.length; index++) {
							EDataType type = EDataType.transfer(attrs
									.get(index).getDataType());
							// logger.info("DATATYPE: " + type);
							int ordinal = type.getIndex();
							switch (ordinal) {
							case 0:
								ps.setObject(index + 1, values[index]);
								break;
							case 1:
								Double val=null;
								if(StringUtils.isNotBlank((CharSequence) values[index])){
									val=Double.parseDouble((String) values[index]);
								}
								ps.setObject(index + 1, val);
								break;
							case 2:
								try {
									Date date =null;
									if(StringUtils.isNotBlank((CharSequence) values[index])){
										date = DateUtil
												.converlUtilDate(DateUtil
														.strToDate(values[index]
																.toString(), null));
									}
									ps.setDate(index + 1, date);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								break;
							case 3:
								try {
									Date date =null;
									if(StringUtils.isNotBlank((CharSequence) values[index])){
										date = DateUtil
												.converlUtilDate(DateUtil
														.strToDate(values[index]
																.toString(), null));
									}
									ps.setDate(index + 1, date);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								break;
							default:
								ps.setObject(index + 1, values[index]);
								break;
							}
						}
					}
				});
	}

	private void batch(List<Object[]> dataList, String batchSql, Setter setter) {
		logger.info("batchSql:" + batchSql);
		jdbcTemplate.batchUpdate(batchSql, dataList, setter.batchSize(),
				new ParameterizedPreparedStatementSetter<Object[]>() {
					@Override
					public void setValues(PreparedStatement ps,
							final Object[] values) throws SQLException {
						for (int index = 0; index < values.length; index++) {
							ps.setObject(index + 1, values[index]);
						}
					}
				});
	}

	private String createInsertSql(ParameterSetter setter) {
		StringBuffer colNames = new StringBuffer("INSERT INTO "
				+ setter.getTableName() + "(");
		StringBuffer colValues = new StringBuffer(" VALUES(");
		int index = 0;
		for (DataDefinitionAttr attr : setter.getAttrs()) {
			if (index > 0) {
				colNames.append(",");
				colValues.append(",");
			}
			colNames.append(attr.getColumnName());
			colValues.append("?");
			index++;
		}
		StringBuffer result = new StringBuffer(colNames).append(")");
		result.append(colValues).append(")");

		return result.toString();
	}

	private Log logger = LogFactory.getLog(getClass());

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public void batchUpdate(List<Object[]> dataList, SqlSetter setter) {
		this.batch(dataList, setter.getSql(), setter);
	}
}
