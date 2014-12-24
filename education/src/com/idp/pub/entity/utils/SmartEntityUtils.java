package com.idp.pub.entity.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idp.pub.entity.SuperEntity;
import com.idp.pub.entity.annotation.Column;
import com.idp.pub.entity.annotation.ForeignKey;
import com.idp.pub.entity.annotation.MetaTable;
import com.idp.pub.entity.annotation.ParentForeignKey;
import com.idp.pub.entity.annotation.PrimaryKey;
import com.idp.pub.entity.annotation.TimeStamp;
import com.idp.pub.entity.cache.SamrtEntityCache;
import com.idp.pub.utils.StringUtils;

/**
 * 类加载工具
 * 
 * @author panfei
 * 
 */
public class SmartEntityUtils {

	/**
	 * 判断是否为core注解
	 * 
	 * @param checkclass
	 * @return
	 */
	public static boolean isFromTableName(Class<?> checkclass) {
		return checkclass.isAnnotationPresent(MetaTable.class);
	}

	public static Map<String, Object> recordAnnotationInfo(Class<?> checkclass) {
		Map<String, Object> cacheinfo = SamrtEntityCache.getInstance()
				.getValue(checkclass);
		if (cacheinfo != null) {
			return cacheinfo;
		}
		String tableName = null;
		List<String> pkFeildNames = new ArrayList<String>();
		List<ColumnAttribute> pkFeildNamesAttr = new ArrayList<ColumnAttribute>();
		List<String> parentPkFeildNames = new ArrayList<String>();
		List<String> foreginPkFeildNames = new ArrayList<String>();
		List<ColumnAttribute> colFeildNamesAttr = new ArrayList<ColumnAttribute>();

		String ts = null;
		ColumnAttribute tsattr = null;

		if (isFromTableName(checkclass)) {
			MetaTable tbitf = checkclass.getAnnotation(MetaTable.class);
			tableName = tbitf.name();
			Field[] chekfields = checkclass.getDeclaredFields();
			if (chekfields != null) {
				for (Field tmp : chekfields) {

					if (tmp.isAnnotationPresent(PrimaryKey.class)) {
						PrimaryKey pkitf = tmp.getAnnotation(PrimaryKey.class);
						String value = pkitf.name();
						if (StringUtils.isEmpty(value)) {
							value = tmp.getName();
						}
						ColumnAttribute pkcolattr = new ColumnAttribute(
								tmp.getName(), value, pkitf.createType());
						pkFeildNamesAttr.add(pkcolattr);
						colFeildNamesAttr.add(pkcolattr);
						pkFeildNames.add(value);
					} else if (tmp.isAnnotationPresent(ParentForeignKey.class)) {
						ParentForeignKey pkitf = tmp
								.getAnnotation(ParentForeignKey.class);
						String value = pkitf.name();
						if (StringUtils.isEmpty(value)) {
							value = tmp.getName();
						}
						parentPkFeildNames.add(value);
						ColumnAttribute parpkcolattr = new ColumnAttribute(
								tmp.getName(), value);
						colFeildNamesAttr.add(parpkcolattr);
					} else if (tmp.isAnnotationPresent(ForeignKey.class)) {
						ForeignKey pkitf = tmp.getAnnotation(ForeignKey.class);
						String value = pkitf.name();
						if (StringUtils.isEmpty(value)) {
							value = tmp.getName();
						}
						parentPkFeildNames.add(value);
						ColumnAttribute forpkcolattr = new ColumnAttribute(
								tmp.getName(), value);
						colFeildNamesAttr.add(forpkcolattr);
					} else if (tmp.isAnnotationPresent(TimeStamp.class)) {
						TimeStamp pkitf = tmp.getAnnotation(TimeStamp.class);
						ts = pkitf.name();
						if (StringUtils.isEmpty(ts)) {
							ts = tmp.getName();
						}
						tsattr = new ColumnAttribute(tmp.getName(), ts);
						colFeildNamesAttr.add(tsattr);
					} else if (tmp.isAnnotationPresent(Column.class)) {
						String colName = tmp.getName();
						Column colAtr = tmp.getAnnotation(Column.class);
						colName = colAtr.name();
						colFeildNamesAttr.add(new ColumnAttribute(
								tmp.getName(), colName));
					}
				}
			}
			// 缓存
			Map<String, Object> mapinfo = new HashMap<String, Object>();
			// 表名
			mapinfo.put(SuperEntity.TABLENAME_SHOWNAME, tableName);
			// 主键
			mapinfo.put(SuperEntity.PKFIELDNAMES_SHOWNAME,
					pkFeildNames.toArray(new String[pkFeildNames.size()]));
			mapinfo.put(SuperEntity.PKFIELDATTRS, pkFeildNamesAttr
					.toArray(new ColumnAttribute[pkFeildNamesAttr.size()]));
			// 主表外键
			mapinfo.put(SuperEntity.PARENTPKFIELDNAMES_SHOWNAME,
					parentPkFeildNames.toArray(new String[parentPkFeildNames
							.size()]));
			// 外键
			mapinfo.put(SuperEntity.FOREIGNKEY_SHOWNAME, foreginPkFeildNames
					.toArray(new String[foreginPkFeildNames.size()]));
			// 时间戳
			mapinfo.put(SuperEntity.TS_SHOWNAME, ts);
			mapinfo.put(SuperEntity.TS_ATTR, tsattr);
			// 列名
			mapinfo.put(SuperEntity.FIELDCOLUMN_ATTRS, colFeildNamesAttr
					.toArray(new ColumnAttribute[colFeildNamesAttr.size()]));
			//
			SamrtEntityCache.getInstance().putValue(checkclass, mapinfo);
			return mapinfo;
		}
		return null;
	}
}
