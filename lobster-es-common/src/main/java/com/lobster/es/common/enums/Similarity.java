package com.lobster.es.common.enums;

/**
 * @author zeyingshi
 * @date 04 7月 2024 23:53
 */
public enum Similarity {
    /**
     * l2_norm
     */
    L2_NORM,
    /**
     * dot_product
     */
    DOT_PRODUCT,
    /***
     * cosine
     */
    COSINE,
    /**
     * max_inner_product
     */
    MAX_INNER_PRODUCT,
    /**
     * bm25 only type
     * 
     * @see FieldType#KEYWORD
     * @see FieldType#TEXT
     */
    BM25,
    /**
     * 
     * only type
     * 
     * @see FieldType#KEYWORD
     * @see FieldType#TEXT
     */
    BOOLEAN,
    /**
     * None
     */
    NONE,
}