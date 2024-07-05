package com.lobster.es.annotation.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zeyingshi
 * @date 05 7月 2024 00:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultJoinType implements Serializable {
	private static final long serialVersionUID = 1L;
	private String parent;
	private String name;
}