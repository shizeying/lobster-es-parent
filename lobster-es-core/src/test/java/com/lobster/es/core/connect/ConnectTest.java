package com.lobster.es.core.connect;

import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public  void connect(){
        EsConnectConfig instance = EsConnectConfig.getInstance(settingConfigProperties);
        RestClient restClient = instance.commonConnect();
        restClient.
    }
}