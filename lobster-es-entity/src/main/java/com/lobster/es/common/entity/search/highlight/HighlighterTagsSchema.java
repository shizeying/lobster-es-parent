package com.lobster.es.common.entity.search.highlight;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zeyingshi
 * @date 15 7月 2024 08:45
 */
@AllArgsConstructor
@Getter
public enum HighlighterTagsSchema {
    Styled("styled"),

    ;

    private final String jsonValue;
}