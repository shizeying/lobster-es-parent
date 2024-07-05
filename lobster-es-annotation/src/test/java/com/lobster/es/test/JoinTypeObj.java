package com.lobster.es.test;

import com.lobster.es.annotation.Fields;
import com.lobster.es.annotation.IndexField;
import com.lobster.es.annotation.enums.FieldType;
import com.lobster.es.annotation.params.DefaultJoinType;

import lombok.Data;

/**
 * @author zeyingshi
 * @date 05 7月 2024 01:52
 */
@Data
public class JoinTypeObj extends DefaultJoinType {
    @IndexField(type = FieldType.TEXT, fieldName = "joinField3")
    @Fields(fields = {@IndexField(type = FieldType.TEXT, fieldName = "joinField1"),
        @IndexField(type = FieldType.TEXT, fieldName = "joinField2")})
    private String joinField;
}