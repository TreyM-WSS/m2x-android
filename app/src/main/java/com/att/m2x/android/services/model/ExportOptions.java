package com.att.m2x.android.services.model;

import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by collinbrown on 10/15/17.
 */

public class ExportOptions {
    private Integer limit;
    private Set<String> streams;
    private Boolean includeLocation;

    public ExportOptions() {
    }

    public ExportOptions(Integer limit, Set<String> streams, Boolean includeLocation) {
        this.limit = limit;
        this.streams = streams;
        this.includeLocation = includeLocation;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Set<String> getStreams() {
        return streams;
    }

    public void setStreams(Set<String> streams) {
        this.streams = streams;
    }

    public Boolean getIncludeLocation() {
        return includeLocation;
    }

    public void setIncludeLocation(Boolean includeLocation) {
        this.includeLocation = includeLocation;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        if (limit != null) map.put("limit", limit.toString());
        if (ArrayUtils.isNotEmpty(streams)) map.put("streams", StringUtils.join(streams, ","));
        if (includeLocation != null) map.put("include_location", includeLocation.toString());
        return map;
    }
}
