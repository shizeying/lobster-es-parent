package com.lobster.es.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.PropertyFilter;
import com.lobster.es.core.indexing.Mappings;

import java.util.*;

import static com.lobster.es.core.indexing.IndexMappingConverter.createIndexMapping;

public class MappingConverterTest {
	
	public static void main(String[] args) {
		Mappings jsonMapping = createIndexMapping(com.lobster.es.test.TestIndex.class);
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