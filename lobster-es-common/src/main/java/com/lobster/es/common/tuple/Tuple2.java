package com.lobster.es.common.tuple;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author w7410
 * @date 09 7月 2024 02:10
 */
@Data
@AllArgsConstructor
public class Tuple2<T1, T2> {
    private final T1 first;
    private final T2 second;
}