package com.att.m2x.android.services.model;

import com.att.m2x.android.model.Visibility;
import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.DateUtils;
import com.att.m2x.android.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by collinbrown on 8/30/17.
 */

public class DistributionSearch {

    private String q;
    private Integer page;
    private Integer limit;
    private Date modifiedSince;
    private Date unmodifiedSince;
    private Visibility visibility;
    private Collection<String> tags;
    private DistributionSort sort;
    private SortDirection sortDirection;
    private Map<String, String> metadata;

    public DistributionSearch() {
    }

    public DistributionSearch(String q, Integer page, Integer limit, Date modifiedSince,
                              Date unmodifiedSince, Visibility visibility,
                              Collection<String> tags, DistributionSort sort,
                              SortDirection sortDirection, Map<String, String> metadata) {
        this.q = q;
        this.page = page;
        this.limit = limit;
        this.modifiedSince = modifiedSince;
        this.unmodifiedSince = unmodifiedSince;
        this.visibility = visibility;
        this.tags = tags;
        this.sort = sort;
        this.sortDirection = sortDirection;
        this.metadata = metadata;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
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

    public Date getModifiedSince() {
        return modifiedSince;
    }

    public void setModifiedSince(Date modifiedSince) {
        this.modifiedSince = modifiedSince;
    }

    public Date getUnmodifiedSince() {
        return unmodifiedSince;
    }

    public void setUnmodifiedSince(Date unmodifiedSince) {
        this.unmodifiedSince = unmodifiedSince;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public DistributionSort getSort() {
        return sort;
    }

    public void setSort(DistributionSort sort) {
        this.sort = sort;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(SortDirection sortDirection) {
        this.sortDirection = sortDirection;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();

        try {
            if (StringUtils.hasText(q)) body.put("q", q);
            if (page != null) body.put("page", page);
            if (limit != null) body.put("limit", limit);
            if (modifiedSince != null) body.put("modified_since", DateUtils.dateTimeToString(modifiedSince));
            if (unmodifiedSince != null) body.put("unmodified_since", DateUtils.dateTimeToString(unmodifiedSince));
            if (visibility != null) body.put("visibility", visibility.visibility());
            if (ArrayUtils.isNotEmpty(tags)) body.put("tags", StringUtils.join(tags, ","));
            if (sort != null) body.put("sort", sort.fieldName());
            if (sortDirection != null) body.put("dir", sortDirection.direction());
            // Metadata
            if (metadata != null && !metadata.isEmpty()) {
                JSONObject jsonMetadata = new JSONObject();
                for (Map.Entry<String, String> nextMetadata : metadata.entrySet()) {
                    JSONObject nextMetadataJson = new JSONObject();
                    nextMetadataJson.put("match", nextMetadata.getValue());
                    jsonMetadata.put(nextMetadata.getKey(), nextMetadataJson);
                }
                body.put("metadata", jsonMetadata);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return body;
    }

    public enum DistributionSort {
        NAME("name"),
        CREATED("created");

        private String fieldName;

        DistributionSort(String fieldName) {
            this.fieldName = fieldName;
        }

        public String fieldName() {
            return fieldName;
        }
    }
}
