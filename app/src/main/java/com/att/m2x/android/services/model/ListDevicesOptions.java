package com.att.m2x.android.services.model;

import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by collinbrown on 10/15/17.
 */

public class ListDevicesOptions {
    private Integer limit;
    private Integer page;
    private String includeStreamValues;
    private Boolean includeMetaData;
    private DeviceSort sort;
    private SortDirection sortDirection;

    public ListDevicesOptions() {
    }

    public ListDevicesOptions(Integer limit, Integer page, Boolean includeStreamValues,
                              Boolean includeMetaData, DeviceSort sort, SortDirection sortDirection) {
        this.limit = limit;
        this.page = page;
        if (includeStreamValues != null) this.includeStreamValues = includeStreamValues.toString();
        this.includeMetaData = includeMetaData;
        this.sort = sort;
        this.sortDirection = sortDirection;
    }

    public ListDevicesOptions(Integer limit, Integer page, Set<String> includeStreamValues,
                              Boolean includeMetaData, DeviceSort sort, SortDirection sortDirection) {
        this.limit = limit;
        this.page = page;
        if (includeStreamValues != null) this.includeStreamValues = StringUtils.join(includeStreamValues, ",");
        this.includeMetaData = includeMetaData;
        this.sort = sort;
        this.sortDirection = sortDirection;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getIncludeStreamValues() {
        return includeStreamValues;
    }

    public void setIncludeStreamValues(Boolean includeStreamValues) {
        this.includeStreamValues = includeStreamValues == null ? null : includeStreamValues.toString();
    }

    public void setIncludeStreamValues(Set<String> includeStreamValues) {
        this.includeStreamValues = includeStreamValues == null ? null : StringUtils.join(includeStreamValues, ",");
    }

    public Boolean getIncludeMetaData() {
        return includeMetaData;
    }

    public void setIncludeMetaData(Boolean includeMetaData) {
        this.includeMetaData = includeMetaData;
    }

    public DeviceSort getSort() {
        return sort;
    }

    public void setSort(DeviceSort sort) {
        this.sort = sort;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        if (limit != null) map.put("limit", limit.toString());
        if (page != null) map.put("page", page.toString());
        if (StringUtils.hasText(includeStreamValues)) map.put("include_stream_values", includeStreamValues);
        if (includeMetaData != null) map.put("include_metadata", includeMetaData.toString());
        if (sort != null) {
            map.put("sort", sort.fieldName());
        }
        if (sortDirection != null) {
            map.put("dir", sortDirection.direction());
        }
        return map;
    }
}
