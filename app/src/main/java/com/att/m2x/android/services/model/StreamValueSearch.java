package com.att.m2x.android.services.model;

import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.DateUtils;
import com.att.m2x.android.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by collinbrown on 10/15/17.
 */
public class StreamValueSearch {

    private Date start;
    private Date end;
    private List<String> streams;
    private Map<String, Condition> conditions;

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

    public List<String> getStreams() {
        return streams;
    }

    public void setStreams(List<String> streams) {
        this.streams = streams;
    }

    public Map<String, Condition> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, Condition> conditions) {
        this.conditions = conditions;
    }

    public void addCondition(String stream, Condition condition) {
        if (this.conditions == null) {
            this.conditions = new HashMap<>();
        }
        this.conditions.put(stream, condition);
    }

    public StreamValueSearch() {
    }

    public StreamValueSearch(Date start, Date end, List<String> streams, Map<String, Condition> conditions) {
        this.start = start;
        this.end = end;
        this.streams = streams;
        this.conditions = conditions;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (start != null) jsonObject.put("start", DateUtils.dateTimeToString(start));
            if (end != null) jsonObject.put("end", DateUtils.dateTimeToString(end));
            if (streams != null) {
                JSONArray streamsArray = new JSONArray();
                for (String stream : streams) {
                    streamsArray.put(stream);
                }
                jsonObject.put("streams", streamsArray);
            }
            if (conditions != null) {
                JSONObject conditionObject = new JSONObject();
                for (Map.Entry<String, Condition> e : conditions.entrySet()) {
                    conditionObject.put(e.getKey(), e.getValue().toJsonObject());
                }
                jsonObject.put("conditions", conditionObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static class Condition {
        private Double lt;
        private Double lte;
        private Double gt;
        private Double gte;
        private Double eq;

        public Condition() {
        }

        public Condition(Double lt, Double lte, Double gt, Double gte, Double eq) {
            this.lt = lt;
            this.lte = lte;
            this.gt = gt;
            this.gte = gte;
            this.eq = eq;
        }

        public Double getLt() {
            return lt;
        }

        public void setLt(Double lt) {
            this.lt = lt;
        }

        public Double getLte() {
            return lte;
        }

        public void setLte(Double lte) {
            this.lte = lte;
        }

        public Double getGt() {
            return gt;
        }

        public void setGt(Double gt) {
            this.gt = gt;
        }

        public Double getGte() {
            return gte;
        }

        public void setGte(Double gte) {
            this.gte = gte;
        }

        public Double getEq() {
            return eq;
        }

        public void setEq(Double eq) {
            this.eq = eq;
        }

        public JSONObject toJsonObject() throws JSONException {
            JSONObject jsonObject = new JSONObject();
            if (lt != null) jsonObject.put("lt", lt.toString());
            if (lte != null) jsonObject.put("lte", lte.toString());
            if (gt != null) jsonObject.put("gt", gt.toString());
            if (gte != null) jsonObject.put("gte", gte.toString());
            if (eq != null) jsonObject.put("eq", eq.toString());
            return jsonObject;
        }
    }

}
