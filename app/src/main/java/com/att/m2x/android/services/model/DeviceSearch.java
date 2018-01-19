package com.att.m2x.android.services.model;

import android.util.Log;

import com.att.m2x.android.model.Command.CommandStatus;
import com.att.m2x.android.model.Device;
import com.att.m2x.android.model.Visibility;
import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.DateUtils;
import com.att.m2x.android.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by collinbrown on 8/30/17.
 */

public class DeviceSearch {

    private String name;
    private String description;
    private Visibility visibility;
    private Device.DeviceStatus status;
    private Collection<String> ids;
    private Collection<String> serials;
    private Collection<String> tags;
    private Collection<String> triggers;
    private Collection<String> activatedTriggers;
    private Collection<String> inactiveTriggers;
    private Collection<String> enabledTriggers;
    private Collection<String> disabledTriggers;
    private String collectionId;
    private String distributionId;
    private Date modifiedSince;
    private Date unmodifiedSince;
    private CommandStatus commandStatus;
    private Date commandSince;
    private Collection<StreamFilter> streams;
    private Map<String, String> metadata;
    private Collection<LocationFilter> locations;
    private Integer page;
    private Integer limit;
    private DeviceSort sort;
    private SortDirection sortDirection;

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

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Device.DeviceStatus getStatus() {
        return status;
    }

    public void setStatus(Device.DeviceStatus status) {
        this.status = status;
    }

    public Collection<String> getIds() {
        return ids;
    }

    public void setIds(Collection<String> ids) {
        this.ids = ids;
    }

    public Collection<String> getSerials() {
        return serials;
    }

    public void setSerials(Collection<String> serials) {
        this.serials = serials;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public Collection<String> getTriggers() {
        return triggers;
    }

    public void setTriggers(Collection<String> triggers) {
        this.triggers = triggers;
    }

    public Collection<String> getActivatedTriggers() {
        return activatedTriggers;
    }

    public void setActivatedTriggers(Collection<String> activatedTriggers) {
        this.activatedTriggers = activatedTriggers;
    }

    public Collection<String> getInactiveTriggers() {
        return inactiveTriggers;
    }

    public void setInactiveTriggers(Collection<String> inactiveTriggers) {
        this.inactiveTriggers = inactiveTriggers;
    }

    public Collection<String> getEnabledTriggers() {
        return enabledTriggers;
    }

    public void setEnabledTriggers(Collection<String> enabledTriggers) {
        this.enabledTriggers = enabledTriggers;
    }

    public Collection<String> getDisabledTriggers() {
        return disabledTriggers;
    }

    public void setDisabledTriggers(Collection<String> disabledTriggers) {
        this.disabledTriggers = disabledTriggers;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(String distributionId) {
        this.distributionId = distributionId;
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

    public CommandStatus getCommandStatus() {
        return commandStatus;
    }

    public void setCommandStatus(CommandStatus commandStatus) {
        this.commandStatus = commandStatus;
    }

    public Date getCommandSince() {
        return commandSince;
    }

    public void setCommandSince(Date commandSince) {
        this.commandSince = commandSince;
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

    public DeviceSearch() {
    }

    public DeviceSearch(
            final String name, final String description, final Visibility visibility,
            final Device.DeviceStatus status, final Collection<String> ids,
            final Collection<String> serials, final Collection<String> tags,
            final Collection<String> triggers, final Collection<String> activatedTriggers,
            final Collection<String> inactiveTriggers, final Collection<String> enabledTriggers,
            final Collection<String> disabledTriggers, final String collectionId,
            final String distributionId, final Date modifiedSince, final Date unmodifiedSince,
            final CommandStatus commandStatus, final Date commandSince,
            final Collection<StreamFilter> streams,
            final Map<String, String> metadata,
            final Collection<LocationFilter> locations,
            final Integer page, final Integer limit,
            final DeviceSort sort, final SortDirection sortDirection) {
        this.name = name;
        this.description = description;
        this.visibility = visibility;
        this.status = status;
        this.ids = ids;
        this.serials = serials;
        this.tags = tags;
        this.triggers = triggers;
        this.activatedTriggers = activatedTriggers;
        this.inactiveTriggers = inactiveTriggers;
        this.enabledTriggers = enabledTriggers;
        this.disabledTriggers = disabledTriggers;
        this.collectionId = collectionId;
        this.distributionId = distributionId;
        this.modifiedSince = modifiedSince;
        this.unmodifiedSince = unmodifiedSince;
        this.commandStatus = commandStatus;
        this.commandSince = commandSince;
        this.streams = streams;
        this.metadata = metadata;
        this.locations = locations;
        this.page = page;
        this.limit = limit;
        this.sort = sort;
        this.sortDirection = sortDirection;
    }

    public JSONObject toJsonObject() {
        JSONObject body = new JSONObject();

        try {
            // ID's
            if (ArrayUtils.isNotEmpty(ids)) {
                body.put("ids", StringUtils.join(ids, ","));
            }

            // Serial
            if (ArrayUtils.isNotEmpty(serials)) {
                body.put("serials", StringUtils.join(serials, ","));
            }

            // Tags
            if (ArrayUtils.isNotEmpty(tags)) {
                body.put("tags", StringUtils.join(tags, ","));
            }

            // Triggers
            if (ArrayUtils.isNotEmpty(triggers)) {
                body.put("triggers", StringUtils.join(triggers, ","));
            }

            // Activated Triggers
            if (ArrayUtils.isNotEmpty(activatedTriggers)) {
                body.put("activated_triggers", StringUtils.join(activatedTriggers, ","));
            }

            // Inactive Triggers
            if (ArrayUtils.isNotEmpty(inactiveTriggers)) {
                body.put("inactive_triggers", StringUtils.join(inactiveTriggers, ","));
            }

            // Enabled Triggers
            if (ArrayUtils.isNotEmpty(enabledTriggers)) {
                body.put("enabled_triggers", StringUtils.join(enabledTriggers, ","));
            }

            // Disabled Triggers
            if (ArrayUtils.isNotEmpty(disabledTriggers)) {
                body.put("disabled_triggers", StringUtils.join(disabledTriggers, ","));
            }

            // Collection
            if (StringUtils.hasText(collectionId)) {
                body.put("collection", collectionId);
            }

            // Distribution
            if (StringUtils.hasText(distributionId)) {
                body.put("distribution", distributionId);
            }

            // Modified Since
            if (modifiedSince != null && modifiedSince.getTime() > 0) {
                body.put("modified_since", DateUtils.dateTimeToString(modifiedSince));
            }

            // Unmodified Since
            if (unmodifiedSince != null && unmodifiedSince.getTime() > 0) {
                body.put("unmodified_since", DateUtils.dateTimeToString(unmodifiedSince));
            }

            // Command Status
            if (commandStatus != null) {
                body.put("command_status", commandStatus.value());
            }

            // Command Since
            if (commandSince != null && commandSince.getTime() > 0) {
                body.put("command_since", DateUtils.dateTimeToString(commandSince));
            }

            // Name
            if (StringUtils.hasText(name)) {
                body.put("name", name);
            }

            // Description
            if (StringUtils.hasText(description)) {
                body.put("description", description);
            }

            // Status
            if (status != null) {
                body.put("status", status.status());
            }

            // Visibility
            if (visibility != null) {
                body.put("visibility", visibility.visibility());
            }

            // Streams
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

            // Locations
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

        } catch(JSONException e) {
            Log.e("DeviceSearch", e.getMessage());
        }

        return body;
    }

}
