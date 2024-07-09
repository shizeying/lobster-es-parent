package com.lobster.es.common.constants;

/**
 * @author zeyingshi
 * @date 05 7月 2024 01:33
 */
public final class BaseEsConstants {
	/**
	 * 默认分片数
	 */
	public static final int DEFAULT_SHARDS = -1;
	
	/**
	 * 默认副本数
	 */
	public static final int DEFAULT_REPLICAS = -1;
	
	
	/**
	 * REFRESH_INTERVAL
	 */
	public static final String DEFAULT_REFRESH_INTERVAL = "-1";
	
	public static final String INDEX_REFRESH_INTERVAL_KEY = "index.refresh_interval";
	public static final String INDEX_ALIAS_KEY = "index.alias";
	public static final String INDEX_SHARDS_KEY = "index.number_of_shards";
	public static final String INDEX_REPLICAS_KEY = "index.number_of_replicas";
}