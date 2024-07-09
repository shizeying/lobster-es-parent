package com.lobster.es.test;

import com.lobster.es.annotation.Fields;
import com.lobster.es.annotation.IndexField;
import com.lobster.es.common.enums.FieldType;
import com.lobster.es.common.params.DefaultNestedClass;
import lombok.Data;

/**
 * @author zeyingshi
 * @date 05 7月 2024 01:51
 */
@Data
public class NestedField extends DefaultNestedClass {
	@IndexField(type = FieldType.KEYWORD, fieldName = "nestedFiel4",ignore_above = 512)
    @Fields(fields = {@IndexField(type = FieldType.TEXT, fieldName = "nestedFiel41",search_analyzer = "standard"),
        @IndexField(type = FieldType.TEXT, fieldName = "nestedFiel42")})
    private String nestedField;
}