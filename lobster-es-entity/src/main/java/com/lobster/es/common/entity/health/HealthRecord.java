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

package com.lobster.es.common.entity.health;

import com.alibaba.fastjson2.annotation.JSONType;
import com.lobster.es.common.json.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

// typedef: cat.health.HealthRecord

/**
 *
 * @see <a href="../../doc-files/api-spec.html#cat.health.HealthRecord">API specification</a>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JSONType
public class HealthRecord {
    @JsonAlias({"epoch", "time"})
    private Long epoch;
    @JsonAlias({"timestamp", "ts", "hms", "hhmmss"})
    private String timestamp;
    @JsonAlias({"cluster", "cl"})

    private String cluster;

    @JsonAlias({"status", "st"})
    private String status;
    @JsonAlias({"node.total", "nt", "nodeTotal"})

    private String nodeTotal;

    @JsonAlias({"node.data", "nd", "nodeData"})
    private String nodeData;

    @JsonAlias({"shards", "t", "sh", "shards.total", "shardsTotal"})
    private String shards;

    @JsonAlias({"pri", "p", "shards.primary", "shardsPrimary"})
    private String pri;
    @JsonAlias({"relo", "r", "shards.relocating", "shardsRelocating"})
    private String relo;

    @JsonAlias({"init", "i", "shards.initializing", "shardsInitializing"})
    private String init;

    @JsonAlias({"unassign", "u", "shards.unassigned", "shardsUnassigned"})
    private String unassign;

    @JsonAlias({"pending_tasks", "pt", "pendingTasks"})
    private String pendingTasks;

    @JsonAlias({"max_task_wait_time", "mtwt", "maxTaskWaitTime"})
    private String maxTaskWaitTime;

    @JsonAlias({"active_shards_percent", "asp", "activeShardsPercent"})
    private String activeShardsPercent;

}