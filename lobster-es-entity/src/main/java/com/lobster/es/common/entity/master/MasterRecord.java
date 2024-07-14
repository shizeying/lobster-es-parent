package com.lobster.es.common.entity.master;

import com.alibaba.fastjson2.annotation.JSONType;
import com.lobster.es.common.json.JsonAlias;
import com.lobster.es.common.util.ConvertUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zeyingshi
 * @date 14 7月 2024 01:15
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JSONType
public class MasterRecord {
    @JsonAlias("id")
    private String id;
    @JsonAlias(value = {"host", "h"})
    private String host;
    @JsonAlias(value = "ip")
    private String ip;
    @JsonAlias(value = {"node", "n"})
    private String node;

    @Override
    public String toString() {
        return ConvertUtils.prettyFormat(this);
    }
}