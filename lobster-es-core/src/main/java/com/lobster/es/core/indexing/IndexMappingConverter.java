package com.lobster.es.core.indexing;

import com.google.common.collect.Maps;
import com.lobster.es.annotation.*;
import com.lobster.es.annotation.enums.*;
import com.lobster.es.annotation.params.DefaultJoinClass;
import com.lobster.es.common.constants.BaseEsConstants;
import com.lobster.es.common.tuple.Tuple2;
import com.lobster.es.common.util.Asserts;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.lobster.es.common.constants.BaseEsConstants.*;

/**
 * @author w7410
 */
public class IndexMappingConverter {
	
	public static <T> Mappings createIndexMapping(Class<T> clazz) {
		Map<String, FieldMapping> properties = Maps.newHashMap();
		List<Field> allFields = getAllFields(clazz);
		
		allFields.forEach(field -> {
			Tuple2<String, FieldMapping> tuple2 = buildFieldMapping(field);
			if (tuple2 != null) {
				properties.put(tuple2.getFirst(), tuple2.getSecond());
			}
		});
		//判断是否存在ALIAS
		checkAlias(properties);
		
		
		return Mappings.builder()
				.settings(convertSettings(clazz))
				.mappings(Mapping.builder()
						.properties(properties)
						
						.build()).build();
	}
	
	private static List<Field> getAllFields(Class<?> type) {
		List<Field> fields = new ArrayList<>();
		while (type != null && type != Object.class) {
			fields.addAll(Arrays.asList(type.getDeclaredFields()));
			type = type.getSuperclass();
		}
		return fields;
	}
	
	private static Tuple2<String, FieldMapping> buildFieldMapping(Field field) {
		FieldMapping.FieldMappingBuilder fieldMappingBuilder = FieldMapping.builder();
		if (field.isAnnotationPresent(IndexId.class)) {
			return null;
		}
		String name = field.getName();
		
		if (field.isAnnotationPresent(IndexField.class)) {
			IndexField indexField = field.getAnnotation(IndexField.class);
			// 跳过
			if (!indexField.exists()) {
				return null;
			}
			if (!indexField.fieldName().isEmpty()) {
				name = indexField.fieldName();
			}
			
			FieldType type = indexField.type();
			
			fieldMappingBuilder.type(type.name().toLowerCase())
					.analyzer(indexField.analyzer().isEmpty() ? null : indexField.analyzer())
					.searchAnalyzer(indexField.search_analyzer().isEmpty() ? null : indexField.search_analyzer())
					.format(indexField.format().isEmpty() ? null : indexField.format())
					.ignoreAbove(indexField.ignore_above() == 0 ? null : indexField.ignore_above())
					.dims(indexField.dims() == -1 ? null : indexField.dims())
					.index(DocValuesOption.getDocValuesOption(indexField.index()))
					.similarity(indexField.similarity() == Similarity.NONE ? null : indexField.similarity().name().toLowerCase())
					.norms(DocValuesOption.getDocValuesOption(indexField.norms()))
					.metrics(indexField.metrics().length == 0 ? null : Arrays.stream(indexField.metrics())
							.map(Enum::name).map(String::toLowerCase).collect(Collectors.toList()))
					.defaultMetric(indexField.default_metric() == Metrics.NONE ? null :
							indexField.default_metric().name().toLowerCase())
					.timeSeriesMetric(indexField.time_series_metric() == TimeSeriesMetric.NONE ? null : indexField.time_series_metric().name().toLowerCase())
					.path(indexField.path().isEmpty() ? null : indexField.path())
					.docValues(DocValuesOption.getDocValuesOption(indexField.doc_values()))
					.store(DocValuesOption.getDocValuesOption(indexField.store()))
					.ignoreMalformed(DocValuesOption.getDocValuesOption(indexField.ignore_malformed()))
					.nullValue(indexField.null_value() == NullValue.NONE ? null : indexField.null_value().name().toLowerCase())
					.eagerGlobalOrdinals(DocValuesOption.getDocValuesOption(indexField.eager_global_ordinals()));
			
			if (indexField.fielddata_frequency_filter().min() != Integer.MIN_VALUE
					&& indexField.fielddata_frequency_filter().max() != Integer.MIN_VALUE
					&& indexField.fielddata_frequency_filter().minSegmentSize() != Integer.MIN_VALUE) {
				FieldMapping.FielddataFrequencyFilterConfig filterConfig = FieldMapping.FielddataFrequencyFilterConfig.builder()
						.min(indexField.fielddata_frequency_filter().min())
						.max(indexField.fielddata_frequency_filter().max())
						.minSegmentSize(indexField.fielddata_frequency_filter().minSegmentSize()).build();
				fieldMappingBuilder.fielddataFrequencyFilter(filterConfig);
			}
			if (indexField.index_options().type() != IndexOptionsType.NONE) {
				FieldMapping.IndexOptionsConfig indexOptionsConfig = FieldMapping.IndexOptionsConfig.builder()
						.type(indexField.index_options().type().name().toLowerCase())
						.m(indexField.index_options().m())
						.efConstruction(indexField.index_options().efConstruction())
						.confidenceInterval(indexField.index_options().confidenceInterval()).build();
				fieldMappingBuilder.indexOptions(indexOptionsConfig);
			}
			checkSimilarity(type, indexField, field);
			if (indexField.type() == FieldType.SCALED_FLOAT) {
				fieldMappingBuilder.scalingFactor(indexField.scalingFactor());
			}
			if (indexField.type() == FieldType.AGGREGATE_METRIC_DOUBLE) {
				
				fieldMappingBuilder.defaultMetric(indexField.default_metric().name().toLowerCase());
			}
			
			
			if (type == FieldType.JOIN) {
				Map<@Nullable String, @Nullable List<String>> relations = Maps.newHashMap();
				for (JoinTypeRelations relation : indexField.relations()) {
					relations.put(relation.parent(), Arrays.asList(relation.child()));
				}
				fieldMappingBuilder.relations(relations);
			}
			
			if (type == FieldType.NESTED || type == FieldType.OBJECT) {
				Map<String, FieldMapping> nestedProperties = Maps.newHashMap();
				Class<?> nestedClass = getNestedClass(indexField, type);
				List<Field> allFields = getAllFields(nestedClass);
				for (Field nestedField : allFields) {
					Tuple2<String, FieldMapping> stringFieldMappingTuple2 = buildFieldMapping(nestedField);
					if (stringFieldMappingTuple2 != null) {
						nestedProperties.put(stringFieldMappingTuple2.getFirst(), stringFieldMappingTuple2.getSecond());
					}
				}
				fieldMappingBuilder.properties(nestedProperties);
			}
			
			
			if (field.isAnnotationPresent(Fields.class)) {
				Fields fields = field.getAnnotation(Fields.class);
				Map<String, FieldMapping> multiFields = Maps.newHashMap();
				for (IndexField nestedIndexField : fields.fields()) {
					FieldMapping nestedFieldMapping = buildFieldMapping(nestedIndexField);
					if (nestedFieldMapping != null) {
						multiFields.put(nestedIndexField.fieldName(), nestedFieldMapping);
					}
				}
				fieldMappingBuilder.fields(multiFields);
			}
			
		}
		
		FieldMapping fieldMapping = fieldMappingBuilder.build();
		return new Tuple2<>(name, fieldMapping);
	}
	
	private static FieldMapping buildFieldMapping(IndexField indexField) {
		// 重载方法，用于处理 Fields 注解中的嵌套 IndexField
		FieldMapping.FieldMappingBuilder fieldMappingBuilder = FieldMapping.builder()
				.type(indexField.type().name().toLowerCase())
				.analyzer(indexField.analyzer().isEmpty() ? null : indexField.analyzer())
				.searchAnalyzer(indexField.search_analyzer().isEmpty() ? null : indexField.search_analyzer())
				.format(indexField.format().isEmpty() ? null : indexField.format())
				.ignoreAbove(indexField.ignore_above() == 0 ? null : indexField.ignore_above())
				.dims(indexField.dims() == -1 ? null : indexField.dims())
				.index(DocValuesOption.getDocValuesOption(indexField.index()))
				.similarity(indexField.similarity() == Similarity.NONE ? null : indexField.similarity().name().toLowerCase())
				.norms(DocValuesOption.getDocValuesOption(indexField.norms()))
				.metrics(indexField.metrics().length == 0 ? null : Arrays.stream(indexField.metrics())
						.map(Enum::name).map(String::toLowerCase).collect(Collectors.toList()))
				.defaultMetric(indexField.default_metric() == Metrics.NONE ? null :
						indexField.default_metric().name().toLowerCase())
				.timeSeriesMetric(indexField.time_series_metric() == TimeSeriesMetric.NONE ? null : indexField.time_series_metric().name().toLowerCase())
				.path(indexField.path().isEmpty() ? null : indexField.path())
				.docValues(DocValuesOption.getDocValuesOption(indexField.doc_values()))
				.store(DocValuesOption.getDocValuesOption(indexField.store()))
				.ignoreMalformed(DocValuesOption.getDocValuesOption(indexField.ignore_malformed()))
				.nullValue(indexField.null_value() == NullValue.NONE ? null : indexField.null_value().name().toLowerCase())
				.eagerGlobalOrdinals(DocValuesOption.getDocValuesOption(indexField.eager_global_ordinals()));
		
		if (indexField.fielddata_frequency_filter().min() != Integer.MIN_VALUE
				&& indexField.fielddata_frequency_filter().max() != Integer.MIN_VALUE
				&& indexField.fielddata_frequency_filter().minSegmentSize() != Integer.MIN_VALUE) {
			FieldMapping.FielddataFrequencyFilterConfig filterConfig = FieldMapping.FielddataFrequencyFilterConfig.builder()
					.min(indexField.fielddata_frequency_filter().min())
					.max(indexField.fielddata_frequency_filter().max())
					.minSegmentSize(indexField.fielddata_frequency_filter().minSegmentSize()).build();
			fieldMappingBuilder.fielddataFrequencyFilter(filterConfig);
		}
		if (indexField.index_options().type() != IndexOptionsType.NONE) {
			FieldMapping.IndexOptionsConfig indexOptionsConfig = FieldMapping.IndexOptionsConfig.builder()
					.type(indexField.index_options().type().name().toLowerCase())
					.m(indexField.index_options().m())
					.efConstruction(indexField.index_options().efConstruction())
					.confidenceInterval(indexField.index_options().confidenceInterval()).build();
			fieldMappingBuilder.indexOptions(indexOptionsConfig);
		}
		
		checkSimilarity(indexField.type(), indexField);
		
		return fieldMappingBuilder.build();
	}
	
	private static void checkSimilarity(FieldType type, IndexField indexField) {
		if ((type == FieldType.TEXT || type == FieldType.KEYWORD) && indexField.similarity() != Similarity.NONE) {
			Asserts.check(
					indexField.similarity() == Similarity.BM25 || indexField.similarity() == Similarity.BOOLEAN,
					"similarity only support BM25 or BOOLEAN");
		}
		if (type == FieldType.DENSE_VECTOR && indexField.similarity() != Similarity.NONE) {
			Asserts.check(
					!(indexField.similarity() == Similarity.BM25 || indexField.similarity() == Similarity.BOOLEAN),
					"similarity not support BM25 or BOOLEAN");
		}
		if (type == FieldType.DENSE_VECTOR) {
			Asserts.check(indexField.dims() <= 2048, "vector dims must be less than or equal to 2048");
		}
		if (type == FieldType.TOKEN_COUNT) {
			Asserts.notEmpty(indexField.analyzer(), "token_count_analyzer must be set");
		}
		if (type == FieldType.ALIAS) {
			//TODO: alias field 需要验证 path存在的情况，否则不能创建
			Asserts.notEmpty(indexField.path(), "alias fields must be set");
		}
		if (indexField.type() == FieldType.SCALED_FLOAT) {
			Asserts.check(indexField.scalingFactor() > 0, "scalingFactor must be greater than 0");
		}
		if (indexField.type() == FieldType.AGGREGATE_METRIC_DOUBLE) {
			Asserts.check(indexField.default_metric() != Metrics.NONE, "default_metric cannot be NONE for " +
					"aggregate_metric_double fields");
		}
		if (type == FieldType.JOIN) {
			Asserts.notNull(indexField.relations(), "relations cannot be null for object fields");
			Asserts.check(indexField.relations().length != 0, "relations cannot be empty for object fields");
		}
		
		
	}
	
	private static void checkSimilarity(FieldType type, IndexField indexField, Field field) {
		checkSimilarity(type, indexField);
		if (type == FieldType.NESTED || type == FieldType.OBJECT) {
			Asserts.check(!field.isAnnotationPresent(Fields.class),
					"Fields cannot be used with nested or object or join  fields");
			Class<?> nestedClass = getNestedClass(indexField, type);
			Asserts.notNull(nestedClass, "nestedClass cannot be null for nested or object or join fields");
			
		}
		if (type == FieldType.JOIN) {
			//如果field为数组或者集合，则需判断里面的是否默认为DefaultJoinType
			// 如果field为数组或者集合，则需判断里面的是否默认为DefaultJoinType
			Class<?> fieldType = field.getType();
			if (fieldType.isArray()) {
				// 检查数组类型
				Class<?> componentType = fieldType.getComponentType();
				Asserts.check(componentType == DefaultJoinClass.class,
						"Array elements must be of type DefaultJoinClass");
			} else if (Collection.class.isAssignableFrom(fieldType)) {
				// 检查集合类型
				// 这里假设泛型类型信息可以通过反射获取
				java.lang.reflect.Type genericType = field.getGenericType();
				if (genericType instanceof java.lang.reflect.ParameterizedType) {
					java.lang.reflect.ParameterizedType parameterizedType = (java.lang.reflect.ParameterizedType) genericType;
					java.lang.reflect.Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
					if (actualTypeArguments.length > 0) {
						Class<?> actualTypeArgument = (Class<?>) actualTypeArguments[0];
						Asserts.check(actualTypeArgument == DefaultJoinClass.class,
								"Collection elements must be of type DefaultJoinClass");
					}
				}
			} else {
				Asserts.check(fieldType == DefaultJoinClass.class,
						"Array elements must be of type DefaultJoinClass");
			}
			
			
		}
		
	}
	
	/**
	 * 如果存在alias的类型，则需要验证key是否存在
	 *
	 * @param properties
	 */
	
	private static void checkAlias(Map<String, FieldMapping> properties) {
		Set<String> aliasFieldNames =
				properties.values().stream().filter(fieldMapping -> FieldType.ALIAS.name().toLowerCase().equals(fieldMapping.type())).map(FieldMapping::path).collect(Collectors.toSet());
		properties.values().stream().map(FieldMapping::properties)
				.filter(Objects::nonNull)
				.map(Map::entrySet)
				.flatMap(Collection::stream)
				.filter(fieldMapping -> FieldType.ALIAS.name().toLowerCase().equals(fieldMapping.getValue().type()))
				.map(Map.Entry::getValue).map(FieldMapping::path).forEach(aliasFieldNames::add);
		properties.values().stream().map(FieldMapping::fields)
				.filter(Objects::nonNull)
				.map(Map::entrySet)
				.flatMap(Collection::stream)
				.filter(fieldMapping -> FieldType.ALIAS.name().toLowerCase().equals(fieldMapping.getValue().type()))
				.map(Map.Entry::getValue).map(FieldMapping::path).forEach(aliasFieldNames::add);
		if (aliasFieldNames.isEmpty()) {
			return;
		}
		for (String aliasFieldName : aliasFieldNames) {
			if (aliasFieldName.contains(".")) {
				handleNestedAliasField(properties, aliasFieldName);
				return;
			}
			handleSimpleAliasField(properties, aliasFieldName);
			
		}
		
		
	}
	
	private static void handleNestedAliasField(Map<String, FieldMapping> properties, String aliasFieldName) {
		// 拆分嵌套字段名
		String[] parts = aliasFieldName.split("\\.");
		int startIndex = 0;
		int endIndex = parts.length - 1;
		
		if (!recursiveFindAlias(properties, parts, startIndex, endIndex)) {
			
			
			throw new IllegalArgumentException("Alias field not found in the path: " + aliasFieldName);
		}
	}
	
	
	private static boolean recursiveFindAlias(Map<String, FieldMapping> properties, String[] parts, int currentIndex,
	                                          int endIndex) {
		if (currentIndex > endIndex) {
			return false;
		}
		
		String currentKey = parts[currentIndex];
		FieldMapping currentFieldMapping = properties.get(currentKey);
		
		if (currentFieldMapping == null) {
			return false;
		}
		
		if (currentIndex == endIndex) {
			// 检查最后一个部分是否是alias字段
			return true;
		} else {
			// 递归查找下一级嵌套
			Map<String, FieldMapping> nextFields = currentFieldMapping.fields();
			
			// 再递归检查fields
			if (nextFields != null && recursiveFindAlias(nextFields, parts, currentIndex + 1, endIndex)) {
				return true;
			}
			
			return false;
		}
	}
	
	
	private static void handleSimpleAliasField(Map<String, FieldMapping> properties, String aliasFieldName) {
		for (Map.Entry<String, FieldMapping> stringFieldMappingEntry : properties.entrySet()) {
			if (stringFieldMappingEntry.getKey().equals(aliasFieldName)) {
				return;
			}
			if (stringFieldMappingEntry.getValue().properties() != null) {
				for (Map.Entry<String, FieldMapping> fieldMappingEntry : stringFieldMappingEntry.getValue().properties().entrySet()) {
					if (fieldMappingEntry.getKey().equals(aliasFieldName)) {
						return;
					}
					
				}
			}
			if (stringFieldMappingEntry.getValue().fields() != null) {
				for (Map.Entry<String, FieldMapping> fieldMappingEntry : stringFieldMappingEntry.getValue().fields().entrySet()) {
					if (fieldMappingEntry.getKey().equals(aliasFieldName)) {
						return;
					}
					
				}
			}
		}
		throw new IllegalArgumentException("Alias field not found: " + aliasFieldName);
	}
	
	private static Class<?> getNestedClass(IndexField indexField, FieldType type) {
		switch (type) {
			case OBJECT:
				return indexField.object();
			case NESTED:
				return indexField.nested();
			
			default:
				return null;
		}
	}
	
	
	public static <T> Map<String, Object> convertSettings(Class<T> clazz) {
		Map<String, Object> settings = Maps.newHashMap();
		if (clazz.isAnnotationPresent(IndexName.class)) {
			IndexName indexName = clazz.getAnnotation(IndexName.class);
			if (!indexName.refreshInterval().equals(BaseEsConstants.DEFAULT_REFRESH_INTERVAL)) {
				settings.put(INDEX_REFRESH_INTERVAL_KEY, indexName.refreshInterval());
			}
			if (StringUtils.isNotBlank(indexName.aliasName())) {
				settings.put(BaseEsConstants.INDEX_ALIAS_KEY, indexName.aliasName());
			}
			if (indexName.shardsNum() > 0) {
				settings.put(BaseEsConstants.INDEX_SHARDS_KEY, indexName.shardsNum());
			}
			if (indexName.replicasNum() > 0) {
				settings.put(BaseEsConstants.INDEX_REPLICAS_KEY, indexName.replicasNum());
			}
		}
		return settings;
	}
	
	
}