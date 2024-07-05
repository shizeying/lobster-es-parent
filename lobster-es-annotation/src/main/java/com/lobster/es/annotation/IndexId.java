package com.lobster.es.annotation;

import com.lobster.es.annotation.enums.IdType;

/**
 * @author zeyingshi
 * @date 04 7月 2024 22:43
 */
public @interface IndexId {
	/**
	 * id 生成策略
	 * @see IdType
	 * @return  id 生成策略
	 */
	IdType type() default IdType.DEFAULT;
}