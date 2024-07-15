package com.lobster.es.common.entity.search.highlight;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zeyingshi
 * @date 15 7月 2024 08:43
 */
@AllArgsConstructor
@Getter
public enum BoundaryScanner {
    /**
     * chars
     */
    CHARS("chars"),
    /**
     * sentence
     */
    SENTENCE("sentence"),
    /**
     * word
     */
    WORD("word");

    private final String value;

}