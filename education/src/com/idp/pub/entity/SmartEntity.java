package com.idp.pub.entity;

import java.util.Map;

import com.idp.pub.entity.utils.ColumnAttribute;
import com.idp.pub.entity.utils.SmartEntityUtils;

/**
 * 智能感知VO
 * 
 * @author panfei
 * 
 */
public abstract class SmartEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7640004706138515041L;

	private String tableName = null;

	private String[] pkFieldsNames = null;

	private ColumnAttribute[] pkFieldsAttributes = null;

	private ColumnAttribute[] colFieldsAttributes = null;

	private String[] parentPkFieldsNames = null;

	private String tsName;

	private ColumnAttribute tsAttr;

	/**
	 * 属性映射关系缓存
	 */
	public SmartEntity() {
		super();
		Map<String, Object> attribute_map = SmartEntityUtils
				.recordAnnotationInfo(this.getClass());
		if (attribute_map != null) {
			tableName = (String) attribute_map
					.get(SuperEntity.TABLENAME_SHOWNAME);
			pkFieldsNames = (String[]) attribute_map
					.get(SuperEntity.PKFIELDNAMES_SHOWNAME);
			pkFieldsAttributes = (ColumnAttribute[]) attribute_map
					.get(SuperEntity.PKFIELDATTRS);
			parentPkFieldsNames = (String[]) attribute_map
					.get(SuperEntity.PARENTPKFIELDNAMES_SHOWNAME);
			colFieldsAttributes = (ColumnAttribute[]) attribute_map
					.get(SuperEntity.FIELDCOLUMN_ATTRS);
			tsName = (String) attribute_map.get(SuperEntity.TS_SHOWNAME);
			tsAttr = (ColumnAttribute) attribute_map.get(SuperEntity.TS_ATTR);
		}
	}

	@Override
	public String[] getPKFieldNames() {
		return pkFieldsNames;
	}

	@Override
	public String[] getParentPKFieldNames() {
		return parentPkFieldsNames;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public String getTimeStampName() {
		return tsName;
	}

	@Override
	public ColumnAttribute getTimeStampAttribute() {
		return tsAttr;
	}

	@Override
	public ColumnAttribute[] getPKFieldAttributes() {
		return pkFieldsAttributes;
	}

	@Override
	public ColumnAttribute[] findColFieldAttributes() {
		return colFieldsAttributes;
	}

}
