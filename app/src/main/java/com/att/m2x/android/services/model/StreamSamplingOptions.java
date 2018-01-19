package com.att.m2x.android.services.model;

import com.att.m2x.android.utils.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by collinbrown on 10/15/17.
 */
public class StreamSamplingOptions {
    private Integer interval;
    private SamplingType type;
    private Date start;
    private Date end;
    private Double min;
    private Double max;
    private Integer limit;

    public StreamSamplingOptions() {
    }

    public StreamSamplingOptions(Integer interval, SamplingType type, Date start, Date end,
                                 Double min, Double max, Integer limit) {
        this.interval = interval;
        this.type = type;
        this.start = start;
        this.end = end;
        this.min = min;
        this.max = max;
        this.limit = limit;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public SamplingType getType() {
        return type;
    }

    public void setType(SamplingType type) {
        this.type = type;
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

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        if (interval != null) map.put("interval", interval.toString());
        if (type != null) map.put("type", type.type());
        if (start != null) map.put("start", DateUtils.dateTimeToString(start));
        if (end != null) map.put("end", DateUtils.dateTimeToString(end));
        if (min != null) map.put("min", min.toString());
        if (max != null) map.put("max", max.toString());
        if (limit != null) map.put("limit", limit.toString());
        return map;
    }

    public enum SamplingType {
        NTH("nth"),
        MIN("min"),
        MAX("max"),
        COUNT("count"),
        AVG("avg"),
        SUM("sum"),
        STDDEV("stddev");

        private final String type;

        SamplingType(String type) {
            this.type = type;
        }

        public String type() {
            return type;
        }
    }
}
