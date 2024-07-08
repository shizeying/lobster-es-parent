package com.lobster.es.annotation.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zeyingshi
 * @date 05 7月 2024 00:35
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultJoinClass implements Serializable {
	private static final long serialVersionUID = 1L;
	private String parent;
	private String name;
}