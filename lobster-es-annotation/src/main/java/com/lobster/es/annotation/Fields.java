package com.lobster.es.annotation;

import java.lang.annotation.*;

/**
 * @see com.lobster.es.annotation.enums.FieldType#TEXT
 * @author zeyingshi
 * @date 05 7月 2024 01:17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Fields {
	 IndexField[] fields() default @IndexField;

}