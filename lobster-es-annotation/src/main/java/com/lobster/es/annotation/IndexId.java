package com.lobster.es.annotation;


import com.lobster.es.common.enums.IdType;

import java.lang.annotation.*;

/**
 * @author zeyingshi
 * @date 04 7月 2024 22:43
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface IndexId {
	/**
	 * id 生成策略
	 * @see IdType
	 * @return  id 生成策略
	 */
	IdType type() default IdType.DEFAULT;
}