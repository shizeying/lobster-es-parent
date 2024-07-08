package com.lobster.es.core.mapping;

/**
 * @author zeyingshi
 * @date 05 7月 2024 02:02
 */

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.PropertyNamingStrategy;
import com.alibaba.fastjson2.annotation.JSONType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Builder
@Accessors(fluent = true)
@AllArgsConstructor
@NoArgsConstructor
@JSONType(naming = PropertyNamingStrategy.SnakeCase, deserializeFeatures = JSONReader.Feature.TrimString, serializeFeatures = JSONWriter.Feature.WriteNulls)
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
	private Map<String, FieldMapping> fields;
	private IndexOptionsConfig indexOptions;
	private Map<String, List<String>> relations;
	private  Integer scalingFactor;
	
	
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JSONType(naming = PropertyNamingStrategy.SnakeCase, deserializeFeatures = JSONReader.Feature.TrimString, serializeFeatures = JSONWriter.Feature.WriteNulls)
	public static class FielddataFrequencyFilterConfig {
		private Integer min;
		private Integer max;
		private Integer minSegmentSize;
	}
	
	@Data
	@Builder
	@Accessors(fluent = true)
	@JSONType(naming = PropertyNamingStrategy.SnakeCase, deserializeFeatures = JSONReader.Feature.TrimString, serializeFeatures = JSONWriter.Feature.WriteNulls)
	public static class IndexOptionsConfig {
		private String type;
		private int m;
		private int efConstruction;
		private double confidenceInterval;
	}
	
	
}