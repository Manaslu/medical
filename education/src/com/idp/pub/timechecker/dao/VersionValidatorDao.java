package com.idp.pub.timechecker.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.idp.pub.dao.impl.SmartBaseDao;
import com.idp.pub.entity.BaseEntity;
import com.idp.pub.entity.SmartEntity;
import com.idp.pub.exception.BusinessException;
import com.idp.pub.sqlresolver.service.ISqlPartResolverService;
import com.idp.pub.timechecker.exception.TimeOutException;
import com.idp.pub.utils.StringUtils;

/**
 * 
 * @author panfei
 * 
 */
@Repository("VersionValidatorDao")
public class VersionValidatorDao extends SmartBaseDao<SmartEntity> {

	private static int Type_Char = 0;

	@Resource(name = "sqlResolverService")
	private ISqlPartResolverService sqlPartResolverService;

	public void validateBaseEntitys(BaseEntity... vos) throws BusinessException {
		for (BaseEntity tmpvo : vos) {
			String sql = buildQuerySql(tmpvo.getPKFieldNames(),
					tmpvo.getPrimaryKeys(), tmpvo.getTableName(),
					tmpvo.getTimeStampName(), tmpvo.getTimeStamp(), Type_Char);
			List<?> reslut = this.selectSql(sql);
			if (reslut == null || reslut.size() == 0) {
				throw new TimeOutException("该页面已经被他人修改，请刷新页面，重做业务！");
			}
			tmpvo.setTimeStamp(getNowDateTime());
		}
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	private static String getNowDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(Calendar.getInstance().getTime());
	}

	/**
	 * 拼装sql语句
	 * 
	 * @param pkColNames
	 *            pk的列名
	 * @param pkColValues
	 *            pk的值
	 * @param tableName
	 *            表名
	 * @param tsName
	 *            时间戳列名
	 * @param tsValue
	 *            时间戳值
	 * @return
	 * @throws BusinessException 
	 */
	private String buildQuerySql(String[] pkColNames, String[] pkColValues,
			String tableName, String tsName, String tsValue, int type)
			throws BusinessException {
		if (StringUtils.isEmpty(tableName)) {
			throw new TimeOutException("数据库表名称不能为空！");
		}

		if (StringUtils.isEmpty(tableName)) {
			throw new TimeOutException("时间戳字段值不能为空！");
		}

		StringBuilder appender = new StringBuilder();
		appender.append("select ");
		appender.append(tsName);
		appender.append(" from ");
		appender.append(tableName);
		appender.append(" where ");
		appender.append("(");
		for (int i = 0; i < pkColNames.length; i++) {
			appender.append(pkColNames[i]);
			appender.append("='");
			appender.append(pkColValues[i]);
			appender.append("'");
			if (i != pkColNames.length - 1) {
				appender.append(" and ");
			}
		}
		appender.append(") and (");
		appender.append(tsName);
		appender.append("=");
		appender.append(sqlPartResolverService.createTimeVersionSql(tsValue));
		appender.append(")");
		return appender.toString();
	}

	@Override
	public String getUserDefineNamespace() {
		return null;
	}
}
