package com.idp.pub.entity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Types;

/**
 * 数据库列
 * 
 * @author panfei
 * @see Types
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	String name() default "";

	boolean isIgonre() default false;

	int jdbcType() default Types.NULL;

}
