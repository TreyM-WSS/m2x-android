package com.att.m2x.android.services.model;

import com.att.m2x.android.model.Command;
import com.att.m2x.android.services.model.SortDirection;
import com.att.m2x.android.utils.DateUtils;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by collinbrown on 10/15/17.
 */

public class ListDeviceCommandsOptions {
    private Integer limit;
    private Integer page;
    private SortDirection sortDirection;
    private Date start;
    private Date end;
    private String name;
    private Command.CommandStatus status;

    public ListDeviceCommandsOptions() {
    }

    public ListDeviceCommandsOptions(Integer limit, Integer page, SortDirection sortDirection,
                                     Date start, Date end, String name, Command.CommandStatus status) {
        this.limit = limit;
        this.page = page;
        this.sortDirection = sortDirection;
        this.start = start;
        this.end = end;
        this.name = name;
        this.status = status;
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

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Command.CommandStatus getStatus() {
        return status;
    }

    public void setStatus(Command.CommandStatus status) {
        this.status = status;
    }

    public Map<String, String> toMap() {
        HashMap<String, String> map = new HashMap<>();
        if (limit != null) map.put("limit", limit.toString());
        if (page != null) map.put("page", page.toString());
        if (sortDirection != null) map.put("dir", sortDirection.direction());
        if (start != null) map.put("start", DateUtils.dateTimeToString(start));
        if (end != null) map.put("end", DateUtils.dateTimeToString(end));
        if (name != null) map.put("name", name);
        if (status != null) map.put("status", status.value());
        return map;
    }
}
