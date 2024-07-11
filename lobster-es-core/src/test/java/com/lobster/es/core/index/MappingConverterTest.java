package com.lobster.es.core.index;

import static com.lobster.es.core.indexing.IndexMappingConverter.createIndexMapping;

import org.junit.jupiter.api.Test;

import com.lobster.es.core.indexing.Mappings;

public class MappingConverterTest {
    @Test
    public void createMappingTest() {
        Mappings mappings = createIndexMapping(TestIndex.class);

        System.out.println(mappings);
    }

}