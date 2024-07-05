package com.lobster.es.annotation;

import com.lobster.es.annotation.enums.*;
import com.lobster.es.annotation.params.DefaultJoinType;
import com.lobster.es.annotation.params.DefaultNestedClass;
import com.lobster.es.annotation.params.DefaultObjectClass;

import java.lang.annotation.*;

/**
 * @author zeyingshi
 * @date 04 7月 2024 22:49
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface IndexField {
    boolean exists() default true;
    /**
     * 字段名称
     * 
     * @return 字段名称
     */
    String fieldName() default "";

    /**
     * 字段类型
     * 
     * @return 字段类型
     */
    FieldType type() default FieldType.NONE;

    /**
     * 是否分词
     * 
     * @return 是否分词
     */
    String analyzer() default "";

    /**
     * This field only supports
     * 
     * @see FieldType#AGGREGATE_METRIC_DOUBLE
     *
     */
    Metrics[] metrics() default {};

    /**
     * This field only supports
     * 
     * @see FieldType#AGGREGATE_METRIC_DOUBLE
     */
    Metrics default_metric() default Metrics.VALUE_COUNT;

    /**
     * This field only supports
     * 
     * @see FieldType#AGGREGATE_METRIC_DOUBLE
     */
    TimeSeriesMetric time_series_metric() default TimeSeriesMetric.NONE;

    /**
     * if filed type is
     * 
     * @see FieldType#ALIAS
     * then this field is required
     */
    String path() default "";

    /**
     * Should the field be stored on disk in a column-stride fashion, so that it can later be used for sorting,
     * aggregations, or scripting? Accepts true or false (default).
     */
    boolean doc_values() default false;

    /**
     * Whether the field value should be stored and retrievable separately from the _source field. Accepts true or false
     * (default).
     * 
     * @return boolean
     */
    boolean store() default false;

    /**
     *
     * Should the field be quickly searchable? Accepts true (default) and false. Fields that only have doc_values
     * enabled can still be queried using term or range-based queries, albeit slower.
     */
    boolean index() default false;

    /**
     * ignore_malformed
     */
    boolean ignore_malformed() default false;

    /**
     * null_value
     */
    NullValue null_value() default NullValue.NONE;

    /**
     * search_analyzer
     */
    String search_analyzer() default "";

    /**
     * format
     */
    String format() default "";

    /**
     * if field type is
     * 
     * @see FieldType#DENSE_VECTOR
     */
    int dimension() default 0;

    /**
     * if field type is
     * 
     * @see FieldType#DENSE_VECTOR and index must be true
     */
    Similarity similarity() default Similarity.NONE;

    /**
     * index_options if field type is
     * 
     * @see FieldType#DENSE_VECTOR
     */
    IndexOptions index_options() default @IndexOptions(type = IndexOptionsType.NONE, m = 0, efConstruction = 0,
        confidenceInterval = 0.0);
    
    /**
     * ignore_above
     * @return int
     */
    int ignore_above() default 0;
    
    /**
     * if field type is
     * @see FieldType#JOIN
     * @return Class
     *
     */
    Class<? extends  DefaultJoinType> joinType() default DefaultJoinType.class;
    
    /**
     * @see FieldType#KEYWORD
     * @return boolean
     * Whether field-length should be taken into account when scoring queries. Accepts true or false (default).
     */
    boolean norms()default false;
    
    /**
     * only for
     * @see FieldType#NESTED
     * @return {@link Class }<{@link ? } {@link extends } {@link DefaultNestedClass }>
     */
    Class<? extends DefaultNestedClass> nested() default DefaultNestedClass.class;
    
    /**
     * only for
     * @see FieldType#OBJECT
     * @return {@link Class }<{@link ? } {@link extends } {@link DefaultObjectClass }>
     */
    Class<? extends DefaultObjectClass> object() default DefaultObjectClass.class;
    /**
     *  only for
     * @see FieldType#RANK_FEATURE
     * @see FieldType#RANK_FEATURES
     */
    boolean positive_score_impact() default false;
    
    /**
     * @return boolean
     */
    boolean fielddata() default false;
    
    /**
     * @see  FieldType#TEXT
     * @return {@link FielddataFrequencyFilter }
     */
    FielddataFrequencyFilter fielddata_frequency_filter() default @FielddataFrequencyFilter;
    
    
    /**
     * @return boolean
     */
    boolean eager_global_ordinals() default false;
    
    
    /**
     * only for fieldType is
     * @see  FieldType#JOIN
     */
    JoinTypeRelations[] relations()  default {};
    
   
  
    

}