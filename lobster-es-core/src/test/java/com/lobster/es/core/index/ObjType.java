
package com.lobster.es.core.index;

import com.lobster.es.annotation.Fields;
import com.lobster.es.annotation.IndexField;
import com.lobster.es.common.enums.FieldType;
import com.lobster.es.common.params.DefaultObjectClass;

import lombok.Data;

/**
 * @author zeyingshi
 * @date 05 7月 2024 01:52
 */
@Data
public class ObjType extends DefaultObjectClass {
    @IndexField(type = FieldType.TEXT, fieldName = "objField3")
    @Fields(fields = {@IndexField(type = FieldType.TEXT, fieldName = "objField1", search_analyzer = "standard"),
        @IndexField(type = FieldType.TEXT, fieldName = "objField2")})
    private String objField;
    @IndexField(type = FieldType.TEXT, fieldName = "obj2Field3")
    @Fields(fields = {@IndexField(type = FieldType.TEXT, fieldName = "obj2Field1"),
        @IndexField(type = FieldType.TEXT, fieldName = "obj2Field2")})
    private String obj2Field;
}