package com.lobster.es.core.mapping;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.PropertyFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author w7410
 * @date 09 7月 2024 01:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mappings {
	
	private Mapping mappings;
	
	@Override
	public String toString() {
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
		return JSON.toJSONString(this, propertyFilter,
				JSONWriter.Feature.PrettyFormat,
				JSONWriter.Feature.WriteMapNullValue,
				JSONWriter.Feature.WriteNulls);
		
		
	}
}