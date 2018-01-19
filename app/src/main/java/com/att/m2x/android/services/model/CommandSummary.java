package com.att.m2x.android.services.model;

import com.att.m2x.android.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by collinbrown on 10/15/17.
 */

public class CommandSummary {
    private String id;
    private String url;
    private String name;
    private Date sentAt;
    private StatusCounts statusCounts;

    public CommandSummary() {
    }

    public CommandSummary(String id, String url, String name, Date sentAt, StatusCounts statusCounts) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.sentAt = sentAt;
        this.statusCounts = statusCounts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public StatusCounts getStatusCounts() {
        return statusCounts;
    }

    public void setStatusCounts(StatusCounts statusCounts) {
        this.statusCounts = statusCounts;
    }

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("id")) id = jsonObject.getString("id");
        if (jsonObject.has("url")) url = jsonObject.getString("url");
        if (jsonObject.has("name")) name = jsonObject.getString("name");
        if (!jsonObject.isNull("sent_at")) try {
            sentAt = DateUtils.stringToDateTime(jsonObject.getString("sent_at"));
        } catch (ParseException e) {
            throw new JSONException(e.getMessage());
        }
        if (jsonObject.has("status_counts"))
            statusCounts = StatusCounts.fromJsonObject(jsonObject.getJSONObject("status_counts"));
    }

    public static CommandSummary fromJsonObject(JSONObject jsonObject) throws JSONException {
        CommandSummary command = new CommandSummary();
        command.fromJson(jsonObject);
        return command;
    }

    public static class StatusCounts {
        private Integer processed;
        private Integer accepted;
        private Integer rejected;

        public StatusCounts() {
        }

        public StatusCounts(Integer processed, Integer accepted, Integer rejected) {
            this.processed = processed;
            this.accepted = accepted;
            this.rejected = rejected;
        }

        public Integer getProcessed() {
            return processed;
        }

        public void setProcessed(Integer processed) {
            this.processed = processed;
        }

        public Integer getAccepted() {
            return accepted;
        }

        public void setAccepted(Integer accepted) {
            this.accepted = accepted;
        }

        public Integer getRejected() {
            return rejected;
        }

        public void setRejected(Integer rejected) {
            this.rejected = rejected;
        }

        public void fromJson(JSONObject jsonObject) throws JSONException {
            if (!jsonObject.isNull("processed")) processed = jsonObject.getInt("processed");
            if (!jsonObject.isNull("accepted")) accepted = jsonObject.getInt("accepted");
            if (!jsonObject.isNull("rejected")) rejected = jsonObject.getInt("rejected");
        }

        public static StatusCounts fromJsonObject(JSONObject jsonObject) throws JSONException {
            if (jsonObject == null) return null;
            StatusCounts counts = new StatusCounts();
            counts.fromJson(jsonObject);
            return counts;
        }
    }
}
