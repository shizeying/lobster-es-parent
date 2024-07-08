package com.lobster.es.test;


import com.lobster.es.annotation.*;
import com.lobster.es.annotation.enums.*;
import com.lobster.es.annotation.params.DefaultJoinClass;
import lombok.Data;

import java.util.List;

@Data
@IndexName(value = "test_index", shardsNum = 1, replicasNum = 1, aliasName = "test_alias")
public class TestIndex {
	
	@IndexId(type = IdType.DEFAULT)
	private String id;
	//
	@IndexField(fieldName = "binary_field", type = FieldType.BINARY)
	private byte[] binaryField;

	@IndexField(fieldName = "completion_field", type = FieldType.COMPLETION)
	private String completionField;

	@IndexField(fieldName = "date_field", type = FieldType.DATE, format = "yyyy-MM-dd")
	private String dateField;

	@IndexField(fieldName = "date_nanos_field", type = FieldType.DATE_NANOS, format = "strict_date_optional_time_nanos")
	private String dateNanosField;

	@IndexField(fieldName = "dense_vector_field", type = FieldType.DENSE_VECTOR, dims = 128, index = DocValuesOption.TRUE
			, similarity =
			Similarity.L2_NORM)
	private float[] denseVectorField;

	@IndexField(fieldName = "flattened_field", type = FieldType.FLATTENED)
	private Object flattenedField;
		@IndexField(fieldName = "histogram_field", type = FieldType.HISTOGRAM)
	private Object histogramField;

	@IndexField(fieldName = "geo_point_field", type = FieldType.GEO_POINT)
	private String geoPointField;

	@IndexField(fieldName = "geo_shape_field", type = FieldType.GEO_SHAPE)
	private String geoShapeField;


	
	@IndexField(fieldName = "join_field", type = FieldType.JOIN, joinType = JoinTypeObj.class, relations = {
			@JoinTypeRelations(parent = "parent_field", child = {"child_field", "child_field3"}),
			@JoinTypeRelations(parent = "parent_field2", child = "child_field2")
	})
	private DefaultJoinClass joinField;
	
	@IndexField(fieldName = "nested_field", type = FieldType.NESTED, nested = NestedField.class)
	private NestedField nestedField;
	//
	@IndexField(fieldName = "byte_field", type = FieldType.BYTE)
	private byte byteField;                                                        

	@IndexField(fieldName = "half_float_field", type = FieldType.HALF_FLOAT)
	private float halfFloatField;

	@IndexField(fieldName = "scaled_float_field", type = FieldType.SCALED_FLOAT,scalingFactor = 100)
	private double scaledFloatField;

	@IndexField(fieldName = "unsigned_long_field", type = FieldType.UNSIGNED_LONG)
	private long unsignedLongField;

	@IndexField(fieldName = "object_field", type = FieldType.OBJECT, object = ObjType.class)
	private List<ObjType> objectField;
	//
	@IndexField(fieldName = "percolator_field", type = FieldType.PERCOLATOR)
	private String percolatorField;

	@IndexField(fieldName = "point_field", type = FieldType.POINT)
	private Object pointField;

	@IndexField(fieldName = "integer_range_field", type = FieldType.INTEGER_RANGE)
	private Object integerRangeField;

	@IndexField(fieldName = "float_range_field", type = FieldType.FLOAT_RANGE)
	private Object floatRangeField;

	@IndexField(fieldName = "long_range_field", type = FieldType.LONG_RANGE)
	private Object longRangeField;

	@IndexField(fieldName = "double_range_field", type = FieldType.DOUBLE_RANGE)
	private Object doubleRangeField;

	@IndexField(fieldName = "date_range_field", type = FieldType.DATE_RANGE)
	private Object dateRangeField;

	@IndexField(fieldName = "ip_range_field", type = FieldType.IP_RANGE)
	private Object ipRangeField;

	@IndexField(fieldName = "rank_feature_field", type = FieldType.RANK_FEATURE, positive_score_impact =
			DocValuesOption.TRUE)
	private float rankFeatureField;

	@IndexField(fieldName = "rank_features_field", type = FieldType.RANK_FEATURES)
	private Object rankFeaturesField;

	@IndexField(fieldName = "shape_field", type = FieldType.SHAPE)
	private Object shapeField;

	@IndexField(fieldName = "sparse_vector_field", type = FieldType.SPARSE_VECTOR)
	private Object sparseVectorField;

	@IndexField(fieldName = "token_count_field", type = FieldType.TOKEN_COUNT,analyzer = "standard")
	private int tokenCountField;

	@IndexField(fieldName = "version_field", type = FieldType.VERSION)
	private long versionField;

	@IndexField(fieldName = "text_field", type = FieldType.TEXT, analyzer = "standard", search_analyzer = "standard",
			fielddata = DocValuesOption.TRUE, fielddata_frequency_filter = @FielddataFrequencyFilter(min = 2, max = 100,
			minSegmentSize = 1000))
	@Fields(fields = {
	        @IndexField(fieldName = "keywordfield", type = FieldType.KEYWORD, fielddata = DocValuesOption.TRUE )
	})
	private String textField;

	@IndexField(fieldName = "keyword_field", type = FieldType.KEYWORD, ignore_above = 256, norms = DocValuesOption.TRUE)
	private String keywordField;

	@IndexField(fieldName = "integer_field", type = FieldType.INTEGER)
	private int integerField;

	@IndexField(fieldName = "long_field", type = FieldType.LONG)
	private long longField;

	@IndexField(fieldName = "double_field", type = FieldType.DOUBLE)
	private double doubleField;

	@IndexField(fieldName = "float_field", type = FieldType.FLOAT)
	private float floatField;

	@IndexField(fieldName = "boolean_field", type = FieldType.BOOLEAN)
	private boolean booleanField;

	@IndexField(fieldName = "ip_field", type = FieldType.IP)
	private String ipField;

	@IndexField(fieldName = "aggregate_metric_double_field", type = FieldType.AGGREGATE_METRIC_DOUBLE, metrics =
			{Metrics.MIN, Metrics.MAX, Metrics.SUM, Metrics.VALUE_COUNT},
			time_series_metric = TimeSeriesMetric.GAUGE, default_metric = Metrics.VALUE_COUNT)
	private double aggregateMetricDoubleField;

	@IndexField(fieldName = "alias_field", type = FieldType.ALIAS, path = "text_field.keywordfield")
	private String aliasField;
	
	
}