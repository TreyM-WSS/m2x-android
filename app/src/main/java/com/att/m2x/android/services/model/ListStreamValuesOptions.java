package com.att.m2x.android.services.model;

import com.att.m2x.android.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by collinbrown on 10/15/17.
 */
public class ListStreamValuesOptions {
    private Date start;
    private Date end;
    private Double min;
    private Double max;
    private Integer limit;
    private ValueSort sort;
    private SortDirection sortDirection;

    public ListStreamValuesOptions() {
    }

    public ListStreamValuesOptions(Date start, Date end, Double min, Double max, Integer limit,
                                   ValueSort sort, SortDirection sortDirection) {
        this.start = start;
        this.end = end;
        this.min = min;
        this.max = max;
        this.limit = limit;
        this.sort = sort;
        this.sortDirection = sortDirection;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public ValueSort getSort() {
        return sort;
    }

    public void setSort(ValueSort sort) {
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
        if (start != null) map.put("start", DateUtils.dateTimeToString(start));
        if (end != null) map.put("end", DateUtils.dateTimeToString(end));
        if (min != null) map.put("min", min.toString());
        if (max != null) map.put("max", max.toString());
        if (limit != null) map.put("limit", limit.toString());
        if (sort != null) map.put("sort", sort.sort());
        if (sortDirection != null) map.put("dir", sortDirection.direction());
        return map;
    }

    public enum ValueSort {
        TIMESTAMP("timestamp"),
        VALUE("value");

        private final String sort;

        ValueSort(String sort) {
            this.sort = sort;
        }

        public String sort() {
            return sort;
        }
    }
}
