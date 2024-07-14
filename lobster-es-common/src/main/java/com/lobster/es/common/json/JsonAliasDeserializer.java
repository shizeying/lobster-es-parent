package com.lobster.es.common.json;

/**
 * @author zeyingshi
 * @date 13 7月 2024 02:34
 */
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.reader.ObjectReader;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class JsonAliasDeserializer<T> implements ObjectReader<T> {
    private final Class<T> clazz;

    public JsonAliasDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * @param jsonReader
     * @param fieldType
     * @param fieldName
     * @param features
     * @return {@link T}
     * @throws JSONException If a suitable ObjectReader is not found
     */
    @Override
    public T readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
        JSONObject jsonObject = jsonReader.read(JSONObject.class);
        try {
            T instance = clazz.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                JsonAlias alias = field.getAnnotation(JsonAlias.class);
                if (alias != null) {
                    for (String key : alias.value()) {
                        if (jsonObject.containsKey(key)) {
                            field.setAccessible(true);
                            field.set(instance, jsonObject.get(key));
                            break;
                        }
                    }
                }
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JSONException("Error creating instance of " + clazz.getName(), e);
        }
    }
}