
package com.lobster.es.annotation;

import com.lobster.es.annotation.enums.IndexOptionsType;

import java.lang.annotation.*;

/**
 * @author zeyingshi
 * @date 05 7月 2024 00:13
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface IndexOptions {
    IndexOptionsType type() default IndexOptionsType.NONE;

    int m() default 16;

    /**
     * supported values are:
     * 
     * @see IndexOptionsType#HNSW
     * @see IndexOptionsType#INT8_FLAT
     */
    int efConstruction() default 100;

    /**
     * supported values are:
     * 
     * @see IndexOptionsType#HNSW
     * @see IndexOptionsType#INT8_FLAT
     */
    double confidenceInterval() default 0.95;
}