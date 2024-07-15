/*
 * Licensed to Elasticsearch B.V. under one or more contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership. Elasticsearch B.V. licenses this file to you
 * under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.lobster.es.common.entity.search.highlight;

import java.util.List;
import java.util.Map;


import com.lobster.es.common.entity.search.query.Query;
import com.lobster.es.common.json.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

// ----------------------------------------------------------------
// THIS CODE IS GENERATED. MANUAL EDITS WILL BE LOST.
// ----------------------------------------------------------------
//
// This code is generated from the Elasticsearch API specification
// at https://github.com/elastic/elasticsearch-specification
//
// Manual updates to this file will be lost when the code is
// re-generated.
//
// If you find a property that is missing or wrongly typed, please
// open an issue or a PR on the API specification repository.
//
// ----------------------------------------------------------------

// typedef: _global.search._types.HighlightBase

/**
 *
 * @see <a href= "../../doc-files/api-spec.html#_global.search._types.HighlightBase">API specification</a>
 */

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class HighlightBase {

    @JsonAlias({"type"})
    private String type;

    @JsonAlias({"boundary_chars"})
    private String boundaryChars;

    @JsonAlias({"boundary_max_scan"})
    private Integer boundaryMaxScan;

    @JsonAlias({"boundary_scanner"})
    private BoundaryScanner boundaryScanner;

    @JsonAlias({"boundary_scanner_locale"})
    private String boundaryScannerLocale;

    @JsonAlias({"force_source"})
    private Boolean forceSource;

    @JsonAlias({"fragmenter"})
    private HighlighterFragmenter fragmenter;

    @JsonAlias({"fragment_size"})
    private Integer fragmentSize;

    @JsonAlias({"highlight_filter"})
    private Boolean highlightFilter;

    @JsonAlias({"highlight_query"})
    private Query highlightQuery;

    @JsonAlias({"max_fragment_length"})
    private Integer maxFragmentLength;

    @JsonAlias({"max_analyzed_offset"})

    private Integer maxAnalyzedOffset;

    @JsonAlias({"no_match_size"})
    private Integer noMatchSize;

    @JsonAlias({"number_of_fragments"})
    private Integer numberOfFragments;

    @JsonAlias({"options"})
    private Map<String, Object> options;

    @JsonAlias({"order"})
    private HighlighterOrder order;

    @JsonAlias({"phrase_limit"})
    private Integer phraseLimit;

    @JsonAlias({"post_tags"})
    private List<String> postTags;

    @JsonAlias({"pre_tags"})
    private List<String> preTags;

    @JsonAlias({"require_field_match"})
    private Boolean requireFieldMatch;

    @JsonAlias({"tags_schema"})
    private HighlighterTagsSchema tagsSchema;

}