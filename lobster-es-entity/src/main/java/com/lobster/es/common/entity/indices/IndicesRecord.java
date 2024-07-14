package com.lobster.es.common.entity.indices;

import com.alibaba.fastjson2.annotation.JSONType;
import com.lobster.es.common.health.ClusterHealthStatus;
import com.lobster.es.common.json.JsonAlias;
import com.lobster.es.common.util.ConvertUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JSONType
public class IndicesRecord {

    @JsonAlias({"health", "h"})
    private ClusterHealthStatus health;

    @JsonAlias({"status", "s"})
    private String status;

    @JsonAlias({"index", "i", "idx"})
    private String index;

    @JsonAlias({"uuid", "id"})
    private String uuid;

    @JsonAlias({"pri", "p", "shards.primary", "shardsPrimary"})
    private String pri;

    @JsonAlias({"rep", "r", "shards.replica", "shardsReplica"})
    private String rep;

    @JsonAlias({"docs.count", "dc", "docsCount"})
    private String docsCount;

    @JsonAlias({"docs.deleted", "dd", "docsDeleted"})
    private String docsDeleted;

    @JsonAlias({"creation.date", "cd"})
    private String creationDate;

    @JsonAlias({"creation.date.string", "cds"})
    private String creationDateString;

    @JsonAlias({"store.size", "ss", "storeSize"})
    private String storeSize;

    @JsonAlias({"pri.store.size"})
    private String priStoreSize;

    @JsonAlias({"completion.size", "cs", "completionSize"})
    private String completionSize;

    @JsonAlias({"pri.completion.size"})
    private String priCompletionSize;

    @JsonAlias({"fielddata.memory_size", "fm", "fielddataMemory"})
    private String fielddataMemorySize;

    @JsonAlias({"pri.fielddata.memory_size"})
    private String priFielddataMemorySize;

    @JsonAlias({"fielddata.evictions", "fe", "fielddataEvictions"})
    private String fielddataEvictions;

    @JsonAlias({"pri.fielddata.evictions"})
    private String priFielddataEvictions;

    @JsonAlias({"query_cache.memory_size", "qcm", "queryCacheMemory"})
    private String queryCacheMemorySize;

    @JsonAlias({"pri.query_cache.memory_size"})
    private String priQueryCacheMemorySize;

    @JsonAlias({"query_cache.evictions", "qce", "queryCacheEvictions"})
    private String queryCacheEvictions;

    @JsonAlias({"pri.query_cache.evictions"})
    private String priQueryCacheEvictions;

    @JsonAlias({"request_cache.memory_size", "rcm", "requestCacheMemory"})
    private String requestCacheMemorySize;

    @JsonAlias({"pri.request_cache.memory_size"})
    private String priRequestCacheMemorySize;

    @JsonAlias({"request_cache.evictions", "rce", "requestCacheEvictions"})
    private String requestCacheEvictions;

    @JsonAlias({"pri.request_cache.evictions"})
    private String priRequestCacheEvictions;

    @JsonAlias({"request_cache.hit_count", "rchc", "requestCacheHitCount"})
    private String requestCacheHitCount;

    @JsonAlias({"pri.request_cache.hit_count"})
    private String priRequestCacheHitCount;

    @JsonAlias({"request_cache.miss_count", "rcmc", "requestCacheMissCount"})
    private String requestCacheMissCount;

    @JsonAlias({"pri.request_cache.miss_count"})
    private String priRequestCacheMissCount;

    @JsonAlias({"flush.total", "ft", "flushTotal"})
    private String flushTotal;

    @JsonAlias({"pri.flush.total"})
    private String priFlushTotal;

    @JsonAlias({"flush.total_time", "ftt", "flushTotalTime"})
    private String flushTotalTime;

    @JsonAlias({"pri.flush.total_time"})
    private String priFlushTotalTime;

    @JsonAlias({"get.current", "gc", "getCurrent"})
    private String getCurrent;

    @JsonAlias({"pri.get.current"})
    private String priGetCurrent;

    @JsonAlias({"get.time", "gti", "getTime"})
    private String getTime;

    @JsonAlias({"pri.get.time"})
    private String priGetTime;

    @JsonAlias({"get.total", "gto", "getTotal"})
    private String getTotal;

    @JsonAlias({"pri.get.total"})
    private String priGetTotal;

    @JsonAlias({"get.exists_time", "geti", "getExistsTime"})
    private String getExistsTime;

    @JsonAlias({"pri.get.exists_time"})
    private String priGetExistsTime;

    @JsonAlias({"get.exists_total", "geto", "getExistsTotal"})
    private String getExistsTotal;

    @JsonAlias({"pri.get.exists_total"})
    private String priGetExistsTotal;

    @JsonAlias({"get.missing_time", "gmti", "getMissingTime"})
    private String getMissingTime;

    @JsonAlias({"pri.get.missing_time"})
    private String priGetMissingTime;

    @JsonAlias({"get.missing_total", "gmto", "getMissingTotal"})
    private String getMissingTotal;

    @JsonAlias({"pri.get.missing_total"})
    private String priGetMissingTotal;

    @JsonAlias({"indexing.delete_current", "idc", "indexingDeleteCurrent"})
    private String indexingDeleteCurrent;

    @JsonAlias({"pri.indexing.delete_current"})
    private String priIndexingDeleteCurrent;

    @JsonAlias({"indexing.delete_time", "idti", "indexingDeleteTime"})
    private String indexingDeleteTime;

    @JsonAlias({"pri.indexing.delete_time"})
    private String priIndexingDeleteTime;

    @JsonAlias({"indexing.delete_total", "idto", "indexingDeleteTotal"})
    private String indexingDeleteTotal;

    @JsonAlias({"pri.indexing.delete_total"})
    private String priIndexingDeleteTotal;

    @JsonAlias({"indexing.index_current", "iic", "indexingIndexCurrent"})
    private String indexingIndexCurrent;

    @JsonAlias({"pri.indexing.index_current"})
    private String priIndexingIndexCurrent;

    @JsonAlias({"indexing.index_time", "iiti", "indexingIndexTime"})
    private String indexingIndexTime;

    @JsonAlias({"pri.indexing.index_time"})
    private String priIndexingIndexTime;

    @JsonAlias({"indexing.index_total", "iito", "indexingIndexTotal"})
    private String indexingIndexTotal;

    @JsonAlias({"pri.indexing.index_total"})
    private String priIndexingIndexTotal;

    @JsonAlias({"indexing.index_failed", "iif", "indexingIndexFailed"})
    private String indexingIndexFailed;

    @JsonAlias({"pri.indexing.index_failed"})
    private String priIndexingIndexFailed;

    @JsonAlias({"merges.current", "mc", "mergesCurrent"})
    private String mergesCurrent;

    @JsonAlias({"pri.merges.current"})
    private String priMergesCurrent;

    @JsonAlias({"merges.current_docs", "mcd", "mergesCurrentDocs"})
    private String mergesCurrentDocs;

    @JsonAlias({"pri.merges.current_docs"})
    private String priMergesCurrentDocs;

    @JsonAlias({"merges.current_size", "mcs", "mergesCurrentSize"})
    private String mergesCurrentSize;

    @JsonAlias({"pri.merges.current_size"})
    private String priMergesCurrentSize;

    @JsonAlias({"merges.total", "mt", "mergesTotal"})
    private String mergesTotal;

    @JsonAlias({"pri.merges.total"})
    private String priMergesTotal;

    @JsonAlias({"merges.total_docs", "mtd", "mergesTotalDocs"})
    private String mergesTotalDocs;

    @JsonAlias({"pri.merges.total_docs"})
    private String priMergesTotalDocs;

    @JsonAlias({"merges.total_size", "mts", "mergesTotalSize"})
    private String mergesTotalSize;

    @JsonAlias({"pri.merges.total_size"})
    private String priMergesTotalSize;

    @JsonAlias({"merges.total_time", "mtt", "mergesTotalTime"})
    private String mergesTotalTime;

    @JsonAlias({"pri.merges.total_time"})
    private String priMergesTotalTime;

    @JsonAlias({"refresh.total", "rto", "refreshTotal"})
    private String refreshTotal;

    @JsonAlias({"pri.refresh.total"})
    private String priRefreshTotal;

    @JsonAlias({"refresh.time", "rti", "refreshTime"})
    private String refreshTime;

    @JsonAlias({"pri.refresh.time"})
    private String priRefreshTime;

    @JsonAlias({"refresh.external_total"})
    private String refreshExternalTotal;

    @JsonAlias({"pri.refresh.external_total"})
    private String priRefreshExternalTotal;

    @JsonAlias({"refresh.external_time"})
    private String refreshExternalTime;

    @JsonAlias({"pri.refresh.external_time"})
    private String priRefreshExternalTime;

    @JsonAlias({"refresh.listeners", "rli", "refreshListeners"})
    private String refreshListeners;

    @JsonAlias({"pri.refresh.listeners"})
    private String priRefreshListeners;

    @JsonAlias({"search.fetch_current", "sfc", "searchFetchCurrent"})
    private String searchFetchCurrent;

    @JsonAlias({"pri.search.fetch_current"})
    private String priSearchFetchCurrent;

    @JsonAlias({"search.fetch_time", "sfti", "searchFetchTime"})
    private String searchFetchTime;

    @JsonAlias({"pri.search.fetch_time"})
    private String priSearchFetchTime;

    @JsonAlias({"search.fetch_total", "sfto", "searchFetchTotal"})
    private String searchFetchTotal;

    @JsonAlias({"pri.search.fetch_total"})
    private String priSearchFetchTotal;

    @JsonAlias({"search.open_contexts", "so", "searchOpenContexts"})
    private String searchOpenContexts;

    @JsonAlias({"pri.search.open_contexts"})
    private String priSearchOpenContexts;

    @JsonAlias({"search.query_current", "sqc", "searchQueryCurrent"})
    private String searchQueryCurrent;

    @JsonAlias({"pri.search.query_current"})
    private String priSearchQueryCurrent;

    @JsonAlias({"search.query_time", "sqti", "searchQueryTime"})
    private String searchQueryTime;

    @JsonAlias({"pri.search.query_time"})
    private String priSearchQueryTime;

    @JsonAlias({"search.query_total", "sqto", "searchQueryTotal"})
    private String searchQueryTotal;

    @JsonAlias({"pri.search.query_total"})
    private String priSearchQueryTotal;

    @JsonAlias({"search.scroll_current", "scc", "searchScrollCurrent"})
    private String searchScrollCurrent;

    @JsonAlias({"pri.search.scroll_current"})
    private String priSearchScrollCurrent;

    @JsonAlias({"search.scroll_time", "scti", "searchScrollTime"})
    private String searchScrollTime;

    @JsonAlias({"pri.search.scroll_time"})
    private String priSearchScrollTime;

    @JsonAlias({"search.scroll_total", "scto", "searchScrollTotal"})
    private String searchScrollTotal;

    @JsonAlias({"pri.search.scroll_total"})
    private String priSearchScrollTotal;

    @JsonAlias({"segments.count", "sc", "segmentsCount"})
    private String segmentsCount;

    @JsonAlias({"pri.segments.count"})
    private String priSegmentsCount;

    @JsonAlias({"segments.memory", "sm", "segmentsMemory"})
    private String segmentsMemory;

    @JsonAlias({"pri.segments.memory"})
    private String priSegmentsMemory;

    @JsonAlias({"segments.index_writer_memory", "siwm", "segmentsIndexWriterMemory"})
    private String segmentsIndexWriterMemory;

    @JsonAlias({"pri.segments.index_writer_memory"})
    private String priSegmentsIndexWriterMemory;

    @JsonAlias({"segments.version_map_memory", "svmm", "segmentsVersionMapMemory"})
    private String segmentsVersionMapMemory;

    @JsonAlias({"pri.segments.version_map_memory"})
    private String priSegmentsVersionMapMemory;

    @JsonAlias({"segments.fixed_bitset_memory", "sfbm", "fixedBitsetMemory"})
    private String segmentsFixedBitsetMemory;

    @JsonAlias({"pri.segments.fixed_bitset_memory"})
    private String priSegmentsFixedBitsetMemory;

    @JsonAlias({"warmer.current", "wc", "warmerCurrent"})
    private String warmerCurrent;

    @JsonAlias({"pri.warmer.current"})
    private String priWarmerCurrent;

    @JsonAlias({"warmer.total", "wto", "warmerTotal"})
    private String warmerTotal;

    @JsonAlias({"pri.warmer.total"})
    private String priWarmerTotal;

    @JsonAlias({"warmer.total_time", "wtt", "warmerTotalTime"})
    private String warmerTotalTime;

    @JsonAlias({"pri.warmer.total_time"})
    private String priWarmerTotalTime;

    @JsonAlias({"suggest.current", "suc", "suggestCurrent"})
    private String suggestCurrent;

    @JsonAlias({"pri.suggest.current"})
    private String priSuggestCurrent;

    @JsonAlias({"suggest.time", "suti", "suggestTime"})
    private String suggestTime;

    @JsonAlias({"pri.suggest.time"})
    private String priSuggestTime;

    @JsonAlias({"suggest.total", "suto", "suggestTotal"})
    private String suggestTotal;

    @JsonAlias({"pri.suggest.total"})
    private String priSuggestTotal;

    @JsonAlias({"memory.total", "tm", "memoryTotal"})
    private String memoryTotal;

    @JsonAlias({"pri.memory.total"})
    private String priMemoryTotal;

    @JsonAlias({"search.throttled"})
    private String searchThrottled;

    @JsonAlias({"bulk.total_operations", "bto", "bulkTotalOperation"})
    private String bulkTotalOperations;

    @JsonAlias({"pri.bulk.total_operations"})
    private String priBulkTotalOperations;

    @JsonAlias({"bulk.total_time", "btti", "bulkTotalTime"})
    private String bulkTotalTime;

    @JsonAlias({"pri.bulk.total_time"})
    private String priBulkTotalTime;

    @JsonAlias({"bulk.total_size_in_bytes", "btsi", "bulkTotalSizeInBytes"})
    private String bulkTotalSizeInBytes;

    @JsonAlias({"pri.bulk.total_size_in_bytes"})
    private String priBulkTotalSizeInBytes;

    @JsonAlias({"bulk.avg_time", "bati", "bulkAvgTime"})
    private String bulkAvgTime;

    @JsonAlias({"pri.bulk.avg_time"})
    private String priBulkAvgTime;

    @JsonAlias({"bulk.avg_size_in_bytes", "basi", "bulkAvgSizeInBytes"})
    private String bulkAvgSizeInBytes;

    @JsonAlias({"pri.bulk.avg_size_in_bytes"})
    private String priBulkAvgSizeInBytes;

    @Override
    public String toString() {
        return ConvertUtils.prettyFormat(this);
    }
}