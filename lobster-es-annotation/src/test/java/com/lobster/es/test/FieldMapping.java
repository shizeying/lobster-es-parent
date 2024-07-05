package com.lobster.es.test;

/**
 * @author zeyingshi
 * @date 05 7月 2024 02:02
 */

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
public class FieldMapping {
    private String type;
    private String analyzer;
    private String searchAnalyzer;
    private String format;
    private Integer dims;
    private Boolean index;
    private String similarity;
    private Integer ignoreAbove;
    private Boolean norms;
    private List<String> metrics;
    private String defaultMetric;
    private String timeSeriesMetric;
    private String path;
    private Boolean docValues;
    private Boolean store;
    private Boolean ignoreMalformed;
    private String nullValue;
    private FielddataFrequencyFilterConfig fielddataFrequencyFilter;
    private Boolean eagerGlobalOrdinals;
    private Map<String, FieldMapping> properties;
    private IndexOptionsConfig indexOptions;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class FielddataFrequencyFilterConfig {
        private Integer min;
        private Integer max;
        private Integer minSegmentSize;
    }

    @Data
    @Builder
    @Accessors(fluent = true)
    class IndexOptionsConfig {
        private String type;
        private int m;
        private int efConstruction;
        private double confidenceInterval;
    }
}