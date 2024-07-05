package com.lobster.es.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.PropertyFilter;
import com.google.common.collect.Maps;
import com.lobster.es.annotation.Fields;
import com.lobster.es.annotation.IndexField;
import com.lobster.es.annotation.IndexId;
import com.lobster.es.annotation.JoinTypeRelations;
import com.lobster.es.annotation.enums.*;
import com.lobster.es.common.util.Asserts;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class MappingConverter {
	
	public static void main(String[] args) {
		Mapping jsonMapping = createMapping(TestIndex.class);
		// 创建属性过滤器来排除空值、空集合和空对象
		PropertyFilter propertyFilter = (object, name, value) -> {
			if (value == null) {
				return false;
			}
			if (value instanceof Collection && ((Collection<?>) value).isEmpty()) {
				return false;
			}
			if (value instanceof Map && ((Map<?, ?>) value).isEmpty()) {
				return false;
			}
			if (value instanceof String && ((String) value).isEmpty()) {
				return false;
			}
			return true;
		};
		
		
		// 使用过滤器和序列化配置进行序列化
		String jsonString = JSON.toJSONString(jsonMapping, propertyFilter,
				JSONWriter.Feature.PrettyFormat,
				JSONWriter.Feature.WriteMapNullValue,
				JSONWriter.Feature.WriteNulls);
		
		
		System.out.println(jsonString);
	}
	
	@SneakyThrows
	private static <T> Mapping createMapping(Class<T> clazz) {
		Map<String, FieldMapping> properties = new HashMap<>();
		List<Field> allFields = getAllFields(clazz);
		
		allFields.forEach(field -> {
			FieldMapping fieldMapping = buildFieldMapping(field);
			if (fieldMapping != null) {
				properties.put(field.getName(), fieldMapping);
			}
		});
		return Mapping.builder()
				.properties(properties)
				.build();
	}
	
	private static List<Field> getAllFields(Class<?> type) {
		List<Field> fields = new ArrayList<>();
		while (type != null && type != Object.class) {
			fields.addAll(Arrays.asList(type.getDeclaredFields()));
			type = type.getSuperclass();
		}
		return fields;
	}
	
	private static FieldMapping buildFieldMapping(Field field) {
		FieldMapping.FieldMappingBuilder fieldMappingBuilder = FieldMapping.builder();
		if (field.isAnnotationPresent(IndexId.class)) {
			return null;
		}
		
		if (field.isAnnotationPresent(IndexField.class)) {
			IndexField indexField = field.getAnnotation(IndexField.class);
			// 跳过
			if (!indexField.exists()) {
				return null;
			}
			FieldType type = indexField.type();
			
			fieldMappingBuilder.type(type.name().toLowerCase())
					.analyzer(indexField.analyzer().isEmpty() ? null : indexField.analyzer())
					.searchAnalyzer(indexField.search_analyzer().isEmpty() ? null : indexField.search_analyzer())
					.format(indexField.format().isEmpty() ? null : indexField.format())
					.ignoreAbove(indexField.ignore_above() == 0 ? null : indexField.ignore_above())
					.dims(indexField.dimension() == 0 ? null : indexField.dimension())
					.index(indexField.index() ? true : null)
					.similarity(indexField.similarity() == Similarity.NONE ? null : indexField.similarity().name().toLowerCase())
					.norms(indexField.norms() ? true : null)
					.metrics(indexField.metrics().length == 0 ? null : Arrays.stream(indexField.metrics())
							.map(Enum::name).map(String::toLowerCase).collect(Collectors.toList()))
					.defaultMetric(indexField.default_metric() == Metrics.VALUE_COUNT ? null : indexField.default_metric().name().toLowerCase())
					.timeSeriesMetric(indexField.time_series_metric() == TimeSeriesMetric.NONE ? null : indexField.time_series_metric().name().toLowerCase())
					.path(indexField.path().isEmpty() ? null : indexField.path())
					.docValues(indexField.doc_values() ? true : null)
					.store(indexField.store() ? true : null)
					.ignoreMalformed(indexField.ignore_malformed() ? true : null)
					.nullValue(indexField.null_value() == NullValue.NONE ? null : indexField.null_value().name().toLowerCase())
					.eagerGlobalOrdinals(indexField.eager_global_ordinals() ? true : null);
			
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
			
			checkSimilarity(type, indexField);
			if (type == FieldType.JOIN) {
				Asserts.notNull(indexField.relations(), "relations cannot be null for object fields");
				Asserts.check(indexField.relations().length != 0, "relations cannot be empty for object fields");
				Map<@Nullable String, @Nullable List<String>> relations = Maps.newHashMap();
				for (JoinTypeRelations relation : indexField.relations()) {
					relations.put(relation.parent(), Arrays.asList(relation.child()));
				}
				fieldMappingBuilder.relations(relations);
			}
			
			if (type == FieldType.NESTED || type == FieldType.OBJECT) {
				Asserts.check(!field.isAnnotationPresent(Fields.class),
						"Fields cannot be used with nested or object or join  fields");
				Map<String, FieldMapping> nestedProperties = Maps.newHashMap();
				Class<?> nestedClass = getNestedClass(indexField, type);
				if (nestedClass != null) {
					List<Field> allFields = getAllFields(nestedClass);
					for (Field nestedField : allFields) {
						FieldMapping nestedFieldMapping = buildFieldMapping(nestedField);
						if (nestedFieldMapping != null) {
							nestedProperties.put(nestedField.getName(), nestedFieldMapping);
						}
					}
					fieldMappingBuilder.properties(nestedProperties);
				}
			}
			
			if (field.isAnnotationPresent(Fields.class)) {
				Fields fields = field.getAnnotation(Fields.class);
				Map<String, FieldMapping> multiFields = new HashMap<>();
				for (IndexField nestedIndexField : fields.fields()) {
					FieldMapping nestedFieldMapping = buildFieldMapping(nestedIndexField);
					if (nestedFieldMapping != null) {
						multiFields.put(nestedIndexField.fieldName(), nestedFieldMapping);
					}
				}
				fieldMappingBuilder.fields(multiFields);
			}
			
		}
		return fieldMappingBuilder.build();
	}
	
	private static FieldMapping buildFieldMapping(IndexField indexField) {
		// 重载方法，用于处理 Fields 注解中的嵌套 IndexField
		FieldMapping.FieldMappingBuilder fieldMappingBuilder = FieldMapping.builder()
				.type(indexField.type().name().toLowerCase())
				.analyzer(indexField.analyzer().isEmpty() ? null : indexField.analyzer())
				.searchAnalyzer(indexField.search_analyzer().isEmpty() ? null : indexField.search_analyzer())
				.format(indexField.format().isEmpty() ? null : indexField.format())
				.ignoreAbove(indexField.ignore_above() == 0 ? null : indexField.ignore_above())
				.dims(indexField.dimension() == 0 ? null : indexField.dimension())
				.index(indexField.index() ? true : null)
				.similarity(indexField.similarity() == Similarity.NONE ? null : indexField.similarity().name().toLowerCase())
				.norms(indexField.norms() ? true : null)
				.metrics(indexField.metrics().length == 0 ? null : Arrays.stream(indexField.metrics())
						.map(Enum::name).map(String::toLowerCase).collect(Collectors.toList()))
				.defaultMetric(indexField.default_metric() == Metrics.VALUE_COUNT ? null : indexField.default_metric().name().toLowerCase())
				.timeSeriesMetric(indexField.time_series_metric() == TimeSeriesMetric.NONE ? null : indexField.time_series_metric().name().toLowerCase())
				.path(indexField.path().isEmpty() ? null : indexField.path())
				.docValues(indexField.doc_values() ? true : null)
				.store(indexField.store() ? true : null)
				.ignoreMalformed(indexField.ignore_malformed() ? true : null)
				.nullValue(indexField.null_value() == NullValue.NONE ? null : indexField.null_value().name().toLowerCase())
				.eagerGlobalOrdinals(indexField.eager_global_ordinals() ? true : null);
		
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
	}
	
	private static Class<?> getNestedClass(IndexField indexField, FieldType type) {
		switch (type) {
			case OBJECT:
				return indexField.object();
			case NESTED:
				return indexField.nested();
			case JOIN:
				return indexField.joinType();
			default:
				return null;
		}
	}
}