package com.att.m2x.android.services.model;

import android.util.Log;

import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by collinbrown on 10/14/17.
 */
public class PublicCatalogSearch {

    private String name;
    private String description;
    private Integer page;
    private Integer limit;
    private Collection<String> tags;
    private Collection<String> serials;
    private DeviceSort sort;
    private SortDirection sortDirection;
    private Collection<StreamFilter> streams;
    private Map<String, String> metadata;
    private Collection<LocationFilter> locations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public Collection<String> getSerials() {
        return serials;
    }

    public void setSerials(Collection<String> serials) {
        this.serials = serials;
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

    public Collection<StreamFilter> getStreams() {
        return streams;
    }

    public void setStreams(Collection<StreamFilter> streams) {
        this.streams = streams;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Collection<LocationFilter> getLocations() {
        return locations;
    }

    public void setLocations(Collection<LocationFilter> locations) {
        this.locations = locations;
    }

    public PublicCatalogSearch() {
    }

    public PublicCatalogSearch(String name, String description, Integer page, Integer limit,
                               Collection<String> tags, Collection<String> serials, DeviceSort sort,
                               SortDirection sortDirection, Collection<StreamFilter> streams,
                               Map<String, String> metadata, Collection<LocationFilter> locations) {
        this.name = name;
        this.description = description;
        this.page = page;
        this.limit = limit;
        this.tags = tags;
        this.serials = serials;
        this.sort = sort;
        this.sortDirection = sortDirection;
        this.streams = streams;
        this.metadata = metadata;
        this.locations = locations;
    }

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        if (StringUtils.hasText(name)) {
            params.put("name", name);
        }

        if (StringUtils.hasText(description)) {
            params.put("description", description);
        }

        if (page != null) {
            params.put("page", page.toString());
        }

        if (limit != null) {
            params.put("limit", limit.toString());
        }

        if (ArrayUtils.isNotEmpty(tags)) {
            params.put("tags", StringUtils.join(tags, ","));
        }

        if (ArrayUtils.isNotEmpty(serials)) {
            params.put("serials", StringUtils.join(serials, ","));
        }
        if (sort != null) {
            params.put("sort", sort.fieldName());
        }

        if (sortDirection != null) {
            params.put("dir", sortDirection.direction());
        }

        return params;
    }

    public JSONObject getBody() {
        JSONObject body = new JSONObject();
        try {
            if (ArrayUtils.isNotEmpty(streams)) {
                JSONObject jsonStreams = new JSONObject();
                for (StreamFilter streamFilter : streams) {
                    JSONObject jsonStreamFilter = streamFilter.toJsonObject();
                    if (jsonStreamFilter != null) {
                        Iterator<String> itrStreamFilterKeys = jsonStreamFilter.keys();
                        while (itrStreamFilterKeys.hasNext()) {
                            String key = itrStreamFilterKeys.next();
                            jsonStreams.put(key, jsonStreamFilter.get(key));
                        }
                    }
                }
                body.put("streams", jsonStreams);
            }

            // Metadata
            if (metadata != null && !metadata.isEmpty()) {
                JSONObject jsonMetadata = new JSONObject();
                for (Map.Entry<String, String> nextMetadata : metadata.entrySet()) {
                    if (nextMetadata.getKey() != null && nextMetadata.getKey().trim().length() > 0 && nextMetadata.getValue() != null && nextMetadata.getValue().trim().length() > 0) {
                        JSONObject nextMetadataJson = new JSONObject();
                        nextMetadataJson.put("match", nextMetadata.getValue());
                        jsonMetadata.put(nextMetadata.getKey(), nextMetadataJson);
                    }
                }
                body.put("metadata", jsonMetadata);
            }

            if (locations != null && !locations.isEmpty()) {
                JSONObject jsonLocation = new JSONObject();
                for (LocationFilter nextLocation : locations) {
                    JSONObject nextLocationJson = nextLocation.toJsonObject();
                    Iterator<String> itrJsonKeys = nextLocationJson.keys();
                    while (itrJsonKeys.hasNext()) {
                        String nextKey = itrJsonKeys.next();
                        Object nextValue = nextLocationJson.get(nextKey);
                        if (nextValue != null) {
                            jsonLocation.put(nextKey, nextValue);
                        }
                    }
                }
                body.put("location", jsonLocation);
            }

        } catch (JSONException e) {
            Log.e("PublicCatalogSearch", "JSON Error", e);
        }
        return body;
    }

    public enum DeviceSort {
        NAME("name"),
        SERIAL("serial"),
        CREATED("created");

        private String fieldName;

        DeviceSort(String fieldName) {
            this.fieldName = fieldName;
        }

        public String fieldName() {
            return fieldName;
        }
    }
}
