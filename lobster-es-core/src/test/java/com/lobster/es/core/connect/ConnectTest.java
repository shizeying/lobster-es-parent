package com.lobster.es.core.connect;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.http.HttpEntity;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReaderProvider;
import com.lobster.es.common.entity.indices.IndicesRecord;
import com.lobster.es.common.json.JsonAliasDeserializer;
import com.lobster.es.core.config.EsConnectConfig;
import com.lobster.es.core.config.SettingConfigProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zeyingshi
 * @date 11 7月 2024 13:21
 */
@Slf4j
public class ConnectTest {
    SettingConfigProperties settingConfigProperties;

    @BeforeEach
    public void init() {
        settingConfigProperties = new SettingConfigProperties();
        settingConfigProperties.setAddress("elasticsearch.grep.free.hr");
        settingConfigProperties.setSchema("https");
    }

    @Test
    public void connect() throws IOException {
        ObjectReaderProvider provider = new ObjectReaderProvider();
        provider.register(IndicesRecord.class, new JsonAliasDeserializer<>(IndicesRecord.class));
        EsConnectConfig instance = EsConnectConfig.getInstance(settingConfigProperties);
        RestClient restClient = instance.commonConnect();
        Request request = new Request("GET", "/_cat/indices?format=json");
        Response response = restClient.performRequest(request);
        

        // 获取响应实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try (InputStream inputStream = entity.getContent();
                JSONReader jsonReader = JSONReader.of(inputStream, StandardCharsets.UTF_8)) {


                // 使用 fastjson2 解析 JSON 响应为对象列表

                // 使用 fastjson2 流式处理 JSON 数据
                List<IndicesRecord> list = jsonReader.readArray(IndicesRecord.class);
                
                list.forEach(System.out::println);
            }
        }

    }
}