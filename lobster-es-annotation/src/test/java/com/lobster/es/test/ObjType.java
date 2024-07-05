
package com.lobster.es.test;

import com.lobster.es.annotation.Fields;
import com.lobster.es.annotation.IndexField;
import com.lobster.es.annotation.enums.FieldType;

import com.lobster.es.annotation.params.DefaultObjectClass;
import lombok.Data;

/**
 * @author zeyingshi
 * @date 05 7月 2024 01:52
 */
@Data
public class ObjType  extends DefaultObjectClass {
    @IndexField(type = FieldType.TEXT, fieldName = "objField3")
    @Fields(fields = {@IndexField(type = FieldType.TEXT, fieldName = "objField1"),
        @IndexField(type = FieldType.TEXT, fieldName = "objField2")})
    private String objField;
}