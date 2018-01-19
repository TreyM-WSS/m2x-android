package com.att.m2x.android.services.model;

import com.att.m2x.android.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by collinbrown on 10/15/17.
 */

public class ListCollectionDevicesOptions {
    private Boolean includeChildren;
    private Integer page;
    private Integer limit;
    private Sort sort;
    private SortDirection sortDirection;
    private String includeStreamValues;

    public ListCollectionDevicesOptions() {
    }

    public ListCollectionDevicesOptions(Boolean includeChildren, Integer page, Integer limit,
                                        Sort sort, SortDirection sortDirection,
                                        Boolean includeStreamValues) {
        this.includeChildren = includeChildren;
        this.page = page;
        this.limit = limit;
        this.sort = sort;
        this.sortDirection = sortDirection;
        if (includeStreamValues != null)
            this.includeStreamValues = includeStreamValues.toString();
    }

    public ListCollectionDevicesOptions(Boolean includeChildren, Integer page, Integer limit,
                                        Sort sort, SortDirection sortDirection,
                                        List<String> includeStreamValues) {
        this.includeChildren = includeChildren;
        this.page = page;
        this.limit = limit;
        this.sort = sort;
        this.sortDirection = sortDirection;
        if (includeStreamValues != null)
            this.includeStreamValues = StringUtils.join(includeStreamValues, ",");
    }


    public Boolean getIncludeChildren() {
        return includeChildren;
    }

    public void setIncludeChildren(Boolean includeChildren) {
        this.includeChildren = includeChildren;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getIncludeStreamValues() {
        return includeStreamValues;
    }

    public void setIncludeStreamValues(Boolean includeStreamValues) {
        this.includeStreamValues = includeStreamValues == null ? null : includeStreamValues.toString();
    }

    public void setIncludeStreamValues(List<String> includeStreamValues) {
        this.includeStreamValues = includeStreamValues == null ? null : StringUtils.join(includeStreamValues, ",");
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        if (includeChildren != null) map.put("include_children", includeChildren.toString());
        if (page != null) map.put("page", page.toString());
        if (limit != null) map.put("limit", limit.toString());
        if (sort != null) map.put("sort", sort.field());
        if (sortDirection != null) map.put("dir", sortDirection.direction());
        if (includeStreamValues != null) map.put("include_stream_values", includeStreamValues);
        return map;
    }

    public enum Sort {
        LAST_ACTIVITY("last_activity"),
        CREATED("created"),
        NAME("name");

        private final String field;

        Sort(String field) {
            this.field = field;
        }

        public String field() {
            return field;
        }
    }
}
