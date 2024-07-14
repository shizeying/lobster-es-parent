package com.lobster.es.common.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.PropertyFilter;

import java.util.Collection;
import java.util.Map;

/**
 * @author zeyingshi
 * @date 14 7月 2024 01:17
 */
public final class ConvertUtils {
    public static <T> String prettyFormat(T entity) {
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
        return JSON.toJSONString(entity, propertyFilter, JSONWriter.Feature.PrettyFormat,
                JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNulls);
	}
}