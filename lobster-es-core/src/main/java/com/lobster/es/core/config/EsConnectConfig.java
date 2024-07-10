package com.lobster.es.core.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.sniff.SniffOnFailureListener;
import org.elasticsearch.client.sniff.Sniffer;
import org.elasticsearch.client.sniff.SnifferBuilder;

import com.lobster.es.common.util.Asserts;

import lombok.NoArgsConstructor;

/**
 * Singleton class for EsConnectConfig. EsConnectConfig 单例类
 * 
 * @autor zeyingshi
 * @date 10 July 2024
 */
@NoArgsConstructor
public class EsConnectConfig {

    /**
     * Instance of SettingConfigProperties. 设置配置属性实例
     */
    private SettingConfigProperties settingConfigProperties;

    /**
     * Static instance of the singleton class. 静态单例实例
     */
    private static EsConnectConfig instance;

    /**
     * Private constructor to prevent instantiation. 私有构造函数，防止外部实例化
     */
    private EsConnectConfig(SettingConfigProperties settingConfigProperties) {
        this.settingConfigProperties = settingConfigProperties;
    }

    /**
     * Provides access to the singleton instance. 公共方法提供单例实例的访问
     * 
     * @return the single instance of EsConnectConfig
     */
    public static synchronized EsConnectConfig getInstance(SettingConfigProperties settingConfigProperties) {
        if (instance == null) {
            Asserts.notNull(settingConfigProperties, "SettingConfigProperties cannot be null.");
            instance = new EsConnectConfig(settingConfigProperties);
        }
        return instance;
    }

    /**
     * Gets the setting configuration properties. 获取设置配置属性
     * 
     * @return the setting configuration properties
     */
    public SettingConfigProperties getSettings() {
        return settingConfigProperties;
    }

    public RestClient commonConnect() {
        return null;
    }

    public RestClient snifferConnect() {
        SniffOnFailureListener sniffOnFailureListener = new SniffOnFailureListener();
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200))

            .setFailureListener(sniffOnFailureListener).build();
        SnifferBuilder builder = Sniffer.builder(restClient);
        if (this.settingConfigProperties.getSniffAfterFailureDelayMillis() > 0) {
            builder.setSniffAfterFailureDelayMillis(this.settingConfigProperties.getSniffAfterFailureDelayMillis());
        }
        Sniffer sniffer = builder.build();
        sniffOnFailureListener.setSniffer(sniffer);
        return restClient;
    }

}