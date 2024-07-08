package com.lobster.es.core.mapping;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * @author zeyingshi
 * @date 05 7月 2024 02:05
 */
@Data
@Builder
public class Mapping {
    private Map<String, FieldMapping> properties;
}