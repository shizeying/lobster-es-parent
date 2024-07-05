package com.lobster.es.test;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.lobster.es.annotation.*;
import com.lobster.es.annotation.enums.*;
import com.lobster.es.common.util.Asserts;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class MappingConverter {

    public static void main(String[] args) {
        JSONObject jsonMapping = createMapping(TestIndex.class);
        String jsonString = JSON.toJSONString(jsonMapping, true);
        System.out.println(jsonString);
    }

    @SneakyThrows
    private static <T> JSONObject createMapping(Class<T> clazz) {
        Map<String, Object> properties = new HashMap<>();
        getAllFields(clazz).forEach(field -> {
            FieldMapping fieldMapping = buildFieldMapping(field);
            if (fieldMapping != null) {
                properties.put(field.getName(), fieldMapping);
            }
        });
        Map<String, Object> mappings = new HashMap<>();
        mappings.put("properties", properties);
        Map<String, Object> root = new HashMap<>();
        root.put("mappings", mappings);
        return new JSONObject(root);
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

            if (type == FieldType.OBJECT || type == FieldType.NESTED || type == FieldType.JOIN) {
                Map<String, FieldMapping> nestedProperties = new HashMap<>();
                Class<?> nestedClass = getNestedClass(indexField, type);
                if (nestedClass != null) {
                    getAllFields(nestedClass).forEach(nestedField -> {
                        FieldMapping nestedFieldMapping = buildFieldMapping(nestedField);
                        if (nestedFieldMapping != null) {
                            nestedProperties.put(nestedField.getName(), nestedFieldMapping);
                        }
                    });
                    fieldMappingBuilder.properties(nestedProperties);
                }
            }

            if (field.isAnnotationPresent(Fields.class)) {
                Fields fields = field.getAnnotation(Fields.class);
                for (IndexField nestedIndexField : fields.fields()) {
                    FieldMapping nestedFieldMapping = buildFieldMapping(nestedIndexField);
                    if (nestedFieldMapping != null) {
                        fieldMappingBuilder.properties(nestedFieldMapping.properties());
                    }
                }
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