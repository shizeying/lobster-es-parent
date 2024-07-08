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
import com.lobster.es.annotation.params.DefaultJoinClass;
import com.lobster.es.common.util.Asserts;
import com.lobster.es.core.mapping.FieldMapping;
import com.lobster.es.core.mapping.Mapping;
import com.lobster.es.core.mapping.Mappings;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.lobster.es.core.mapping.MappingConverter.createMapping;

public class MappingConverterTest {
	
	public static void main(String[] args) {
		Mappings jsonMapping = createMapping(com.lobster.es.test.TestIndex.class);
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
	
}