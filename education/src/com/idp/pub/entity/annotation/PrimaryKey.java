package com.idp.pub.entity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键注解枚举
 * 
 * @author panfei
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PrimaryKey {
	String name() default "";

	PK createType() default PK.useDATABASE;

	public enum PK {
		useIDP, useDATABASE
	}
}
