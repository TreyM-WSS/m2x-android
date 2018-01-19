package com.att.m2x.android.services.model;

import com.att.m2x.android.model.Command;
import com.att.m2x.android.utils.ArrayUtils;
import com.att.m2x.android.utils.DateUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by collinbrown on 10/15/17.
 */

public class DeviceCommand {
    private String id;
    private String url;
    private String name;
    private Map<String, String> data;
    private Date sentAt;
    private Command.CommandStatus status;
    private Map<String, String> responseData;
    private Date receivedAt;

    public DeviceCommand() {
    }

    public DeviceCommand(String id, String url, String name, Map<String, String> data, Date sentAt,
                         Command.CommandStatus status, Map<String, String> responseData,
                         Date receivedAt) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.data = data;
        this.sentAt = sentAt;
        this.status = status;
        this.responseData = responseData;
        this.receivedAt = receivedAt;
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

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public Date getSentAt() {
        return sentAt;
    }

    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }

    public Command.CommandStatus getStatus() {
        return status;
    }

    public void setStatus(Command.CommandStatus status) {
        this.status = status;
    }

    public Map<String, String> getResponseData() {
        return responseData;
    }

    public void setResponseData(Map<String, String> responseData) {
        this.responseData = responseData;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Command toCommand() {
        return new Command(id, url, name, data, sentAt, null);
    }

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("id")) id = jsonObject.getString("id");
        if (jsonObject.has("url")) url = jsonObject.getString("url");
        if (jsonObject.has("name")) name = jsonObject.getString("name");
        if (!jsonObject.isNull("data")) data = ArrayUtils.jsonObjectToMap(jsonObject.getJSONObject("data"));
        if (!jsonObject.isNull("sent_at")) try {
            sentAt = DateUtils.stringToDateTime(jsonObject.getString("sent_at"));
        } catch (ParseException e) {
            throw new JSONException(e.getMessage());
        }
        if (!jsonObject.isNull("status")) status = Command.CommandStatus.parse(jsonObject.getString("status"));
        if (!jsonObject.isNull("response_data")) responseData = ArrayUtils.jsonObjectToMap(jsonObject.getJSONObject("response_data"));
        if (!jsonObject.isNull("received_at")) try {
            receivedAt = DateUtils.stringToDateTime(jsonObject.getString("received_at"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static DeviceCommand fromJsonObject(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) return null;
        DeviceCommand command = new DeviceCommand();
        command.fromJson(jsonObject);
        return command;
    }
}
