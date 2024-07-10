package com.lobster.es.core.config;

import com.lobster.es.common.enums.SchemaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Configuration properties class for SettingConfigProperties.
 * SettingConfigProperties 配置属性类
 *
 * @autor zeyingshi
 * @date 10 July 2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingConfigProperties {
    /**
     * ES client address.
     * es客户端地址
     */
    private String address = "127.0.0.1:9200";
    
    /**
     * Schema.
     * 模式
     */
    private String schema = SchemaEnum.HTTP.name().toLowerCase();
    
    /**
     * Username of ES, optional.
     * 用户名,可缺省
     */
    private String username;
    
    /**
     * Password of ES, optional.
     * 密码,可缺省
     */
    private String password;
    
    /**
     * Maximum number of connections.
     * 最大连接数
     */
    private Integer maxConnTotal;
    
    /**
     * Maximum number of connections per route.
     * 最大连接路由数
     */
    private Integer maxConnPerRoute;
    
    /**
     * Connection timeout, in milliseconds.
     * 连接超时时间, 单位毫秒
     */
    private Integer connectTimeout;
    
    /**
     * Socket timeout, in milliseconds, default is 10 minutes.
     * 通讯超时时间, 单位毫秒, 默认10分钟
     */
    private Integer socketTimeout = 600000;
    
    /**
     * Keep-alive time, in milliseconds, default is 30 seconds.
     * 保持心跳时间, 单位毫秒, 默认30秒
     */
    private Integer keepAliveMillis = 30000;
    
    /**
     * Connection request timeout, in milliseconds.
     * 连接请求超时时间, 单位毫秒
     */
    private Integer connectionRequestTimeout;
    
    
    private Integer sniffAfterFailureDelayMillis=-1;

  
   
}