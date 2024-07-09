package com.lobster.es.annotation;

import com.lobster.es.common.enums.FieldType;

import java.lang.annotation.*;

/**
 * @see FieldType#TEXT
 * @author zeyingshi
 * @date 05 7月 2024 01:17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Fields {
	 IndexField[] fields() default @IndexField;

}