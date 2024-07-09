package com.lobster.es.common.enums;

/**
 * <p>
 * 字段类型
 *
 * <pre>
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-types.html">mapping-types</a> </per>
 * <p>
 *
 * @author zeyingshi
 * @date 04 7月 2024 22:49
 */
public enum FieldType {
    /**
     * binary
     */
    BINARY,
    /**
     * completion
     */
    COMPLETION,
    /**
     * date
     */
    DATE,
    /**
     * date_nanos
     */
    DATE_NANOS,
    /**
     * dense_vector
     */
    DENSE_VECTOR,
    /**
     * flattened
     */
    FLATTENED,
    /**
     * geo_point
     */
    GEO_POINT,
    /**
     * geo_shape
     */
    GEO_SHAPE,
    /**
     * histogram
     */
    HISTOGRAM,
    /**
     * join
     */
    JOIN,
    /**
     * nested
     */
    NESTED,
    /**
     * short
     */
    /**
     * byte
     */
    BYTE,
    /**
     * half_float
     */
    HALF_FLOAT,
    /**
     * scaled_float
     */
    SCALED_FLOAT,
    /**
     * unsigned_long
     */
    UNSIGNED_LONG,
    /**
     * Object
     */
    OBJECT,
    /**
     * percolator
     */
    PERCOLATOR,
    /**
     * point
     */
    POINT,
    /**
     * integer_range
     */
    INTEGER_RANGE,
    /**
     * float_range
     */
    FLOAT_RANGE,
    /**
     * long_range
     */
    LONG_RANGE,
    /**
     * double_range
     */
    DOUBLE_RANGE,
    /**
     * date_range
     */
    DATE_RANGE,
    /**
     * ip_range
     */
    IP_RANGE,
    /**
     * rank_feature
     */
    RANK_FEATURE,
    /**
     * rank_features
     */
    RANK_FEATURES,
 
    /**
     * shape
     */
    SHAPE,
    /**
     * sparse_vector
     */
    SPARSE_VECTOR,
    /**
     * token_count
     */
    TOKEN_COUNT,
    /**
     * version
     */
    VERSION,

    /**
     * text
     */
    TEXT,
    /**
     * keyword
     */
    KEYWORD,
    /**
     * integer
     */
    INTEGER,
    /**
     * long
     */
    LONG,
    /**
     * double
     */
    DOUBLE,
    /**
     * float
     */
    FLOAT,

    /**
     * boolean
     */
    BOOLEAN,

    /**
     * ip
     */
    IP,
    /**
     * aggregate_metric_double
     */
    AGGREGATE_METRIC_DOUBLE,
    /**
     * alias
     */
    ALIAS,
    /**
     * None
     */
    NONE

}