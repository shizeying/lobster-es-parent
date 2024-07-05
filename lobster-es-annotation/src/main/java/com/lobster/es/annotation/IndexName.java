package com.lobster.es.annotation;

import com.lobster.es.common.constants.BaseEsConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zeyingshi
 * @date 05 7月 2024 01:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface IndexName {

    /**
     * 实体对应的索引名
     *
     * @return 默认为空
     */
    String value() default "";

    /**
     * 分片数
     *
     * @return 默认为1
     */
    int shardsNum() default BaseEsConstants.DEFAULT_SHARDS;

    /**
     * 副本数
     *
     * @return 默认为1
     */
    int replicasNum() default BaseEsConstants.DEFAULT_REPLICAS;

    /**
     * 索引别名
     *
     * @return 别名
     */
    String aliasName() default "";
}