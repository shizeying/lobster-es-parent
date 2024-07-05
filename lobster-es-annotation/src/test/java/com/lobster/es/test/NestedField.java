package com.lobster.es.test;

import com.lobster.es.annotation.Fields;
import com.lobster.es.annotation.IndexField;
import com.lobster.es.annotation.enums.FieldType;
import com.lobster.es.annotation.params.DefaultNestedClass;
import lombok.Data;

/**
 * @author zeyingshi
 * @date 05 7月 2024 01:51
 */
@Data
public class NestedField extends DefaultNestedClass {
	@IndexField(type = FieldType.TEXT, fieldName = "NestedField3")
    @Fields(fields = {@IndexField(type = FieldType.TEXT, fieldName = "NestedField1"),
        @IndexField(type = FieldType.TEXT, fieldName = "NestedField2")})
    private String nestedField;
}