package com.lobster.es.common.entity.search.highlight;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zeyingshi
 * @date 15 7月 2024 08:44
 */
@AllArgsConstructor
@Getter
public enum HighlighterOrder {
		Score("score"),

	;

	private final String jsonValue;
}