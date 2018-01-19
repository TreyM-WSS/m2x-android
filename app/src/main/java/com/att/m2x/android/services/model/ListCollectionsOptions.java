package com.att.m2x.android.services.model;

import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by collinbrown on 10/15/17.
 */

public class ListCollectionsOptions {
    private String parent;
    private String name;
    private Set<String> tags;

    public ListCollectionsOptions() {
    }

    public ListCollectionsOptions(String parent, String name, Set<String> tags) {
        this.parent = parent;
        this.name = name;
        this.tags = tags;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        if (parent != null) map.put("parent", parent);
        if (name != null) map.put("name", name);
        if (ArrayUtils.isNotEmpty(tags)) map.put("tags", StringUtils.join(tags, ","));
        return map;
    }

}
