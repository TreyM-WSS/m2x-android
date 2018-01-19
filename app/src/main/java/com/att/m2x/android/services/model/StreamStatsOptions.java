package com.att.m2x.android.services.model;

import com.att.m2x.android.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by collinbrown on 10/15/17.
 */

public class StreamStatsOptions {
    private Date start;
    private Date end;
    private Integer min;
    private Integer max;

    public StreamStatsOptions() {
    }

    public StreamStatsOptions(Date start, Date end, Integer min, Integer max) {
        this.start = start;
        this.end = end;
        this.min = min;
        this.max = max;
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

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        if (start != null) map.put("start", DateUtils.dateTimeToString(start));
        if (end != null) map.put("end", DateUtils.dateTimeToString(end));
        if (min != null) map.put("min", min.toString());
        if (max != null) map.put("max", max.toString());
        return map;
    }
}
