package com.att.m2x.android.model;

import com.att.m2x.android.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by collinbrown on 10/15/17.
 */

public class StreamStats {
    private Date start;
    private Date end;
    private Stats stats;

    public StreamStats() {
    }

    public StreamStats(Date start, Date end, Stats stats) {
        this.start = start;
        this.end = end;
        this.stats = stats;
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

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public static StreamStats fromJsonObject(JSONObject jsonObject) throws JSONException, ParseException {
        if (jsonObject == null) return null;
        StreamStats stats = new StreamStats();
        if (jsonObject.has("start") && !jsonObject.isNull("start"))
            stats.setStart(DateUtils.stringToDateTime(jsonObject.getString("start")));
        if (jsonObject.has("end") && !jsonObject.isNull("end"))
            stats.setStart(DateUtils.stringToDateTime(jsonObject.getString("end")));
        if (jsonObject.has("stats")) stats.stats = Stats.fromJsonObject(jsonObject.getJSONObject("stats"));
        return stats;
    }

    public static class Stats {
        private Integer count;
        private Double min;
        private Double max;
        private Double avg;
        private Double stddev;

        public Stats() {
        }

        public Stats(Integer count, Double min, Double max, Double avg, Double stddev) {
            this.count = count;
            this.min = min;
            this.max = max;
            this.avg = avg;
            this.stddev = stddev;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
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

        public Double getAvg() {
            return avg;
        }

        public void setAvg(Double avg) {
            this.avg = avg;
        }

        public Double getStddev() {
            return stddev;
        }

        public void setStddev(Double stddev) {
            this.stddev = stddev;
        }

        public static Stats fromJsonObject(JSONObject jsonObject) throws JSONException {
            if (jsonObject == null) return null;
            Stats stats = new Stats();
            if (jsonObject.has("count") && !jsonObject.isNull("count")) stats.count = jsonObject.getInt("count");
            if (jsonObject.has("min") && !jsonObject.isNull("min")) stats.min = jsonObject.getDouble("min");
            if (jsonObject.has("max") && !jsonObject.isNull("max")) stats.max = jsonObject.getDouble("max");
            if (jsonObject.has("avg") && !jsonObject.isNull("avg")) stats.min = jsonObject.getDouble("avg");
            if (jsonObject.has("stddev") && !jsonObject.isNull("stddev")) stats.stddev = jsonObject.getDouble("stddev");
            return stats;
        }
    }
}
