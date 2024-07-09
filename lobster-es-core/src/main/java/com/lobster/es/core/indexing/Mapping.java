package com.lobster.es.core.indexing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author zeyingshi
 * @date 05 7月 2024 02:05
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mapping {
    private Map<String, FieldMapping> properties;
}