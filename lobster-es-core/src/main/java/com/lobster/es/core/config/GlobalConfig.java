package com.lobster.es.core.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zeyingshi
 * @date 10 7月 2024 12:49
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GlobalConfig {
	/**
	 * 打印 dsl
	 */
	private boolean printDsl = false;
	/**
	 * 打印请求
	 */
	private boolean printRequest = false;
	/**
	 * 打印响应
	 */
	private boolean printResponse = false;
	/**
	 * 请求时间
	 */
	private boolean printRequestTime = false;

	
	
	
	
	
	
}